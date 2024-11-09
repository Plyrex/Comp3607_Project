package s_jamz.TemplatePattern;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import java.io.File;

public abstract class FileProcessorTemplate {
    protected abstract void extractFile(File zipFile);
    protected abstract void compileFile(File file);
    protected abstract void storeResults(File file, DiagnosticCollector<JavaFileObject> diagnostics);
    
    public void processFile(File file) {
        extractFile(file);
        compileFile(file);
    }

    public void compileDirectory(File directory) {
        compileFile(directory);
    }
}