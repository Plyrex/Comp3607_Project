
/**
 * Write a description of class ChatBotGenerator here.
 * 816034823
 * @author (your name)Zidane Timothy
 * @version (a version number or a date)02/03/2024
 */
public class ChatBotGenerator
{
    // instance variables - replace the example below with your own
    

    /**
     * Constructor for objects of class ChatBotGenerator
     */
    public static String ChatBotGenerator(int LLMCodeNumber)
    {
        // initialise instance variables
        if(LLMCodeNumber == 2){
            return "LLama";
        }
        else if(LLMCodeNumber == 3){
            return "Mistral7B";
        }
        else if(LLMCodeNumber == 4){
            return "Bard";
        }
        else if(LLMCodeNumber == 5){
            return "Claude";
        }
        else if(LLMCodeNumber == 6){
            return "Solar";
        }
        else{
            return "ChatGPT-3.5";
        }
        
    }
    
    String generateChatBotLLM(String botName){
       return botName; 
    }  
}
