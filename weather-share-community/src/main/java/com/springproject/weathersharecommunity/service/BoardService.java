package com.springproject.weathersharecommunity.service;

import com.springproject.weathersharecommunity.Controller.dto.*;
import com.springproject.weathersharecommunity.domain.Board;
import com.springproject.weathersharecommunity.domain.Member;
import com.springproject.weathersharecommunity.domain.clothes.Clothes;
import com.springproject.weathersharecommunity.repository.BoardRepository;
import com.springproject.weathersharecommunity.repository.ClothesRepository;
import com.springproject.weathersharecommunity.repository.ImageRepository;
import com.springproject.weathersharecommunity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final S3FileUploadService fileUploadService;

    private final BoardRepository boardRepository;

    private final ImageRepository imageRepository;

    private final MemberRepository memberRepository;

    private final ClothesRepository clothesRepository;
    @Transactional
    public BoardResponseDto boardDetail(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("글을 찾을수 없습니다."));
        List<String> images = imageRepository.findUrlByBoardId(boardId);

        Clothes clothes = clothesRepository.findAllByBoardId(boardId);
        ClothesResponseDto clothesResponseDto = new ClothesResponseDto(clothes);
        BoardResponseDto boardResponseDto = new BoardResponseDto(board, images, null, clothesResponseDto);
        Long memberId = board.getMember().getId();
        Member entity = memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("해당 사용자가 없습니다."));
        MemberResponseDto memberResponseDto = new MemberResponseDto(entity);
        boardResponseDto.setMemberResponseDto(memberResponseDto);

        return boardResponseDto;
    }

    @Transactional
    public void save(BoardRequestDto requestDto, Clothes clothes, List<MultipartFile> images){

        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) user.getPrincipal();
        requestDto.setMember(member);
        Board board1 = boardRepository.save(requestDto.toEntity());
        clothes.setBoard(board1);
        board1.setImages(fileUploadService.uploadImage(images,board1));

    }

    @Transactional
    public List<BoardAllResponseDto> findAllBoard() {
        List<Board> boards = boardRepository.findAll();
        List<BoardAllResponseDto> list = new ArrayList<>();
        try{
        list = boards
                .stream().map(BoardAllResponseDto::new)
                .collect(Collectors.toList());

        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return list;

    }

}
