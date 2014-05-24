/* Licensed to the Apache Software Foundation (ASF) under one
   or more contributor license agreements.  See the NOTICE file
   distributed with this work for additional information
   regarding copyright ownership.  The ASF licenses this file
   to you under the Apache License, Version 2.0 (the
   "License"); you may not use this file except in compliance
   with the License.  You may obtain a copy of the License at
   
     http://www.apache.org/licenses/LICENSE-2.0
   
   Unless required by applicable law or agreed to in writing,
   software distributed under the License is distributed on an
   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
   KIND, either express or implied.  See the License for the
   specific language governing permissions and limitations
   under the License.
*/
package frunit;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.Result;

public class FrTestRunListener extends RunListener {
  public FrTestRunListener() {
    super();
  }

  // Called when an atomic test flags that it assumes a condition that is false
  public void testAssumptionFailure(Failure failure) {
    //System.out.println(failure.getMessage());
  }

  // Called when an atomic test fails.
  public void testFailure(Failure failure) {
    //System.out.println(failure.getMessage());
    //System.out.println(failure.getTrace());
  }

  // Called when an atomic test has finished, whether the test succeeds or fails.
  public void testFinished(Description description) {
    //System.out.println(description.toString());
  }

  // Called when a test will not be run, generally because a test method is annotated with Ignore.
  public void testIgnored(Description description) {
  }

  // Called when all tests have finished
  public void testRunFinished(Result result) {
    final int numFailures = result.getFailureCount();
    if (numFailures > 0) {
      for (Failure failure : result.getFailures()) {
        Description desc = failure.getDescription();
        String msg = failure.getMessage();
        String trace = failure.getTrace();
        System.out.print("  - Test " + desc.getDisplayName() + " failed");
        if (msg != null) {
          System.out.println(":\n\t" + msg);
        } else {
          System.out.println();
        }
      }
      System.out.println();
    }

    final int numIgnored = result.getIgnoreCount();
    final int numTests = result.getRunCount();
    System.out.println("Cases: "   + Integer.toString(numTests)
                    + " Ignored: " + Integer.toString(numIgnored)
                    + " Failures: "  + Integer.toString(numFailures));
  }

  //Called before any tests have been run.
  public void testRunStarted(Description description) {
  }

  // Called when an atomic test is about to be started.
  public void testStarted(Description description) {
  }
}
