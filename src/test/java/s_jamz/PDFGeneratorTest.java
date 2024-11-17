package s_jamz;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import s_jamz.CompositePattern.TestResultLeaf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PDFGeneratorTest {

    private PDFGenerator pdfGenerator;
    private File testDir;
    private HashMap<String, TestResultLeaf> behaviour;
    private HashMap<String, TestResultLeaf> naming;
    private HashMap<String, TestResultLeaf> signature;
    private HashMap<String, TestResultLeaf> attribute;

    @BeforeEach
    public void setUp() {
        pdfGenerator = new PDFGenerator();
        testDir = new File("src/test/resources/StudentFolders/FirstName_Lastname_123456789_A1");
        if (!testDir.exists()) {
            testDir.mkdirs();
        }

        behaviour = new HashMap<>();
        naming = new HashMap<>();
        signature = new HashMap<>();
        attribute = new HashMap<>();

        // putting the dummy test results
        behaviour.put("ChatBot", new TestResultLeaf(23, "ChatBot behaviour test feedback"));
        naming.put("ChatBot", new TestResultLeaf(5, "ChatBot naming test feedback"));
        signature.put("ChatBot", new TestResultLeaf(8, "ChatBot signature test feedback"));
        attribute.put("ChatBot", new TestResultLeaf(10, "ChatBot attribute test feedback"));
    }

    @Test
    public void testGeneratePDF() throws FileNotFoundException {
        pdfGenerator.generatePDF(testDir, behaviour, signature, attribute);

        File pdfFile = new File(testDir, "FirstName_Lastname_123456789_A1_results.pdf");
        assertTrue(pdfFile.exists(), "PDF file should be generated");
    }

    @AfterEach
    public void tearDown() {
        deleteDirectory(new File("src/test/resources/StudentFolders"));
        deleteDirectory(new File("src/test/resources"));
    }

    private void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        directory.delete();
    }

    
}