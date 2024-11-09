//816030520

import java.util.Random; // Lab 1 part 3

public class ChatBot
{
    public String chatBotName;
    public int numResponsesGenerated;
    private static int messageLimit= 10;
    private static int messageNumber=0;
    
    //constructor
    public ChatBot()
    {
        chatBotName = "ChatGPT-3.5";
    }
    
    //overloaded constructor
    public ChatBot(int LLMCode){
     ChatBotGenerator generate = new ChatBotGenerator();
     chatBotName = generate.generateChatBotLLM(LLMCode);
    }

    //accessors
    public String getChatBotName() {return chatBotName;}
    public int getNumResponsesGenerated() {return numResponsesGenerated;}
    public static int getTotalNumResponsesGenerated(){return messageNumber;}
    public static int getMessageLimit(){return messageLimit;}
    
    public static int getTotalNumResponsesRemaining(){
        int j = getMessageLimit();
        int x = getTotalNumResponsesGenerated();
        return (j - x);}
        
    public static boolean limitReached(){
        if(getTotalNumResponsesGenerated()<getMessageLimit()){
            return false;
        }
        return true;
    }
    
    private String generateResponse(){
    
    numResponsesGenerated = numResponsesGenerated + 1;
    messageNumber = messageNumber + 1;  
      
    return "(Message #) " + messageNumber + " Response from " + getChatBotName() +
      "\t >>generatedTextHere";
      
    }
    
    public String prompt (String requestMessage){
        if(!limitReached()){
            return generateResponse();
        }
        
        else{
            return "Daily Limit Reached. Wait 24 hours to resume chatbot usage.";
        }
    }
    
    public String toString(){
        
        return "ChatBot Name: " + getChatBotName() + "\t Number Messages Used: " + 
            getNumResponsesGenerated();
    }
    
}
