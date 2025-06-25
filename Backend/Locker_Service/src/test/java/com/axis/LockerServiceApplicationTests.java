package com.axis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.axis.entity.Account;
import com.axis.entity.Locker;
import com.axis.repository.AccountRepository;
import com.axis.repository.CustomerTransactionRepository;
import com.axis.repository.LockerRepository;
import com.axis.repository.UserRepository;
import com.axis.service.UserDetailsServiceImpl;

@SpringBootTest
class LockerServiceApplicationTests {
	
	@Autowired
	private UserDetailsServiceImpl service;

	@MockBean
	private UserRepository ur;
	
	@MockBean
	private AccountRepository ar;
	
	@MockBean
	private CustomerTransactionRepository ctr;
	
	@MockBean
	private LockerRepository lr;

	 @Test
	    public void testGetAllMyLockers() {
	        // Prepare test data
	        int accountId = 1;
	        Locker locker1 = new Locker("TYPE1", "LOCATION1", "SIZE1", 100.0,false, "PENDING", new Account());
	        Locker locker2 = new Locker("TYPE2", "LOCATION2", "SIZE2", 150.0,false, "APPROVED", new Account());
	        List<Locker> expectedLockers = Arrays.asList(locker1, locker2);

	        // Mock the behavior of LockerRepository
	        when(lr.getAllMyLockers(accountId)).thenReturn(expectedLockers);

	        // Call the method to be tested
	        List<Locker> actualLockers = service.getAllMyLockers(accountId);

	        // Verify the result
	        assertEquals(expectedLockers, actualLockers);

	        // Verify that the LockerRepository getAllMyLockers method was called with the correct accountId
	        verify(lr).getAllMyLockers(accountId);
	    }
	 
	 @Test
	    public void testPendingLockerRequests() {
	        // Mock data
	        List<Locker> expectedLockers = new ArrayList<>();
	        Locker locker1 = new Locker();
	        Locker locker2 = new Locker();
	        expectedLockers.add(locker1);
	        expectedLockers.add(locker2);

	        // Stubbing the behavior of LockerRepository
	        when(lr.pendingLockerRequests()).thenReturn(expectedLockers);

	        // Call the service method
	        List<Locker> result = service.pendingLockerRequests();

	        // Verify the result
	        assertEquals(expectedLockers, result);
	      

	        // Verify that the LockerRepository method was called
	        verify(lr, times(1)).pendingLockerRequests();
	    }
	 
	 @Test
	    public void testClosingLockerRequests() {
	        // Mock data
	        List<Locker> expectedLockers = new ArrayList<>();
	        Locker locker1 = new Locker();
	        Locker locker2 = new Locker();
	        expectedLockers.add(locker1);
	        expectedLockers.add(locker2);

	        // Stubbing the behavior of LockerRepository
	        when(lr.closingLockerRequests()).thenReturn(expectedLockers);

	        // Call the service method
	        List<Locker> result = service.closingLockerRequests();

	        // Verify the result
	        assertEquals(expectedLockers, result);
	        

	        // Verify that the LockerRepository method was called
	        verify(lr, times(1)).closingLockerRequests();
	    }
	 
	 @Test
	    public void testActivateLocker() {
	        // Mock data
	        int lockerId = 1;

	        // Call the service method
	        service.activateLocker(lockerId);

	        // Verify that the LockerRepository method was called with the correct lockerId
	        verify(lr, times(1)).activateLocker(lockerId);
	    }
	 
	 @Test
	    public void testGetAllLockers() {
	        // Mock data
	        Locker locker1 = new Locker();
	        Locker locker2 = new Locker();
	        List<Locker> mockLockers = new ArrayList<>();
	        mockLockers.add(locker1);
	        mockLockers.add(locker2);

	        // Configure the mock behavior
	        when(lr.findAll()).thenReturn(mockLockers);

	        // Call the service method
	        List<Locker> result = service.getAllLockers();

	        // Verify that the LockerRepository method was called once
	        verify(lr, times(1)).findAll();

	        // Verify the result
	        assertEquals(2, result.size());
	        
	    }
	 
	 @Test
	    public void testForeCloseLocker() {
	        int lockerId = 123; 

	        // Call the service method
	        service.foreCloseLocker(lockerId);

	        // Verify that the LockerRepository method was called once with the correct lockerId
	        verify(lr, times(1)).foreCloseLocker(lockerId);
	    }
	 
	 
	 


}
