package com.springproject.weathersharecommunity.Controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springproject.weathersharecommunity.domain.*;
import com.springproject.weathersharecommunity.domain.clothes.Clothes;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class BoardRequestDto {

    private Member member;

    private String content;

    private boolean privacy;

    private LocalDateTime createDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private WeatherStatus status; //날씨 상태[더워요, 따뜻해요, 딱 좋아요, 서늘해요, 추워요]

    private SkyCode skyCode;

    private Clothes clothes;

    private String presentTemperature;

    private String highestTemperature;

    private String lowestTemperature;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime codyDate;

    private String region;
    @Builder
    public BoardRequestDto(Member member , String content, boolean privacy, LocalDateTime createDate, WeatherStatus status, String presentTemperature, String highestTemperature, String lowestTemperature, Clothes clothes, SkyCode skyCode, LocalDateTime codyDate) {
        this.member = member;
        this.content = content;
        this.privacy = privacy;
        this.createDate = createDate;
        this.status = status;
        this.skyCode = skyCode;
        this.clothes = clothes;
        this.presentTemperature = presentTemperature;
        this.highestTemperature = highestTemperature;
        this.lowestTemperature = lowestTemperature;
        this.codyDate = codyDate;
    }

    public Board toEntity() {
        return Board.builder()
                .content(content)
                .createDate(createDate)
                .member(member)
                .status(status)
                .clothes(clothes)
                .skyCode(skyCode)
                .highestTemperature(highestTemperature)
                .lowestTemperature(lowestTemperature)
                .presentTemperature(presentTemperature)
                .privacy(privacy)
                .codyDate(codyDate)
                .region(region)
                .build();
    }
}
