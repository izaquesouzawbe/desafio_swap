package com.desafioswap.webhook;

import com.desafioswap.webhook.domain.entity.Configuration;
import com.desafioswap.webhook.domain.entity.UserGit;
import com.desafioswap.webhook.repository.ConfigurationRepository;
import com.desafioswap.webhook.repository.UserGitRepository;
import com.desafioswap.webhook.scheduled.SendWebhookJob;
import com.desafioswap.webhook.service.SimpleRequestFacade;
import com.desafioswap.webhook.service.UserGitService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class SendWebhookJobTest {

    @Mock
    SendWebhookJob sendWebhookJob;

    @Mock
    UserGitRepository userGitRepository;

    @Mock
    UserGitService userGitService;

    @Mock
    SimpleRequestFacade simpleRequestFacade;

    @Mock
    ConfigurationRepository configurationRepository;

    @Test
    void executeTest() {

        Optional<Configuration> configuration = doConfiguration();
        List<UserGit> userGits = doUserGitList();
        String responseBody = "";

        Mockito.when(configurationRepository.findById(Mockito.anyLong())).thenReturn(configuration);
        Mockito.when(userGitRepository.findAllBySentFalse()).thenReturn(userGits);
        Mockito.when(simpleRequestFacade.doRequestPost(Mockito.anyString(), Mockito.anyString())).thenReturn(responseBody);
        Mockito.when(userGitRepository.save(Mockito.any())).thenReturn(responseBody);

        sendWebhookJob.execute();

        Mockito.verify(sendWebhookJob).sendWebHook(Mockito.anyList(), Mockito.any());

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

    public Optional<Configuration> doConfiguration() {

        Configuration configuration = new Configuration();
        configuration.setId(1L);
        configuration.setUrlGitHub("https://webhook.site/#!/b30176a5-3c2b-4c1f-9368-76e1ea11675b/0c7ba3c5-9cf5-4e88-991f-5a45b4e18827/1");
        configuration.setAuth("1234");
        configuration.setUrlGitHub("https://api.github.com/repos/");
        return Optional.of(configuration);

    }


}
