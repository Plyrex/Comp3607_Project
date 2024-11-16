package s_jamz.AutoGrader;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AttributeTypeTest {

    private static int totalScore;
    private static int chatBotScore =0;
    private static int chatBotPlatformScore=0;
    private String chatBotTestString;
    private String chatBotPlatformTestString;
    
    private static List<String> results;

    

    private HashMap<String, Field[]> attributeTest;

    public AttributeTypeTest(){
        attributeTest = new HashMap<String, Field[]>();
        results = new ArrayList<String>();
    }

    @BeforeEach
    public void setup(){
        try{
            chatBotTestString = ""; 
            chatBotPlatformTestString = "";
        attributeTest.clear();
        loadAttributeNames("ChatBot");
        loadAttributeNames("ChatBotPlatform");
        }
        catch(Exception e){
            System.err.println("Could not load attribute names for class: " + e.getMessage());
        }
    }



    public Field[] getClassFields(Class<?> class1){
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

    public void loadAttributeNames(String className) throws Exception{

        Class<?> class1 = loadClass(className);
       
        boolean hasAttributes = true;

        if(class1==null){
        System.out.println("Provided class is null.");
        hasAttributes = false;
        return;
        }

        try{
        if(class1.getName().equals("ChatBot")){
            attributeTest.put("ChatBot", getClassFields(class1));
        }

        if(class1.getName().equals("ChatBotPlatform")){
            attributeTest.put("ChatBotPlatform", getClassFields(class1));
        }
       }

        catch(Exception e){
            System.err.println(e.getMessage());
        }

        assertEquals(true, hasAttributes);


    }

    @Test
    @Order(1)
    public void chatBotAttributeTest(){
        

       Field[] chatBotAttributes = attributeTest.get("ChatBot");
       int score = 0;
       HashMap<String, Class<?>> expectedAttributes = new HashMap<String, Class<?>>();

       expectedAttributes.put("chatBotName", String.class);
       expectedAttributes.put("numResponsesGenerated", int.class);
       expectedAttributes.put("messageLimit", int.class);
       expectedAttributes.put("messageNumber", int.class);

       chatBotTestString = chatBotTestString + ("ChatBot Test. \n"); 

       for(Field field: chatBotAttributes){
        try{
        if(expectedAttributes.containsKey(field.getName())){

            chatBotTestString = chatBotTestString + ("Class contains: " + field.getName() + ". ");
        }
        if(expectedAttributes.containsKey(field.getName()) && expectedAttributes.get(field.getName()).equals(field.getType())){

            chatBotTestString = chatBotTestString + (field.getName()+ " has correct type. \n");
            score++;
        }
        else{
           chatBotTestString = chatBotTestString + (field.getName() + " has incorrect type.\n");
        }}
        catch(Exception e){
            System.err.println(e.getMessage());
        }
       }
       chatBotScore = score;
       totalScore = totalScore + chatBotScore;
       chatBotTestString = chatBotTestString + ("ChatBot Class Score: " + chatBotScore + "/4");

       
      
       System.out.println("End of ChatBotTest"); //test statement

    
    }

    @Test
    @Order(2)
    public void chatBotPlatformAttributeTest(){
        Field[] chatBotPlatformAttributes = attributeTest.get("ChatBotPlatform");
        int score = 0;
        HashMap<String, Class<?>> expectedAttributes = new HashMap<String, Class<?>>();
 
        expectedAttributes.put("bots", ArrayList.class);
    
        chatBotPlatformTestString = chatBotPlatformTestString + ("ChatBotPlatform Test. \n");

        for(Field field: chatBotPlatformAttributes){
        try{
         if(expectedAttributes.containsKey(field.getName())){
             chatBotPlatformTestString = chatBotPlatformTestString + ("Class contains: " + field.getName() + ". ");
         }
         if(expectedAttributes.containsKey(field.getName()) && expectedAttributes.get(field.getName()).equals(field.getType())){
            chatBotPlatformTestString = chatBotPlatformTestString + (field.getName()+ " has correct type. \n");
             score++;
         }
         else{
            chatBotPlatformTestString = chatBotPlatformTestString + (field.getName() + " has incorrect type. \n\n");
         }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        }

        chatBotPlatformScore = score;
        totalScore = totalScore + chatBotPlatformScore;
        chatBotPlatformTestString = chatBotPlatformTestString + ("ChatBotPlatform Class Score: " + chatBotPlatformScore + "/1 \n");
        results.add(chatBotPlatformTestString);

        System.out.println("End of ChatBotPlatformTest"); //test statement

        
     }

     public static void removeResults(){
        results.clear();
     }

     public static List<String> getAttributeTypeTestResults(){
        return results;
     }

     public static int getTotalScore(){
        return totalScore;
     }
    

     @AfterAll
     public static void calculateTotal(){
     results.add("Total Score = " + totalScore + "/5 \n");
     totalScore = 0;
     chatBotScore = 0;
     chatBotPlatformScore = 0;


     }

}



