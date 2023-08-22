package hr.algebra.javawebprojekt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JavaWebProjektApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaWebProjektApplication.class, args);
    }

}
