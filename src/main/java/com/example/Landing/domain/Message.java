package com.example.Landing.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


import javax.persistence.*;

@Entity
@Data
@Table(name = "messages", schema = "public")
@ToString(of = {"id","text"})
@EqualsAndHashCode(of = {"id"})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String text;
}
