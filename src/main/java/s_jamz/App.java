package s_jamz;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        System.out.println("This is a test");
        System.out.println("Please input the directory path of the zip file");
        
        //read in input from user for zip file path
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        // String zipFilePath = scanner.nextLine();
        String zipFilePath = "D:\\UWI\\Year 3\\Sem 1\\Comp3607\\Practice\\Project practice stuff\\compressed.zip";

        //read in input from user for directory path to extract the zip file to
        System.out.println("Please input the directory path to extract the zip file to");
        // String destDir = scanner.nextLine(); //need to get a better way to input the directory path
        String destDir = "Desktop";

        //unzip the file
        Zipped.unzip(zipFilePath, destDir);

        scanner.close();
    }
}
