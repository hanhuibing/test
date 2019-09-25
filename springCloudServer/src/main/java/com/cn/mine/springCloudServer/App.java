package com.cn.mine.springCloudServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 *  spring cloud config server demo
 *
 */
@SpringBootApplication
@EnableConfigServer
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);

        /***
         * 测试地址:
         *    http://localhost:8888/config/test-dev.properties
         *    http://localhost:8888/test/dev
         */
    }
}
