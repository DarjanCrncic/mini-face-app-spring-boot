package com.example.minifaceapp.utils;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;


@PropertySource(value = "classpath:queries.properties")
@Configuration
@ConfigurationProperties
@Getter
@Setter
public class QueryHolder {
	private Map<String, String> queries;
}
