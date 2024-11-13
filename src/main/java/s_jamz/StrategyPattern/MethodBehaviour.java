package s_jamz.StrategyPattern;

import java.io.File;

import s_jamz.CompositePattern.TestResultComponent;

public class MethodBehaviour implements EvaluationStrategy {
    @Override
    public void evaluate(File javaFile) {
        throw new UnsupportedOperationException("Unimplemented method 'evaluate'");
    }

    @Override
    public void runTests() {
        throw new UnsupportedOperationException("Unimplemented method 'runTests'");
    }

    @Override
    public TestResultComponent getResults() {
    
        throw new UnsupportedOperationException("Unimplemented method 'getResults'");
    }
    
}
