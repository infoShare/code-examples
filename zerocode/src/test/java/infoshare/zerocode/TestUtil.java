package infoshare.zerocode;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestUtil {

    public int sum(Input input) {
        return input.a() + input.b();
    }

    record Input(@JsonProperty("a") int a, @JsonProperty("b") int b) {
    }
}
