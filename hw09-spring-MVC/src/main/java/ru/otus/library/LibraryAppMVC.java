package ru.otus.library;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableMongock
@EnableMongoRepositories
public class LibraryAppMVC {
    /*//как уже упоминали проблема на маках с арм процессорами
    static {
        System.setProperty("os.arch", "i686_64");
    }*/

    public static void main(String[] args) {
        SpringApplication.run(LibraryAppMVC.class, args);
    }
}
