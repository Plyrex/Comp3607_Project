package s_jamz;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import s_jamz.CompositePattern.TestResultComposite;
import s_jamz.StrategyPattern.AttributeType;
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


        String newDestFolder = System.getProperty("user.dir") + "/src/main/resources/StudentFolders/";
        File extractedDir = new File(newDestFolder);
        if (extractedDir.exists() && extractedDir.isDirectory()) {
            System.out.println("Processing extracted directories in: " + newDestFolder);
            for (File studentDir : extractedDir.listFiles()) {
                if (studentDir.isDirectory() && !studentDir.getName().equalsIgnoreCase("StudentSubmissions2")) {
                    fileProcessor.compileDirectory(studentDir);

                    // Run tests using the NamingConvention strategy
                    GradingContext gradingContext = new GradingContext();
                    TestResultComposite finalResults = new TestResultComposite();

                    // NamingConvention namingConvention = new NamingConvention(studentDir.getAbsolutePath());
                    // gradingContext.setStrategy(namingConvention);
                    // gradingContext.evaluate();
                    // finalResults.add(namingConvention.getResults());

                    // MethodSignature methodSignature = new MethodSignature(studentDir.getAbsolutePath());
                    // gradingContext.setStrategy(methodSignature);
                    // gradingContext.evaluate();
                    // finalResults.add(methodSignature.getResults());

                    AttributeType attributeType = new AttributeType(studentDir.getAbsolutePath());
                    gradingContext.setStrategy(attributeType);
                    gradingContext.evaluate();
                    finalResults.add(attributeType.getResults());
                    finalResults.print();

                    // System.out.println("Final Test Results for student in folder: " + studentDir.getName());
                    // int totalScore = finalResults.getScore();
                    // System.out.println("Total Score: " + totalScore + " points\n");

                    

                }
            }
        }
    }
}