package s_jamz;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

public class PDFGenerator {
    String dest= System.getProperty("user.dir") + "/src/main/test.pdf";

    public void generatePDF() throws FileNotFoundException{
        PdfWriter pdf= new PdfWriter(dest);

        int[] scores= {6, 36, 20, 12, 5, 10, 0, 5};
        String bonus= String.valueOf(scores[4])+ ", "+ String.valueOf(scores[5])+ ", "+ String.valueOf(scores[6])+ ", "+ String.valueOf(scores[7]);
        String total= String.valueOf(Arrays.stream(scores).sum());

        String info= ("John Doe \n 816012345 \n"+ String.valueOf(total)+"%");
        

        PdfDocument pdfDocument= new PdfDocument(pdf);
        pdfDocument.addNewPage();

        Paragraph para= new Paragraph(info);
        // float infoColumnWidth[]= {200f};
        // Table infoTable= new Table(infoColumnWidth);

        float scoreColumnWidth[]= {200f, 80f, 200f};
        Table scoreTable= new Table(scoreColumnWidth);

        

        String[] headers= {"Criteria", "Mark", "Comment"};
        String[][] items= {
            {"Chatbot Generator", String.valueOf(scores[0]), ""},
            {"Chatbot", String.valueOf(scores[1]), "*Put comment here*"},
            {"ChatbotPlatform", String.valueOf(scores[2]), "*Put comment here*"},
            {"ChatbotSimulation", String.valueOf(scores[3]), "*Put comment here*"},
            {"Bonuses", bonus, "*Put comment here*"},
            {"TOTAL", total, "*Put comment here*"},
        };

        for(int i=0; i<3; i++){
            scoreTable.addCell(new Cell().add(headers[i]));
        }

        for(int i=0; i<6; i++){
            for(int j=0; j<3; j++){
            scoreTable.addCell(new Cell().add(items[i][j]));
            }
        }


        Document document= new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        document.setTextAlignment(TextAlignment.CENTER);
        document.add(para);
        document.add(scoreTable);

        document.close();
    }

    public void generatePDF(TestResultComponent results, Student student){
        throw new UnsupportedOperationException("Unimplemented method 'generatePDF'");
    }
}

