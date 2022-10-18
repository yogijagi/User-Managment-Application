package com.sudhir.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sudhir.entity.CityEntity;

public interface CityRepository extends JpaRepository<CityEntity, Integer>{
	public List<CityEntity> findByStateId(Integer stateId);
}
