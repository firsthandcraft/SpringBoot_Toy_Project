package com.example.member.Controller;

import com.example.member.Dto.MemberDto;
import com.example.member.Service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {
    //생성자 주입
    private final MemberService memberService;
    String saveForm(){
        return "join";
    };
    @GetMapping("/member/save")
    public String join(){
        return "join";
    }
    @GetMapping("/member/login")
    public String login(){
        return "login";
    }
    /*@PostMapping("/member/save")
    public String save(@RequestParam("memberEmail") String MemberEmail,
                       @RequestParam("memberPw") String MemberPw,
                       @RequestParam("memberName") String MemberName
    ){
        System.out.println("MemberController.save");//soutm
        System.out.println(MemberEmail);

        return "index";
    }*/
    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDto memberDto){
        System.out.println("MemberController.save");//soutm
        System.out.println(memberDto);
        memberService.save(memberDto);

        return "index";
    }

    @PostMapping("/member/login")
    public String login (@ModelAttribute MemberDto memberDto, HttpSession session){
        MemberDto loginResult =memberService.login(memberDto);
        if(loginResult != null){
            session.setAttribute("loginEmail",loginResult.getMemberEmail());
            return "main";
        } else {
            return "login";
        }

    }
}
