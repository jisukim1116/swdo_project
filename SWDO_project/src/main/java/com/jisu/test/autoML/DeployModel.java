package com.jisu.test.autoML;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.automl.v1.AutoMlClient;
import com.google.cloud.automl.v1.DeployModelRequest;
import com.google.cloud.automl.v1.ModelName;
import com.google.cloud.automl.v1.OperationMetadata;
import com.google.protobuf.Empty;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

class DeployModel {

  public static void main(String[] args)
      throws IOException, ExecutionException, InterruptedException {
    // TODO(developer): Replace these variables before running the sample.
    String projectId = "jisu-328201";
    String modelId = "ICN8027963203927408640";
    deployModel(projectId, modelId);
  }

  // Deploy a model for prediction
  static void deployModel(String projectId, String modelId)
      throws IOException, ExecutionException, InterruptedException {
    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests. After completing all of your requests, call
    // the "close" method on the client to safely clean up any remaining background resources.
    try (AutoMlClient client = AutoMlClient.create()) {
      // Get the full path of the model.
      ModelName modelFullId = ModelName.of(projectId, "us-central1", modelId);
      DeployModelRequest request =
          DeployModelRequest.newBuilder().setName(modelFullId.toString()).build();
      OperationFuture<Empty, OperationMetadata> future = client.deployModelAsync(request);

      future.get();
      System.out.println("Model deployment finished");
    }
  }
}
