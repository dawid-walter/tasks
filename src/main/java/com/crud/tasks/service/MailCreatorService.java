package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;
    private AdminConfig adminConfig;

    @Autowired
    public MailCreatorService(TemplateEngine templateEngine, AdminConfig adminConfig) {
        this.templateEngine = templateEngine;
        this.adminConfig = adminConfig;
    }

    public String buildTrelloCardMail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("task_url", "https://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        return templateEngine.process("mail/creted-trello-card-mail", context);
    }
}
