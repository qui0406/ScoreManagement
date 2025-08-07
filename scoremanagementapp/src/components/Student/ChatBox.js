import { useEffect, useState, useRef, useContext } from "react";
import { useParams } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/MyContexts";
import { Form, Button, Card, Spinner, Alert } from "react-bootstrap";

const ChatBox = () => {
    const { classDetailId } = useParams();
    const user = useContext(MyUserContext);
    const [conversationId, setConversationId] = useState(null);
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState("");
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");
    const chatEnd = useRef(null);

    useEffect(() => {
        const loadOrCreateConversation = async () => {
            try {
                const res = await authApis().post(endpoints['createConversation'](classDetailId), {
                });
                setConversationId(classDetailId);
            } catch {
                setConversationId(classDetailId); 
            }
        };
        loadOrCreateConversation();
    }, [classDetailId]);

    useEffect(() => {
        const loadMessages = async () => {
            setLoading(true);
            try {
                let res = await authApis().get(`/get-message/${conversationId}`);
                setMessages(res.data || []);
                setMsg("");
            } catch {
                setMsg("Không thể tải tin nhắn!");
            } finally {
                setLoading(false);
            }
        };
        if (conversationId) loadMessages();
    }, [conversationId]);

    // Scroll xuống cuối khi có tin nhắn mới
    useEffect(() => {
        chatEnd.current?.scrollIntoView({ behavior: "smooth" });
    }, [messages]);

    // 3. Gửi tin nhắn mới
    const sendMessage = async (e) => {
        e.preventDefault();
        if (!input.trim()) return;
        try {
            await authApis().post(`/create-message/${conversationId}`, {
                content: input,
            });
            setInput("");
            // Reload lại tin nhắn (có thể tối ưu bằng push, ở đây reload đơn giản)
            let res = await authApis().get(`/get-message/${conversationId}`);
            setMessages(res.data || []);
        } catch {
            alert("Gửi tin nhắn thất bại!");
        }
    };

    return (
        <div className="container mt-5" style={{ maxWidth: 650 }}>
            <Card>
                <Card.Header>
                    <b>Diễn đàn lớp {classDetailId}</b>
                </Card.Header>
                <Card.Body style={{ minHeight: 340, maxHeight: 480, overflowY: "auto", background: "#f8f9fa" }}>
                    {loading ? <Spinner animation="border" /> : null}
                    {msg && <Alert variant="danger">{msg}</Alert>}
                    {messages.length === 0 && <div className="text-center text-muted">Chưa có tin nhắn nào.</div>}
                    {messages.map((m, idx) => (
                        <div key={idx} style={{
                            textAlign: m.sender?.id === user?.id ? "right" : "left",
                            margin: "8px 0"
                        }}>
                            <span
                                className="px-3 py-2"
                                style={{
                                    background: m.sender?.id === user?.id ? "#cce5ff" : "#e9ecef",
                                    borderRadius: 16,
                                    display: "inline-block",
                                    maxWidth: "80%",
                                    wordBreak: "break-word"
                                }}
                            >
                                <b>{m.sender?.name || "Bạn"}: </b>
                                {m.content}
                                <span style={{ display: "block", fontSize: 12, color: "#888" }}>
                                    {new Date(m.createdAt).toLocaleString()}
                                </span>
                            </span>
                        </div>
                    ))}
                    <div ref={chatEnd}></div>
                </Card.Body>
                <Card.Footer>
                    <Form onSubmit={sendMessage} className="d-flex">
                        <Form.Control
                            value={input}
                            onChange={e => setInput(e.target.value)}
                            placeholder="Nhập tin nhắn..."
                            className="me-2"
                        />
                        <Button type="submit" variant="primary" disabled={!input.trim()}>Gửi</Button>
                    </Form>
                </Card.Footer>
            </Card>
        </div>
    );
};
export default ChatBox;
