import java.util.Random;
/**
 * Write a description of class ChatBotSimulation here.
 * 816034823
 * @author (your name)  Zidane Timothy
 * @version (a version number or a date)02/03/2024
 */
public class ChatBotSimulation
{
    // instance variables - replace the example below with your own
    // private int x;

    // /**
     // * Constructor for objects of class ChatBotSimulation
     // */
    // // public ChatBotSimulation()
    // // {
        // // // initialise instance variables
         
    // // }
    
    public static void main (String[] args){
        //1.
        System.out.println("Hello World");
        
        //2.
        ChatBotPlatform bots = new ChatBotPlatform();
        
        //3.
        ChatBotPlatform b1 = new ChatBotPlatform();
        bots.addChatBot(1);       
        ChatBotPlatform b2 = new ChatBotPlatform();
        bots.addChatBot(2);
        ChatBotPlatform b3 = new ChatBotPlatform();
        bots.addChatBot(3);
        ChatBotPlatform b4 = new ChatBotPlatform();
        bots.addChatBot(4);
        ChatBotPlatform b5 = new ChatBotPlatform();
        bots.addChatBot(5);
        ChatBotPlatform b6 = new ChatBotPlatform();
        bots.addChatBot(6);
        ChatBotPlatform b7 = new ChatBotPlatform();
        bots.addChatBot(7);
        
        //4
    
        System.out.println(bots.getChatBotList());
        
        
        //5.
        //interact with chatbot 15 times
        System.out.println("\n");
        int count=0;
    
        int botNum;
        Random rand = new Random();
       
        while(count != 15){
            String user = "Message";
            botNum = rand.nextInt(8);
            System.out.println(bots.interactWithBot(botNum, user));
            count++;
        }//figured out from lab 1
        
        //6.
        System.out.println("\n");
        System.out.println(bots.getChatBotList());
        
        /*
         * https://youtu.be/-Y67pdWHr9Y?si=rIa48Go0bdKRp2oP
         * https://youtube.com/playlist?list=PLkeaG1zpPTHiMjczpmZ6ALd46VjjiQJ_8&si=otXTAtQlmqVqiiZd
         * https://runestone.academy/ns/books/published/javajavajava/chapter-objects.html
         * https://runestone.academy/ns/books/published/javajavajava/chapter-methods.html
         */
        
    }

}
