package s_jamz.TemplatePattern;

import java.io.File;

public abstract class FileProcessorTemplate {
    public final void processFile(File file) {
        extractFile(file);
        compileFile(file);
        storeResults(file);
    }

    protected abstract void extractFile(File file);
    protected abstract void compileFile(File file);
    protected abstract void storeResults(File file);
}