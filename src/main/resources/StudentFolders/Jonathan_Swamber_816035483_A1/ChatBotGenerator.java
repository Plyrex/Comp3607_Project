/**
 * ID:816035483
 * This class has one method whis is responsible for accepting an integer
 * as inpur and returns the name of a chatbot based on the number it was 
 * assigned to. The default was set to "ChatGPT-3.5".
 */
public class ChatBotGenerator {
    public static String generateChatBotLLM(int LLMCodeNumber) {
        if (LLMCodeNumber == 1) {
            return "LLaMa";
        } else if (LLMCodeNumber == 2) {
            return "Mistral7B";
        } else if (LLMCodeNumber == 3) {
            return "Bard";
        } else if (LLMCodeNumber == 4) {
            return "Claude";
        } else if (LLMCodeNumber == 5) {
            return "Solar";
        } else {
            return "ChatGPT-3.5";
        }
    }
}
/**
 * Sources used:
 * 1)Used to learn the implementation of if statements in java (discovered its the same as C++)
 *   Also was addresssed in the labs.
 *   https://docs.oracle.com/javase/tutorial/java/nutsandbolts/if.html
 */