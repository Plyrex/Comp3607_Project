import java.util.Random;//necessary import for generating responses
/**
 * Write a description of class ChatBot here.
 * 816034823
 * @author (your name)Zidane Timothy
 * @version (a version number or a date)02/03/2024
 */
public class ChatBot
{
    // instance variables 
    private String chatBotName;//Once declared shouldn't change by external use hence private
    private int numResponsesGenerated;//Once declared shouldn't change by external use hence private
    private static int messageLimit = 10;//Once declared should only change by specific use hence private
    public static int messageNumber = 0;//changes everytime the ChatBot is used
    
    public ChatBot(){
        ChatBotGenerator defLLM = new ChatBotGenerator();
        this.chatBotName = defLLM.generateChatBotLLM("ChatGpt-3.5");
    }
    
    public ChatBot(int LLMCode){
        // ChatBotGenerator specLLM = new ChatBotGenerator();
        this.chatBotName = ChatBotGenerator.ChatBotGenerator(LLMCode);
    }
    
    public String getChatBotName(){
        return chatBotName;
    }
    
    public int getNumResponsesGenerated(){
        return numResponsesGenerated;
    }

    public static int getTotalNumResponsesGenerated(){
        return messageNumber;//check back
    }
    
    public static int getTotalNumMessagesRemaining(){
        return (messageLimit - getTotalNumResponsesGenerated());
    }
    
    public static boolean limitReached(){
        if( getTotalNumResponsesGenerated() == 10){
            return true;
        }
        return false;
    }
    
    private String generateResponse(){//concepts repurposed from lab 1 part three
        Random rand = new Random();
        
        numResponsesGenerated += 1;
        messageNumber += 1;
        
        return("(Message: #" + messageNumber + ")" + "Response from " + this.chatBotName + " >>generatedTextHere ");
    }
    
    public String prompt(String requestMessage){
        if(!limitReached()){
            return generateResponse();
        }
        else{
            return ("Daily Limit Reached. Wait 24 hours to resume chatbot usage");
        }
    }
    
    public String toString() {
        return (getChatBotName() + " " + getTotalNumResponsesGenerated());
    }//to string followed from lab 2
    
    
}
