package s_jamz.AutoGrader;

// import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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

    public static Map<String, Integer> scores = new HashMap<>();
    private static Map<String, Method[]> methodTest = new HashMap<String, Method[]>();
    private HashMap<String, Field[]> attributeTest = new HashMap<String, Field[]>();

    public MethodBehaviourTest() {
        scores.put("ChatBot", 0);
        scores.put("ChatBotPlatform", 0);
        scores.put("ChatBotGenerator", 0);        
    }

    @BeforeEach
    public void initialize(){
        methodTest.clear();
        attributeTest.clear();
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
        int chatBotNameScore = 0;
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


        System.out.println("Testing getChatBotName method");
        try{
            Object chatBotGPT = defaultConstructor.newInstance();
            Method getChatBotName = chatBotClass.getMethod("getChatBotName");
            String chatBotName = (String) getChatBotName.invoke(chatBotGPT);
            if(chatBotName.contains("ChatGPT-3.5")){  chatBotNameScore += 1; }
                        
            Constructor<?> llmConstructor = chatBotClass.getDeclaredConstructor(int.class);
            llmConstructor.setAccessible(true);
            Object chatBotWithLLM = llmConstructor.newInstance(1);
            chatBotName = (String) getChatBotName.invoke(chatBotWithLLM);
            if(chatBotName.contains("LLaMa")){ chatBotNameScore += 1;}


            Constructor<?> mistralConstructor = chatBotClass.getDeclaredConstructor(int.class);
            mistralConstructor.setAccessible(true);
            Object chatBotWith2 = mistralConstructor.newInstance(2);
            chatBotName = (String) getChatBotName.invoke(chatBotWith2);
            if(chatBotName.contains("Mistral7B")){ chatBotNameScore += 1; }

            Constructor<?> claudeConstructor = chatBotClass.getDeclaredConstructor(int.class);
            claudeConstructor.setAccessible(true);
            Object chatBotWith4 = claudeConstructor.newInstance(4);
            chatBotName = (String) getChatBotName.invoke(chatBotWith4);
            if(chatBotName.contains("Claude")){ chatBotNameScore += 1; }

            Constructor<?> solarConstructor = chatBotClass.getDeclaredConstructor(int.class);
            solarConstructor.setAccessible(true);
            Object chatBotWith5 = solarConstructor.newInstance(5);
            chatBotName = (String) getChatBotName.invoke(chatBotWith5);
            if(chatBotName.contains("Solar")){ chatBotNameScore += 1; }
            
            Constructor<?> newConstructor = chatBotClass.getDeclaredConstructor(int.class);
            newConstructor.setAccessible(true);
            Object chatBotWith6 = newConstructor.newInstance(6);
            chatBotName = (String) getChatBotName.invoke(chatBotWith6);
            if(chatBotName.contains("ChatGPT-3.5")){ chatBotNameScore += 1; }
        } 
        catch(Exception e){
            System.err.println("Error in getChatBotName method: " + e.getMessage());
        }

        if(chatBotNameScore == 6){ chatBotScores.put("getChatBotName", 1); System.out.println("Test passed"); }
        else{ System.out.println("Test failed");}

        // //Test generateResponse method
        System.out.println("Testing generateResponse method");
        try{
            Method generateResponse = chatBotClass.getDeclaredMethod("generateResponse");
            generateResponse.setAccessible(true);
            String response = (String) generateResponse.invoke(chatBot);
            if(response.contains("ChatGPT-3.5")){ chatBotScores.put("generateResponse", 1);  System.out.println("Test passed"); }
            else{ System.out.println("Test failed");}
        }
        catch(Exception e){
            System.err.println("Error in generateResponse method: " + e.getMessage());
        }

        // Test getNumResponsesGenerated method
        System.out.println("Testing getNumResponsesGenerated method");
        try{
            Method getNumResponsesGenerated = chatBotClass.getMethod("getNumResponsesGenerated");
            int numResponsesGenerated = (int) getNumResponsesGenerated.invoke(chatBot);
            if(numResponsesGenerated == 0){ chatBotScores.put("getNumResponsesGenerated", 1);  System.out.println("Test passed"); }
            else{ System.out.println("Test failed");}
            
        }
        catch(Exception e){
            System.err.println("Error in getNumResponsesGenerated method: " + e.getMessage());
        } 

        // Test getTotalNumResponsesGenerated method
        System.out.println("Testing getTotalNumResponsesGenerated method");
        try{
            Method getTotalNumResponsesGenerated = chatBotClass.getMethod("getTotalNumResponsesGenerated");
            int totalNumResponsesGenerated = (int) getTotalNumResponsesGenerated.invoke(null);
            if(totalNumResponsesGenerated == 0){ chatBotScores.put("getTotalNumResponsesGenerated", 1);  System.out.println("Test passed"); }
            else{ System.out.println("Test failed");}
        }
        catch(Exception e){
            System.err.println("Error in getTotalNumResponsesGenerated method: " + e.getMessage());
        }

        // Test getTotalNumMessagesRemaining method
        System.out.println("Testing getTotalNumMessagesRemaining method");
        try{
            Method getTotalNumMessagesRemaining = chatBotClass.getMethod("getTotalNumMessagesRemaining");
            int totalNumMessagesRemaining = (int) getTotalNumMessagesRemaining.invoke(null);
            if(totalNumMessagesRemaining == 10){ chatBotScores.put("getTotalNumMessagesRemaining", 1); System.out.println("Test passed"); }
            else{ System.out.println("Test failed");}
        }
        catch(Exception e){
            System.err.println("Error in getTotalNumMessagesRemaining method: " + e.getMessage());
        }

        // Test limitReached method
        System.out.println("Testing limitReached method");
        try{
            Method limitReached = chatBotClass.getMethod("limitReached");
            boolean isLimitReached = (boolean) limitReached.invoke(null);
            if(!isLimitReached){ chatBotScores.put("limitReached", 1); System.out.println("Test passed"); }
            else{ System.out.println("Test failed");}
        }
        catch(Exception e){
            System.err.println("Error in limitReached method: " + e.getMessage());
        }

        // Test prompt method
        System.out.println("Testing prompt method");
        try{
            Method prompt = chatBotClass.getMethod("prompt", String.class);
            String promptResponse = (String) prompt.invoke(chatBot, "Hello");
            if(promptResponse.contains("Response from")){ chatBotScores.put("prompt", 1);  System.out.println("Test passed"); }
            else{ System.out.println("Test failed");}
        }
        catch(Exception e){
            System.err.println("Error in prompt method: " + e.getMessage());
        }

        // Test toString method
        System.out.println("Testing toString method");
        try{
            Method toStringMethod = chatBotClass.getMethod("toString");
            String toStringResponse = (String) toStringMethod.invoke(chatBot);
            if(toStringResponse.contains("ChatBot Name: ChatGPT-3.5")){ chatBotScores.put("toString", 1); System.out.println("Test passed"); }
            else{ System.out.println("Test failed"); }
        }
        catch(Exception e){
            System.err.println("Error in toString method: " + e.getMessage());
        }

        for (Map.Entry<String, Integer> entry : chatBotScores.entrySet()) {
            score += entry.getValue();
        }

        scores.put("ChatBot", score);

        assertEquals(6, score);
    }


    @Test
    public void testChatBotPlatformBehaviour() throws Exception {
        System.out.println("Running method tests for class: ChatBotPlatform");
        Class<?> chatBotPlatformClass = loadClass("ChatBotPlatform");
        Constructor<?> defaultConstructor = chatBotPlatformClass.getDeclaredConstructor();
        int score = 0;
        defaultConstructor.setAccessible(true);
        Object chatBotPlatform = defaultConstructor.newInstance();
        HashMap<String, Integer> chatBotPlatformScores = new HashMap<>();
        chatBotPlatformScores.put("addChatBot", 0);
        chatBotPlatformScores.put("interactWithBot", 0);
        chatBotPlatformScores.put("getChatBotList", 0);

        // Test addChatBot method
        System.out.println("Testing addChatBot method");
        try{
            Method addChatBot = chatBotPlatformClass.getMethod("addChatBot", int.class);
            boolean addChatBotResult = (boolean) addChatBot.invoke(chatBotPlatform, 1);
            if(addChatBotResult){ chatBotPlatformScores.put("addChatBot", 1); System.out.println("Test passed"); }
            else{ System.out.println("Test failed"); }
        }
        catch(Exception e){
            System.err.println("Error in addChatBot method: " + e.getMessage());
        }

        // Test interactWithBot method
        System.out.println("Testing interactWithBot method");
        try{
            Method interactWithBot = chatBotPlatformClass.getMethod("interactWithBot", int.class, String.class);
            String interaction = (String) interactWithBot.invoke(chatBotPlatform, 0, "Hello");
            if(interaction.contains("Response from")){ chatBotPlatformScores.put("interactWithBot", 1); System.out.println("Test passed"); }
            else{ System.out.println("Test failed"); }
        }
        catch(Exception e){
            System.err.println("Error in interactWithBot method: " + e.getMessage());
        }

        // Test getChatBotList method
        System.out.println("Testing getChatBotList method");
        try{
            Method getChatBotList = chatBotPlatformClass.getMethod("getChatBotList");
            String chatBotList = (String) getChatBotList.invoke(chatBotPlatform);
            if(chatBotList.contains("ChatGPT-3.5")){ chatBotPlatformScores.put("getChatBotList", 1); System.out.println("Test passed"); }
            else{ System.out.println("Test failed"); }
        }
        catch(Exception e){
            System.err.println("Error in getChatBotList method: " + e.getMessage());
        }

        for (Map.Entry<String, Integer> entry : chatBotPlatformScores.entrySet()) {
            score += entry.getValue();
        }

        scores.put("ChatBotPlatform", score);
    }

    @Test
    public void testChatBotGeneratorBehaviour() throws Exception {
        System.out.println("Running method tests for class: ChatBotGenerator");
        Class<?> chatBotGeneratorClass = loadClass("ChatBotGenerator");
        Constructor<?> defaultConstructor = chatBotGeneratorClass.getDeclaredConstructor();
        int score = 0;
        defaultConstructor.setAccessible(true);
        Object chatBotGenerator = defaultConstructor.newInstance();
        HashMap<String, Integer> chatBotGeneratorScores = new HashMap<>();
        chatBotGeneratorScores.put("generateChatBotLLM", 0);

        // Test generateChatBotLLM method
        System.out.println("Testing generateChatBotLLM method");
        try{
            Method generateChatBotLLM = chatBotGeneratorClass.getMethod("generateChatBotLLM", int.class);
            String llmName = (String) generateChatBotLLM.invoke(null, 1);
            if(llmName.contains("LLaMa")){ chatBotGeneratorScores.put("generateChatBotLLM", 1); System.out.println("Test passed"); }
            else{ System.out.println("Test failed"); }
        }
        catch(Exception e){
            System.err.println("Error in generateChatBotLLM method: " + e.getMessage());
        }

        for (Map.Entry<String, Integer> entry : chatBotGeneratorScores.entrySet()) {
            score += entry.getValue();
        }

        scores.put("ChatBotGenerator", score);
    }


    @AfterEach
    public void printResults() {
        System.out.println("Method Behaviour Test Results: " + scores);
    }
}

