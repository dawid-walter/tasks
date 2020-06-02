package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.facade.TrelloFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/trello")
public class TrelloController {

    private TrelloFacade trelloFacade;
    private TrelloClient trelloClient;

    @Autowired
    public TrelloController(TrelloFacade trelloFacade, TrelloClient trelloClient) {
        this.trelloFacade = trelloFacade;
        this.trelloClient = trelloClient;
    }

    @GetMapping(value = "getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {
        return trelloFacade.fetchTrelloBoards();
    }

    @PostMapping(value = "createTrelloCard")
    public CreatedTrelloCardDto createdTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloFacade.createCard(trelloCardDto);
    }

    @GetMapping(value = "getTrelloBoardsContainsIdAndName")
    public void getTrelloBoardsContainsIdAndName() {
        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();
        trelloBoards.stream().filter(e -> e.getName() != null && e.getId() != null && e.getName().contains("Kodilla")).forEach(e -> System.out.println(e.getId() + " " + e.getName()));
    }
}
