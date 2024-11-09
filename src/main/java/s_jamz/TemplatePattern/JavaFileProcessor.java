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
        // Use FileExtractor to extract only .java files
        FileExtractor extractor = new FileExtractor();
        try {
            extractor.extractZip(zipFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void compileFile(File file) {
        // Ensure the file is a directory
        if (!file.isDirectory()) {
            return;
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        List<File> javaFiles = new ArrayList<>();
        collectJavaFiles(file, javaFiles);

        if (javaFiles.isEmpty()) {
            System.out.println("No Java files found in directory: " + file.getName());
            return;
        }

        // Create a bin directory for the student's assignment
        Path binDir = Paths.get(file.getPath(), "bin");
        try {
            if (!Files.exists(binDir)) {
                Files.createDirectories(binDir);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(javaFiles);
        List<String> options = new ArrayList<>();
        options.add("-d");
        options.add(binDir.toString());

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits);

        boolean success = task.call();
        try {
            fileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!success) {
            System.out.println("Compilation failed for files in " + file.getName());
        } else {
            System.out.println("Compilation successful for files in " + file.getName());
        }
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
        // Implement logic to store compilation results
        // For example, you can log the results or save them to a file
        Path resultDir = Paths.get(System.getProperty("user.dir"), "results");
        try {
            if (!Files.exists(resultDir)) {
                Files.createDirectories(resultDir);
            }
            Path resultFile = resultDir.resolve(file.getName() + ".log");
            List<String> log = new ArrayList<>();
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                log.add(diagnostic.getMessage(null));
            }
            if (log.isEmpty()) {
                log.add("Compilation successful for files in " + file.getName());
            }
            Files.write(resultFile, log, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}