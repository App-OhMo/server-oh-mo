package com.springproject.weathersharecommunity.Controller;

import com.springproject.weathersharecommunity.Controller.dto.BoardAllResponseDto;
import com.springproject.weathersharecommunity.http.DefaultRes;
import com.springproject.weathersharecommunity.http.StatusCode;
import com.springproject.weathersharecommunity.service.BoardFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardFilterController {

    private final BoardFilterService boardFilterService;



    @GetMapping(value = "/board/filter")
    public ResponseEntity FilterBoard(@RequestParam(value = "tempPick", required = false) int tempPick) {
        List<BoardAllResponseDto> boards = new ArrayList<>();
        List<String> temps = new ArrayList<>();
        for (int i = tempPick-2; i < tempPick+2; i++) {
            temps.add(String.valueOf(i));
        }
        boards = boardFilterService.filterPosts(temps);
        if (boards.size() == 0) {
            return new ResponseEntity(DefaultRes.defaultRes(StatusCode.OK, "컨텐츠가 없습니다"), HttpStatus.OK);
        } else
            return new ResponseEntity(DefaultRes.defaultRes(StatusCode.OK, "필터링 게시글", boards), HttpStatus.OK);
    }


}
