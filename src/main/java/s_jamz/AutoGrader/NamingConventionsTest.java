package s_jamz.AutoGrader;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class NamingConventionsTest {

    public static Map<String, Integer> scores = new HashMap<>();
    public static Map<String, List<String>> feedback = new HashMap<>();

    private Class<?> loadClass(String className) throws Exception {
        // Use the specified path for the student folders
        File studentFoldersDir = new File(System.getProperty("user.dir") + "/src/main/resources/StudentFolders/");
        if (!studentFoldersDir.exists() || !studentFoldersDir.isDirectory()) {
            throw new IllegalArgumentException("Invalid student folders path: " + studentFoldersDir.getAbsolutePath());
        }

        // Iterate through each student folder
        for (File studentDir : studentFoldersDir.listFiles()) {
            if (studentDir.isDirectory()) {
                // Navigate to the bin directory
                File binDir = new File(studentDir, "bin");
                if (binDir.exists() && binDir.isDirectory()) {
                    // Dynamically add the bin directory to the classpath
                    URL[] urls = {binDir.toURI().toURL()};
                    URLClassLoader urlClassLoader = new URLClassLoader(urls, this.getClass().getClassLoader());
                    try {
                        return Class.forName(className, true, urlClassLoader);
                    } catch (ClassNotFoundException e) {
                        // Continue searching in other student folders
                    }
                }
            }
        }

        throw new ClassNotFoundException("Class " + className + " not found in any student folder.");
    }

    @Test
    public void testChatBotClassNamingConventions() throws Exception {
        int score = 0;
        List<String> feedbackMessages = new ArrayList<>();
        Class<?> chatBotClass = loadClass("ChatBot");

        // Check class name
        try {
            assertEquals("ChatBot", chatBotClass.getSimpleName(), "Class name should be 'ChatBot'");
            score += 1;
            feedbackMessages.add("Class name 'ChatBot' is correct.");
        } catch (AssertionError e) {
            feedbackMessages.add("Class name should be 'ChatBot'.");
        }

        // Check attribute names
        Field[] fields = chatBotClass.getDeclaredFields();
        ArrayList<String> expectedFieldNames = new ArrayList<>();
        expectedFieldNames.add("chatBotName");
        expectedFieldNames.add("numResponsesGenerated");
        expectedFieldNames.add("messageLimit");
        expectedFieldNames.add("messageNumber");

        for (Field field : fields) {
            try {
                assertTrue(expectedFieldNames.contains(field.getName()), "Field name should follow naming conventions");
                score += 1;
                feedbackMessages.add("Field name '" + field.getName() + "' follows naming conventions.");
            } catch (AssertionError e) {
                feedbackMessages.add("Field name '" + field.getName() + "' does not follow naming conventions.");
            }
        }

        // Check method names
        Method[] methods = chatBotClass.getDeclaredMethods();
        ArrayList<String> expectedMethodNames = new ArrayList<>();
        expectedMethodNames.add("getChatBotName");
        expectedMethodNames.add("getNumResponsesGenerated");
        expectedMethodNames.add("getTotalNumResponsesGenerated");
        expectedMethodNames.add("getTotalNumMessagesRemaining");
        expectedMethodNames.add("limitReached");
        expectedMethodNames.add("ChatBot");
        expectedMethodNames.add("ChatBot");
        expectedMethodNames.add("generateResponse");
        expectedMethodNames.add("prompt");
        expectedMethodNames.add("toString");

        for (Method method : methods) {
            try {
                assertTrue(expectedMethodNames.contains(method.getName()), "Method name should follow naming conventions");
                score += 1;
                feedbackMessages.add("Method name '" + method.getName() + "' follows naming conventions.");
            } catch (AssertionError e) {
                feedbackMessages.add("Method name '" + method.getName() + "' does not follow naming conventions.");
            }
        }
        scores.put("ChatBot", score);
        feedback.put("ChatBot", feedbackMessages);
    }

    @Test
    public void testChatBotPlatformClassNamingConventions() throws Exception {
        int score = 0;
        List<String> feedbackMessages = new ArrayList<>();
        Class<?> chatBotPlatformClass = loadClass("ChatBotPlatform");

        // Check class name
        try {
            assertEquals("ChatBotPlatform", chatBotPlatformClass.getSimpleName(), "Class name should be 'ChatBotPlatform'");
            score += 1;
            feedbackMessages.add("Class name 'ChatBotPlatform' is correct.");
        } catch (AssertionError e) {
            feedbackMessages.add("Class name should be 'ChatBotPlatform'.");
        }

        // Check method names
        Method[] methods = chatBotPlatformClass.getDeclaredMethods();
        ArrayList<String> expectedMethodNames = new ArrayList<>();
        expectedMethodNames.add("addChatBot");
        expectedMethodNames.add("getChatBotList");
        expectedMethodNames.add("interactWithBot");

        for (Method method : methods) {
            try {
                assertTrue(expectedMethodNames.contains(method.getName()), "Method name should follow naming conventions");
                score += 1;
                feedbackMessages.add("Method name '" + method.getName() + "' follows naming conventions.");
            } catch (AssertionError e) {
                feedbackMessages.add("Method name '" + method.getName() + "' does not follow naming conventions.");
            }
        }
        scores.put("ChatBotPlatform", score);
        feedback.put("ChatBotPlatform", feedbackMessages);
    }

    @Test
    public void testChatBotGeneratorClassNamingConventions() throws Exception {
        int score = 0;
        List<String> feedbackMessages = new ArrayList<>();
        Class<?> chatBotGeneratorClass = loadClass("ChatBotGenerator");

        // Check class name
        try {
            assertEquals("ChatBotGenerator", chatBotGeneratorClass.getSimpleName(), "Class name should be 'ChatBotGenerator'");
            score += 1;
            feedbackMessages.add("Class name 'ChatBotGenerator' is correct.");
        } catch (AssertionError e) {
            feedbackMessages.add("Class name should be 'ChatBotGenerator'.");
        }

        // Check method names
        Method[] methods = chatBotGeneratorClass.getDeclaredMethods();
        ArrayList<String> expectedMethodNames = new ArrayList<>();
        expectedMethodNames.add("generateChatBotLLM");

        for (Method method : methods) {
            try {
                assertTrue(expectedMethodNames.contains(method.getName()), "Method name should follow naming conventions");
                score += 1;
                feedbackMessages.add("Method name '" + method.getName() + "' follows naming conventions.");
            } catch (AssertionError e) {
                feedbackMessages.add("Method name '" + method.getName() + "' does not follow naming conventions.");
            }
        }
        scores.put("ChatBotGenerator", score);
        feedback.put("ChatBotGenerator", feedbackMessages);
    }
}