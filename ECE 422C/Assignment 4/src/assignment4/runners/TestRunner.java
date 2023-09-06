package assignment4.runners;

import assignment4.assertions.AssertionException;
import assignment4.listeners.TestListener;
import assignment4.results.TestClassResult;
import assignment4.results.TestMethodResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestRunner {
    Class<?> testClass;
    public Method[] methods;
    TestListener myListener;

    public TestRunner(Class testClass) {
        this.testClass = testClass;
        methods = testClass.getDeclaredMethods();
        checkTestAnno();
    }

    public TestClassResult run() {
        TestClassResult classResult = new TestClassResult(testClass.getName());
        for (Method m : methods) {
            TestMethodResult methodResult = null;
            try {
                myListener.testStarted(testClass.getName() + "." + m.getName());
                Object instance = testClass.newInstance();
                m.invoke(instance);
                methodResult = new TestMethodResult(m.getName(), true, null);
                myListener.testSucceeded(methodResult);
            } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
                Throwable underlying = e.getCause();
                methodResult = new TestMethodResult(m.getName(), false, (AssertionException) underlying);
                myListener.testFailed(methodResult);
            } catch (AssertionException e) {
                methodResult = new TestMethodResult(m.getName(), false, e);
                myListener.testFailed(methodResult);
            } finally {
                classResult.addTestMethodResult(methodResult);
            }
        }

        return classResult;
    }

    private void checkTestAnno() {
        int nullCount = 0;
        for (int k = 0; k < methods.length; k++) {
            if (methods[k].getAnnotation(assignment4.annotations.Test.class) == null) {
                methods[k] = null;
                nullCount++;
            }
        }
        Method[] filteredMethods = new Method[methods.length - nullCount];
        int i = 0, j = 0;
        while (j < filteredMethods.length) {
            if (methods[i] != null) {
                filteredMethods[j] = methods[i];
                j++;
            }
            i++;
        }
        methods = filteredMethods;
    }

    public void addListener(TestListener listener) {
        myListener = listener;
    }
}