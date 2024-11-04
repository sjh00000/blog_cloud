package com.example.searchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableElasticsearchRepositories(basePackages = "com.example.searchservice")
public class SearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchServiceApplication.class, args);
    }

}
