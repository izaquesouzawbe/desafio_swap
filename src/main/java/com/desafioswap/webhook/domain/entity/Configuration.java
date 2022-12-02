package com.desafioswap.webhook.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String auth;
    private String urlGitHub;
    private String urlWebhook;

}
