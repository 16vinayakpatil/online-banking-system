package com.axis.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.axis.entity.Account;
import com.axis.entity.Locker;
import com.axis.entity.Transaction;
import com.axis.entity.Users;
import com.axis.repository.AccountRepository;
import com.axis.repository.CustomerTransactionRepository;
import com.axis.repository.LockerRepository;
import com.axis.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private AccountRepository ar;
	
	@Autowired
	private CustomerTransactionRepository ctr;
	
	@Autowired
	private LockerRepository lr;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("Validating user...................."+email);
		Users result = ur.findByUsername(email);
		if(result == null)
		{
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
		System.out.println("User validation successful........."+result.getEmail());
		return new UserDetailsImpl(result);
	}
	
	public void applyForLocker(Users user, String lockertype, String lockerlocation, String lockersize) {
		double lockeramount = calculateLockerPrice(lockertype,lockersize);
		Locker locker = new Locker(lockertype, lockerlocation, lockersize, lockeramount,false,"PENDING", findAccountByUserId(user.getId()));
		lr.save(locker);
		ar.lockerPayment(lockeramount, findAccId(user.getId()));
		Transaction t=new Transaction(generateTransactionId(), LocalDateTime.now(), lockeramount, lockertype + " Payment Withdrawal", "DEBIT", ar.findAccountByUserId(user.getId()));
		ctr.save(t);
	}
	
	public List<Locker> getAllMyLockers(int accid) {
		return lr.getAllMyLockers(accid);
	}
	
	public boolean lockerPayment(String lockertype, double lockeramount, Users user) {
		ar.lockerPayment(lockeramount, findAccId(user.getId()));
		Transaction t=new Transaction(generateTransactionId(), LocalDateTime.now(), lockeramount, lockertype + " Payment Withdrawal", "DEBIT", ar.findAccountByUserId(user.getId()));
		ctr.save(t);
		return true;
	}
	
	public void closeLockerRequest(Users user,String lockertype) {
		lr.closeLockerRequest(findAccId(user.getId()),lockertype);
	}
	
	
	
	
	
	
	
	
	public List<Locker> pendingLockerRequests() {
		return lr.pendingLockerRequests();
	}
	
	public List<Locker> closingLockerRequests() {
		return lr.closingLockerRequests();
	}
	
	public void activateLocker(int lockerid) {
		lr.activateLocker(lockerid);
	}
	
	public List<Locker> getAllLockers() {
		return lr.findAll();
	}
	
	public void foreCloseLocker(int lockerid) {
		lr.foreCloseLocker(lockerid);
	}
	
	public static String generateTransactionId() {
		String ALPHANUMERIC_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	    int ID_LENGTH = 15;
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            int randomIndex = random.nextInt(ALPHANUMERIC_CHARS.length());
            char randomChar = ALPHANUMERIC_CHARS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
	
	public double calculateLockerPrice(String lockertype,String lockersize) {
		double lockertypeamount = 0,lockersizeamount = 0;
		
		if(lockertype.equals("Temporary Locker"))
			lockertypeamount = 30;
		if(lockertype.equals("Personal Locker"))
			lockertypeamount = 40;
		if(lockertype.equals("Self Deposit Locker"))
			lockertypeamount = 40;
		if(lockertype.equals("Business Locker"))
			lockertypeamount = 50;
		if(lockertype.equals("Shared Locker"))
			lockertypeamount = 50;
		if(lockertype.equals("Digital Locker"))
			lockertypeamount = 50;
		if(lockertype.equals("Keyless Locker"))
			lockertypeamount = 50;
		
		if(lockersize.equals("Small"))
			lockersizeamount = 10;
		if(lockersize.equals("Medium"))
			lockersizeamount = 20;
		if(lockersize.equals("Large"))
			lockersizeamount = 30;
		if(lockersize.equals("Extra Large"))
			lockersizeamount = 40;
		
		return lockertypeamount * lockersizeamount;
	}
	

	public Users findUser(String email) {
		//return ur.findByEmail(email);
		return ur.findByUsername(email);

	}
	 public Users findUserById(int userid) {
	        return ur.findUserById(userid);
	    }

	public int findAccId(int userid) {
		return ar.findAccountIdByUserId(userid);
	}
	
	 public Locker findLockerById(int lockerId) {
	        return lr.findLockerById(lockerId);
	    }

	public Account findAccountByUserId(int userid) {
		return ar.findAccountByUserId(userid);
	}

	public void updateLocker(Locker locker) {
		lr.save(locker);
		
	}

	

	
}
