//816030520
//very self explanatory, no sources used.

public class ChatBotGenerator
{
   public String generateChatBotLLM (int LLMCodeNumber){
        if(LLMCodeNumber == 1){
            return "LLaMa";
        }
        if(LLMCodeNumber == 2){
            return "Mistral7B";
        }
        if(LLMCodeNumber == 3){
            return "Bard";
        }
        if(LLMCodeNumber == 4){
            return "Claude";
        }
        if(LLMCodeNumber == 5){
            return "Solar";
        }
        else{
            return "ChatGPT-3.5";
        }
    }
}
