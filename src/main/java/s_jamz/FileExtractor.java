package s_jamz;

import net.lingala.zip4j.exception.ZipException;
import java.io.File;
import java.io.IOException;
import net.lingala.zip4j.ZipFile;


public class FileExtractor {
    public void extractZip(File zipFile) throws IOException {
        String destFolder = System.getProperty("user.dir") + "\\src\\main\\java\\s_jamz\\resources\\Submissions";
        try {
            ZipFile zip = new ZipFile(zipFile);
            zip.extractAll(destFolder);          
            System.out.println("File unzipped successfully\n");
        } 
        catch (ZipException e) {
            System.out.println("Error unzipping file\n");
            e.printStackTrace();
        }
    }
}
