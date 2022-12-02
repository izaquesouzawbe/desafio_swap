package com.desafioswap.webhook.scheduled;

import com.desafioswap.webhook.domain.dto.UserGitDTO;
import com.desafioswap.webhook.domain.entity.Configuration;
import com.desafioswap.webhook.domain.entity.UserGit;
import com.desafioswap.webhook.service.ConfigurationService;
import com.desafioswap.webhook.service.SimpleRequestFacade;
import com.desafioswap.webhook.service.UserGitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SendWebhookJob implements ScheduledJob {

    Logger logger = LoggerFactory.getLogger(SendWebhookJob.class);

    @Autowired
    SimpleRequestFacade simpleRequestFacade;

    @Autowired
    UserGitService userGitService;

    @Autowired
    ConfigurationService configurationService;

    @Autowired
    ObjectMapper objectMapper;

    private final ModelMapper mapper;

    public SendWebhookJob(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    @Scheduled(fixedRate = 20000)
    public void execute() {

        Optional<Configuration> configuration = configurationService.findConfiguration();
        if (configuration.isEmpty()) {
            logger.warn("Não existe configuração cadastrada.");
            return;
        }
        ;

        logger.info("Iniciando Job");
        List<UserGit> userGits = userGitService.findAllPending();
        sendWebHook(userGits, configuration.get());
        logger.info("Finalizando Job");

    }

    public void sendWebHook(List<UserGit> userGits, Configuration configuration) {

        logger.info("Enviando Webhook");

        userGits.stream().forEach(userGit -> {

            try {
                UserGitDTO userGitDTO = mapper.map(userGit, UserGitDTO.class);
                simpleRequestFacade.doRequestPost(configuration.getUrlWebhook(), objectMapper.writeValueAsString(userGitDTO));
                userGitService.updateUserSent(userGit);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

    }

}
