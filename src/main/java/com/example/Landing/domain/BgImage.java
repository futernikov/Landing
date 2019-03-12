package com.example.Landing.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "image")
@NoArgsConstructor
@Data
public class BgImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int size;
    private int width;
    private int height;
    private String contentType;
    private boolean isPaid;
    private LocalDateTime createdAt;
}
