package s_jamz.TemplatePattern;

import java.io.File;

public abstract class FileProcessorTemplate {
    protected abstract void extractFile(File zipFile);
    protected abstract void compileFile(File file);
    protected abstract void storeResults(File file);

    public void processFile(File file) {
        extractFile(file);
        // Compilation will be handled separately
    }

    public void compileDirectory(File directory) {
        compileFile(directory);
    }
}