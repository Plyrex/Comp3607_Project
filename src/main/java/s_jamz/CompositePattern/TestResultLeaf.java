package s_jamz.CompositePattern;

import java.util.Collections;
import java.util.List;

public class TestResultLeaf implements TestResultComponent {
    private int score;
    private String feedback;
    private String studentFolderPath;

    public TestResultLeaf(String studentFolderPath, int score, String feedback) {
        this.studentFolderPath = studentFolderPath;
        this.score = score;
        this.feedback = feedback;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public String getFeedback() {
        return feedback;
    }

    @Override
    public void add(TestResultComponent component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(TestResultComponent component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TestResultComponent getChild(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void print() {
        System.out.println(studentFolderPath + " Score: " + score + ", Feedback: " + feedback);
    }

    @Override
    public List<TestResultComponent> getResults() {
        return Collections.emptyList();
    }
}