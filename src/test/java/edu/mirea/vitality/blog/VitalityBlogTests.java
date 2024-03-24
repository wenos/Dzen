package edu.mirea.vitality.blog;

import edu.mirea.vitality.blog.config.PostgresTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = {PostgresTestConfig.Initializer.class})
class VitalityBlogTests {

    @Test
    void contextLoads() {
    }

}
