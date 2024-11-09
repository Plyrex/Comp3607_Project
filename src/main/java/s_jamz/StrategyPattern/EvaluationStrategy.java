package s_jamz.StrategyPattern;

import java.io.File;

public interface EvaluationStrategy {
    void evaluate(File javaFile);
    void runTests(File javaFile);
}