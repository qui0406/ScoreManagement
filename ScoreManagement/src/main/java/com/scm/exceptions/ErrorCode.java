package com.scm.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    ONLY_1_COLLUM(1001, "Chi duoc nhap 1 cot diem", HttpStatus.BAD_REQUEST),
    OVER_5_TEST(1002, "Nhap qua 5 cot diem", HttpStatus.BAD_REQUEST),
    INVALID_DATA(1003, "Loi truyen du lieu", HttpStatus.BAD_REQUEST),
    EXIST_CLASS(1004, "Ban da dang ky lop nay roi", HttpStatus.BAD_REQUEST),
    UNAUTHORIZE(1005, "Ban khong co quyen",  HttpStatus.UNAUTHORIZED),
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
