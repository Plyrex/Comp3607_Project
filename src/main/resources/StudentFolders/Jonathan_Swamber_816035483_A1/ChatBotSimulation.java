/**
 * ID:816035483
 * This is the main class. It initializes a platform, adds the different 
 * chatbots, and manages interactions with them. Its also responsable for 
 * displaying the final stats of all chatbots
 */
import java.util.Random;
public class ChatBotSimulation {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        ChatBotPlatform platform = new ChatBotPlatform();

        // Adding several ChatBot objects (at least one of each kind) to the ChatBotPlatform
        for (int LLMCode = 0; LLMCode <= 6; LLMCode++) {
            platform.addChatBot(LLMCode);
        }

        // Printing out on the screen the list of all ChatBots managed by the ChatBotPlatform with summary statistics
        System.out.println(platform.getChatBotList());

        // Interact to 15 times with random ChatBots by sending messages and printing the responses
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            int botNumber = random.nextInt(8); 
            String message = "This is message number " + (i + 1);
            // Geting the bot's response
            String response = platform.interactWithBot(botNumber, message); 
            System.out.println( response);
        }

        // Printing a final list of all ChatBots from the ChatBotPlatform with a summary
        System.out.println(platform.getChatBotList());
    }
}
/**
 * Sources used:
 * 1) To learn how to generate random numbers:
 *    https://www.educative.io/answers/how-to-generate-random-numbers-in-java
 */
