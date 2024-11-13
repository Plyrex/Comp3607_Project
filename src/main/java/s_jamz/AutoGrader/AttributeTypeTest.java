package s_jamz.AutoGrader;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AttributeTypeTest {

    private static int totalScore;
    private int chatBotScore =0;
    private int chatBotPlatformScore=0;
    private int chatBotSimulationScore =0 ;


    private HashMap<String, Field[]> attributeTest;

    public AttributeTypeTest(){
        totalScore = 0;
        attributeTest = new HashMap<String, Field[]>();
    }

    @BeforeEach
    public void setup(){
        attributeTest.clear();
        try{
        loadAttributeNames("ChatBot");
        loadAttributeNames("ChatBotPlatform");
        loadAttributeNames("ChatBotSimulation");
        }
        catch(Exception e){
            System.err.println("Could not load attribute names for classes" + e.getMessage());
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

    public void printMap() {
    for (Map.Entry<String, Field[]> entry : attributeTest.entrySet()) {
        System.out.println("Class: " + entry.getKey());
        Field[] fields = entry.getValue();
        
        // Print each field in the Field[]
        for (Field field : fields) {
            System.out.println("  Field: " + field.getName() + ", Type: " + field.getType().getSimpleName());
        }
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

    @Test
    public void chatBotAttributeTest(){
        System.out.println("ChatBot Test. \n");
       Field[] chatBotAttributes = attributeTest.get("ChatBot");
       int score = 0;
       HashMap<String, Class<?>> expectedAttributes = new HashMap<String, Class<?>>();

       expectedAttributes.put("chatBotName", String.class);
       expectedAttributes.put("numResponsesGenerated", int.class);
       expectedAttributes.put("messageLimit", int.class);
       expectedAttributes.put("messageNumber", int.class);

       for(Field field: chatBotAttributes){
        try{
        if(expectedAttributes.containsKey(field.getName())){
            System.out.print("Class contains: " + field.getName() + ". ");
        }
        if(expectedAttributes.containsKey(field.getName()) && expectedAttributes.get(field.getName()).equals(field.getType())){
            System.out.print(field.getName()+ " has correct type. \n");
            score++;
        }
        else{
            System.out.println(field.getName() + " has incorrect type.\n");
        }}
        catch(Exception e){
            System.err.println(e.getMessage());
        }
       }
       chatBotScore = score;
       totalScore = totalScore + chatBotScore;
       System.out.println("ChatBot Class Score: " + chatBotScore + "/4");
       assertEquals(4, score);
    }

    @Test
    public void chatBotPlatformAttributeTest(){
        System.out.println("ChatBotPlatform Test. \n");
        Field[] chatBotPlatformAttributes = attributeTest.get("ChatBotPlatform");
        int score = 0;
        HashMap<String, Class<?>> expectedAttributes = new HashMap<String, Class<?>>();
 
        expectedAttributes.put("bots", ArrayList.class);
    
 
        for(Field field: chatBotPlatformAttributes){
        try{
         if(expectedAttributes.containsKey(field.getName())){
             System.out.print("Class contains: " + field.getName() + ". ");
         }
         if(expectedAttributes.containsKey(field.getName()) && expectedAttributes.get(field.getName()).equals(field.getType())){
             System.out.print(field.getName()+ " has correct type. \n");
             score++;
         }
         else{
             System.out.println(field.getName() + " has incorrect type. \n\n");
         }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        }

        chatBotPlatformScore = score;
        totalScore = totalScore + chatBotPlatformScore;
        System.out.println("ChatBotPlatform Class Score: " + chatBotPlatformScore + "/1 \n");
        assertEquals(1, score);
     }

     @Test
     public void chatBotSimulationTest(){
        System.out.println("ChatBotSimulation Test. \n");

          Field[] chatBotSimulationAttributes = attributeTest.get("ChatBotSimulation");
          int score = 0;
          boolean hasChatBotPlatform = false;
          boolean hasChatBot = false;

          try{
          for(Field field: chatBotSimulationAttributes){
            if(field.getType().getSimpleName().equals("ChatBotPlatform")){
                hasChatBotPlatform= true;
            }
            if(field.getType().getSimpleName().equals("ChatBot")){
                hasChatBot = true;
            }
          }

            if(hasChatBotPlatform){
                score++;
                System.out.println("ChatBotPlatform present. \n");
            }
            else{
                System.out.println("ChatBotPlatform not present. \n");
            }

            if(hasChatBot){
                score++;
                System.out.println("ChatBot present.");
            }
            else{
                System.out.println("ChatBot not present. \n");
            }
          }
          catch(Exception e){
            System.out.println(e.getMessage());
          }
     
     chatBotSimulationScore = score;
     totalScore = totalScore + chatBotSimulationScore;
     System.out.println("ChatBotSimulation Class Score: " + chatBotSimulationScore + "/2 \n");
     assertEquals(2, score);
    }

    

     @AfterAll
     public static void calculateTotal(){
  
     System.out.println("Total Score = " + totalScore + "/7 \n");
     }

}



