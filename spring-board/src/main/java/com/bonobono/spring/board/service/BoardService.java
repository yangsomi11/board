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
	//boardMapper를 @Autowired해줌으로써 객체의 주소를 따로 생성하지않아도 바로 호출 할 수 있음
	@Autowired private BoardMapper boardMapper;
	
	//boardNo의 값을 가지고 selectBoard 메서드를 호출하는 메서드
	public Board getBoard(int boardNo) {
		return boardMapper.selectBoard(boardNo);
	}
	
	//페이징작업을 위한 전체 selectBoardCount를 호출하는 메서드
	public Map<String, Object> selectBoardList(int currentPage){//값을 하나만 받아와야해서 
		//1.
		final int ROW_PER_PAGE = 10;  //상수로 지정 값을 변경 할 수 없다.
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("currentPage", (currentPage-1)*ROW_PER_PAGE);
		map.put("rowPerPage", ROW_PER_PAGE);  //request.setAttribute = map
		//2.
		int boardCount = boardMapper.selectBoardCount();
		int lastPage = (int)(Math.ceil(boardCount/ROW_PER_PAGE)); //Math.ceil 올림 함수 = if문 써도 상관 없다.
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		//넘어오는 리턴값과 넘겨주는 값까지 모두 변경
		
		returnMap.put("list", boardMapper.selectBoardList(map));
		returnMap.put("boardCount", boardCount);
		returnMap.put("lastPage", lastPage);//필요한 리턴값을 모두 넘겨주기 위해서 사용
		
		return returnMap;
		//총 세번 호출  selecet가 두 번 호출 될 수 있는 경우도 가능하나 하나라도 실패하는 경우 모두 실패!!
		//*controller는 service만 호출 가능 , service는 Mapper만 호출 가능 *
		
	}
	
	//입력받은 값들을 담은 board 참조변수의 주소를 가지고 insertBoard를 호출하는 메서드
	public int insertBoard(Board board) {
		return boardMapper.insertBoard(board);
		
	}
	
	//삭제할 boardNo값을 담고 deleteBoard를 호출하는 메서드
	public int removeBoard(int boardNo) {
		return boardMapper.deleteBoard(boardNo);
		
	}
	
	//업데이트할 값들을 담은 board 참조변수의 주소를 가지고 updateBoard를 호출하는 메서드
	public int modifyBoard(Board board) {
		System.out.println("modifyBoard 실행, Service");
		return boardMapper.updateBoard(board);
		
	}
	

		

}
