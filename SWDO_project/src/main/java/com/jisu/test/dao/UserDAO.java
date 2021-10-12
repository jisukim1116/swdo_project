package com.jisu.test.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jisu.test.vo.UserVO;

@Repository
public class UserDAO {
	
	@Autowired
	private SqlSession session;
	
	public int userInsert(UserVO user) {
		int cnt = 0;
		
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			cnt = mapper.userInsert(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cnt;
	}
	
	public ArrayList<UserVO> userSelectAll(){
		
		ArrayList<UserVO> list = new ArrayList<>();
		
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			list = mapper.userSelectAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public int userDelete(UserVO user) {
		int cnt = 0;
		
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			cnt = mapper.userDelete(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cnt;
	}
	
	public UserVO userSelectOne(UserVO user) {
		UserVO result = null;
		
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			result = mapper.userSelectOne(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int userUpdate(UserVO user) {
		int cnt = 0;
		
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			cnt = mapper.userUpdate(user);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return cnt;
	}
	
	public UserVO userLogin(UserVO user) {
		UserVO result = null;

		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			result = mapper.userLogin(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
