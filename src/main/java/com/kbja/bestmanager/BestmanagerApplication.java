package com.kbja.bestmanager;

import java.io.FileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ResourceUtils;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class BestmanagerApplication{

  public static void main(String[] args) {
    SpringApplication.run(BestmanagerApplication.class, args);
  }
}
