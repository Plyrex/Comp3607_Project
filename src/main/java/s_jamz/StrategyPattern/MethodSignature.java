package s_jamz.StrategyPattern;

import java.io.File;

public class MethodSignature implements EvaluationStrategy{

    @Override
    public void evaluate(File javaFile) {
        throw new UnsupportedOperationException("Unimplemented method 'evaluate'");
    }

    @Override
    public void runTests(File javaFile) {
    
        throw new UnsupportedOperationException("Unimplemented method 'runTests'");
    }
}
