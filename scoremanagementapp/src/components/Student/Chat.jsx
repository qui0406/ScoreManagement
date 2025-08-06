
import React, { useState, useEffect, useRef, useCallback } from "react";
import { io } from "socket.io-client";
import { authApis, endpoints } from "./../../configs/Apis";


const Chat = ({ classDetailId = "1" }) => { // Default classDetailId for demo
  const [message, setMessage] = useState("");
  const [newChatOpen, setNewChatOpen] = useState(false);
  const [conversations, setConversations] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [selectedConversation, setSelectedConversation] = useState(null);
  const [messagesMap, setMessagesMap] = useState({});
  const messageContainerRef = useRef(null);
  const socketRef = useRef(null);

  // Scroll to bottom of message container
  const scrollToBottom = useCallback(() => {
    if (messageContainerRef.current) {
      messageContainerRef.current.scrollTop = messageContainerRef.current.scrollHeight;
      setTimeout(() => {
        messageContainerRef.current.scrollTop = messageContainerRef.current.scrollHeight;
      }, 100);
      setTimeout(() => {
        messageContainerRef.current.scrollTop = messageContainerRef.current.scrollHeight;
      }, 300);
    }
  }, []);

  // Fetch conversations
  const fetchConversations = useCallback(async () => {
    setLoading(true);
    try {
      const response = await authApis().get(endpoints.getAllConversations(classDetailId));
      setConversations(response.data || []);
      console.log("Conversations fetched:", response.data);
      if (response.length > 0 && !selectedConversation) {
        setSelectedConversation(response[0]);
      }

      console.log("Conversations fetched:", response.data);
    } catch (err) {
      setError("Failed to load conversations");
    } finally {
      setLoading(false);
    }
  }, [classDetailId, selectedConversation]);

  // Fetch messages for selected conversation
  const fetchMessages = useCallback(async (conversationId) => {
    try {
      const messages = await authApis().get(endpoints.getAllMessages(conversationId));
      setMessagesMap((prev) => ({
        ...prev,
        [conversationId]: messages || [],
      }));
      scrollToBottom();
    } catch (err) {
      setError("Failed to load messages");
    }
  }, [scrollToBottom]);

  // Start new conversation
  const handleSelectNewChatUser = async (userId) => {
    try {
      const newConversation = await authApis().post(endpoints.createConversation(classDetailId));
      setConversations([newConversation, ...conversations]);
      setSelectedConversation(newConversation);
      setMessagesMap((prev) => ({
        ...prev,
        [newConversation.id]: [],
      }));
      setNewChatOpen(false);
    } catch (err) {
      setError("Failed to start new conversation");
    }
  };

  // Select conversation
  const handleConversationSelect = (conversation) => {
    setSelectedConversation(conversation);
    setConversations((prev) =>
      prev.map((conv) =>
        conv.id === conversation.id ? { ...conv, unread: 0 } : conv
      )
    );
    if (!messagesMap[conversation.id]) {
      fetchMessages(conversation.id);
    }
  };

  // Send message
  const handleSendMessage = async () => {
    if (!message.trim() || !selectedConversation) return;

    try {
      const newMessage = {
        conversationId: selectedConversation.id,
        message: message,
      };
      const sentMessage = await authApis().post(endpoints.createMessage(selectedConversation.id), newMessage);
      socketRef.current.emit("message", sentMessage); // Emit to Socket.IO
      setMessagesMap((prev) => ({
        ...prev,
        [selectedConversation.id]: [
          ...(prev[selectedConversation.id] || []),
          sentMessage,
        ],
      }));
      setConversations((prev) =>
        prev.map((conv) =>
          conv.id === selectedConversation.id
            ? {
                ...conv,
                lastMessage: message,
                modifiedDate: new Date().toISOString(),
              }
            : conv
        )
      );
      setMessage("");
      scrollToBottom();
    } catch (err) {
      setError("Failed to send message");
    }
  };

  // Initialize socket and fetch conversations
  useEffect(() => {
    fetchConversations();

    if (!socketRef.current) {
      console.log("Initializing socket connection...");
      const token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxdWk1Iiwicm9sZSI6IlJPTEVfVVNFUiIsImV4cCI6MTc1NDUwNDA1MCwiaWF0IjoxNzU0NDE3NjUwfQ.Q2Qg8JikozuOrHQKfOi1pOAijmcUEC3BlgtS1fN7iBk";
      socketRef.current = io(`http://localhost:8099?token=${token}`);

      socketRef.current.on("connect", () => {
        console.log("Socket connected");
      });

      socketRef.current.on("disconnect", () => {
        console.log("Socket disconnected");
      });

      socketRef.current.on("message", (message) => {
        console.log("New message received:", message);
        setMessagesMap((prev) => ({
          ...prev,
          [message.conversationId]: [
            ...(prev[message.conversationId] || []),
            message,
          ],
        }));
        setConversations((prev) =>
          prev.map((conv) =>
            conv.id === message.conversationId
              ? {
                  ...conv,
                  lastMessage: message.message,
                  modifiedDate: new Date().toISOString(),
                  unread: conv.id === selectedConversation?.id ? 0 : (conv.unread || 0) + 1,
                }
              : conv
          )
        );
        scrollToBottom();
      });
    }

    return () => {
      if (socketRef.current) {
        socketRef.current.disconnect();
        socketRef.current = null;
      }
    };
  }, [fetchConversations, scrollToBottom, selectedConversation]);

  // Fetch messages when selected conversation changes
  useEffect(() => {
    if (selectedConversation && !messagesMap[selectedConversation.id]) {
      fetchMessages(selectedConversation.id);
    }
  }, [selectedConversation, fetchMessages, messagesMap]);

  const currentMessages = selectedConversation
    ? messagesMap[selectedConversation.id] || []
    : [];

  return (
    <div style={styles.container}>
      {/* Conversation List */}
      <div style={styles.conversationList}>
        <div style={styles.conversationHeader}>
          <h3 style={styles.headerText}>Chats</h3>
          <button
            style={styles.newChatButton}
            onClick={() => setNewChatOpen(true)}
          >
            +
          </button>
        </div>
        {newChatOpen && (
          <div style={styles.newChatPopover}>
            <button
              style={styles.newChatOption}
              onClick={() => handleSelectNewChatUser("123")}
            >
              User 123
            </button>
            <button
              style={styles.newChatOption}
              onClick={() => handleSelectNewChatUser("456")}
            >
              User 456
            </button>
            <button
              style={styles.closeButton}
              onClick={() => setNewChatOpen(false)}
            >
              Close
            </button>
          </div>
        )}
        <div style={styles.conversationItems}>
          {loading ? (
            <div style={styles.loading}>Loading...</div>
          ) : error ? (
            <div style={styles.error}>
              {error}
              <button onClick={() => { setError(null); fetchConversations(); }}>
                Retry
              </button>
            </div>
          ) : conversations.length === 0 ? (
            <div style={styles.empty}>No conversations yet. Start a new chat.</div>
          ) : (
            conversations.map((conversation) => (
              <div
                key={conversation.id}
                style={{
                  ...styles.conversationItem,
                  backgroundColor:
                    selectedConversation?.id === conversation.id
                      ? "#f0f0f0"
                      : "transparent",
                }}
                onClick={() => handleConversationSelect(conversation)}
              >
                <div style={styles.avatar}>
                  {conversation.unread > 0 && (
                    <span style={styles.badge}>{conversation.unread}</span>
                  )}
                  {conversation.conversationName[0]}
                </div>
                <div style={styles.conversationDetails}>
                  <div style={styles.conversationName}>
                    {conversation.conversationName}
                    <span style={styles.date}>
                      {new Date(conversation.modifiedDate).toLocaleDateString("vi-VN")}
                    </span>
                  </div>
                  <div style={styles.lastMessage}>
                    {conversation.lastMessage || "Start a conversation"}
                  </div>
                </div>
              </div>
            ))
          )}
        </div>
      </div>

      {/* Chat Area */}
      <div style={styles.chatArea}>
        {selectedConversation ? (
          <>
            <div style={styles.chatHeader}>
              <div style={styles.avatar}>{selectedConversation.conversationName[0]}</div>
              <h3>{selectedConversation.conversationName}</h3>
            </div>
            <div ref={messageContainerRef} style={styles.messageContainer}>
              {currentMessages.map((msg) => (
                <div
                  key={msg.id}
                  style={{
                    ...styles.message,
                    justifyContent: msg.me ? "flex-end" : "flex-start",
                  }}
                >
                  {!msg.me && (
                    <div style={styles.messageAvatar}>
                      {msg.sender?.avatar || "U"}
                    </div>
                  )}
                  <div
                    style={{
                      ...styles.messageBubble,
                      backgroundColor: msg.me ? "#e3f2fd" : "#f5f5f5",
                    }}
                  >
                    <p>{msg.message}</p>
                    <span style={styles.messageTime}>
                      {new Date(msg.createdDate).toLocaleString()}
                    </span>
                  </div>
                  {msg.me && (
                    <div style={{ ...styles.messageAvatar, backgroundColor: "#1976d2" }}>
                      You
                    </div>
                  )}
                </div>
              ))}
            </div>
            <form
              style={styles.messageForm}
              onSubmit={(e) => {
                e.preventDefault();
                handleSendMessage();
              }}
            >
              <input
                style={styles.messageInput}
                placeholder="Type a message"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
              />
              <button
                style={styles.sendButton}
                type="submit"
                disabled={!message.trim()}
              >
                Send
              </button>
            </form>
          </>
        ) : (
          <div style={styles.emptyChat}>
            Select a conversation to start chatting
          </div>
        )}
      </div>
    </div>
  );
};

