package s_jamz.CompositePattern;

import java.util.ArrayList;
import java.util.List;

public class TestResultComposite implements TestResultComponent {
    private List<TestResultComponent> results = new ArrayList<>();

    @Override
    public void add(TestResultComponent component) {
        results.add(component);
    }

    @Override
    public void remove(TestResultComponent component) {
        results.remove(component);
    }

    @Override
    public TestResultComponent getChild(int i) {
        return results.get(i);
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
    public void print() {
        for (TestResultComponent result : results) {
            result.print();
        }
    }
}