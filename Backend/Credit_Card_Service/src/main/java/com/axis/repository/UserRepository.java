package com.axis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.axis.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,String>{

//	public Users findByUsername(String username);
	
	@Query("SELECT u FROM Users u where u.username = :username")
	public Users findByUsername(String username);
	
	@Query("SELECT u FROM Users u where u.email = :email")
	public Users findByEmail(String email);

	@Query("SELECT u FROM Users u WHERE u.id = :id")
	public int findByUserId(int id);
	
	 public Users findUserById(int userId);
}
