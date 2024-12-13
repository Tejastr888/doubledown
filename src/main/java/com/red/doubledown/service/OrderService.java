package com.red.doubledown.service;

import com.red.doubledown.domain.OrderType;
import com.red.doubledown.modal.Coin;
import com.red.doubledown.modal.Order;
import com.red.doubledown.modal.OrderItem;
import com.red.doubledown.modal.User;

import java.util.List;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);
    Order getOrderById(Long orderId) throws Exception;
    List<Order> getAllOrderOfUsers(Long userId,OrderType orderType,String assetSymbol);
    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;

}

