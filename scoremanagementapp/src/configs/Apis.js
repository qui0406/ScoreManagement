import axios from "axios";
import cookies from "react-cookies";

const BASE_URL = "http://localhost:8080/ScoreManagementApp/";

export const endpoints = {
    'register': '/api/auth/register',
    'login': '/api/auth/login',
};

export const authApis = () => {
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            'Authorization': `Bearer ${cookies.load('token')}`
        }

    });
}
export default axios.create({
    baseURL: BASE_URL,
})