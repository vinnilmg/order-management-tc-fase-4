package com.fiap.techchallenge4.ms_shipping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients

public class MsShippingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsShippingApplication.class, args);
	}

}
