package s_jamz.AutoGrader;

import org.junit.After;
import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import s_jamz.CompositePattern.TestResultLeaf;

// import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.lang.reflect.Modifier;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Order;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MethodBehaviourTest {

    private static int totalScore;
    private static int chatBotScore = 0;
    private static int chatBotPlatformScore = 0;
    private static int chatBotGeneratorScore = 0;

    private static HashMap<String, Class<?>> classes;


    private static HashMap<String, TestResultLeaf> testResults = new HashMap<>();
    public static Map<String, Integer> scores = new HashMap<>();

    

    public MethodBehaviourTest() {
        classes = new HashMap<>(); 
        scores.put("ChatBot", 0);
        scores.put("ChatBotPlatform", 0);
        scores.put("ChatBotGenerator", 0);   
          
    }

  

    @BeforeEach
    public void resetState() {
        classes.clear();
        try {
            loadClassDetails("ChatBot");
            loadClassDetails("ChatBotPlatform");
            loadClassDetails("ChatBotGenerator");

        } catch (NoSuchMethodException e) {
            // If the resetState method does not exist, ignore the exception
        } catch (Exception e) {
            e.printStackTrace(System.err);
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
                        if(class1!=null)
                        System.out.println("Loading class: " + className + " from student folder: " + studentDir.getName());
                        
                        classes.put(className, class1);
                        
                    } catch (ClassNotFoundException e) {
                        // Continue searching in other student folders
                    }
                }
            }
        }   
    }


    @Test
    @Order(1)
    public void testChatBotBehaviour() throws Exception {

        System.out.println("Running method tests for class: Chatbot");
        Class<?> chatBotClass = classes.get("ChatBot");
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


            Constructor<?> llmConstructor = chatBotClass.getDeclaredConstructor(int.class);
            llmConstructor.setAccessible(true);
             Object chatBotWith2 = llmConstructor.newInstance(2);
            if (chatBotWith2 != null) {
                chatBotScores.put("ChatBot()", 3);
                feedback.append("Overloaded Constructor passed. ");
            } else {
                feedback.append("Overloaded Constructor not passed. ");}

            if(chatBotGPT !=null){
                chatBotScores.put("ChatBot", 3);
                feedback.append("Constructor passed. \n");
            }
            else{
                feedback.append("Constructor not passed. \n");
            }
            if (chatBotName.contains("ChatGPT-3.5")) {
                chatBotScores.put("getChatBotName", 1);
                feedback.append("getChatBotName method passed.\n");
            } else {
                feedback.append("getChatBotName method failed. Expected 'ChatGPT-3.5', but got '").append(chatBotName).append("'.\n");}
        } 
        catch(Exception e){
            feedback.append("Error in getChatBotName method: ").append(e.getMessage()).append("\n");
        }


        // //Test generateResponse method
        // System.out.println("Testing generateResponse method");
        try{
            int generateResponseScore = 0;
            Method generateResponse = chatBotClass.getDeclaredMethod("generateResponse");
            generateResponse.setAccessible(true);
            String response = (String) generateResponse.invoke(chatBot);
          
            if (response.contains("ChatGPT-3.5")) {
                generateResponseScore = generateResponseScore + 4;
                chatBotScores.put("generateResponse", generateResponseScore);
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

            if (numResponsesGenerated == 1) { //because generateResponse was called
                chatBotScores.put("getNumResponsesGenerated", 1);
                feedback.append("getNumResponsesGenerated method passed.\n");
            } else {
                feedback.append("getNumResponsesGenerated method failed. Expected 1, but got ").append(numResponsesGenerated).append(".\n");}    
        }
        catch(Exception e){
            feedback.append("Error in getNumResponsesGenerated method: ").append(e.getMessage()).append("\n");
        }

        // Test getTotalNumResponsesGenerated method
        // System.out.println("Testing getTotalNumResponsesGenerated method");
        try{
            int getTotalNumResponsesGeneratedScore = 0;
            Method getTotalNumResponsesGenerated = chatBotClass.getMethod("getTotalNumResponsesGenerated");
            int totalNumResponsesGenerated = (int) getTotalNumResponsesGenerated.invoke(null);
          
            if (totalNumResponsesGenerated == 1) { //because generateResponse was called
                getTotalNumResponsesGeneratedScore++;
                chatBotScores.put("getTotalNumResponsesGenerated", getTotalNumResponsesGeneratedScore);
                feedback.append("getTotalNumResponsesGenerated method passed.\n");
            } else {
                feedback.append("getTotalNumResponsesGenerated method failed. Expected 1, but got ").append(totalNumResponsesGenerated).append(".\n");}
            
        }
        catch(Exception e){
            feedback.append("Error in getTotalNumResponsesGenerated method: ").append(e.getMessage()).append("\n");
        }

        // Test getTotalNumMessagesRemaining method
        // System.out.println("Testing getTotalNumMessagesRemaining method");
        try{
            int getTotalNumMessagesRemainingScore = 0;
            Method getTotalNumMessagesRemaining = chatBotClass.getMethod("getTotalNumMessagesRemaining");
            int totalNumMessagesRemaining = (int) getTotalNumMessagesRemaining.invoke(null);

            if (totalNumMessagesRemaining == 9) { //because generateResponse was called
                getTotalNumMessagesRemainingScore = getTotalNumMessagesRemainingScore + 2;
                chatBotScores.put("getTotalNumMessagesRemaining", getTotalNumMessagesRemainingScore);
                feedback.append("getTotalNumMessagesRemaining method passed.\n");
            } else {
                feedback.append("getTotalNumMessagesRemaining method failed. Expected 9, but got ").append(totalNumMessagesRemaining).append(".\n");}
        }
        catch(Exception e){
            feedback.append("Error in getTotalNumMessagesRemaining method: ").append(e.getMessage()).append("\n");
        }

        // Test limitReached method
        // System.out.println("Testing limitReached method");
        try{
            int limitReachedScore = 0;
            Method limitReached = chatBotClass.getMethod("limitReached");
            boolean isLimitReached = (boolean) limitReached.invoke(null);

            if (!isLimitReached) {
                limitReachedScore = limitReachedScore + 2;
                chatBotScores.put("limitReached", limitReachedScore);
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
                chatBotScores.put("prompt", 3);
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
                chatBotScores.put("toString", 3);
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
        feedback.append("ChatBot Class Score: ").append(chatBotScore).append("/23\n");
        testResults.put("ChatBot", new TestResultLeaf(chatBotScore, feedback.toString()));
    }


    @Test
    @Order(2)
    public void testChatBotPlatformBehaviour() throws Exception {
   

        System.out.println("Running method tests for class: ChatBotPlatform");
        Class<?> chatBotPlatformClass = classes.get("ChatBotPlatform");
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
            if(chatBotPlatform!=null){
                chatBotPlatformScores.put("ChatBotPlatform", 2);
                feedback.append("ChatBotPlatform constructor passed. \n");
            }
            else{
                feedback.append("ChatBotPlatform constructor failed. \n");
            }
        }
        catch(Exception e){
            feedback.append("Error in ChatBotPlatform constructor: ").append(e.getMessage()).append("\n");
        }

        try{
            Method addChatBot = chatBotPlatformClass.getMethod("addChatBot", int.class);
            boolean addChatBotResult = (boolean) addChatBot.invoke(chatBotPlatform, 1);
            if (addChatBotResult) {
                chatBotPlatformScores.put("addChatBot", 4);
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
            int interactWithBotScore= 0;
            Method interactWithBot = chatBotPlatformClass.getMethod("interactWithBot", int.class, String.class);

            String interaction = (String) interactWithBot.invoke(chatBotPlatform, 7, "Hello");

            if (interaction.contains("Incorrect")) {
                interactWithBotScore = interactWithBotScore + 2;
                feedback.append("interactWithBot method passed for incorrect parameter.\n");
            } else {
                feedback.append("interactWithBot method failed. Expected response containing 'Incorrect Bot Number', but got '").append(interaction).append("'.\n");}

            interaction = (String) interactWithBot.invoke(chatBotPlatform, 0, "Hello");
        
            if (interaction.contains("Response from")) {
                interactWithBotScore = interactWithBotScore + 2;

                chatBotPlatformScores.put("interactWithBot", interactWithBotScore);

                feedback.append("interactWithBot method passed for correct parameter.\n");
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
            if (chatBotList.contains("ChatBot Name") || chatBotList.contains("Messages Used")) {
                chatBotPlatformScores.put("getChatBotList", 5);
                feedback.append("getChatBotList method passed.\n");
            } else {
                feedback.append("getChatBotList method failed. Expected list containing ChatBot Names and Messages Used, but got '").append(chatBotList).append("'.\n");}
        }
        catch(Exception e){
            feedback.append("Error in getChatBotList method: ").append(e.getMessage()).append("\n");
        }

        for (Map.Entry<String, Integer> entry : chatBotPlatformScores.entrySet()) {
            score += entry.getValue();
        }

        chatBotPlatformScore = score;
        totalScore += chatBotPlatformScore;
        feedback.append("ChatBotPlatform Class Score: ").append(chatBotPlatformScore).append("/15\n");
        testResults.put("ChatBotPlatform", new TestResultLeaf(chatBotPlatformScore, feedback.toString()));
    }


    @Test
    @Order(3)
    public void testChatBotGeneratorBehaviour() throws Exception {
        
        System.out.println("Running method tests for class: ChatBotGenerator");
        Class<?> chatBotGeneratorClass = classes.get("ChatBotGenerator");
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
                chatBotGeneratorScores.put("generateChatBotLLM", 6);
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
        feedback.append("ChatBotGenerator Class Score: ").append(chatBotGeneratorScore).append("/6\n");
        testResults.put("ChatBotGenerator", new TestResultLeaf(chatBotGeneratorScore, feedback.toString()));
    }   

    // @AfterEach
    // public void printResults() {
    //     // System.out.println("Method Behaviour Test Results: " + testResults);
    // }
    
    public static HashMap<String, TestResultLeaf> getTestResults() {
        return testResults;

    }
   
}

