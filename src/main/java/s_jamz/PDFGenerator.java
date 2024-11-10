package s_jamz;

import java.io.*;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import s_jamz.CompositePattern.TestResultComponent;


public class PDFGenerator {
    String Dest= System.getProperty("user.dir") + "\\src\\main\\java\\s_jamz\\resources\\test.pdf";

    public void generatePDF() throws FileNotFoundException{
        PdfWriter pdf= new PdfWriter(Dest);

        PdfDocument pdfDocument= new PdfDocument(pdf);
        pdfDocument.addNewPage();

        Document document= new Document(pdfDocument);
        document.close();
    }

    public void generatePDF(TestResultComponent results, Student student){
        throw new UnsupportedOperationException("Unimplemented method 'generatePDF'");
    }
}

