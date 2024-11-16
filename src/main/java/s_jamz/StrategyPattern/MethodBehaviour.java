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
import s_jamz.AutoGrader.MethodBehaviourTest;
import s_jamz.AutoGrader.NamingConventionsTest;

public class MethodBehaviour implements EvaluationStrategy {

    private TestResultComponent results;
    private String studentFolderPath;
    private List<String> feedback;

    public MethodBehaviour(String studentFolderPath) {
        this.studentFolderPath = studentFolderPath;
        this.results = new TestResultComposite();
        this.feedback = new ArrayList<>();
    }

    @Override
    public void evaluate() {
        try {
            MethodBehaviourTest testClass = new MethodBehaviourTest();

            System.out.println("Running tests for class: " + testClass.getClass().getName());

            SummaryGeneratingListener listener = JUnitTestExecutor.executeTests(testClass.getClass());
            TestExecutionSummary summary = listener.getSummary();

            results = runMethodBehaviourTests(testClass.getClass());

            // Process the summary to extract scores and feedback
            summary.getFailures().forEach(failure -> {
                feedback.add(failure.getException().getMessage());
                results.add(new TestResultLeaf(0, failure.getException().getMessage()));
            });

             // Retrieve and store the results from MethodBehaviourTest
            HashMap<String, TestResultLeaf> testResults = MethodBehaviourTest.getTestResults();
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

    private TestResultComponent runMethodBehaviourTests(Class<?> testClass) {
        TestResultComposite composite = new TestResultComposite();
        try {
            composite.add(new TestResultLeaf(0, "Method signature test passed"));
        } catch (Exception e) {
            e.printStackTrace();
            composite.add(new TestResultLeaf(0, "Failed to load test class: " + e.getMessage()));
        }
        return composite;
    }

    public static HashMap<String, TestResultLeaf> getTestResults() {
        return MethodBehaviourTest.getTestResults();
    }
}