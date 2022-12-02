package com.desafioswap.webhook.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class UserGit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private Boolean sent = false;
    private String repository;
    @OneToMany(cascade=CascadeType.PERSIST)
    @JoinColumn(name="issue_id")
    private List<Issue> issue;
    @OneToMany(cascade=CascadeType.PERSIST)
    @JoinColumn(name="contributors_id")
    private List<Contributors> contributors;

}
