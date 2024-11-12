package s_jamz.AutoGrader;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class MethodSignaturesTest {

    public static Map<String, Double> scores = new HashMap<>();
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

    // Compares a method's signature with the expected signature details
    private boolean checkMethodSignature(Method method, String expectedReturnType, String expectedMethodName, List<String> expectedParameterTypes) {
        if (!method.getReturnType().getName().equals(expectedReturnType)) return false;
        if (!method.getName().equals(expectedMethodName)) return false;

        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != expectedParameterTypes.size()) return false;
        for (int i = 0; i < parameterTypes.length; i++) {
            if (!parameterTypes[i].getName().equals(expectedParameterTypes.get(i))) return false;
        }
        return true;
    }

    @Test
    public void testChatBotMethodSignatures() throws Exception {
        double score = 0;
        List<String> feedbackMessages = new ArrayList<>();
        Class<?> chatBotClass = loadClass("ChatBot");

        // Expected method signatures for ChatBot class
        List<MethodSignature> expectedMethodSignatures = Arrays.asList(
            new MethodSignature("java.lang.String", "getChatBotName", Arrays.asList(), 0.5),
            new MethodSignature("int", "getNumResponsesGenerated", Arrays.asList(), 0.5),
            new MethodSignature("int", "getTotalNumResponsesGenerated", Arrays.asList(), 1),
            new MethodSignature("int", "getTotalNumMessagesRemaining", Arrays.asList(), 1.5),
            new MethodSignature("boolean", "limitReached", Arrays.asList(), 1.5),
            new MethodSignature("java.lang.String", "generateResponse", Arrays.asList(), 2),
            new MethodSignature("java.lang.String", "prompt", Arrays.asList("java.lang.String"), 1),
            new MethodSignature("java.lang.String", "toString", Arrays.asList(), 2)
        );

        // Check each method in the loaded class
        for (Method method : chatBotClass.getDeclaredMethods()) {
            boolean matched = false;
            for (MethodSignature expectedSignature : expectedMethodSignatures) {
                if (checkMethodSignature(method, expectedSignature.returnType, expectedSignature.methodName, expectedSignature.parameterTypes)) {
                    matched = true;
                    score += expectedSignature.weight;
                    feedbackMessages.add("Method signature '" + method.toString() + "' matches expected signature.");
                    break;
                }
            }
            if (!matched) {
                feedbackMessages.add("Mismatched signature: found " + method.toString() 
                    + ", expected one of " + expectedMethodSignatures);
            }
        }

        scores.put("ChatBot", score);
        feedback.put("ChatBot", feedbackMessages);
        assertEquals(10.0, score, 
            "Not all method signatures matched for ChatBot");
    }

    @Test
    public void testChatBotPlatformMethodSignatures() throws Exception {
        double score = 0;
        List<String> feedbackMessages = new ArrayList<>();
        Class<?> chatBotPlatformClass = loadClass("ChatBotPlatform");

        // Expected method signatures for ChatBotPlatform class
        List<MethodSignature> expectedMethodSignatures = Arrays.asList(
            new MethodSignature("boolean", "addChatBot", Arrays.asList("int"), 2),
            new MethodSignature("java.lang.String", "getChatBotList", Arrays.asList(), 3),
            new MethodSignature("java.lang.String", "interactWithBot", Arrays.asList("int", "java.lang.String"), 2)
        );

        // Check each method in the loaded class
        for (Method method : chatBotPlatformClass.getDeclaredMethods()) {
            boolean matched = false;
            for (MethodSignature expectedSignature : expectedMethodSignatures) {
                if (checkMethodSignature(method, expectedSignature.returnType, expectedSignature.methodName, expectedSignature.parameterTypes)) {
                    matched = true;
                    score += expectedSignature.weight;
                    feedbackMessages.add("Method signature '" + method.toString() + "' matches expected signature.");
                    break;
                }
            }
            if (!matched) {
                feedbackMessages.add("Mismatched signature: found " + method.toString() 
                    + ", expected one of " + expectedMethodSignatures);
            }
        }

        scores.put("ChatBotPlatform", score);
        feedback.put("ChatBotPlatform", feedbackMessages);
        assertEquals(7, score, 
            "Not all method signatures matched for ChatBotPlatform");
    }

    @Test
    public void testChatBotGeneratorMethodSignatures() throws Exception {
        double score = 0;
        List<String> feedbackMessages = new ArrayList<>();
        Class<?> chatBotGeneratorClass = loadClass("ChatBotGenerator");

        // Expected method signatures for ChatBotGenerator class
        List<MethodSignature> expectedMethodSignatures = Arrays.asList(
            new MethodSignature("java.lang.String", "generateChatBotLLM", Arrays.asList("int"), 1)
        );

        // Check each method in the loaded class
        for (Method method : chatBotGeneratorClass.getDeclaredMethods()) {
            boolean matched = false;
            for (MethodSignature expectedSignature : expectedMethodSignatures) {
                if (checkMethodSignature(method, expectedSignature.returnType, expectedSignature.methodName, expectedSignature.parameterTypes)) {
                    matched = true;
                    score += expectedSignature.weight;
                    feedbackMessages.add("Method signature '" + method.toString() + "' matches expected signature.");
                    break;
                }
            }
            if (!matched) {
                feedbackMessages.add("Mismatched signature: found " + method.toString() 
                    + ", expected one of " + expectedMethodSignatures);
            }
        }

        scores.put("ChatBotGenerator", score);
        feedback.put("ChatBotGenerator", feedbackMessages);
        assertEquals(1, score, 
            "Not all method signatures matched for ChatBotGenerator");
    }

    // Helper class to store method signature details
    private static class MethodSignature {
        String returnType;
        String methodName;
        List<String> parameterTypes;
        double weight;

        MethodSignature(String returnType, String methodName, List<String> parameterTypes, double weight) {
            this.returnType = returnType;
            this.methodName = methodName;
            this.parameterTypes = parameterTypes;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return returnType + " " + methodName + parameterTypes.toString();
        }
    }
}