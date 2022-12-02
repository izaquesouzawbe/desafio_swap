package com.desafioswap.webhook.service;

import com.desafioswap.webhook.domain.dto.ConfigurationDTO;
import com.desafioswap.webhook.domain.entity.Configuration;
import com.desafioswap.webhook.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ConfigurationService {

    @Autowired
    ConfigurationRepository configurationRepository;

    @Transactional
    public Configuration save(ConfigurationDTO configurationDTO){

        Configuration configuration = new Configuration();
        configuration.setAuth(configurationDTO.getAuth());
        configuration.setUrlWebhook(configurationDTO.getUrlWebhook());
        configuration.setUrlGitHub(configurationDTO.getUrlGitHub());

        return configurationRepository.save(configuration);
    }

    public Configuration findById(Long id){

        return configurationRepository.findById(id).get();

    }

}
