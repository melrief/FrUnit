package frunit;

import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;
//import org.junit.runner.notification.RunNotifier;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
//import org.junit.runners.model.InitializationError;
//import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import frege.runtime.*;


@RunWith(Parameterized.class)
public class FrTest {

  static class LambdaListener extends RunListener {
    public LambdaListener() {
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
      System.out.println("numTests: "   + Integer.toString(numTests)
                      + " numIgnored: " + Integer.toString(numIgnored)
                      + " numFailed: "  + Integer.toString(numFailures));
    }

    //Called before any tests have been run.
    public void testRunStarted(Description description) {
    }

    // Called when an atomic test is about to be started.
    public void testStarted(Description description) {
    }
  }
  
  static List<Object[]> tests;

  @Parameters(name="{0}")
  public static Collection<Object[]> getTests() {
    return FrTest.tests;
  }

  public static void setTests(List<Object[]> tests) {
    FrTest.tests = tests;
  }

  private Object obj;

  public FrTest(String name, Object obj) {
    this.obj = obj;
  }

  final static Object FAKE_OBJECT = new Object();

  @Test
  public void execute() throws Exception {
    if (obj != null && obj instanceof Fun1) {
      Delayed.forced(((Fun1)obj).apply(FAKE_OBJECT).result());
    } else {
      Assert.fail("Fail: object " + obj.toString() + " isn't a delayed test"
                + " (type: " + obj.getClass().toString());
    }
  }

  public final static void runTests() {
    JUnitCore junit = new JUnitCore();
    junit.addListener(new LambdaListener());
    Result result = junit.run(FrTest.class);
    //System.out.println("Result: " + result.toString());
    //BlockJUnit4ClassRunner runner = new BlockJUnit4ClassRunner(FrTest.class);
    //junit.run(new RunNotifier());
  }

  public final static void runTests(List<Object[]> tests) {
    FrTest.setTests(tests);
    FrTest.runTests();
  }

  public static class Util {
    public final static Object[] fromPair(Object a, Object b) {
      return new Object[]{ a,b };
    }
  }
}
