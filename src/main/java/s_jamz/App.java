package s_jamz;

import s_jamz.TemplatePattern.JavaFileProcessor;
import s_jamz.TemplatePattern.FileProcessorTemplate;
import s_jamz.StrategyPattern.NamingConvention;
import s_jamz.StrategyPattern.TestContext;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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
                if (studentDir.isDirectory()) {
                    fileProcessor.compileDirectory(studentDir);

                    // Run tests using the NamingConvention strategy
                    TestContext testContext = new TestContext();
                    testContext.setStrategy(new NamingConvention());
                    for (File javaFile : studentDir.listFiles((dir, name) -> name.endsWith(".java"))) {
                        testContext.evaluate(javaFile);
                        testContext.runTests(javaFile);
                    }
                }
            }
        }
    }
}