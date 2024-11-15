package s_jamz.AutoGrader;

// import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        // try{
        //     loadMethodNames("ChatBot");
        //     loadMethodNames("ChatBotPlatform");
        //     loadMethodNames("ChatBotGenerator");
        // }
        // catch(Exception e){
        //     System.err.println("Could not load method names for classes" + e.getMessage());
        // }
        // try{
        //     loadAttributeNames("ChatBot");
        //     loadAttributeNames("ChatBotPlatform");
        //     loadAttributeNames("ChatBotGenerator");
        // }
        // catch(Exception e){
        //     System.err.println("Could not load attribute names for classes" + e.getMessage());
        // }
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

    // public void loadMethodNames(String className) throws Exception{
    //     Class<?> class1 = loadClass(className);
    //     // setAllMethodsAccessible(class1);
    //     boolean hasMethods = true;

    //     if(class1 == null){
    //         System.out.println("Provided class is null.");
    //         hasMethods = false;
    //         return;
    //     }

    //     try{
    //         if(class1.getName().equals("ChatBot")){
    //             //actually places the right methods in the hashmap
    //             methodTest.put("ChatBot", getMethods(class1));
    //         }

    //         if(class1.getName().equals("ChatBotPlatform")){
    //             methodTest.put("ChatBotPlatform", getMethods(class1));
    //         }
    //         if(class1.getName().equals("ChatBotGenerator")){
    //             methodTest.put("ChatBotGenerator", getMethods(class1));
    //         }
    //     }

    //     catch(Exception e){
    //         System.err.println(e.getMessage());
    //     }
    // }

    // public void loadAttributeNames(String className) throws Exception{

    //     Class<?> class1 = loadClass(className);
    //     // setAllAttributesAccessible(class1);
    //     boolean hasAttributes = true;

    //     if(class1==null){
    //     System.out.println("Provided class is null.");
    //     hasAttributes = false;
    //     return;
    //     }

    //     try{
    //     if(class1.getName().equals("ChatBot")){
    //         attributeTest.put("ChatBot", getFields(class1));
    //     }

    //     if(class1.getName().equals("ChatBotPlatform")){
    //         attributeTest.put("ChatBotPlatform", getFields(class1));
    //     }
    //     if(class1.getName().equals("ChatBotSimulation")){
    //         attributeTest.put("ChatBotSimulation", getFields(class1));
    //     }}

    //     catch(Exception e){
    //         System.err.println(e.getMessage());
    //     }

    //     assertEquals(true, hasAttributes);


    // }


    // public Method[] getMethods(Class<?> class1){
    //     try{
    //         if(class1 == null){
    //             System.err.println("Class not found");
    //             return new Method[0];
    //         }
    //         return class1.getDeclaredMethods();
    //     }
    //     catch(Exception e){
    //         System.err.println("Error in getting methods" + e.getMessage());
    //         return new Method[0];
    //     }
    // }

    // public Field[] getFields(Class<?> class1){
    //     try {
    //         if (class1 == null) {
    //             System.err.println("Provided class is null.");
    //             return new Field[0]; 
    //         }
    //         return class1.getDeclaredFields();
    //     } catch (Exception e) {
    //         System.err.println("An error occurred while retrieving fields: " + e.getMessage());
    //         return new Field[0]; 
    //     }
    // }

    // public Method getMethodByName(String methodName, Class<?> class1) {
    //     try {
    //         Method method = class1.getDeclaredMethod(methodName);
    //         method.setAccessible(true); // Set method accessible
    //         return method;
    //     } catch (Exception e) {
    //         System.err.println("Error in getting method by name: " + e.getMessage());
    //     }
    //     return null;
    // }

    

    // public void setAllAttributesAccessible(Class<?> class1) {
    //     try {
    //         Arrays.stream(class1.getDeclaredFields()).forEach(field -> field.setAccessible(true));
    //     } catch (Exception e) {
    //         System.err.println("Error in setting attributes accessible: " + e.getMessage());
    //     }
    // }

    // public void setAllMethodsAccessible(Class<?> class1) {
    //     try {
    //         Arrays.stream(class1.getDeclaredMethods()).forEach(method -> method.setAccessible(true));
    //     } catch (Exception e) {
    //         System.err.println("Error in setting methods accessible: " + e.getMessage());
    //     }
    // }

    // public void setAllConstructorsAccessible(Class<?> class1){
    //     //set all constructors to be accessible
    //     try{
    //         Arrays.stream(class1.getDeclaredConstructors()).forEach(constructor -> constructor.setAccessible(true));
    //     }
    //     catch(Exception e){
    //         System.err.println("Error in setting constructors accessible: " + e.getMessage());
    //     }
    // }

    @Test
    public void testChatBotBehaviour() throws Exception {
        System.out.println("Running method tests for class: Chatbot");
        Class<?> chatBotClass = loadClass("ChatBot");
        int score = 0;
        // boolean flag = true;

        System.out.println("Testing getChatBotName method");
        try{
            Constructor<?> defaultConstructor = chatBotClass.getDeclaredConstructor();
            defaultConstructor.setAccessible(true);
            Object chatBotGPT = defaultConstructor.newInstance();
            Method getChatBotName = chatBotClass.getMethod("getChatBotName");
            String chatBotName = (String) getChatBotName.invoke(chatBotGPT);
            try{
                assertEquals("ChatGPT-3.5", chatBotName, "Expected: " + chatBotName + ", Actual: " + chatBotName);
                score += 1;
                System.out.println("Method behaviour 'getChatBotName()' matches expected behaviour."); 
            }
            catch(Exception e){
                System.err.println("Error in getChatBotName method: " + e.getMessage());
            }
            
            Constructor<?> llmConstructor = chatBotClass.getDeclaredConstructor(int.class);
            llmConstructor.setAccessible(true);
            Object chatBotWithLLM = llmConstructor.newInstance(1);
            chatBotName = (String) getChatBotName.invoke(chatBotWithLLM);
            try{
                assertEquals("LLaMa", chatBotName, "Expected: " + chatBotName + ", Actual: " + chatBotName);
                score += 1;
                System.out.println("Method behaviour 'ChatBot(LLaMa)' matches expected behaviour.");
            }
            catch(Exception e){
                System.err.println("Error in getChatBotName method: " + e.getMessage());
            }

            Constructor<?> mistralConstructor = chatBotClass.getDeclaredConstructor(int.class);
            mistralConstructor.setAccessible(true);
            Object chatBotWith2 = mistralConstructor.newInstance(2);
            chatBotName = (String) getChatBotName.invoke(chatBotWith2);
            try{
                assertEquals("Mistral7B", chatBotName, "Expected: " + chatBotName + ", Actual: " + chatBotName);
                score += 1;
                System.out.println("Method behaviour 'ChatBot(Mistral7B)' matches expected behaviour.");
            }
            catch(Exception e){
                System.err.println("Error in getChatBotName method: " + e.getMessage());
            }

            Constructor<?> bardConstructor = chatBotClass.getDeclaredConstructor(int.class);
            bardConstructor.setAccessible(true);
            Object chatBotWith3 = bardConstructor.newInstance(3);
            chatBotName = (String) getChatBotName.invoke(chatBotWith3);
            try{
                assertEquals("Bard", chatBotName, "Expected: " + chatBotName + ", Actual: " + chatBotName);
                score += 1;
                System.out.println("Method behaviour 'ChatBot(Bard)' matches expected behaviour.");
            }
            catch(Exception e){
                System.err.println("Error in getChatBotName method: " + e.getMessage());
            }

            Constructor<?> claudeConstructor = chatBotClass.getDeclaredConstructor(int.class);
            claudeConstructor.setAccessible(true);
            Object chatBotWith4 = claudeConstructor.newInstance(4);
            chatBotName = (String) getChatBotName.invoke(chatBotWith4);
            try{
                assertEquals("Claude", chatBotName, "Expected: " + chatBotName + ", Actual: " + chatBotName);
                score += 1;
                System.out.println("Method behaviour 'ChatBot(Claude)' matches expected behaviour.");
            }
            catch(Exception e){
                System.err.println("Error in getChatBotName method: " + e.getMessage());
            }

            Constructor<?> solarConstructor = chatBotClass.getDeclaredConstructor(int.class);
            solarConstructor.setAccessible(true);
            Object chatBotWith5 = solarConstructor.newInstance(5);
            chatBotName = (String) getChatBotName.invoke(chatBotWith5);
            try{
                assertEquals("Solar", chatBotName, "Expected: " + chatBotName + ", Actual: " + chatBotName);
                score += 1;
                System.out.println("Method behaviour 'ChatBot(Solar)' matches expected behaviour.");
            }
            catch(Exception e){
                System.err.println("Error in getChatBotName method: " + e.getMessage());
            }
            
            Constructor<?> newConstructor = chatBotClass.getDeclaredConstructor(int.class);
            newConstructor.setAccessible(true);
            Object chatBotWith6 = newConstructor.newInstance(6);
            chatBotName = (String) getChatBotName.invoke(chatBotWith6);
            try{
                assertEquals("ChatGPT-3.5", chatBotName, "Expected: " + chatBotName + ", Actual: " + chatBotName);
                score += 1;
                System.out.println("Method behaviour 'ChatBot(ChatGPT-3.5)' matches expected behaviour.");
            }
            catch(Exception e){
                System.err.println("Error in getChatBotName method: " + e.getMessage());
            }
        } 
        catch(Exception e){
            System.err.println("Error in getChatBotName method: " + e.getMessage());
        }

        System.out.println("Testing generateResponse method");

        scores.put("ChatBot", score);

        //Test generateResponse method
        // try{
        //     Method generateResponse = chatBotClass.getDeclaredMethod("generateResponse");
        //     generateResponse.setAccessible(true);
        //     String response = (String) generateResponse.invoke(chatBot);
        //     assertTrue(response.contains("ChatGPT-3.5"), "Expected response to contain: ChatGPT-3.5, Actual: " + response);
        //     score += 5;
        //     System.out.println("Method behaviour 'generateResponse()' matches expected behaviour.");
        // }
        // catch(Exception e){
        //     System.err.println("Error in generateResponse method: " + e.getMessage());
        // }



        // assertEquals(6, score);
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