package com.jisu.test.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jisu.test.controller.BoardController;
import com.jisu.test.dao.BoardDAO;
import com.jisu.test.util.FileService;
import com.jisu.test.vo.BoardVO;
import com.jisu.test.vo.ReplyVO;
import com.jisu.test.vo.UserVO;

@Service
public class BoardService {
	
	@Autowired
	private BoardDAO dao;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private HttpServletResponse response; 
	
	private static final String UPLOAD_PATH = "C:/Users/kangyool/Desktop/SWDO_project/board_files"; 
	private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
	
	public void boardInsert(BoardVO board, MultipartFile upload) {

		UserVO user = (UserVO) session.getAttribute("loginVO");
		board.setUser_id(user.getUser_id()); 

		if(!upload.isEmpty()) { //첨부파일이 비어있지 않을 때 (=있을때)
			//업로드 된 파일을 서버에 저장 후, 수정된 파일 이름 반환 
			String savedFileName = FileService.saveFile(upload, UPLOAD_PATH); //저장된 파일 이름을 savedfile 변수에 저장.
			// 원본 파일 이름 저장
			String originalFileName = upload.getOriginalFilename();
			
			//저장된 파일명칭과 원본 파일명칭을 board객체에 세팅
			board.setBoard_original(originalFileName);	
			board.setBoard_saved(savedFileName);
		}
		
		int cnt = dao.boardInsert(board);

		if(cnt == 0) {
			logger.info("글 등록 실패 : {}", board);
		}else {
			logger.info("글 등록 성공 : {}", board);
		}
	}
	
	public ArrayList<HashMap<String, Object>> boardSelectAll(String searchText, int startRecord, int countPerPage){
		
		ArrayList<HashMap<String, Object>> list = dao.boardSelectAll(searchText, startRecord, countPerPage);
		
		return list;
	} 
	
	public BoardVO boardSelectOne(BoardVO board) {	
		BoardVO result = dao.boardSelectOne(board);
		return result;
	}
	
	public void boardDelete(BoardVO board) {

		UserVO user = (UserVO) session.getAttribute("loginVO");
		board.setUser_id(user.getUser_id()); 
		
		//첨부 파일 삭제하기 위한 정보 저장
		BoardVO result = dao.boardSelectOne(board);
		
		int cnt = dao.boardDelete(board);

		if(cnt == 0) {
			logger.info("글 삭제 실패 : {}", board);
		}else {
			logger.info("글 삭제 성공 : {}", board);
			
			//첨부파일이 있을 때만 해야 한다
			if(result.getBoard_saved() != null) {	
				String fullPath = UPLOAD_PATH + "/" + result.getBoard_saved();
				FileService.deleteFile(fullPath);
			}
		}
	}
	
	public String boardUpdate(BoardVO board, MultipartFile upload) {
		
		UserVO user = (UserVO) session.getAttribute("loginVO");
		board.setUser_id(user.getUser_id());
		
		BoardVO result = dao.boardSelectOne(board);
		
		if(!upload.isEmpty()) {

			//파일삭제
			if(result.getBoard_saved() != null ) {
				String fullPath = UPLOAD_PATH + "/" + result.getBoard_saved();
				FileService.deleteFile(fullPath);
			}
			
			//파일등록
			String savedFileName = FileService.saveFile(upload, UPLOAD_PATH);
			String originalFileName = upload.getOriginalFilename();
			
			//board객체에 세팅
			board.setBoard_original(originalFileName);
			board.setBoard_saved(savedFileName);
		}
		
		int cnt = dao.boardUpdate(board);
		
		String path;
		
		if(cnt == 0) {
			System.out.println("게시글 수정 실패");
			path = "redirect:/board/boardUpdateForm?user_id=" + board.getUser_id();
		}else {
			System.out.println("게시글 수정 성공");
			path = "redirect:/board/boardListForm";
		}
		
		return path;
	}
	

	public void download(BoardVO board) {
		//전달 받은 PK로 글 정보 조회
		BoardVO result = dao.boardSelectOne(board);
		
		//원본 파일명
		String originalFileName = result.getBoard_original();
		
		//원본 파일명의 인코딩과, 다운로드할 파일의 이름을 originalFileName으로 하기 위한 코드.
		try {	
			
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(originalFileName,"UTF-8"));
		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//저장된 파일의 전체 경로를 변수에 저장
		String fullPath = UPLOAD_PATH + "/" + result.getBoard_saved(); 
		
		FileInputStream fis = null; 
		ServletOutputStream sos = null; 
		
		try {
			// 통로 열기
			fis = new FileInputStream(fullPath); 
			sos = response.getOutputStream();

			FileCopyUtils.copy(fis, sos); // 파일을 실제로 복사해서 다운로드하는 코드.
				
			// 통로 닫기 (순서대로 닫아야함. fis먼저 열었으므로 fis먼저 닫아줘야함.)
			fis.close();
			sos.close();
				
		} catch (IOException e) { 
			e.printStackTrace();
		}			
	}
	
	public int boardTotalRecordsCount(String searchText) {
		
		int totalRecord = dao.boardTotalRecordsCount(searchText);
		
		return totalRecord;
	}
	
	public void replyInsert(ReplyVO reply) {

		UserVO user = (UserVO) session.getAttribute("loginVO");
		reply.setUser_id(user.getUser_id());

		int cnt = dao.replyInsert(reply);
	
		if(cnt == 0) {
			logger.info("댓글저장 실패");
		}else {
			logger.info("댓글저장 성공");
		}
	}
	
	public HashMap<String, Object> replySelectAll(int board_no){
		ArrayList<ReplyVO> result = dao.replySelectAll(board_no);
		
		//차후, javascript 영역에서 세션의 로그인 정보를 사용하기 위함. html에선 sessionScope.loginVO.user_id로 사용 가능
		UserVO loginVO = (UserVO) session.getAttribute("loginVO");
		
		String user_id;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if (loginVO != null) {
			user_id = loginVO.getUser_id();
			map.put("user_id", user_id);
		}
		
		map.put("replyList", result);   
		
		return map;
	}
	
	public void replyDelete(int reply_no) {
		
		int cnt = dao.replyDelete(reply_no);
		
		if (cnt == 0 ) {
			logger.info("댓글 삭제 실패");
		}
		else {
			logger.info("댓글 삭제 성공");
		}
	}
	
	public void replyUpdate(ReplyVO reply) {
		int cnt = dao.replyUpdate(reply);
		
		if (cnt == 0 ) {
			logger.info("댓글 수정 실패");
		}
		else {
			logger.info("댓글 수정 성공");
		}
	}
	
}
