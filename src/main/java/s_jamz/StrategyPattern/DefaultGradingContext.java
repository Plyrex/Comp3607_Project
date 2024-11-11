package s_jamz.StrategyPattern;

import java.io.File;

public class DefaultGradingContext implements GradingContext {
    private EvaluationStrategy strategy;

    @Override
    public void setStrategy(EvaluationStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void evaluate(File javaFile) {
        strategy.evaluate(javaFile);
    }

    @Override
    public void runTests(File javaFile) {
        strategy.runTests(javaFile);
    }

    @Override
    public EvaluationStrategy getStrategy() {
        return strategy;
    }
}