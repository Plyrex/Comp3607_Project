package s_jamz.StrategyPattern;


import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import s_jamz.JUnitTestExecutor;
import s_jamz.CompositePattern.TestResultComponent;
import s_jamz.CompositePattern.TestResultComposite;
import s_jamz.CompositePattern.TestResultLeaf;
import s_jamz.AutoGrader.AttributeTypeTest;


public class AttributeType implements EvaluationStrategy {
    private TestResultComponent results;
    private String studentFolderPath;

    public AttributeType(String studentFolderPath) {
        this.studentFolderPath = studentFolderPath;
    }

//path: /Users/maianeptune/Downloads/StudentSubmissions2.zip
    @Override
    public void evaluate() {
        try {
            AttributeTypeTest testClass = new AttributeTypeTest();
        
            // Print the class name
            System.out.println("Running tests for class: " + testClass.getClass().getName());


            SummaryGeneratingListener listener = JUnitTestExecutor.executeTests(testClass.getClass());
            TestExecutionSummary summary = listener.getSummary();

            // // Run JUnit tests for AttributeType
            results = runAttributeTypeTests(testClass.getClass());

        } catch (Exception e) {
            e.printStackTrace();
            results = new TestResultLeaf(0, "Failed to load test class: " + e.getMessage());
        }
    }


      @Override
    public TestResultComponent getResults() {
        return results;
    }


    private TestResultComponent runAttributeTypeTests(Class<?> testClass) {
        TestResultComposite composite = new TestResultComposite();
        try {
            
            composite.add(new TestResultLeaf(90, "Attribute type test passed")); // Placeholder result
        } catch (Exception e) {
            e.printStackTrace();
            composite.add(new TestResultLeaf(0, "Test execution failed: " + e.getMessage()));
        }
        return composite;
    }
    
}