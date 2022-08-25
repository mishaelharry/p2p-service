package com.p2p.app.service;

import com.p2p.app.model.User;
import com.p2p.app.model.Wallet;
import com.p2p.app.repository.UserRepository;
import com.p2p.app.repository.WalletRepository;
import com.p2p.app.response.BaseResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void whenCreateUserShouldReturnUser(){
        User user = new User();
        user.setUsername("userA");
        user.setFullName("User A");
        user.setStatus(true);

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setUser(user);

        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        when(walletRepository.save(ArgumentMatchers.any(Wallet.class))).thenReturn(wallet);

        BaseResponse<User> response = userService.createUser(user.getFullName(), user.getUsername());
        assertThat(response.getData().getUsername()).isSameAs(user.getUsername());

        verify(userRepository).save(user);
        verify(walletRepository).save(wallet);
    }
}
