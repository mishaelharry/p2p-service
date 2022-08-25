package com.p2p.app.repository;

import com.p2p.app.model.User;
import com.p2p.app.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    //Optional<User> findByUsername(String username);

}
