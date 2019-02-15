package com.bonobono.spring.board.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bonobono.spring.board.service.BoardService;
import com.bonobono.spring.board.vo.Board;


@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;
    
    // 리스트 요청
    @RequestMapping(value="/boardList")
    public String boardList(Model model, @RequestParam(value="currentPage", required=false, defaultValue="1") int currentPage) {
    	Map<String, Object> map = boardService.selectBoardList(currentPage);
        model.addAttribute("list", map.get("list"));
    	return "/boardList";
    }
    
    // 입력(액션) 요청
    @RequestMapping(value="/boardAddAction", method = RequestMethod.POST)
    public String boardAddAction(Board board) {
        //commend객체 -> 필드와 input type이 같아야 한다. 이름 = name ->setter
    	boardService.insertBoard(board);
        return "redirect:/boardList"; // 글입력후 "/boardList"로 리다이렉트(재요청)
    }
    
    // 입력페이지 요청
    @RequestMapping(value="/boardAdd", method = RequestMethod.GET)
    public String boardAdd() {
        System.out.println("boardAdd 폼 요청");
        return "boardAdd";
    }
    @GetMapping
    public String boardDelete(@RequestParam(value="boardNo")int boardNo) {
    	
    	boardService.removeBoard(boardNo);
		return "redirect:/boardList";
    	
    }

    
} 
