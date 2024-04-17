package com.example.member.Repository;

import com.example.member.Entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository <MemberEntity, Long>{//<어떤entityclass를다룰것인지,사용하고자 하는 entityclass의 pk의 타입>
    //save 메소드는 자체에 있다. 

    // 메소드 정의 하기
    // 이메일로 회원정보 조회
     Optional<MemberEntity> findByMemberEmail(String memberEmail);//자바 util에서 제공하는 optional클래스 null 방지 임
}
