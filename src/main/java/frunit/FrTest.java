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

import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;
//import org.junit.runner.notification.RunNotifier;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
//import org.junit.runners.model.InitializationError;
//import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import frege.runtime.*;


@RunWith(Parameterized.class)
public class FrTest {

  static List<Object[]> tests;

  @Parameters(name="{0}")
  public static Collection<Object[]> getTests() {
    return FrTest.tests;
  }

  /**
   * Set the test that will be executed next time {@link FrUnit#runTests()}
   * is called
   *
   * @param tests the tests to set
   */
  public static void setTests(List<Object[]> tests) {
    FrTest.tests = tests;
  }

  private Fun1 action;

  public FrTest(String name, Fun1 action) throws NullPointerException {
    if (name == null || action == null)
      throw new NullPointerException("Both name and action must be not null");
    this.action = action;
  }

  /** A dummy object used to evaluate {@link FrTest#action} */
  private final static Object FAKE_OBJECT = new Object();

  @Test
  public void execute() throws Exception {
    Delayed.forced((this.action).apply(FrTest.FAKE_OBJECT).result());
  }

  /**
   * Run all the tests in {@link FrTest#tests}
   *
   * @return true if all the tests pass else false
   */
  public final static boolean runTests() {
    JUnitCore junit = new JUnitCore();
    junit.addListener(new FrTestRunListener());
    Result result = junit.run(FrTest.class);
    return result.getFailureCount() == 0;
    //System.out.println("Result: " + result.toString());
    //BlockJUnit4ClassRunner runner = new BlockJUnit4ClassRunner(FrTest.class);
    //junit.run(new RunNotifier());
  }

  public final static boolean runTests(List<Object[]> tests) {
    FrTest.setTests(tests);
    return FrTest.runTests();
  }
}
