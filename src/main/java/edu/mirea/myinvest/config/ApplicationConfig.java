package edu.mirea.myinvest.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.text.Transliterator;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import static edu.mirea.myinvest.service.system.ConfigService.CONFIGURATIONS_CACHE_NAME;

/**
 * Класс общей конфигурации
 */
@Configuration
@EnableCaching
@RequiredArgsConstructor
public class ApplicationConfig {

    private final CloudStorageConfig cloudStorageConfig;


    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModules(
                        new ConstraintViolationProblemModule(),
                        new ProblemModule())
                .findAndRegisterModules();
    }

    @Bean
    public Transliterator transliterator() {
        return Transliterator.getInstance("Russian-Latin/BGN");
    }

    @Bean
    public AmazonS3 getClient() {
        AWSCredentials credentials = new BasicAWSCredentials(
                cloudStorageConfig.getAccessKey(),
                cloudStorageConfig.getSecretKey()
        );

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(
                                cloudStorageConfig.getEndpointUrl(),
                                cloudStorageConfig.getRegion()
                        )
                )
                .build();
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CONFIGURATIONS_CACHE_NAME);
    }
}
