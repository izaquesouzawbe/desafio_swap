package com.desafioswap.webhook.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String author;
    @OneToMany(cascade=CascadeType.PERSIST)
    private List<Label> labels;
    @ManyToOne
    private UserGit userGit;
}
