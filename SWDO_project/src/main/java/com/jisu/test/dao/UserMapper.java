package com.jisu.test.dao;

import java.util.ArrayList;

import com.jisu.test.vo.UserVO;

public interface UserMapper {
	
	public int userInsert(UserVO user);
	
	public ArrayList<UserVO> userSelectAll();
	
	public int userDelete(UserVO user);
	
	public UserVO userSelectOne(UserVO user);

	public int userUpdate(UserVO user);
	
	public UserVO userLogin(UserVO user);
}
