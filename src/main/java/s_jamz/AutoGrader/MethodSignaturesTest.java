package s_jamz.AutoGrader;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import s_jamz.CompositePattern.TestResultLeaf;

public class MethodSignaturesTest {

    private static int totalScore;
    private static int chatBotScore = 0;
    private static int chatBotPlatformScore = 0;
    private static int chatBotGeneratorScore = 0;

    private HashMap<String, Method[]> methodTest;
    private static HashMap<String, TestResultLeaf> testResults = new HashMap<>();

    public MethodSignaturesTest() {
        methodTest = new HashMap<>();
    }

    @BeforeEach
    public void setup() {
        methodTest.clear();
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

    private Class<?> loadClass(String className, URL studentBinDir) throws Exception {
        URL[] urls = {studentBinDir};
        try (IsolatedClassLoader loader = new IsolatedClassLoader(urls)) {
            return Class.forName(className, true, loader);
        }
    }

    public void loadClassDetails(String className) throws Exception {
        File studentFoldersDir = new File(System.getProperty("user.dir") + "/src/main/resources/StudentFolders/");
        if (!studentFoldersDir.exists() || !studentFoldersDir.isDirectory()) {
            throw new IllegalArgumentException("Invalid student folders path: " + studentFoldersDir.getAbsolutePath());
        }

        for (File studentDir : studentFoldersDir.listFiles()) {
            if (studentDir.isDirectory()) {
                File binDir = new File(studentDir, "bin");
                if (binDir.exists() && binDir.isDirectory()) {
                    URL studentBinDir = binDir.toURI().toURL();
                    // System.out.println("Loading class: " + className + " from student folder: " + studentDir.getName());
                    try {
                        Class<?> class1 = loadClass(className, studentBinDir);
                        methodTest.put(className, getMethods(class1));
                    } catch (ClassNotFoundException e) {
                        // Continue searching in other student folders
                    }
                }
            }
        }
    }

    private boolean checkMethodSignature(Method method, String expectedReturnType, String expectedMethodName, List<String> expectedParameterTypes) {
        if (!method.getReturnType().getSimpleName().equals(expectedReturnType)) return false;
        if (!method.getName().equals(expectedMethodName)) return false;

        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != expectedParameterTypes.size()) return false;
        for (int i = 0; i < parameterTypes.length; i++) {
            if (!parameterTypes[i].getSimpleName().equals(expectedParameterTypes.get(i))) return false;
        }
        return true;
    }

    @Test
    public void chatBotMethodSignaturesTest() {
        System.out.println("ChatBot Method Signatures Test. \n");
        Method[] chatBotMethods = methodTest.get("ChatBot");
        int score = 0;
        StringBuilder feedback = new StringBuilder();

        List<String> expectedSignatures = Arrays.asList(
            "String getChatBotName()",
            "int getNumResponsesGenerated()",
            "int getTotalNumResponsesGenerated()",
            "int getTotalNumMessagesRemaining()",
            "boolean limitReached()",
            "String generateResponse()",
            "String prompt(String)",
            "String toString()"
        );

        for (Method method : chatBotMethods) {
            String methodSignature = method.getReturnType().getSimpleName() + " " + method.getName() + "(";
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                methodSignature += parameterTypes[i].getSimpleName();
                if (i < parameterTypes.length - 1) {
                    methodSignature += ", ";
                }
            }
            methodSignature += ")";

            if(methodSignature.equals("int getTotalNumResponsesGenerated()")){
                if(Modifier.isStatic(method.getModifiers())){
                    feedback.append(methodSignature).append(" is static. ");
                }
                else{
                    feedback.append(methodSignature).append(" is not static. ");
                    score--;
                }
            }

            if(methodSignature.equals("int getTotalNumMessagesRemaining()")){
                if(Modifier.isStatic(method.getModifiers())){
                    feedback.append(methodSignature).append(" is static. ");
                }
                else{
                    feedback.append(methodSignature).append(" is not static. ");
                    score--;
                }
            }

            if(methodSignature.equals("boolean limitReached()")){
                if(Modifier.isStatic(method.getModifiers())){
                    feedback.append(methodSignature).append(" is static. ");
                }
                else{
                    feedback.append(methodSignature).append(" is not static. ");
                    score--;
                }
            }

            if(methodSignature.equals("String generateResponse()")){
                if(Modifier.isPrivate(method.getModifiers())){
                    feedback.append(methodSignature).append(" is private. ");
                }
                else{
                    feedback.append(methodSignature).append(" is not private. ");
                    score--;
                }
            }


            if (expectedSignatures.contains(methodSignature)) {
                score += 1;
                feedback.append("Method signature '").append(methodSignature).append("' matches expected signature.\n");
            } else {
                feedback.append("Mismatched signature: found ").append(methodSignature)
                        .append(", expected one of ").append(expectedSignatures).append("\n");
            }
        }

