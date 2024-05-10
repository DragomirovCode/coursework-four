package com.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@Table(name = "News")
public class News {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Заголовок новости не должен быть пустым")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Описание новости не должно быть пустым")
    @Column(name = "description")
    private String description;
    public News(){
        super();
    }
    public News(String title, String description) {
        super();
        this.title = title;
        this.description = description;
    }
}
