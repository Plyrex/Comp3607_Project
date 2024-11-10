package s_jamz.CompositePattern;

import java.util.ArrayList;
import java.util.List;

public class TestResultComposite implements TestResultComponent {
    private List<TestResultComponent> results = new ArrayList<>();

    @Override
    public void add(TestResultComponent result) {
        results.add(result);
    }

    @Override
    public void remove(TestResultComponent result) {
        results.remove(result);
    }

    @Override
    public TestResultComponent getChild(int i) {
        return results.get(i);
    }

    @Override
    public void print() {
        for (TestResultComponent result : results) {
            result.print();
        }
    }

    @Override
    public int getScore() {
        int totalScore = 0;
        for (TestResultComponent result : results) {
            totalScore += result.getScore();
        }
        return totalScore;
    }

    @Override
    public String getFeedback() {
        StringBuilder feedback = new StringBuilder();
        for (TestResultComponent result : results) {
            feedback.append(result.getFeedback()).append("\n");
        }
        return feedback.toString();
    }

    @Override
    public List<TestResultComponent> getResults() {
        return results;
    }
}