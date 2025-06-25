package com.axis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.axis.entity.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {

	@Transactional
    @Modifying
    @Query("DELETE FROM Roles r WHERE r.userid = :userid")
	public void deleteRoleByUserId(int userid);
	
}
