package com.desafioswap.webhook.scheduled;

import com.desafioswap.webhook.domain.entity.UserGit;
import com.desafioswap.webhook.service.GitHubService;
import com.desafioswap.webhook.service.TaskService;
import com.desafioswap.webhook.service.UserGitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegisterUserGitJob implements ScheduledJob {

    @Autowired
    GitHubService gitHubService;

    @Autowired
    TaskService taskService;

    @Autowired
    UserGitService userGitService;

    @Override
    @Scheduled(fixedRate = 5000)
    public void execute() {

        List<UserGit> userGits = gitHubService.doUserDetails(taskService.findAll());
        userGitService.saveAll(userGits);

    }


}
