package com.example.member.Controller;

import com.example.member.Dto.MemberDto;
import com.example.member.Service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    //생성자 주입
    private final MemberService memberService;
     /*@Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    } 와  @RequiredArgsConstructor 이게 같은 코드 */
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
    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }
    @GetMapping("/member/")
    public String member(Model model){
        List<MemberDto> memberDtoList = memberService.findAll();
        //어떠한 html 로 가져갈 데이터가 있다면 model로 사용
        model.addAttribute("memberList",memberDtoList);
        return "member";
    }
    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id, Model model) {//PathVariable 경로상의 값을 담아온다.
        MemberDto memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail";
    }

    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model) {
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDto memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update";
    }
    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDto memberDto) {
        memberService.update(memberDto);
        return "redirect:/member/" + memberDto.getId();
    }
    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/member/";
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
        //@RequestParam과 @ModelAttribute의 눈에 띄는 차이점은, 1:1 매핑이냐, 객체 매핑이냐 인것
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

    @PostMapping("/member/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        //@RequestParam  어노테이션은 사용자가 요청시 전달하는 값을 Handler(Controller)의 매개변수로 1:1 맵핑
        System.out.println("memberEmail = " + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
//        if (checkResult != null) {
//            return "ok";
//        } else {
//            return "no";
//        }
    }
}
