package com.example.Landing.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "bgImg", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
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
    private Date createdAt = new Date(System.currentTimeMillis());
}
