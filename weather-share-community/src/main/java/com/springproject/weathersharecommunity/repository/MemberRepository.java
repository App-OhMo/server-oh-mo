package com.springproject.weathersharecommunity.repository;

import com.springproject.weathersharecommunity.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNickName(String nickName);
    Optional<Member> findByUserEmail(String userEmail);

    boolean existsByUserEmail(String userEmail);

    boolean existsByNickName(String nickName);
}
