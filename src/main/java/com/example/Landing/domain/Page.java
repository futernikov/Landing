package com.example.Landing.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "pages", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String name;
    private Date createdAt = new Date(System.currentTimeMillis());
    @ManyToOne(targetEntity = User.class)
    private User user;
    private String contentType;
    private String title;
    private String description;

    public Page(Type type, String name, Date createdAt, String title, String description) {
        this.type = type;
        this.name = name;
        this.createdAt = createdAt;
        this.title = title;
        this.description = description;
    }
}
