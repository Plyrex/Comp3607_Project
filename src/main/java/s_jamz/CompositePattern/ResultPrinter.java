package s_jamz.CompositePattern;

import java.util.List;

public class ResultPrinter {
    private TestResultComponent results;
    private List<String> feedback;

    public ResultPrinter(TestResultComponent results, List<String> feedback) {
        this.results = results;
        this.feedback = feedback;
    }

    public void printResults() {
        System.out.println("==========================");
        System.out.println("Autograder Test Results");
        System.out.println("==========================");

        results.print();
        System.out.println("Detailed Feedback:");
        feedback.forEach(System.out::println);
    }

    public void printFormattedResults() {
        System.out.println("==========================");
        System.out.println("Autograder Test Results");
        System.out.println("==========================");

        String currentClass = "";
        for (String feedbackMessage : feedback) {
            if (feedbackMessage.startsWith("Test Results for")) {
                if (!currentClass.isEmpty()) {
                    System.out.println("------------------------------");
                }
                currentClass = feedbackMessage;
                System.out.println(currentClass);
                System.out.println("--------------------------------");
            } else if (feedbackMessage.startsWith("Score:")) {
                System.out.println(feedbackMessage);
            } else {
                System.out.println("- " + feedbackMessage);
            }
        }

        System.out.println("------------------------------");
        System.out.println("Total Score for NamingConventions: " + results.getScore() + " points");
        System.out.println("------------------------------");

        System.out.println("==============================");
        System.out.println("Overall Total Score: " + results.getScore() + " points");
        System.out.println("==============================");
    }
}