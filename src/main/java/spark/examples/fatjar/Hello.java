package spark.examples.fatjar;

import lombok.Data;

@Data
public class Hello {
    public String message() {
        return "hello injection";
    }
}
