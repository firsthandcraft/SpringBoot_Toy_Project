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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto_increment
    private Long id;

    @Column(unique = true)//유니크 제약조건 추가
    private String memberEmail;

    @Column
    private String memberPw;

    @Column
    private String memberName;

    public static MemberEntity toMemberEntity(MemberDto memberDto){
        MemberEntity memberEntity= new MemberEntity();
        memberEntity.setMemberEmail(memberDto.getMemberEmail());
        memberEntity.setMemberPw(memberDto.getMemberPw());
        memberEntity.setMemberName(memberDto.getMemberName());
        return memberEntity;
    }
}
