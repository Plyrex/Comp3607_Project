package s_jamz.StrategyPattern;

import java.io.File;

public class GradingContext {
    private EvaluationStrategy strategy;

    public void setStrategy(EvaluationStrategy strategy) {
        this.strategy = strategy;
    }

    public void evaluate(File javaFile) {
        if (strategy != null) {
            strategy.evaluate(javaFile);
        } else {
            throw new IllegalStateException("Evaluation strategy not set");
        }
    }

    public void runTests(File javaFile) {
        if (strategy != null) {
            strategy.runTests(javaFile);
        } else {
            throw new IllegalStateException("Test strategy not set");
        }
    }

    public EvaluationStrategy getStrategy() {
        return strategy;
    }
}