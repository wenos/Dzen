package edu.mirea.vitality.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @file VitalityBlog.java
 * @brief Этот файл содержит класс VitalityBlog.
 */

/**
 * @class VitalityBlog
 * @brief Класс, представляющий точку входа в приложение VitalityBlog.
 */
@SpringBootApplication
public class VitalityBlog {
    /**
     * Точка входа в приложение.
     * @param args Аргументы командной строки.
     */
    public static void main(String[] args) {
        SpringApplication.run(VitalityBlog.class, args);
    }
}
