package com.desafioswap.webhook.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserGitDTO {

    private String userName;
    private String repository;
    private List<IssueDTO> issue;
    private List<ContributorsDTO> contributors;
}
