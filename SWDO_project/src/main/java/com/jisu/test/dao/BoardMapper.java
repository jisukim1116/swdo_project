package com.jisu.test.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;

import com.jisu.test.vo.BoardVO;
import com.jisu.test.vo.ReplyVO;

public interface BoardMapper {
	
	public int boardInsert(BoardVO board);
	
	public ArrayList<HashMap<String, Object>> boardSelectAll(String searchText, RowBounds rb);
	
	public BoardVO boardSelectOne(BoardVO board);
	
	public void boardUpdateHits(int board_no);
	
	public int boardDelete(BoardVO board);
	
	public int boardUpdate(BoardVO board);
	
	public int boardTotalRecordsCount(String searchText);
	
	public int replyInsert(ReplyVO reply);
	
	public ArrayList<ReplyVO> replySelectAll(int board_no);
	
	public int replyDelete(int reply_no);
	
	public int replyUpdate(ReplyVO reply);
	
}
