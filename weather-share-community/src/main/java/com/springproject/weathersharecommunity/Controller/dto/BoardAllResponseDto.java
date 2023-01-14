package com.springproject.weathersharecommunity.Controller.dto;


import com.springproject.weathersharecommunity.domain.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardAllResponseDto {

    private Long id;
    private String content;

    private boolean privacy;

    private LocalDateTime createDate = LocalDateTime.now();

    private WeatherStatus status; //날씨 상태[더워요, 따뜻해요, 딱 좋아요, 서늘해요, 추워요]

    private String memberName;

    private String profileUrl;

    private String imgUrl;

    private SkyCode skyCode;


    public BoardAllResponseDto(Board entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.memberName = entity.getMember().getUsername();
        this.profileUrl = entity.getMember().getProfileUrl();
        this.status = entity.getStatus();
        try{
        this.imgUrl = entity.getImages().get(0).getUrl();
        }catch(IndexOutOfBoundsException e){
            imgUrl = "";
        }
        this.skyCode = entity.getSkyCode();
    }


}
