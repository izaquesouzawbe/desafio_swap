package com.desafioswap.webhook.domain.GitHub;

import lombok.Data;

@Data
public class ContributorsDto {

    private String name;
    private String user;
    private Long qtd_commits;

}
