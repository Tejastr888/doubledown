package com.red.doubledown.controllers;


import com.red.doubledown.domain.OrderType;
import com.red.doubledown.modal.Coin;
import com.red.doubledown.modal.Order;
import com.red.doubledown.modal.User;
import com.red.doubledown.request.CreateOrderRequest;
import com.red.doubledown.service.CoinService;
import com.red.doubledown.service.OrderService;
import com.red.doubledown.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CoinService coinService;

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(@RequestHeader("Authorization") String jwt, @RequestBody CreateOrderRequest req) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin=coinService.findById(req.getCoinId());
        Order order=orderService.processOrder(coin,req.getQuantity(),req.getOrderType(),user);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderid}")
    public ResponseEntity<Order> getOrderById(@RequestHeader("Authorization") String jwt,@PathVariable Long orderId) throws Exception {
        User user =userService.findUserProfileByJwt(jwt);
        Order order = orderService.getOrderById(orderId);
        if (order.getUser().getId().equals(user.getId())) {
            return  ResponseEntity.ok(order);
        }else {
            throw new Exception("not accessible");
        }
    }

    @GetMapping();
    public ResponseEntity<List<Order>> getAllOrdersForUser(@RequestHeader("Authorization") String jwt, @RequestParam(required = false)OrderType orderType,
                                                           @RequestParam(required = false) String asset_symbol) throws Exception {
        Long userId=userService.findUserProfileByJwt(jwt).getId();
        List<Order> userOrders=orderService.getAllOrderOfUsers(userId,orderType,asset_symbol);

        return ResponseEntity.ok(userOrders);
    }





}
