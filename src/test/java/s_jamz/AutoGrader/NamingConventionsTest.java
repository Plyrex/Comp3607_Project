package s_jamz.AutoGrader;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NamingConventionsTest {

    private static int totalScore;
    private static int classNamesScore;
    private static int attributeNamesScore;
    private static int methodNamesScore;

    @BeforeEach
    public void setup() {
        // Only reset individual scores for each test
        classNamesScore = 0;
        attributeNamesScore = 0;
        methodNamesScore = 0;
    }

    @Test
    public void testClassNames() {
        int score = 0;

        // Class Names
        String[] classNames = {"ChatBot", "ChatBotPlatform", "ChatBotGenerator", "ChatBotSimulation"};
        for (String className : classNames) {
            try {
                assertTrue(className.matches("^[A-Z][a-zA-Z0-9]*$"), "Class name " + className + " does not follow CamelCase.");
                score++;
            } catch (AssertionError e) {
                System.out.println(e.getMessage());
            }
        }

        classNamesScore = Math.min(score, 4); // Ensure score does not exceed 4
        totalScore += classNamesScore;
        System.out.println("Class Names Score: " + classNamesScore + "/4");
    }

    @Test
    public void testAttributeNames() {
        int score = 0;

        // Attribute Names
        String[] attributeNames = {"chatBotName", "numResponsesGenerated", "messageLimit", "messageNumber", "bots"};
        for (String attributeName : attributeNames) {
            try {
                assertTrue(attributeName.matches("^[a-z][a-zA-Z0-9]*$"), "Attribute name " + attributeName + " does not follow camelCase.");
                score++;
            } catch (AssertionError e) {
                System.out.println(e.getMessage());
            }
        }

        // Additional marks for descriptiveness
        if (score == attributeNames.length) {
            score += 2;
        }

        attributeNamesScore = Math.min(score, 7); // Ensure score does not exceed 7
        totalScore += attributeNamesScore;
        System.out.println("Attribute Names Score: " + attributeNamesScore + "/7");
    }

    @Test
    public void testMethodNames() {
        int score = 0;

        // Method Names
        String[] methodNames = {
            "getChatBotName", "getNumResponsesGenerated", "getTotalNumResponsesGenerated", 
            "getTotalNumMessagesRemaining", "limitReached", "generateResponse", 
            "prompt", "toString", "addChatBot", "getChatBotList", 
            "interactWithBot", "generateChatBotLLM"
        };
        for (String methodName : methodNames) {
            try {
                assertTrue(methodName.matches("^[a-z][a-zA-Z0-9]*$"), "Method name " + methodName + " does not follow camelCase.");
                score++;
            } catch (AssertionError e) {
                System.out.println(e.getMessage());
            }
        }

        // Additional marks for descriptiveness
        if (score == methodNames.length) {
            score += 2;
        }

        methodNamesScore = Math.min(score, 9); // Ensure score does not exceed 9
        totalScore += methodNamesScore;
        System.out.println("Method Names Score: " + methodNamesScore + "/9");
    }

    @AfterAll
    public static void printTotalScore() {
        System.out.println("Total Score: " + totalScore + "/20");
    }
}
