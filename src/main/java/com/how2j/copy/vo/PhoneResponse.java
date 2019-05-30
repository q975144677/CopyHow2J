package com.how2j.copy.vo;

public class PhoneResponse {

String Message;
String RequestId;
String Code;

    @Override
    public String toString() {
        return "PhoneResponse{" +
                "Message='" + Message + '\'' +
                ", RequestId='" + RequestId + '\'' +
                ", Code='" + Code + '\'' +
                '}';
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
