package com.springproject.weathersharecommunity.Controller;

import com.springproject.weathersharecommunity.Controller.dto.BoardAllResponseDto;
import com.springproject.weathersharecommunity.Controller.dto.BoardImgResponseDto;
import com.springproject.weathersharecommunity.domain.SkyCode;
import com.springproject.weathersharecommunity.http.DefaultRes;
import com.springproject.weathersharecommunity.http.StatusCode;
import com.springproject.weathersharecommunity.repository.BoardRepository;
import com.springproject.weathersharecommunity.service.BoardService;
import com.springproject.weathersharecommunity.service.recommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class recommendController {

    private final BoardService boardService;
    private final recommendService recommendService;

    @GetMapping(value = "/board/recommend/{skyCode}")
    public ResponseEntity RecommendBoard(@RequestParam(value = "currentWeather", required = false) int currentWeather, @PathVariable SkyCode skyCode) {
//        boardList = boardList.stream().filter(b -> b.getCategory().getTitle().equals(Status.PROCEEDING.getCode())).collect(Collectors.toList());
        List<BoardAllResponseDto> boards = new ArrayList<>();
        List<String> temps = new ArrayList<>();
        for (int i = currentWeather-2; i < currentWeather+2; i++) {
            temps.add(String.valueOf(i));
        }
        System.out.println();
        boards = recommendService.recommendPosts(temps).stream().filter(b -> b.getSkyCode().equals(skyCode)).collect(Collectors.toList());

        if (boards.size() == 0) {
            return new ResponseEntity(DefaultRes.defaultRes(StatusCode.OK, "컨텐츠가 없습니다"), HttpStatus.OK);
        } else
            return new ResponseEntity(DefaultRes.defaultRes(StatusCode.OK, "추천 게시글", boards), HttpStatus.OK);

    }

}
