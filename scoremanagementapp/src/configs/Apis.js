import axios from "axios";
import cookie from "react-cookies";

const BASE_URL = "http://localhost:8080/ScoreManagement";

export const endpoints = {
    //Auth
    'register': '/api/auth/register',
    'login': '/api/auth/login',
    'my-profile' :'/api/secure/user/my-profile',
    // Lớp học của giáo viên
    'my-classes': '/api/secure/teacher/my-classes',
    'classDetails': classId => `/api/secure/teacher/class-subject/${classId}/details`,
    'studentList': classId =>`/api/secure/teacher/class-subject/${classId}/students`,

    // Điểm
    'getScores': classSubjectId => `/api/secure/teacher/class-subject/${classSubjectId}/scores`,
    'addScore': '/api/secure/teacher/add-list-score-all-student', 
    'closeScore': classSubjectId => `/api/secure/teacher/score/close-score/${classSubjectId}`,
    'getExportScores': classSubjectId => `/api/secure/teacher/export-list-score/${classSubjectId}`,
    // Loại điểm
    'allScoreTypes': '/api/secure/teacher/class-subject/score-types',
    'getScoreTypes': classSubjectId => `/api/secure/teacher/class-subject/score-types/${classSubjectId}`,
    'addScoreType': (classSubjectId, scoreTypeId) =>`/api/secure/teacher/class-subject/score-type/${classSubjectId}/add?scoreTypeId=${scoreTypeId}`,
    

    'findExportListScoreBase': classSubjectId =>`/api/secure/teacher/find-export-list-score/${classSubjectId}`,

    // 'findExportListScore': (classSubjectId, keyword) =>
    //     `/api/secure/teacher/find-export-list-score/${classSubjectId}?mssv=${encodeURIComponent(keyword)}&fullName=${encodeURIComponent(keyword)}`,

    'mySubjects': '/api/secure/user/get-all-subjects',
    'getMyScore': (studentId, classSubjectId) => `/api/secure/user/get-my-score/${studentId}?classSubjectId=${classSubjectId}`,

    'my-profile': '/api/secure/user/my-profile',

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