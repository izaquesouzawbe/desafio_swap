package com.desafioswap.webhook.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Configuration {

    @Id
    private Long id;
    private String auth;
    private String urlGitHub;
    private String urlWebhook;

}
