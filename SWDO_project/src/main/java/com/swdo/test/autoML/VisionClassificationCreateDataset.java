package com.swdo.test.autoML;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.automl.v1.AutoMlClient;
import com.google.cloud.automl.v1.ClassificationType;
import com.google.cloud.automl.v1.Dataset;
import com.google.cloud.automl.v1.ImageClassificationDatasetMetadata;
import com.google.cloud.automl.v1.LocationName;
import com.google.cloud.automl.v1.OperationMetadata;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class VisionClassificationCreateDataset {
	
	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
		// TODO Auto-generated method stub
		
		String projectId = "jisu-328201";
	    String displayName = "mushrooms";
	    createDataset(projectId, displayName);
	    
	}
	
	// Create a dataset
	  static void createDataset(String projectId, String displayName)
	      throws IOException, ExecutionException, InterruptedException {
	    // Initialize client that will be used to send requests. This client only needs to be created
	    // once, and can be reused for multiple requests. After completing all of your requests, call
	    // the "close" method on the client to safely clean up any remaining background resources.
	    try (AutoMlClient client = AutoMlClient.create()) {
	      // A resource that represents Google Cloud Platform location.
	      LocationName projectLocation = LocationName.of(projectId, "us-central1");

	      // Specify the classification type
	      // Types:
	      // MultiLabel: Multiple labels are allowed for one example.
	      // MultiClass: At most one label is allowed per example.
	      ClassificationType classificationType = ClassificationType.MULTILABEL;
	      ImageClassificationDatasetMetadata metadata =
	          ImageClassificationDatasetMetadata.newBuilder()
	              .setClassificationType(classificationType)
	              .build();
	      Dataset dataset =
	          Dataset.newBuilder()
	              .setDisplayName(displayName)
	              .setImageClassificationDatasetMetadata(metadata)
	              .build();
	      OperationFuture<Dataset, OperationMetadata> future =
	          client.createDatasetAsync(projectLocation, dataset);

	      Dataset createdDataset = future.get();

	      // Display the dataset information.
	      System.out.format("Dataset name: %s\n", createdDataset.getName());
	      // To get the dataset id, you have to parse it out of the `name` field. As dataset Ids are
	      // required for other methods.
	      // Name Form: `projects/{project_id}/locations/{location_id}/datasets/{dataset_id}`
	      String[] names = createdDataset.getName().split("/");
	      String datasetId = names[names.length - 1];
	      System.out.format("Dataset id: %s\n", datasetId);
	    }
	  }
}
