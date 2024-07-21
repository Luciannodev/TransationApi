package br.com.ludevsp;


import br.com.ludevsp.dataprovider.DataSeeder;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan("br.com.ludevsp.domain.entities")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public ApplicationRunner initializer(DataSeeder dataSeeder) {
        return args -> {
            dataSeeder.seedDataCategory();
            dataSeeder.seedMockAccount();
            dataSeeder.seedMerchantDataMock();
        };
    }
}
