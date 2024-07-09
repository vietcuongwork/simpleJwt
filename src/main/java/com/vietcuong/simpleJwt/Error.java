package com.vietcuong.simpleJwt;

public class Error {
    public String SUCCESS_CODE = "00";
    public String SUCCESS_MESSAGE = "";

    public static enum ClientRegisterValidationError {
        INVALID_FULL_NAME("01", "Invalid full name"),
        INVALID_USERNAME("02", "Invalid username"),
        INVALID_EMAIL("03", "Invalid email"),
        INVALID_DATE_OF_BIRTH("04", "Invalid date of birth");


        private final String code;
        private final String message;

        ClientRegisterValidationError(String code, String message) {
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
