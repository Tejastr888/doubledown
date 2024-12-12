package com.red.doubledown.service;

import com.red.doubledown.domain.OrderType;
import com.red.doubledown.modal.Order;
import com.red.doubledown.modal.User;
import com.red.doubledown.modal.Wallet;
import com.red.doubledown.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet=walletRepository.findByUserId(user.getId());
        if (wallet==null){
            wallet=new Wallet();
            wallet.setUser(user);
        }
        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long amount) {
        BigDecimal balance=wallet.getBalance();
        BigDecimal newBalance=balance.add(BigDecimal.valueOf(amount));
        wallet.setBalance(newBalance);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long id) throws Exception {
        Optional<Wallet> wallet=walletRepository.findById(id);
        if(wallet.isPresent()){
            return wallet.get();
        }
        throw new Exception("wallet not found");
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet reciverWallet, Long amount) throws Exception {
        Wallet senderWallet=getUserWallet(sender);
        if (senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount))<0){
            throw new Exception("insuffiecient Balance");
        }
        BigDecimal senderBalance=senderWallet.getBalance().subtract(BigDecimal.valueOf(amount));
        senderWallet.setBalance(senderBalance);
        walletRepository.save(senderWallet);
        BigDecimal recieverBalance=reciverWallet.getBalance().add(BigDecimal.valueOf(amount));
        reciverWallet.setBalance(recieverBalance);
        walletRepository.save(reciverWallet);
        return senderWallet;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {
        Wallet wallet=getUserWallet(user);
        if (order.getOrderType().equals(OrderType.BUY)){
            BigDecimal newBalance=wallet.getBalance().subtract(order.getPrice());
            if(newBalance.compareTo(order.getPrice())<0){
                throw new Exception("Insufficient funds for transaction");
            }
            wallet.setBalance(newBalance);
        }
        else {
            BigDecimal newBalance=wallet.getBalance().add(order.getPrice());
            wallet.setBalance(newBalance);

        }
        walletRepository.save(wallet);
        return wallet;
    }
}
