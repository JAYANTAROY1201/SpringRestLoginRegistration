package com.spring.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.model.EmployeeBean;

/**
 * purpose: General interface that extends mongorepositry to access the methods
 * @author JAYANTA ROY
 * @version 1.0
 * @since 10/07/18
 */
public interface GeneralMongoRepository extends MongoRepository<EmployeeBean, String> {

}
