package com.axis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.axis.entity.Account;
import com.axis.entity.Locker;

@Repository
public interface LockerRepository extends JpaRepository<Locker, Integer> {

	@Query("SELECT l FROM Locker l WHERE l.account.accid = :accid")
	public List<Locker> getAllMyLockers(int accid);
	
	@Query("SELECT l FROM Locker l WHERE l.lockerstatus = 'PENDING'")
	public List<Locker> pendingLockerRequests();
	
	@Modifying
	@Transactional
    @Query("UPDATE Locker l SET l.lockerstatus = 'ACTIVE' WHERE l.lockerid = :lockerid")
    public void activateLocker(int lockerid);
	
	@Modifying
	@Transactional
    @Query("UPDATE Locker l SET l.lockerstatus = 'CLOSING REQUEST BY CUSTOMER' WHERE l.account.accid = :accid AND l.lockertype = :lockertype")
    public void closeLockerRequest(int accid,String lockertype);
	
	@Query("SELECT l FROM Locker l WHERE l.lockerstatus = 'CLOSING REQUEST BY CUSTOMER'")
	public List<Locker> closingLockerRequests();
	
	@Modifying
	@Transactional
    @Query("UPDATE Locker l SET l.lockerstatus = 'CLOSED' WHERE l.lockerid = :lockerid")
    public void foreCloseLocker(int lockerid);
	
	@Query("SELECT l FROM Locker l WHERE l.lockerid = :lockerid")
	public Locker findLockerById(int lockerid);
	
}
