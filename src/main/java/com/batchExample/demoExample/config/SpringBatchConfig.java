package com.batchExample.demoExample.config;



import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.batchExample.demoExample.EmployeeFieldMapper;
import com.batchExample.demoExample.Model.User;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
	
	@Bean("job1")
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,@Qualifier("job1Reader") ItemReader<User> itemReader,ItemProcessor<User, User> itemProcessor,@Qualifier("job1Writer") ItemWriter<User> itemWriter) {
		Step step=stepBuilderFactory.get("Step").<User,User>chunk(100)
									.reader(itemReader)
									.processor(itemProcessor)
									.writer(itemWriter)
									.build();
		return jobBuilderFactory.get("User-Detail").incrementer(new RunIdIncrementer())
						 .start(step)
						 .build();
	}
	@Bean("job1Reader")
	public FlatFileItemReader<User> itemReader(){
		FlatFileItemReader<User> reader=new FlatFileItemReader<User>();
		reader.setResource(new FileSystemResource("src/main/resources/users.csv"));
		reader.setName("csv-Reader");
		reader.setLinesToSkip(1);
		reader.setLineMapper(lineMapper());
		return reader;
	}
	
	@Bean
	public LineMapper<User> lineMapper(){
		DefaultLineMapper<User> defaultLineMapper=new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer= new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] {"id","name","dept","salary"});
		//defaultLineMapper.setFieldSetMapper(new EmployeeFieldMapper());
		BeanWrapperFieldSetMapper<User> feildMapper=new BeanWrapperFieldSetMapper<>();
		feildMapper.setTargetType(User.class);
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(feildMapper);
		return defaultLineMapper;
				
	}
}
