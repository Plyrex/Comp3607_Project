package s_jamz.TemplatePattern;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import s_jamz.FileExtractor;

public class JavaFileProcessor extends FileProcessorTemplate {

    @Override
    protected void extractFile(File zipFile) {
        // Use FileExtractor to extract only .java files to the specified directory
        FileExtractor extractor = new FileExtractor();
        try {
            extractor.extractZip(zipFile, zipFile.getParentFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void compileFile(File file) {
        // Ensure the file is a directory
        if (file == null || !file.isDirectory()) {
            System.out.println("Trying to compile. Error...Invalid directory: " + file);
            return;
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.out.println("Java compiler not available.");
            return;
        }

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        // Collect Java files and compile them
        List<File> javaFiles = new ArrayList<>();
        collectJavaFiles(file, javaFiles);

        if (javaFiles.isEmpty()) {
            System.out.println("No Java files found in directory: " + file);
            return;
        }

        // Set the output directory to the student's bin folder
        Path outputDir = file.toPath().resolve("bin");
        try {
            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);
                System.out.println("Created bin directory: " + outputDir.toString());
            }
        } catch (IOException e) {
            System.err.println("Failed to create bin directory: " + outputDir.toString());
            e.printStackTrace();
            return;
        }

        List<String> compileOptions = new ArrayList<>();
        compileOptions.add("-d");
        compileOptions.add(outputDir.toString());

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(javaFiles);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, compileOptions, null, compilationUnits);

        boolean success = task.call();
        if (success) {
            System.out.println("Compilation successful for files in " + file.getName());
        } else {
            System.out.println("Compilation failed for files in " + file.getName());
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                System.out.println(diagnostic.getMessage(null));
            }
        }

        try {
            fileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Store the results
        storeResults(file, diagnostics);
    }

    private void collectJavaFiles(File dir, List<File> javaFiles) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    collectJavaFiles(file, javaFiles);
                } else if (file.getName().endsWith(".java")) {
                    javaFiles.add(file);
                }
            }
        }
    }

    @Override
    protected void storeResults(File file, DiagnosticCollector<JavaFileObject> diagnostics) {
        // Store compilation results in the bin folder within the student's directory
        Path resultDir = file.toPath().resolve("bin");
        try {
            if (!Files.exists(resultDir)) {
                Files.createDirectories(resultDir);
                System.out.println("Created bin directory for results: " + resultDir.toString());
            }
            Path resultFile = resultDir.resolve("Test.log");
            List<String> logLines = new ArrayList<>();
            logLines.add("Compilation results for " + file.getName() + ":");
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                logLines.add(diagnostic.getMessage(null));
            }
            Files.write(resultFile, logLines);
            System.out.println("Stored compilation results in: " + resultFile.toString());
        } catch (IOException e) {
            System.err.println("Failed to store compilation results in: " + resultDir.toString());
            e.printStackTrace();
        }
    }
}