package com.vietcuong.simpleJwt.entity;

import jakarta.persistence.Enumerated;

public enum Role {
    USER, ADMIN;

    private Role() {
    }
}
