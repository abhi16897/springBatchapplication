package com.batchExample.demoExample.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batchExample.demoExample.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
