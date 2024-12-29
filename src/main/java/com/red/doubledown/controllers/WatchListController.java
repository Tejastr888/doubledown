package com.red.doubledown.controllers;


import com.red.doubledown.modal.Coin;
import com.red.doubledown.modal.User;
import com.red.doubledown.modal.WatchList;
import com.red.doubledown.service.CoinService;
import com.red.doubledown.service.UserService;
import com.red.doubledown.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
public class WatchListController {
    @Autowired
    private  WatchListService watchListService;
    @Autowired
    private UserService userService;
    @Autowired
    private CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<WatchList> getUserWatchList(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        WatchList watchList=watchListService.findUserWatchList(user.getId());
        return ResponseEntity.ok(watchList);
    }

    @PostMapping("/create")
    public ResponseEntity<WatchList> createWatchList(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        WatchList createdwatchList=watchListService.createWatchList(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdwatchList);
    }

    @GetMapping("/{watchlistId}")
    public ResponseEntity<WatchList> getWatchlistById(@PathVariable Long watchListId) throws Exception {
        WatchList watchList=watchListService.findById(watchListId);
        return ResponseEntity.ok(watchList);
    }

    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchList(@RequestHeader("Authorization") String jwt,@PathVariable String coinId) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Coin coin=coinService.findById(coinId);
        Coin addedCoin = watchListService.addItemToWatchList(coin,user);
        return ResponseEntity.ok(addedCoin);
    }
}
