package com.example.member.Service;

import com.example.member.Dto.MemberDto;
import com.example.member.Entity.MemberEntity;
import com.example.member.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private  final MemberRepository memberRepository;


    public void save(MemberDto memberDto) {//MemberRepository에서 save라는 메소드가 자체에 있었다.
        //1. dto-> entity로 변환하기
        //2. respository의 save메서드 호
        //repository의 save메서드 호출(조건. entity 객체를 넘져줘야함)
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDto);
        memberRepository.save(memberEntity);//save란 말은 jpa가 제공해주는 메서드

    }

    public MemberDto login(MemberDto memberDto) {
        /**
         * 1.회원이 입력한 이메일로 DB 에서 조회하기
         * 2. DB 에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDto.getMemberEmail());
        if(byMemberEmail.isPresent()){
            //조회 결과가 있으면 해당 이메일이 있다.
            MemberEntity memberEntity = byMemberEmail.get();
            if(memberEntity.getMemberPw().equals(memberDto.getMemberPw())){//string 값일때 equals로 숫자는 == 로
                //비밀번호 일치
                MemberDto dto = MemberDto.toMemberDTO(memberEntity);
                return dto;
            }else {return null;}

        }else {return null;}
    }
}
