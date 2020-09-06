package com.batchExample.demoExample;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;

import com.batchExample.demoExample.Model.User;

public class EmployeeFieldMapper implements FieldSetMapper<User> {

	@Override
	public User mapFieldSet(FieldSet fieldSet) throws BindException {
		// TODO Auto-generated method stub
		User user=new User();
		user.setId(fieldSet.readInt("id"));
		user.setName(fieldSet.readString("name"));
		user.setDept(fieldSet.readString("dept"));
		user.setSalary(fieldSet.readInt("salary"));
		return user;
	}

}
