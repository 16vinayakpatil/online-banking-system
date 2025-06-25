package com.axis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.axis.entity.Transaction;

@Repository
public interface CustomerTransactionRepository extends JpaRepository<Transaction, Integer> {

	@Query("SELECT t FROM Transaction t WHERE t.account.accid = :accid")
	public List<Transaction> getAllTransactionsByAccountId(int accid);
	
}
