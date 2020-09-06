package com.batchExample.demoExample.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User {
	public User() {}
	@Id
	private Integer id;
	private String name;
	private String dept;
	private Integer salary;
}
