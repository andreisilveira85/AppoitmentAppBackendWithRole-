package com.example.loginauthapi.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String email;
    private String password;

    private User_Role role = User_Role.PATIENT;

    private int roleValue;

    public int getRoleValue() {
        return this.role.getValue();
    }

    public void setRoleValue(int value) {
        this.role = User_Role.fromValue(value);
    }
}




