package s_jamz.AutoGrader;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;


public class AttributeTypeTest {

    private static int totalScore;
    private int chatBotScore =0;
    private int chatBotPlatformScore=0;
    private int chatBotSimulationScore =0 ;


    private HashMap<String, Field[]> attributeTest;

    AttributeTypeTest(){
        totalScore = 0;
        attributeTest = new HashMap<String, Field[]>();
    }

    @BeforeEach
    public void setup() {
        // Only reset individual scores for each test
        chatBotScore =0;
        chatBotPlatformScore=0;
        chatBotSimulationScore =0;
    }


    public Field[] getFields(Class<?> class1){
        try {
            if (class1 == null) {
                System.err.println("Provided class is null.");
                return new Field[0]; 
            }
            return class1.getFields();
        } catch (Exception e) {
            System.err.println("An error occurred while retrieving fields: " + e.getMessage());
            return new Field[0]; 
        }
    }
    @Test
    public void loadAttributeNames(Class<?> class1){
        
        boolean hasAttributes = true;

        if(class1==null){
        System.err.println("Provided class is null.");
        hasAttributes = false;
        return;
        }
        try{
        if(class1.getSimpleName().equals("ChatBot")){
            attributeTest.put("ChatBot", getFields(class1));
        }

        if(class1.getSimpleName().equals("ChatBotPlatform")){
            attributeTest.put("ChatBotPlatform", getFields(class1));
        }
        if(class1.getSimpleName().equals("ChatBotSimulation")){
            attributeTest.put("ChatBotSimulation", getFields(class1));
        }}

        catch(Exception e){
            System.err.println(e.getMessage());
        }

        assertEquals(true, hasAttributes);


    }

    @Test
    public void chatBotAttributeTest(){
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
            System.out.println(field.getName() + " has incorrect type.");
        }}
        catch(Exception e){
            System.err.println(e.getMessage());
        }
       }
       chatBotScore = score;
       System.out.println("ChatBot Class Score: " + chatBotScore + "/4");
       assertEquals(4, score);
    }

    @Test
    public void chatBotPlatformAttributeTest(){
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
             System.out.println(field.getName() + " has incorrect type.");
         }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        }

        chatBotPlatformScore = score;
        System.out.println("ChatBotPlatform Class Score: " + chatBotPlatformScore + "/1");
        assertEquals(1, score);
     }

     @Test
     public void chatBotSimulationTest(){
          Field[] chatBotSimulationAttributes = attributeTest.get("ChatBotSimulation");
          int score = 0;
          boolean hasChatBotPlatform = false;
          boolean hasChatBot = false;

          for(Field field: chatBotSimulationAttributes){
           try{
            if(field.getType().getSimpleName().equals("ChatBotPlatform")){
                hasChatBotPlatform= true;
            }
            if(field.getType().getSimpleName().equals("ChatBot")){
                hasChatBot = true;
            }
          

            if(hasChatBotPlatform){
                score++;
                System.out.println("ChatBotPlatform present.");
            }

            if(hasChatBot){
                score++;
                System.out.println("ChatBot present.");
            }
          }
          catch(Exception e){
            System.out.println(e.getMessage());
          }

          chatBotSimulationScore = score;
          System.out.println("ChatBotSimulation Class Score: " + chatBotSimulationScore + "/2");
          assertEquals(2, score);
     }
    }

    

     @AfterAll
     public static void calculateTotal(){
  
     System.out.println("Total Score = " + totalScore + "/7");
     }

}



