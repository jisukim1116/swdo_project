package com.jisu.test.autoML;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import com.google.cloud.automl.v1.AutoMlClient;
import com.google.longrunning.Operation;

public class GetOperationStatus {
	
	public static void main(String[] args)
		      throws IOException, ExecutionException, InterruptedException, TimeoutException {
		    // TODO(developer): Replace these variables before running the sample.
			String operationFullId = "projects/895850026348/locations/us-central1/operations/ICN315343508258947072";
			getOperationStatus(operationFullId);
		  }
	
	// Get the status of an operation
	  static void getOperationStatus(String operationFullId) throws IOException {
	    // Initialize client that will be used to send requests. This client only needs to be created
	    // once, and can be reused for multiple requests. After completing all of your requests, call
	    // the "close" method on the client to safely clean up any remaining background resources.
	    try (AutoMlClient client = AutoMlClient.create()) {
	      // Get the latest state of a long-running operation.
	      Operation operation = client.getOperationsClient().getOperation(operationFullId);

	      // Display operation details.
	      System.out.println("Operation details:");
	      System.out.format("\tName: %s\n", operation.getName());
	      System.out.format("\tMetadata Type Url: %s\n", operation.getMetadata().getTypeUrl());
	      System.out.format("\tDone: %s\n", operation.getDone());
	      if (operation.hasResponse()) {
	        System.out.format("\tResponse Type Url: %s\n", operation.getResponse().getTypeUrl());
	      }
	      if (operation.hasError()) {
	        System.out.println("\tResponse:");
	        System.out.format("\t\tError code: %s\n", operation.getError().getCode());
	        System.out.format("\t\tError message: %s\n", operation.getError().getMessage());
	      }
	    }
	  }
}
