package s_jamz.StrategyPattern;

import java.io.File;

public class StudentFolderProcessor {
    private GradingContext gradingContext;

    public StudentFolderProcessor(GradingContext gradingContext) {
        this.gradingContext = gradingContext;
    }

    public void processStudentFolder(String studentFolderPath) {
        File studentDir = new File(studentFolderPath);
        if (!studentDir.exists() || !studentDir.isDirectory()) {
            throw new IllegalArgumentException("Invalid student folder path: " + studentFolderPath);
        }

        File[] javaFiles = studentDir.listFiles((dir, name) -> name.endsWith(".java"));
        if (javaFiles != null) {
             gradingContext.evaluate();
        }
        
    }
}