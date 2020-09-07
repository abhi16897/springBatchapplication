package com.batchExample.demoExample.config;



import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.batchExample.demoExample.EmployeeFieldMapper;
import com.batchExample.demoExample.Model.User;
import com.batchExample.demoExample.mappers.UserRowMapper;

@Configuration
@EnableBatchProcessing
public class DatatoCsvConfig {
	@Autowired
	DataSource dataSource;
	@Bean("job2")
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,@Qualifier("job2Reader") ItemReader<User> itemReader,@Qualifier("job2Writer") ItemWriter<User> itemWriter) {
		
		Step step=stepBuilderFactory.get("Steps123").<User,User>chunk(100)
									.reader(itemReader)
									//.processor(itemProcessor)
									.writer(itemWriter)
									.build();
		return jobBuilderFactory.get("User-Detail2").incrementer(new RunIdIncrementer())
						 .start(step)
						 .build();
	}
	@Bean("job2Reader")
	public JdbcCursorItemReader<User> itemReader(){
		
		JdbcCursorItemReader<User> reader=new JdbcCursorItemReader<User>();
		reader.setDataSource(dataSource);
		 reader.setSql("SELECT * from user");
		 reader.setRowMapper(new UserRowMapper());
		return reader;
	}
	
	@Bean("job2Writer")
	public FlatFileItemWriter<User> writer(){
		FlatFileItemWriter<User> writer=new FlatFileItemWriter<User>();
		writer.setResource(new ClassPathResource("users.csv"));
		DelimitedLineAggregator<User> aggregator=new DelimitedLineAggregator<User>();
		aggregator.setDelimiter(",");
		
		BeanWrapperFieldExtractor<User> feildExtractor=new BeanWrapperFieldExtractor<User>();
		feildExtractor.setNames(new String[] {"id","name","dept","salary"});
		aggregator.setFieldExtractor(feildExtractor);
		writer.setLineAggregator(aggregator);
		return writer;
		
	}
}
