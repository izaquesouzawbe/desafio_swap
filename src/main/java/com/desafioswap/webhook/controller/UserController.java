package com.desafioswap.webhook.controller;

import com.desafioswap.webhook.domain.entity.UserGit;
import com.desafioswap.webhook.service.UserGitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    UserGitService userGitService;

    @GetMapping
    public ResponseEntity findAll() {
        List<UserGit> userGits = userGitService.findAll();
        return ResponseEntity.ok().body(userGits);
    }
}
