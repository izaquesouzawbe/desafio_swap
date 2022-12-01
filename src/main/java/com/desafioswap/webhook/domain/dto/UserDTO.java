package com.desafioswap.webhook.domain.dto;

import com.desafioswap.webhook.domain.entity.Contributors;
import com.desafioswap.webhook.domain.entity.Issue;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private String user;
    private String sent;
    private String repository;
    private List<IssueDTO> issue;
    private List<ContributorsDTO> contributors;
}
