package com.axis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axis.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,String>{

	public Users findByUsername(String username);

}
