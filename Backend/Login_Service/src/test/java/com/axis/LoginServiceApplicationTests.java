package com.axis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.axis.entity.Account;
import com.axis.entity.Roles;
import com.axis.entity.Users;
import com.axis.repository.AccountRepository;
import com.axis.repository.RoleRepository;
import com.axis.repository.UserRepository;
import com.axis.service.UserDetailsServiceImpl;
import com.google.common.annotations.VisibleForTesting;

@SpringBootTest
class LoginServiceApplicationTests {

	@Autowired
	private UserDetailsServiceImpl service;
	
	@MockBean
	private UserRepository ur;
	
	@MockBean
	private AccountRepository ar;
	
	@MockBean
	private RoleRepository rr;

	@Test
	public void  listAllEmployeesTest() {
		when(ur.findAllEmployees()).thenReturn(Stream
				.of(new Users("sumit", "12345678", "sumit1905pal@gmail.com", "kanpur", "7408774783", 0, false)).collect(Collectors.toList()));
		assertEquals(1, service.listAllEmployees().size());
	}
	
	@Test
	public void  listAllAccountsTest() {
		when(ar.findAll()).thenReturn(Stream
				.of(new Account(24, "12345678", "SAVING AC", "DEMO1234",1000000.000000,"ACTIVE")).collect(Collectors.toList()));
		assertEquals(1, service.listAllAccounts().size());
	}
	
	@Test
	public void  listAllCustomersTest() {
		when(ur.findAllCustomers()).thenReturn(Stream
				.of(new Users("sumit", "12345678", "sumit1905pal@gmail.com", "kanpur", "7408774783", 0, false)).collect(Collectors.toList()));
		assertEquals(1, service.listAllCustomers().size());
	}
	
	  @Test
	    public void getUserByUsernameTest() {
	        // Create a mock user
	        Users mockUser = new Users("sumit", "12345678", "sumit1905pal@gmail.com", "kanpur", "7408774783", 0, false);

	        // Mock the userRepository.findByUsername method to return the mock user
	        when(ur.findByUsername("sumit")).thenReturn(mockUser);

	        // Call the method being tested
	        Users result = service.getUserByUsername("sumit");

	        // Verify the result
	        assertEquals(mockUser, result);
	    }
	  
	  @Test
	    public void deleteUserAndAccountTest() {
	        // Mock data
	        String accno = "DEMO1234";
	        int userId = 42;

	        // Mock the accountRepository.findUserIdByAccNo method to return the userId
	        when(ar.findUserIdByAccNo(accno)).thenReturn(userId);

	        // Call the method being tested
	        service.deleteUserAndAccount(accno);

	        // Verify that the expected methods are called with the correct arguments
	        verify(ur).deleteUserByUserId(userId);
	        verify(ar).deleteAccountByUserId(userId);
	        verify(rr).deleteRoleByUserId(userId);
	    }
	  
	  @Test
	    public void findAccountByIdTest() {
	        // Create a mock account
	        Account mockAccount = new Account();
	        mockAccount.setAccid(1);
	        mockAccount.setAccno("ACC123456");
	        mockAccount.setAcctype("SAVING AC");
	        // Set other properties...

	        // Mock the accountRepository.findAccountById method to return the mock account
	        when(ar.findAccountById(1)).thenReturn(mockAccount);

	        // Call the method being tested
	        Account result = service.findAccountById(1);

	        // Verify the result
	        assertEquals(mockAccount, result);
	    }
	  
	  @Test
	    public void deleteEmployeeTest() {
	        // Mock data
	        int userId = 42;

	        // Call the method being tested
	        service.deleteEmployee(userId);

	        // Verify that the expected methods are called with the correct arguments
	        verify(ur).deleteUserByUserId(userId);
	        verify(rr).deleteRoleByUserId(userId);
	    }
	  
	  @Test
	    public void viewProfileTest() {
	        // Create a mock user
	        Users mockUser = new Users("sumit", "12345678", "sumit1905pal@gmail.com", "kanpur", "7408774783", 0, false);

	        // Mock the userRepository.findByUsername method to return the mock user
	        when(ur.findByUsername("sumit")).thenReturn(mockUser);

	        // Call the method being tested
	        Users result = service.viewProfile("sumit");

	        // Verify the result
	        assertEquals(mockUser, result);
	    }
	  
	  @Test
	    public void activateAccountTest() {
	        // Call the method being tested
	        int accountId = 42;
	        service.activateAccount(accountId);

	        // Verify that the expected method is called with the correct argument
	        verify(ar).updateStatus(accountId);
	    }
	  
	  @VisibleForTesting
	    String generateAccountNumber() {
	        // Return a fixed account number for testing purposes
	        return "GeneratedAccountNumber";
	    }
	  

//	    @Test
//	    public void createCustomerTest() {
//	        // Create a mock user
//	        Users mockUser = new Users("sumit", "12345678", "sumit1905pal@gmail.com", "kanpur", "7408774783", 0, false);
//
//	        // Mock the userRepository.save method to return the mock user
//	        when(ur.save(any(Users.class))).thenReturn(mockUser);
//
//	        // Mock the roleRepository.save method to return any(Roles.class) instance
//	        when(rr.save(any(Roles.class))).thenReturn(any(Roles.class));
//
//	        // Mock the generateAccountNumber() method to return a fixed account number during the test
//	        when(service.generateAccountNumber()).thenReturn("GeneratedAccountNumber");
//
//	        // Call the method being tested
//	        Users result = service.createCustomer(mockUser);
//
//	        // Verify the result and method invocations
//	        assertEquals(mockUser, result);
//	        verify(ur).save(mockUser);
//	        verify(rr).save(any(Roles.class));
//	        verify(ar).save(new Account(mockUser.getId(), "GeneratedAccountNumber", "SAVINGS A/C", "DEMO0015014", 10000.0, "PENDING"));
//	    }
	}








