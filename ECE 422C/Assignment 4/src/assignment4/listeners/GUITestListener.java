package assignment4.listeners;

import assignment4.results.TestMethodResult;
import assignment4.runners.TestRunner;

public class GUITestListener implements TestListener {


    // Call this method right before the test method starts running
    @Override
    public void testStarted(String testMethod) {
        System.out.println(testMethod + " has started.");
    }

    // Call this method right after the test method finished running successfully
    @Override
    public void testSucceeded(TestMethodResult testMethodResult) {
        System.out.println("TEST: SUCCESS");
    }

    // Call this method right after the test method finished running and failed
    @Override
    public void testFailed(TestMethodResult testMethodResult) {
        System.out.println("TEST: FAILURE");
        testMethodResult.getException().printStackTrace();
    }
}