// package s_jamz.AutoGrader;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.AfterAll;
// import java.io.File;
// import java.lang.reflect.Constructor;
// import java.lang.reflect.Method;
// import java.net.URL;
// import java.net.URLClassLoader;
// import java.util.HashMap;
// import java.util.Map;
// import static org.junit.jupiter.api.Assertions.*;

// public class MethodBehaviourTest {

//     private static int totalScore;
//     private int chatBotScore = 0;
//     private int chatBotPlatformScore = 0;
//     private int chatBotGeneratorScore = 0;
//     private int chatBotSimulationScore = 0;

//     public MethodBehaviourTest() {
//         totalScore = 0;
//     }

//     @BeforeEach
//     public void resetState() {
//         try {
//             Class<?> chatBotClass = loadClass("ChatBot");
//             Method resetMethod = chatBotClass.getMethod("resetState");
//             resetMethod.invoke(null);

//             Class<?> chatBotPlatformClass = loadClass("ChatBotPlatform");
//             resetMethod = chatBotPlatformClass.getMethod("resetState");
//             resetMethod.invoke(null);
//         } catch (NoSuchMethodException e) {
//             // If the resetState method does not exist, ignore the exception
//         } catch (Exception e) {
//             e.printStackTrace(System.err);
//         }
//     }

