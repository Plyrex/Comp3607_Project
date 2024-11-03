package s_jamz;

import java.io.File;
import java.io.IOException;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class FileExtractor {
    public void extractZip(File zipFile) throws IOException {
        String destFolder = System.getProperty("user.dir") + "\\src\\main\\java\\resources\\Submissions";
        String newDestFolder = System.getProperty("user.dir") + "\\src\\main\\java\\resources\\StudentFolders";
        
        // Create Submissions directory if it doesn't exist
        File submissionsDir = new File(destFolder);
        if (!submissionsDir.exists()) {
            submissionsDir.mkdirs();
        }

        // Create StudentFolders directory if it doesn't exist
        File studentFoldersDir = new File(newDestFolder);
        if (!studentFoldersDir.exists()) {
            studentFoldersDir.mkdirs();
        }

        // Extract the main zip file
        try {
            ZipFile zip = new ZipFile(zipFile);
            zip.extractAll(destFolder);
            System.out.println("Main zip file " + zipFile.getName() + " unzipped successfully\n");
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

        // Check if the extracted content is a directory named "Submissions"
        File nestedSubmissionsDir = new File(destFolder + "\\Submissions");
        if (nestedSubmissionsDir.exists() && nestedSubmissionsDir.isDirectory()) {
            submissionFiles = nestedSubmissionsDir.listFiles();
        }

        // Extract individual student submissions
        if (submissionFiles != null) {
            for (File submission : submissionFiles) {
                if (submission.isFile() && submission.getName().endsWith(".zip")) {
                    try {
                        ZipFile studentZip = new ZipFile(submission);
                        String studentFolder = newDestFolder + "\\" + submission.getName().replace(".zip", "");
                        studentZip.extractAll(studentFolder);
                        System.out.println("Student zip file " + submission.getName() + " unzipped successfully\n");
                    } catch (ZipException e) {
                        System.out.println("Error unzipping student file " + submission.getName() + "\n");
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}