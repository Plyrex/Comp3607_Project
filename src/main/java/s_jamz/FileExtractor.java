package s_jamz;

import net.lingala.zip4j.exception.ZipException;
import java.io.File;
import java.io.IOException;
import net.lingala.zip4j.ZipFile;


public class FileExtractor {
    public void extractZip(File zipFile) throws IOException {
        String destFolder = System.getProperty("user.dir") + "\\src\\main\\resources\\Submissions";
        String newDestFolder = System.getProperty("user.dir") + "\\src\\main\\resources\\StudentFolders";
        File tempFile = new File(destFolder);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }

        //extract the main zip file
        try {
            ZipFile zip = new ZipFile(zipFile);
            zip.extractAll(destFolder);          
            System.out.println("Main zip file unzipped successfully\n");
        } 
        catch (ZipException e) {
            System.out.println("Error unzipping file\n");
            e.printStackTrace();
        }

        //iteratively extract all the zipFiles in the extracted folder and add student individual Folders to the extracted folder
        extractZipFiles(tempFile, newDestFolder);
    }

    public void extractZipFiles(File zipFile, String destFolder) throws IOException {
    try{
        for (File file : zipFile.listFiles()) {
            try{
                ZipFile zip = new ZipFile(file);
                zip.extractAll(destFolder);
                System.out.println("Student zipFile " + file.getName() + " unzipped successfully\n");
            }
            catch (ZipException e) {
                System.out.println("Error unzipping file\n");
                e.printStackTrace();
            }
        }
    }
    catch (Exception e) {
        System.out.println("Error unzipping file\n");
        e.printStackTrace();
    }
    }

}
