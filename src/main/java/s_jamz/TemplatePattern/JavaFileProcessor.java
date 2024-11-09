package s_jamz.TemplatePattern;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import s_jamz.FileExtractor;

public class JavaFileProcessor extends FileProcessorTemplate {
    @Override
    protected void extractFile(File file) {
        // Check if the directory already exists
        String newDestFolder = System.getProperty("user.dir") + "/src/main/resources/StudentFolders/";
        String studentFolder = newDestFolder + file.getName().replace(".zip", "");
        File studentFolderFile = new File(studentFolder);
        if (studentFolderFile.exists()) {
            System.out.println("Student folder already exists: " + studentFolder);
            return;
        }

        // Extraction logic here
        FileExtractor fileExtractor = new FileExtractor();
        try {
            fileExtractor.extractZip(file);
        } catch (Exception e) {
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

        // Collect all .java files in the directory
        List<File> javaFiles = new ArrayList<>();
        collectJavaFiles(file, javaFiles);

        if (javaFiles.isEmpty()) {
            System.out.println("No Java files found in directory: " + file.getName());
            return;
        }

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(javaFiles);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);

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

    @Override
    protected void storeResults(File file) {
        // This method is now empty because we handle storing results in the overloaded method
    }

    private void storeResults(File file, DiagnosticCollector<JavaFileObject> diagnostics) {
        File resultsDir = new File("results");
        resultsDir.mkdir();
        String resultFileName = file.getName() + ".txt";
        File resultFile = new File(resultsDir, resultFileName);
        try (FileWriter writer = new FileWriter(resultFile)) {
            if (diagnostics.getDiagnostics().isEmpty()) {
                writer.write("No compilation errors or warnings.\n");
            } else {
                diagnostics.getDiagnostics().forEach(diagnostic -> {
                    try {
                        writer.write(diagnostic.toString() + System.lineSeparator());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            System.out.println("Compilation results written to " + resultFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void collectJavaFiles(File dir, List<File> javaFiles) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                collectJavaFiles(file, javaFiles);
            } else if (file.getName().endsWith(".java")) {
                javaFiles.add(file);
            }
        }
    }
}