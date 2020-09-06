package com.batchExample.demoExample.processor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.batchExample.demoExample.Model.User;

@Component
public class Processor implements ItemProcessor<User,User>{
	 

	private static final Map<String,String> DEPT_NAMES=new HashMap<>();
	public Processor() {
		DEPT_NAMES.put("001", "CDE");
		DEPT_NAMES.put("002", "CDB");
		DEPT_NAMES.put("003", "COC");
		
	}
	@Override
	public User process(User user) throws Exception {
		// TODO Auto-generated method stub
		String deptCode=user.getDept();
		String dept=DEPT_NAMES.get(deptCode);
		user.setDept(dept);
		
		return user;
	}

}
