package com.springproject.weathersharecommunity.Controller.dto;

import com.springproject.weathersharecommunity.domain.Member;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collections;

@Getter @Setter
@NoArgsConstructor
public class MemberSaveRequestDto {
    private PasswordEncoder passwordEncoder;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickName;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String userEmail;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
//    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String pwd;
    private String profileUrl;
    private ArrayList<String> roles;
    @Builder
    public MemberSaveRequestDto(String nickName, String userEmail, String pwd) {
        this.nickName = nickName;
        this.userEmail = userEmail;
        this.pwd = pwd;
    }

    public Member toEntity(){
        return Member.builder()
                .nickName(nickName)
                .userEmail(userEmail)
                .pwd(pwd)
                .profileUrl(profileUrl)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}
