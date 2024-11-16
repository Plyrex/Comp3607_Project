package s_jamz.TemplatePattern;

import javax.tools.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import s_jamz.FileExtractor;

public class JavaFileProcessor extends FileProcessorTemplate {

    @Override
    protected void extractFile(File zipFile) {
        FileExtractor extractor = new FileExtractor();
        try {
            extractor.extractZip(zipFile, zipFile.getParentFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void compileFile(File file) {
        if (file == null || !file.isDirectory()) {
            System.out.println("Error...Invalid directory: " + file);
            return;
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.out.println("Java compiler not available.");
            return;
        }

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        List<File> javaFiles = new ArrayList<>();
        collectJavaFiles(file, javaFiles);

        if (javaFiles.isEmpty()) {
            System.out.println("No Java files found in directory: " + file);
            return;
        }

        Path outputDir = file.toPath().resolve("bin");
        try {
            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);
                System.out.println("Created bin directory: " + outputDir);
            }
        } catch (IOException e) {
            System.err.println("Failed to create bin directory: " + outputDir);
            e.printStackTrace();
            return;
        }

        List<String> compileOptions = new ArrayList<>();
        compileOptions.add("-d");
        compileOptions.add(outputDir.toString());

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(javaFiles);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);
        JavaCompiler.CompilationTask task = compiler.getTask(writer, fileManager, diagnostics, compileOptions, null, compilationUnits);

        boolean success = task.call();
        writer.flush();
        String compilerOutput = outputStream.toString();
        System.out.println(success ? "Compilation successful for files in " + file.getName() : "Compilation failed for files in " + file.getName());
        if (!success) {
            System.out.println(compilerOutput);
        }

        try {
            fileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        storeResults(file, diagnostics, compilerOutput);

        // If compilation is successful, attempt to run the program
        if (success) {
            runMainClass(outputDir.toString(), "ChatBotSimulation");  
        }
    }

    private void runMainClass(String binDirectory, String mainClassName) {
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", binDirectory, mainClassName);
        processBuilder.redirectErrorStream(true);  // Combine stderr and stdout

        try {
            Process process = processBuilder.start();

            // Capture program output
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                Path outputLog = Paths.get(binDirectory, "ProgramOutput.log");
                List<String> outputLines = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    outputLines.add(line);
                }

                // Save output to a log file
                Files.write(outputLog, outputLines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Stored program output in: " + outputLog);
            }

            int exitCode = process.waitFor();
            System.out.println("Program exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to run the program for class " + mainClassName);
            e.printStackTrace();
        }
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
    protected void storeResults(File file, DiagnosticCollector<JavaFileObject> diagnostics, String compilerOutput) {
        Path resultDir = file.toPath().resolve("bin");
        try {
            if (!Files.exists(resultDir)) {
                Files.createDirectories(resultDir);
                System.out.println("Created bin directory for results: " + resultDir);
            }
            Path resultFile = resultDir.resolve("Test.log");
            List<String> logLines = new ArrayList<>();
            logLines.add("Compilation results for " + file.getName() + ":");

            // Add detailed diagnostics
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                String diagnosticInfo = String.format("File: %s, Line: %d, Severity: %s, Message: %s",
                        diagnostic.getSource() == null ? "Unknown" : diagnostic.getSource().getName(),
                        diagnostic.getLineNumber(),
                        diagnostic.getKind(),
                        diagnostic.getMessage(null));
                logLines.add(diagnosticInfo);
            }

            // Append compiler output
            logLines.add("Compiler Output:");
            logLines.add(compilerOutput.trim().isEmpty() ? "No output from compiler." : compilerOutput);

            Files.write(resultFile, logLines);
            System.out.println("Stored compilation results in: " + resultFile);
        } catch (IOException e) {
            System.err.println("Failed to store compilation results in: " + resultDir);
            e.printStackTrace();
        }
    }
}