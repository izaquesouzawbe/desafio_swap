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

    @Autowired
    ConfigurationService configurationService;
    private final ObjectMapper objectMapper;
    private Configuration configuration;


    public GitHubService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void doConfiguration() {
        this.configuration = configurationService.findById(1L);
    }

    @Override
    public List<UserGit> doUserDetails(List<Task> tasks) {

        doConfiguration();

        return tasks.stream().map(task -> {

            String newURL = configuration.getUrlGitHub() + task.getUserName() + "/" + task.getRepositoryName();

            UserGit userGit = new UserGit();
            userGit.setUserName(task.getUserName());
            userGit.setRepository(task.getRepositoryName());

            userGit.setIssue(doListIssues(newURL + "/issues"));
            userGit.setContributors(doListContributors(newURL + "/contributors"));
            return userGit;

        }).collect(Collectors.toList());

    }

    @Override
    public List<Issue> doListIssues(String url) {

        try {

            JsonNode jsonNode = objectMapper.readTree(simpleRequest.doRequestGetWithAuth(url, configuration.getAuth()));

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

        return new ArrayList<>();
    }

    @Override
    public List<Contributors> doListContributors(String url) {

        try {
            JsonNode jsonNode = objectMapper.readTree(simpleRequest.doRequestGetWithAuth(url, configuration.getAuth()));
            return StreamSupport.stream(jsonNode.spliterator(), false)
                    .map(c -> {

                        Contributors contributors = new Contributors();
                        contributors.setUserName(c.get("login").asText());
                        contributors.setQtdCommits(c.get("contributions").asLong());
                        return contributors;

                    })
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

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
