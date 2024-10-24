package com.fiap.techchallenge4.ms_shipping.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
