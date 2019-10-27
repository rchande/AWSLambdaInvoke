
package com.example.s3;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.services.lambda.LambdaAsyncClient;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import software.amazon.awssdk.regions.Region;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

// snippet-end:[s3.java2.get_bucket_policy.import]
/**
* Get the bucket policy from an existing S3 bucket.
*
* This code expects that you have AWS credentials set up per:
* http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
*/
// snippet-start:[s3.java2.get_bucket_policy.main]
public class InvokeFunction
{
   public static void main(String[] args) throws ExecutionException, InterruptedException {
      final String USAGE = "\n" +
         "Usage:\n" +
         "    InvokeFunction <function> <timeout>\n\n" +
         "Where:\n" +
         "    bucket - the name of the function in US_WEST2 to invoke\n\n" +
         "    timeout - timeout in minutes.\n\n" +
         "Example:\n" +
         "    InvokeFunction testfunction 1\n\n";

      if (args.length < 1) {
         System.out.println(USAGE);
         System.exit(1);
      }

      String functionName = args[0];
      int timeout = Integer.parseInt(args[1]);

      try {
         Region region = Region.US_WEST_2;
         LambdaAsyncClient l = LambdaAsyncClient.builder().region(region)
                 .httpClientBuilder(NettyNioAsyncHttpClient.builder()
                         .connectionTimeout(Duration.ofMinutes(15))
                         .readTimeout(Duration.ofMinutes(15))

                         .maxConcurrency(100)
                         .maxPendingConnectionAcquires(10_000))

                 .build();
         CompletableFuture<InvokeResponse> invoke = l.invoke(builder -> builder
                 .functionName(functionName)
                 .overrideConfiguration(builder1 -> builder1.apiCallTimeout(Duration.ofMinutes(15))
                         .apiCallAttemptTimeout(Duration.ofMinutes(15))));


         InvokeResponse response = invoke.get();
         System.out.println(response.statusCode());
         System.out.println(response.functionError());
         System.out.println(response.payload().asUtf8String());
      }
      catch (Exception e)
      {
         System.out.println(e.getMessage());
      }
      return;


   }
}
 
// snippet-end:[s3.java2.get_bucket_policy.main]
// snippet-end:[s3.java2.get_bucket_policy.complete]
