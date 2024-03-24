package com.bookStore.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "это поле не должно быть пустым")
    @Column(name = "username")
    private String username;
    @NotEmpty(message = "это поле не должно быть пустым")
    private String email;
    @NotEmpty(message = "это поле не должно быть пустым")
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;

    public Person(){
        super();
        // TODO Auto-generated constructor stub
    }

    public Person(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
