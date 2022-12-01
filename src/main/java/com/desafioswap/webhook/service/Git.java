package com.desafioswap.webhook.service;

import com.desafioswap.webhook.domain.entity.Contributors;
import com.desafioswap.webhook.domain.entity.Issue;
import com.desafioswap.webhook.domain.entity.Label;
import com.desafioswap.webhook.domain.entity.UserGit;
import com.desafioswap.webhook.domain.dto.UserFilterDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface Git {

    UserGit doUserDetails(UserFilterDTO dto) throws JsonProcessingException;
    List<Issue> doListIssues(String url) throws JsonProcessingException;
    List<Contributors> doListContributors(String url) throws JsonProcessingException;
    List<Label> doListLabels(JsonNode json);

}
