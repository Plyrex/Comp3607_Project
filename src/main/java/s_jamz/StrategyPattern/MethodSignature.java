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
import s_jamz.AutoGrader.MethodSignaturesTest;

public class MethodSignature implements EvaluationStrategy {

    private TestResultComponent results;
    private String studentFolderPath;

    public MethodSignature(String studentFolderPath) {
        this.studentFolderPath = studentFolderPath;
        this.results = new TestResultComposite();
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

            int score = calculateScore(javaFile.getName());
            results.add(new TestResultLeaf(score, "Test Results: " + score + " points"));

            printTestSummary(javaFile, summary, score);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            results.add(new TestResultLeaf(0, "Class not found: " + e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            results.add(new TestResultLeaf(0, "Failed to load test class: " + e.getMessage()));
        }
    }

    private URLClassLoader createClassLoader(File javaFile) throws Exception {
        File studentDir = javaFile.getParentFile();
        URL studentURL = studentDir.toURI().toURL();
        return new URLClassLoader(new URL[]{studentURL}, this.getClass().getClassLoader());
    }

    private void printTestSummary(File javaFile, TestExecutionSummary summary, int score) {
        System.out.println("Test Results for " + javaFile.getName() + ":");
        summary.getFailures().forEach(failure -> System.out.println("Failed: " + failure.getTestIdentifier().getDisplayName()));
        System.out.println("Score: " + score + " points\n");
    }

    private int calculateScore(String fileName) {
        return MethodSignaturesTest.scores.getOrDefault(fileName.replace(".java", ""), 0);
    }

    public void processStudentFolder() {
        File folder = new File(studentFolderPath);
        File[] javaFiles = folder.listFiles((dir, name) -> name.endsWith(".java"));
        if (javaFiles != null) {
            for (File javaFile : javaFiles) {
                runTests(javaFile);
            }
        }

        // Print the final results for the student
        System.out.println("Final Test Results for student in folder: " + studentFolderPath);
        int totalScore = 0;
        int chatBotScore = MethodSignaturesTest.scores.getOrDefault("ChatBot", 0);
        int chatBotPlatformScore = MethodSignaturesTest.scores.getOrDefault("ChatBotPlatform", 0);
        int chatBotGeneratorScore = MethodSignaturesTest.scores.getOrDefault("ChatBotGenerator", 0);

        totalScore = chatBotScore + chatBotPlatformScore + chatBotGeneratorScore;
        System.out.println("Total Score: " + totalScore + " points\n");
    }

    @Override
    public TestResultComponent getResults() {
        return results;
    }
}