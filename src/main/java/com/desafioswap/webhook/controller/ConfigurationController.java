package com.desafioswap.webhook.controller;

import com.desafioswap.webhook.domain.dto.ConfigurationDTO;
import com.desafioswap.webhook.domain.entity.Configuration;
import com.desafioswap.webhook.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/configuration")
public class ConfigurationController {

    @Autowired
    ConfigurationService configurationService;

    @PostMapping
    public ResponseEntity save(@RequestBody ConfigurationDTO configurationDTO){

        Configuration configuration = configurationService.save(configurationDTO);
        return ResponseEntity.ok().body(configuration);

    }
}
