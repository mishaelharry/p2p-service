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
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    public BaseResponse<User> createUser(String fullName, String username){
        if (userRepository.existsByUsername(username)){
            return new BaseResponse<>(false, "User already exists", null);
        }

        User user = new User();
        user.setFullName(fullName);
        user.setUsername(username);
        user.setStatus(true);
        User result = userRepository.save(user);

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setUser(result);
        walletRepository.save(wallet);

        return new BaseResponse<>(true, "User created successfully", result);
    }

}
