package s_jamz;

import java.io.File;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the directory path of the zip file");
        String zipFilePath = scanner.nextLine();
        scanner.close();


        FileExtractor fileExtractor = new FileExtractor();
        File zipFile = new File(zipFilePath);
        try{
            fileExtractor.extractZip(zipFile);
        }
        catch(Exception e){
            e.printStackTrace();   
        }
    }
}
