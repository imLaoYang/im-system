package com.yang.im.service;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.yang.im.service.*.mapper")
public class ServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServiceApplication.class,args);
  }
}
