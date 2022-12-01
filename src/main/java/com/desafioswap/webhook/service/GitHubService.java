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

import java.util.ArrayList;
import java.util.List;

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
    public UserGitDTO doUserDetails(UserDTO dto) throws JsonProcessingException {

        String newURL = BASE_URL + dto.getUserName() + "/" + dto.getRepositoryName();

        UserGitDTO gitDTO = new UserGitDTO();
        gitDTO.setUser(dto.getUserName());
        gitDTO.setRepository(dto.getRepositoryName());
        gitDTO.setIssueDtos(doListIssues(newURL + "/issues"));
        gitDTO.setContributorsDtos(doListContributors(newURL + "/collaborators"));

        return gitDTO;

    }

    @Override
    public List<IssueDto> doListIssues(String url) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(simpleRequest.doResponseURL(url));

        IssueDto issueDto = new IssueDto();
        issueDto.setAuthor(jsonNode.at("user").asText("login"));
        issueDto.setTitle(jsonNode.asText("title"));
        issueDto.setLabels(doListLabels(jsonNode.at("labels")));

        return new ArrayList<>();
    }

    @Override
    public List<ContributorsDto> doListContributors(String url) throws JsonProcessingException {

        JsonNode jsonNode = objectMapper.readTree(simpleRequest.doResponseURL(url));
        return new ArrayList<>();

    }

    @Override
    public List<LabelDto> doListLabels(JsonNode json){

        // json.get.stream().forEach((a)-> System.out.println(a)).collect(Collectors.toList());
        List<LabelDto> labelDtos = new ArrayList<>();
        return labelDtos;

    }
}
