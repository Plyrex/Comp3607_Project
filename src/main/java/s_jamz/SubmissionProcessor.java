package s_jamz;

import java.io.File;
import java.util.List;

/*
 * Class requires reworking to implement the JavaSubmissionProcessor interface
 */

public abstract class SubmissionProcessor {

    void processSubmission(File zipFile, String destDir, Student student){}

    void extractZip(File zipFile, File destDir){}

    void evaluateCode(List<File> destDir){}

    void generateFeedback(List<TestResultComponent> testResults){}

    void calculateScore(List<TestResultComponent> testResults){}
}