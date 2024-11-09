package s_jamz.CompositePattern;

import java.util.ArrayList;
import java.util.List;

public class TestResultComposite implements TestResultComponent {
    private List<TestResultComponent> components = new ArrayList<>();

    @Override
    public int getScore() {
        int totalScore = 0;
        for (TestResultComponent component : components) {
            totalScore += component.getScore();
        }
        return components.isEmpty() ? 0 : totalScore / components.size(); // Average score
    }

    @Override
    public String getFeedback() {
        StringBuilder feedback = new StringBuilder();
        for (TestResultComponent component : components) {
            feedback.append(component.getFeedback()).append("\n");
        }
        return feedback.toString();
    }

    @Override
    public void add(TestResultComponent component) {
        components.add(component);
    }

    @Override
    public void remove(TestResultComponent component) {
        components.remove(component);
    }

    @Override
    public TestResultComponent getChild(int i) {
        return components.get(i);
    }
}