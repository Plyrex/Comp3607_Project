package s_jamz.AutoGrader;

// import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
// import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodSignaturesTest {

    private static int totalScore;
    private int chatBotScore = 0;
    private int chatBotPlatformScore = 0;
    private int chatBotGeneratorScore = 0;

    private HashMap<String, Method[]> methodTest;
    private Map<String, List<MethodSignature>> expectedMethodSignatures;

    public MethodSignaturesTest() {
        totalScore = 0;
        methodTest = new HashMap<>();
        expectedMethodSignatures = new HashMap<>();
    }

    @BeforeEach
    public void setup() {
        methodTest.clear();
        expectedMethodSignatures.clear();
        try {
            loadClassDetails("ChatBot");
            loadClassDetails("ChatBotPlatform");
            loadClassDetails("ChatBotGenerator");
        } catch (Exception e) {
            System.err.println("Could not load class details: " + e.getMessage());
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
            methodTest.put(className, getMethods(class1));
            if (className.equals("ChatBot")) {
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
            } else if (className.equals("ChatBotPlatform")) {
                expectedMethodSignatures.put("ChatBotPlatform", Arrays.asList(
                    new MethodSignature("boolean", "addChatBot", Arrays.asList("int")),
                    new MethodSignature("java.lang.String", "getChatBotList", Arrays.asList()),
                    new MethodSignature("java.lang.String", "interactWithBot", Arrays.asList("int", "java.lang.String"))
                ));
            } else if (className.equals("ChatBotGenerator")) {
                expectedMethodSignatures.put("ChatBotGenerator", Arrays.asList(
                    new MethodSignature("java.lang.String", "generateChatBotLLM", Arrays.asList("int"))
                ));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
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
    public void chatBotMethodSignaturesTest() {
        System.out.println("ChatBot Method Signatures Test. \n");
        Method[] chatBotMethods = methodTest.get("ChatBot");
        int score = 0;

        List<MethodSignature> expectedSignatures = expectedMethodSignatures.get("ChatBot");

        for (Method method : chatBotMethods) {
            boolean matched = false;
            for (MethodSignature expectedSignature : expectedSignatures) {
                if (checkMethodSignature(method, expectedSignature.returnType, expectedSignature.methodName, expectedSignature.parameterTypes)) {
                    matched = true;
                    score += 1;
                    System.out.println("Method signature '" + method.toString() + "' matches expected signature.");
                    break;
                }
            }
            if (!matched) {
                System.out.println("Mismatched signature: found " + method.toString() 
                    + ", expected one of " + expectedSignatures);
            }
        }

        chatBotScore = score;
        totalScore += chatBotScore;
        System.out.println("ChatBot Class Score: " + chatBotScore + "/36");
    }

    @Test
    public void chatBotPlatformMethodSignaturesTest() {
        System.out.println("ChatBotPlatform Method Signatures Test. \n");
        Method[] chatBotPlatformMethods = methodTest.get("ChatBotPlatform");
        int score = 0;

        List<MethodSignature> expectedSignatures = expectedMethodSignatures.get("ChatBotPlatform");

        for (Method method : chatBotPlatformMethods) {
            boolean matched = false;
            for (MethodSignature expectedSignature : expectedSignatures) {
                if (checkMethodSignature(method, expectedSignature.returnType, expectedSignature.methodName, expectedSignature.parameterTypes)) {
                    matched = true;
                    score += 1;
                    System.out.println("Method signature '" + method.toString() + "' matches expected signature.");
                    break;
                }
            }
            if (!matched) {
                System.out.println("Mismatched signature: found " + method.toString() 
                    + ", expected one of " + expectedSignatures);
            }
        }

        chatBotPlatformScore = score;
        totalScore += chatBotPlatformScore;
        System.out.println("ChatBotPlatform Class Score: " + chatBotPlatformScore + "/20");
    }

    @Test
    public void chatBotGeneratorMethodSignaturesTest() {
        System.out.println("ChatBotGenerator Method Signatures Test. \n");
        Method[] chatBotGeneratorMethods = methodTest.get("ChatBotGenerator");
        int score = 0;

        List<MethodSignature> expectedSignatures = expectedMethodSignatures.get("ChatBotGenerator");

        for (Method method : chatBotGeneratorMethods) {
            boolean matched = false;
            for (MethodSignature expectedSignature : expectedSignatures) {
                if (checkMethodSignature(method, expectedSignature.returnType, expectedSignature.methodName, expectedSignature.parameterTypes)) {
                    matched = true;
                    score += 1;
                    System.out.println("Method signature '" + method.toString() + "' matches expected signature.");
                    break;
                }
            }
            if (!matched) {
                System.out.println("Mismatched signature: found " + method.toString() 
                    + ", expected one of " + expectedSignatures);
            }
        }

        chatBotGeneratorScore = score;
        totalScore += chatBotGeneratorScore;
        System.out.println("ChatBotGenerator Class Score: " + chatBotGeneratorScore + "/7");
    }

    @AfterAll
    public static void calculateTotal() {
        System.out.println("Total Score = " + totalScore + "/63 \n");
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