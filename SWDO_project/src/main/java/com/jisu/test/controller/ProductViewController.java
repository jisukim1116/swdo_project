package com.jisu.test.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.automl.v1.AutoMlClient.ListDatasetsFixedSizeCollection;
import com.google.protobuf.ByteString;
import com.google.cloud.automl.v1.AnnotationPayload;
import com.google.cloud.automl.v1.DeployModelOperationMetadata;
import com.google.cloud.automl.v1.ExamplePayload;
import com.google.cloud.automl.v1.Image;
import com.google.cloud.automl.v1.ModelName;
import com.google.cloud.automl.v1.PredictRequest;
import com.google.cloud.automl.v1.PredictResponse;
import com.google.cloud.automl.v1.PredictionServiceClient;
import com.jisu.test.autoML.VisionClassificationCreateModel;
import com.jisu.test.service.ProductService;
import com.jisu.test.vo.ProductVO;

@RequestMapping(value = "/product")
@Controller
public class ProductViewController {
	
	@Autowired
	private ProductService service;
	
//	private static final int COUNT_PER_PAGE = 3;
	
	private static final Logger logger = LoggerFactory.getLogger(ProductViewController.class);
	
	@RequestMapping(value = "/enrollForm", method = RequestMethod.GET)
	public String moveToEnrollForm() {
		return "product/enrollForm";
	}
	
	//관리자용 제품 목록
	@RequestMapping(value = "/listForm", method = RequestMethod.GET)
	public String listForm_admin(Model model) throws IOException {
		
		ArrayList<ProductVO> result =  service.productSelectAll_admin();
		
		model.addAttribute("productList", result);
		return "product/listForm";
	}
	
	@RequestMapping(value = "/productListForm", method = RequestMethod.GET)
	public String productListForm() {
		return "product/productListForm";
	}
	
//	@RequestMapping(value = "/productListForm", method = RequestMethod.GET)
//	public String productlistForm(Model model, @RequestParam(name = "searchText", defaultValue = "")String searchText, 
//			@RequestParam(name = "currentPage", defaultValue = "1") int currentPage) throws IOException {
//		
//		int totalRecordsCount = service.productTotalRecordsCount(searchText);
//		
//		int temp=1;
//		
//		PageNavigator navi = new PageNavigator(currentPage, totalRecordsCount, COUNT_PER_PAGE, temp);
//		
//		System.out.println(navi.getTotalPageCount());
//		
//		navi.setCurrentPage(navi.getCurrentPage() + 1); 
//		
//		ArrayList<ProductVO> result = service.productSelectAll(searchText, navi.getStartRecord(), COUNT_PER_PAGE);
//		
//		System.out.println(result);
//		
//		model.addAttribute("productList", result);
//		model.addAttribute("searchText", searchText);
//		model.addAttribute("navi", navi);
//		
//		return "product/productListForm";
//	}
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(String openUri) throws IOException {
		
		service.productDelete(openUri);
		
		return "redirect:/product/listForm";
	}
	
	@RequestMapping(value = "/productSearch", method = RequestMethod.POST)
	public String productSearch(MultipartFile upload, Model model) throws IOException {
		
		logger.info("유저가 업로드한 이미지 : {}", upload);
		
		String userImagePath = service.imageInsert(upload);
		
	    String projectId = "jisu-328201";
	    String modelId = "ICN8027963203927408640";
	    String filePath = userImagePath; 
		
		Map<String, String> map = predict(projectId, modelId, filePath);
		
		model.addAttribute("PredictedClassName", map.get("PredictedClassName"));
		model.addAttribute("PredictedClassScore", map.get("PredictedClassScore"));
	    
	    return "product/userImage";
		
	}
	
	static Map<String, String> predict(String projectId, String modelId, String filePath) throws IOException {
	    // Initialize client that will be used to send requests. This client only needs to be created
	    // once, and can be reused for multiple requests. After completing all of your requests, call
	    // the "close" method on the client to safely clean up any remaining background resources.
	    
		Map<String, String> map = new HashMap<String, String>();
		
		try (PredictionServiceClient client = PredictionServiceClient.create()) {
	      // Get the full path of the model.
	      ModelName name = ModelName.of(projectId, "us-central1", modelId);
	      ByteString content = ByteString.copyFrom(Files.readAllBytes(Paths.get(filePath)));
	      Image image = Image.newBuilder().setImageBytes(content).build();
	      ExamplePayload payload = ExamplePayload.newBuilder().setImage(image).build();
	      PredictRequest predictRequest =
	          PredictRequest.newBuilder()
	              .setName(name.toString())
	              .setPayload(payload)
	              .putParams(
	                  "score_threshold", "0.1") // [0.0-1.0] Only produce results higher than this value
	              .build();

	      PredictResponse response = client.predict(predictRequest);

	      for (AnnotationPayload annotationPayload : response.getPayloadList()) {
	        System.out.format("Predicted class name: %s\n", annotationPayload.getDisplayName());
	        System.out.format(
	            "Predicted class score: %.2f\n", annotationPayload.getClassification().getScore());
	      
	        map.put("PredictedClassName", annotationPayload.getDisplayName());
	        map.put("PredictedClassScore", String.valueOf(annotationPayload.getClassification().getScore()));
	      }
	      
	      return map;
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
