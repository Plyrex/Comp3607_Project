package s_jamz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NamingConventionTest {

    @Test
    public void testChatBotNamingConvention() {
        // Example test for naming convention
        String className = "ChatBot";
        assertTrue(className.matches("^[A-Z][a-zA-Z0-9]*$"), "Class name should follow naming conventions");
    }

    // Add more tests as needed
}