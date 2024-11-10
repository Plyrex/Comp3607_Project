package s_jamz.AutoGrader;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NamingConventionsTest {

    private static final Set<String> EXPECTED_CLASS_NAMES = new HashSet<>(Arrays.asList(
        "ChatBot", "ChatBotGenerator", "ChatBotPlatform", "ChatBotSimulation"
    ));

    private static final Set<String> EXPECTED_ATTRIBUTE_NAMES = new HashSet<>(Arrays.asList(
        "chatBotName", "numResponsesGenerated", "messageLimit", "messageNumber", "bots"
    ));

    private static final Set<String> EXPECTED_METHOD_NAMES = new HashSet<>(Arrays.asList(
        "ChatBot", "ChatBot", "getChatBotName", "getNumResponsesGenerated", "getTotalNumResponsesGenerated",
        "getTotalNumMessagesRemaining", "limitReached", "generateResponse", "prompt", "toString",
        "ChatBotPlatform", "addChatBot", "getChatBotList", "interactWithBot", "generateChatBotLLM"
    ));

    private int score;

    @BeforeEach
    public void setUp() {
        score = 0; // Initialize score before each test
    }

    @Test
    public void testClassNameConvention() throws Exception {
        File studentDir = new File(System.getProperty("user.dir") + "/src/main/resources/StudentFolders/");
        URL[] urls = {studentDir.toURI().toURL()};

        try (URLClassLoader classLoader = new URLClassLoader(urls)) {
            for (File studentFile : studentDir.listFiles((dir, name) -> name.endsWith(".java"))) {
                String className = studentFile.getName().replace(".java", "");
                System.out.println("Checking class name: " + className);
                assertTrue(EXPECTED_CLASS_NAMES.contains(className), "Unexpected class name: " + className);
                if (EXPECTED_CLASS_NAMES.contains(className)) {
                    score += 5; // Assign 5 points for each correct class name
                    System.out.println("Matched class name: " + className);
                }
            }
        }
    }

    @Test
    public void testMethodNameConvention() throws Exception {
        File studentDir = new File(System.getProperty("user.dir") + "/src/main/resources/StudentFolders/");
        URL[] urls = {studentDir.toURI().toURL()};

        try (URLClassLoader classLoader = new URLClassLoader(urls)) {
            for (File studentFile : studentDir.listFiles((dir, name) -> name.endsWith(".java"))) {
                String className = studentFile.getName().replace(".java", "");
                Class<?> clazz = classLoader.loadClass(className);

                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    String methodName = method.getName();
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
    public void testAttributeNameConvention() throws Exception {
        File studentDir = new File(System.getProperty("user.dir") + "/src/main/resources/StudentFolders/");
        URL[] urls = {studentDir.toURI().toURL()};

        try (URLClassLoader classLoader = new URLClassLoader(urls)) {
            for (File studentFile : studentDir.listFiles((dir, name) -> name.endsWith(".java"))) {
                String className = studentFile.getName().replace(".java", "");
                Class<?> clazz = classLoader.loadClass(className);

                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    String fieldName = field.getName();
                    System.out.println("Checking attribute name: " + fieldName);
                    assertTrue(EXPECTED_ATTRIBUTE_NAMES.contains(fieldName), "Unexpected attribute name: " + fieldName);
                    if (EXPECTED_ATTRIBUTE_NAMES.contains(fieldName)) {
                        score += 2; // Assign 2 points for each correct attribute name
                        System.out.println("Matched attribute name: " + fieldName);
                    }
                }
            }
        }
    }

    @AfterEach
    public void printFinalScore() {
        System.out.println("Final Naming Convention Score: " + score);
        assertTrue(score >= 0, "Score should be greater than or equal to 0");
    }

    public int getScore() {
        return score;
    }
}