package com.jisu.test.vo;

import lombok.Data;

@Data
public class ReplyVO {
	
	private int reply_no;
	private String user_id;
	private String reply_content;
	private String reply_indate;
	private int board_no;
	
}
