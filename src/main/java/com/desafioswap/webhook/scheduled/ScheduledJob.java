package com.desafioswap.webhook.scheduled;

import com.desafioswap.webhook.domain.entity.Task;
import com.desafioswap.webhook.domain.entity.UserGit;
import com.desafioswap.webhook.repository.UserGitRepository;
import com.desafioswap.webhook.service.GitHubService;
import com.desafioswap.webhook.service.SimpleRequestFacade;
import com.desafioswap.webhook.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class ScheduledJob {

    @Autowired
    SimpleRequestFacade simpleRequestFacade;

    @Autowired
    GitHubService gitHubService;

    @Autowired
    TaskService taskService;

    @Autowired
    UserGitRepository userGitRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Scheduled(fixedRate = 5000)
    public void execute() throws JsonProcessingException {

        List<Task> taskList = taskService.findAll();

        List<UserGit> userGits = gitHubService.doUserDetails(taskList);

        userGits = saveAllUserGits(userGits);
        sendWebHook(userGits);

    }

    public void sendWebHook(List<UserGit> userGits){

        userGits.stream().forEach(userGit -> {
            try {
                simpleRequestFacade.doRequestPost("https://webhook.site/b30176a5-3c2b-4c1f-9368-76e1ea11675b", objectMapper.writeValueAsString(userGit));
                updateUserGit(userGit);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @Transactional
    public void updateUserGit(UserGit userGit){
        userGit.setSent("S");
        userGitRepository.save(userGit);
    }

    @Transactional
    public List<UserGit> saveAllUserGits(List<UserGit> userGits){
        return userGitRepository.saveAll(userGits);
    }

}
