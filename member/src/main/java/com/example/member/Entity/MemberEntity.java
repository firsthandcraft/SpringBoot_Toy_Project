package com.example.member.Entity;

import com.example.member.Dto.MemberDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="member_table")
public class MemberEntity {//일종의 테이블 역할 jpa
    //Entity 실제 DataBase 의 테이블과 1:1 매핑되는 클래스이다. 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto_increment
    private Long id;

    @Column(unique = true)//유니크 제약조건 추가
    private String memberEmail;

    @Column
    private String memberPw;

    @Column
    private String memberName;

    public static MemberEntity toMemberEntity(MemberDto memberDto){//dto를 entity로 변환해주었다.
        MemberEntity memberEntity= new MemberEntity();
        memberEntity.setMemberEmail(memberDto.getMemberEmail());
        memberEntity.setMemberPw(memberDto.getMemberPw());
        memberEntity.setMemberName(memberDto.getMemberName());
        return memberEntity;
    }
    public static MemberEntity toUodateMemberEntity(MemberDto memberDto){
        MemberEntity memberEntity= new MemberEntity();
        memberEntity.setId(memberDto.getId());
        memberEntity.setMemberEmail(memberDto.getMemberEmail());
        memberEntity.setMemberPw(memberDto.getMemberPw());
        memberEntity.setMemberName(memberDto.getMemberName());
        return memberEntity;
    }
    /**@Builder패턴으로 사용하기
    public static MemberEntity toUpdateMemberEntity(MemberDto memberDto) {
        return MemberEntity.builder()
                .id(memberDto.getId())
                .memberEmail(memberDto.getMemberEmail())
                .memberPw(memberDto.getMemberPw())
                .memberName(memberDto.getMemberName())
                .build();
    } */
}
