package com.desafioswap.webhook.domain.GitHub;

import lombok.Data;

import java.util.List;

@Data
public class UserGitDTO {

    private String user;
    private String repository;
    private List<IssueDto> issueDtos;
    private List<ContributorsDto> contributorsDtos;

}
