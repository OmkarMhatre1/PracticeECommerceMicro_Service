package com.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ConfigClass {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClient(){
        return WebClient.builder();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

//    @Autowired
//    private LoadBalancedExchangeFilterFunction filterFunction;
//
//    @Bean
//    public WebClient inventoryWebClient() {
//        return WebClient.builder()
//                .baseUrl("http://INVENTORY-SERVICE")
//                .filter(filterFunction)
//                .build();
//    }
//
//    @Bean
//    public InventoryClient inventoryClient() {
//        HttpServiceProxyFactory httpServiceProxyFactory
//                = HttpServiceProxyFactory
//                .builder(WebClientAdapter.forClient(inventoryWebClient()))
//                .build();
//        return httpServiceProxyFactory.createClient(InventoryClient.class);
//    }

}
