package chord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackageClasses = {"chord", event})
@ComponentScan(basePackages = {"UI", "chord"})
public class ChordDHTApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChordDHTApplication.class, args);
    }

}
