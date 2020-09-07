package com.batchExample.demoExample.write;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.batchExample.demoExample.Model.User;
import com.batchExample.demoExample.repository.UserRepository;

@Service("job1Writer")
public class DBWriter implements ItemWriter<User> {
	@Autowired
	private UserRepository userRepository;
	@Override
	public void write(List<? extends User> user) throws Exception {
		// TODO Auto-generated method stub
		userRepository.saveAll(user);
	}

}
