package com.bonobono.spring.board.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bonobono.spring.board.mapper.BoardMapper;
import com.bonobono.spring.board.vo.Board;
import com.bonobono.spring.board.vo.BoardRequest;
import com.bonobono.spring.board.vo.Boardfile;

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
	//글 등록화면에서 파일업로드 추가 vo,Mapper를 추가하고 수정
	public void insertBoard(BoardRequest boardRequest, String path) {
		/*
		 * 1.boardRequest를 분리 : board, file, File정보 
		 * 2.board -> boardVo 
		 * 3.file정보 -> boardFileVo
		 * 4.file -> +path를 이용해 물리적 장치 저장
		 */
		
		//1.
		Board board = new Board();
		board.setBoardTitle(boardRequest.getBoardTitle());
		//board.getBoardNo()
		boardMapper.insertBoard(board);  //insert쿼리 방금 입력한 데이터값을 가져오는 쿼리 (자동키생성) 카페 1280
		//2
		List<MultipartFile> files = boardRequest.getFiles();
		for(MultipartFile f : files) {
			//f -> boardfile
			Boardfile boardfile = new Boardfile();
			boardfile.setBoardNo(board.getBoardNo());
			boardfile.setFileSize(f.getSize());
			boardfile.setFileType(f.getContentType());
			String originalFilename = f.getOriginalFilename();
			int i = originalFilename.lastIndexOf(".");
			String ext = originalFilename.substring(i+1); //마지막 . 뒤의 글씨를 자르는것
			boardfile.setFileExt(ext);
			String fileName = UUID.randomUUID().toString();  //UUID 16진수 글자를 만들어주는것 
			boardfile.setFileName(fileName);
			//전체작업이 롤백되면 파일삭제작업은 직접 trychach문
			
			//3. 파일저장
			try {
				f.transferTo(new File(path+"/"+fileName+"."+ext));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	  //boardFileMapper.insertBoardFile(boardFile);  //두개 중 하나라도 실패하면 트렌젝션 롤백
	}
	
	
	//삭제할 boardNo값을 담고 deleteBoard를 호출하는 메서드
	public int removeBoard(Board board) {
		return boardMapper.deleteBoard(board);
		
	}
	
	//업데이트할 값들을 담은 board 참조변수의 주소를 가지고 updateBoard를 호출하는 메서드
	public int modifyBoard(Board board) {
		System.out.println("modifyBoard 실행, Service");
		return boardMapper.updateBoard(board);
		
	}
	

		

}
