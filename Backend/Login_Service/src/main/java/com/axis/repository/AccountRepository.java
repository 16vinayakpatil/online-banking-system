package com.axis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.axis.entity.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

	@Query("SELECT a FROM Account a WHERE a.status = 'PENDING'")
	public List<Account> findByStatusPending();
	
	@Modifying
	@Transactional
    @Query("UPDATE Account a SET a.status = 'ACTIVE' WHERE a.accid = :accountid")
    public void updateStatus(int accountid);
	
	@Query("SELECT a.userid FROM Account a WHERE a.accno = :accno")
	public int findUserIdByAccNo(String accno);
	
	@Modifying
	@Transactional
    @Query("DELETE FROM Account a WHERE a.userid = :userid")
    public void deleteAccountByUserId(int userid);
	
	@Query("SELECT a FROM Account a WHERE a.userid = :userid")
	Account findAccountByUserId(int userid);
	
	@Query("SELECT a FROM Account a WHERE a.accid = :accid")
	public Account findAccountById(int accid);

}
