package com.example.demo.repository;

import com.example.demo.entity.BoardEntity;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
    // update board_table set board_hits=board_hits+1 where id=?
    //@Query(value="update (테이블,엔티티이름) 약어 set b.엔티티에 정의한 컬럼=b.boardHits+1 where b.id=:id",nativeQuery = true)
    //nativeQuery = true 실제 DB 에서 사용하는 쿼리를 쓸수 있다.
    @Modifying //
    @Query(value="update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id")  Long id);
    //dn위의처럼 jpa별도의 어노테이션 을 만들경우 service에 @Transactional을 넣어야한다. -영속성 컨텍스트 처리하기
}
