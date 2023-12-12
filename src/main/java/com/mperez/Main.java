package com.mperez;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class Main {

  public static void main(final String[] args) {
    SpringApplication.run(Main.class, args);
  }
}
