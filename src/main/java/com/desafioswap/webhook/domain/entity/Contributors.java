package com.desafioswap.webhook.domain.entity;

import lombok.Data;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Contributors {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String userName;
    private Long qtdCommits;

}
