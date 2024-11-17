package s_jamz.AutoGrader;

import org.junit.After;
import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.itextpdf.io.source.ByteArrayOutputStream;

import s_jamz.CompositePattern.TestResultLeaf;

// import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Modifier;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Order;

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
            feedback.append("Test 2: 'Your ChatBots' printed after adding ChatBots.\n");
            score++;
        } else {
            feedback.append("Test 2: 'Your ChatBots' not printed.\n");
        }

        // Test 3: Verify that the correct list of ChatBots with their summary statistics is printed (2 marks)
        if (output.contains("Bot Number: 0 ChatBot Name: ChatGPT-3.5 Number Messages Used: 0")) {
            feedback.append("Test 3: Found 'ChatGPT-3.5' with 0 messages used.\n");
            score++;
        } else {
            feedback.append("Test 3: Did not find 'ChatGPT-3.5' with 0 messages used.\n");
        }
        
        if (output.contains("Bot Number: 1 ChatBot Name: LLaMa Number Messages Used: 0")) {
            feedback.append("Test 3: Found 'LLaMa' with 0 messages used.\n");
            score++;
        } else {
            feedback.append("Test 3: Did not find 'LLaMa' with 0 messages used.\n");
        }

        if (output.contains("Bot Number: 2 ChatBot Name: Mistral7B Number Messages Used: 0")) {
            feedback.append("Test 3: Found 'Mistral7B' with 0 messages used.\n");
            score++;
        } else {
            feedback.append("Test 3: Did not find 'Mistral7B' with 0 messages used.\n");
        }

        // Test 4: Check that responses from random interactions with the bots are printed (4 marks)
        if (output.contains("Response from Solar")) {
            feedback.append("Test 4: Response from 'Solar' ChatBot found.\n");
            score++;
        } else {
            feedback.append("Test 4: Response from 'Solar' not found.\n");
        }

        if (output.contains("Response from ChatGPT-3.5")) {
            feedback.append("Test 4: Response from 'ChatGPT-3.5' ChatBot found.\n");
            score++;
        } else {
            feedback.append("Test 4: Response from 'ChatGPT-3.5' not found.\n");
        }

        if (output.contains("Response from Mistral7B")) {
            feedback.append("Test 4: Response from 'Mistral7B' ChatBot found.\n");
            score++;
        } else {
            feedback.append("Test 4: Response from 'Mistral7B' not found.\n");
        }

        // Test 5: Check for error messages like "Incorrect Bot Number" and "Daily Limit Reached" (1 mark)
        if (output.contains("Incorrect Bot Number")) {
            feedback.append("Test 5: 'Incorrect Bot Number' error message found.\n");
            score++;
        } else {
            feedback.append("Test 5: 'Incorrect Bot Number' error message not found.\n");
        }

        if (output.contains("Daily Limit Reached")) {
            feedback.append("Test 5: 'Daily Limit Reached' error message found.\n");
            score++;
        } else {
            feedback.append("Test 5: 'Daily Limit Reached' error message not found.\n");
        }

        // Test 6: Verify final list of all ChatBots with updated message counts (2 marks)
        if (output.contains("Bot Number: 0 ChatBot Name: ChatGPT-3.5 Number Messages Used: 4")) {
            feedback.append("Test 6: Final list shows 'ChatGPT-3.5' with 4 messages used.\n");
            score++;
        } else {
            feedback.append("Test 6: Did not find 'ChatGPT-3.5' with 4 messages used in final list.\n");
        }

        if (output.contains("Bot Number: 1 ChatBot Name: LLaMa Number Messages Used: 0")) {
            feedback.append("Test 6: Final list shows 'LLaMa' with 0 messages used.\n");
            score++;
        } else {
            feedback.append("Test 6: Did not find 'LLaMa' with 0 messages used in final list.\n");
        }

        if (output.contains("Bot Number: 5 ChatBot Name: Solar Number Messages Used: 3")) {
            feedback.append("Test 6: Final list shows 'Solar' with 3 messages used.\n");
            score++;
        } else {
            feedback.append("Test 6: Did not find 'Solar' with 3 messages used in final list.\n");
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
