package s_jamz;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExtractionTest {
    private static final String testZipFile = "test.zip";
    private static final String destinationDirectory = System.getProperty("user.dir") + "/src/test/resources/StudentSubmissions2/";
    private static final String studentFoldersDirectory = System.getProperty("user.dir") + "/src/test/resources/StudentFolders/";

    @BeforeEach
    public void setUp() throws IOException {
        Path testZipPath = Paths.get(testZipFile);
        if(!Files.exists(testZipPath)){
            Files.createFile(testZipPath);
        }

        Files.createDirectories(Paths.get(destinationDirectory));
        Files.createDirectories(Paths.get(studentFoldersDirectory));
        
    }

    @Test
    public void testExtractZip() throws IOException {
        File zipFile = new File(testZipFile);
        FileExtractor extractor = new FileExtractor();
        extractor.extractZip(zipFile, destinationDirectory);

        File submissionsDir = new File(destinationDirectory);
        assertTrue(submissionsDir.exists(), "Submissions directory should exist");

        File studentFoldersDir = new File(studentFoldersDirectory);
        assertTrue(studentFoldersDir.exists(), "Student folders directory should exist");
    }

    private void deleteDirectory(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.walk(path)
                .sorted((a, b) -> b.compareTo(a)) // Sort in reverse order to delete files before directories
                .forEach(p -> {
                    try {
                        Files.delete(p);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        }
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(testZipFile));
        deleteDirectory(Paths.get(destinationDirectory));
        deleteDirectory(Paths.get(studentFoldersDirectory));
    }



}
