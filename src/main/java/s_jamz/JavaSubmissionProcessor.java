package s_jamz;

import java.io.File;
import java.util.List;

import s_jamz.StrategyPattern.EvaluationStrategy;

public class JavaSubmissionProcessor extends SubmissionProcessor {
    public FileExtractor fileExtractor;
    public List<EvaluationStrategy> Strategies;
    public PDFGenerator pdfGenerator;

    @Override
    public void processSubmission(File zipFile, String destDir, Student student) {
        throw new UnsupportedOperationException("Unimplemented method 'processSubmission'");
        
        // fileExtractor.extractZip(zipFile, destDir);
        // TestResultComponent testResults = evaluateCode(zipFile);
        // generateFeedback(testResults);
        // calculateScore(testResults);
    }
}
