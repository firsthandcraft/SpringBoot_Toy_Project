package com.example.member.Dto;

import com.example.member.Entity.MemberEntity;
import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDto {
    private  long id;
    private  String memberEmail;
    private  String memberPw;
    private  String memberName;
    public static MemberDto toMemberDTO(MemberEntity memberEntity) {
        MemberDto memberDTO = new MemberDto();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPw(memberEntity.getMemberPw());
        memberDTO.setMemberName(memberEntity.getMemberName());
        return memberDTO;
    }

}
