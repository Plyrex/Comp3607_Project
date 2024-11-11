package s_jamz;

import org.junit.jupiter.api.*;
import s_jamz.TemplatePattern.JavaFileProcessor;

import javax.tools.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class JavaFileProcessorTest {

    private JavaFileProcessor processor;
    private Path tempDir;

    @BeforeEach
    public void setUp() throws IOException {
        processor = new JavaFileProcessor();
        tempDir = Files.createTempDirectory("testDir");
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.walk(tempDir)
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .forEach(File::delete);
    }

    @Test
    public void testExtractFile() throws IOException, ReflectiveOperationException {
        // Create a mock zip file with Java files
        Path zipFilePath = tempDir.resolve("test.zip");
        try (FileOutputStream fos = new FileOutputStream(zipFilePath.toFile());
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            ZipEntry entry = new ZipEntry("Test.java");
            zos.putNextEntry(entry);
            zos.write("public class Test {}".getBytes());
            zos.closeEntry();
        }

        // Use reflection to access the protected extractFile method
        Method extractFileMethod = JavaFileProcessor.class.getDeclaredMethod("extractFile", File.class);
        extractFileMethod.setAccessible(true);
        extractFileMethod.invoke(processor, zipFilePath.toFile());

        // Verify the extracted files
        Path extractedFile = tempDir.resolve("Test.java");
        assertTrue(Files.exists(extractedFile));
    }

    @Test
    public void testCompileFile() throws IOException, ReflectiveOperationException {
        // Create a directory with Java files
        Path javaFilePath = tempDir.resolve("Test.java");
        Files.write(javaFilePath, "public class Test {}".getBytes());

        // Use reflection to access the protected compileFile method
        Method compileFileMethod = JavaFileProcessor.class.getDeclaredMethod("compileFile", File.class);
        compileFileMethod.setAccessible(true);
        compileFileMethod.invoke(processor, tempDir.toFile());

        // Verify the compilation results
        Path binDir = tempDir.resolve("bin");
        assertTrue(Files.exists(binDir));
        Path compiledFile = binDir.resolve("Test.class");
        assertTrue(Files.exists(compiledFile));
    }

    @Test
    public void testStoreResults() throws IOException, ReflectiveOperationException {
        // Create a directory with Java files
        Path javaFilePath = tempDir.resolve("Test.java");
        Files.write(javaFilePath, "public class Test {}".getBytes());

        // Compile the files to generate diagnostics
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Collections.singletonList(javaFilePath.toFile()));
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
        task.call();
        fileManager.close();

        // Use reflection to access the protected storeResults method
        Method storeResultsMethod = JavaFileProcessor.class.getDeclaredMethod("storeResults", File.class, DiagnosticCollector.class);
        storeResultsMethod.setAccessible(true);
        storeResultsMethod.invoke(processor, tempDir.toFile(), diagnostics);

        // Verify the stored results
        Path resultFile = tempDir.resolve("bin/Test.log");
        assertTrue(Files.exists(resultFile));
        List<String> logLines = Files.readAllLines(resultFile);
        assertFalse(logLines.isEmpty());
    }

    @Test
    public void testNoJavaFiles() throws IOException, ReflectiveOperationException {
        // Create an empty directory
        Path emptyDir = tempDir.resolve("emptyDir");
        Files.createDirectories(emptyDir);

        // Use reflection to access the protected compileFile method
        Method compileFileMethod = JavaFileProcessor.class.getDeclaredMethod("compileFile", File.class);
        compileFileMethod.setAccessible(true);
        compileFileMethod.invoke(processor, emptyDir.toFile());

        // Verify no bin directory is created
        Path binDir = emptyDir.resolve("bin");
        assertFalse(Files.exists(binDir));
    }

    @Test
    public void testInvalidZipFile() throws IOException, ReflectiveOperationException {
        // Create an invalid zip file
        Path zipFilePath = tempDir.resolve("invalid.zip");
        Files.write(zipFilePath, "invalid content".getBytes());

        // Use reflection to access the protected extractFile method
        Method extractFileMethod = JavaFileProcessor.class.getDeclaredMethod("extractFile", File.class);
        extractFileMethod.setAccessible(true);
        extractFileMethod.invoke(processor, zipFilePath.toFile());

        // Verify no files are extracted
        Path extractedFile = tempDir.resolve("Test.java");
        assertFalse(Files.exists(extractedFile));
    }
}