package s_jamz.StrategyPattern;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import s_jamz.CompositePattern.TestResultComponent;
import s_jamz.CompositePattern.TestResultComposite;
import s_jamz.CompositePattern.TestResultLeaf;
import s_jamz.JUnitTestExecutor;

public class NamingConvention implements EvaluationStrategy {

    private TestResultComponent results;
    private String studentFolderPath;

    public NamingConvention(String studentFolderPath) {
        this.studentFolderPath = studentFolderPath;
    }

    @Override
    public void evaluate(File javaFile) {
        // Implement evaluation logic if needed
    }

    @Override
    public void runTests(File javaFile) {
        try {
            // Dynamically add the /target/test-classes directory to the classpath
            File testDir = new File(System.getProperty("user.dir") + "/target/test-classes");
            URL testURL = testDir.toURI().toURL();
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{testURL}, this.getClass().getClassLoader());
            Class<?> testClass = Class.forName("s_jamz.AutoGrader.NamingConventionsTest", true, urlClassLoader);

            // Print the class name
            System.out.println("Running tests for class: " + testClass.getName());

            // Run JUnit tests for NamingConvention
            results = runNamingConventionTests(testClass);
        } catch (Exception e) {
            e.printStackTrace();
            results = new TestResultLeaf(0, "Failed to load test class: " + e.getMessage());
        }
    }

    @Override
    public TestResultComponent getResults() {
        return results;
    }

    private TestResultComponent runNamingConventionTests(Class<?> testClass) {
        TestResultComposite composite = new TestResultComposite();
        try {
            // Execute JUnit tests for naming conventions
            JUnitTestExecutor.executeTests(testClass);
            composite.add(new TestResultLeaf(90, "Naming convention test passed")); // Placeholder result
        } catch (Exception e) {
            e.printStackTrace();
            composite.add(new TestResultLeaf(0, "Test execution failed: " + e.getMessage()));
        }
        return composite;
    }
}