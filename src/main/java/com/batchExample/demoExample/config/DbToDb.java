package com.batchExample.demoExample.config;



import java.sql.ResultSet;
import java.sql.SQLException;

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
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcAccessor;

import com.batchExample.demoExample.Model.User;
import com.batchExample.demoExample.mappers.UserRowMapper;
import com.batchExample.demoExample.write.DBWriter;

@Configuration
@EnableBatchProcessing
public class DbToDb {
	@Autowired
	@Qualifier("secondDataSource")
	DataSource dataSource1;
	
	@Autowired
	DataSource dataSource;
	@Bean("job3")
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,@Qualifier("job3Reader") ItemReader<User> itemReader,@Qualifier("job3Writer") ItemWriter<User> itemWriter) {
		Step step=stepBuilderFactory.get("Step").<User,User>chunk(100)
				.reader(itemReader)
				.writer(itemWriter)
				.build();
		return jobBuilderFactory.get("User-Detail123").incrementer(new RunIdIncrementer())
				 .start(step)
				 .build();
	}
	
	@Bean("job3Reader")
	public JdbcCursorItemReader<User> itemReader(){
		JdbcCursorItemReader<User> reader=new JdbcCursorItemReader<User>();
		reader.setDataSource(dataSource);
		 reader.setSql("SELECT id,name from user");
		 reader.setRowMapper(new UserRowMapper());
		return reader;
	}
	public class UserRowMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			return user;
		}
	}
	@Bean("job3Writer")
	public JdbcBatchItemWriter<User> itemWriter(){
		JdbcBatchItemWriter<User> writer=new JdbcBatchItemWriter<User>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<User>());
		writer.setSql("INSERT INTO user (id,name) VALUES (:id, :name)");
		writer.setDataSource(dataSource1);
		writer.afterPropertiesSet();
		return writer;
	}

}
