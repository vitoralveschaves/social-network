package com.example.springsecurity.controllers.dto;

public record LoginResponseDto(String accessToken, Long expiresIn) {
}
