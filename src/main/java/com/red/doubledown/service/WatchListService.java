package com.red.doubledown.service;

import com.red.doubledown.modal.Coin;
import com.red.doubledown.modal.User;
import com.red.doubledown.modal.WatchList;

public interface WatchListService {
    WatchList findUserWatchList(Long userId) throws Exception;
    WatchList createWatchList(User user);
    WatchList findById(Long id) throws Exception;
    Coin addItemToWatchList(Coin coin,User user) throws Exception;
}
