package com.springproject.weathersharecommunity.service;

import com.springproject.weathersharecommunity.Controller.dto.MemberResponseDto;
import com.springproject.weathersharecommunity.Controller.dto.MemberSaveRequestDto;
import com.springproject.weathersharecommunity.domain.Member;
import com.springproject.weathersharecommunity.repository.FollowRepository;
import com.springproject.weathersharecommunity.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
@Setter
@Getter
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3FileUploadService s3FileUploadService;
    private final FollowRepository followRepository;
    @Transactional
    public void save(MemberSaveRequestDto requestDto, MultipartFile multipartFile) throws IOException {
        requestDto.setPwd(passwordEncoder.encode(requestDto.getPwd()));
        try {
            if (multipartFile.isEmpty()) {
                requestDto.setProfileUrl("");
            } else {
                requestDto.setProfileUrl(s3FileUploadService.uploadImage(multipartFile, "user"));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            requestDto.setProfileUrl("");
        }
        Member member = memberRepository.save(requestDto.toEntity());


    }
//    @Transactional
//    public void confirmEmail(String token) {
//        ConfirmToken findConfirmToken = confirmTokenService.findByIdAndExpired(token);
//        Member member = memberRepository.findById(Long.valueOf(findConfirmToken.getUserId()))
//                .orElseThrow(()->new IllegalArgumentException("없는 멤버입니다."));
//        findConfirmToken.useToken();
//        member.setEmailAuth(true);
//
//    }

    @Transactional(readOnly = true)
    public MemberResponseDto myPage(Long memberId) {
        Member entity = memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("해당 사용자가 없습니다."));
        return new MemberResponseDto(entity);
    }

//    @Transactional
//    public MemberResponseDto getMemberProfile(Long memberId) {
//        Member entity = memberRepository.findById(memberId)
//                .orElseThrow(()->new IllegalArgumentException("해당 사용자가 없습니다."));
//        MemberResponseDto memberResponseDto = new MemberResponseDto(entity);
//        memberResponseDto.getFollowingCount(followRepository.findFollowingCountById(memberId));
//
//        return new MemberResponseDto(entity);
//    }

    @Transactional
    public String ProfileImgUpdate(Long memberId ,MultipartFile multipartFile) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("해당 사용자가 없습니다."));
        s3FileUploadService.deleteFile(member.getProfileUrl(),"user");
        member.updateProfile(s3FileUploadService.uploadImage(multipartFile,"user"));
        return member.getProfileUrl();

    }

    @Transactional
    public Member findByUserName(MemberSaveRequestDto requestDto) {
        Member member = memberRepository.findByUserEmail(requestDto.getUserEmail())
                .orElseThrow(()->new IllegalArgumentException("해당 사용자가 없습니다."));
        return member;
    }


    public Member findOne(Long reportId) {
        Member member = memberRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        return member;
    }

    @Transactional
    public void duplicationMember(MemberSaveRequestDto requestDto) {
        boolean result = true;
        Optional<Member> checkEmail = memberRepository.findByUserEmail(requestDto.getUserEmail());
        Optional<Member> checkMember = memberRepository.findByNickName(requestDto.getNickName());
        if (checkMember.isPresent() || checkEmail.isPresent()) {
            result = false;
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    public boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByUserEmail(email);
    }

    @Transactional
    public boolean checkNickNameDuplicate(String nickName) {
        return memberRepository.existsByNickName(nickName);
    }

}