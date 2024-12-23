package com.red.doubledown.modal;

import com.red.doubledown.domain.WithdrawalStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Withdrawal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private WithdrawalStatus status;

    private Long Amount;

    @ManyToOne
    private User user;

    private LocalDateTime date=LocalDateTime.now();

}
