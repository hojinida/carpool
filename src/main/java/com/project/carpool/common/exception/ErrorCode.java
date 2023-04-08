package com.project.carpool.common.exception;

import lombok.Builder;
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
    // ChatMessage
    CHAT_MESSAGE_NOT_FOUND(400, "M001", "ChatMessage Not Found"),
    CHAT_MESSAGE_ALREADY_EXIST(400, "M002", "ChatMessage Already Exist"),
    CHAT_MESSAGE_NOT_MATCH(400, "M003", "ChatMessage Not Match"),
    CHAT_MESSAGE_NOT_AUTHORIZED(400, "M004", "ChatMessage Not Authorized"),
    // ChatRoom
    CHAT_ROOM_NOT_FOUND(400, "R001", "ChatRoom Not Found"),
    CHAT_ROOM_ALREADY_EXIST(400, "R002", "ChatRoom Already Exist"),
    // Email
    EMAIL_TOKEN_NOT_FOUND(400, "E001", "Email Token Not Found");
    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    private int status;
    private String code;
    private String message;
}
