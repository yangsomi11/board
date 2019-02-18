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
    
    @GetMapping("/boardDetail")
    public String boardDetail(int boardNo, Model model) {
    	Board board = boardService.getBoard(boardNo);
    	model.addAttribute("board",board);
		return "boardDetail";
    	
    }
    
    // 리스트 요청 전체 DB에 저장되어있는 데이터를 불러와서 리스트 화면에 나타내는 메서드
    @RequestMapping(value="/boardList")
    public String boardList(Model model, @RequestParam(value="currentPage", required=false, defaultValue="1") int currentPage) {
    	Map<String, Object> map = boardService.selectBoardList(currentPage);
    	
    	// map에서 가져온  list, boardCount, lastPage, currentPage를 model영역에 담는다 -> 담긴 화면을 boardList 포워드한다.
    	model.addAttribute("list", map.get("list"));
    	model.addAttribute("boardCount",map.get("boardCount"));
    	model.addAttribute("lastPage",map.get("lastPage"));
    	model.addAttribute("currentPage",currentPage);
    	System.out.println("boardList 요청");
    	return "boardList"; //단순히 forward해서 list화면을 보여주는 메서드
    }
    
    
    // 데이터값을 입력할 수 있는 화면을 보여주는 메서드
    @GetMapping("/boardAdd")
    public String boardAdd() {
        System.out.println("boardAdd 폼 요청");
        return "boardAdd";  //단순히 forward해서 boardAdd 글 등록화면을 보여주는 메서드
    }
    
    // 입력(액션) 요청 화면에서 입력받은값을 DB에 연결해서 데이처를 insert해주는 메서드
    @PostMapping("/boardAddAction")
    public String boardAddAction(Board board) {
        //commend객체 -> 필드와 input type이 같아야 한다. 이름 = name ->setter
    	boardService.insertBoard(board);
        return "redirect:/boardList"; // 글입력후 "/boardList"로 리다이렉트(재요청)
    }

    //삭제 할 값을 받아와서 DB에 저장되어있는 데이터를 가져와 화면에 보여주는 메서드
    @GetMapping("/deleteBoard")
    //list화면에서 삭제 할 boardNo값을 받아와서 boardNo변수에 담고 그 값을 getBoard메서드를 호출 입력한다.
    public String deleteBoard(@RequestParam(value="boardNo")int boardNo, Model model) {
		System.out.println("deleteBoard 실행, Controller");
    	//getBoard를 호출한 후 return값을 boardUpdate변수에 담아준다.
		Board boardDelete = boardService.getBoard(boardNo);
		//model영역에 boardUpdate값을 board라는 이름으로 담아준다.
    	model.addAttribute("board", boardDelete);
    	return "boardDelete"; //단순히 forward해서 boardDelete 삭제확인 할 화면을 보여주는 메서드
    	
    }
    
    
  //삭제 할 nomber값을 받아와서 그 값의 데이터를 삭제하는 메서드
    @PostMapping("/removeBoard")
    //list화면에서 삭제 할 boardNo값을 받아와서 boardNo변수에 담고 그 값을 removeBoard메서드를 호출 입력한다.
    public String boardDelete(Board board) {  	
    	boardService.removeBoard(board);
		return "redirect:/boardList"; // 글입력후 "/boardList"로 리다이렉트(재요청)
    	
    }
    //수정 할 값을 받아와서 DB에 저장되어있는 데이터를 가져와 화면에 보여주는 메서드
    @GetMapping("/updateBoard")
    //list화면에서 수정 할 boardNo값을 받아와서 boardNo변수에 담고 그 값을 getBoard메서드를 호출 입력한다.
    public String updateBoard(@RequestParam(value="boardNo")int boardNo, Model model) {
		System.out.println("updateBoard 실행, Controller");
    	//getBoard를 호출한 후 return값을 boardUpdate변수에 담아준다.
		Board boardUpdate = boardService.getBoard(boardNo);
		//model영역에 boardUpdate값을 board라는 이름으로 담아준다.
    	model.addAttribute("board", boardUpdate);
    	return "updateBoard"; //단순히 forward해서 updateBoard 수정 할 화면을 보여주는 메서드
    	
    }
    //DB에 저장되어있는 데이터를 수정하고 다시 DB에 저장하는 메서드
    @PostMapping("/boardUpdateAction")
	public String boardUpdateAction(Board board) {
    	System.out.println("1boardUpdateAction 실행, Controller");
		boardService.modifyBoard(board);
		System.out.println("2boardUpdateAction 실행, Controller");
		return "redirect:/boardList"; // 글입력후 "/boardList"로 리다이렉트(재요청) 
		
	}
    
} 
