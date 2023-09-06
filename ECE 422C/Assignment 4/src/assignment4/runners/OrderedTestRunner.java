package assignment4.runners;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class OrderedTestRunner extends TestRunner {

    public OrderedTestRunner(Class testClass) {
        super(testClass);
        sortMethods();
    }

    private void sortMethods() {
        TreeMap<Integer, ArrayList<Method>> map = new TreeMap<>();
        for (Method m : methods) {
            Integer orderAnno = null;
            try {
                orderAnno = m.getAnnotation(assignment4.annotations.Order.class).value();
            } catch (NullPointerException e) {
                orderAnno = Integer.MAX_VALUE;
            } finally {
                if (map.containsKey(orderAnno)) {
                    map.get(orderAnno).add(m);
                } else {
                    ArrayList<Method> temp = new ArrayList<>();
                    temp.add(m);
                    map.put(orderAnno, temp);
                }
            }
        }
        Method[] newMethods = new Method[methods.length];
        int i = 0;
        for (Map.Entry<Integer, ArrayList<Method>> set : map.entrySet()) {
            ArrayList<Method> tempList = set.getValue();
            for (Method m : tempList) {
                newMethods[i] = m;
                i++;
            }
        }
        methods = newMethods;
    }


//    @Override
//    public TestClassResult run() {
//        return super.run();
//    }
}