package com.jisu.test.autoML;

import com.google.cloud.automl.v1.AutoMlClient;
import com.google.cloud.automl.v1.ListModelsRequest;
import com.google.cloud.automl.v1.LocationName;
import com.google.cloud.automl.v1.Model;
import java.io.IOException;

public class ListModels {

	public static void main(String[] args) throws IOException {
		 // TODO(developer): Replace these variables before running the sample.
	    String projectId = "jisu-328201";
	    listModels(projectId);

	}
	
	// List the models available in the specified location
	  static void listModels(String projectId) throws IOException {
	    // Initialize client that will be used to send requests. This client only needs to be created
	    // once, and can be reused for multiple requests. After completing all of your requests, call
	    // the "close" method on the client to safely clean up any remaining background resources.
	    try (AutoMlClient client = AutoMlClient.create()) {
	      // A resource that represents Google Cloud Platform location.
	      LocationName projectLocation = LocationName.of(projectId, "us-central1");

	      // Create list models request.
	      ListModelsRequest listModelsRequest =
	          ListModelsRequest.newBuilder()
	              .setParent(projectLocation.toString())
	              .setFilter("")
	              .build();

	      // List all the models available in the region by applying filter.
	      System.out.println("List of models:");
	      for (Model model : client.listModels(listModelsRequest).iterateAll()) {
	        // Display the model information.
	        System.out.format("Model name: %s\n", model.getName());
	        // To get the model id, you have to parse it out of the `name` field. As models Ids are
	        // required for other methods.
	        // Name Format: `projects/{project_id}/locations/{location_id}/models/{model_id}`
	        String[] names = model.getName().split("/");
	        String retrievedModelId = names[names.length - 1];
	        System.out.format("Model id: %s\n", retrievedModelId);
	        System.out.format("Model display name: %s\n", model.getDisplayName());
	        System.out.println("Model create time:");
	        System.out.format("\tseconds: %s\n", model.getCreateTime().getSeconds());
	        System.out.format("\tnanos: %s\n", model.getCreateTime().getNanos());
	        System.out.format("Model deployment state: %s\n", model.getDeploymentState());
	      }
	    }
	  }

}
