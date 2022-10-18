package com.sudhir.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CITIES_MASTER")
public class CityEntity {
	@Id
	@GeneratedValue
    @Column(name = "city_id")
	private Integer cityId;
	@Column(name = "city_name")
	private String cityName;
	@Column(name = "state_id")
	private Integer stateId;
}
