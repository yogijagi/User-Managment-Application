package com.sudhir.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "USER_ACCOUNT")
public class UserAccountEntity {
	@Id
	@GeneratedValue
    @Column(name = "user_id")
	private Integer userId;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "email")
	private String email;
	@Column(name = "phno")
	private String phno;
	@Column(name = "dob")
	private Date dob;
	@Column(name = "gender")
	private String gender;
	@Column(name = "country_id")
	private Integer countryId;
	@Column(name = "state_id")
	private Integer stateId;
	@Column(name = "city_id")
	private Integer cityId;
	@Column(name = "password")
	private String password;
	@Column(name = "status")
	private String status;
	@Column(name = "created_date")
	@CreationTimestamp
	private Date createdDate;
	@Column(name = "updated_date")
	@UpdateTimestamp
	private Date updatedDate;
	
}
