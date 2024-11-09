import java.util.ArrayList; //necessary import statement
/**
 * Write a description of class ChatBotPlatform here.
 * 816034823
 * @author (your name)Zidane Timothy
 * @version (a version number or a date)02/03/2024
 */


public class ChatBotPlatform
{
    private ArrayList <ChatBot> bots;//taken from arrayList handout

    /**
     * Constructor for objects of class ChatBotPlatform
     */
    
    public ChatBotPlatform()
    {
        // initialise instance variables
        bots = new ArrayList<>();//Taken from arrayList handout
        //figured out from given handout: ArrayList https://myelearning.sta.uwi.edu/pluginfile.php/2218356/mod_resource/content/0/Arraylist%20Handout.pdf
    }
    
    public boolean addChatBot(int LLMcode){        
        if(ChatBot.limitReached() == true){
            return false;
        }
        bots.add(new ChatBot(LLMcode));

        return true;
    }
    
    public String getChatBotList(){
        String output="";
        for(int i = 0; i < this.bots.size(); i++){
            output = output + ("Bot Number: " + i + " ChatBot Name: " + bots.get(i).getChatBotName() +  " Number Messages Used: " + bots.get(i).getNumResponsesGenerated() +"\n");
        }
        return (output + "\n" + "Total Messages Used: " + bots.get(0).getTotalNumResponsesGenerated() + " " + "\nTotal Messages Remaining: " + bots.get(0).getTotalNumMessagesRemaining());
    }
    
    public String interactWithBot(int botNumber, String message){//actual passing of messages between bots
        // ChatBot bot = this.bots.get(botNumber);
        
        if(botNumber > 6){
            return ("Incorrect Bot Number(" + botNumber + ") Selected. Try again");
        }
        else{
                return( this.bots.get(botNumber).prompt(message));
            }
        }
        //public String toString(){
    //    return ("Bot Number: " + bots.get().getTotalNumResponsesGenerated() + " ChatBot Name: " + bots.get() +  " Number Messages Used: " + bots.get(i).getNumResponsesGenerated());
    //}
    //additional resources used in ChatBotSimulation
    }
    

    

