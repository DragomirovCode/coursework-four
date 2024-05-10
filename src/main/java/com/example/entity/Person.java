package com.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя пользователя не должно быть пустым")
    @Size(min = 2, max = 50, message = "Имя пользователя должно содержать от 2 до 50 символов")
    @Column(name = "username", unique = true)
    private String username;

    @NotEmpty(message = "Email не должен быть пустым")
    @Email(message = "Некорректный формат email")
    @Column(name = "email", unique = true)
    private String email;

    @NotEmpty(message = "Пароль не должен быть пустым")
    @Size(min = 1, message = "Пароль должен содержать не менее 1 символов")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    public Person(){
        super();
        // TODO Auto-generated constructor stub
    }

    public Person(String username, String email, String password) {
        super();
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
