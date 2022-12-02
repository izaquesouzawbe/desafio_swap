package com.desafioswap.webhook.scheduled;

import com.desafioswap.webhook.domain.entity.Configuration;
import com.desafioswap.webhook.domain.entity.UserGit;
import com.desafioswap.webhook.service.ConfigurationService;
import com.desafioswap.webhook.service.GitHubService;
import com.desafioswap.webhook.service.TaskService;
import com.desafioswap.webhook.service.UserGitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RegisterUserGitJob implements ScheduledJob {

    Logger logger = LoggerFactory.getLogger(RegisterUserGitJob.class);

    @Autowired
    GitHubService gitHubService;

    @Autowired
    TaskService taskService;

    @Autowired
    UserGitService userGitService;

    @Autowired
    ConfigurationService configurationService;

    @Override
    @Scheduled(fixedRate = 5000)
    public void execute() {

        Optional<Configuration> configuration = configurationService.findConfiguration();

        if(configuration.isEmpty()){
            logger.warn("Não existe configuração cadastrada.");
            return;
        }

        logger.info("Iniciando Job");

        List<UserGit> userGits = gitHubService.doUserDetails(taskService.findAll());
        userGitService.saveAll(userGits);

        logger.info("Finalizando Job");

    }


}
