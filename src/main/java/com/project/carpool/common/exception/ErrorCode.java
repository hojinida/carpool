package com.project.carpool.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", "Invalid Input Value"),
    HANDLE_ACCESS_DENIED(403, "C003", "Access is Denied"),
    ENTITY_NOT_FOUND(400, "C004", "Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C005", "Server Error"),
    INVALID_TYPE_VALUE(400, "C006", "Invalid Type Value"),
    // User
    USER_NOT_FOUND(400, "U001", "User Not Found"),
    USER_ALREADY_EXIST(400, "U002", "User Already Exist"),
    USER_NOT_MATCH(400, "U003", "User Not Match"),
    USER_NOT_AUTHORIZED(400, "U004", "User Not Authorized"),
    USER_DUPLICATE_EMAIL(400, "U005", "User Duplicate Email"),
    //Password
    PASSWORD_NOT_MATCH(400, "P001", "Password Not Match"),
    //auth
    AUTH_NOT_FOUND(400, "A001", "Auth Not Found"),
    AUTH_ALREADY_EXIST(400, "A002", "Auth Already Exist"),
    AUTH_NOT_MATCH(400, "A003", "Auth Not Match"),
    AUTH_NOT_AUTHORIZED(400, "A004", "Auth Not Authorized"),
    //jwt
    JWT_ACCESS_TOKEN_EXPIRED(401,"T006","ACCESS TOKEN EXPIRED"),
    JWT_REFRESH_TOKEN_EXPIRED(401,"T007","REFRESH TOKEN EXPIRED"),
    JWT_UNAUTHORIZED(401,"T001","UNAUTHORIZED TOKEN"),
    JWT_INVALID_SIGNATURE(401,"T002","INVALID SIGNATURE"),
    JWT_UNSUPPORTED(401,"T003","UNSUPPORTED TOKEN"),
    JWT_CLAIMS_EMPTY(401,"T004","CLAIMS_EMPTY"),
    JWT_BLACKLIST(401,"T005","TOKEN IS BLACKLIST"),

    // Post
    POST_NOT_FOUND(400, "P001", "Post Not Found"),
    POST_ALREADY_EXIST(400, "P002", "Post Already Exist"),
    POST_NOT_MATCH(400, "P003", "Post Not Match"),
    POST_NOT_AUTHORIZED(400, "P004", "Post Not Authorized"),
    // Comment
    COMMENT_NOT_FOUND(400, "C001", "Comment Not Found"),
    COMMENT_ALREADY_EXIST(400, "C002", "Comment Already Exist"),
    COMMENT_NOT_MATCH(400, "C003", "Comment Not Match"),
    COMMENT_NOT_AUTHORIZED(400, "C004", "Comment Not Authorized"),
    // Chat
    CHAT_NOT_FOUND(400, "H001", "Chat Not Found"),
    CHAT_ALREADY_EXIST(400, "H002", "Chat Already Exist"),
    CHAT_NOT_MATCH(400, "H003", "Chat Not Match"),
    CHAT_NOT_AUTHORIZED(400, "H004", "Chat Not Authorized"),
    //mail
    MAIL_NOT_FOUND(400, "M001", "Mail Not Found"),
    MAIL_ALREADY_EXIST(400, "M002", "Mail Already Exist"),
    MAIL_NOT_MATCH(400, "M003", "Mail Not Match"),
    MAIL_NOT_AUTHORIZED(400, "M004", "Mail Not Authorized"),
    // ChatMessage
    CHAT_MESSAGE_NOT_FOUND(400, "M001", "ChatMessage Not Found"),
    CHAT_MESSAGE_ALREADY_EXIST(400, "M002", "ChatMessage Already Exist"),
    CHAT_MESSAGE_NOT_MATCH(400, "M003", "ChatMessage Not Match"),
    CHAT_MESSAGE_NOT_AUTHORIZED(400, "M004", "ChatMessage Not Authorized"),
    // ChatRoom
    CHAT_ROOM_NOT_FOUND(400, "R001", "ChatRoom Not Found"),
    CHAT_ROOM_ALREADY_EXIST(400, "R002", "ChatRoom Already Exist");
    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    private int status;
    private String code;
    private String message;
}
