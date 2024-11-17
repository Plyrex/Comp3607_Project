package s_jamz;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import s_jamz.CompositePattern.TestResultComponent;
import s_jamz.CompositePattern.TestResultLeaf;

public class PDFGenerator {
    StringBuilder feedbackBuilder;
    int score;

    private String getInfo(HashMap<String, TestResultLeaf> behaviour, HashMap<String, TestResultLeaf> signature, HashMap<String, TestResultLeaf> attribute, String word) {
        feedbackBuilder = new StringBuilder();
        behaviour.forEach((testName, result) -> {
            if (testName.equals(word)) {
                feedbackBuilder.append(result.getFeedback()).append("\n");
            }
        });
        signature.forEach((testName, result) -> {
            if (testName.equals(word)) {
                feedbackBuilder.append(result.getFeedback()).append("\n");
            }
        });
        attribute.forEach((testName, result) -> {
            if (testName.equals(word)) {
                feedbackBuilder.append(result.getFeedback()).append("\n");
            }
        });
        return feedbackBuilder.toString();
    }

    private int getScore(HashMap<String, TestResultLeaf> behaviour, HashMap<String, TestResultLeaf> signature, HashMap<String, TestResultLeaf> attribute, String word) {
        score = 0;
        behaviour.forEach((testName, result) -> {
            if (testName.equals(word)) {
                score += result.getScore();
            }
        });
        signature.forEach((testName, result) -> {
            if (testName.equals(word)) {
                score += result.getScore();
            }
        });
        attribute.forEach((testName, result) -> {
            if (testName.equals(word)) {
                score += result.getScore();
            }
        });
        return score;
    }

    private Table createTable(HashMap<String, TestResultLeaf> behaviour, HashMap<String, TestResultLeaf> signature, HashMap<String, TestResultLeaf> attribute, String name) {
        float[] width = {500f};
        Table infoTable = new Table(width);
        infoTable.addCell(new Cell().add(new Paragraph(name))
            .setUnderline()
            .setTextAlignment(TextAlignment.LEFT)
            .setVerticalAlignment(VerticalAlignment.BOTTOM)
            .setBorder(Border.NO_BORDER));

        infoTable.addCell(new Cell().add(new Paragraph(getInfo(behaviour, signature, attribute, name)))
            .setTextAlignment(TextAlignment.LEFT)
            .setVerticalAlignment(VerticalAlignment.BOTTOM)
            .setBorder(Border.NO_BORDER));

        return infoTable;
    }

    public void generatePDF(File zip, HashMap<String, TestResultLeaf> behaviour, HashMap<String, TestResultLeaf> signature, HashMap<String, TestResultLeaf> attribute) throws FileNotFoundException {
        PdfWriter pdf = new PdfWriter(zip.getAbsolutePath() + "/" + zip.getName() + "_results.pdf");

        String[] arrStudent = zip.getName().split("_");
        String info = arrStudent[0] + " " + arrStudent[1] + "\n" + arrStudent[2];

        Paragraph para = new Paragraph(info).setTextAlignment(TextAlignment.CENTER);

        PdfDocument pdfDocument = new PdfDocument(pdf);
        pdfDocument.addNewPage();

        float[] scoreColumnWidth = {250f, 250f};
        Table scoreTable = new Table(scoreColumnWidth);

        int total = getScore(behaviour, signature, attribute, "ChatBotGenerator") +
                    getScore(behaviour, signature, attribute, "ChatBot") +
                    getScore(behaviour, signature, attribute, "ChatBotPlatform") +
                    getScore(behaviour, signature, attribute, "ChatBotSimulation");

        String[] headers = {"Criteria", "Mark"};
        String[][] items = {
            {"Chatbot Generator", String.valueOf(getScore(behaviour, signature, attribute, "ChatBotGenerator"))},
            {"Chatbot", String.valueOf(getScore(behaviour, signature, attribute, "ChatBot"))},
            {"ChatbotPlatform", String.valueOf(getScore(behaviour, signature, attribute, "ChatBotPlatform"))},
            {"ChatbotSimulation", String.valueOf(getScore(behaviour, signature, attribute, "ChatBotSimulation"))},
            {"Bonuses", String.valueOf(behaviour.getOrDefault("Compilation Bonus", new TestResultLeaf(0, "")).getScore())},
            {"TOTAL", String.valueOf(total + behaviour.getOrDefault("Compilation Bonus", new TestResultLeaf(0, "")).getScore())}
        };

        for (int i = 0; i < 2; i++) {
            scoreTable.addCell(new Cell().add(new Paragraph(headers[i]))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE));
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 2; j++) {
                scoreTable.addCell(new Cell().add(new Paragraph(items[i][j]))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE));
            }
        }

        Table chatBotGenTable = createTable(behaviour, signature, attribute, "ChatBotGenerator");
        Table chatBotTable = createTable(behaviour, signature, attribute, "ChatBot");
        Table chatBotPlatTable = createTable(behaviour, signature, attribute, "ChatBotPlatform");
        Table chatBotSimTable = createTable(behaviour, signature, attribute, "ChatBotSimulation");

        Document document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.LEGAL);
        document.add(para);
        document.add(scoreTable);

        document.add(new Paragraph("\n"));
        document.add(chatBotGenTable);
        document.add(chatBotTable);
        document.add(chatBotPlatTable);
        document.add(chatBotSimTable);

        document.close();
    }

    public void generatePDF(TestResultComponent results, Student student) {
        throw new UnsupportedOperationException("Unimplemented method 'generatePDF'");
    }
}