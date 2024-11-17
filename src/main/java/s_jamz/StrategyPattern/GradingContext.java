package s_jamz.StrategyPattern;

// import java.io.File;

public class GradingContext {
    private EvaluationStrategy strategy;

    public void setStrategy(EvaluationStrategy strategy) {
        this.strategy = strategy;
    }


    public void evaluate(){
        if (strategy != null) {
            strategy.evaluate();
        } else {
            throw new IllegalStateException("Test strategy not set");
        }
    }

    public EvaluationStrategy getStrategy() {
        return strategy;
    }
}