package com.example.loginauthapi.domain.user;

public enum User_Role {
    PATIENT(0),
    ADMIN(1),
    RECEPTIONIST(2),
    DOCTOR(3);

    private final int value;

    User_Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static User_Role fromValue(int value) {
        switch (value) {
            case 0:
                return PATIENT;
            case 1:
                return ADMIN;
            case 2:
                return RECEPTIONIST;
            case 3:
                return DOCTOR;
            default:
                throw new IllegalArgumentException("Valor de role inválido: " + value);
        }
    }
}
