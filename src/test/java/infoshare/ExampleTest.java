package infoshare;

import org.jsmart.zerocode.core.domain.Scenario;
import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(ZeroCodeUnitRunner.class)
public class ExampleTest {

    @Test
    @Scenario("tests/Example.yml")
    public void example() {
        // This space remains empty
    }
}
