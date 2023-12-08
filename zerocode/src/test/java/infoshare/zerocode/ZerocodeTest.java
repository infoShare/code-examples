package infoshare.zerocode;

import org.jsmart.zerocode.core.domain.Scenario;
import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(ZeroCodeUnitRunner.class)
public class ZerocodeTest {

    @Test
    @Scenario("tests/Zerocode.yml")
    public void zerocode() {
        // This space remains empty
    }
}
