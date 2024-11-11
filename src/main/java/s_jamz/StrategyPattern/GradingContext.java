package s_jamz.StrategyPattern;

import java.io.File;

public class GradingContext {
    private EvaluationStrategy strategy;

    public void setStrategy(EvaluationStrategy strategy) {
        this.strategy = strategy;
    }

    public void evaluate(File javaFile) {
        strategy.evaluate(javaFile);
    }

    public void runTests(File javaFile) {
        strategy.runTests(javaFile);
    }

    public EvaluationStrategy getStrategy() {
        return strategy;
    }
}