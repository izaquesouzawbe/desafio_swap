package com.desafioswap.webhook.domain.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class ConfigurationDTO {

    private String auth;
    private String urlGitHub;
    private String urlWebhook;

}
