package edu.mirea.vitality.blog.config;

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

import static edu.mirea.vitality.blog.service.system.ConfigService.CONFIGURATIONS_CACHE_NAME;

/**
 * @file ApplicationConfig.java
 * @brief Этот файл содержит класс ApplicationConfig.
 */

/**
 * @class ApplicationConfig
 * @brief Класс для общей конфигурации приложения.
 */
@Configuration
@EnableCaching
@RequiredArgsConstructor
public class ApplicationConfig {

    private final CloudStorageConfig cloudStorageConfig;

    /**
     * Создает и возвращает объект ObjectMapper для преобразования объектов JSON.
     * @return Объект ObjectMapper.
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModules(
                        new ConstraintViolationProblemModule(),
                        new ProblemModule())
                .findAndRegisterModules();
    }

    /**
     * Создает и возвращает объект Transliterator для транслитерации текста.
     * @return Объект Transliterator.
     */
    @Bean
    public Transliterator transliterator() {
        return Transliterator.getInstance("Russian-Latin/BGN");
    }

    /**
     * Создает и возвращает клиент Amazon S3 для работы с облачным хранилищем.
     * @return Клиент Amazon S3.
     */
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

    /**
     * Создает и возвращает менеджер кэша для кэширования данных.
     * @return Менеджер кэша.
     */
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CONFIGURATIONS_CACHE_NAME);
    }
}
