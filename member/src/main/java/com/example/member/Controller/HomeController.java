package com.example.member.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // 기본 요청 메서드
    @GetMapping("/")
    public String index(){
        return "index";//template > index.html
    }
}
