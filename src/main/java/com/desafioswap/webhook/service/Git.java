package com.desafioswap.webhook.service;

import com.desafioswap.webhook.domain.GitHub.ContributorsDto;
import com.desafioswap.webhook.domain.GitHub.IssueDto;
import com.desafioswap.webhook.domain.GitHub.LabelDto;
import com.desafioswap.webhook.domain.GitHub.UserGitDTO;
import com.desafioswap.webhook.domain.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface Git {

    UserGitDTO doUserDetails(UserDTO dto) throws JsonProcessingException;
    List<IssueDto> doListIssues(String url) throws JsonProcessingException;
    List<ContributorsDto> doListContributors(String url) throws JsonProcessingException;
    List<LabelDto> doListLabels(JsonNode json);

}
