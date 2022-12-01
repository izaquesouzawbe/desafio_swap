package com.desafioswap.webhook.service;

import com.desafioswap.webhook.domain.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface Git {

    List<UserGit> doUserDetails(List<Task> tasks) throws JsonProcessingException;
    List<Issue> doListIssues(String url);
    List<Contributors> doListContributors(String url) throws JsonProcessingException;
    List<Label> doListLabels(JsonNode json);

}
