package com.axis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.axis.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,String>{

	public Users findByUsername(String username);
	
	@Query("SELECT u FROM Users u JOIN Roles r ON u.id = r.userid WHERE r.name = 'ROLE_EMPLOYEE'")
	public List<Users> findAllEmployees();
	
	@Query("SELECT u FROM Users u JOIN Roles r ON u.id = r.userid WHERE r.name = 'ROLE_CUSTOMER'")
	public List<Users> findAllCustomers();
	
	@Transactional
    @Modifying
    @Query("DELETE FROM Users u WHERE u.id = :userid")
	public void deleteUserByUserId(int userid);
	
	 public Users findUserById(int userId);

	public Users findById(int id);

	public Users findByEmail(String username);
}
