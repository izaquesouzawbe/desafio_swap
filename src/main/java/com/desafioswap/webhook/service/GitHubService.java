package com.desafioswap.webhook.service;

import com.desafioswap.webhook.domain.entity.Contributors;
import com.desafioswap.webhook.domain.entity.Label;
import com.desafioswap.webhook.domain.entity.UserGit;
import com.desafioswap.webhook.domain.entity.Issue;
import com.desafioswap.webhook.domain.dto.UserFilterDTO;
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
    public UserGit doUserDetails(UserFilterDTO dto) throws JsonProcessingException {

        String newURL = BASE_URL + dto.getUserName() + "/" + dto.getRepositoryName();

        UserGit userGit = new UserGit();
        userGit.setUser(dto.getUserName());
        userGit.setRepository(dto.getRepositoryName());

        userGit.setIssue(doListIssues(newURL + "/issues"));
        //gitDTO.setContributors(doListContributors(newURL + "/collaborators"));

        return userGit;

    }

    @Override
    public List<Issue> doListIssues(String url) throws JsonProcessingException {
        System.out.println(url);

        JsonNode jsonNode = objectMapper.readTree(simpleRequest.doRequestURL(url));

        return StreamSupport.stream(jsonNode.spliterator(), false)
                .map((i)-> {
                    System.out.println(i);
                    Issue issue = new Issue();
                    issue.setAuthor(i.get("user").get("login").asText());
                    issue.setTitle(i.get("title").asText());
                    //issue.setLabels(doListLabels());
                    return issue;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Contributors> doListContributors(String url) throws JsonProcessingException {

        JsonNode jsonNode = objectMapper.readTree(simpleRequest.doRequestURL(url));
        return new ArrayList<>();

    }

    @Override
    public List<Label> doListLabels(JsonNode json){

        // json.get.stream().forEach((a)-> System.out.println(a)).collect(Collectors.toList());
        List<Label> labelDtos = new ArrayList<>();
        return labelDtos;

    }
}
