package com.jisu.test.autoML;

import com.google.cloud.automl.v1.AutoMlClient;
import com.google.cloud.automl.v1.ModelName;
import com.google.protobuf.Empty;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

class DeleteModel {

  public static void main(String[] args)
      throws IOException, ExecutionException, InterruptedException {
    // TODO(developer): Replace these variables before running the sample.
    String projectId = "jisu-328201";
    String modelId = "ICN6646202543254798336";
    deleteModel(projectId, modelId);
  }

  // Delete a model
  static void deleteModel(String projectId, String modelId)
      throws IOException, ExecutionException, InterruptedException {
    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests. After completing all of your requests, call
    // the "close" method on the client to safely clean up any remaining background resources.
    try (AutoMlClient client = AutoMlClient.create()) {
      // Get the full path of the model.
      ModelName modelFullId = ModelName.of(projectId, "us-central1", modelId);

      // Delete a model.
      Empty response = client.deleteModelAsync(modelFullId).get();

      System.out.println("Model deletion started...");
      System.out.println(String.format("Model deleted. %s", response));
    }
  }
}
