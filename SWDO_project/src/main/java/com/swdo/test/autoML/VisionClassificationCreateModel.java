package com.swdo.test.autoML;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.automl.v1.AutoMlClient;
import com.google.cloud.automl.v1.ImageClassificationModelMetadata;
import com.google.cloud.automl.v1.LocationName;
import com.google.cloud.automl.v1.Model;
import com.google.cloud.automl.v1.OperationMetadata;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class VisionClassificationCreateModel {
	
	public static void main(String[] args)
		      throws IOException, ExecutionException, InterruptedException {
		    // TODO(developer): Replace these variables before running the sample.
			String projectId = "jisu-328201";
			String datasetId = "ICN1601157760634847232";
		    String displayName = "mushrooms";
		    createModel(projectId, datasetId, displayName);
		  }

		  // Create a model
		  static void createModel(String projectId, String datasetId, String displayName)
		      throws IOException, ExecutionException, InterruptedException {
		    // Initialize client that will be used to send requests. This client only needs to be created
		    // once, and can be reused for multiple requests. After completing all of your requests, call
		    // the "close" method on the client to safely clean up any remaining background resources.
		    try (AutoMlClient client = AutoMlClient.create()) {
		      // A resource that represents Google Cloud Platform location.
		      LocationName projectLocation = LocationName.of(projectId, "us-central1");
		      // Set model metadata.
		      ImageClassificationModelMetadata metadata =
		          ImageClassificationModelMetadata.newBuilder().setTrainBudgetMilliNodeHours(24000).build();
		      Model model =
		          Model.newBuilder()
		              .setDisplayName(displayName)
		              .setDatasetId(datasetId)
		              .setImageClassificationModelMetadata(metadata)
		              .build();

		      // Create a model with the model metadata in the region.
		      OperationFuture<Model, OperationMetadata> future =
		          client.createModelAsync(projectLocation, model);
		      // OperationFuture.get() will block until the model is created, which may take several hours.
		      // You can use OperationFuture.getInitialFuture to get a future representing the initial
		      // response to the request, which contains information while the operation is in progress.
		      System.out.format("Training operation name: %s\n", future.getInitialFuture().get().getName());
		      System.out.println("Training started...");
		    }
		  }
}
