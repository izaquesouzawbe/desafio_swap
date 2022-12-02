package com.desafioswap.webhook.repository;

import com.desafioswap.webhook.domain.entity.UserGit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGitRepository extends JpaRepository<UserGit, Long> {
    List<UserGit> findAllBySentFalse();
}
