package com.jisu.test.service;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jisu.test.dao.ProductDAO;
import com.jisu.test.vo.ProductVO;
import com.jisu.test.util.FileService;
import com.jisu.test.vo.UserImageVO;

@Service
public class ProductService {
	
	@Autowired
	private ProductDAO dao;
	
	@Autowired
	private HttpSession session;
	
	private static final String UPLOAD_PATH = "C:/Users/kangyool/Desktop/SWDO_project/mushrooms_user"; 
	
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	public int productInsert(ProductVO product) {
		
		int cnt = dao.productInsert(product);
		
		if(cnt == 0) {
			logger.info("제품 등록 실패 : {}", product);
		}else {
			logger.info("제품 등록 성공 : {}", product);
		}
		
		return cnt;
	}
	
	public void productDelete(String openUri) {
		
		int cnt = dao.productDelete(openUri);
		
		if(cnt == 0) {
			System.out.println("제품 정보 삭제 실패");
		}else {
			System.out.println("제품 정보 삭제 성공");
		}
	}
	
	public ArrayList<ProductVO> productSelectAll_admin(){ 
		
		ArrayList<ProductVO> result = dao.productSelectAll_admin();
		
		return result;
	}
	
	public int productTotalRecordsCount(String searchText) {
		
		int totalRecord = dao.productTotalRecordsCount(searchText);
		
		return totalRecord;
		
	}
	
	public ArrayList<ProductVO> productSelectAll(String searchText, int startRecord, int countPerPage){ 
		
		ArrayList<ProductVO> result = dao.productSelectAll(searchText, startRecord, countPerPage);
		
		return result;
	}
	
	public String imageInsert(MultipartFile upload) {
		
		UserImageVO userImage = new UserImageVO();
		
		int cnt = 0;
		
		if(!upload.isEmpty()) { 
			
			userImage.setSavedFileName(FileService.saveFile(upload, UPLOAD_PATH));

			userImage.setOriginalFileName(upload.getOriginalFilename());
			
			userImage.setSavedFilePath(UPLOAD_PATH + "/" + userImage.getSavedFileName());
			
			session.setAttribute("userImage", userImage.getSavedFilePath());
			
			cnt = dao.imageInsert(userImage);
			
			if(cnt == 0) {
				logger.info("사용자 이미지 등록 실패 : {}", userImage);
			}else {
				logger.info("사용자 이미지 등록 성공 : {}", userImage);
			}
		}
		
		return userImage.getSavedFilePath();
	}
	
	
	
}