        chatBotScore = score;
        totalScore += chatBotScore;
        feedback.append("ChatBot Class Score: ").append(chatBotScore).append("/8\n");
        testResults.put("ChatBot", new TestResultLeaf(chatBotScore, feedback.toString()));
    }

    @Test
    public void chatBotPlatformMethodSignaturesTest() {
        System.out.println("ChatBotPlatform Method Signatures Test. \n");
        Method[] chatBotPlatformMethods = methodTest.get("ChatBotPlatform");
        int score = 0;
        StringBuilder feedback = new StringBuilder();

        List<String> expectedSignatures = Arrays.asList(
            "boolean addChatBot(int)",
            "String getChatBotList()",
            "String interactWithBot(int, String)"
        );

        for (Method method : chatBotPlatformMethods) {
            String methodSignature = method.getReturnType().getSimpleName() + " " + method.getName() + "(";
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                methodSignature += parameterTypes[i].getSimpleName();
                if (i < parameterTypes.length - 1) {
                    methodSignature += ", ";
                }
            }
            methodSignature += ")";

            if (expectedSignatures.contains(methodSignature)) {
                score += 1;
                feedback.append("Method signature '").append(methodSignature).append("' matches expected signature.\n");
            } else {
                feedback.append("Mismatched signature: found ").append(methodSignature)
                        .append(", expected one of ").append(expectedSignatures).append("\n");
            }
        }

        chatBotPlatformScore = score;
        totalScore += chatBotPlatformScore;
        feedback.append("ChatBotPlatform Class Score: ").append(chatBotPlatformScore).append("/3\n");
        testResults.put("ChatBotPlatform", new TestResultLeaf(chatBotPlatformScore, feedback.toString()));
    }

    @Test
    public void chatBotGeneratorMethodSignaturesTest() {
        System.out.println("ChatBotGenerator Method Signatures Test. \n");
        Method[] chatBotGeneratorMethods = methodTest.get("ChatBotGenerator");
        int score = 0;
        StringBuilder feedback = new StringBuilder();

        List<String> expectedSignatures = Arrays.asList(
            "String generateChatBotLLM(int)"
        );

        for (Method method : chatBotGeneratorMethods) {
            String methodSignature = method.getReturnType().getSimpleName() + " " + method.getName() + "(";
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                methodSignature += parameterTypes[i].getSimpleName();
                if (i < parameterTypes.length - 1) {
                    methodSignature += ", ";
                }
            }
            methodSignature += ")";

            if (expectedSignatures.contains(methodSignature)) {
                score += 1;
                feedback.append("Method signature '").append(methodSignature).append("' matches expected signature.\n");
            } else {
                feedback.append("Mismatched signature: found ").append(methodSignature)
                        .append(", expected one of ").append(expectedSignatures).append("\n");
            }
        }

        chatBotGeneratorScore = score;
        totalScore += chatBotGeneratorScore;
        feedback.append("ChatBotGenerator Class Score: ").append(chatBotGeneratorScore).append("/1\n");
        testResults.put("ChatBotGenerator", new TestResultLeaf(chatBotGeneratorScore, feedback.toString()));
    }

    @AfterAll
    public static void calculateTotal() {
        System.out.println("Total Score = " + totalScore + "/12 \n");
        chatBotGeneratorScore = 0;
        chatBotPlatformScore = 0;
        chatBotScore = 0;
        totalScore = 0;
    }

    public static HashMap<String, TestResultLeaf> getTestResults() {
        return testResults;
    }
}