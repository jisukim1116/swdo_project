package com.jisu.test.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;

import com.jisu.test.vo.ProductVO;
import com.jisu.test.vo.UserImageVO;

public interface ProductMapper {
	
	public int productInsert(ProductVO product);
	public int productDelete(String openUri);
	public ArrayList<ProductVO> productSelectAll_admin();
	
	public int productTotalRecordsCount(String searchText);	
	public ArrayList<ProductVO> productSelectAll(String searchText, RowBounds rb);
	
	public int imageInsert(UserImageVO userImage);
	
}
