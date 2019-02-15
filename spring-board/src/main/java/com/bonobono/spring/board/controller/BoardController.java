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
    
    // 리스트 요청 전체 DB에 저장되어있는 데이터를 불러와서 리스트 화면에 나타내는 메서드
    @RequestMapping(value="/boardList")
    public String boardList(Model model, @RequestParam(value="currentPage", required=false, defaultValue="1") int currentPage) {
    	Map<String, Object> map = boardService.selectBoardList(currentPage);
    	
    	model.addAttribute("list", map.get("list"));
    	return "/boardList";
    }
    
    // 입력(액션) 요청 화면에서 입력받은값을 DB에 연결해서 데이처를 insert해주는 메서드
    @RequestMapping(value="/boardAddAction", method = RequestMethod.POST)
    public String boardAddAction(Board board) {
        //commend객체 -> 필드와 input type이 같아야 한다. 이름 = name ->setter
    	boardService.insertBoard(board);
        return "redirect:/boardList"; // 글입력후 "/boardList"로 리다이렉트(재요청)
    }
    
    //추가 할 데이터값을 입력할 수 있는 화면을 보여주는 메서드
    @RequestMapping(value="/boardAdd", method = RequestMethod.GET)
    public String boardAdd() {
        System.out.println("boardAdd 폼 요청");
        return "boardAdd";
    }
    
  //삭제 할 nomber값을 받아와서 그 값의 데이터를 삭제하는 메서드
    @GetMapping
    public String boardDelete(@RequestParam(value="boardNo")int boardNo) {  	
    	boardService.removeBoard(boardNo);
		return "redirect:/boardList";
    	
    }
    //수정 할 값을 받아와서 DB에 저장되어있는 데이터를 가져와 화면에 보여주는 메서드
    @GetMapping("/boardUpdate")
    public String boardUpdate(@RequestParam(value="boardNo")int boardNo) {
		System.out.println("boardUpdate 실행, Controller");
    	boardService.getBoard(boardNo);
    	return "boardUpdate";
    	
    }

	/*
	 * public String boardUpdate(@RequestParam(value="boardNo")int boardNo) {
	 * System.out.println("boardUpdate 실행, Controller"); int result =
	 * boardService.modifyBoard(boardNo); System.out.println("업데이트 쿼리 성공 여부 -> " +
	 * result); return "boardUpdate";
	 */
    
} 
