package com.jisu.test.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jisu.test.service.BoardService;
import com.jisu.test.vo.BoardVO;
import com.jisu.test.vo.ReplyVO;

@Repository
public class BoardDAO {
	
	@Autowired
	private SqlSession session;
	
	private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
	
	public int boardInsert(BoardVO board) {
		
		int cnt=0;
		
		try {
			BoardMapper mapper = session.getMapper(BoardMapper.class);
			cnt = mapper.boardInsert(board);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	public ArrayList<HashMap<String, Object>> boardSelectAll(String searchText, int startRecord, int countPerPage){
		
		ArrayList<HashMap<String, Object>> list = null;
		
		try {
			BoardMapper mapper = session.getMapper(BoardMapper.class);
			
			RowBounds rb = new RowBounds(startRecord, countPerPage);	//입력받은 startRecord 등 변수를 이용해서, 실제로 범위를 생성하여 해당 범위만 select하게 해주는 객체
			
			list = mapper.boardSelectAll(searchText, rb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	public BoardVO boardSelectOne(BoardVO board) {
		BoardVO result = null;

		try {
			BoardMapper mapper = session.getMapper(BoardMapper.class);
			mapper.boardUpdateHits(board.getBoard_no());
			result = mapper.boardSelectOne(board);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public int boardDelete(BoardVO board) {
		
		int cnt=0;
		
		try {
			BoardMapper mapper = session.getMapper(BoardMapper.class);
			cnt = mapper.boardDelete(board);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	public int boardUpdate(BoardVO board) {
		int cnt = 0;
		
		try {
			BoardMapper mapper = session.getMapper(BoardMapper.class);
			cnt = mapper.boardUpdate(board);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cnt;
	}
	
	public int boardTotalRecordsCount(String searchText) {
		int totalRecord = 0;
		
		try {
			BoardMapper mapper = session.getMapper(BoardMapper.class);
			totalRecord = mapper.boardTotalRecordsCount(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return totalRecord;
	}

	public int replyInsert(ReplyVO reply) {
		int cnt = 0;
		
		try {
			BoardMapper mapper = session.getMapper(BoardMapper.class);
			cnt = mapper.replyInsert(reply);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cnt;
	}
	
	public ArrayList<ReplyVO> replySelectAll(int board_no){
		
		ArrayList<ReplyVO> list = null;
		
		try {
			BoardMapper mapper = session.getMapper(BoardMapper.class);
			list = mapper.replySelectAll(board_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;	
	}
	
	public int replyDelete(int reply_no) {
		int cnt = 0;
		try {
			BoardMapper mapper = session.getMapper(BoardMapper.class);
			cnt = mapper.replyDelete(reply_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cnt;
	}
	
	public int replyUpdate(ReplyVO reply) {
		int cnt = 0;
		
		try {
			BoardMapper mapper = session.getMapper(BoardMapper.class);
			cnt = mapper.replyUpdate(reply);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cnt;
	}
}
