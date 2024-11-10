package s_jamz.AutoGrader;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class MethodSignaturesTest {

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
        int score = 0;
        Class<?> chatBotClass = loadClass("ChatBot");

        // Expected method signatures for ChatBot class
        List<MethodSignature> expectedMethodSignatures = Arrays.asList(
            new MethodSignature("java.lang.String", "getChatBotName", Arrays.asList()),
            new MethodSignature("int", "getNumResponsesGenerated", Arrays.asList()),
            new MethodSignature("int", "getTotalNumResponsesGenerated", Arrays.asList()),
            new MethodSignature("int", "getTotalNumMessagesRemaining", Arrays.asList()),
            new MethodSignature("boolean", "limitReached", Arrays.asList()),
            new MethodSignature("java.lang.String", "generateResponse", Arrays.asList()),
            new MethodSignature("java.lang.String", "prompt", Arrays.asList("java.lang.String")),
            new MethodSignature("java.lang.String", "toString", Arrays.asList())
        );

        // Check each method in the loaded class
        for (Method method : chatBotClass.getDeclaredMethods()) {
            boolean matched = false;
            for (MethodSignature expectedSignature : expectedMethodSignatures) {
                if (checkMethodSignature(method, expectedSignature.returnType, expectedSignature.methodName, expectedSignature.parameterTypes)) {
                    matched = true;
                    score += 1;
                    break;
                }
            }
            if (!matched) {
                System.err.println("Mismatched signature: found " + method.toString() 
                    + ", expected one of " + expectedMethodSignatures);
            }
        }

        scores.put("ChatBot", score);
        assertEquals(expectedMethodSignatures.size(), score, 
            "Not all method signatures matched for ChatBot");
    }

    @Test
    public void testChatBotPlatformMethodSignatures() throws Exception {
        int score = 0;
        Class<?> chatBotPlatformClass = loadClass("ChatBotPlatform");

        // Expected method signatures for ChatBotPlatform class
        List<MethodSignature> expectedMethodSignatures = Arrays.asList(
            new MethodSignature("boolean", "addChatBot", Arrays.asList("int")),
            new MethodSignature("java.lang.String", "getChatBotList", Arrays.asList()),
            new MethodSignature("java.lang.String", "interactWithBot", Arrays.asList("int", "java.lang.String"))
        );

        // Check each method in the loaded class
        for (Method method : chatBotPlatformClass.getDeclaredMethods()) {
            boolean matched = false;
            for (MethodSignature expectedSignature : expectedMethodSignatures) {
                if (checkMethodSignature(method, expectedSignature.returnType, expectedSignature.methodName, expectedSignature.parameterTypes)) {
                    matched = true;
                    score += 1;
                    break;
                }
            }
            if (!matched) {
                System.err.println("Mismatched signature: found " + method.toString() 
                    + ", expected one of " + expectedMethodSignatures);
            }
        }

        scores.put("ChatBotPlatform", score);
        assertEquals(expectedMethodSignatures.size(), score, 
            "Not all method signatures matched for ChatBotPlatform");
    }

    @Test
    public void testChatBotGeneratorMethodSignatures() throws Exception {
        int score = 0;
        Class<?> chatBotGeneratorClass = loadClass("ChatBotGenerator");

        // Expected method signatures for ChatBotGenerator class
        List<MethodSignature> expectedMethodSignatures = Arrays.asList(
            new MethodSignature("java.lang.String", "generateChatBotLLM", Arrays.asList("int"))
        );

        // Check each method in the loaded class
        for (Method method : chatBotGeneratorClass.getDeclaredMethods()) {
            boolean matched = false;
            for (MethodSignature expectedSignature : expectedMethodSignatures) {
                if (checkMethodSignature(method, expectedSignature.returnType, expectedSignature.methodName, expectedSignature.parameterTypes)) {
                    matched = true;
                    score += 1;
                    break;
                }
            }
            if (!matched) {
                System.err.println("Mismatched signature: found " + method.toString() 
                    + ", expected one of " + expectedMethodSignatures);
            }
        }

        scores.put("ChatBotGenerator", score);
        assertEquals(expectedMethodSignatures.size(), score, 
            "Not all method signatures matched for ChatBotGenerator");
    }

    // Helper class to store method signature details
    private static class MethodSignature {
        String returnType;
        String methodName;
        List<String> parameterTypes;

        MethodSignature(String returnType, String methodName, List<String> parameterTypes) {
            this.returnType = returnType;
            this.methodName = methodName;
            this.parameterTypes = parameterTypes;
        }

        @Override
        public String toString() {
            return returnType + " " + methodName + parameterTypes.toString();
        }
    }
}