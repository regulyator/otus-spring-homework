package ru.otus.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryAppAuthorization {
    //как уже упоминали проблема на маках с арм процессорами
    /*static {
        System.setProperty("os.arch", "i686_64");
    }*/

    public static void main(String[] args) {
        SpringApplication.run(LibraryAppAuthorization.class, args);
    }
}
