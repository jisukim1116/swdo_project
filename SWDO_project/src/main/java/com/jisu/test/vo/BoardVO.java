package com.jisu.test.vo;

import lombok.Data;

@Data
public class BoardVO {
	private int board_no;
	private String board_title;
	private String user_id;
	private int board_hits;
	private String board_content;
	private String board_indate;
	private String board_original;
	private String board_saved;
}
