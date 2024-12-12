package com.red.doubledown.modal;

import com.red.doubledown.domain.WalletTransactionType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;


@Entity
@Data
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Wallet wallet;

    private WalletTransactionType walletTransactionType;
    private LocalDateTime date;
    private String transferId;
    private String purpose;
    private Long amount;

}
