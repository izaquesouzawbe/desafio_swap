package com.desafioswap.webhook.scheduled;

import com.desafioswap.webhook.domain.entity.Configuration;
import com.desafioswap.webhook.domain.entity.UserGit;
import com.desafioswap.webhook.service.ConfigurationService;
import com.desafioswap.webhook.service.GitHubService;
import com.desafioswap.webhook.service.SimpleRequestFacade;
import com.desafioswap.webhook.service.UserGitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SendWebhookJob implements ScheduledJob{

    Logger logger = LoggerFactory.getLogger(SendWebhookJob.class);

    @Autowired
    SimpleRequestFacade simpleRequestFacade;

    @Autowired
    UserGitService userGitService;

    @Autowired
    ConfigurationService configurationService;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    @Scheduled(fixedRate = 20000)
    public void execute() {

        Optional<Configuration> configuration = configurationService.findConfiguration();
        if(configuration.isEmpty()){
            logger.warn("Não existe configuração cadastrada.");
            return;
        };

        logger.info("Iniciando Job");
        List<UserGit> userGits = userGitService.findAllPending();
        sendWebHook(userGits, configuration.get());
        logger.info("Finalizando Job");

    }

    public void sendWebHook(List<UserGit> userGits, Configuration configuration){

        logger.info("Enviando Webhook");
        userGits.stream().forEach(userGit -> {
            try {
                simpleRequestFacade.doRequestPost(configuration.getUrlWebhook(), objectMapper.writeValueAsString(userGit));
                userGitService.updateUserSent(userGit);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

    }

}
