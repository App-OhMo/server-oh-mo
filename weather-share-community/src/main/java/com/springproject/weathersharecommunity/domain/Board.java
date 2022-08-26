package com.springproject.weathersharecommunity.domain;

import com.fasterxml.jackson.annotation.*;
import com.springproject.weathersharecommunity.domain.clothes.Clothes;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
//    @JsonIgnore
    private Member member;
    private boolean privacy;

    @CreatedDate
    private LocalDateTime createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime codyDate;

    @Enumerated(EnumType.STRING)
    private WeatherStatus status; //날씨 상태[더워요, 따뜻해요, 딱 좋아요, 서늘해요, 추워요]

    @Enumerated(EnumType.STRING)
    private SkyCode skyCode;

    @Column
    private String region;

    @OneToMany(mappedBy = "board")
    private List<Image> images = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.ALL)
    @JoinColumn(name = "clothes_id")
    private Clothes clothes;

    @OneToMany(mappedBy = "board")
    @JsonIgnore
    private List<Likes> likesList;

    @OneToMany(mappedBy = "board")
    @JsonIgnore
    private List<Scrape> scrapeList;

    private long likesCount;

    @OneToMany(mappedBy = "board")
    @JsonBackReference
    private List<Reply> replies;

    public void mappingBoardLike(Likes likes) {
        this.likesList.add(likes);
    }

    public void updateLikeCount() {
        this.likesCount = likesList.size();
    }

    public void discountLike(Likes likes) {
        this.likesList.remove(likes);
    }

    private String presentTemperature;

    private String highestTemperature;

    private String lowestTemperature;


    @Builder
    public Board(String content, Member member, boolean privacy, LocalDateTime createDate, WeatherStatus status, SkyCode skyCode,Clothes clothes, String presentTemperature, String highestTemperature, String lowestTemperature, LocalDateTime codyDate, String region) {
        this.content = content;
        this.member = member;
        this.privacy = privacy;
        this.createDate = createDate;
        this.skyCode = skyCode;
        this.status = status;
        this.clothes = clothes;
        this.presentTemperature = presentTemperature;
        this.highestTemperature = highestTemperature;
        this.lowestTemperature = lowestTemperature;
        this.codyDate = codyDate;
        this.region = region;
    }


}
