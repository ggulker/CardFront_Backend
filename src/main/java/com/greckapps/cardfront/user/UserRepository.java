package com.greckapps.cardfront.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer> {
	@Query("Select u FROM User u WHERE u.user_id = :username")
	User findByUsername(@Param("username")String username);
}
