package s_jamz;

import java.io.File;
import java.util.List;

public class JavaSubmissionProcessor extends SubmissionProcessor {
    public FileExtractor fileExtractor;
    public List<EvaluationStrategy> Strategies;
    public PDFGenerator pdfGenerator;

    @Override
    public void processSubmission(File zipFile, String destDir, Student student) {
        // TODO method stub
        throw new UnsupportedOperationException("Unimplemented method 'processSubmission'");
        
        // fileExtractor.extractZip(zipFile, destDir);
        // TestResultComponent testResults = evaluateCode(zipFile);
        // generateFeedback(testResults);
        // calculateScore(testResults);
    }
}
