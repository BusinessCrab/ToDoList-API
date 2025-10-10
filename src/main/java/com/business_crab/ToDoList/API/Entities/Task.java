package com.business_crab.ToDoList.API.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String desription;

    public Task() {}
    public Task(final Long id ,
                final String title ,
                final String description) {
        this.id = id;
        this.title = title;
        this.desription = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDesc() {
        return desription;
    }

    public void setDesc(final String description) {
        this.desription = description;
    }

    @Override
    public String toString() {
        return "Id: " + id + "\nTitle: " + title + "\nDescription: " + desription;
    }
}