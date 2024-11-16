package s_jamz.StrategyPattern;


import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.w3c.dom.Attr;

import s_jamz.JUnitTestExecutor;
import s_jamz.CompositePattern.TestResultComponent;
import s_jamz.CompositePattern.TestResultComposite;
import s_jamz.CompositePattern.TestResultLeaf;
import s_jamz.AutoGrader.AttributeTypeTest;
import java.util.List;


public class AttributeType implements EvaluationStrategy {
    private TestResultComponent results;
    private String studentFolderPath;
    private AttributeTypeTest attributeTypeTest;


    public AttributeType(String studentFolderPath) {
        this.studentFolderPath = studentFolderPath;
        this.attributeTypeTest = new AttributeTypeTest();
    }


    @Override
    public void evaluate() {
        try {
            // Print the class name to debug
            System.out.println("Running tests for class: " + attributeTypeTest.getClass().getName());

            // Run JUnit tests for AttributeType
            SummaryGeneratingListener listener = JUnitTestExecutor.executeTests(attributeTypeTest.getClass());
            TestExecutionSummary summary = listener.getSummary();
            
            // Now handle results from the summary
            results = runAttributeTypeTests(attributeTypeTest.getClass());

        } catch (Exception e) {
            e.printStackTrace();
            results = new TestResultLeaf(studentFolderPath, 0, "Failed to load test class: " + e.getMessage());
        }
    }


    @Override
    public TestResultComponent getResults() {
        return results;
    }


    private TestResultComponent runAttributeTypeTests(Class<?> testClass) {
        TestResultComposite composite = new TestResultComposite();

        final List<String> attributeTestResults = attributeTypeTest.getAttributeTypeTestResults();
        int count = 0;
        for(String result: attributeTestResults){
        try {
            System.out.println("Added " + count++);
            System.out.println(result);
            composite.add(new TestResultLeaf(studentFolderPath, 1, result)); //1 point per test passed which is also per line
        } catch (Exception e) {
            e.printStackTrace();
            composite.add(new TestResultLeaf(studentFolderPath, 0, "Test execution failed: " + e.getMessage()));
        }
    }
        return composite;
    }
    
}
