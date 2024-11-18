package s_jamz.TemplatePattern;

import java.io.File;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

import s_jamz.Utilities.CompilationResult;

public abstract class FileProcessorTemplate {
    protected abstract void extractFile(File zipFile);
    protected abstract CompilationResult compileFile(File file); // Changed to return CompilationResult
    protected abstract void storeResults(File file, DiagnosticCollector<JavaFileObject> diagnostics, String compilerOutput);

    public void processFile(File file) {
        extractFile(file);
        // Compilation will be handled separately
    }

    public CompilationResult compileDirectory(File directory) {
        return compileFile(directory); // Return the CompilationResult
    }
}