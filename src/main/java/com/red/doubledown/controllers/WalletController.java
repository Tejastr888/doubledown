package com.red.doubledown.controllers;

import com.red.doubledown.modal.Order;
import com.red.doubledown.modal.User;
import com.red.doubledown.modal.Wallet;
import com.red.doubledown.modal.WalletTransaction;
import com.red.doubledown.service.OrderService;
import com.red.doubledown.service.UserService;
import com.red.doubledown.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("api/wallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet wallet=walletService.getUserWallet(user);
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/wallet/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization") String jwt, @PathVariable Long walletId,
                                                         @RequestBody WalletTransaction req) throws Exception {

        User sendUser=userService.findUserProfileByJwt(jwt);
        Wallet recieverWallet = walletService.findWalletById(walletId);
        Wallet wallet=walletService.walletToWalletTransfer(sendUser,recieverWallet,req.getAmount());

        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization") String jwt, @PathVariable Long orderId) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Order order =orderService.getOrderById(orderId);
        Wallet wallet = walletService.payOrderPayment(order,user);
        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }
}