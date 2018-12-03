package com.cn.mine.hbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@ImportResource(locations = {"classpath*:spring-*.xml"})
public class App 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class);
		System.out.println("启动成功");
    }
}
