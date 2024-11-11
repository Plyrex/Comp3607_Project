package s_jamz.TemplatePattern;

import java.io.File;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

public abstract class FileProcessorTemplate {
    protected abstract void extractFile(File zipFile);
    protected abstract void compileFile(File file);
    protected abstract void storeResults(File file, DiagnosticCollector<JavaFileObject> diagnostics, String compilerOutput);

    public void processFile(File file) {
        extractFile(file);
        // Compilation will be handled separately
    }

    public void compileDirectory(File directory) {
        compileFile(directory);
    }
}