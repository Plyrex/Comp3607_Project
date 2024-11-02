package s_jamz;

import net.lingala.zip4j.exception.ZipException;
import java.io.File;
import java.io.IOException;
import net.lingala.zip4j.ZipFile;


public class FileExtractor {
    String destFolder = System.getProperty("user.dir") + "\\src\\main\\resources\\Submissions";
    String newDestFolder = System.getProperty("user.dir") + "\\src\\main\\resources\\StudentFolders";
    public void extractZip(File zipFile) throws IOException {
        
        // String test = System.getProperty("user.dir");
        // System.out.println(test);
        

        // extract the main zip file
        try {
            ZipFile zip = new ZipFile(zipFile);
            zip.extractAll(destFolder);          
            System.out.println("Main zip file unzipped successfully\n");
        } 
        catch (ZipException e) {
            System.out.println("Error unzipping file\n");
            e.printStackTrace();
        }

    //     //iteratively extract all the zipFiles in the extracted folder and add student individual Folders to the extracted folder
        extractZipFiles();
    }

    public void extractZipFiles() throws IOException {
        File zipFileFile = new File(newDestFolder);
        if (!zipFileFile.exists()) {
            zipFileFile.mkdirs();
            System.out.println("Student Folders created successfully\n");
        }
     
        try{
            File zipFile = new File(destFolder);
            System.out.println("Extracting student zipFile from " + zipFile.getName() + "folder...\n");
            for (File file : zipFile.listFiles()) {
                try{
                    ZipFile zip = new ZipFile(file);
                    zip.extractAll(newDestFolder + "\\" + file.getName().substring(0, file.getName().length() - 4));
                    System.out.println("Student zipFile: " + file.getName() + " unzipped successfully\n");
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
