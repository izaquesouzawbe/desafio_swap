package com.desafioswap.webhook.service;

import com.desafioswap.webhook.domain.entity.UserGit;
import com.desafioswap.webhook.repository.UserGitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGitService {

    @Autowired
    UserGitRepository userGitRepository;

    public List<UserGit> findAll(){
        return userGitRepository.findAll();
    }



}
