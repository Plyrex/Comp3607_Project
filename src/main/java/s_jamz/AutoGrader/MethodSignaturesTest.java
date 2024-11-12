package s_jamz.AutoGrader;

import org.junit.jupiter.api.BeforeEach;
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
// import static org.junit.jupiter.api.Assertions.*;

public class MethodSignaturesTest {

    public static Map<String, Integer> scores = new HashMap<>();
    public static Map<String, List<String>> feedback = new HashMap<>();
    private Map<String, List<MethodSignature>> expectedMethodSignatures;

    public MethodSignaturesTest() {
        expectedMethodSignatures = new HashMap<>();
    }

    @BeforeEach
    public void setup() {
        expectedMethodSignatures.clear();
        expectedMethodSignatures.put("ChatBot", Arrays.asList(
            new MethodSignature("java.lang.String", "getChatBotName", Arrays.asList()),
            new MethodSignature("int", "getNumResponsesGenerated", Arrays.asList()),
            new MethodSignature("int", "getTotalNumResponsesGenerated", Arrays.asList()),
            new MethodSignature("int", "getTotalNumMessagesRemaining", Arrays.asList()),
            new MethodSignature("boolean", "limitReached", Arrays.asList()),
            new MethodSignature("java.lang.String", "generateResponse", Arrays.asList()),
            new MethodSignature("java.lang.String", "prompt", Arrays.asList("java.lang.String")),
            new MethodSignature("java.lang.String", "toString", Arrays.asList())
        ));
        expectedMethodSignatures.put("ChatBotPlatform", Arrays.asList(
            new MethodSignature("boolean", "addChatBot", Arrays.asList("int")),
            new MethodSignature("java.lang.String", "getChatBotList", Arrays.asList()),
            new MethodSignature("java.lang.String", "interactWithBot", Arrays.asList("int", "java.lang.String"))
        ));
        expectedMethodSignatures.put("ChatBotGenerator", Arrays.asList(
            new MethodSignature("java.lang.String", "generateChatBotLLM", Arrays.asList("int"))
        ));
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
        List<String> feedbackMessages = new ArrayList<>();
        Class<?> chatBotClass = loadClass("ChatBot");

        List<MethodSignature> expectedSignatures = expectedMethodSignatures.get("ChatBot");

        for (Method method : chatBotClass.getDeclaredMethods()) {
            boolean matched = false;
            for (MethodSignature expectedSignature : expectedSignatures) {
                if (checkMethodSignature(method, expectedSignature.returnType, expectedSignature.methodName, expectedSignature.parameterTypes)) {
                    matched = true;
                    score += 1;
                    feedbackMessages.add("Method signature '" + method.toString() + "' matches expected signature.");
                    break;
                }
            }
            if (!matched) {
                feedbackMessages.add("Mismatched signature: found " + method.toString() 
                    + ", expected one of " + expectedSignatures);
            }
        }

        scores.put("ChatBot", score);
        feedback.put("ChatBot", feedbackMessages);
    }

    @Test
    public void testChatBotPlatformMethodSignatures() throws Exception {
        int score = 0;
        List<String> feedbackMessages = new ArrayList<>();
        Class<?> chatBotPlatformClass = loadClass("ChatBotPlatform");

        List<MethodSignature> expectedSignatures = expectedMethodSignatures.get("ChatBotPlatform");

        for (Method method : chatBotPlatformClass.getDeclaredMethods()) {
            boolean matched = false;
            for (MethodSignature expectedSignature : expectedSignatures) {
                if (checkMethodSignature(method, expectedSignature.returnType, expectedSignature.methodName, expectedSignature.parameterTypes)) {
                    matched = true;
                    score += 1;
                    feedbackMessages.add("Method signature '" + method.toString() + "' matches expected signature.");
                    break;
                }
            }
            if (!matched) {
                feedbackMessages.add("Mismatched signature: found " + method.toString() 
                    + ", expected one of " + expectedSignatures);
            }
        }

        scores.put("ChatBotPlatform", score);
        feedback.put("ChatBotPlatform", feedbackMessages);
    }

    @Test
    public void testChatBotGeneratorMethodSignatures() throws Exception {
        int score = 0;
        List<String> feedbackMessages = new ArrayList<>();
        Class<?> chatBotGeneratorClass = loadClass("ChatBotGenerator");

        List<MethodSignature> expectedSignatures = expectedMethodSignatures.get("ChatBotGenerator");

        for (Method method : chatBotGeneratorClass.getDeclaredMethods()) {
            boolean matched = false;
            for (MethodSignature expectedSignature : expectedSignatures) {
                if (checkMethodSignature(method, expectedSignature.returnType, expectedSignature.methodName, expectedSignature.parameterTypes)) {
                    matched = true;
                    score += 1;
                    feedbackMessages.add("Method signature '" + method.toString() + "' matches expected signature.");
                    break;
                }
            }
            if (!matched) {
                feedbackMessages.add("Mismatched signature: found " + method.toString() 
                    + ", expected one of " + expectedSignatures);
            }
        }

        scores.put("ChatBotGenerator", score);
        feedback.put("ChatBotGenerator", feedbackMessages);
    }

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