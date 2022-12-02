package com.desafioswap.webhook.scheduled;

import com.desafioswap.webhook.domain.entity.UserGit;
import com.desafioswap.webhook.service.SimpleRequestFacade;
import com.desafioswap.webhook.service.UserGitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public class SendWebhookJob implements ScheduledJob{

    @Autowired
    SimpleRequestFacade simpleRequestFacade;

    @Autowired
    UserGitService userGitService;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    @Scheduled(fixedRate = 60000)
    public void execute() {

        List<UserGit> userGits = userGitService.findAllPending();
        sendWebHook(userGits);

    }

    public void sendWebHook(List<UserGit> userGits){

        userGits.stream().forEach(userGit -> {
            try {
                simpleRequestFacade.doRequestPost("https://webhook.site/b30176a5-3c2b-4c1f-9368-76e1ea11675b", objectMapper.writeValueAsString(userGit));
                userGitService.updateUserSent(userGit);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

    }

}