const styles = {
  container: {
    display: "flex",
    height: "calc(100vh - 64px)",
    width: "100%",
    overflow: "hidden",
  },
  conversationList: {
    width: "300px",
    borderRight: "1px solid #ddd",
    display: "flex",
    flexDirection: "column",
  },
  conversationHeader: {
    padding: "16px",
    borderBottom: "1px solid #ddd",
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
  },
  headerText: {
    margin: 0,
    fontSize: "1.2rem",
  },
  newChatButton: {
    padding: "8px",
    backgroundColor: "#1976d2",
    color: "white",
    border: "none",
    borderRadius: "4px",
    cursor: "pointer",
  },
  newChatPopover: {
    position: "absolute",
    top: "60px",
    right: "16px",
    backgroundColor: "white",
    border: "1px solid #ddd",
    borderRadius: "4px",
    padding: "8px",
    zIndex: 1000,
  },
  newChatOption: {
    display: "block",
    width: "100%",
    padding: "8px",
    border: "none",
    background: "transparent",
    cursor: "pointer",
    textAlign: "left",
  },
  closeButton: {
    display: "block",
    width: "100%",
    padding: "8px",
    border: "none",
    background: "#f0f0f0",
    cursor: "pointer",
  },
  conversationItems: {
    flexGrow: 1,
    overflowY: "auto",
  },
  conversationItem: {
    display: "flex",
    padding: "8px 16px",
    cursor: "pointer",
  },
  avatar: {
    width: "40px",
    height: "40px",
    borderRadius: "50%",
    backgroundColor: "#ccc",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    marginRight: "8px",
    position: "relative",
  },
  badge: {
    position: "absolute",
    top: "-4px",
    right: "-4px",
    backgroundColor: "red",
    color: "white",
    borderRadius: "50%",
    padding: "2px 6px",
    fontSize: "0.7rem",
  },
  conversationDetails: {
    flexGrow: 1,
    overflow: "hidden",
  },
  conversationName: {
    display: "flex",
    justifyContent: "space-between",
    fontWeight: "bold",
  },
  date: {
    fontSize: "0.7rem",
    color: "#666",
  },
  lastMessage: {
    fontSize: "0.9rem",
    color: "#333",
    whiteSpace: "nowrap",
    overflow: "hidden",
    textOverflow: "ellipsis",
  },
  loading: {
    textAlign: "center",
    padding: "16px",
  },
  error: {
    padding: "16px",
    color: "red",
  },
  empty: {
    padding: "16px",
    textAlign: "center",
    color: "#666",
  },
  chatArea: {
    flexGrow: 1,
    display: "flex",
    flexDirection: "column",
  },
  chatHeader: {
    padding: "16px",
    borderBottom: "1px solid #ddd",
    display: "flex",
    alignItems: "center",
  },
  messageContainer: {
    flexGrow: 1,
    padding: "16px",
    overflowY: "auto",
  },
  message: {
    display: "flex",
    marginBottom: "16px",
  },
  messageAvatar: {
    width: "32px",
    height: "32px",
    borderRadius: "50%",
    backgroundColor: "#ccc",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    margin: "0 8px",
    alignSelf: "flex-end",
  },
  messageBubble: {
    padding: "8px 16px",
    borderRadius: "8px",
    maxWidth: "70%",
  },
  messageTime: {
    fontSize: "0.7rem",
    color: "#666",
    display: "block",
    textAlign: "right",
  },
  messageForm: {
    padding: "16px",
    borderTop: "1px solid #ddd",
    display: "flex",
  },
  messageInput: {
    flexGrow: 1,
    padding: "8px",
    border: "1px solid #ddd",
    borderRadius: "4px",
  },
  sendButton: {
    padding: "8px",
    marginLeft: "8px",
    backgroundColor: "#1976d2",
    color: "white",
    border: "none",
    borderRadius: "4px",
    cursor: "pointer",
  },
  emptyChat: {
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    height: "100%",
    color: "#666",
  },
};

export default Chat;
