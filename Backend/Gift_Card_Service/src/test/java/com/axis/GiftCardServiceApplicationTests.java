package com.axis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.axis.entity.Account;
import com.axis.entity.GiftCard;
import com.axis.entity.Transaction;
import com.axis.repository.AccountRepository;
import com.axis.repository.CustomerTransactionRepository;
import com.axis.repository.GiftCardRepository;
import com.axis.repository.UserRepository;
import com.axis.service.UserDetailsServiceImpl;

@SpringBootTest
class GiftCardServiceApplicationTests {

	@Autowired
	private UserDetailsServiceImpl service;

	@MockBean
	private UserRepository ur;
	
	@MockBean
	private AccountRepository ar;
	
	@MockBean
	private CustomerTransactionRepository ctr;
	
	@MockBean
	private GiftCardRepository lr;
	
	 @Test
	    public void testBuyGiftCard() {
	        // Create a sample GiftCard object
	        GiftCard giftCard = new GiftCard();
	        

	        // Create a sample Account object
	        Account account = new Account();
	        account.setAccid(1);
	       

	        // Associate the Account object with the GiftCard object
	        giftCard.setAccount(account);

	        // Mock the behavior of the giftCardRepository.save and accountRepository.withdrawAmount methods
	        when(lr.save(any(GiftCard.class))).thenReturn(giftCard);

	        // Call the service method
	        service.buyGiftCard(giftCard);

	        // Verify that giftCardRepository.save was called once with the correct GiftCard object
	        verify(lr, times(1)).save(giftCard);

	        // Verify that accountRepository.withdrawAmount was called once with the correct account id and gift card amount
	        verify(ar, times(1)).withdrawAmount(account.getAccid(), giftCard.getGiftcardamount());

	        // Use ArgumentCaptor to capture the Transaction object that is saved
	        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
	        verify(ctr, times(1)).save(transactionCaptor.capture());

	        // Retrieve the captured Transaction object
	        Transaction savedTransaction = transactionCaptor.getValue();

	        // Verify that the Transaction object is not null
	        assertNotNull(savedTransaction);

	        // Verify that the properties of the saved Transaction object match the expected values
	        assertEquals(giftCard.getGiftcardamount(), savedTransaction.getAmount());
	        assertEquals(giftCard.getGiftcardname() + " Withdrawal", savedTransaction.getDescription());
	        assertEquals("DEBIT", savedTransaction.getTransactiontype());
	        assertEquals(account, savedTransaction.getAccount());
	        
	    }
	 
	 @Test
	    public void testShowAllMyGiftCardsPurchased() {
	        // Prepare test data
	        int accountId = 1;

	        GiftCard giftCard1 = new GiftCard();
	        giftCard1.setGiftcardid(1);
	        

	        GiftCard giftCard2 = new GiftCard();
	        giftCard2.setGiftcardid(2);
	        

	        List<GiftCard> giftCards = new ArrayList<>();
	        giftCards.add(giftCard1);
	        giftCards.add(giftCard2);

	        // Mock the behavior of the giftCardRepository.findGiftCardsByAccId method
	        when(lr.findGiftCardsByAccId(accountId)).thenReturn(giftCards);

	        // Call the service method
	        List<GiftCard> result = service.showAllMyGiftCardsPurchased(accountId);

	        // Verify that the giftCardRepository.findGiftCardsByAccId method was called once with the correct account ID
	        verify(lr, times(1)).findGiftCardsByAccId(accountId);

	        // Verify that the returned list of gift cards is not null
	        assertNotNull(result);

	        // Verify that the returned list contains the correct number of gift cards
	        assertEquals(2, result.size());

	        // Verify that the returned list contains the expected gift cards
	        assertTrue(result.contains(giftCard1));
	        assertTrue(result.contains(giftCard2));
	    }
}
