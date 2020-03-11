package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.Rate;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/trello")
public class TrelloController {

    private TrelloClient trelloClient;

    @Autowired
    public void setTrelloClient(TrelloClient trelloClient) {
        this.trelloClient = trelloClient;
    }

    @GetMapping(value = "getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }

    @GetMapping(value = "getGbpCurrency")
    public void getGbpCurrency() {
        Rate rate = trelloClient.getRate();
        System.out.println(rate.getMid());
    }

    @GetMapping(value = "getTrelloBoardsContainsIdAndName")
    public void getTrelloBoardsContainsIdAndName() {
        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();
        trelloBoards.stream().filter(e -> e.getName() != null && e.getId() != null && e.getName().contains("Kodilla")).forEach(e -> System.out.println(e.getId() + " " + e.getName()));
    }

    @PostMapping(value = "createTrelloCard")
    public CreatedTrelloCard createdTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloClient.createNewCard(trelloCardDto);
    }
}
