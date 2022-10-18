package com.sudhir.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sudhir.entity.CountryEntity;

public interface CountryRepository extends JpaRepository<CountryEntity, Serializable>{

}
