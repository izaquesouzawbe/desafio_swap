package com.desafioswap.webhook.controller;

import com.desafioswap.webhook.domain.GitHub.UserGitDTO;
import com.desafioswap.webhook.domain.UserDTO;
import com.desafioswap.webhook.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    GitHubService gitHubService;

    @GetMapping
    public ResponseEntity findAllIssue(@RequestBody UserDTO dto) throws URISyntaxException, IOException, InterruptedException {
        UserGitDTO gitDTO = gitHubService.doUser(dto);
        return ResponseEntity.ok().body(gitDTO);
    }
}
