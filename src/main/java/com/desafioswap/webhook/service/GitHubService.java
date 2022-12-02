package com.desafioswap.webhook.service;

import com.desafioswap.webhook.domain.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GitHubService implements Git {

    @Autowired
    SimpleRequestFacade simpleRequest;

    private String BASE_URL = "https://api.github.com/repos/";
    private String ClientId = "6c5043be7862b7bffd13";
    private String Token = "eb16102efc551e8a4582ceddb2821dcaae925881";
    private String Code = "041c1d11cce301e32ede";
    private final ObjectMapper objectMapper;

    public GitHubService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<UserGit> doUserDetails(List<Task> tasks) throws JsonProcessingException {

        return tasks.stream().map(task -> {

            String newURL = BASE_URL + task.getUserName() + "/" + task.getRepositoryName();

            UserGit userGit = new UserGit();
            userGit.setUserName(task.getUserName());
            userGit.setRepository(task.getRepositoryName());

            userGit.setIssue(doListIssues(newURL + "/issues"));
            //gitDTO.setContributors(doListContributors(newURL + "/collaborators"));
            return userGit;

        }).collect(Collectors.toList());

    }

    @Override
    public List<Issue> doListIssues(String url) {

        try {

            JsonNode jsonNode = objectMapper.readTree(simpleRequest.doRequestGet(url));

            return StreamSupport.stream(jsonNode.spliterator(), false)
                    .map(i -> {

                        Issue issue = new Issue();
                        issue.setAuthor(i.get("user").get("login").asText());
                        issue.setTitle(i.get("title").asText());
                        issue.setLabels(doListLabels(i.get("labels")));
                        return issue;

                    })
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Contributors> doListContributors(String url) throws JsonProcessingException {

        JsonNode jsonNode = objectMapper.readTree(simpleRequest.doRequestGet(url));
        return new ArrayList<>();

    }

    @Override
    public List<Label> doListLabels(JsonNode json) {
        return StreamSupport.stream(json.spliterator(), false)
                .map(l -> {

                    Label label = new Label();
                    label.setName(l.get("name").asText());
                    return label;

                })
                .collect(Collectors.toList());

    }
}
