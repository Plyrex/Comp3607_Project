package s_jamz;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import s_jamz.Utilities.FileExtractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class ExtractionTest {
    private static final String testZipFile = "test.zip";
    private static final String destinationDirectory = System.getProperty("user.dir") + "/src/test/resources/StudentSubmissions2/";
    private static final String studentFoldersDirectory = System.getProperty("user.dir") + "/src/test/resources/StudentFolders/";
    private static final String testResourcesDirectory = System.getProperty("user.dir") + "/src/test/resources/";
    private static final String mainResourcesDirectory = System.getProperty("user.dir") + "/src/main/resources/";

    @BeforeEach
    public void setUp() throws IOException {
        Path testZipPath = Paths.get(testZipFile);
        if (!Files.exists(testZipPath)) {
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
        Files.deleteIfExists(Paths.get(testZipFile));
        deleteDirectory(Paths.get(destinationDirectory));
        deleteDirectory(Paths.get(studentFoldersDirectory));
        deleteDirectory(Paths.get(testResourcesDirectory));
        deleteDirectory(Paths.get(mainResourcesDirectory));
    }
}