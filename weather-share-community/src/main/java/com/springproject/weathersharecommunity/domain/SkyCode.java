package com.springproject.weathersharecommunity.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum SkyCode {
    Sunny("맑음"), Blur("흐림"), Rain("비"), Snow("눈");
    private String viewName;

    SkyCode(String viewName) {
        this.viewName = viewName;
    }

    @JsonCreator
    public static SkyCode fromValue(String value) {
        switch (value) {
            case "맑음":
                return SkyCode.Sunny;
            case "흐림":
                return SkyCode.Blur;
            case "비":
                return SkyCode.Rain;
            case "눈":
                return SkyCode.Snow;
        }
        return null;
    }
}

