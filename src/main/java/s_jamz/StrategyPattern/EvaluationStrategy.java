package s_jamz.StrategyPattern;

import java.io.File;

import s_jamz.CompositePattern.TestResultComponent;


public interface EvaluationStrategy {
    void evaluate(File javaFile);
    void runTests();
    TestResultComponent getResults();
}