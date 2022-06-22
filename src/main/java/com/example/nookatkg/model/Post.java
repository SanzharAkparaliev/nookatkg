package com.example.nookatkg.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 500)
    private String title;
    private String imageUrl;

    @Column(length = 15000)
    private String content;
    private Date date;

    @ManyToOne
    private Category category;
}
