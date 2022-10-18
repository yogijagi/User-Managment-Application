package com.sudhir.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sudhir.entity.UserAccountEntity;

public interface UserAccountRepository  extends JpaRepository<UserAccountEntity, String>{
	
	// select * from USER_ACCOUNT where email=?
	public UserAccountEntity findByEmail(String email);
	
	// select * from USER_ACCOUNT where email=? and password=?
	public UserAccountEntity findByEmailAndPassword(String email,String password);
}
