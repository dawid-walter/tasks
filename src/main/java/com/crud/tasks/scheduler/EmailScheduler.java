package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class EmailScheduler {
    private SimpleEmailService simpleEmailService;
    private TaskRepository taskRepository;
    private AdminConfig adminConfig;
    private static final String SUBJECT = "Task: new trello card";


    @Autowired
    public void setSimpleEmailService(SimpleEmailService simpleEmailService) {
        this.simpleEmailService = simpleEmailService;
    }

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    public void setAdminConfig(AdminConfig adminConfig) {
        this.adminConfig = adminConfig;
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
        String taskWord = "tasks";
        if (taskRepository.count() == 1) {
            taskWord = "task";
        }
        simpleEmailService.send(new Mail(adminConfig.getAdminMail(), SUBJECT, "Currently in database you got " + taskRepository.count() + " " + taskWord));
    }
}