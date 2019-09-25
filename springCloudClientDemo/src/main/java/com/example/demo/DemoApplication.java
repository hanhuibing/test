package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * spring cloud config client demo
 * */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class DemoApplication {

    @Value("${name}")
    String name ="world";

    @Value("${pwd}")
    String pwd = "pwd";

    @RequestMapping("/")
    public String getName(){
        return name + "---" + pwd;
    }
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        //测试页面  http://localhost:8999/
    }

}
