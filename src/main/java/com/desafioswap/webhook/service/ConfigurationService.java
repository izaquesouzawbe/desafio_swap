package com.desafioswap.webhook.service;

import com.desafioswap.webhook.domain.dto.ConfigurationDTO;
import com.desafioswap.webhook.domain.entity.Configuration;
import com.desafioswap.webhook.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ConfigurationService {

    @Autowired
    ConfigurationRepository configurationRepository;

    @Transactional
    public Configuration save(ConfigurationDTO configurationDTO){

        Configuration configuration = new Configuration();
        configuration.setId(1L);
        configuration.setAuth(configurationDTO.getAuth());
        configuration.setUrlWebhook(configurationDTO.getUrlWebhook());
        configuration.setUrlGitHub(configurationDTO.getUrlGitHub());

        return configurationRepository.save(configuration);
    }

    public Optional<Configuration> findConfiguration(){

        return configurationRepository.findById(1L);

    }

}
