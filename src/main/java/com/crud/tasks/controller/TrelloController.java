package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trello")
public class TrelloController {

    private TrelloClient trelloClient;

    @Autowired
    public void setTrelloClient(TrelloClient trelloClient) {
        this.trelloClient = trelloClient;
    }

    @GetMapping(value = "getTrelloBoards")
    public void getTrelloBoards() {

        // GET request
        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        trelloBoards.forEach(trelloBoardDto -> {

            System.out.println(trelloBoardDto.getName() + " - " + trelloBoardDto.getId());

            System.out.println("This board contains lists: ");

            trelloBoardDto.getLists().forEach(trelloList ->
                    System.out.println(trelloList.getName() + " - " + trelloList.getId() + " - " + trelloList.isClosed()));

        });
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
