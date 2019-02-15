package com.bonobono.spring.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bonobono.spring.board.mapper.BoardMapper;
import com.bonobono.spring.board.vo.Board;

@Service
@Transactional //메서드 확인 처리 
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	public Board getBoard(int boardNo) {
		return boardMapper.selectBoard(boardNo);
	}
	
	public Map<String, Object> selectBoardList(int currentPage){//값을 하나만 받아와야해서 
		//1.
		final int ROW_PER_PAGE = 10;  //상수로 지정 값을 변경 할 수 없다.
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("currentPage", currentPage);
		map.put("rowPerPage", ROW_PER_PAGE);  //request.setAttribute = map
		//2.
		int boardCount = boardMapper.selectBoardCount();
		int lastPage = (int)(Math.ceil(boardCount / ROW_PER_PAGE)); //Math.ceil 올림 함수 = if문 써도 상관 없다.
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		//넘어오는 리턴값과 넘겨주는 값까지 모두 변경
		
		returnMap.put("list", boardMapper.selectBoardList(map));
		returnMap.put("boardCount", boardCount);
		returnMap.put("lastPage", lastPage);//필요한 리턴값을 모두 넘겨주기 위해서 사용
		
		return returnMap;
		
		//총 세번 호출  selecet가 두 번 호출 될 수 있는 경우도 가능하나 하나라도 실패하는 경우 모두 실패!!
		//*controller는 service만 호출 가능 , service는 Mapper만 호출 가능 *
		
	}
	
	
	public int selectBoardCount() {
		
		return boardMapper.selectBoardCount();
		
	}
	
	public int insertBoard(Board board) {
		return boardMapper.insertBoard(board);
		
	}
	
	public int removeBoard(int boardNo) {
		return boardMapper.deleteBoard(boardNo);
		
	}
	
	public int modifyBoard(Board board) {
		return boardMapper.updateBoard(board);
		
	}
	

		

}
