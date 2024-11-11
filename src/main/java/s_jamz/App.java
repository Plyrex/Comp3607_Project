package s_jamz;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import s_jamz.StrategyPattern.GradingContext;
import s_jamz.StrategyPattern.MethodSignature;
import s_jamz.StrategyPattern.NamingConvention;
import s_jamz.TemplatePattern.FileProcessorTemplate;
import s_jamz.TemplatePattern.JavaFileProcessor;

public class App {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the directory path of the zip file");
        String zipFilePath = scanner.nextLine();
        scanner.close();

        File zipFile = new File(zipFilePath);
        if (!zipFile.exists() || !zipFile.isFile()) {
            System.out.println("Invalid zip file path: " + zipFilePath);
            return;
        }

        FileProcessorTemplate fileProcessor = new JavaFileProcessor();
        fileProcessor.processFile(zipFile);

        // Assuming the extracted files are placed in the specified directory
        String newDestFolder = System.getProperty("user.dir") + "/src/main/resources/StudentFolders/";
        File extractedDir = new File(newDestFolder);
        if (extractedDir.exists() && extractedDir.isDirectory()) {
            System.out.println("Processing extracted directories in: " + newDestFolder);
            for (File studentDir : extractedDir.listFiles()) {
                if (studentDir.isDirectory() && !studentDir.getName().equalsIgnoreCase("StudentSubmissions2")) {
                    fileProcessor.compileDirectory(studentDir);

                    // Run tests using the NamingConvention strategy
                    GradingContext gradingContext = new GradingContext();
                    NamingConvention namingConvention = new NamingConvention(studentDir.getAbsolutePath());
                    gradingContext.setStrategy(namingConvention);
                    gradingContext.processStudentFolder(studentDir.getAbsolutePath());  // Pass as String

                    // Run tests using the MethodSignature strategy
                    MethodSignature methodSignature = new MethodSignature(studentDir.getAbsolutePath());
                    gradingContext.setStrategy(methodSignature);
                    gradingContext.processStudentFolder(studentDir.getAbsolutePath());  // Pass as String

                    // Print the results for the student
                    System.out.println("Final Test Results for student in folder: " + studentDir.getName());
                    namingConvention.getResults().getResults().forEach(result -> System.out.println(result.getFeedback()));
                    int totalScoreNaming = namingConvention.getResults().getScore();
                    System.out.println("Total Score for NamingConvention: " + totalScoreNaming + " points\n");

                    methodSignature.getResults().getResults().forEach(result -> System.out.println(result.getFeedback()));
                    int totalScoreMethod = methodSignature.getResults().getScore();
                    System.out.println("Total Score for MethodSignature: " + totalScoreMethod + " points\n");

                    int totalScore = totalScoreNaming + totalScoreMethod;
                    System.out.println("Overall Total Score: " + totalScore + " points\n");
                }
            }
        }
    }
}
