//816030520
import java.util.Random; //Lab 1 part 3

public class ChatBotSimulation
{
    public static void main (String[] args){
        
       System.out.println("Hello World!");
       System.out.println("--------------------------------");
       
       ChatBotPlatform chat = new ChatBotPlatform();
        
       for(int i=0; i<=6; i++){
           chat.addChatBot(i); //Java, Java, Java p 784
       }
       
       System.out.println("Your ChatBots");
       System.out.println(chat.getChatBotList());
       
       System.out.println("--------------------------------");
       
       Random random = new Random(); //ioflood.com Java Math.random() guide. link at bottom
       int i;
       for (int j=1; j<=15; j++){
          i = random.nextInt(8); //ioflood.com Java Math.random() guide. see link at bottom 
           
           System.out.println(chat.interactWithBot(i,"String"));
       }
       
       System.out.println("--------------------------------");
       
       System.out.println("Your ChatBots");
       System.out.println(chat.getChatBotList());
       
       System.out.println("--------------------------------");

    }
    //https://ioflood.com/blog/math-random-java/#:~:text=Generating%20Random%20Numbers%20Within%20a%20Range%20in%20Java (continues down below)
    //,-While%20Math.&text=random()%20generates%20a%20double,start%20value%20of%20the%20range.
}
