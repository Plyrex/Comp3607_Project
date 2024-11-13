package s_jamz.StrategyPattern;

import java.io.File;
import s_jamz.CompositePattern.TestResultComponent;

public class GradingContext {
    private EvaluationStrategy strategy;

    public void setStrategy(EvaluationStrategy strategy) {
        this.strategy = strategy;
    }

    public void evaluate(File javaFile) {
        strategy.evaluate(javaFile);
    }

    public void runTests() {
        strategy.runTests();
    }

    public void processStudentFolder(String studentFolderPath) {
        File studentDir = new File(studentFolderPath);
        if (!studentDir.exists() || !studentDir.isDirectory()) {
            throw new IllegalArgumentException("Invalid student folder path: " + studentFolderPath);
        }

        File[] javaFiles = studentDir.listFiles((dir, name) -> name.endsWith(".java"));
        if (javaFiles != null) {
            for (File javaFile : javaFiles) {
                runTests();
            }
        }

        printResults();
    }

    public void printResults() {
        TestResultComponent results = strategy.getResults();
        System.out.println("Final Test Results:");
        results.print();
    }
}
