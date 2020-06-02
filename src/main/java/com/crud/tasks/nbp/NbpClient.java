package com.crud.tasks.nbp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NbpClient {
    private RestTemplate restTemplate;

    @Autowired
    public NbpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Rate getRate() {
        CurrencyNBP currencyBoard = restTemplate.getForObject("http://api.nbp.pl/api/exchangerates/rates/a/gbp/?format=json", CurrencyNBP.class);
        return currencyBoard.getRates().get(0);
    }
}
