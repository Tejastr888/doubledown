package com.red.doubledown.modal;

import jakarta.persistence.*;
import lombok.Data;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class WatchList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    @ManyToMany
    private List<Coin> coins=new ArrayList<>();



}
