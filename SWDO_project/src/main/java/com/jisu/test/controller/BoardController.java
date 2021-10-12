	package com.jisu.test.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sun.javafx.scene.KeyboardShortcutsHandler;
import com.jisu.test.util.PageNavigator;
import com.jisu.test.service.BoardService;
import com.jisu.test.vo.BoardVO;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final int COUNT_PER_PAGE = 5;
	private static final int PAGE_PER_GROUP = 5;
	
	@RequestMapping(value = "/boardListForm", method = RequestMethod.GET)
	public String moveToBoardList(Model model, @RequestParam(name= "searchText", defaultValue = "") String searchText, 
			@RequestParam(name = "currentPage", defaultValue = "1") int currentPage) {
		
		int totalRecordsCount = service.boardTotalRecordsCount(searchText);

		PageNavigator navi = new PageNavigator(currentPage, totalRecordsCount, COUNT_PER_PAGE, PAGE_PER_GROUP);

		ArrayList<HashMap<String, Object>> result = service.boardSelectAll(searchText, navi.getStartRecord(), navi.getCountPerPage());
		
		model.addAttribute("boardList", result);
		model.addAttribute("boardNavi", navi);
		model.addAttribute("searchText", searchText);
		
		return "board/boardListForm";
	}
	
	@RequestMapping(value = "/boardWriteForm", method = RequestMethod.GET)
	public String moveToBoardWriteForm() {
		return "board/boardWriteForm";
	}
	
	@RequestMapping(value = "/boardWrite", method = RequestMethod.POST)
	public String boardWrite(BoardVO board, MultipartFile upload) {
		logger.info("Controller_boardWrite_board1 : {}", board);
		service.boardInsert(board, upload);
		return "redirect:/board/boardListForm";
	}
	
	@RequestMapping(value = "/boardDetailForm", method = RequestMethod.GET)
	public String moveToboardDetailForm(int board_no, Model model) {
		
		BoardVO board = new BoardVO();
		board.setBoard_no(board_no);
		
		BoardVO result = service.boardSelectOne(board);
		
		HashMap<String, Object> map = service.replySelectAll(board.getBoard_no());
		
		model.addAttribute("boardDetail", result);
		model.addAttribute("map", map);
		
		return "board/boardDetailForm";
	}
	
	@RequestMapping(value = "boardDelete", method = RequestMethod.GET)
	public String boardDelete(BoardVO board) {
		service.boardDelete(board);
		return "redirect:/board/boardListForm";
	}
	
	@RequestMapping(value = "/boardUpdateForm", method = RequestMethod.GET)
	public String moveToBoardUpdateForm(int board_no, Model model) {
		
		BoardVO board = new BoardVO();
		board.setBoard_no(board_no);
		
		BoardVO result = service.boardSelectOne(board);
		model.addAttribute("boardDetail", result);
		return "board/boardUpdateForm";
	}
	
	@RequestMapping(value="/boardUpdate", method=RequestMethod.POST)
	public String update(BoardVO board, MultipartFile upload) {
		String path = service.boardUpdate(board, upload);
		return path;
	}
	
	@RequestMapping(value = "/download" , method = RequestMethod.GET)
	public void download(BoardVO board) { 
		service.download(board);
	}


	
}
