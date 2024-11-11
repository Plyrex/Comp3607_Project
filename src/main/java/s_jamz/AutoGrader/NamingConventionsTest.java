package s_jamz.AutoGrader;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class NamingConventionsTest {

    public static Map<String, Integer> scores = new HashMap<>();

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
        Class<?> chatBotClass = loadClass("ChatBot");

        // Check class name
        assertEquals("ChatBot", chatBotClass.getSimpleName(), "Class name should be 'ChatBot'");
        score += 1;

        // Check attribute names
        Field[] fields = chatBotClass.getDeclaredFields();
        ArrayList<String> expectedFieldNames = new ArrayList<>();
        expectedFieldNames.add("chatBotName");
        expectedFieldNames.add("numResponsesGenerated");
        expectedFieldNames.add("messageLimit");
        expectedFieldNames.add("messageNumber");

        for (Field field : fields) {
            assertTrue(expectedFieldNames.contains(field.getName()), "Field name should follow naming conventions");
            score += 1;
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
            assertTrue(expectedMethodNames.contains(method.getName()), "Method name should follow naming conventions");
            score += 1;
        }
        scores.put("ChatBot", score);
    }

    @Test
    public void testChatBotPlatformClassNamingConventions() throws Exception {
        int score = 0;
        Class<?> chatBotPlatformClass = loadClass("ChatBotPlatform");

        // Check class name
        assertEquals("ChatBotPlatform", chatBotPlatformClass.getSimpleName(), "Class name should be 'ChatBotPlatform'");
        score += 1;

        // Check method names
        Method[] methods = chatBotPlatformClass.getDeclaredMethods();
        ArrayList<String> expectedMethodNames = new ArrayList<>();
        expectedMethodNames.add("addChatBot");
        expectedMethodNames.add("getChatBotList");
        expectedMethodNames.add("interactWithBot");

        for (Method method : methods) {
            assertTrue(expectedMethodNames.contains(method.getName()), "Method name should follow naming conventions");
            score += 1;
        }
        scores.put("ChatBotPlatform", score);
    }

    @Test
    public void testChatBotGeneratorClassNamingConventions() throws Exception {
        int score = 0;
        Class<?> chatBotGeneratorClass = loadClass("ChatBotGenerator");

        // Check class name
        assertEquals("ChatBotGenerator", chatBotGeneratorClass.getSimpleName(), "Class name should be 'ChatBotGenerator'");
        score += 1;

        // Check method names
        Method[] methods = chatBotGeneratorClass.getDeclaredMethods();
        ArrayList<String> expectedMethodNames = new ArrayList<>();
        expectedMethodNames.add("generateChatBotLLM");

        for (Method method : methods) {
            assertTrue(expectedMethodNames.contains(method.getName()), "Method name should follow naming conventions");
            score += 1;
        }
        scores.put("ChatBotGenerator", score);
    }
}