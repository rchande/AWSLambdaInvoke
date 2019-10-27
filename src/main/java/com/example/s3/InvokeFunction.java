//snippet-sourcedescription:[GetBucketPolicy.java demonstrates how to get the bucket policy for an existing S3 bucket.]
//snippet-keyword:[SDK for Java 2.0]
//snippet-keyword:[Code Sample]
//snippet-service:[s3]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[]
//snippet-sourceauthor:[soo-aws]
/*
Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.

This file is licensed under the Apache License, Version 2.0 (the "License").
You may not use this file except in compliance with the License. A copy of
the License is located at

http://aws.amazon.com/apache2.0/

This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
*/
package com.example.s3;
// snippet-start:[s3.java2.get_bucket_policy.complete]
// snippet-start:[s3.java2.get_bucket_policy.import]

import software.amazon.awssdk.services.lambda.LambdaAsyncClient;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import software.amazon.awssdk.regions.Region;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

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
   public static void main(String[] args)
   {
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


      Region region = Region.US_WEST_2;
      LambdaAsyncClient l = LambdaAsyncClient.builder().region(region).build();
      CompletableFuture<InvokeResponse> invoke = l.invoke(builder -> builder
              .functionName(functionName)
              .overrideConfiguration(builder1 -> builder1.apiCallTimeout(Duration.ofMinutes(5)))
      );

   }
}
 
// snippet-end:[s3.java2.get_bucket_policy.main]
// snippet-end:[s3.java2.get_bucket_policy.complete]
