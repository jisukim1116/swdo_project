package com.jisu.test.autoML;

import com.google.cloud.automl.v1.AnnotationPayload;
import com.google.cloud.automl.v1.ExamplePayload;
import com.google.cloud.automl.v1.Image;
import com.google.cloud.automl.v1.ModelName;
import com.google.cloud.automl.v1.PredictRequest;
import com.google.cloud.automl.v1.PredictResponse;
import com.google.cloud.automl.v1.PredictionServiceClient;
import com.google.protobuf.ByteString;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class VisionClassificationPredict {

  public static void main(String[] args) throws IOException {
    // TODO(developer): Replace these variables before running the sample.
    String projectId = "jisu-328201";
    
    String modelId = "ICN8027963203927408640";
    String filePath = "C:/Users/kangyool/Desktop/SWDO_project/agaricus_test2.jpg"; 

//    String modelId = "ICN6029772344258461696";
//    String filePath = "C:/Users/kangyool/Desktop/SWDO_project/rose_test.jpg"; 
    
    predict(projectId, modelId, filePath);
  }

  static void predict(String projectId, String modelId, String filePath) throws IOException {
    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests. After completing all of your requests, call
    // the "close" method on the client to safely clean up any remaining background resources.
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
      }
    }
  }
}
