package com.greckapps.cardfront.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	@Query("Select u FROM User u WHERE u.userId = :username")
	User findByUsername(@Param("username")String username);


	boolean existsByUserId(String user_id);
}
