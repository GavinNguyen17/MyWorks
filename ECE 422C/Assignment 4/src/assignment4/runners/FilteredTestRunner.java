package assignment4.runners;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FilteredTestRunner extends TestRunner {
    List<String> testMethods;
    public List<String> allMethods;
    public FilteredTestRunner(Class testClass, List<String> testMethods) {
        super(testClass);
        allMethods = testMethods;
        this.testMethods = testMethods;
        filterMethods();
    }
    

    private void filterMethods() {
        ArrayList<Method> list = new ArrayList<>();
        for (Method m : methods) {
            if (testMethods.contains(m.getName())) {
                list.add(m);
            }
        }
        Method[] filtered = new Method[list.size()];
        for (int i = 0; i < filtered.length; i++) {
            filtered[i] = list.get(i);
        }
        methods = filtered;
    }
}