//     private Class<?> loadClass(String className) throws Exception {
//         File studentFoldersDir = new File(System.getProperty("user.dir") + "/src/main/resources/StudentFolders/");
//         if (!studentFoldersDir.exists() || !studentFoldersDir.isDirectory()) {
//             throw new IllegalArgumentException("Invalid student folders path: " + studentFoldersDir.getAbsolutePath());
//         }

//         for (File studentDir : studentFoldersDir.listFiles()) {
//             if (studentDir.isDirectory()) {
//                 File binDir = new File(studentDir, "bin");
//                 if (binDir.exists() && binDir.isDirectory()) {
//                     URL[] urls = {binDir.toURI().toURL()};
//                     URLClassLoader urlClassLoader = new URLClassLoader(urls, this.getClass().getClassLoader());
//                     try {
//                         return Class.forName(className, true, urlClassLoader);
//                     } catch (ClassNotFoundException e) {
//                         // Continue to the next student folder
//                     }
//                 }
//             }
//         }

//         throw new ClassNotFoundException("Class " + className + " not found in any student folder.");
//     }

//     @Test
//     public void testChatBotBehaviour() throws Exception {
//         System.out.println("ChatBot Behaviour Test. \n");
//         int score = 0;
//         Class<?> chatBotClass = loadClass("ChatBot");

