package s_jamz.StrategyPattern;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class NamingConvention implements EvaluationStrategy {
    @Override
    public void evaluate(File javaFile) {
        // Implement evaluation logic for naming conventions
        try {
            List<String> lines = Files.readAllLines(javaFile.toPath());
            for (String line : lines) {
                if (line.contains("ChatBot") || line.contains("chatBot")) {
                    System.out.println("Naming convention for ChatBot is correct.");
                }
                // Add more checks as needed
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void runTests(File javaFile) {
        // Implement logic to run JUnit tests for naming conventions
        // For example, you can use JUnitCore to run tests programmatically
        System.out.println("Running naming convention tests for " + javaFile.getName());
        // Add JUnit test execution logic here
    }
}