package com.crud.tasks.trello.client;

import com.crud.tasks.config.TrelloConfig;
import com.crud.tasks.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
public class TrelloClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);
    private RestTemplate restTemplate;
    private TrelloConfig trelloConfig;

    @Autowired
    public TrelloClient(RestTemplate restTemplate, TrelloConfig trelloConfig) {
        this.restTemplate = restTemplate;
        this.trelloConfig = trelloConfig;
    }

    public List<TrelloBoardDto> getTrelloBoards() {
        URI url = getTrelloBoardsUrl();
        try {
            Optional<TrelloBoardDto[]> boardsResponse = Optional.ofNullable(restTemplate.getForObject(url, TrelloBoardDto[].class));
            return Arrays.asList(boardsResponse.orElse(new TrelloBoardDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    private URI getTrelloBoardsUrl() {
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/members/davedohc/boards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloAppToken())
                .queryParam("fields", "name,id")
                .queryParam("lists", "all").build().encode().toUri();
    }

    public CreatedTrelloCardDto createNewCard(TrelloCardDto trelloCardDto) {
        URI url = createTrelloCardUrl(trelloCardDto);
        return restTemplate.postForObject(url, null, CreatedTrelloCardDto.class);
    }

    private URI createTrelloCardUrl(TrelloCardDto trelloCardDto) {
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards/")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloAppToken())
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getIdList()).build().encode().toUri();
    }
}
