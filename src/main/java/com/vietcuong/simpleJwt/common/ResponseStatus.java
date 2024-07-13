package com.vietcuong.simpleJwt.common;

public class ResponseStatus {

    public static enum GlobalError {
        USER_REGISTRATION_SUCCESS("00", "USER_REGISTRATION_SUCCESS"),
        USER_REGISTRATION_ERROR("01", "USER_REGISTRATION_ERROR");


        private final String code;
        private final String description;

        GlobalError(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public String getCode() {
            return this.code;
        }

        public String getDescription() {
            return this.description;
        }
    }
}
