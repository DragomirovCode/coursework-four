package com.example.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
