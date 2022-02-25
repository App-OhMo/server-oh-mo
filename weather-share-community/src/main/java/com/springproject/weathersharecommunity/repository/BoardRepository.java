package com.springproject.weathersharecommunity.repository;

import com.springproject.weathersharecommunity.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.util.ProxyUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Board board){
        if (board.getId() == null) {
            em.persist(board);
        } else {
            em.merge(board);
        }
    }

    public Board findOne(Long id){

        return em.find(Board.class, id);
    }

    public List<Board> findAll(){
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }

    public List<Board> findByUser(Long user){
        return em.createQuery("select b from Board b where b.user = :user", Board.class)
                .setParameter("user", user)
                .getResultList();
    }


    public void deleteOne(Board board){
//        return em.createQuery("delete from Board b where b.board_id = :id", Board.class)
//                .setParameter("id", id)
//                .getResultList();
//
        Class<Board> type = (Class<Board>) ProxyUtils.getUserClass(board);

        if(type == null){
            return;
        }

        em.remove(em.contains(board) ? board : em.merge(board));
    }


}