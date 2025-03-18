package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Account;

@Repository 
public interface AccountRepository extends JpaRepository <Account, Integer> {

    // @Query("SELECT * FROM account WHERE username = :username")
    // @Query("SELECT account FROM account WHERE username = :username")
    @Query(value = "SELECT * FROM account WHERE username = :username", nativeQuery = true)
    Account findByUsername(@Param("username") String username);

}
