package s_jamz.StrategyPattern;

import java.io.File;
import s_jamz.CompositePattern.TestResultComponent;

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

    public void printResults() {
        TestResultComponent results = strategy.getResults();
        System.out.println("Final Test Results:");
        results.print();
    }
}