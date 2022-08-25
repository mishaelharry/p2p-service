package com.p2p.app.service;

import com.p2p.app.model.User;
import com.p2p.app.model.Wallet;
import com.p2p.app.repository.UserRepository;
import com.p2p.app.repository.WalletRepository;
import com.p2p.app.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    public BaseResponse<Wallet> getWallet(User user){
        Wallet wallet = walletRepository.findByUser(user);
        if (wallet != null){
            return new BaseResponse<>(true, "Wallet retrieved successfully", wallet);
        }
        return new BaseResponse<>(false, "Wallet detail not found.", null);
    }

    public BaseResponse<Wallet> fund(User user, BigDecimal amount){
        /*BaseResponse validateAmount = validateAmount(amount);
        if (!validateAmount.isStatus()){
            return validateAmount;
        }*/

        if (!userRepository.existsById(user.getId())){
            return new BaseResponse<>(false, "User not found.", null);
        }

        Wallet wallet = walletRepository.findByUser(user);
        if (wallet != null){
            wallet.setBalance(wallet.getBalance().add(amount));
            walletRepository.save(wallet);
            return new BaseResponse<>(true, "Wallet retrieved successfully", wallet);
        } else {
            return new BaseResponse<>(false, "Wallet detail not found.", null);
        }
    }

    public BaseResponse transfer(User debitUser, User creditUser, BigDecimal amount){
        //valid amount
        BaseResponse validateAmount = validateAmount(amount);
        if (!validateAmount.isStatus()){
            return validateAmount;
        }

        //valid debit user
        if (!userRepository.existsById(debitUser.getId())){
            return new BaseResponse<>(false, "Debit user not found.", null);
        }

        //valid credit user
        if (!userRepository.existsById(creditUser.getId())){
            return new BaseResponse<>(false, "Credit user not found.", null);
        }

        //get debit wallet
        Wallet debitWallet = walletRepository.findByUser(debitUser);
        if (debitWallet == null){
            return new BaseResponse<>(false, "Debit wallet not found.", null);
        }

        //get credit wallet
        Wallet creditWallet = walletRepository.findByUser(creditUser);
        if (creditWallet == null){
            return new BaseResponse<>(false, "Credit wallet not found.", null);
        }

        //check sufficient fund
        if (debitWallet.getBalance().compareTo(amount) < 0){
            return new BaseResponse<>(false, "Insufficient fund.", null);
        }

        debitWallet.setBalance(debitWallet.getBalance().subtract(amount));
        walletRepository.save(debitWallet);

        creditWallet.setBalance(creditWallet.getBalance().add(amount));
        walletRepository.save(creditWallet);

        return new BaseResponse<>(true, "Transfer was successful", null);
    }

    public BaseResponse<Wallet> validateAmount(BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) == 0){
            return new BaseResponse<>(false, "Amount must be greater than zero.", null);
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0){
            return new BaseResponse<>(false, "Amount must be greater than zero.", null);
        }
        return new BaseResponse<>(true, "Valid amount", null);
    }
}
