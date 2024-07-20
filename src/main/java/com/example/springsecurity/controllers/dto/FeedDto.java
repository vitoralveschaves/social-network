package com.example.springsecurity.controllers.dto;

import java.util.List;

public record FeedDto(List<FeedItemDto> feedItems, Integer page, Integer pageSize, Integer totalPages, Long totalElements) {
}
