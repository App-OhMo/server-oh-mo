package com.springproject.weathersharecommunity.Controller.dto;

import com.springproject.weathersharecommunity.domain.clothes.Accessory1;
import com.springproject.weathersharecommunity.domain.clothes.Accessory2;
import com.springproject.weathersharecommunity.domain.clothes.Clothes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ClothesResponseDto {

    private String top;
    private String bottom;
    private String outerClothing;
    private String shoes;
    private String accessory1;
    private String accessory2;

    public ClothesResponseDto (Clothes entity) {
        try {
            this.top = entity.getTop().getViewName();
        } catch (NullPointerException e) {
            top = "";
        }
        try {
            this.bottom = entity.getBottom().getViewName();
        } catch (NullPointerException e) {
            bottom = "";
        }
        try {
            this.outerClothing = entity.getOuterClothing().getViewName();
        } catch (NullPointerException e) {
            outerClothing = "";
        }
        try {
            this.shoes = entity.getShoes().getViewName();
        } catch (NullPointerException e) {
            shoes = "";
        }
        try {
            this.accessory1 = entity.getAccessory1().getViewName();
        } catch (NullPointerException e) {
            accessory1 = "";
        }
        try {
            this.accessory2 = entity.getAccessory2().getViewName();
        } catch (NullPointerException e) {
            accessory2 = "";
        }
    }

}
