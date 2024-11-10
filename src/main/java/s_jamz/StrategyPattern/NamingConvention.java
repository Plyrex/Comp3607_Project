package s_jamz.StrategyPattern;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import s_jamz.CompositePattern.TestResultComponent;
import s_jamz.CompositePattern.TestResultComposite;
import s_jamz.CompositePattern.TestResultLeaf;
import s_jamz.JUnitTestExecutor;

public class NamingConvention implements EvaluationStrategy {

    private TestResultComponent results;
    private String studentFolderPath;

    public NamingConvention(String studentFolderPath) {
        this.studentFolderPath = studentFolderPath;
        this.results = new TestResultComposite();
    }

    @Override
    public void evaluate(File javaFile) {
        // Implement evaluation logic if needed
    }

    @Override
    public void runTests(File javaFile) {
        try {
            // Use the studentFolderPath field if needed
            File studentDir = new File(studentFolderPath);
            if (!studentDir.exists() || !studentDir.isDirectory()) {
                throw new IllegalArgumentException("Invalid student folder path: " + studentFolderPath);
            }

            // Dynamically add the /target/test-classes directory to the classpath
            File testDir = new File(System.getProperty("user.dir") + "/target/test-classes");
            URL testURL = testDir.toURI().toURL();
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{testURL}, this.getClass().getClassLoader());
            Class<?> testClass = Class.forName("s_jamz.AutoGrader.NamingConventionsTest", true, urlClassLoader);

            // Print the class name
            System.out.println("Running tests for class: " + testClass.getName());

            // Run JUnit tests for NamingConvention using the JUnitTestExecutor
            SummaryGeneratingListener listener = JUnitTestExecutor.executeTests(testClass);

            // Calculate the score based on the test results
            int score = calculateScore(listener);
            results.add(new TestResultLeaf(score, "Test Results: " + score + " points"));

            // Print the test summary
            listener.getSummary().printTo(new PrintWriter(System.out));
        } catch (Exception e) {
            e.printStackTrace();
            results.add(new TestResultLeaf(0, "Failed to load test class: " + e.getMessage()));
        }
    }

    @Override
    public TestResultComponent getResults() {
        return results;
    }

    private int calculateScore(SummaryGeneratingListener listener) {
        long testsSucceeded = listener.getSummary().getTestsSucceededCount();
        long testsFailed = listener.getSummary().getTestsFailedCount();
        return (int) (testsSucceeded * 10 - testsFailed * 5); // Example calculation
    }
}