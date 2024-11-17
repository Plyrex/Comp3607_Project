package s_jamz.AutoGrader;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.lang.reflect.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import s_jamz.CompositePattern.TestResultLeaf;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AttributeTypeTest {

    private static int totalScore;
    private static int chatBotScore = 0;
    private static int chatBotPlatformScore = 0;
    private static StringBuilder feedback = new StringBuilder();

    private HashMap<String, Field[]> attributeTest;
    private static HashMap<String, TestResultLeaf> testResults = new HashMap<>();

    public AttributeTypeTest() {
        attributeTest = new HashMap<>();
    }

    @BeforeEach
    public void setup(){
        try{
            loadAttributeNames("ChatBot");
            loadAttributeNames("ChatBotPlatform");
        }
        catch(Exception e){
            System.err.println("Could not load attribute names for class: " + e.getMessage());
        }
    }

    public Field[] getClassFields(Class<?> class1) {
        try {
            if (class1 == null) {
                System.err.println("Provided class is null.");
                return new Field[0];
            }
            return class1.getDeclaredFields();
        } catch (Exception e) {
            System.err.println("An error occurred while retrieving fields: " + e.getMessage());
            return new Field[0];
        }
    }

    public void printMap() {
        for (Map.Entry<String, Field[]> entry : attributeTest.entrySet()) {
            System.out.println("Class: " + entry.getKey());
            Field[] fields = entry.getValue();
            for (Field field : fields) {
                System.out.println("  Field: " + field.getName() + ", Type: " + field.getType().getSimpleName());
            }
        }
    }

    private Class<?> loadClass(String className, URL studentBinDir) throws Exception {
        URL[] urls = {studentBinDir};
        try (IsolatedClassLoader loader = new IsolatedClassLoader(urls)) {
            return Class.forName(className, true, loader);
        }
    }

    public void loadAttributeNames(String className) throws Exception {
        File studentFoldersDir = new File(System.getProperty("user.dir") + "/src/main/resources/StudentFolders/");
        if (!studentFoldersDir.exists() || !studentFoldersDir.isDirectory()) {
            throw new IllegalArgumentException("Invalid student folders path: " + studentFoldersDir.getAbsolutePath());
        }

        for (File studentDir : studentFoldersDir.listFiles()) {
            if (studentDir.isDirectory()) {
                File binDir = new File(studentDir, "bin");
                if (binDir.exists() && binDir.isDirectory()) {
                    URL studentBinDir = binDir.toURI().toURL();
                    // System.out.println("Loading class: " + className + " from student folder: " + studentDir.getName());
                    try {
                        Class<?> class1 = loadClass(className, studentBinDir);
                        attributeTest.put(className, getClassFields(class1));
                    } catch (ClassNotFoundException e) {
                        // Continue searching in other student folders
                    }
                }
            }
        }
    }

    @Test
    @Order(1)
    public void chatBotAttributeTest() {
        System.out.println("ChatBot Test. \n");
        Field[] chatBotAttributes = attributeTest.get("ChatBot");
        int score = 0;
        HashMap<String, Class<?>> expectedAttributes = new HashMap<>();

        expectedAttributes.put("chatBotName", String.class);
        expectedAttributes.put("numResponsesGenerated", int.class);
        expectedAttributes.put("messageLimit", int.class);
        expectedAttributes.put("messageNumber", int.class);

        for (Field field : chatBotAttributes) {
            try {
                if (expectedAttributes.containsKey(field.getName())) {
                    feedback.append("Class contains: ").append(field.getName()).append(". ");
                }
                else{
                    feedback.append("Class contains: ").append(". Required one of: ").append(expectedAttributes.keySet());
                }
                if (expectedAttributes.containsKey(field.getName()) && expectedAttributes.get(field.getName()).equals(field.getType())) {
                    feedback.append(field.getName()).append(" has correct type. \n");
                    score++;
                } else {
                    feedback.append(field.getName()).append(" has incorrect type.\n");
                }
                if (expectedAttributes.containsKey(field.getName()) && 
                    (field.getName().equals("messageLimit") || field.getName().equals("messageNumber"))
                     && java.lang.reflect.Modifier.isStatic(field.getModifiers())){

                    feedback.append(field.getName()).append(" is static. ");
                    score++;
                }

                if (expectedAttributes.containsKey(field.getName()) && field.getName().equals("messageLimit")
                 && java.lang.reflect.Modifier.isFinal(field.getModifiers())){

                    feedback.append(field.getName()).append(" is final. ");
                    score++;
            }
            } catch (Exception e) {
                feedback.append(e.getMessage()).append("\n");
            }
        }

        chatBotScore = score;
        totalScore += chatBotScore;
        feedback.append("ChatBot Class Score: ").append(chatBotScore).append("/7\n");
        testResults.put("ChatBot", new TestResultLeaf(chatBotScore, feedback.toString()));
    }

    @Test
    @Order(2)
    public void chatBotPlatformAttributeTest() {
        System.out.println("ChatBotPlatform Test. \n");
        Field[] chatBotPlatformAttributes = attributeTest.get("ChatBotPlatform");
        int score = 0;
        HashMap<String, Class<?>> expectedAttributes = new HashMap<>();

        expectedAttributes.put("bots", ArrayList.class);

        for (Field field : chatBotPlatformAttributes) {
            try {
                if (expectedAttributes.containsKey(field.getName())) {
                    feedback.append("Class contains: ").append(field.getName()).append(". ");
                }
                if (expectedAttributes.containsKey(field.getName()) && expectedAttributes.get(field.getName()).equals(field.getType())) {
                    feedback.append(field.getName()).append(" has correct type. \n");
                    score= score+ 2;
                } else {
                    feedback.append(field.getName()).append(" has incorrect type.\n");
                }
            } catch (Exception e) {
                feedback.append(e.getMessage()).append("\n");
            }
        }

        chatBotPlatformScore = score;
        totalScore += chatBotPlatformScore;
        feedback.append("ChatBotPlatform Class Score: ").append(chatBotPlatformScore).append("/2\n");
        testResults.put("ChatBotPlatform", new TestResultLeaf(chatBotPlatformScore, feedback.toString()));
    }

    @AfterAll
    public static void calculateTotal() {
        feedback.append("Total Score = " + totalScore + "/9 \n");
        chatBotScore = 0;
        chatBotPlatformScore = 0;
        totalScore = 0;
        feedback.delete(0, feedback.length());
    }

    public static HashMap<String, TestResultLeaf> getTestResults() {
        return testResults;
    }
}