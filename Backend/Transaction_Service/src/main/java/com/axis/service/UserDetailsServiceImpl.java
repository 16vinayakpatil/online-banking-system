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
import com.axis.entity.Transaction;
import com.axis.entity.Users;
import com.axis.repository.AccountRepository;
import com.axis.repository.CustomerTransactionRepository;
import com.axis.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private CustomerTransactionRepository ctr;
	
	@Autowired
	private UserRepository ur;

	@Autowired
	private AccountRepository ar;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Validating user...................."+username);
		Users result = ur.findByUsername(username);
		if(result == null)
		{
			throw new UsernameNotFoundException("User not found with email: " + username);
		}
		System.out.println("User validation successful........."+result.getUsername());
		return new UserDetailsImpl(result);
	}
	
	public Users findUser(String username) {
		List<Users> userList=ur.findAll();
		for (Users user : userList) 
            if(user.getUsername().equalsIgnoreCase(username)) 
                return user; 
        return null;
	}
	
	public Users findUserById(int userid) {
		
		return ur.findById(userid);
	}
	public List<Transaction> fetchTransactionsForAccId(int accid) {
		return ctr.getAllTransactionsByAccountId(accid);
	}
	
	public List<Transaction> fetchAllTransactions() {
		return ctr.findAll();
	}
	
	
	
	public int findUserIdByUsername(String username) {
		if(findUser(username)!=null)
			return ur.findUserIdByUsername(username);
		return -1;
	}
	
	public int findAccountIdByUserId(int userid) {
		return ar.findAccountIdByUserId(userid);
	}
	
	public void deposit(int userid,double totalBalance,double amount) {
		ar.updateAmount(userid, totalBalance);
		Transaction t=new Transaction(generateTransactionId(), LocalDateTime.now(), amount, "Deposited From Bank/ATM", "CREDIT", ar.findAccountByUserId(userid));
		ctr.save(t);
	}
	
	public void withdraw(int userid,double totalBalance,double amount) {
		ar.updateAmount(userid, totalBalance);
		Transaction t=new Transaction(generateTransactionId(), LocalDateTime.now(), amount, "Withdrawal From Bank/ATM", "DEBIT", ar.findAccountByUserId(userid));
		ctr.save(t);
	}
	
	public void bankTransferDeposit(int userid,double totalBalance,double amount) {
		ar.updateAmount(userid, totalBalance);
		Transaction t=new Transaction(generateTransactionId(), LocalDateTime.now(), amount, "Deposited By Bank Transfer", "CREDIT", ar.findAccountByUserId(userid));
		ctr.save(t);
	}
	
	public void bankTransferWithdraw(int userid,double totalBalance,double amount) {
		ar.updateAmount(userid, totalBalance);
		Transaction t=new Transaction(generateTransactionId(), LocalDateTime.now(), amount, "Withdrawal By Bank Transfer", "DEBIT", ar.findAccountByUserId(userid));
		ctr.save(t);
	}
	
	public Account findAccountByUserId(int userid) {
		return ar.findAccountByUserId(userid);
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

	public int findUseridByAccNo(String accNoReceiver) {
		
		return ar.findUseridByAccno(accNoReceiver);
	}

	

	

}
