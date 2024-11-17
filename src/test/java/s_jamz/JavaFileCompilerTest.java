package s_jamz;

import org.junit.jupiter.api.*;
import s_jamz.TemplatePattern.JavaFileProcessor;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class JavaFileCompilerTest {

    private JavaFileProcessor processor;
    private Path temporaryDirectory;

    @BeforeEach
    public void setUp() throws IOException {
        processor = new JavaFileProcessor();
        temporaryDirectory = Files.createTempDirectory("testDir");
    }


    @Test
    public void testCompileFile() throws IOException, ReflectiveOperationException {
        Path javaFilePath = temporaryDirectory.resolve("Test.java");
        Files.write(javaFilePath, "public class Test {}".getBytes());

        Method compileFileMethod = JavaFileProcessor.class.getDeclaredMethod("compileFile", File.class);
        compileFileMethod.setAccessible(true);
        compileFileMethod.invoke(processor, temporaryDirectory.toFile());

        Path binDir = temporaryDirectory.resolve("bin");
        assertTrue(Files.exists(binDir), "Bin directory should be created.");
        Path compiledFile = binDir.resolve("Test.class");
        assertTrue(Files.exists(compiledFile), "Compiled .class file should exist.");
    }


    @Test
    public void testNoJavaFiles() throws IOException, ReflectiveOperationException {
        // make empty directory
        Path emptyDir = temporaryDirectory.resolve("emptyDir");
        Files.createDirectories(emptyDir);

        // using reflection to get the protected compileFile method
        Method compileFileMethod = JavaFileProcessor.class.getDeclaredMethod("compileFile", File.class);
        compileFileMethod.setAccessible(true);
        compileFileMethod.invoke(processor, emptyDir.toFile());

        // making sure no bin directory is created
        Path binDir = emptyDir.resolve("bin");
        assertFalse(Files.exists(binDir), "Bin directory should not be created for empty input.");
    }

    @Test
    public void testInvalidZipFile() throws IOException, ReflectiveOperationException {
        // making a invalid zip file
        Path zipFilePath = temporaryDirectory.resolve("invalid.zip");
        Files.write(zipFilePath, "invalid content".getBytes());

        // using the java reflections to get the protected extractFile method
        Method extractFileMethod = JavaFileProcessor.class.getDeclaredMethod("extractFile", File.class);
        extractFileMethod.setAccessible(true);
        extractFileMethod.invoke(processor, zipFilePath.toFile());

        // make sure no files are extracted
        Path extractedFile = temporaryDirectory.resolve("Test.java");
        assertFalse(Files.exists(extractedFile));
    }

    private void deleteDirectory(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }
    
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    @AfterEach
    public void tearDown() throws IOException {
        deleteDirectory(temporaryDirectory);
    }
}