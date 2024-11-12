package s_jamz.StrategyPattern;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import s_jamz.CompositePattern.TestResultComponent;
import s_jamz.CompositePattern.TestResultComposite;
import s_jamz.CompositePattern.TestResultLeaf;
import s_jamz.JUnitTestExecutor;
import s_jamz.AutoGrader.MethodSignaturesTest;

public class MethodSignature implements EvaluationStrategy {

    private TestResultComponent results;
    private String studentFolderPath;
    private List<String> feedback;

    public MethodSignature(String studentFolderPath) {
        this.studentFolderPath = studentFolderPath;
        this.results = new TestResultComposite();
        this.feedback = new ArrayList<>();
    }

    @Override
    public void evaluate(File javaFile) {
        // Implement method signature checks or other related logic if needed
    }

    @Override
    public void runTests(File javaFile) {
        try {
            URLClassLoader urlClassLoader = createClassLoader(javaFile);
            Class<?> testClass = Class.forName("s_jamz.AutoGrader.MethodSignaturesTest", true, urlClassLoader);

            System.out.println("Running tests for class: " + testClass.getName());

            SummaryGeneratingListener listener = JUnitTestExecutor.executeTests(testClass);
            TestExecutionSummary summary = listener.getSummary();

            double score = calculateScore(javaFile.getName());
            results.add(new TestResultLeaf((int) score, "Test Results: " + score + " points"));

            printTestSummary(javaFile, summary, score);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            results.add(new TestResultLeaf(0, "Class not found: " + e.getMessage()));
            feedback.add("Class not found: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            results.add(new TestResultLeaf(0, "Failed to load test class: " + e.getMessage()));
            feedback.add("Failed to load test class: " + e.getMessage());
        }
    }

    private URLClassLoader createClassLoader(File javaFile) throws Exception {
        File studentDir = javaFile.getParentFile();
        URL studentURL = studentDir.toURI().toURL();
        return new URLClassLoader(new URL[]{studentURL}, this.getClass().getClassLoader());
    }

    private void printTestSummary(File javaFile, TestExecutionSummary summary, double score) {
        System.out.println("Test Results for " + javaFile.getName() + ":");
        summary.getFailures().forEach(failure -> {
            String feedbackMessage = "Failed: " + failure.getTestIdentifier().getDisplayName() + " - " + failure.getException().getMessage();
            System.out.println(feedbackMessage );
            feedback.add(feedbackMessage);
        });
        feedback.add("Score: " + score + " points\n");

        // Add detailed feedback from MethodSignaturesTest
        String className = javaFile.getName().replace(".java", "");
        List<String> classFeedback = MethodSignaturesTest.feedback.getOrDefault(className, new ArrayList<>());
        feedback.addAll(classFeedback);
    }

    private double calculateScore(String fileName) {
        return MethodSignaturesTest.scores.getOrDefault(fileName.replace(".java", ""), 0.0);
    }

    @Override
    public TestResultComponent getResults() {
        return results;
    }

    public List<String> getFeedback() {
        return feedback;
    }
}