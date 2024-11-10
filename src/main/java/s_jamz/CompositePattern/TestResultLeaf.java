package s_jamz.CompositePattern;

public class TestResultLeaf implements TestResultComponent {
    private int score;
    private String feedback;

    public TestResultLeaf(int score, String feedback) {
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
        // Leaf nodes do not support add operation
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(TestResultComponent component) {
        // Leaf nodes do not support remove operation
        throw new UnsupportedOperationException();
    }

    @Override
    public TestResultComponent getChild(int i) {
        // Leaf nodes do not support getChild operation
        throw new UnsupportedOperationException();
    }

    @Override
    public void print() {
        System.out.println(feedback);
    }
}