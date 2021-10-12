package com.jisu.test.autoML;

import com.google.cloud.automl.v1.AutoMlClient;
import com.google.cloud.automl.v1.DatasetName;
import com.google.protobuf.Empty;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class DeleteDataset {

	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
		// TODO Auto-generated method stub
	    String projectId = "jisu-328201";
	    String datasetId = "ICN8024205622939484160";
	    deleteDataset(projectId, datasetId);
	}
	
	  // Delete a dataset
	  static void deleteDataset(String projectId, String datasetId)
	      throws IOException, ExecutionException, InterruptedException {
	    // Initialize client that will be used to send requests. This client only needs to be created
	    // once, and can be reused for multiple requests. After completing all of your requests, call
	    // the "close" method on the client to safely clean up any remaining background resources.
	    try (AutoMlClient client = AutoMlClient.create()) {
	      // Get the full path of the dataset.
	      DatasetName datasetFullId = DatasetName.of(projectId, "us-central1", datasetId);
	      Empty response = client.deleteDatasetAsync(datasetFullId).get();
	      System.out.format("Dataset deleted. %s\n", response);
	    }
	  }
}
