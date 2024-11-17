package s_jamz;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class FileExtractor {
    public void extractZip(File zipFile, String destFolder) throws IOException {
        String submissionsFolder = System.getProperty("user.dir") + "/src/main/resources/ZippedSubmissions/";
        String studentFoldersDir = System.getProperty("user.dir") + "/src/main/resources/StudentFolders/";

        createDirectoryIfNotExists(submissionsFolder);
        createDirectoryIfNotExists(studentFoldersDir);

        extractMainZipFile(zipFile, submissionsFolder);

        File[] submissionFiles = getSubmissionFiles(submissionsFolder);
        if (submissionFiles == null) {
            System.out.println("Submissions directory is empty or not accessible.");
            return;
        }

        extractStudentSubmissions(submissionFiles, studentFoldersDir);

        deleteDirectoryIfEmpty(new File(submissionsFolder));
    }

    private void createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private void extractMainZipFile(File zipFile, String submissionsFolder) throws IOException {
        try (ZipFile zip = new ZipFile(zipFile)) {
            zip.extractAll(submissionsFolder);
            System.out.println("Main zip file " + zipFile.getName() + " unzipped successfully to " + submissionsFolder + "\n");
        } catch (ZipException e) {
            System.out.println("Error unzipping main file " + zipFile.getName() + "\n");
            e.printStackTrace();
        }
    }

    private File[] getSubmissionFiles(String submissionsFolder) {
        File submissionsDir = new File(submissionsFolder);
        File[] submissionFiles = submissionsDir.listFiles();
        if (submissionFiles != null) {
            System.out.println("Files in Submissions directory:");
            for (File file : submissionFiles) {
                System.out.println(file.getName());
            }
        }
        return submissionFiles;
    }

    private void extractStudentSubmissions(File[] submissionFiles, String studentFoldersDir)throws IOException {
        for (File submission : submissionFiles) {
            if (submission.isFile() && submission.getName().endsWith(".zip")) {
                String studentFolder = studentFoldersDir + "/" + submission.getName().replace(".zip", "");
                File studentFolderFile = new File(studentFolder);
                if (studentFolderFile.exists()) {
                    System.out.println("Student folder already exists: " + studentFolder);
                    continue;
                }

                extractStudentZipFile(submission, studentFolder);
            }
        }
    }

    private void extractStudentZipFile(File studentSubmission, String studentFolder) throws IOException {
        try (ZipFile studentZip = new ZipFile(studentSubmission)) {
            studentZip.extractAll(studentFolder);
            System.out.println("Student zip file " + studentSubmission.getName() + " unzipped successfully to " + studentFolder + "\n");
        } catch (ZipException e) {
            System.out.println("Error unzipping student file " + studentSubmission.getName() + "\n");
            e.printStackTrace();
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