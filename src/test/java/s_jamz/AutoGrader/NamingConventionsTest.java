package s_jamz.AutoGrader;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NamingConventionsTest {

    private static final Set<String> EXPECTED_CLASS_NAMES = new HashSet<>(Arrays.asList(
        "ChatBot", "ChatBotGenerator", "ChatBotPlatform", "ChatBotSimulation"
    ));

    private static final Set<String> EXPECTED_ATTRIBUTE_NAMES = new HashSet<>(Arrays.asList(
        "chatBotName", "numResponsesGenerated", "messageLimit", "messageNumber"
    ));

    private static final Set<String> EXPECTED_METHOD_NAMES = new HashSet<>(Arrays.asList(
        "ChatBot", "ChatBot", "getChatBotName", "getNumResponsesGenerated", "getTotalNumResponsesGenerated",
        "getTotalNumMessagesRemaining", "limitReached", "generateResponse", "prompt", "toString",
        "ChatBotPlatform", "addChatBot", "getChatBotList", "interactWithBot", "generateChatBotLLM"
    ));

    private static int score;

    @BeforeAll
    public static void setUp() {
        score = 0; // Initialize score once at the start of all tests
    }

    @Test
    public void testClassNameConvention() throws IOException {
        File studentDir = new File(System.getProperty("user.dir") + "/src/main/resources/StudentFolders/");
        for (File studentFile : studentDir.listFiles((dir, name) -> name.endsWith(".java"))) {
            List<String> lines = Files.readAllLines(studentFile.toPath());
            for (String line : lines) {
                if (line.startsWith("public class ")) {
                    String className = line.split(" ")[2];
                    System.out.println("Checking class name: " + className);
                    assertTrue(EXPECTED_CLASS_NAMES.contains(className), "Unexpected class name: " + className);
                    if (EXPECTED_CLASS_NAMES.contains(className)) {
                        score += 5; // Assign 5 points for each correct class name
                        System.out.println("Matched class name: " + className);
                    }
                }
            }
        }
    }

    @Test
    public void testMethodNameConvention() throws IOException {
        File studentDir = new File(System.getProperty("user.dir") + "/src/main/resources/StudentFolders/");
        for (File studentFile : studentDir.listFiles((dir, name) -> name.endsWith(".java"))) {
            List<String> lines = Files.readAllLines(studentFile.toPath());
            for (String line : lines) {
                if (line.contains("public ") && line.contains("(") && line.contains(")")) {
                    String methodName = line.split(" ")[2].split("\\(")[0];
                    System.out.println("Checking method name: " + methodName);
                    assertTrue(EXPECTED_METHOD_NAMES.contains(methodName), "Unexpected method name: " + methodName);
                    if (EXPECTED_METHOD_NAMES.contains(methodName)) {
                        score += 3; // Assign 3 points for each correct method name
                        System.out.println("Matched method name: " + methodName);
                    }
                }
            }
        }
    }

    @Test
    public void testAttributeNameConvention() throws IOException {
        File studentDir = new File(System.getProperty("user.dir") + "/src/main/resources/StudentFolders/");
        for (File studentFile : studentDir.listFiles((dir, name) -> name.endsWith(".java"))) {
            List<String> lines = Files.readAllLines(studentFile.toPath());
            for (String line : lines) {
                if (line.contains("private ") && line.contains(";")) {
                    String attributeName = line.split(" ")[2].replace(";", "");
                    System.out.println("Checking attribute name: " + attributeName);
                    assertTrue(EXPECTED_ATTRIBUTE_NAMES.contains(attributeName), "Unexpected attribute name: " + attributeName);
                    if (EXPECTED_ATTRIBUTE_NAMES.contains(attributeName)) {
                        score += 2; // Assign 2 points for each correct attribute name
                        System.out.println("Matched attribute name: " + attributeName);
                    }
                }
            }
        }
    }

    @AfterAll
    public static void printFinalScore() {
        System.out.println("Final Naming Convention Score: " + score);
    }
}