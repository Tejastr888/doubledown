package com.red.doubledown.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.red.doubledown.modal.Coin;
import com.red.doubledown.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coins")
public class CoinController {
    @Autowired
    private CoinService coinService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    ResponseEntity<List<Coin>> getCoinList(@RequestParam("page") Integer page) throws Exception{
        List<Coin> coins = coinService.getCoinList(page);
        return new ResponseEntity<>(coins, HttpStatus.ACCEPTED);

    }

    @GetMapping("/{coinId}/chart")
    ResponseEntity<JsonNode> getMarketChart(@PathVariable String coinId,@RequestParam("days") Integer days) throws Exception{
        String coins = coinService.getMarketChart(coinId,days);
        JsonNode jsonNode = objectMapper.readTree(coins);
        return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);

    }

    @GetMapping("/search")
    ResponseEntity<JsonNode> searchCoin(@RequestParam("q") String keyword) throws Exception {
        String coin=coinService.getCoinDetails(keyword);
        JsonNode jsonNode=objectMapper.readTree(coin);
        return  ResponseEntity.ok(jsonNode);
    }

    @GetMapping("/top50")
    ResponseEntity<JsonNode> getTop50coins() throws Exception {
        String coin=coinService.getTop50CoinsByMarketCapRank();
        JsonNode jsonNode=objectMapper.readTree(coin);
        return  ResponseEntity.ok(jsonNode);
    }


    @GetMapping("/trading")
    ResponseEntity<JsonNode> getTradingCoin(@PathVariable String coinId) throws Exception {
        String coin=coinService.getTradingCoins();
        JsonNode jsonNode=objectMapper.readTree(coin);
        return  ResponseEntity.ok(jsonNode);
    }

    @GetMapping("/details/{coinId}")
    ResponseEntity<JsonNode> getCoinDetails(@PathVariable String coinId) throws Exception {
        String coin=coinService.getCoinDetails(coinId);
        JsonNode jsonNode=objectMapper.readTree(coin);
        return  ResponseEntity.ok(jsonNode);
    }
}
