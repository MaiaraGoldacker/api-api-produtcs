package com.api.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper ModelMapper() {
		var modelMapper = new ModelMapper();
		
		return modelMapper;
	}
}
