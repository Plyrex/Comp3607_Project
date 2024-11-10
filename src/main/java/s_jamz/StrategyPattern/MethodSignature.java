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

            // Dynamically add the student's directory to the classpath
            URL studentURL = studentDir.toURI().toURL();
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{studentURL}, this.getClass().getClassLoader());
            Class<?> testClass = Class.forName("s_jamz.AutoGrader.MethodSignaturesTest", true, urlClassLoader);

            // Print the class name
            System.out.println("Running tests for class: " + testClass.getName());

            // Run JUnit tests for MethodSignature using the JUnitTestExecutor
            SummaryGeneratingListener listener = JUnitTestExecutor.executeTests(testClass);

            // Calculate the score based on the test results
            TestExecutionSummary summary = listener.getSummary();
            int score = calculateScore(javaFile.getName());

            results.add(new TestResultLeaf(score, "Test Results: " + score + " points"));

            // Print the test summary
            System.out.println("Test Results for " + javaFile.getName() + ":");
            summary.getFailures().forEach(failure -> System.out.println("Failed: " + failure.getTestIdentifier().getDisplayName()));
            System.out.println("Score: " + score + " points\n");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            results.add(new TestResultLeaf(0, "Class not found: " + e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            results.add(new TestResultLeaf(0, "Failed to load test class: " + e.getMessage()));
        }
    }

    private int calculateScore(String fileName) {
        int score = 0;
        switch (fileName) {
            case "ChatBot.java":
                score = MethodSignaturesTest.scores.getOrDefault("ChatBot", 0);
                System.out.println("Score for ChatBot class: " + score + " out of 36");
                break;
            case "ChatBotPlatform.java":
                score = MethodSignaturesTest.scores.getOrDefault("ChatBotPlatform", 0);
                System.out.println("Score for ChatBotPlatform class: " + score + " out of 20");
                break;
            case "ChatBotGenerator.java":
                score = MethodSignaturesTest.scores.getOrDefault("ChatBotGenerator", 0);
                System.out.println("Score for ChatBotGenerator class: " + score + " out of 7");
                break;
            default:
                break;
        }
        return score;
    }

    @Override
    public TestResultComponent getResults() {
        return results;
    }

    public void processStudentFolder() {
        File studentDir = new File(studentFolderPath);
        if (!studentDir.exists() || !studentDir.isDirectory()) {
            throw new IllegalArgumentException("Invalid student folder path: " + studentFolderPath);
        }

        File[] javaFiles = studentDir.listFiles((dir, name) -> name.endsWith(".java"));
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

        // System.out.println("Score for ChatBot class: " + chatBotScore + " out of 36");
        // System.out.println("Score for ChatBotPlatform class: " + chatBotPlatformScore + " out of 20");
        // System.out.println("Score for ChatBotGenerator class: " + chatBotGeneratorScore + " out of 7");

        totalScore = chatBotScore + chatBotPlatformScore + chatBotGeneratorScore;
        System.out.println("Total Score: " + totalScore + " points\n");
    }
}