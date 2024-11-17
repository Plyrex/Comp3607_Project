package s_jamz;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import s_jamz.AutoGrader.IsolatedClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class IsolatedClassLoaderTest {

    private File classDir;
    private File sourceFile;
    private File resourcesDir;

    @BeforeEach
    public void setUp() throws IOException {
        resourcesDir = new File("src/test/resources");
        classDir = new File(resourcesDir, "classes");
        if (!classDir.exists()) {
            classDir.mkdirs();
        }

        sourceFile = new File(classDir, "ChatBot.java");
        try (FileWriter writer = new FileWriter(sourceFile)) {
            writer.write("public class ChatBot {}");
        }

        //compiling the source file
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null, null, null, sourceFile.getPath());
        assertEquals(0, result, "Compilation should succeed");
    }

    @Test
    public void testLoadClass() throws IOException, ClassNotFoundException {
        //make URL pointing to the directory containing the class files
        URL[] urls = {classDir.toURI().toURL()};

        try (IsolatedClassLoader isolatedClassLoader = new IsolatedClassLoader(urls)) {
            Class<?> loadedClass = isolatedClassLoader.loadClass("ChatBot");

            //making sure that the class was loaded successfully
            assertNotNull(loadedClass, "Class should be loaded successfully");
            assertEquals("ChatBot", loadedClass.getSimpleName(), "Loaded class name should match");
            assertEquals(isolatedClassLoader, loadedClass.getClassLoader(), "Class should be loaded by the isolated class loader");
        }
    }

    @Test
    public void testLoadNonExistentClass() throws MalformedURLException {
        //make URL pointing to the directory containing the class files
        URL[] urls = {classDir.toURI().toURL()};

        try (IsolatedClassLoader isolatedClassLoader = new IsolatedClassLoader(urls)) {
            assertThrows(ClassNotFoundException.class, () -> {
                isolatedClassLoader.loadClass("ChatBotSimulation");
            }, "Loading a non-existent class should throw ClassNotFoundException");
        } catch (IOException e) {
            fail("IOException should not occur during class loading");
        }
    }

    @AfterEach
    public void tearDown() {
        //delete the class file and source file
        File compiledClassFile = new File(classDir, "ChatBot.class");
        if (compiledClassFile.exists()) {
            compiledClassFile.delete();
        }
        if (sourceFile.exists()) {
            sourceFile.delete();
        }
        if (classDir.exists() && classDir.isDirectory() && classDir.list().length == 0) {
            classDir.delete();
        }
        if (resourcesDir.exists() && resourcesDir.isDirectory() && resourcesDir.list().length == 0) {
            resourcesDir.delete();
        }
    }
}