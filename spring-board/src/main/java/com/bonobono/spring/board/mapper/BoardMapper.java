package com.bonobono.spring.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bonobono.spring.board.vo.Board;

@Mapper
public interface BoardMapper {
	
	//수정, 상세정보 등 한가지 정보를 가져올때 
	Board selectBoard(int boardNo);
	
	//list를 보여주기위해 두가지 이상 값을 가져오지 못해서 Map을 사용  
	List<Board> selectBoardList(Map<String, Integer> map); 
	
	//페이징 전체갯수를 불러오기위해서 
	int selectBoardCount(); 
	
	//DB에 추가
	int insertBoard(Board board);
	
	//값을 수정하고 DB에 수정한 값을 저장
	int updateBoard(Board board);
	
	//한가지 데이터를 삭제
	int deleteBoard(Board board);

	
	
	
}
//@ 사용하여 쿼리를 만들 수 있으나 우리는 xml에 만들것임