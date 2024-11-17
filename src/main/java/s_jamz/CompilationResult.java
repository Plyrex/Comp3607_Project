package s_jamz;

public class CompilationResult {
    private boolean compilationSuccess;
    private boolean runSuccess;

    public CompilationResult(boolean compilationSuccess, boolean runSuccess) {
        this.compilationSuccess = compilationSuccess;
        this.runSuccess = runSuccess;
    }

    public boolean isCompilationSuccess() {
        return compilationSuccess;
    }

    public boolean isRunSuccess() {
        return runSuccess;
    }
}