package s_jamz;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import s_jamz.CompositePattern.TestResultComposite;
import s_jamz.CompositePattern.TestResultLeaf;
import s_jamz.StrategyPattern.AttributeType;
import s_jamz.StrategyPattern.GradingContext;
import s_jamz.StrategyPattern.MethodBehaviour;
import s_jamz.StrategyPattern.MethodSignature;
import s_jamz.StrategyPattern.NamingConvention;
import s_jamz.StrategyPattern.Main;
import s_jamz.TemplatePattern.FileProcessorTemplate;
import s_jamz.TemplatePattern.JavaFileProcessor;

public class App {
    public static void main(String[] args) throws IOException {
        HashMap<String, TestResultLeaf> results= new HashMap<String, TestResultLeaf>();

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


                    //Method Behaviour
                    MethodBehaviour methodBehaviour = new MethodBehaviour(studentDir.getAbsolutePath());
                    gradingContext.setStrategy(methodBehaviour);
                    gradingContext.evaluate();

                    finalResults.add(methodBehaviour.getResults());
                    printTestResults(MethodBehaviour.getTestResults());
                    results.putAll(MethodBehaviour.getTestResults());
            

               

                    //Method Signatures
                    MethodSignature methodSignature = new MethodSignature(studentDir.getAbsolutePath());
                    gradingContext.setStrategy(methodSignature);
                    gradingContext.evaluate();

                    finalResults.add(methodSignature.getResults());
                    results.putAll(MethodSignature.getTestResults());
                    printTestResults(MethodSignature.getTestResults());
               

                    //Attributes
                    AttributeType attributeType = new AttributeType(studentDir.getAbsolutePath());
                    gradingContext.setStrategy(attributeType);
                    gradingContext.evaluate();

                    finalResults.add(attributeType.getResults());
                    results.putAll(AttributeType.getTestResults());
                    printTestResults(AttributeType.getTestResults());
               
                    //Main
                    Main mainTest = new Main(studentDir.getAbsolutePath());
                    gradingContext.setStrategy(mainTest);
                    gradingContext.evaluate();

                    finalResults.add(mainTest.getResults());
                    results.putAll(Main.getTestResults());
                    printTestResults(Main.getTestResults());


                   


                    // System.out.println("Final Test Results for student in folder: " + studentDir.getName());
                    // int totalScore = finalResults.getScore();
                    // System.out.println("Total Score: " + totalScore + " points\n");

                    // System.out.println("Final Test Results for student in folder: " + studentDir.getName());
                    // int totalScore = finalResults.getScore();
                    // System.out.println("Total Score: " + totalScore + " points\n");

                    // int totalScore = totalScoreNaming + totalScoreMethod;
                    // System.out.println("Overall Total Score: " + totalScore + " points\n");

                    // Clear the static maps after running the tests
                    // MethodSignaturesTest.scores.clear();
                    // MethodSignaturesTest.feedback.clear();
                   

                    // System.out.println("\n\n\n ////////////////////////////////////\n supposed to have all");
                    // System.out.println(Arrays.asList(Results));
              
                }
                System.out.print("PDF Generating for "+studentDir.getName()+"\n");
                PDFGenerator pdf= new PDFGenerator();
                pdf.generatePDF(studentDir, MethodBehaviour.getTestResults(), NamingConvention.getTestResults(), MethodSignature.getTestResults(), AttributeType.getTestResults());
                System.out.print("\nPDF Generated");
            }
        }
    }

    //test method to print the contents of the map
    private static void printTestResults(HashMap<String, TestResultLeaf> testResults) {
        testResults.forEach((testName, result) -> {
            System.out.println("Test Name: " + testName);
            System.out.println("Score: " + result.getScore());
            System.out.println("Feedback: " + result.getFeedback());
            System.out.println();
        });
    }

}