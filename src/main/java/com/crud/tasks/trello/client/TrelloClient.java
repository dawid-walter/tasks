package com.crud.tasks.trello.client;

import com.crud.tasks.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
public class TrelloClient {
    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloAppToken;

    @Value("${trello.app.username}")
    private String trelloAppUsername;

    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<TrelloBoardDto> getTrelloBoards() {
        URI url = getTrelloBoardsUrl();

        Optional<TrelloBoardDto[]> boardsResponse = Optional.ofNullable(restTemplate.getForObject(url, TrelloBoardDto[].class));
        return Arrays.asList(boardsResponse.orElse(new TrelloBoardDto[0]));
    }

    private URI getTrelloBoardsUrl() {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloAppUsername + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloAppToken)
                .queryParam("fields", "name,id")
                .queryParam("lists", "all").build().encode().toUri();
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto, double amount) {
        URI url = createTrelloCardUrl(trelloCardDto, amount);
        return restTemplate.postForObject(url, null, CreatedTrelloCard.class);
    }

    private URI createTrelloCardUrl(TrelloCardDto trelloCardDto, double amount) {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/cards/")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloAppToken)
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", "Amount in GBP = " + (amount * Double.parseDouble(getRate().getMid())))
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getIdList()).build().encode().toUri();
    }

    public Rate getRate() {
        CurrencyNBP currencyBoard = restTemplate.getForObject("http://api.nbp.pl/api/exchangerates/rates/a/gbp/?format=json", CurrencyNBP.class);
        return currencyBoard.getRates().get(0);
    }
}