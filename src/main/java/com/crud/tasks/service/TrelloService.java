package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class TrelloService {
    private static final String SUBJECT = "Task: new trello card";
    private TrelloClient trelloClient;
    private SimpleEmailService simpleEmailService;
    private AdminConfig adminConfig;

    @Autowired
    public TrelloService(TrelloClient trelloClient, SimpleEmailService simpleEmailService, AdminConfig adminConfig) {
        this.trelloClient = trelloClient;
        this.simpleEmailService = simpleEmailService;
        this.adminConfig = adminConfig;
    }

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }

    public CreatedTrelloCardDto createdTrelloCard(final TrelloCardDto trelloCardDto) {
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);
        ofNullable(newCard).ifPresent(card -> simpleEmailService.send(new Mail(adminConfig.getAdminMail(), SUBJECT, "New card: " + trelloCardDto.getName() + "has been created on your trello account")));

        return newCard;
    }
}
