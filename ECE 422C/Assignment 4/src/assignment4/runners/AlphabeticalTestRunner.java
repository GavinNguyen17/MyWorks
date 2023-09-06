package assignment4.runners;

import assignment4.results.TestClassResult;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;
import java.util.Arrays;

public class AlphabeticalTestRunner extends TestRunner {

    public AlphabeticalTestRunner(Class testClass) {
        super(testClass);
        sortMethods();
    }

    private void sortMethods() {
    	
        Arrays.sort(methods, Comparator.comparing(String::valueOf));
//        Collections.sort(methods, Comparator.comparing(String::));
    }

//    @Override
//    public TestClassResult run() {
//        return super.run();
//    }
}