import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class MainTest {

    @Disabled("mainTest is temporary disabled")
    @Test
    @Timeout(value = 22)
    void mainTest() throws Exception {
        Main.main(null);
    }
}