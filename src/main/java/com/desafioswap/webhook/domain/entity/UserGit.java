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
    private String user;
    private String sent;
    private String repository;
    @OneToMany(cascade=CascadeType.PERSIST)
    private List<Issue> issue;
    @OneToMany(cascade=CascadeType.PERSIST)
    private List<Contributors> contributors;

    public UserGit(){
        this.sent = "S";
    }

}
