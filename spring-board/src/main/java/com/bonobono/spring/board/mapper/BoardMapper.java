package com.bonobono.spring.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bonobono.spring.board.vo.Board;

@Mapper
public interface BoardMapper {
	
	Board selectBoard(int boardNo);//수정, 상세정보 등 한가지 정보를 가져올때 
	
	List<Board> selectBoardList(Map<String, Integer> map); //두가지 이상 값을 가져오지 못해서 Map을 사용  
	
	int selectBoardCount(); 
	
	int insertBoard(Board board);
	
	int updateBoard(int board);
	
	int deleteBoard(int boardNo);
	
	
}
//@ 사용하여 쿼리를 만들 수 있으나 우리는 xml에 만들것임