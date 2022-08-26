package com.springproject.weathersharecommunity.Controller.dto;

import com.springproject.weathersharecommunity.domain.*;
import com.springproject.weathersharecommunity.domain.clothes.Clothes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;

    private String content;

    private boolean privacy;

    private LocalDateTime createDate = LocalDateTime.now();

    private WeatherStatus status; //날씨 상태[더워요, 따뜻해요, 딱 좋아요, 서늘해요, 추워요]

    private String skyCode;

    private String presentTemperature;

    private String highestTemperature;

    private String lowestTemperature;

    private LocalDateTime codyDate;

    private Clothes clothes;

    private List<String> images;
    private MemberResponseDto memberResponseDto;
    private ClothesResponseDto clothesResponseDto;
    private String region;
    public BoardResponseDto(Board entity, List<String> images, MemberResponseDto memberResponseDto, ClothesResponseDto clothesResponseDto) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.images = images;
        this.clothesResponseDto = clothesResponseDto;
        this.memberResponseDto = memberResponseDto;
        this.status = entity.getStatus();
        this.skyCode = entity.getSkyCode().getViewName();
        this.highestTemperature = entity.getHighestTemperature();
        this.lowestTemperature = entity.getLowestTemperature();
        this.presentTemperature = entity.getPresentTemperature();
        this.codyDate = entity.getCodyDate();
        this.region = entity.getRegion();
    }


}
