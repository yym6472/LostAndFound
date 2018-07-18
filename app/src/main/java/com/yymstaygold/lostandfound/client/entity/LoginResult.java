package com.yymstaygold.lostandfound.client.entity;

public class LoginResult {
    private int code;
    private String message;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private int userId;
        private boolean firstLogin;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public boolean isFirstLogin() {
            return firstLogin;
        }

        public void setFirstLogin(boolean firstLogin) {
            this.firstLogin = firstLogin;
        }
    }
}