//         try {
//             Constructor<?> defaultConstructor = chatBotClass.getDeclaredConstructor();
//             defaultConstructor.setAccessible(true);
//             Object chatBot = defaultConstructor.newInstance();
//             Method getChatBotName = chatBotClass.getMethod("getChatBotName");
//             String chatBotName = (String) getChatBotName.invoke(chatBot);
//             assertEquals("ChatGPT-3.5", chatBotName, "Expected: ChatGPT-3.5, Actual: " + chatBotName);
//             score += 3;
//             System.out.println("Method behaviour 'getChatBotName()' matches expected behaviour.");

//             Constructor<?> llmConstructor = chatBotClass.getDeclaredConstructor(int.class);
//             llmConstructor.setAccessible(true);
//             Object chatBotWithLLM = llmConstructor.newInstance(1);
//             chatBotName = (String) getChatBotName.invoke(chatBotWithLLM);
//             assertEquals("LLaMa", chatBotName, "Expected: LLaMa, Actual: " + chatBotName);
//             score += 3;
//             System.out.println("Method behaviour 'ChatBot(int LLMCode)' matches expected behaviour.");

//             Method generateResponse = chatBotClass.getDeclaredMethod("generateResponse");
//             generateResponse.setAccessible(true);
//             String response = (String) generateResponse.invoke(chatBot);
//             assertTrue(response.contains("ChatGPT-3.5"), "Expected response to contain: ChatGPT-3.5, Actual: " + response);
//             score += 5;
//             System.out.println("Method behaviour 'generateResponse()' matches expected behaviour.");

//             Method getNumResponsesGenerated = chatBotClass.getMethod("getNumResponsesGenerated");
//             int numResponsesGenerated = (int) getNumResponsesGenerated.invoke(chatBot);
//             assertEquals(0, numResponsesGenerated, "Expected: 0, Actual: " + numResponsesGenerated);
//             score += 1;
//             System.out.println("Method behaviour 'getNumResponsesGenerated()' matches expected behaviour.");

//             Method getTotalNumResponsesGenerated = chatBotClass.getMethod("getTotalNumResponsesGenerated");
//             int totalNumResponsesGenerated = (int) getTotalNumResponsesGenerated.invoke(null);
//             assertEquals(0, totalNumResponsesGenerated, "Expected: 0, Actual: " + totalNumResponsesGenerated);
//             score += 2;
//             System.out.println("Method behaviour 'getTotalNumResponsesGenerated()' matches expected behaviour.");

//             Method getTotalNumMessagesRemaining = chatBotClass.getMethod("getTotalNumMessagesRemaining");
//             int totalNumMessagesRemaining = (int) getTotalNumMessagesRemaining.invoke(null);
//             assertEquals(10, totalNumMessagesRemaining, "Expected: 10, Actual: " + totalNumMessagesRemaining);
//             score += 3;
//             System.out.println("Method behaviour 'getTotalNumMessagesRemaining()' matches expected behaviour.");

//             Method limitReached = chatBotClass.getMethod("limitReached");
//             boolean isLimitReached = (boolean) limitReached.invoke(null);
//             assertFalse(isLimitReached, "Expected: false, Actual: " + isLimitReached);
//             score += 3;
//             System.out.println("Method behaviour 'limitReached()' matches expected behaviour.");

//             Method prompt = chatBotClass.getMethod("prompt", String.class);
//             String promptResponse = (String) prompt.invoke(chatBot, "Hello");
//             assertTrue(promptResponse.contains("ChatGPT-3.5"), "Expected response to contain: ChatGPT-3.5, Actual: " + promptResponse);
//             score += 4;
//             System.out.println("Method behaviour 'prompt(String requestMessage)' matches expected behaviour.");

//             Method toStringMethod = chatBotClass.getMethod("toString");
//             String toStringResponse = (String) toStringMethod.invoke(chatBot);
//             assertTrue(toStringResponse.contains("ChatGPT-3.5"), "Expected response to contain: ChatGPT-3.5, Actual: " + toStringResponse);
//             score += 4;
//             System.out.println("Method behaviour 'toString()' matches expected behaviour.");

