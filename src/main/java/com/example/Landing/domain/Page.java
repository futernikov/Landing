package com.example.Landing.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne(targetEntity = User.class)
    private User user;
    private String contentType;
    private String title;
    private String description;
}
