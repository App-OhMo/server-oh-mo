package com.springproject.weathersharecommunity.repository;

import com.springproject.weathersharecommunity.domain.Block;
import com.springproject.weathersharecommunity.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlockRepository extends JpaRepository<Block, Long> {

    List<Block> findByMember(Member member);
}
