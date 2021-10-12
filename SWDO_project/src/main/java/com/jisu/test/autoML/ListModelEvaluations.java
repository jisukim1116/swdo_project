package com.jisu.test.autoML;

import com.google.cloud.automl.v1.AutoMlClient;
import com.google.cloud.automl.v1.ListModelEvaluationsRequest;
import com.google.cloud.automl.v1.ModelEvaluation;
import com.google.cloud.automl.v1.ModelName;
import java.io.IOException;

class ListModelEvaluations {

  public static void main(String[] args) throws IOException {
    // TODO(developer): Replace these variables before running the sample.
    String projectId = "jisu-328201";
    String modelId = "ICN8027963203927408640";
    listModelEvaluations(projectId, modelId);
  }

  // List model evaluations
  static void listModelEvaluations(String projectId, String modelId) throws IOException {
    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests. After completing all of your requests, call
    // the "close" method on the client to safely clean up any remaining background resources.
    try (AutoMlClient client = AutoMlClient.create()) {
      // Get the full path of the model.
      ModelName modelFullId = ModelName.of(projectId, "us-central1", modelId);
      ListModelEvaluationsRequest modelEvaluationsrequest =
          ListModelEvaluationsRequest.newBuilder().setParent(modelFullId.toString()).build();

      // List all the model evaluations in the model by applying filter.
      System.out.println("List of model evaluations:");
      for (ModelEvaluation modelEvaluation :
          client.listModelEvaluations(modelEvaluationsrequest).iterateAll()) {

        System.out.format("Model Evaluation Name: %s\n", modelEvaluation.getName());
        System.out.format("Model Annotation Spec Id: %s", modelEvaluation.getAnnotationSpecId());
        System.out.println("Create Time:");
        System.out.format("\tseconds: %s\n", modelEvaluation.getCreateTime().getSeconds());
        System.out.format("\tnanos: %s", modelEvaluation.getCreateTime().getNanos() / 1e9);
        System.out.format(
            "Evalution Example Count: %d\n", modelEvaluation.getEvaluatedExampleCount());
        System.out.format(
            "Classification Model Evaluation Metrics: %s\n",
            modelEvaluation.getClassificationEvaluationMetrics());
      }
    }
  }
}
