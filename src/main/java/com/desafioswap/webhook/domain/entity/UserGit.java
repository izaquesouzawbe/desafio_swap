package com.desafioswap.webhook.domain.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    @OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    @JoinColumn(name="issue_id")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Issue> issue;
    @OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    @JoinColumn(name="contributors_id")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Contributors> contributors;

}
