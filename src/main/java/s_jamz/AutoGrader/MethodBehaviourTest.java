package s_jamz.AutoGrader;

// import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class MethodBehaviourTest {

    public static Map<String, Integer> scores = new HashMap<>();
    public static Map<String, Method[]> methodTest = new HashMap<>();
    private HashMap<String, Field[]> attributeTest = new HashMap<String, Field[]>();

    public MethodBehaviourTest() {
        scores.put("ChatBot", 0);
        scores.put("ChatBotPlatform", 0);
        scores.put("ChatBotGenerator", 0);
        // System.out.println("MethodBehaviourTest constructor");
        
    }

    @BeforeEach
    public void initialize(){
        methodTest.clear();
        attributeTest.clear();
        try{
            loadMethodNames("ChatBot");
            loadMethodNames("ChatBotPlatform");
            loadMethodNames("ChatBotGenerator");
        }
        catch(Exception e){
            System.err.println("Could not load method names for classes" + e.getMessage());
        }
        try{
            loadAttributeNames("ChatBot");
            loadAttributeNames("ChatBotPlatform");
            loadAttributeNames("ChatBotGenerator");
        }
        catch(Exception e){
            System.err.println("Could not load attribute names for classes" + e.getMessage());
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
        // setAllMethodsAccessible(class1);
        boolean hasMethods = true;

        if(class1 == null){
            System.out.println("Provided class is null.");
            hasMethods = false;
            return;
        }

        try{
            if(class1.getName().equals("ChatBot")){
                //actually places the right methods in the hashmap
                methodTest.put("ChatBot", getMethods(class1));
            }

            if(class1.getName().equals("ChatBotPlatform")){
                methodTest.put("ChatBotPlatform", getMethods(class1));
            }
            if(class1.getName().equals("ChatBotGenerator")){
                methodTest.put("ChatBotGenerator", getMethods(class1));
            }
        }

        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    public void loadAttributeNames(String className) throws Exception{

        Class<?> class1 = loadClass(className);
        // setAllAttributesAccessible(class1);
        boolean hasAttributes = true;

        if(class1==null){
        System.out.println("Provided class is null.");
        hasAttributes = false;
        return;
        }

        try{
        if(class1.getName().equals("ChatBot")){
            attributeTest.put("ChatBot", getFields(class1));
        }

        if(class1.getName().equals("ChatBotPlatform")){
            attributeTest.put("ChatBotPlatform", getFields(class1));
        }
        if(class1.getName().equals("ChatBotSimulation")){
            attributeTest.put("ChatBotSimulation", getFields(class1));
        }}

        catch(Exception e){
            System.err.println(e.getMessage());
        }

        assertEquals(true, hasAttributes);


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

    public Field[] getFields(Class<?> class1){
        try {
            if (class1 == null) {
                System.err.println("Provided class is null.");
                return new Field[0]; 
            }
            return class1.getDeclaredFields();
        } catch (Exception e) {
            System.err.println("An error occurred while retrieving fields: " + e.getMessage());
            return new Field[0]; 
        }
    }

    public Method getMethodByName(String methodName, Class<?> class1){
        try{
            Method method = class1.getDeclaredMethod(methodName);
            return method;
        }
        catch(Exception e){
            System.err.println("Error in getting method by name: " + e.getMessage());
        }
        return null;
    }

    

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

    @Test
    public void testChatBotMethodResults() throws Exception {
        System.out.println("Running method tests for class: Chatbot");
        int score = 0;
        HashMap<String, Method> expectedMethods = new HashMap<>();


        expectedMethods.put("getChatBotName", null);
        expectedMethods.put("getNumResponsesGenerated", null);
        expectedMethods.put("getTotalNumResponsesGenerated", null);
        expectedMethods.put("getTotalNumMessagesRemaining", null);
        expectedMethods.put("limitReached", null);
        expectedMethods.put("generateResponse", null);
        expectedMethods.put("prompt", null);
        expectedMethods.put("toString", null);

        
        // Testing getChatBotName method
        String [] chatBotNames = {"ChatGpt3.5", "LLaMa", "Mistral", "Bard", "Claude", "Solar"};
        // System.out.println("Expected Methods: " + Arrays.toString(methodTest.get("ChatBot")));
        Method getChatBotName = getMethodByName("getChatBotName", methodTest.get("ChatBot")[0].getDeclaringClass());
        getChatBotName.setAccessible(true);

        Field chatBotName = attributeTest.get("ChatBot")[0];
        chatBotName.setAccessible(true);


        try{
            getChatBotName.setAccessible(true);
            Object chatBotInstance = methodTest.get("ChatBot")[0].getDeclaringClass().getDeclaredConstructor().newInstance();
            
            String test = (String) getChatBotName.invoke(chatBotInstance);
            System.out.println("ChatBot Name HERE: " + test);

            // if(chatBotNames.contains(test)){
            //     score++;
            // }
            // for(String name: chatBotNames){
            //     if(test.equals(name)){
            //         score++;
            //     }
            // }
        } catch(Exception e){
            System.err.println("Error in getChatBotName method: " + e.getMessage());
        }

        assertEquals(6, score);
    }

    @AfterEach
    public void printResults() {
        System.out.println("Method Behaviour Test Results: " + scores);
    }
}