package com.axis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.axis.entity.Account;
import com.axis.entity.CreditCard;
import com.axis.entity.Users;
import com.axis.repository.AccountRepository;
import com.axis.repository.CreditCardRepository;
import com.axis.repository.CustomerTransactionRepository;
import com.axis.repository.UserRepository;
import com.axis.service.UserDetailsServiceImpl;

@SpringBootTest
class CreditCardServiceApplicationTests {

	@Autowired
	private UserDetailsServiceImpl service;

	@MockBean
	private UserRepository ur;
	
	@MockBean
	private AccountRepository ar;
	
	@MockBean
	private CustomerTransactionRepository ctr;
	
	@MockBean
	private CreditCardRepository lr;
	
	@Test
    public void testGetAllMyCreditCards() {
        // Prepare test data
        int userId = 1;
        int accountId = 101;

        Users user = new Users();
         user.setId(userId);
        

        Account account = new Account();
        account.setAccid(accountId);
        account.setUserid(user.getId());

        CreditCard creditCard1 = new CreditCard();
        creditCard1.setCreditcardid(1);
        

        CreditCard creditCard2 = new CreditCard();
        creditCard2.setCreditcardid(2);
        

        List<CreditCard> creditCards = new ArrayList<>();
        creditCards.add(creditCard1);
        creditCards.add(creditCard2);

        // Mock the behavior of the accountRepository.findAccountByUserId method
        when(ar.findAccountByUserId(userId)).thenReturn(account);

        // Mock the behavior of the creditCardRepository.getAllMyCreditCards method
        when(lr.getAllMyCreditCards(accountId)).thenReturn(creditCards);

        // Call the service method
        List<CreditCard> result = service.getAllMyCreditCards(user);

        // Verify that the accountRepository.findAccountByUserId method was called once with the correct user ID
        verify(ar, times(1)).findAccountByUserId(userId);

        // Verify that the creditCardRepository.getAllMyCreditCards method was called once with the correct account ID
        verify(lr, times(1)).getAllMyCreditCards(accountId);

        // Verify that the returned list of credit cards is not null
        assertNotNull(result);

        // Verify that the returned list contains the correct number of credit cards
        assertEquals(2, result.size());

        // Verify that the returned list contains the expected credit cards
        assertTrue(result.contains(creditCard1));
        assertTrue(result.contains(creditCard2));
    }
	
	 @Test
	    public void testActivateCreditCard() {
	        // Prepare test data
	        int creditCardId = 1;

	        // Call the service method
	        service.activateCreditCard(creditCardId);

	        // Verify that the creditCardRepository.activateCreditCard method was called once with the correct creditcardid
	        verify(lr, times(1)).activateCreditCard(creditCardId);
	    }
	 
	 @Test
	    public void testForeCloseCreditCard() {
	        // Prepare test data
	        int creditCardId = 1;

	        // Call the service method
	        service.foreCloseCreditCard(creditCardId);

	        // Verify that the creditCardRepository.deleteCreditCard method was called once with the correct creditcardid
	        verify(lr, times(1)).deleteCreditCard(creditCardId);
	    }
	 
	 @Test
	    public void testPendingCreditCardRequests() {
	        // Prepare test data
	        CreditCard creditCard1 = new CreditCard();
	        CreditCard creditCard2 = new CreditCard();
	        List<CreditCard> expectedCreditCardList = new ArrayList<>();
	        expectedCreditCardList.add(creditCard1);
	        expectedCreditCardList.add(creditCard2);

	        // Mock the creditCardRepository.pendingCreditCardRequests method to return the expected list
	        when(lr.pendingCreditCardRequests()).thenReturn(expectedCreditCardList);

	        // Call the service method
	        List<CreditCard> actualCreditCardList = service.pendingCreditCardRequests();

	        // Verify that the creditCardRepository.pendingCreditCardRequests method was called once
	        verify(lr, times(1)).pendingCreditCardRequests();

	        // Verify that the actual list returned by the service method matches the expected list
	        assertEquals(expectedCreditCardList, actualCreditCardList);
	    }
	 
	 @Test
	    public void testClosingCreditCardRequests() {
	        // Prepare test data
	        CreditCard creditCard1 = new CreditCard();
	        CreditCard creditCard2 = new CreditCard();
	        List<CreditCard> expectedCreditCardList = new ArrayList<>();
	        expectedCreditCardList.add(creditCard1);
	        expectedCreditCardList.add(creditCard2);

	        // Mock the creditCardRepository.closingCreditCardRequests method to return the expected list
	        when(lr.closingCreditCardRequests()).thenReturn(expectedCreditCardList);

	        // Call the service method
	        List<CreditCard> actualCreditCardList = service.closingCreditCardRequests();

	        // Verify that the creditCardRepository.closingCreditCardRequests method was called once
	        verify(lr, times(1)).closingCreditCardRequests();

	        // Verify that the actual list returned by the service method matches the expected list
	        assertEquals(expectedCreditCardList, actualCreditCardList);
	    }
	 
	 @Test
	    public void testAllCreditCards() {
	        // Prepare test data
	        CreditCard creditCard1 = new CreditCard();
	        CreditCard creditCard2 = new CreditCard();
	        List<CreditCard> expectedCreditCardList = new ArrayList<>();
	        expectedCreditCardList.add(creditCard1);
	        expectedCreditCardList.add(creditCard2);

	        // Mock the creditCardRepository.findAll method to return the expected list
	        when(lr.findAll()).thenReturn(expectedCreditCardList);

	        // Call the service method
	        List<CreditCard> actualCreditCardList = service.allCreditCards();

	        // Verify that the creditCardRepository.findAll method was called once
	        verify(lr, times(1)).findAll();

	        // Verify that the actual list returned by the service method matches the expected list
	        assertEquals(expectedCreditCardList, actualCreditCardList);
	    }

}
