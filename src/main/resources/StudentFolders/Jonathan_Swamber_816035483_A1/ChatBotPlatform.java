/**
 * ID:816035483
 * The ChatBotPlatform class manages the different ChatBot objects.
 * It allows chatbots to be added and interacted with, and displays usage stats.
 */
import java.util.ArrayList;
public class ChatBotPlatform{
    // Storing all the chatbots in an array list
    private ArrayList<ChatBot> bots;
    
    // Constructor
    public ChatBotPlatform(){
        bots = new ArrayList<>();
    }
    
    // Method that adds a new ChatBot to the platform
    public boolean addChatBot(int LLMcode){
        if(!ChatBot.limitReached()){
            bots.add(new ChatBot(LLMcode));
            return true;
        } else {
            return false;
        }
    }
    
    //Method that gets a list of all the ChatBots on the platform
    public String getChatBotList() {
    String result = "Your ChatBots\n";
    // Looping over all bots to get the current ChatBot name and no. of messages.
    for (int i = 0; i < this.bots.size(); i++) {
        ChatBot currentBot = this.bots.get(i);
        String botName = currentBot.getChatBotName();
        int numMessagesUsed = currentBot.getNumResponsesGenerated();
        result = result + "Bot Number: " + i + " ChatBot Name: " +
                 botName + " Number Messages Used: " + numMessagesUsed + "\n";
    }

    // Getting the total number of messages and remaining messages
    int totalMessagesUsed = ChatBot.getTotalNumResponsesGenerated();
    int totalMessagesRemaining = ChatBot.getTotalNumMessagesRemaining();
    
    result = result + "Total Messages Used: " + totalMessagesUsed + 
             "\nTotal Messages Remaining: " + totalMessagesRemaining;
            
    return result;
    }
    
    //Method that interacts with a specific chatbot
    public String interactWithBot(int botNumber, String message) {
    if (botNumber < 0 || botNumber >= this.bots.size()) {
        return "Incorrect Bot Number (" + botNumber + ") Selected. Try again";
    } else {
        return this.bots.get(botNumber).prompt(message);
    }
    }

}
/**
 * 
 *Sources used:
 *1)Used for understanding how ArrayList<> is implemented
 *  https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
 *  https://www.javatpoint.com/java-arraylist

 *2)Used for understaning how to retrive the elements of an index in ArrayList.
 *  https://www.codecademy.com/resources/docs/java/array-list/get
 */
