package com.example.springsecurity.repositories;

import com.example.springsecurity.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
