package s_jamz.CompositePattern;

import java.util.List;

public interface TestResultComponent {
    int getScore();
    String getFeedback();
    void add(TestResultComponent component);
    void remove(TestResultComponent component);
    TestResultComponent getChild(int i);
    void print();
    List<TestResultComponent> getResults();
}