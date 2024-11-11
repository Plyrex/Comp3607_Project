package s_jamz.CompositePattern;

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