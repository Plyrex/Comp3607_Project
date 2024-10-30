package s_jamz;

import java.io.File;
import java.util.List;

public class JavaSubmissionProcessor extends SubmissionProcessor {
    public FileExtractor fileExrtactor;
    public List<EvaluationStrategy> Strategies;
    public PDFGenerator pdfGenerator;

    @Override
    public void processSubmission(File zipFile, File destDir, Student student) {
        // TODO method stub
        throw new UnsupportedOperationException("Unimplemented method 'processSubmission'");
        
        Student student = new Student();
        fileExtractor.extractZip(zipFile, destDir);
        TestResultComponent testResults = evaluateCode(destDir);
        generateFeedback(testResults);
        calculateScore(testResults);
    }
}
