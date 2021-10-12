package com.jisu.test.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jisu.test.service.ProductService;
import com.jisu.test.util.PageNavigator;
import com.jisu.test.vo.ProductVO;

@RestController
@RequestMapping(value = "/product")
public class ProductRestController {
	
	@Autowired
	private ProductService service;
	
	private static final int COUNT_PER_PAGE = 8;
	
	private static final Logger logger = LoggerFactory.getLogger(ProductRestController.class);
	
	
	@RequestMapping(value = "/enroll", method = RequestMethod.POST)
	public int enroll(ProductVO product) throws IOException {
			
		int cnt = service.productInsert(product);
		
		return cnt;
	}
	
	@RequestMapping(value="/productList" , method = RequestMethod.GET)
	public HashMap<String, Object> productList(@RequestParam(name = "searchText", defaultValue = "")String searchText, 
			@RequestParam(name = "currentPage", defaultValue = "1") int currentPage) {
		
		HashMap<String, Object> map = new HashMap<>();
		
		int totalRecordsCount = service.productTotalRecordsCount(searchText);
		
		int temp=1; //PageNavigator를 매개변수를 다르게 해서 클래스를 두 번 생성하지 않기 위함
		
		PageNavigator navi = new PageNavigator(currentPage, totalRecordsCount, COUNT_PER_PAGE, temp);
		
		navi.setCurrentPage(navi.getCurrentPage() + 1);

		ArrayList<ProductVO> result = service.productSelectAll(searchText, navi.getStartRecord(), COUNT_PER_PAGE);
		
		map.put("productList", result);
		map.put("navi", navi);
		map.put("searchText", searchText);
		
		return map;
	}

}
