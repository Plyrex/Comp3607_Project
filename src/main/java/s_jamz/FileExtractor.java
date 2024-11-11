package s_jamz;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class FileExtractor {
    public void extractZip(File zipFile, String destFolder) throws IOException {
        String submissionsFolder = System.getProperty("user.dir") + "/src/main/resources/StudentSubmissions2/";
        String studentFoldersDir = System.getProperty("user.dir") + "/src/main/resources/StudentFolders/";

        Path submissionsPath = Paths.get(submissionsFolder);
        Path studentFoldersPath = Paths.get(studentFoldersDir);

        // Create Submissions directory if it doesn't exist
        File submissionsDir = submissionsPath.toFile();
        if (!submissionsDir.exists()) {
            submissionsDir.mkdirs();
        }

        // Create StudentFolders directory if it doesn't exist
        File studentFoldersDirectory = studentFoldersPath.toFile();
        if (!studentFoldersDirectory.exists()) {
            studentFoldersDirectory.mkdirs();
        }

        // Extract the main zip file
        try (ZipFile zip = new ZipFile(zipFile)) {
            zip.extractAll(submissionsFolder);
            System.out.println("Main zip file " + zipFile.getName() + " unzipped successfully to " + submissionsFolder + "\n");
        } catch (ZipException e) {
            System.out.println("Error unzipping main file " + zipFile.getName() + "\n");
            e.printStackTrace();
        }

        // Verify the contents of the Submissions directory
        File[] submissionFiles = submissionsDir.listFiles();
        if (submissionFiles != null) {
            System.out.println("Files in Submissions directory:");
            for (File file : submissionFiles) {
                System.out.println(file.getName());
            }
        } else {
            System.out.println("Submissions directory is empty or not accessible.");
            return;
        }

        // Extract individual student submissions
        if (submissionFiles != null) {
            for (File submission : submissionFiles) {
                if (submission.isFile() && submission.getName().endsWith(".zip")) {
                    // Skip unwanted zip files
                    if (submission.getName().equalsIgnoreCase("compressed.zip")) {
                        System.out.println("Skipping unwanted zip file: " + submission.getName());
                        continue;
                    }

                    String studentFolder = studentFoldersDir + "/" + submission.getName().replace(".zip", "");
                    File studentFolderFile = new File(studentFolder);
                    if (studentFolderFile.exists()) {
                        System.out.println("Student folder already exists: " + studentFolder);
                        continue;
                    }
                    try {
                        try (ZipFile studentZip = new ZipFile(submission)) {
                            studentZip.extractAll(studentFolder);
                            System.out.println("Student zip file " + submission.getName() + " unzipped successfully to " + studentFolder + "\n");
                        }
                    } catch (ZipException e) {
                        System.out.println("Error unzipping student file " + submission.getName() + "\n");
                        e.printStackTrace();
                    }

                    // Extract only .java files from the student folder
                }
            }
        }

        // Delete the extracted master file directory if it is empty
        deleteDirectoryIfEmpty(submissionsDir);
    }

    private void extractJavaFiles(File studentFolder, Path outputDir) {
        File[] files = studentFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    extractJavaFiles(file, outputDir);
                } else if (file.getName().endsWith(".java")) {
                    try {
                        Path filePath = outputDir.resolve(file.getName());
                        Files.createDirectories(filePath.getParent());
                        Files.copy(file.toPath(), filePath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Extracted Java file: " + file.getName() + " to " + filePath.toString());
                    } catch (IOException e) {
                        System.out.println("Error extracting Java file " + file.getName() + "\n");
                        e.printStackTrace();
                    }
                } else {
                    // Delete non-Java files
                    if (file.delete()) {
                        System.out.println("Deleted non-Java file: " + file.getName());
                    } else {
                        System.out.println("Failed to delete non-Java file: " + file.getName());
                    }
                }
            }
        }
    }

    private void deleteDirectoryIfEmpty(File directory) {
        if (directory.isDirectory() && directory.list().length == 0) {
            if (directory.delete()) {
                System.out.println("Deleted empty directory: " + directory.getAbsolutePath());
            } else {
                System.out.println("Failed to delete empty directory: " + directory.getAbsolutePath());
            }
        }
    }
}