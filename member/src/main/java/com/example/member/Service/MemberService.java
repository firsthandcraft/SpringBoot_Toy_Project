package com.example.member.Service;

import com.example.member.Dto.MemberDto;
import com.example.member.Entity.MemberEntity;
import com.example.member.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;


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
        if (byMemberEmail.isPresent()) {
            //조회 결과가 있으면 해당 이메일이 있다.
            MemberEntity memberEntity = byMemberEmail.get();
            if (memberEntity.getMemberPw().equals(memberDto.getMemberPw())) {//string 값일때 equals로 숫자는 == 로
                //비밀번호 일치
                MemberDto dto = MemberDto.toMemberDTO(memberEntity);//(id=12, memberEmail=eee, memberPw=eee, memberName=eee)
                return dto;
            } else {
                return null;
            }

        } else {
            return null;
        }
    }

    public List<MemberDto> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDto> memberDtoList = new ArrayList<>();
        for (MemberEntity memberEntity : memberEntityList) {
            memberDtoList.add(MemberDto.toMemberDTO(memberEntity));
            // MemberDto memberDto=MemberDto.toMemberDTO(memberEntity);
            //memberDtoList.add(memberDto);
        }
        return memberDtoList;
    }

    public MemberDto findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            // optionalMemberEntity memberEntity = optionalMemberEntity.get();
            //MemberDto memberDto=MemberDto.toMemberDTO(memberEntity);
            //return memberDto;
            return MemberDto.toMemberDTO(optionalMemberEntity.get());
        } else {return null;}
    }




    public MemberDto updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()) {
            return MemberDto.toMemberDTO(optionalMemberEntity.get());
        } else {return null;}
    }

    public void update(MemberDto memberDto) {
        memberRepository.save(MemberEntity.toUodateMemberEntity(memberDto));
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
    public String emailCheck(String myEmail) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(myEmail);
        if (byMemberEmail.isPresent()) {
            return null;
        } else {return "ok";}

    }
}
