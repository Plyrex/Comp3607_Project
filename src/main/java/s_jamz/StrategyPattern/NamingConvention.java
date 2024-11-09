package s_jamz.StrategyPattern;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class NamingConvention implements EvaluationStrategy {
    @Override
    public void evaluate(File javaFile) {
        try {
            List<String> lines = Files.readAllLines(javaFile.toPath());
            // Check for naming conventions
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
}