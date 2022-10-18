package com.sudhir.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sudhir.entity.StateEntity;

public interface StateRepository extends JpaRepository<StateEntity, Integer>{
	public List<StateEntity> findByCountryId(Integer countryId);
}
