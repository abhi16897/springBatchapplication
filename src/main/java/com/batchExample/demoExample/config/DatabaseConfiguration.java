package com.batchExample.demoExample.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DatabaseConfiguration {
	@Bean
	@Primary
	public DataSource primaryDataSource() {
		  DriverManagerDataSource dataSource = new DriverManagerDataSource();
		  dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		  dataSource.setUrl("jdbc:mysql://localhost/batchingdatabse");
		  dataSource.setUsername("root");
		  dataSource.setPassword("password-1");
		  
		  return dataSource;
	}	
	@Bean("secondDataSource")
	public DataSource dataSource() {
		  DriverManagerDataSource dataSource = new DriverManagerDataSource();
		  dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		  dataSource.setUrl("jdbc:mysql://localhost/springbatch");
		  dataSource.setUsername("root");
		  dataSource.setPassword("password-1");
		  
		  return dataSource;
		 }
}
