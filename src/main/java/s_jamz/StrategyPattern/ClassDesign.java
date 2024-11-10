package s_jamz.StrategyPattern;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import s_jamz.CompositePattern.TestResultComponent;


public class ClassDesign implements EvaluationStrategy {
    @Override
    public void evaluate(File javaFile) {
        try {
            List<String> lines = Files.readAllLines(javaFile.toPath());
            // Check for class design and structure
            for (String line : lines) {
                if (line.contains("public class ChatBot")) {
                    System.out.println("Class ChatBot is correctly defined.");
                }
                if (line.contains("public class ChatBotPlatform")) {
                    System.out.println("Class ChatBotPlatform is correctly defined.");
                }
                // Add more checks as needed
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void runTests(File javaFile) {
        throw new UnsupportedOperationException("Unimplemented method 'runTests'");
    }

    @Override
    public TestResultComponent getResults() {
    
        throw new UnsupportedOperationException("Unimplemented method 'getResults'");
    }
}