package com.desafioswap.webhook.scheduled;

import com.desafioswap.webhook.domain.dto.UserFilterDTO;
import com.desafioswap.webhook.domain.entity.UserGit;
import com.desafioswap.webhook.repository.UserGitRepository;
import com.desafioswap.webhook.service.GitHubService;
import com.desafioswap.webhook.service.SimpleRequestFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class ScheduledJob {

    @Autowired
    SimpleRequestFacade simpleRequestFacade;

    @Autowired
    GitHubService gitHubService;

    @Autowired
    UserGitRepository userGitRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Scheduled(fixedRate = 5000)
    public void execute() throws JsonProcessingException {
        UserFilterDTO userDTO = new UserFilterDTO();
        userDTO.setUserName("izaquesouzawbe");
        userDTO.setRepositoryName("desafio_swap");

        UserGit userGit = gitHubService.doUserDetails(userDTO);

        saveUserGit(userGit);
        simpleRequestFacade.doRequestPostURL("https://webhook.site/b30176a5-3c2b-4c1f-9368-76e1ea11675b", objectMapper.writeValueAsString(userGit));

    }

    @Transactional
    public void saveUserGit(UserGit userGit){
        userGitRepository.save(userGit);
    }

}
