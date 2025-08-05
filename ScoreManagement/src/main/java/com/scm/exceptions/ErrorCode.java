package com.scm.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    // ---DEFAULT
    UNCATEGORIZED_EXCEPTION(9999, "Xay ra lỗi", HttpStatus.INTERNAL_SERVER_ERROR),

    // --- INPUT VALIDATION ERRORS ---
    ONLY_1_COLLUM(1001, "Chỉ được nhập 1 cột điểm", HttpStatus.BAD_REQUEST),
    OVER_5_TEST(1002, "Nhập quá 5 cột điểm", HttpStatus.BAD_REQUEST),
    INVALID_DATA(1003, "Lỗi truyền dữ liệu", HttpStatus.BAD_REQUEST),
    MISSING_REQUIRED_FIELD(1004, "Thiếu trường bắt buộc", HttpStatus.BAD_REQUEST),
    INVALID_ID_FORMAT(1005, "ID không đúng định dạng", HttpStatus.BAD_REQUEST),
    EXIST_CLASS(1006, "Bạn đã đăng ký lớp này rồi", HttpStatus.BAD_REQUEST),
    CLASS_FULL(1007, "Lớp học đã đủ số lượng", HttpStatus.BAD_REQUEST),
    SCORE_ALREADY_SUBMITTED(1008, "Bạn đã nhập điểm trước đó", HttpStatus.CONFLICT),
    SEMESTER_CLOSED(1009, "Học kỳ đã kết thúc, không thể chỉnh sửa", HttpStatus.BAD_REQUEST),
    NOT_ENROLLED(1010, "Bạn chưa đăng ký môn học này", HttpStatus.BAD_REQUEST),
    DUPLICATE_DATA(1011, "Dữ liệu đã tồn tại", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1012, "Email existed",  HttpStatus.BAD_REQUEST),
    USER_EXISTED(1013, "User existed", HttpStatus.BAD_REQUEST),
    SCORE_TYPE_EXISTED(1014, "Score type existed", HttpStatus.BAD_REQUEST),
    EMAIL_NO_FORRMAT(1015, "Email khong dung dinh dang",  HttpStatus.BAD_REQUEST),
    CLASS_EXISTED(1016, "Class existed",  HttpStatus.BAD_REQUEST),
    SCORE_TYPE_INCORRECT(1017, "Score type incorrect",  HttpStatus.BAD_REQUEST),
    SCORE_IS_EMPTY(1018, "Score is empty",  HttpStatus.BAD_REQUEST),
    FILE_IS_EMPTY(1019, "File is empty",  HttpStatus.BAD_REQUEST),
    LIST_STUDENT_NOT_SUITABLE(1120, "List student not suitable",  HttpStatus.BAD_REQUEST),
    FORUM_NOT_FOUND(1121, "Forum not found",  HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(1122, "User not found",  HttpStatus.NOT_FOUND),
    UPLOAD_FILE_ERROR(1123, "Upload file error",  HttpStatus.BAD_REQUEST),
    DOWNLOAD_FILE_ERROR(1124, "Download file error",  HttpStatus.BAD_REQUEST),

    // --- AUTHENTICATION & AUTHORIZATION ERRORS ---
    UNAUTHORIZED(1101, "Bạn không có quyền", HttpStatus.UNAUTHORIZED),
    AUTHENTICATION_FAILED(1102, "Đăng nhập thất bại", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(1103, "Truy cập bị từ chối", HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED(1104, "Token đã hết hạn", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(1105, "Token không hợp lệ", HttpStatus.UNAUTHORIZED),




    // --- RESOURCE ERRORS ---
    NOT_FOUND(1201, "Không tìm thấy dữ liệu", HttpStatus.NOT_FOUND),

    // --- SERVER & SYSTEM ERRORS ---
    INTERNAL_ERROR(1301, "Lỗi hệ thống", HttpStatus.INTERNAL_SERVER_ERROR),
    DATABASE_ERROR(1302, "Lỗi truy vấn cơ sở dữ liệu", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_UNAVAILABLE(1303, "Dịch vụ tạm thời không khả dụng", HttpStatus.SERVICE_UNAVAILABLE),
    TIMEOUT(1304, "Yêu cầu quá thời gian xử lý", HttpStatus.REQUEST_TIMEOUT),
    READ_FILE_ERROR(1305, "Read file fails",  HttpStatus.INTERNAL_SERVER_ERROR),
    BLOCK_SCORE_ERROR(1306, "Error block score", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
