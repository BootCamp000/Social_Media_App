package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository <Message, Integer> {

    // @Query("SELECT * FROM message WHERE postedBy = :postedBY")
    // @Query("FROM message WHERE postedBy = :postedBY")
    // @Query("SELECT List<Message> FROM message WHERE postedBy = :postedBY")
    // @Query(value = "SELECT * FROM message WHERE postedBy = :postedBy", nativeQuery = true)
    // List<Message> findMessagesByAccountId(@Param("postedBy") int postedBy);
}
