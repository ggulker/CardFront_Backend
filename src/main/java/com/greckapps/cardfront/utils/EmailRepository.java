package com.greckapps.cardfront.utils;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends CrudRepository<Email, String> {
	@Query("Select u FROM Email u WHERE u.email_id = :emailId")
	Email findByEmailId(@Param("emailId")String emailId);
}

