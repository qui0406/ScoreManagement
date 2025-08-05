import axios from "axios";
import cookie from "react-cookies";

const BASE_URL = "http://localhost:8080/ScoreManagement";

export const endpoints = {
    //Auth
    'register': '/api/auth/register',
    'login': '/api/auth/login',
    'my-profile': '/api/secure/user/my-profile',
    'update-profile': '/api/secure/user/update-profile',
    'my-score': classDetailId => `/api/secure/user/my-score/${classDetailId}`,

    // Lớp học của giáo viên
    'my-classes': '/api/secure/teacher/my-classrooms',
    'classDetails': classId => `/api/secure/teacher/class-subject/${classId}/details`,
    'studentList': classDetailId => `/api/secure/user/list-student-enrollment/${classDetailId}`,

    // Điểm
    'getScores': classSubjectId => `/api/secure/teacher/class-subject/${classSubjectId}/scores`,
    'addScore': '/api/secure/teacher/add-list-scores-all-student',
    'blockScore': classDetailId => `/api/secure/teacher/score/block-score/${classDetailId}`,
    'statusScore': classDetailId => `/api/secure/teacher/score/get-status/${classDetailId}`,

    'getExportScores': classDetailId => `/api/secure/teacher/export-list-score/${classDetailId}`,

    // Loại điểm
    'allScoreTypes': '/api/secure/teacher/class-subject/score-types',
    'getScoreTypes': classDetailId => `/api/secure/teacher/class-subject/score-types/${classDetailId}`,
    'addScoreType': (classDetailId, scoreTypeId) =>`/api/secure/teacher/class-subject/score-type/${classDetailId}/add?scoreTypeId=${scoreTypeId}`,
    'deleteScoreType': (classDetailId, scoreTypeId) => `/api/secure/teacher/class-subject/score-type/${classDetailId}/delete?scoreTypeId=${scoreTypeId}`,

    //Doc diem tu file
    'importScore': classDetailId => `/api/secure/teacher/upload-scores/${classDetailId}`,
    'exportScore': classDetailId => `/api/secure/teacher/export-scores/${classDetailId}`,

    //Tim kiem sinh vien
    'findExportListScoreBase': classDetailId => `/api/secure/teacher/find-export-list-score/${classDetailId}`,


    'mySubjects': '/api/secure/user/get-all-classes-me-register',
    'getMyScore': (studentId, classSubjectId) => `/api/secure/user/get-my-score/${studentId}?classSubjectId=${classSubjectId}`,

    //Dang ky mon hoc, lop hoc
    'registerClass': '/api/secure/user/register-class',
    'deleteClass': enrollId => `/api/secure/user/delete-class/${enrollId}`,
    'getClassesInSemester': semesterId => `/api/secure/user/list-subject/semester/${semesterId}`,

    // Sinh vien trong lop
    'getAllStudentsInClass': classDetailId => `/list-student-enrollment/${classDetailId}`,

    //Tao cuoc hoi thoai
    'createConversation': classDetailId => `/create-conversation/${classDetailId}`,
    'deleteConversation': conversationId => `/delete-conversation/${conversationId}`,
    'getAllConversations': classDetailId => `/list-conversation/${classDetailId}`,

    // Tao forum
    'createForum': '/create-forum',
    'deleteForum': forumId => `/delete-forum/${forumId}`,
    'replyForum': forumId => `/forum-reply/${forumId}`,
    'replyForumUpdate': forumDetailId => `/forum-reply/update/${forumDetailId}`,
    'replyForumDelete': forumDetailId => `/forum-reply/delete/${forumDetailId}`,
    'getAllForums': forumId => `/get-all-reply-forum/${forumId}`,
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