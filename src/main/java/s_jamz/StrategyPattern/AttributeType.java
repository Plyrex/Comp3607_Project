package s_jamz.StrategyPattern;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import s_jamz.JUnitTestExecutor;
import s_jamz.CompositePattern.TestResultComponent;
import s_jamz.CompositePattern.TestResultComposite;
import s_jamz.CompositePattern.TestResultLeaf;
import s_jamz.JUnitTestExecutor;
import s_jamz.AutoGrader.AttributeTypeTest;


public class AttributeType implements EvaluationStrategy {
    private TestResultComponent results;
    private String studentFolderPath;

    public AttributeType(String studentFolderPath) {
        this.studentFolderPath = studentFolderPath;
    }

//path: /Users/maianeptune/Downloads/StudentSubmissions2.zip
    @Override
    public void evaluate(File javaFile) {
         throw new UnsupportedOperationException("Unimplemented method 'evaluate'");
    }

   
    @Override
     public void runTests(File javaFile) {
        try {
              // Dynamically add the /target/test-classes directory to the classpath
              File testDir = new File(System.getProperty("user.dir") + "/target/test-classes");
              URL testURL = testDir.toURI().toURL();
              URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{testURL}, this.getClass().getClassLoader());
              Class<?> testClass = Class.forName("s_jamz.AutoGrader.AttributeTypeTest", true, urlClassLoader);

            // Print the class name
            System.out.println("Running tests for class: " + testClass.getSimpleName());

            // // Run JUnit tests for AttributeType
            results = runAttributeTypeTests(testClass);

        } catch (Exception e) {
            e.printStackTrace();
            results = new TestResultLeaf(0, "Failed to load test class: " + e.getMessage());
        }
    }

      @Override
    public TestResultComponent getResults() {
        return results;
    }

    private TestResultComponent runAttributeTypeTests(Class<?> testClass) {
        TestResultComposite composite = new TestResultComposite();
        try {
            // Execute JUnit tests for naming conventions
            JUnitTestExecutor.executeTests(testClass);
            composite.add(new TestResultLeaf(90, "Attribute type test passed")); // Placeholder result
        } catch (Exception e) {
            e.printStackTrace();
            composite.add(new TestResultLeaf(0, "Test execution failed: " + e.getMessage()));
        }
        return composite;
    }
    
}
