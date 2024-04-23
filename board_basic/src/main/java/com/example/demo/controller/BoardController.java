package com.example.demo.controller;

import com.example.demo.dto.BoardDTO;
import com.example.demo.service.BoardService;
import lombok.Lombok;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor //lombok
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    @GetMapping("/")
    public String findAll(Model model) {
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }
    @GetMapping("/save")
    public String save(){
        return "save";
    }
    @PostMapping("/save")
    public String saveForm(@ModelAttribute BoardDTO boardDTO){
        boardService.save(boardDTO);
        return "index";
    }
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model,@PageableDefault(page=1) Pageable pageable){
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board",boardDTO);
        model.addAttribute("page",pageable.getPageNumber());
            return "detail";
    }
    @GetMapping("/update/{id}")
    public String updateId(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate",boardDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model){
        System.out.println(boardDTO);
        System.out.println(model);
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board",board);
        return "detail";
        //return "redirect:/board/"+boardDTO.getId();//수정시에도 조회수가 올라갈수 잇다.
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/board/";
    }

    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable,Model model ){
       //@PageableDefault(page = 1) 기본 페이지 1부터시작
        pageable.getPageNumber(); //board/paging?page=1 ㅇㅔ서 1
        Page<BoardDTO> boardList = boardService.paging(pageable);
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();
        // page 갯수 20개
        // 현재 사용자가 3페이지
        // 1 2 3
        // 현재 사용자가 7페이지
        // 7 8 9
        // 보여지는 페이지 갯수 3개
        //endPage:: 삼항연산자로 함 ,,  총 페이지 갯수 8개  9보다 작은 값을 가지고 있을때 9페이지는 보이면 안되는거다 전체페이지 값으로 하라
        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "paging";
    }
}
