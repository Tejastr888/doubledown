package com.red.doubledown.service;

import com.red.doubledown.modal.Order;
import com.red.doubledown.modal.User;
import com.red.doubledown.modal.Wallet;

public interface WalletService {
    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet,Long amount);
    Wallet findWalletById(Long id) throws Exception;
    Wallet walletToWalletTransfer(User sender,Wallet reciever,Long amount) throws Exception;
    Wallet payOrderPayment(Order order, User user) throws Exception;
}
