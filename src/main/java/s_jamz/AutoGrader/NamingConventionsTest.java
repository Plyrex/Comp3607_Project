package s_jamz.AutoGrader;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import s_jamz.CompositePattern.TestResultLeaf;

public class NamingConventionsTest {

    private static int totalScore;
    private static int chatBotScore = 0;
    private static int chatBotPlatformScore = 0;
    private static int chatBotGeneratorScore = 0;

    private HashMap<String, Field[]> attributeTest;
    private HashMap<String, Method[]> methodTest;
    private static HashMap<String, TestResultLeaf> testResults = new HashMap<>();

    public NamingConventionsTest() {
        attributeTest = new HashMap<>();
        methodTest = new HashMap<>();
    }

    @BeforeEach
    public void setup() {
        attributeTest.clear();
        methodTest.clear();
        try {
            loadClassDetails("ChatBot");
            loadClassDetails("ChatBotPlatform");
            loadClassDetails("ChatBotGenerator");
        } catch (Exception e) {
            System.err.println("Could not load class details: " + e.getMessage());
        }
    }

    public Field[] getFields(Class<?> class1) {
        try {
            if (class1 == null) {
                System.err.println("Provided class is null.");
                return new Field[0];
            }
            return class1.getDeclaredFields();
        } catch (Exception e) {
            System.err.println("An error occurred while retrieving fields: " + e.getMessage());
            return new Field[0];
        }
    }

    public Method[] getMethods(Class<?> class1) {
        try {
            if (class1 == null) {
                System.err.println("Provided class is null.");
                return new Method[0];
            }
            return class1.getDeclaredMethods();
        } catch (Exception e) {
            System.err.println("An error occurred while retrieving methods: " + e.getMessage());
            return new Method[0];
        }
    }

    private Class<?> loadClass(String className) throws Exception {
        File studentFoldersDir = new File(System.getProperty("user.dir") + "/src/main/resources/StudentFolders/");
        if (!studentFoldersDir.exists() || !studentFoldersDir.isDirectory()) {
            throw new IllegalArgumentException("Invalid student folders path: " + studentFoldersDir.getAbsolutePath());
        }

        for (File studentDir : studentFoldersDir.listFiles()) {
            if (studentDir.isDirectory()) {
                File binDir = new File(studentDir, "bin");
                if (binDir.exists() && binDir.isDirectory()) {
                    URL[] urls = {binDir.toURI().toURL()};
                    URLClassLoader urlClassLoader = new URLClassLoader(urls, this.getClass().getClassLoader());
                    try {
                        return Class.forName(className, true, urlClassLoader);
                    } catch (ClassNotFoundException e) {
                        // Continue to the next student folder
                    }
                }
            }
        }

        throw new ClassNotFoundException("Class " + className + " not found in any student folder.");
    }

