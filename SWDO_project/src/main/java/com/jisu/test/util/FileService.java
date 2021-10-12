package com.jisu.test.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 관련 유틸
 * 업로드한 파일의 저장 & 서버에 저장된 파일 삭제 등의 기능 제공
 */
public class FileService {

	/**
	 * 업로드 된 파일을 지정된 경로에 저장하고, 저장된 파일명을 리턴(X) 파일의 전체경로를 리턴(O)
	 * @param mfile 업로드된 파일
	 * @param path 저장할 경로
	 * @return 저장된 파일명
	 */
	public static String saveFile(MultipartFile mfile, String uploadPath) {
		//업로드된 파일이 없거나 크기가 0이면 저장하지 않고 null을 리턴
		if (mfile == null || mfile.isEmpty() || mfile.getSize() == 0) {
			return null;
		}
		
		//저장 폴더가 없으면 생성
		File path = new File(uploadPath);   //file 객체는 자바에서 file를 다루기 위해 생성된 클래스. ex) 사과를 객체로 만들면 apple클래스가 생성되어야 한다. apple클래스와 file 클래스 같은 개념
		if (!path.isDirectory()) {  		//즉, uploadPath의 경로에 실제로 폴더가 없니? 응, 없으면 make directory 하자.
			path.mkdirs();
		}
		
		//원본 파일명 가져오기 (파일 저장 메소드는 파일을 원본 파일로 저장하지 않는다. 그러면 중복되서 누가 올렸는지 알 수가 없음) 
		String originalFilename = mfile.getOriginalFilename();
		
		//저장할 파일명을 오늘 날짜의 년월일로 생성 (목적 : 같은 파일명으로 저장하지 않기 위해)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String savedFilename = sdf.format(new Date());
		
		//원본 파일의 확장자 (상기에 날짜 이용해서 저장할 파일명을 생성한 건 좋은데, 확장자가 없으니까 이 작업을 기반으로 확장자를 붙여준다)
		String ext;
		int lastIndex = originalFilename.lastIndexOf('.');
		//확장자가 없는 경우
		if (lastIndex == -1) {
			ext = "";
		}
		//확장자가 있는 경우
		else {
			ext = "." + originalFilename.substring(lastIndex + 1);
		}

		//저장할 전체 경로를 포함한 File 객체
		File serverFile = null;
		
		//같은 이름의 파일이 있는 경우의 처리
		while (true) {
			serverFile = new File(uploadPath + "/" + savedFilename + ext);
			//같은 이름의 파일이 없으면 나감.
			if ( !serverFile.isFile()) break;	
			//같은 이름의 파일이 있으면 이름 뒤에 long 타입의 시간정보를 덧붙임.
			savedFilename = savedFilename + new Date().getTime();	
		}		
		
		
		
		//	----------------------------여기까지 파일명 결정하는 과정----------------------------------
		
		
		
		//파일 저장
		try {
			mfile.transferTo(serverFile);   //serverFile은 우리가 만들어낸 경로+파일명. mfile은 실제 파일. 이걸 저 위치와 이름으로 transfer하는 메소드.
		} catch (Exception e) {
			savedFilename = null;
			e.printStackTrace();
		}
		
		return savedFilename + ext;
		//return uploadPath + "/" + savedFilename + ext;
	}
	
	/**
	 * 서버에 저장된 파일의 전체 경로를 전달받아, 해당 파일을 삭제
	 * @param fullpath 삭제할 파일의 경로
	 * @return 삭제 여부
	 */
	public static boolean deleteFile(String fullpath) {
		//파일 삭제 여부를 리턴할 변수
		boolean result = false;
		
		//전달된 전체 경로로 File객체 생성
		File delFile = new File(fullpath);
		
		//해당 파일이 존재하면 삭제
		if (delFile.isFile()) {
			delFile.delete();
			result = true;
		}
		
		return result;
	}
}
