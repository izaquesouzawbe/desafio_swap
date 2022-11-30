package com.desafioswap.webhook.service;

import com.desafioswap.webhook.domain.GitHub.ContributorsDto;
import com.desafioswap.webhook.domain.GitHub.LabelDto;
import com.desafioswap.webhook.domain.GitHub.UserGitDTO;
import com.desafioswap.webhook.domain.GitHub.IssueDto;
import com.desafioswap.webhook.domain.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubService {

    @Autowired
    SimpleRequest simpleRequest;

    private String BASE_URL = "https://api.github.com/";

    public UserGitDTO doUser(UserDTO dto) throws URISyntaxException, IOException, InterruptedException {

        String newURL = BASE_URL + dto.getUserName() + "/" + dto.getRepositoryName();

        UserGitDTO gitDTO = new UserGitDTO();
        gitDTO.setUser(dto.getUserName());
        gitDTO.setRepository(dto.getRepositoryName());
        gitDTO.setIssueDtos(doListIssues(newURL + "/issues"));
        //gitDTO.setContributorsDtos(doListContributors(newURL + "/collaborators"));

        return gitDTO;

    }

    private JsonNode toStringInJson(String value) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(value);

    }

    private List<IssueDto> doListIssues(String url) throws IOException, URISyntaxException, InterruptedException {

        JsonNode jsonNode = toStringInJson(simpleRequest.doResponseURL(url));

        IssueDto issueDto = new IssueDto();
        issueDto.setAuthor(jsonNode.at("user").asText("login"));
        issueDto.setTitle(jsonNode.asText("title"));
        issueDto.setLabels(doListLabels(jsonNode.at("labels")));

        return new ArrayList<>();

    }

    private List<ContributorsDto> doListContributors(String url) throws IOException, URISyntaxException, InterruptedException {

        JsonNode jsonNode = toStringInJson(simpleRequest.doResponseURL(url));
        return new ArrayList<>();
    }

    private List<LabelDto> doListLabels(JsonNode json){

       // json.get.stream().forEach((a)-> System.out.println(a)).collect(Collectors.toList());
        List<LabelDto> labelDtos = new ArrayList<>();
        return labelDtos;

    }


}
