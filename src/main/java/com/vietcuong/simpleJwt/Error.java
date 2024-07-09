package com.vietcuong.simpleJwt;

public class Error {
    public String SUCCESS_CODE = "00";
    public String SUCCESS_MESSAGE = "";

    public static enum GlobalError {
        CLIENT_REGISTRATION_ERROR("01", "CLIENT_REGISTRATION_ERROR");


        private final String code;
        private final String message;

        GlobalError(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.message;
        }
    }
}
