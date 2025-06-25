package com.axis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.axis.entity.CreditCard;
import com.axis.entity.Users;


@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

	@Modifying
	@Transactional
    @Query("UPDATE CreditCard c SET c.status = 'CLOSING REQUEST BY CUSTOMER' WHERE c.account.accid = :accid AND c.creditcardnumber = :creditcardnumber")
    public void closeCreditCardRequest(int accid,String creditcardnumber);
	
	@Query("SELECT c FROM CreditCard c WHERE c.account.accid = :accid")
	public List<CreditCard> getAllMyCreditCards(int accid);
	
	@Modifying
	@Transactional
    @Query("UPDATE CreditCard c SET c.status = 'ACTIVE' WHERE c.creditcardid = :creditcardid")
    public void activateCreditCard(int creditcardid);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM CreditCard c WHERE c.creditcardid = :creditcardid")
	public void deleteCreditCard(int creditcardid);
	
	@Query("SELECT c FROM CreditCard c WHERE c.status = 'PENDING'")
	public List<CreditCard> pendingCreditCardRequests();
	
	@Query("SELECT c FROM CreditCard c WHERE c.status = 'CLOSING REQUEST BY CUSTOMER'")
	public List<CreditCard> closingCreditCardRequests();
	
	@Query("SELECT c FROM CreditCard c WHERE c.creditcardid = :creditcardid")
	public CreditCard findCreditCardById(int creditcardid);
	
    // Method to find a credit card by name 
    CreditCard findByCreditcardname(String creditcardname);

	public CreditCard findByCreditcardnumber(String creditCardNumber);
	
	  List<CreditCard> findByStatus(String status);

	    @Query("SELECT c FROM CreditCard c WHERE lower(c.creditcardname) = lower(?1)")
	    List<CreditCard> findByCreditcardnameIgnoreCase(String creditCardName);
}
