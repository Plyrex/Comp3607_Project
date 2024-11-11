package s_jamz.StrategyPattern;

import java.io.File;

public interface GradingContext {
    void setStrategy(EvaluationStrategy strategy);
    void evaluate(File javaFile);
    void runTests(File javaFile);
    EvaluationStrategy getStrategy();
}