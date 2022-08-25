package com.p2p.app;

import com.p2p.app.model.User;
import com.p2p.app.model.Wallet;
import com.p2p.app.response.BaseResponse;
import com.p2p.app.service.UserService;
import com.p2p.app.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;

@Slf4j
@SpringBootApplication
public class P2pServiceApplication {

	@Autowired
	private UserService userService;

	@Autowired
	private WalletService walletService;

	public static void main(String[] args) {
		SpringApplication.run(P2pServiceApplication.class, args);
	}

	@EventListener
	public void seed(ContextRefreshedEvent event) {
		BaseResponse<User> userA = userService.createUser("User A", "userA");
		if (userA.isStatus()){
			log.info(userA.getData().getFullName() + " is added to the app");
			walletService.fund(userA.getData(), BigDecimal.valueOf(10));
			log.info(userA.getData().getFullName() + " deposits 10 dollars");
		}

		BaseResponse<User> userB = userService.createUser("User B", "userB");
		if (userB.isStatus()){
			log.info(userB.getData().getFullName() + " is added to the app");
			walletService.fund(userB.getData(), BigDecimal.valueOf(20));
			log.info(userB.getData().getFullName() + " deposits 20 dollars");
		}

		walletService.transfer(userB.getData(), userA.getData(), BigDecimal.valueOf(15));
		log.info("User B sends 15 dollars to User A");

		BaseResponse<Wallet> walletA = walletService.getWallet(userA.getData());
		if (walletA.isStatus()){
			log.info("User A checks their balance and has "+ walletA.getData().getBalance() +" dollars");
		}

		BaseResponse<Wallet> walletB = walletService.getWallet(userB.getData());
		if (walletB.isStatus()){
			log.info("User B checks their balance and has "+ walletB.getData().getBalance() +" dollars");
		}

		walletService.transfer(userA.getData(), userB.getData(), BigDecimal.valueOf(25));
		log.info("User A transfers 25 dollars from their account");

		BaseResponse<Wallet> walletA2 = walletService.getWallet(userA.getData());
		if (walletA2.isStatus()){
			log.info("User A checks their balance and has "+ walletA2.getData().getBalance() +" dollars");
		}

	}
}
