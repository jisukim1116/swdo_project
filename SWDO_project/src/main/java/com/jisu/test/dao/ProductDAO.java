package com.jisu.test.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jisu.test.dao.ProductMapper;
import com.jisu.test.vo.ProductVO;
import com.jisu.test.vo.UserImageVO;

@Repository
public class ProductDAO {
	
	@Autowired
	private SqlSession session;
	
	private static final Logger logger = LoggerFactory.getLogger(ProductDAO.class);
	
	public int productInsert(ProductVO product) {
		
		int cnt=0;
		
		try {
			ProductMapper mapper = session.getMapper(ProductMapper.class);
			cnt = mapper.productInsert(product);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return cnt;
	}
	
	public int productDelete(String openUri) {
		int cnt = 0;
		
		try {
			ProductMapper mapper = session.getMapper(ProductMapper.class);
			cnt = mapper.productDelete(openUri);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cnt;
	}
	
	
	public ArrayList<ProductVO> productSelectAll_admin(){
		
		ArrayList<ProductVO> result = null;
		
		try {
			
			ProductMapper mapper = session.getMapper(ProductMapper.class);
			
			result = mapper.productSelectAll_admin();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int productTotalRecordsCount(String searchText) {
		
		int totalRecord = 0;
		
		try {
			ProductMapper mapper = session.getMapper(ProductMapper.class);
			totalRecord = mapper.productTotalRecordsCount(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
			return totalRecord;
		
	}
	
	public ArrayList<ProductVO> productSelectAll(String searchText, int startRecord, int countPerPage){
		
		ArrayList<ProductVO> result = null;
		
		try {
			
			ProductMapper mapper = session.getMapper(ProductMapper.class);
			
			RowBounds rb = new RowBounds(startRecord, countPerPage); //입력받은 startRecord 등 변수를 이용해서, 실제로 범위를 생성하여 해당 범위만 select하게 해주는 객체
			
			result = mapper.productSelectAll(searchText, rb);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;	
	}
	
	public int imageInsert(UserImageVO userImage) {
		
		int cnt = 0;
		
		try {
			ProductMapper mapper = session.getMapper(ProductMapper.class);
			cnt = mapper.imageInsert(userImage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cnt;
	}
	
}
