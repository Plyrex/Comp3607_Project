package s_jamz.StrategyPattern;

import s_jamz.CompositePattern.TestResultComponent;

public class ResultPrinter {
    private TestResultComponent results;

    public ResultPrinter(TestResultComponent results) {
        this.results = results;
    }

    public void printResults() {
        System.out.println("Final Test Results:");
        results.print();
    }
}