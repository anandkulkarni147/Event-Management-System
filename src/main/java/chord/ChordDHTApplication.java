package chord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"chord", "event", "email", "kafka"})
public class ChordDHTApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChordDHTApplication.class, args);
    }

}
