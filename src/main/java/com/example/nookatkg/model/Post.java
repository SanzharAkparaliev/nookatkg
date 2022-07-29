package com.example.nookatkg.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    @Builder.Default
    private boolean banner = false;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();


}
