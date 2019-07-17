package com.ame.amedigital.api.commons;

public class ResponseObject<T> {

    public static class ResponseError {

        private String code;
        private String description;
        private String userMessage;
        private String field;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUserMessage() {
            return userMessage;
        }

        public void setUserMessage(String userMessage) {
            this.userMessage = userMessage;
        }

    }

    private T data;
    private ResponseError error;

    public T getData() {
        return data;
    }

    public ResponseError getError() {
        return error;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setError(ResponseError error) {
        this.error = error;
    }

}
