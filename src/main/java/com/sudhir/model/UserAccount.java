package com.sudhir.model;

import java.util.Date;

import javax.persistence.Column;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
public class UserAccount {
	private Integer userId;
	private String firstName;
	private String lastName;
	private String email;
	private String phno;
	private Date dob;
	private String gender;
	private Integer countryId;
	private Integer stateId;
	private Integer cityId;
	private String password;
	private String status;
	private Date createdDate;
	private Date updatedDate;
	
}
