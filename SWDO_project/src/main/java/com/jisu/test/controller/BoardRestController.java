package com.jisu.test.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jisu.test.service.BoardService;
import com.jisu.test.vo.ReplyVO;

@RestController
@RequestMapping(value = "/board")
public class BoardRestController {

	@Autowired
	private BoardService service;
	
	private static final Logger logger = LoggerFactory.getLogger(BoardRestController.class);
	
	@RequestMapping(value="/replyInsert" , method = RequestMethod.POST)
	public HashMap<String, Object> replyInsert(ReplyVO reply) {
		
		logger.info("전달받은 reply 정보 : {}", reply);
		
		service.replyInsert(reply);
		
		HashMap<String, Object> map = service.replySelectAll(reply.getBoard_no());
		
		logger.info("전달받은 전체 reply 정보 : {}", map);
		
		return map;
	}
	
	@RequestMapping(value="/replyDelete" , method = RequestMethod.POST)
	public HashMap<String, Object> replyDelete(ReplyVO reply) {

		service.replyDelete(reply.getReply_no());
			
		//삭제 후에도 전체 리플을 보여주어야 한다. 
		HashMap<String, Object> map = service.replySelectAll(reply.getBoard_no());
		
		return map;
	}
	
	@RequestMapping(value="/replyUpdate" , method = RequestMethod.POST)
	public HashMap<String, Object> replyUpdate(ReplyVO reply) {

		service.replyUpdate(reply);
			
		HashMap<String, Object> map = service.replySelectAll(reply.getBoard_no());
		
		return map;
	}

}
