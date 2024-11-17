package s_jamz.CompositePattern;

public interface TestResultComponent {
    int getScore();
    String getFeedback();
    void add(TestResultComponent component);
    void remove(TestResultComponent component);
    void print();
}