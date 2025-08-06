import axios from "axios";
import cookie from "react-cookies";
import { endpoints } from "./../configs/Apis";

const BASE_URL = "http://localhost:8080/ScoreManagement";

const getToken = () => cookie.load('token');

const createAuthAxios = () => axios.create({
  baseURL: BASE_URL,
  headers: {
    Authorization: `Bearer ${getToken()}`,
    'Content-Type': 'application/json',
  },
});

export const getMyConversations = async (classDetailId) => {
  try {
    const response = await createAuthAxios().get(endpoints.conversations.getAllConversations(classDetailId));
    return response.data;
  } catch (error) {
    console.error('Error fetching conversations:', error);
    throw error;
  }
};

export const createConversation = async (data) => {
  try {
    const response = await createAuthAxios().post(
      endpoints.conversations.createConversation(data.classDetailId),
      {
        type: data.type,
        participantIds: data.participantIds,
      }
    );
    return response.data;
  } catch (error) {
    console.error('Error creating conversation:', error);
    throw error;
  }
};

export const createMessage = async (data) => {
  try {
    const response = await createAuthAxios().post(
      endpoints.conversations.replyForum(data.conversationId),
      {
        conversationId: data.conversationId,
        message: data.message,
      }
    );
    return response.data;
  } catch (error) {
    console.error('Error creating message:', error);
    throw error;
  }
};

export const getMessages = async (conversationId) => {
  try {
    const response = await createAuthAxios().get(
      `${endpoints.conversations.getAllConversations()}?conversationId=${conversationId}`
    );
    return response.data;
  } catch (error) {
    console.error('Error fetching messages:', error);
    throw error;
  }
};