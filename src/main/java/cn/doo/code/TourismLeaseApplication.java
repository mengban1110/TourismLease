package cn.doo.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TourismLeaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(TourismLeaseApplication.class, args);
    }

}
