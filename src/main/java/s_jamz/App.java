package s_jamz;

import java.io.File;
import java.io.IOException;
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
                    CompilationResult compilationResult = fileProcessor.compileDirectory(studentDir);

                    // Run tests using the NamingConvention strategy
                    GradingContext gradingContext = new GradingContext();
                    TestResultComposite finalResults = new TestResultComposite();


                    //Method Behaviour
                    MethodBehaviour methodBehaviour = new MethodBehaviour(studentDir.getAbsolutePath());
                    gradingContext.setStrategy(methodBehaviour);
                    gradingContext.evaluate();

                    finalResults.add(methodBehaviour.getResults());
                    HashMap<String, TestResultLeaf> behaviourResults = MethodBehaviour.getTestResults();

                    //Method Signatures
                    MethodSignature methodSignature = new MethodSignature(studentDir.getAbsolutePath());
                    gradingContext.setStrategy(methodSignature);
                    gradingContext.evaluate();
    
                    finalResults.add(methodSignature.getResults());
                    HashMap<String, TestResultLeaf> signatureResults = MethodSignature.getTestResults();


                    //Attributes
                    AttributeType attributeType = new AttributeType(studentDir.getAbsolutePath());
                    gradingContext.setStrategy(attributeType);
                    gradingContext.evaluate();

                    finalResults.add(attributeType.getResults());
                    HashMap<String, TestResultLeaf> attributeResults = AttributeType.getTestResults();

                    //Main
                    Main main = new Main(studentDir.getAbsolutePath());
                    gradingContext.setStrategy(main);
                    gradingContext.evaluate();

                    finalResults.add(main.getResults());
                    HashMap<String, TestResultLeaf> mainResults = Main.getTestResults();

                    // Add 5 marks if the assignment compiles
                    if (compilationResult.isCompilationSuccess()) {
                        TestResultLeaf bonusResult = new TestResultLeaf(3, "Assignment compiled successfully.");
                        behaviourResults.put("Compilation Bonus", bonusResult);
                    }

                    // Add 10 marks if the code runs successfully
                    if (compilationResult.isRunSuccess()) {
                        TestResultLeaf runBonusResult = new TestResultLeaf(10, "Code ran successfully.");
                        behaviourResults.put("Run Bonus", runBonusResult);
                    }

                    // Print test results
                    printTestResults(behaviourResults);
                    printTestResults(signatureResults);
                    printTestResults(attributeResults);
                    printTestResults(mainResults);

                    // Generate PDF for the student
                    System.out.print("PDF Generating for " + studentDir.getName() + "\n");
                    PDFGenerator pdf = new PDFGenerator();
                    pdf.generatePDF(studentDir, behaviourResults, signatureResults, attributeResults, mainResults);
                    System.out.print("\nPDF Generated");

                }
            }
        }
    }

    // Test method to print the contents of the map
    private static void printTestResults(HashMap<String, TestResultLeaf> testResults) {
        testResults.forEach((testName, result) -> {
            System.out.println("Test Name: " + testName);
            System.out.println("Score: " + result.getScore());
            System.out.println("Feedback: " + result.getFeedback());
            System.out.println();
        });
    }
}