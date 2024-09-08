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

    // O enum que é utilizado no backend
    private User_Role role = User_Role.PATIENT;

    // O valor smallint que será salvo no banco de dados
    private int roleValue;

    // Getter e setter para roleValue
    public int getRoleValue() {
        return this.role.getValue(); // Converte o enum para smallint
    }

    public void setRoleValue(int value) {
        this.role = User_Role.fromValue(value); // Converte o smallint de volta para o enum
    }
}




