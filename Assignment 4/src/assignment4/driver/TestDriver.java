/* UNIT TESTING FRAMEWORK TestDriver.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Eric Tu
 * ect734
 * 17185
 * Gavin Nguyen
 * gpn235
 * 17180
 * Slip days used: <0>
 * Git URL: https://github.com/ECE422C-Shi/sp-23-assignment-4-sp23-pr4-pair-11.git
 * Spring 2023
 */

package assignment4.driver;

import assignment4.gui.TestGUI;
import assignment4.listeners.GUITestListener;
import assignment4.results.TestClassResult;
import assignment4.results.TestMethodResult;
import assignment4.runners.AlphabeticalTestRunner;
import assignment4.runners.FilteredTestRunner;
import assignment4.runners.OrderedTestRunner;
import assignment4.runners.TestRunner;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;


public class TestDriver {
    static ArrayList<TestClassResult> results = new ArrayList<>();

    public static ArrayList<String> getTestPackage(String s) { //add package name arg
        if (Objects.equals(s, "")) {
            return null;
        }
        try {
            File folder = new File("src/" + s);
            String[] listOfFiles = folder.list(); //fills with Test#.java instead of test.Test#
            for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
                listOfFiles[i] = "test." + listOfFiles[i].substring(0,listOfFiles[i].indexOf(".java"));
            }
            return new ArrayList<>(Arrays.asList(listOfFiles));
        } catch (NullPointerException e) {
            //package doesn't exist
            return null;
        }
    }

    public static void runTests(String[] testclasses/*, TextArea text, ByteArrayOutputStream baos, GridPane output*/) {

        String filteredMethods = null;

        for (String s : testclasses) {
            if (s.contains("#")) {
                filteredMethods = s.substring(s.indexOf('#')+1);
                s = s.substring(0, s.indexOf('#'));
            }

            GUITestListener listener = new GUITestListener();
            Class<?> className = null;
            try {
                className = Class.forName(s);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            Annotation[] anno = className.getAnnotations();

            if (filteredMethods != null) {          //filter runner
                List<String> testedMethods = Arrays.asList(filteredMethods.split(","));
                FilteredTestRunner runner = new FilteredTestRunner(className, testedMethods);
                runner.addListener(listener);
                results.add(runner.run());
//                text.setText(baos + "\n");
//                output.add(text, 0, 2);
                filteredMethods = null;
            }
            else if (anno.length == 0) {          //No annotations
                TestRunner runner = new TestRunner(className);
                runner.addListener(listener);
                results.add(runner.run());
//                text.setText(baos + "\n");
//                output.add(text, 0, 2);
            }
            else if (String.valueOf(anno[0]).contains("Alphabetical")) {     //alphabetical annotation
                AlphabeticalTestRunner runner = new AlphabeticalTestRunner(className);
                runner.addListener(listener);
                results.add(runner.run());
//                text.setText(baos + "\n");
//                output.add(text, 0, 2);
            } else if (String.valueOf(anno[0]).contains("Ordered")) {       //ordered annotation
                OrderedTestRunner runner = new OrderedTestRunner(className);
                runner.addListener(listener);
                results.add(runner.run());
//                text.setText(baos + "\n");
//                output.add(text, 0, 2);
            }

        }
    }

    public static void printSummary() {
        System.out.println("\nFinal Summary:");

        int testCount = 0, failCount = 0;
        HashMap<TestClassResult, ArrayList<TestMethodResult>> failures = new HashMap<>();
        for (TestClassResult r : results) {
            List<TestMethodResult> curList = r.getTestMethodResults();
            for (TestMethodResult m : curList) {
                if (m.isPass()) {
                    System.out.println(r.getTestClassName() + "." + m.getName() + " : PASS");
                } else {
                    System.out.println(r.getTestClassName() + "." + m.getName() + " : FAIL");
                    failCount++;
                    if (!failures.containsKey(r)) {
                        failures.put(r, new ArrayList<>());
                    }
                    failures.get(r).add(m);
                }
                testCount++;
            }
        }
        System.out.println("==========\nFAILURES:");

        for (Map.Entry<TestClassResult, ArrayList<TestMethodResult>> set : failures.entrySet()) {
            for (int i = 0; i < set.getValue().size(); i++) {
                System.out.println(set.getKey().getTestClassName() + "." + set.getValue().get(i).getName() + ":");
                set.getValue().get(i).getException().printStackTrace();
            }
        }

        System.out.println("==========\nTests run: " + testCount + ", Failures: " + failCount);
    }

    public static void clearResults() {
        results.clear();
    }

    public static void main(String[] args) throws Throwable {
        // Use this for your testing.  We will not be calling this method.
        String[] input1 = new String[]{"test.TestA", "test.TestB"};
        String[] input2 = new String[]{"test.TestA", "test.TestD", "test.TestE"};
        String[] input3 = new String[]{"test.TestB", "test.TestC#test4,test6"};
        String[] input4 = new String[]{"test.TestC"};
//        runTests(input3);
    }
    public static Annotation[] AnnotationsGet(String[] testclasses){
        String filteredMethods = null;
        Annotation[] anno=null;
        for (String s : testclasses) {
            if (s.contains("#")) {
                filteredMethods = s.substring(s.indexOf('#')+1);
                s = s.substring(0, s.indexOf('#'));
            }

            GUITestListener listener = new GUITestListener();
            Class<?> className = null;
            try {
                className = Class.forName(s);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            anno = className.getAnnotations();

        }
        return anno;
    }
}