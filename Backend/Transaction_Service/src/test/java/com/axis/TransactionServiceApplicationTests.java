package com.axis;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.axis.entity.Account;
import com.axis.entity.Transaction;
import com.axis.entity.Users;
import com.axis.repository.AccountRepository;
import com.axis.repository.CustomerTransactionRepository;
import com.axis.repository.UserRepository;
import com.axis.service.UserDetailsServiceImpl;

@SpringBootTest
class TransactionServiceApplicationTests {
	
	@Autowired
	private UserDetailsServiceImpl service;

	@MockBean
	private CustomerTransactionRepository ctr;
	
	@MockBean
	private UserRepository ur;

	@MockBean
	private AccountRepository ar;
	
	  @Test
	    public void testFindUser_UserFound() {
	        // Prepare test data
	        String username = "testuser";
	        Users mockUser = new Users(username, "12345678", "sumit1905pal@gmail.com", "kanpur", "7408774783");

	        // Mock the userRepository.findAll method to return a list of users
	        List<Users> userList = new ArrayList<>();
	        userList.add(mockUser);
	        when(ur.findAll()).thenReturn(userList);

	        // Call the findUser method
	        Users result = service.findUser(username);

	        // Verify that the correct user is returned
	        Assertions.assertEquals(username, result.getUsername());
	        Assertions.assertEquals("12345678", result.getPassword());
	        // Add more assertions for other properties as needed
	    }

	    @Test
	    public void testFindUser_UserNotFound() {
	        // Prepare test data
	        String username = "nonexistentuser";

	        // Mock the userRepository.findAll method to return an empty list
	        when(ur.findAll()).thenReturn(new ArrayList<>());

	        // Call the findUser method
	        Users result = service.findUser(username);

	        // Verify that null is returned when the user is not found
	        Assertions.assertNull(result);
	    }
	    
	    @Test
	    public void testFindUserById_UserFound() {
	        // Prepare test data
	        int userId = 1;
	        Users mockUser = new Users("sumit", "12345678", "sumit1905pal@gmail.com", "kanpur", "7408774783");

	        // Mock the userRepository.findById method to return the mockUser
	        when(ur.findById(userId)).thenReturn(mockUser);

	        // Call the findUserById method
	        Users result = service.findUserById(userId);

	        // Verify that the correct user is returned
	        Assertions.assertNotNull(result);
	        Assertions.assertEquals("sumit", result.getUsername());
	        Assertions.assertEquals("12345678", result.getPassword());
	        // Add more assertions for other properties as needed
	    }

	    @Test
	    public void testFindUserById_UserNotFound() {
	        // Prepare test data
	        int userId = 42;

	        // Mock the userRepository.findById method to return null (user not found)
	        when(ur.findById(userId)).thenReturn(null);

	        // Call the findUserById method
	        Users result = service.findUserById(userId);

	        // Verify that null is returned when the user is not found
	        Assertions.assertNull(result);
	    }
	    
	    @Test
	    public void testFetchAllTransactions() {
	        // Prepare test data
	        Transaction transaction1 = new Transaction(/* transaction data */);
	        Transaction transaction2 = new Transaction(/* transaction data */);
	        List<Transaction> mockTransactions = Arrays.asList(transaction1, transaction2);

	        // Mock the transactionRepository.findAll method to return the mockTransactions list
	        when(ctr.findAll()).thenReturn(mockTransactions);

	        // Call the fetchAllTransactions method
	        List<Transaction> result = service.fetchAllTransactions();

	        // Verify that the correct list of transactions is returned
	        Assertions.assertEquals(mockTransactions.size(), result.size());
	        Assertions.assertEquals(mockTransactions.get(0), result.get(0));
	        Assertions.assertEquals(mockTransactions.get(1), result.get(1));
	        // Add more assertions for other properties as needed
	    }
	    
	   

	    @Test
	    public void testDeposit() {
	        // Prepare test data
	        int userId = 1;
	        double totalBalance = 1200.0;
	        double amount = 200.0;

	        // Mock the updateAmount method
	        doNothing().when(ar).updateAmount(userId, totalBalance);

	        // Mock the save method in TransactionRepository
	        when(ctr.save(any(Transaction.class))).thenAnswer(invocation -> {
	            Transaction savedTransaction = invocation.getArgument(0);
	            // Simulate the saving process by setting the ID manually (in a real application, it would be auto-generated)
	            savedTransaction.setTransactionid("1"); // Assuming ID 1 is assigned after saving
	            return savedTransaction;
	        });

	        // Call the deposit method
	        service.deposit(userId, totalBalance, amount);

	        // Verify that the updateAmount method is called with the correct arguments
	        verify(ar).updateAmount(userId, totalBalance);

	        // Verify that the save method is called with the correct Transaction object
	        verify(ctr).save(any(Transaction.class));
	    }
	    

	    @Test
	    public void testWithdraw() {
	        // Prepare test data
	        int userId = 1;
	        double totalBalance = 1000.0;
	        double amount = 200.0;

	        // Mock the updateAmount method
	        doNothing().when(ar).updateAmount(userId, totalBalance);

	        // Mock the save method in TransactionRepository
	        when(ctr.save(any(Transaction.class))).thenAnswer(invocation -> {
	            Transaction savedTransaction = invocation.getArgument(0);
	            // Simulate the saving process by setting the ID manually (in a real application, it would be auto-generated)
	            savedTransaction.setTransactionid("1"); // Assuming ID 1 is assigned after saving
	            return savedTransaction;
	        });

	        // Call the withdraw method
	        service.withdraw(userId, totalBalance, amount);

	        // Verify that the updateAmount method is called with the correct arguments
	        verify(ar).updateAmount(userId, totalBalance);

	        // Verify that the save method is called with the correct Transaction object
	        verify(ctr).save(any(Transaction.class));
	    }
}
