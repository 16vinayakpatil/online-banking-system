package com.axis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.axis.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,String>{

	public Users findByUsername(String username);
	
	@Query("SELECT u.id FROM Users u WHERE u.username = :username")
	public int findUserIdByUsername(String username);

	@Query("SELECT u FROM Users u WHERE u.id = :id")
	public int findByUserId(int id);
    @Query("SELECT u FROM Users u WHERE u.id = :userid")
	public Users findById(int userid);
    

}
