package s_jamz.AutoGrader;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.itextpdf.io.source.ByteArrayOutputStream;

import s_jamz.CompositePattern.TestResultLeaf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainTest {

    private Class<?> chatBotSimulationClass;
    private static HashMap<String, TestResultLeaf> testResults = new HashMap<>();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        // Redirecting System.out to capture the printed output
        try{
            loadClass("ChatBotSimulation");
        }
        catch(Exception e){
            System.err.println("Exception: " + e.getMessage());
        }
        System.setOut(new PrintStream(outContent));
    }

    private void loadClass(String className) throws Exception {
        // Use the specified path for the student folders
        File studentFoldersDir = new File(System.getProperty("user.dir") + "/src/main/resources/StudentFolders/");
        if (!studentFoldersDir.exists() || !studentFoldersDir.isDirectory()) {
            throw new IllegalArgumentException("Invalid student folders path: " + studentFoldersDir.getAbsolutePath());
        }

        // Iterate through each student folder
        for (File studentDir : studentFoldersDir.listFiles()) {
            if (studentDir.isDirectory()) {
                // Navigate to the bin directory
                File binDir = new File(studentDir, "bin");
                if (binDir.exists() && binDir.isDirectory()) {

                    URL[] urls = {binDir.toURI().toURL()};
                    URLClassLoader urlClassLoader = new URLClassLoader(urls, this.getClass().getClassLoader());
                    try {
                        chatBotSimulationClass = Class.forName(className, true, urlClassLoader);
                    } catch (ClassNotFoundException e) {
                        // Continue searching in other student folders
                    }
                }
            }
        }
    }

   

@Test
public void testMainMethod() {
    // Running the main method of ChatBotSimulation
    StringBuilder feedback = new StringBuilder();
    int score = 0;
    String[] args = {}; // No arguments needed
    
    try {
        // Capture output from the main method
        System.setOut(new PrintStream(outContent));
        Method mainMethod = chatBotSimulationClass.getMethod("main", String[].class);
        mainMethod.invoke(null, (Object) args);

        // Test 1: Check if "Hello World!" is printed (1 mark)
        String output = outContent.toString();

        if (output.contains("Hello World!")) {
            feedback.append("Test 1: 'Hello World!' printed.\n");
            score++;
        } else {
            feedback.append("Test 1: 'Hello World!' not printed.\n");
        }

        // Test 2: Verify ChatBotPlatform is declared and initialized (1 mark)
        if (output.contains("Your ChatBots")) {
            feedback.append("Test 2: ChatBot declared and initialised and added to chatBotPlatform. \n");
            score=score + 3;
        } else {
            feedback.append("Test 2: ChatBot not declared and initialised.\n");
        }

        // Test 3: Verify that the correct list of ChatBots with their summary statistics is printed (2 marks)
        if (output.contains("Bot Number: 1") && output.contains("Bot Number: 2") && output.contains("Bot Number: 3") && output.contains("Bot Number: 4") &&
        output.contains("Bot Number: 5") && output.contains("Bot Number: 6")) {
            feedback.append("Test 3: Prints list of chatBots\n");
            score = score + 2;
        } else {
            feedback.append("Test 3: Did not find 'ChatGPT-3.5' with 0 messages used.\n");
        }
        
        boolean interacted = false;

        // Test 4: Check that responses from random interactions with the bots are printed (4 marks)
        if (output.contains("Response from Solar")) {
            interacted = true;
        } 

        if (output.contains("Response from ChatGPT-3.5")) {
            interacted = true;
        } 

        if (output.contains("Response from Mistral7B")) {
           interacted = true;
        } 
        if (output.contains("Response from Bard")) {
            interacted = true;
         } 
         if (output.contains("Response from Claude")) {
            interacted = true;
         } 
         if (output.contains("Response from LLaMa")) {
            interacted = true;
         } 
         
         if(interacted == true){
            feedback.append("Test 4: Interacts with ChatBots. \n");
            score = score + 4;
         }

        // Test 6: Verify final list of all ChatBots with updated message counts (2 marks)
        if (output.contains("Total Messages Remaining: 0")) {
            feedback.append("Test 6: Final list shown. \n");
            score=score + 2;
        } else {
            feedback.append("Test 6: Final list not shown. \n");
        }


        // Output final feedback
        feedback.append("Total Score: ").append(score).append("/12\n");
        testResults.put("ChatBotSimulation", new TestResultLeaf(score, feedback.toString()));

    } catch (Exception e) {
        feedback.append("Error occurred: ").append(e.getMessage()).append("\n");
    }
}
 
    public static HashMap<String, TestResultLeaf> getTestResults() {
        return testResults;

    }

    @AfterEach
    public void restoreSystemOut() {
        // Restoring the original System.out after the test
        System.setOut(originalOut);

    }
   
}
