package com.desafioswap.webhook;

import com.desafioswap.webhook.domain.entity.*;
import com.desafioswap.webhook.repository.ConfigurationRepository;
import com.desafioswap.webhook.repository.TaskRepository;
import com.desafioswap.webhook.repository.UserGitRepository;
import com.desafioswap.webhook.scheduled.RegisterUserGitJob;
import com.desafioswap.webhook.service.ConfigurationService;
import com.desafioswap.webhook.service.GitHubService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class RegisterUserGitJobTest {

    @Mock
    ConfigurationService configurationService;

    @Mock
    ConfigurationRepository configurationRepository;

    @Mock
    UserGitRepository userGitRepository;

    @Mock
    TaskRepository taskRepository;

    @Mock
    RegisterUserGitJob registerUserGitJob;

    @Mock
    GitHubService gitHubService;


    @Test
    void executeTest() {

        Optional<Configuration> configuration = doConfiguration();
        List<UserGit> userGits = doUserGitList();
        List<Task> tasks = doTaskList();
        List<Label> labels = new ArrayList<>();
        List<Issue> issueList = new ArrayList<>();
        List<Contributors> contributors = new ArrayList<>();

        Mockito.when(configurationRepository.findById(Mockito.anyLong())).thenReturn(configuration);
        Mockito.when(userGitRepository.saveAll(Mockito.anyList())).thenReturn(userGits);
        Mockito.when(taskRepository.findAll()).thenReturn(tasks);

        Mockito.when(gitHubService.doListIssues(Mockito.anyString(), Mockito.any())).thenReturn(issueList);
        Mockito.when(gitHubService.doListContributors(Mockito.anyString(), Mockito.any())).thenReturn(contributors);
        Mockito.when(gitHubService.doListLabels(Mockito.any())).thenReturn(labels);

        registerUserGitJob.execute();

        Mockito.verify(gitHubService).doUserDetails(tasks);

    }

    public List<UserGit> doUserGitList() {

        UserGit userGit = new UserGit();
        userGit.setUserName("userName");
        userGit.setIssue(new ArrayList<>());
        userGit.setContributors(new ArrayList<>());
        userGit.setSent(false);

        List<UserGit> userGits = new ArrayList<>();
        userGits.add(userGit);
        return userGits;

    }

    public List<Task> doTaskList() {

        Task task = new Task();
        task.setUserName("userName");
        task.setRepositoryName("repositoryName");

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        return tasks;

    }

    public Optional<Configuration> doConfiguration() {

        Configuration configuration = new Configuration();
        configuration.setId(1L);
        configuration.setUrlGitHub("https://webhook.site/#!/b30176a5-3c2b-4c1f-9368-76e1ea11675b/0c7ba3c5-9cf5-4e88-991f-5a45b4e18827/1");
        configuration.setAuth("1234");
        configuration.setUrlGitHub("https://api.github.com/repos/");
        return Optional.of(configuration);

    }

}
