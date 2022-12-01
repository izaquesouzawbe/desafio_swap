package com.desafioswap.webhook.service;

import com.desafioswap.webhook.domain.dto.TaskDTO;
import com.desafioswap.webhook.domain.entity.Task;
import com.desafioswap.webhook.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    @Transactional
    public Task save(TaskDTO taskDTO){

        Task task = new Task();
        task.setUserName(taskDTO.getUserName());
        task.setRepositoryName(taskDTO.getRepositoryName());

        return taskRepository.save(task);
    }

}
