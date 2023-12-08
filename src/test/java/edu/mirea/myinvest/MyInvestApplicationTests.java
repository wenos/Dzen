package edu.mirea.myinvest;

import edu.mirea.myinvest.config.PostgresTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = {PostgresTestConfig.Initializer.class})
class MyInvestApplicationTests {

    @Test
    void contextLoads() {
    }

}
