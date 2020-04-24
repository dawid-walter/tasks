package com.crud.tasks;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTest {
    @Autowired
    TrelloMapper trelloMapper;

    @Test
    public void trelloCardMapperTest() {

        //Given
        TrelloCard trelloCard = new TrelloCard("card", "domain", "1", "1");
        TrelloCardDto trelloCardDto = new TrelloCardDto("cardDto", "transport", "2", "2");

        //When
        TrelloCardDto trelloCardToDto = trelloMapper.mapToCardDto(trelloCard);
        TrelloCard trelloCardfromDto = trelloMapper.mapToCard(trelloCardDto);

        //Then
        Assert.assertEquals("card", trelloCardToDto.getName());
        Assert.assertEquals("domain", trelloCardToDto.getDescription());
        Assert.assertEquals("1", trelloCardToDto.getPos());
        Assert.assertEquals("1", trelloCardToDto.getIdList());

        Assert.assertEquals("cardDto", trelloCardfromDto.getName());
        Assert.assertEquals("transport", trelloCardfromDto.getDescription());
        Assert.assertEquals("2", trelloCardfromDto.getPos());
        Assert.assertEquals("2", trelloCardfromDto.getIdList());

    }

    @Test
    public void trelloBoardMapperTest() {
        //Given
        List<TrelloList> lists = new ArrayList<>();
        lists.add(new TrelloList("1", "domain", true));
        lists.add(new TrelloList("2", "domain", false));

        List<TrelloListDto> listsDto = new ArrayList<>();
        listsDto.add(new TrelloListDto("1", "transport", true));
        listsDto.add(new TrelloListDto("2", "transport", false));

        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(new TrelloBoard("1", "domain", lists));

        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(new TrelloBoardDto("1", "transport", listsDto));

        //When
        List<TrelloBoard> trelloBoardsFromDto = trelloMapper.mapToBoards(trelloBoardDtoList);
        List<TrelloBoardDto> trelloBoardsToDto = trelloMapper.mapToBoardsDto(trelloBoardList);

        //Then
        Assert.assertEquals(1, trelloBoardsFromDto.size());
        Assert.assertEquals(2, trelloBoardsFromDto.get(0).getLists().size());
        Assert.assertEquals("transport", trelloBoardsFromDto.get(0).getName());
        Assert.assertEquals("transport", trelloBoardsFromDto.get(0).getLists().get(0).getName());



        Assert.assertEquals(1, trelloBoardsToDto.size());
        Assert.assertEquals(2, trelloBoardsToDto.get(0).getLists().size());
        Assert.assertEquals("domain", trelloBoardsToDto.get(0).getName());
        Assert.assertEquals("domain", trelloBoardsToDto.get(0).getLists().get(0).getName());
    }
}
