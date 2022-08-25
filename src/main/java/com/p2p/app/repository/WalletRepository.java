package com.p2p.app.repository;

import com.p2p.app.model.User;
import com.p2p.app.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findByUser(User user);

}
