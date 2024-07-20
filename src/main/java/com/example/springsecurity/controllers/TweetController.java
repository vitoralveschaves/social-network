package com.example.springsecurity.controllers;

import com.example.springsecurity.controllers.dto.CreateTweetDto;
import com.example.springsecurity.controllers.dto.FeedDto;
import com.example.springsecurity.controllers.dto.FeedItemDto;
import com.example.springsecurity.entities.Role;
import com.example.springsecurity.entities.Tweet;
import com.example.springsecurity.repositories.TweetRepository;
import com.example.springsecurity.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class TweetController {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetController(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/feed")
    public ResponseEntity<FeedDto> feed(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        var tweets = this.tweetRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimestamp"))
                .map(tweet -> new FeedItemDto(tweet.getTweetId(), tweet.getContent(), tweet.getUser().getUsername()));

        return ResponseEntity.status(HttpStatus.OK).body(new FeedDto(tweets.getContent(), page, pageSize, tweets.getTotalPages(), tweets.getTotalElements()));

    }

    @PostMapping("/tweets")
    public ResponseEntity<Void> createTweet(@RequestBody CreateTweetDto createTweetDto, JwtAuthenticationToken  token) {
        var user = this.userRepository.findById(UUID.fromString(token.getName()));

        var tweet = new Tweet();
        tweet.setUser(user.get());
        tweet.setContent(createTweetDto.content());

        tweetRepository.save(tweet);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/tweets/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable("id") Long tweetId, JwtAuthenticationToken token) {
        var user = this.userRepository.findById(UUID.fromString(token.getName()));
        var tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var isAdmin = user.get().getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

        if(isAdmin || tweet.getUser().getUserId().equals(UUID.fromString(token.getName()))) {
            this.tweetRepository.deleteById(tweetId);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
