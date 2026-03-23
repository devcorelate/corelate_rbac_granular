package com.corelate.rbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RbacServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacServiceApplication.class, args);
    }
}
