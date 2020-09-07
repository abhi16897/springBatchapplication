package com.batchExample.demoExample.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.batchExample.demoExample.Model.User;

public class UserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		User user=new User();
		user.setId(rs.getInt("id"));
		user.setDept(rs.getString("dept"));
		user.setName(rs.getString("name"));
		user.setSalary(rs.getInt("salary"));
		return user;
	}

}
