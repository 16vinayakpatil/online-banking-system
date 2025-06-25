package com.axis.service;

import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.axis.entity.Account;

import com.axis.entity.Roles;
import com.axis.entity.Users;
import com.axis.repository.AccountRepository;
import com.axis.repository.RoleRepository;
import com.axis.repository.UserRepository;
import com.google.common.annotations.VisibleForTesting;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private RoleRepository rr;
	
	@Autowired
	private AccountRepository ar;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Validating user...................."+username);
		Users result = ur.findByUsername(username);
		System.out.println("User validation successful........."+result.getUsername());
		return new UserDetailsImpl(result);
	}
	
	public Users createCustomer(Users user) {
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Users u=ur.save(user);
		rr.save(new Roles("ROLE_CUSTOMER",u.getId()));
		ar.save(new Account(u.getId(),generateAccountNumber(),"SAVINGS A/C","DEMO0015014",10000.0,"PENDING"));
		return u;
	}
	
	public Users createEmployee(Users user) {
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Users u=ur.save(user);
		rr.save(new Roles("ROLE_EMPLOYEE",u.getId()));
		return u;
	}
	
	public void deleteEmployee(int userid) {
		ur.deleteUserByUserId(userid);
		rr.deleteRoleByUserId(userid);
	}
	
	public Users viewProfile(String username) {
		return ur.findByUsername(username);
	}
	
	public List<Account> viewAllPendingAccounts() {
		return ar.findByStatusPending();
	}
	
	public void activateAccount(int accountid) {
		ar.updateStatus(accountid);
	}
	
	public List<Users> listAllEmployees() {
		return ur.findAllEmployees();
	}

	public List<Account> listAllAccounts() {
		return ar.findAll();
	}
	
	public List<Users> listAllCustomers() {
		return ur.findAllCustomers();
	}
	
	public void deleteUserAndAccount(String accno) {
		int userid=ar.findUserIdByAccNo(accno);
		ur.deleteUserByUserId(userid);
		ar.deleteAccountByUserId(userid);
		rr.deleteRoleByUserId(userid);
	}
	
	public Account findAccountByUserId(int userid) {
		return ar.findAccountByUserId(userid);
	}
	 public Account findAccountById(int accid) {
	        return ar.findAccountById(accid);
	    }
	 
	 public Users findUserById(int userid) {
	        return ur.findUserById(userid);
	    }
	 @VisibleForTesting
	public static String generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++) 
            sb.append(random.nextInt(10));
        return sb.toString();
    }
	

    public Users editUser(Users updatedUser) {
        // Find the existing user by id
        Users existingUser = ur.findById(updatedUser.getId());

        if (existingUser != null) {
            // User found, update the details
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setPhone(updatedUser.getPhone());

            // Save the updated user back to the database
            return ur.save(existingUser);
        } else {
            // User not found, return null or throw a custom exception if needed
            return null;
        }
    }

	public Users findUserByEmail(String username) {
				return ur.findByEmail(username);
	}

	 @Transactional
	    public Users editVendor(Users updatedVendor) {
	        // Fetch the existing vendor record from the database using its id
	        Users existingVendor = ur.findUserById(updatedVendor.getId());

	        if (existingVendor != null) {
	            // Update the fields of the existing vendor record with the data from updatedVendor
	            existingVendor.setId(updatedVendor.getId());
	            existingVendor.setUsername(updatedVendor.getUsername());
	            existingVendor.setPassword(updatedVendor.getPassword());
	            existingVendor.setEmail(updatedVendor.getEmail());
	            existingVendor.setAddress(updatedVendor.getAddress());
	            existingVendor.setPhone(updatedVendor.getPhone());
	           

	            // Save the updated vendor record to the database
	            return ur.save(existingVendor);
	        }

	        // Return null if the vendor record with the given id is not found
	        return null;
	    }

	public Users getUserByUsername(String username) {
		
		return ur.findByUsername(username);
	}

	public void updateCustomer(Users customer) {
        ur.save(customer);
    }
	
	public void updateEmployee(Users employee) {
        ur.save(employee);
    }

}
