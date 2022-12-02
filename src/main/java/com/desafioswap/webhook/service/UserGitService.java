package com.desafioswap.webhook.service;

import com.desafioswap.webhook.domain.entity.UserGit;
import com.desafioswap.webhook.repository.UserGitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserGitService {

    @Autowired
    UserGitRepository userGitRepository;

    public List<UserGit> findAll(){
        return userGitRepository.findAll();
    }

    public List<UserGit> findAllPending(){
        return userGitRepository.findAllBySentFalse();
    }

    @Transactional
    public List<UserGit> saveAll(List<UserGit> userGits){
        return userGitRepository.saveAll(userGits);
    }

    @Transactional
    public void updateUserSent(UserGit userGit){
        userGit.setSent(true);
        userGitRepository.save(userGit);
    }

}
