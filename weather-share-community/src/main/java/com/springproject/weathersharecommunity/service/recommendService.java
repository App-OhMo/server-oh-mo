package com.springproject.weathersharecommunity.service;

import com.springproject.weathersharecommunity.Controller.dto.BoardAllResponseDto;
import com.springproject.weathersharecommunity.Controller.dto.LikeSaveRequestDto;
import com.springproject.weathersharecommunity.domain.Board;
import com.springproject.weathersharecommunity.domain.Follow;
import com.springproject.weathersharecommunity.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.expression.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class recommendService {

    private final BoardRepository boardRepository;

    @Transactional
    public List<BoardAllResponseDto> recommendPosts(List<String> temps) {
        return boardRepository.recommendPosts(temps).stream()
                .map(BoardAllResponseDto::new)
                .collect(Collectors.toList());
    }

}
