//816030520
import java.util.ArrayList; //ArrayList sheet on course shell

public class ChatBotPlatform
{
    ArrayList<ChatBot> bots; //p 784 Java, Java, Java Morelli and Wilde
    
    //constructor
    public ChatBotPlatform()
    {
        bots = new ArrayList<ChatBot>(); //p 784 Java, Java, Java Morelli and Wilde
    }
    
    //methods
    public boolean addChatBot(int LLMcode){
        
        if(ChatBot.limitReached()){
            return false;
        }
        
        ChatBot chatBot = new ChatBot(LLMcode);
        bots.add(chatBot); //Java Java Java p 784
        return true;
    }
    
    public String getChatBotList(){
        int count = 0;
        String output = "";
        
        for (ChatBot b: bots){ //ArrayList handout on course shell
            output = output + ("Bot Number: " + bots.indexOf(b) + " " + b.toString() + "\n"); //indexOf method sourced from oracle documentation available on course shell (see link below)
            count = count + 1;
        }
        
        
        output = output + "Total Messages Used: " + ChatBot.getTotalNumResponsesGenerated() + "\nTotal Messages Remaining: " + ChatBot.getTotalNumResponsesRemaining(); 
        return output;
    }
    
    public String interactWithBot (int botNumber, String message){
        String response ="";
        
        if(botNumber < bots.size()){
            ChatBot chatter = bots.get(botNumber); //oracle documentation
            response =  chatter.prompt(message);
        }
        else{
            response = "Incorrect bot number (" + botNumber + ") Selected. Try again.";        }
            
        return response;
        
    }
    //https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ArrayList.html
}
