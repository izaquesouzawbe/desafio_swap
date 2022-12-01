package com.desafioswap.webhook.controller;

import com.desafioswap.webhook.domain.dto.TaskDTO;
import com.desafioswap.webhook.domain.entity.Task;
import com.desafioswap.webhook.repository.TaskRepository;
import com.desafioswap.webhook.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping
    public ResponseEntity save(@RequestBody TaskDTO taskDTO){

        Task task = taskService.save(taskDTO);
        return ResponseEntity.ok().body(task);

    }

}
