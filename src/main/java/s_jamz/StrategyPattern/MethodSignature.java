package s_jamz.StrategyPattern;

import java.io.File;

import s_jamz.CompositePattern.TestResultComponent;


public class MethodSignature implements EvaluationStrategy{

    @Override
    public void evaluate(File javaFile) {
        throw new UnsupportedOperationException("Unimplemented method 'evaluate'");
    }

    @Override
    public void runTests(File javaFile) {
    
        throw new UnsupportedOperationException("Unimplemented method 'runTests'");
    }

    @Override
    public TestResultComponent getResults() {
        throw new UnsupportedOperationException("Unimplemented method 'getResults'");
    }
}
