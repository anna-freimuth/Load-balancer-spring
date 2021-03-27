package anna.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StressClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(StressClientApplication.class, args).close();
    }

}
