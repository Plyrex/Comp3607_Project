package s_jamz.AutoGrader;

// import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import s_jamz.CompositePattern.TestResultLeaf;

// import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MethodBehaviourTest {

    private static int totalScore;
    private static int chatBotScore = 0;
    private static int chatBotPlatformScore = 0;
    private static int chatBotGeneratorScore = 0;

    private static HashMap<String, TestResultLeaf> testResults = new HashMap<>();
    private static int chatBotScores = 0;
    private static int chatBotPlatformScores = 0;
    private static int chatBotGeneratorScores = 0;
    public static Map<String, Integer> scores = new HashMap<>();
    private static Map<String, Method[]> methodTest = new HashMap<String, Method[]>();
    private HashMap<String, Field[]> attributeTest = new HashMap<String, Field[]>();

    public MethodBehaviourTest() {
        scores.put("ChatBot", 0);
        scores.put("ChatBotPlatform", 0);
        scores.put("ChatBotGenerator", 0);        
    }

    @BeforeAll
    public static void init() {
        System.out.println("Running Method Behaviour Tests");
        chatBotGeneratorScores = 0;
        chatBotPlatformScores = 0;
        chatBotScores = 0;
    }

    @BeforeEach
    public void resetState() {
        try {
            Class<?> chatBotClass = loadClass("ChatBot");
            Method resetMethod = chatBotClass.getMethod("resetState");
            resetMethod.invoke(null);

            Class<?> chatBotPlatformClass = loadClass("ChatBotPlatform");
            resetMethod = chatBotPlatformClass.getMethod("resetState");
            resetMethod.invoke(null);
        } catch (NoSuchMethodException e) {
            // If the resetState method does not exist, ignore the exception
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

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
    public void testChatBotBehaviour() throws Exception {
        System.out.println("Running method tests for class: Chatbot");
        Class<?> chatBotClass = loadClass("ChatBot");
        Constructor<?> defaultConstructor = chatBotClass.getDeclaredConstructor();
        int score = 0;
        StringBuilder feedback = new StringBuilder();
        defaultConstructor.setAccessible(true);
        Object chatBot = defaultConstructor.newInstance();
        HashMap<String, Integer> chatBotScores = new HashMap<>();
        chatBotScores.put("getChatBotName", 0);
        chatBotScores.put("generateResponse", 0);
        chatBotScores.put("getNumResponsesGenerated", 0);
        chatBotScores.put("getTotalNumResponsesGenerated", 0);
        chatBotScores.put("getTotalNumMessagesRemaining", 0);
        chatBotScores.put("limitReached", 0);
        chatBotScores.put("prompt", 0);
        chatBotScores.put("toString", 0);


        // System.out.println("Testing getChatBotName method");
        try{
            Object chatBotGPT = defaultConstructor.newInstance();
            Method getChatBotName = chatBotClass.getMethod("getChatBotName");
            String chatBotName = (String) getChatBotName.invoke(chatBotGPT);
            if (chatBotName.contains("ChatGPT-3.5")) {
                chatBotScores.put("getChatBotName", 1);
                feedback.append("getChatBotName method passed.\n");
            } else {
                feedback.append("getChatBotName method failed. Expected 'ChatGPT-3.5', but got '").append(chatBotName).append("'.\n");}
                        
            Constructor<?> llmConstructor = chatBotClass.getDeclaredConstructor(int.class);
            llmConstructor.setAccessible(true);
            Object chatBotWithLLM = llmConstructor.newInstance(1);
            chatBotName = (String) getChatBotName.invoke(chatBotWithLLM);
            if (chatBotName.contains("LLaMa")) {
                chatBotScores.put("getChatBotName", 1);
                feedback.append("getChatBotName method passed for LLaMa.\n");
            } else {
                feedback.append("getChatBotName method failed for LLaMa. Expected 'LLaMa', but got '").append(chatBotName).append("'.\n");}


            Constructor<?> mistralConstructor = chatBotClass.getDeclaredConstructor(int.class);
            mistralConstructor.setAccessible(true);
            Object chatBotWith2 = mistralConstructor.newInstance(2);
            chatBotName = (String) getChatBotName.invoke(chatBotWith2);
            if (chatBotName.contains("Mistral7B")) {
                chatBotScores.put("getChatBotName", 1);
                feedback.append("getChatBotName method passed for Mistral7B.\n");
            } else {
                feedback.append("getChatBotName method failed for Mistral7B. Expected 'Mistral7B', but got '").append(chatBotName).append("'.\n");}

            Constructor<?> claudeConstructor = chatBotClass.getDeclaredConstructor(int.class);
            claudeConstructor.setAccessible(true);
            Object chatBotWith4 = claudeConstructor.newInstance(4);
            chatBotName = (String) getChatBotName.invoke(chatBotWith4);
            if (chatBotName.contains("Claude")) {
                chatBotScores.put("getChatBotName", 1);
                feedback.append("getChatBotName method passed for Claude.\n");
            } else {
                feedback.append("getChatBotName method failed for Claude. Expected 'Claude', but got '").append(chatBotName).append("'.\n");}

            Constructor<?> solarConstructor = chatBotClass.getDeclaredConstructor(int.class);
            solarConstructor.setAccessible(true);
            Object chatBotWith5 = solarConstructor.newInstance(5);
            chatBotName = (String) getChatBotName.invoke(chatBotWith5);
            if (chatBotName.contains("Solar")) {
                chatBotScores.put("getChatBotName", 1);
                feedback.append("getChatBotName method passed for Solar.\n");
            } else {
                feedback.append("getChatBotName method failed for Solar. Expected 'Solar', but got '").append(chatBotName).append("'.\n");}
            
            Constructor<?> newConstructor = chatBotClass.getDeclaredConstructor(int.class);
            newConstructor.setAccessible(true);
            Object chatBotWith6 = newConstructor.newInstance(6);
            chatBotName = (String) getChatBotName.invoke(chatBotWith6);
            if (chatBotName.contains("ChatGPT-3.5")) {
                chatBotScores.put("getChatBotName", 1);
                feedback.append("getChatBotName method passed for ChatGPT-3.5.\n");
            } else {
                feedback.append("getChatBotName method failed for ChatGPT-3.5. Expected 'ChatGPT-3.5', but got '").append(chatBotName).append("'.\n");}
        } 
        catch(Exception e){
            feedback.append("Error in getChatBotName method: ").append(e.getMessage()).append("\n");
        }


        // //Test generateResponse method
        // System.out.println("Testing generateResponse method");
        try{
            Method generateResponse = chatBotClass.getDeclaredMethod("generateResponse");
            generateResponse.setAccessible(true);
            String response = (String) generateResponse.invoke(chatBot);
            if (response.contains("ChatGPT-3.5")) {
                chatBotScores.put("generateResponse", 1);
                feedback.append("generateResponse method passed.\n");
            } else {
                feedback.append("generateResponse method failed. Expected response containing 'ChatGPT-3.5', but got '").append(response).append("'.\n");}
        }
        catch(Exception e){
            feedback.append("Error in generateResponse method: ").append(e.getMessage()).append("\n");
        }

        // Test getNumResponsesGenerated method
        // System.out.println("Testing getNumResponsesGenerated method");
        try{
            Method getNumResponsesGenerated = chatBotClass.getMethod("getNumResponsesGenerated");
            int numResponsesGenerated = (int) getNumResponsesGenerated.invoke(chatBot);
            if (numResponsesGenerated == 0) {
                chatBotScores.put("getNumResponsesGenerated", 1);
                feedback.append("getNumResponsesGenerated method passed.\n");
            } else {
                feedback.append("getNumResponsesGenerated method failed. Expected 0, but got ").append(numResponsesGenerated).append(".\n");}    
        }
        catch(Exception e){
            feedback.append("Error in getNumResponsesGenerated method: ").append(e.getMessage()).append("\n");
        }

        // Test getTotalNumResponsesGenerated method
        // System.out.println("Testing getTotalNumResponsesGenerated method");
        try{
            Method getTotalNumResponsesGenerated = chatBotClass.getMethod("getTotalNumResponsesGenerated");
            int totalNumResponsesGenerated = (int) getTotalNumResponsesGenerated.invoke(null);
            if (totalNumResponsesGenerated == 0) {
                chatBotScores.put("getTotalNumResponsesGenerated", 1);
                feedback.append("getTotalNumResponsesGenerated method passed.\n");
            } else {
                feedback.append("getTotalNumResponsesGenerated method failed. Expected 0, but got ").append(totalNumResponsesGenerated).append(".\n");}
        }
        catch(Exception e){
            feedback.append("Error in getTotalNumResponsesGenerated method: ").append(e.getMessage()).append("\n");
        }

        // Test getTotalNumMessagesRemaining method
        // System.out.println("Testing getTotalNumMessagesRemaining method");
        try{
            Method getTotalNumMessagesRemaining = chatBotClass.getMethod("getTotalNumMessagesRemaining");
            int totalNumMessagesRemaining = (int) getTotalNumMessagesRemaining.invoke(null);
            if (totalNumMessagesRemaining == 10) {
                chatBotScores.put("getTotalNumMessagesRemaining", 1);
                feedback.append("getTotalNumMessagesRemaining method passed.\n");
            } else {
                feedback.append("getTotalNumMessagesRemaining method failed. Expected 10, but got ").append(totalNumMessagesRemaining).append(".\n");}
        }
        catch(Exception e){
            feedback.append("Error in getTotalNumMessagesRemaining method: ").append(e.getMessage()).append("\n");
        }

        // Test limitReached method
        // System.out.println("Testing limitReached method");
        try{
            Method limitReached = chatBotClass.getMethod("limitReached");
            boolean isLimitReached = (boolean) limitReached.invoke(null);
            if (!isLimitReached) {
                chatBotScores.put("limitReached", 1);
                feedback.append("limitReached method passed.\n");
            } else {
                feedback.append("limitReached method failed. Expected false, but got true.\n");}
        }
        catch(Exception e){
            feedback.append("Error in limitReached method: ").append(e.getMessage()).append("\n");
        }

        // Test prompt method
        // System.out.println("Testing prompt method");
        try{
            Method prompt = chatBotClass.getMethod("prompt", String.class);
            String promptResponse = (String) prompt.invoke(chatBot, "Hello");
            if (promptResponse.contains("Response from")) {
                chatBotScores.put("prompt", 1);
                feedback.append("prompt method passed.\n");
            } else {
                feedback.append("prompt method failed. Expected response containing 'Response from', but got '").append(promptResponse).append("'.\n");}
        }
        catch(Exception e){
            feedback.append("Error in prompt method: ").append(e.getMessage()).append("\n");
        }

        // Test toString method
        // System.out.println("Testing toString method");
        try{
            Method toStringMethod = chatBotClass.getMethod("toString");
            String toStringResponse = (String) toStringMethod.invoke(chatBot);
            if (toStringResponse.contains("ChatBot Name: ChatGPT-3.5")) {
                chatBotScores.put("toString", 1);
                feedback.append("toString method passed.\n");
            } else {
                feedback.append("toString method failed. Expected 'ChatBot Name: ChatGPT-3.5', but got '").append(toStringResponse).append("'.\n");}
        }
        catch(Exception e){
            feedback.append("Error in toString method: ").append(e.getMessage()).append("\n");
        }

        for (Map.Entry<String, Integer> entry : chatBotScores.entrySet()) {
            score += entry.getValue();
        }

        chatBotScore = score;
        totalScore += chatBotScore;
        feedback.append("ChatBot Class Score: ").append(chatBotScore).append("/36\n");
        testResults.put("ChatBot", new TestResultLeaf(chatBotScore, feedback.toString()));
    }


    @Test
    public void testChatBotPlatformBehaviour() throws Exception {
        System.out.println("Running method tests for class: ChatBotPlatform");
        Class<?> chatBotPlatformClass = loadClass("ChatBotPlatform");
        Constructor<?> defaultConstructor = chatBotPlatformClass.getDeclaredConstructor();
        int score = 0;
        StringBuilder feedback = new StringBuilder();
        defaultConstructor.setAccessible(true);
        Object chatBotPlatform = defaultConstructor.newInstance();
        HashMap<String, Integer> chatBotPlatformScores = new HashMap<>();
        chatBotPlatformScores.put("addChatBot", 0);
        chatBotPlatformScores.put("interactWithBot", 0);
        chatBotPlatformScores.put("getChatBotList", 0);

        // Test addChatBot method
        // System.out.println("Testing addChatBot method");
        try{
            Method addChatBot = chatBotPlatformClass.getMethod("addChatBot", int.class);
            boolean addChatBotResult = (boolean) addChatBot.invoke(chatBotPlatform, 1);
            if (addChatBotResult) {
                chatBotPlatformScores.put("addChatBot", 1);
                feedback.append("addChatBot method passed.\n");
            } else {
                feedback.append("addChatBot method failed. Expected true, but got false.\n");}
        }
        catch(Exception e){
            feedback.append("Error in addChatBot method: ").append(e.getMessage()).append("\n");
        }

        // Test interactWithBot method
        // System.out.println("Testing interactWithBot method");
        try{
            Method interactWithBot = chatBotPlatformClass.getMethod("interactWithBot", int.class, String.class);
            String interaction = (String) interactWithBot.invoke(chatBotPlatform, 0, "Hello");
            if (interaction.contains("Response from")) {
                chatBotPlatformScores.put("interactWithBot", 1);
                feedback.append("interactWithBot method passed.\n");
            } else {
                feedback.append("interactWithBot method failed. Expected response containing 'Response from', but got '").append(interaction).append("'.\n");}
        }
        catch(Exception e){
            feedback.append("Error in interactWithBot method: ").append(e.getMessage()).append("\n");
        }

        // Test getChatBotList method
        // System.out.println("Testing getChatBotList method");
        try{
            Method getChatBotList = chatBotPlatformClass.getMethod("getChatBotList");
            String chatBotList = (String) getChatBotList.invoke(chatBotPlatform);
            if (chatBotList.contains("ChatGPT-3.5")) {
                chatBotPlatformScores.put("getChatBotList", 1);
                feedback.append("getChatBotList method passed.\n");
            } else {
                feedback.append("getChatBotList method failed. Expected list containing 'ChatGPT-3.5', but got '").append(chatBotList).append("'.\n");}
        }
        catch(Exception e){
            feedback.append("Error in getChatBotList method: ").append(e.getMessage()).append("\n");
        }

        for (Map.Entry<String, Integer> entry : chatBotPlatformScores.entrySet()) {
            score += entry.getValue();
        }

        chatBotPlatformScore = score;
        totalScore += chatBotPlatformScore;
        feedback.append("ChatBotPlatform Class Score: ").append(chatBotPlatformScore).append("/20\n");
        testResults.put("ChatBotPlatform", new TestResultLeaf(chatBotPlatformScore, feedback.toString()));
    }


    @Test
    public void testChatBotGeneratorBehaviour() throws Exception {
        System.out.println("Running method tests for class: ChatBotGenerator");
        Class<?> chatBotGeneratorClass = loadClass("ChatBotGenerator");
        Constructor<?> defaultConstructor = chatBotGeneratorClass.getDeclaredConstructor();
        int score = 0;
        StringBuilder feedback = new StringBuilder();
        defaultConstructor.setAccessible(true);
        Object chatBotGenerator = defaultConstructor.newInstance();
        HashMap<String, Integer> chatBotGeneratorScores = new HashMap<>();
        chatBotGeneratorScores.put("generateChatBotLLM", 0);

        // Test generateChatBotLLM method
        // System.out.println("Testing generateChatBotLLM method");
        try{
            Method generateChatBotLLM = chatBotGeneratorClass.getMethod("generateChatBotLLM", int.class);
            String llmName = (String) generateChatBotLLM.invoke(null, 1);
            if (llmName.contains("LLaMa")) {
                chatBotGeneratorScores.put("generateChatBotLLM", 1);
                feedback.append("generateChatBotLLM method passed.\n");
            } else {
                feedback.append("generateChatBotLLM method failed. Expected 'LLaMa', but got '").append(llmName).append("'.\n");}
        }
        catch(Exception e){
            feedback.append("Error in generateChatBotLLM method: ").append(e.getMessage()).append("\n");
        }

        for (Map.Entry<String, Integer> entry : chatBotGeneratorScores.entrySet()) {
            score += entry.getValue();
        }

        chatBotGeneratorScore = score;
        totalScore += chatBotGeneratorScore;
        feedback.append("ChatBotGenerator Class Score: ").append(chatBotGeneratorScore).append("/7\n");
        testResults.put("ChatBotGenerator", new TestResultLeaf(chatBotGeneratorScore, feedback.toString()));
    }


    @AfterEach
    public void printResults() {
        System.out.println("Method Behaviour Test Results: " + testResults);
    }

    public static HashMap<String, TestResultLeaf> getTestResults() {
        return testResults;
    }
}

