import axios from "axios";
import cookie from "react-cookies";

const BASE_URL = "http://localhost:8080/ScoreManagement";

export const endpoints = {
    'register': '/api/auth/register',
    'login': '/api/auth/login',
    'my-profile' :'/api/secure/user/my-profile',
    'my-classes': '/api/secure/teacher/my-classes',
    'classDetails': classId => `/api/secure/teacher/class-subject/${classId}/details`,
    'studentList': classSubjectId =>`/api/secure/teacher/class-subject/${classSubjectId}/students`,
    'addScore': '/api/secure/teacher/add-list-score-all-students', 
    'getScoreTypes': classSubjectId => `/api/secure/teacher/class-subject/${classSubjectId}/score-types`,
    'addScoreType': classSubjectId => `/api/secure/teacher/class-subject/${classSubjectId}/score-types`,
    'closeScore': classSubjectId => `/api/secure/teacher/score/close-score/${classSubjectId}`,
};

export const authApis = () => {
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            'Authorization': `Bearer ${cookie.load('token')}`
        }

    });
}
export default axios.create({
    baseURL: BASE_URL,
})