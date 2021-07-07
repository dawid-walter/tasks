package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;
    private AdminConfig adminConfig;
    private DbService dbService;
    private LocalDate date;

    private final List<String> functionality;

    @Autowired
    public MailCreatorService(TemplateEngine templateEngine, AdminConfig adminConfig, DbService dbService) {
        this.templateEngine = templateEngine;
        this.adminConfig = adminConfig;
        this.dbService = dbService;

        functionality = new ArrayList<>();
        functionality.add("Function 1");
        functionality.add("Function 2");
        functionality.add("Function 3");
    }

    public String buildTrelloCardMail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("task_url", "https://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("admin", adminConfig);
        context.setVariable("goodBye", "To juz jest koniec, nie ma juz nic...");
        context.setVariable("companyName", adminConfig.getCompanyName());
        context.setVariable("companyAddress", adminConfig.getCompanyAddress());
        context.setVariable("copyright", adminConfig.getCopyright());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("application_functionality", functionality);
        context.setVariable("tasks", dbService);
        context.setVariable("date", date);

        return templateEngine.process("mail/creted-trello-card-mail", context);
    }
}
