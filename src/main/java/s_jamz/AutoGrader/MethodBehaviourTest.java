package s_jamz.AutoGrader;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
// import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MethodBehaviourTest {

    public static Map<String, Integer> scores = new HashMap<>();
    public static Map<String, Method[]> methodTest = new HashMap<>();

    public MethodBehaviourTest() {
        scores.put("ChatBot", 0);
        scores.put("ChatBotPlatform", 0);
        scores.put("ChatBotGenerator", 0);
        // System.out.println("MethodBehaviourTest constructor");
    }

    @BeforeEach
    public void initialize(){
        methodTest.clear();
        try{
            loadMethodNames("ChatBot");
            loadMethodNames("ChatBotPlatform");
            loadMethodNames("ChatBotGenerator");
        }
        catch(Exception e){
            System.err.println("Could not load method names for classes" + e.getMessage());
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

    public void loadMethodNames(String className) throws Exception{
        Class<?> class1 = loadClass(className);
        boolean hasMethods = true;

        if(class1 == null){
            System.out.println("Provided class is null.");
            hasMethods = false;
            return;
        }

        try{
            if(class1.getName().equals("ChatBot")){
                methodTest.put("ChatBot", getMethods(class1));
            }

            if(class1.getName().equals("ChatBotPlatform")){
                methodTest.put("ChatBotPlatform", getMethods(class1));
            }
            if(class1.getName().equals("ChatBotGenerator")){
                methodTest.put("ChatBotSimulation", getMethods(class1));
            }
        }

        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }


    public Method[] getMethods(Class<?> class1){
        try{
            if(class1 == null){
                System.err.println("Class not found");
                return new Method[0];
            }
            return class1.getDeclaredMethods();
        }
        catch(Exception e){
            System.err.println("Error in getting methods" + e.getMessage());
            return new Method[0];
        }
    }




    @Test
    public void testChatBotMethodResults() {
        System.out.println("Running method tests for class: Chatbot");
        int score = 0;
        HashMap<String, Method> expectedMethods = new HashMap<>();
        Class<?> class1;
        try {
            class1 = Class.forName("ChatBot");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        expectedMethods.put("getChatBotName", null);
        expectedMethods.put("getNumResponsesGenerated", null);
        expectedMethods.put("getTotalNumResponsesGenerated", null);
        expectedMethods.put("getTotalNumMessagesRemaining", null);
        expectedMethods.put("limitReached", null);
        expectedMethods.put("generateResponse", null);
        expectedMethods.put("prompt", null);
        expectedMethods.put("toString", null);

        
        // Testing getChatBotName method
        ArrayList<String> expectedMethodNames = new ArrayList<>();
        expectedMethodNames.add("getChatBotName");
        expectedMethodNames.add("getNumResponsesGenerated");
        expectedMethodNames.add("getTotalNumResponsesGenerated");
        expectedMethodNames.add("getTotalNumMessagesRemaining");
        expectedMethodNames.add("limitReached");
        expectedMethodNames.add("generateResponse");
        expectedMethodNames.add("prompt");
        expectedMethodNames.add("toString");

        for(Method method: methodTest.get("ChatBot")){
            if(method.getName().equals("getChatBotName")){
                method.setAccessible(true);

                try{
                    String test = (String) method.invoke(null);
                    System.out.println("ChatBot Name: " + test);
                    // System.out.println("ChatBot Name: " + method.invoke(null));
                    
                }
                catch(Exception e){
                    System.err.println("Error in getChatBotName method: " + e.getMessage());
                }
            }
        }
    }

    @AfterAll
    public void printResults() {
        System.out.println("Method Behaviour Test Results: " + scores);
    }
}