//         } catch (Exception e) {
//             e.printStackTrace(System.err);
//         }

//         chatBotScore = score;
//         totalScore += chatBotScore;
//         System.out.println("ChatBot Class Score: " + chatBotScore + "/28");
//     }

//     @Test
//     public void testChatBotPlatformBehaviour() throws Exception {
//         System.out.println("ChatBotPlatform Behaviour Test. \n");
//         int score = 0;
//         Class<?> chatBotPlatformClass = loadClass("ChatBotPlatform");

//         try {
//             Object chatBotPlatform = chatBotPlatformClass.getDeclaredConstructor().newInstance();
//             Method addChatBot = chatBotPlatformClass.getMethod("addChatBot", int.class);
//             boolean addChatBotResult = (boolean) addChatBot.invoke(chatBotPlatform, 1);
//             assertTrue(addChatBotResult, "Expected: true, Actual: " + addChatBotResult);
//             score += 5;
//             System.out.println("Method behaviour 'addChatBot(int LLMcode)' matches expected behaviour.");

//             Method interactWithBot = chatBotPlatformClass.getMethod("interactWithBot", int.class, String.class);
//             String interaction = (String) interactWithBot.invoke(chatBotPlatform, 0, "Hello");
//             assertTrue(interaction.contains("ChatGPT-3.5"), "Expected response to contain: ChatGPT-3.5, Actual: " + interaction);
//             score += 5;
//             System.out.println("Method behaviour 'interactWithBot(int botNumber, String message)' matches expected behaviour.");

//             Method getChatBotList = chatBotPlatformClass.getMethod("getChatBotList");
//             String chatBotList = (String) getChatBotList.invoke(chatBotPlatform);
//             assertTrue(chatBotList.contains("ChatGPT-3.5"), "Expected response to contain: ChatGPT-3.5, Actual: " + chatBotList);
//             score += 6;
//             System.out.println("Method behaviour 'getChatBotList()' matches expected behaviour.");

//         } catch (Exception e) {
//             e.printStackTrace(System.err);
//         }

//         chatBotPlatformScore = score;
//         totalScore += chatBotPlatformScore;
//         System.out.println("ChatBotPlatform Class Score: " + chatBotPlatformScore + "/16");
//     }

//     @Test
//     public void testChatBotGeneratorBehaviour() throws Exception {
//         System.out.println("ChatBotGenerator Behaviour Test. \n");
//         int score = 0;
//         Class<?> chatBotGeneratorClass = loadClass("ChatBotGenerator");

//         try {
//             Method generateChatBotLLM = chatBotGeneratorClass.getMethod("generateChatBotLLM", int.class);
//             String llmName = (String) generateChatBotLLM.invoke(null, 1);
//             assertEquals("LLaMa", llmName, "Expected: LLaMa, Actual: " + llmName);
//             System.out.println("Method behaviour 'generateChatBotLLM(int LLMCodeNumber)' matches expected behaviour.");
//             llmName = (String) generateChatBotLLM.invoke(null, 0);
//             assertEquals("ChatGPT-3.5", llmName, "Expected: ChatGPT-3.5, Actual: " + llmName);
//             score += 7;
//             System.out.println("Method behaviour 'generateChatBotLLM(int LLMCodeNumber)' matches expected behaviour.");

//         } catch (Exception e) {
//             e.printStackTrace(System.err);
//         }

//         chatBotGeneratorScore = score;
//         totalScore += chatBotGeneratorScore;
//         System.out.println("ChatBotGenerator Class Score: " + chatBotGeneratorScore + "/7");
//     }

//     @Test
//     public void testChatBotSimulationBehaviour() throws Exception {
//         System.out.println("ChatBotSimulation Behaviour Test. \n");
//         int score = 0;
//         Class<?> chatBotSimulationClass = loadClass("ChatBotSimulation");

//         try {
//             Method mainMethod = chatBotSimulationClass.getMethod("main", String[].class);
//             mainMethod.invoke(null, (Object) new String[]{});
//             score += 12;
//             System.out.println("ChatBotSimulation ran successfully.");

//         } catch (Exception e) {
//             e.printStackTrace(System.err);
//         }

//         chatBotSimulationScore = score;
//         totalScore += chatBotSimulationScore;
//         System.out.println("ChatBotSimulation Class Score: " + chatBotSimulationScore + "/12");
//     }

//     @AfterAll
//     public static void calculateTotal() {
//         System.out.println("Total Score = " + totalScore + "/63 \n");
//     }
// }