package com.business_crab.ToDoList.API.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="title")
    private String title;
    @Column(name="description")
    private String description;

    public Task() {}
    public Task(final Long id ,
                final String title ,
                final String description) {
        this.id = id;
        this.title = title;
        this.description = description;
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
        return description;
    }

    public void setDesc(final String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Id: " + id + "\nTitle: " + title + "\nDescription: " + description;
    }
}