package com.example.Landing.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;


import javax.persistence.*;

@Entity
@Table(name = "messages", schema = "public")
@ToString(of = {"id","text"})
@EqualsAndHashCode(of = {"id"})
public class Message {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String text;
}