    public void loadClassDetails(String className) throws Exception {
        Class<?> class1 = loadClass(className);

        if (class1 == null) {
            System.out.println("Provided class is null.");
            return;
        }

        try {
            attributeTest.put(className, getFields(class1));
            methodTest.put(className, getMethods(class1));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void chatBotNamingConventionsTest() {
        System.out.println("ChatBot Naming Conventions Test. \n");
        Field[] chatBotAttributes = attributeTest.get("ChatBot");
        Method[] chatBotMethods = methodTest.get("ChatBot");
        int score = 0;
        StringBuilder feedback = new StringBuilder();

        // Check class name
        try {
            assertEquals("ChatBot", "ChatBot", "Class name should be 'ChatBot'");
            score += 1;
            feedback.append("Class name 'ChatBot' is correct.\n");
        } catch (AssertionError e) {
            feedback.append("Class name should be 'ChatBot'.\n");
        }

        // Check attribute names
        HashMap<String, Class<?>> expectedAttributes = new HashMap<>();
        expectedAttributes.put("chatBotName", String.class);
        expectedAttributes.put("numResponsesGenerated", int.class);
        expectedAttributes.put("messageLimit", int.class);
        expectedAttributes.put("messageNumber", int.class);

        for (Field field : chatBotAttributes) {
            try {
                if (expectedAttributes.containsKey(field.getName())) {
                    feedback.append("Class contains: ").append(field.getName()).append(". ");
                }
            } catch (Exception e) {
                feedback.append(e.getMessage()).append("\n");
            }
        }

        // Check method names
        ArrayList<String> expectedMethodNames = new ArrayList<>();
        expectedMethodNames.add("getChatBotName");
        expectedMethodNames.add("getNumResponsesGenerated");
        expectedMethodNames.add("getTotalNumResponsesGenerated");
        expectedMethodNames.add("getTotalNumMessagesRemaining");
        expectedMethodNames.add("limitReached");
        expectedMethodNames.add("ChatBot");
        expectedMethodNames.add("generateResponse");
        expectedMethodNames.add("prompt");
        expectedMethodNames.add("toString");

        for (Method method : chatBotMethods) {
            try {
                if (expectedMethodNames.contains(method.getName())) {
                    feedback.append("Class contains: ").append(method.getName()).append(". ");
                    score++;
                } else {
                    feedback.append(method.getName()).append(" does not follow naming conventions.\n");
                }
            } catch (Exception e) {
                feedback.append(e.getMessage()).append("\n");
            }
        }

        chatBotScore = score;
        totalScore += chatBotScore;
        feedback.append("ChatBot Class Score: ").append(chatBotScore).append("/36\n");
        testResults.put("ChatBot", new TestResultLeaf(chatBotScore, feedback.toString()));
    }

    @Test
    public void chatBotPlatformNamingConventionsTest() {
        System.out.println("ChatBotPlatform Naming Conventions Test. \n");
        Field[] chatBotPlatformAttributes = attributeTest.get("ChatBotPlatform");
        Method[] chatBotPlatformMethods = methodTest.get("ChatBotPlatform");
        int score = 0;
        StringBuilder feedback = new StringBuilder();

        // Check class name
        try {
            assertEquals("ChatBotPlatform", "ChatBotPlatform", "Class name should be 'ChatBotPlatform'");
            score += 1;
            feedback.append("Class name 'ChatBotPlatform' is correct.\n");
        } catch (AssertionError e) {
            feedback.append("Class name should be 'ChatBotPlatform'.\n");
        }

        // Check attribute names
        HashMap<String, Class<?>> expectedAttributes = new HashMap<>();
        expectedAttributes.put("bots", ArrayList.class);

        for (Field field : chatBotPlatformAttributes) {
            try {
                if (expectedAttributes.containsKey(field.getName())) {
                    feedback.append("Class contains: ").append(field.getName()).append(". ");
                }

            } catch (Exception e) {
                feedback.append(e.getMessage()).append("\n");
            }
        }

        // Check method names
        ArrayList<String> expectedMethodNames = new ArrayList<>();
        expectedMethodNames.add("addChatBot");
        expectedMethodNames.add("getChatBotList");
        expectedMethodNames.add("interactWithBot");

        for (Method method : chatBotPlatformMethods) {
            try {
                if (expectedMethodNames.contains(method.getName())) {
                    feedback.append("Class contains: ").append(method.getName()).append(". ");
                    score++;
                } else {
                    feedback.append(method.getName()).append(" does not follow naming conventions.\n");
                }
            } catch (Exception e) {
                feedback.append(e.getMessage()).append("\n");
            }
        }

        chatBotPlatformScore = score;
        totalScore += chatBotPlatformScore;
        feedback.append("ChatBotPlatform Class Score: ").append(chatBotPlatformScore).append("/20\n");
        testResults.put("ChatBotPlatform", new TestResultLeaf(chatBotPlatformScore, feedback.toString()));
    }

    @Test
    public void chatBotGeneratorNamingConventionsTest() {
        System.out.println("ChatBotGenerator Naming Conventions Test. \n");
        Field[] chatBotGeneratorAttributes = attributeTest.get("ChatBotGenerator");
        Method[] chatBotGeneratorMethods = methodTest.get("ChatBotGenerator");
        int score = 0;
        StringBuilder feedback = new StringBuilder();

        // Check class name
        try {
            assertEquals("ChatBotGenerator", "ChatBotGenerator", "Class name should be 'ChatBotGenerator'");
            score += 1;
            feedback.append("Class name 'ChatBotGenerator' is correct.\n");
        } catch (AssertionError e) {
            feedback.append("Class name should be 'ChatBotGenerator'.\n");
        }

        // Check attribute names
        HashMap<String, Class<?>> expectedAttributes = new HashMap<>();
        expectedAttributes.put("chatBotName", String.class);
        expectedAttributes.put("numResponsesGenerated", int.class);
        expectedAttributes.put("messageLimit", int.class);
        expectedAttributes.put("messageNumber", int.class);

        for (Field field : chatBotGeneratorAttributes) {
            try {
                if (expectedAttributes.containsKey(field.getName())) {
                    feedback.append("Class contains: ").append(field.getName()).append(". ");
                }
            } catch (Exception e) {
                feedback.append(e.getMessage()).append("\n");
            }
        }

        // Check method names
        ArrayList<String> expectedMethodNames = new ArrayList<>();
        expectedMethodNames.add("generateChatBotLLM");

        for (Method method : chatBotGeneratorMethods) {
            try {
                if (expectedMethodNames.contains(method.getName())) {
                    feedback.append("Class contains: ").append(method.getName()).append(". ");
                    score++;
                } else {
                    feedback.append(method.getName()).append(" does not follow naming conventions.\n");
                }
            } catch (Exception e) {
                feedback.append(e.getMessage()).append("\n");
            }
        }

        chatBotGeneratorScore = score;
        totalScore += chatBotGeneratorScore;
        feedback.append("ChatBotGenerator Class Score: ").append(chatBotGeneratorScore).append("/7\n");
        testResults.put("ChatBotGenerator", new TestResultLeaf(chatBotGeneratorScore, feedback.toString()));
    }

    @AfterAll
    public static void calculateTotal() {
        System.out.println("Total Score = " + totalScore + "/63 \n");
        chatBotGeneratorScore = 0;
        chatBotPlatformScore = 0;
        chatBotScore = 0;
    }

    public static HashMap<String, TestResultLeaf> getTestResults() {
        return testResults;
    }
}