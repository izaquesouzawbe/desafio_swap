package com.desafioswap.webhook.domain.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class ContributorsDTO {

    private String name;
    private String user;
    private Long qtdCommits;

}
