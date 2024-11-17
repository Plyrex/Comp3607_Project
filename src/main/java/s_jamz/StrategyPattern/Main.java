package s_jamz.StrategyPattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import s_jamz.CompositePattern.TestResultComponent;
import s_jamz.CompositePattern.TestResultComposite;
import s_jamz.CompositePattern.TestResultLeaf;
import s_jamz.JUnitTestExecutor;
import s_jamz.AutoGrader.MainTest;


public class Main implements EvaluationStrategy {

    private TestResultComponent results;
    private List<String> feedback;

    public Main(String studentFolderPath) {
        this.results = new TestResultComposite();
        this.feedback = new ArrayList<>();
    }

    @Override
    public void evaluate() {
        try {
            MainTest testClass = new MainTest();

            // System.out.println("Running tests for class: " + testClass.getClass().getName());

            SummaryGeneratingListener listener = JUnitTestExecutor.executeTests(testClass.getClass());
            TestExecutionSummary summary = listener.getSummary();

            results = runMainTest(testClass.getClass());

            // Process the summary to extract scores and feedback
            summary.getFailures().forEach(failure -> {
                feedback.add(failure.getException().getMessage());
                results.add(new TestResultLeaf(0, failure.getException().getMessage()));
            });

             // Retrieve and store the results from MainBehaviourTest
            HashMap<String, TestResultLeaf> testResults = MainTest.getTestResults();
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

    private TestResultComponent runMainTest(Class<?> testClass) {
        TestResultComposite composite = new TestResultComposite();
        try {
            composite.add(new TestResultLeaf(0, "Main test passed"));
        } catch (Exception e) {
            e.printStackTrace();
            composite.add(new TestResultLeaf(0, "Failed to load test class: " + e.getMessage()));
        }
        return composite;
    }

    public static HashMap<String, TestResultLeaf> getTestResults() {
        return MainTest.getTestResults();
    }
}