package com.desafioswap.webhook.service;

import com.desafioswap.webhook.domain.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GitHubService implements Git {

    Logger logger = LoggerFactory.getLogger(GitHubService.class);

    @Autowired
    SimpleRequestFacade simpleRequest;

    @Autowired
    ConfigurationService configurationService;
    private final ObjectMapper objectMapper;

    public GitHubService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<UserGit> doUserDetails(List<Task> tasks) {

        Optional<Configuration> configuration = configurationService.findConfiguration();

        if(configuration.isEmpty()){
            logger.warn("Não existe configuração cadastrada.");
            return new ArrayList<>();
        };

        return tasks.stream().map(task -> {

            String newURL = configuration.get().getUrlGitHub() + task.getUserName() + "/" + task.getRepositoryName();

            UserGit userGit = new UserGit();
            userGit.setUserName(task.getUserName());
            userGit.setRepository(task.getRepositoryName());

            userGit.setIssue(doListIssues(newURL + "/issues", configuration.get()));
            userGit.setContributors(doListContributors(newURL + "/contributors", configuration.get()));
            return userGit;

        }).collect(Collectors.toList());

    }

    @Override
    public List<Issue> doListIssues(String url, Configuration configuration) {

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
            logger.error(e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public List<Contributors> doListContributors(String url, Configuration configuration) {

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
            logger.error(e.getMessage());
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
