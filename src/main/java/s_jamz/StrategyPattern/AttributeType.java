package s_jamz.StrategyPattern;

import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import s_jamz.JUnitTestExecutor;
import s_jamz.CompositePattern.TestResultComponent;
import s_jamz.CompositePattern.TestResultComposite;
import s_jamz.CompositePattern.TestResultLeaf;
import s_jamz.AutoGrader.AttributeTypeTest;
import java.util.HashMap;

public class AttributeType implements EvaluationStrategy {
    private TestResultComponent results;
    private String studentFolderPath;
    private AttributeTypeTest attributeTypeTest;


    public AttributeType(String studentFolderPath) {
        this.studentFolderPath = studentFolderPath;
        this.results = new TestResultComposite();
    }

    @Override
    public void evaluate() {
        try {
            AttributeTypeTest testClass = new AttributeTypeTest();

            System.out.println("Running tests for class: " + testClass.getClass().getName());

            SummaryGeneratingListener listener = JUnitTestExecutor.executeTests(testClass.getClass());
            TestExecutionSummary summary = listener.getSummary();

            results = runAttributeTypeTests(testClass.getClass());

            // Process the summary to extract scores and feedback
            summary.getFailures().forEach(failure -> {
                results.add(new TestResultLeaf(0, failure.getException().getMessage()));
            });

            // Retrieve and store the results from AttributeTypeTest
            HashMap<String, TestResultLeaf> testResults = AttributeTypeTest.getTestResults();
            testResults.forEach((testName, result) -> {
                results.add(result);
            });

        } catch (Exception e) {
            e.printStackTrace();
            results.add(new TestResultLeaf(0, "Failed to load test class: " + e.getMessage()));
        }
    }

    @Override
    public TestResultComponent getResults() {
        return results;
    }

    private TestResultComponent runAttributeTypeTests(Class<?> testClass) {
        TestResultComposite composite = new TestResultComposite();

        final List<String> attributeTestResults = attributeTypeTest.getAttributeTypeTestResults();
        int count = 1; //test statment

        for(String result: attributeTestResults){
        try {
            composite.add(new TestResultLeaf(0, "Attribute type test passed"));
        } catch (Exception e) {
            e.printStackTrace();
            composite.add(new TestResultLeaf(studentFolderPath, 0, "Test execution failed: " + e.getMessage()));
        }
    }
        return composite;
    }

    public static HashMap<String, TestResultLeaf> getTestResults() {
        return AttributeTypeTest.getTestResults();
    }
}