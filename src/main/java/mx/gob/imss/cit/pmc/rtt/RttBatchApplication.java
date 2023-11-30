package mx.gob.imss.cit.pmc.rtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
public class RttBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(RttBatchApplication.class, args);
    }

}
