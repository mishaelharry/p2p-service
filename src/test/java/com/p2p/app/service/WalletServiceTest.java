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
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WalletService walletService;

    @InjectMocks
    private UserService userService;

    @Test
    public void whenGetWalletShouldReturnWallet(){
        User user = new User();
        user.setUsername("userA");
        user.setFullName("User A");
        user.setStatus(true);

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.TEN);
        wallet.setUser(user);

        when(walletRepository.findByUser(ArgumentMatchers.any(User.class))).thenReturn(wallet);

        BaseResponse<Wallet> response = walletService.getWallet(user);
        assertThat(response.getData().getBalance()).isSameAs(wallet.getBalance());
    }

    @Test
    public void whenFundShouldReturnWallet(){
        User user = new User();
        user.setUsername("userA");
        user.setFullName("User A");
        user.setStatus(true);

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.TEN);
        wallet.setUser(user);

        when(walletRepository.save(ArgumentMatchers.any(Wallet.class))).thenReturn(wallet);
        when(userRepository.existsById(ArgumentMatchers.any(Long.class))).thenReturn(true);
        when(walletRepository.findByUser(ArgumentMatchers.any(User.class))).thenReturn(wallet);

        BaseResponse<Wallet> response = walletService.fund(user, BigDecimal.TEN);
        assertThat(response.getData().getBalance()).isSameAs(wallet.getBalance());

        verify(walletRepository).save(wallet);
    }

    @Test
    public void whenTransferShouldReturnTrue(){
        User userA = new User();
        userA.setUsername("userA");
        userA.setFullName("User A");
        userA.setStatus(true);

        User userB = new User();
        userB.setUsername("userB");
        userB.setFullName("User B");
        userB.setStatus(true);

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.TEN);
        wallet.setUser(userA);


        when(userRepository.existsById(ArgumentMatchers.any(Long.class))).thenReturn(true);
        when(walletRepository.findByUser(ArgumentMatchers.any(User.class))).thenReturn(wallet);

        when(walletRepository.save(ArgumentMatchers.any(Wallet.class))).thenReturn(wallet);

        BaseResponse response = walletService.transfer(userA, userB, BigDecimal.TEN);
        assertThat(response.isStatus()).isSameAs(true);

    }

    @Test
    public void whenValidateAmountShouldReturnTrue(){
        BaseResponse response = walletService.validateAmount(BigDecimal.TEN);
        assertThat(response.isStatus()).isSameAs(true);
    }
}
