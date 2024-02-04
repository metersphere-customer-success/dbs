package metersphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ServletComponentScan
public class DatabaseServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DatabaseServerApplication.class,args);
    }
}

