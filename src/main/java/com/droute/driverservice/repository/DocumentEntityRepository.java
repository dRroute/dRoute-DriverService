package com.droute.driverservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.droute.driverservice.entity.DocumentEntity;

@Repository
public interface DocumentEntityRepository extends JpaRepository<DocumentEntity, Long>{

	

	
}
