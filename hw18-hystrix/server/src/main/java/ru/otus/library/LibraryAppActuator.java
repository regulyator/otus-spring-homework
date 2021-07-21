package ru.otus.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableCircuitBreaker
public class LibraryAppActuator {
    //как уже упоминали проблема на маках с арм процессорами
    static {
        System.setProperty("os.arch", "i686_64");
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryAppActuator.class, args);
    }
}
