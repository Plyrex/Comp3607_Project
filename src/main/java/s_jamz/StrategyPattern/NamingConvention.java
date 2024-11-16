package s_jamz.StrategyPattern;

import java.util.ArrayList;
import java.util.List;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import s_jamz.CompositePattern.TestResultComponent;
import s_jamz.CompositePattern.TestResultComposite;
import s_jamz.CompositePattern.TestResultLeaf;
import s_jamz.JUnitTestExecutor;
import s_jamz.AutoGrader.NamingConventionsTest;

public class NamingConvention implements EvaluationStrategy {

    private TestResultComponent results;
    private String studentFolderPath;
    private List<String> feedback;

    public NamingConvention(String studentFolderPath) {
        this.studentFolderPath = studentFolderPath;
        this.results = new TestResultComposite();
        this.feedback = new ArrayList<>();
    }

    @Override
    public void evaluate() {
        try {
            NamingConventionsTest testClass = new NamingConventionsTest();

            System.out.println("Running tests for class: " + testClass.getClass().getName());

            SummaryGeneratingListener listener = JUnitTestExecutor.executeTests(testClass.getClass());
            TestExecutionSummary summary = listener.getSummary();

            results = runNamingConventionsTests(testClass.getClass());

            // Process the summary to extract scores and feedback
            summary.getFailures().forEach(failure -> {
                feedback.add(failure.getException().getMessage());
                results.add(new TestResultLeaf(studentFolderPath, 0, failure.getException().getMessage()));
            });



            // results.add(new TestResultLeaf((int) successfulTests, "Number of successful tests"));
            // results.add(new TestResultLeaf((int) failedTests, "Number of failed tests"));

        } catch (Exception e) {
            e.printStackTrace();
            results.add(new TestResultLeaf(studentFolderPath, 0, "Failed to load test class: " + e.getMessage()));
        }
    }

    @Override
    public TestResultComponent getResults() {
        return results;
    }

    private TestResultComponent runNamingConventionsTests(Class<?> testClass) {
        TestResultComposite composite = new TestResultComposite();
        try {
            composite.add(new TestResultLeaf(studentFolderPath, 0, "Attribute type test passed"));
        } catch (Exception e) {
            e.printStackTrace();
            composite.add(new TestResultLeaf(studentFolderPath,0, "Failed to load test class: " + e.getMessage()));
        }
        return composite;
    }

    public List<String> getFeedback() {
        return feedback;
    }
}