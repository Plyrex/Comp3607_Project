package s_jamz.StrategyPattern;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
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

    public NamingConvention(String studentFolderPath) {
        this.studentFolderPath = studentFolderPath;
        this.results = new TestResultComposite();
    }

    @Override
    public void evaluate(File javaFile) {
        // Implement naming convention checks or other related logic if needed
    }

    @Override
    public void runTests() {
        try {
            NamingConventionsTest testClass = new NamingConventionsTest();

            System.out.println("Running tests for class: " + testClass.getClass().getName());

            SummaryGeneratingListener listener = JUnitTestExecutor.executeTests(testClass.getClass());
            TestExecutionSummary summary = listener.getSummary();

            int score = calculateScore(testClass.getClass().getName());
            results.add(new TestResultLeaf(score, "Test Results: " + score + " points"));

            printTestSummary(testClass.getClass(), summary, score);
        }  catch (Exception e) {
            e.printStackTrace();
            results.add(new TestResultLeaf(0, "Failed to load test class: " + e.getMessage()));
        }
    }

    private URLClassLoader createClassLoader(File javaFile) throws Exception {
        File studentDir = javaFile.getParentFile();
        URL studentURL = studentDir.toURI().toURL();
        return new URLClassLoader(new URL[]{studentURL}, this.getClass().getClassLoader());
    }

    private void printTestSummary(Class<?> class1, TestExecutionSummary summary, int score) {
        System.out.println("Test Results for " + class1.getName() + ":");
        summary.getFailures().forEach(failure -> System.out.println("Failed: " + failure.getTestIdentifier().getDisplayName()));
        System.out.println("Score: " + score + " points\n");
    }

    private int calculateScore(String fileName) {
        return NamingConventionsTest.scores.getOrDefault(fileName.replace(".java", ""), 0);
    }

    @Override
    public TestResultComponent getResults() {
        return results;
    }
}
