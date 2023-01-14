package com.springproject.weathersharecommunity.service;

import com.springproject.weathersharecommunity.Controller.dto.BoardAllResponseDto;
import com.springproject.weathersharecommunity.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardFilterService {

    private final BoardRepository boardRepository;

    @Transactional
    public List<BoardAllResponseDto> filterPosts(List<String> temps) {
        return boardRepository.findByTemps(temps).stream()
                .map(BoardAllResponseDto::new)
                .collect(Collectors.toList());
    }
}
