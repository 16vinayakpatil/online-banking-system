package com.axis.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.axis.entity.Account;
import com.axis.entity.CreditCard;
import com.axis.entity.Transaction;
import com.axis.entity.Users;
import com.axis.repository.AccountRepository;
import com.axis.repository.CreditCardRepository;
import com.axis.repository.CustomerTransactionRepository;

import com.axis.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository ur;

	@Autowired
	private AccountRepository ar;
	
	@Autowired
	private CreditCardRepository ccr;
	
	@Autowired
	private CustomerTransactionRepository ctr;
	
	private static final Logger logger = LoggerFactory.getLogger(CreditCard.class);
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("Validating user...................."+email);
		Users result = ur.findByUsername(email);
		System.out.println("User validation successful........."+result.getEmail());
		return new UserDetailsImpl(result);
	}
	
	public Users findUser(String email) {
		return ur.findByEmail(email);

	}
	
	public Users findUserByUsername(String email) {
		return ur.findByUsername(email);

	}
	
	public void applyForCreditCard(Users user,String creditcardname) {
		
		 // Check if the user already has an active credit card with the same name
	    CreditCard existingCreditCard = ccr.findByCreditcardname(creditcardname);
	    if (existingCreditCard != null && existingCreditCard.getStatus().equals("ACTIVE")) {
	        // Do not allow applying for a new credit card with the same name if there is already an active one.
	        throw new RuntimeException("You already have an active credit card with the same name.");
	    }

//	    // Check if the user already has a credit card with the same name in pending status
//	    if (existingCreditCard != null && existingCreditCard.getStatus().equals("PENDING")) {
//	        throw new RuntimeException("You cannot apply for a new credit card with the same name until the previous one's status is closed or active.");
//	    }
		
		LocalDate expiryDate = LocalDate.now().plusMonths(60);
		CreditCard creditCard = new CreditCard(creditcardname, generateCreditCardNumber(), generateCVV(), 100000, expiryDate, "PENDING", findAccountByUserId(user.getId()));
		ccr.save(creditCard);
	}
	
	public void closeCreditCardRequest(Users user, String creditcardnumber) {
		Account account = ar.findAccountByUserId(user.getId());
		ccr.closeCreditCardRequest(account.getAccid(),creditcardnumber);
	}
	
	public List<CreditCard> getAllMyCreditCards(Users user) {
		Account account = ar.findAccountByUserId(user.getId());
		return ccr.getAllMyCreditCards(account.getAccid());
	}
	
	public void activateCreditCard(int creditcardid) {
		ccr.activateCreditCard(creditcardid);
	}
	
	public void foreCloseCreditCard(int creditcardid) {
		ccr.deleteCreditCard(creditcardid);
		
	}
	
	public List<CreditCard> pendingCreditCardRequests() {
		return ccr.pendingCreditCardRequests();
	}
	
	public List<CreditCard> closingCreditCardRequests() {
		return ccr.closingCreditCardRequests();
	}
	
	public List<CreditCard> allCreditCards() {
		return ccr.findAll();
	}

	public Account findAccountByUserId(int userid) {
		return ar.findAccountByUserId(userid);
	}
	
	 public Users findUserById(int userid) {
	        return ur.findUserById(userid);
	    }
	 
	 public CreditCard findCreditCardById(int creditcardid) {
	        return ccr.findCreditCardById(creditcardid);
	    }
	
	public static String generateCreditCardNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) 
            sb.append(random.nextInt(10));
        return sb.toString();
    }

	public static String generateCVV() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 13; i++) 
            sb.append(random.nextInt(10));
        return sb.toString();
    }
	

    public boolean processCreditCardPayment(Users user, Double amount, String creditCardNumber, String cvv, String expiryDate) {
        // Find the user's active credit card by credit card number
        CreditCard creditCard = ccr.findByCreditcardnumber(creditCardNumber);
        
        if (creditCard != null) {
            logger.info("Credit card found");
        } else {
            logger.info("Credit card not found");
        }

        // Perform necessary validations for credit card details
        if (creditCard == null || !creditCard.getCreditcardcvv().equals(cvv)) {
            return false; // Payment failed due to incorrect credit card details
        }

        // Update the credit card balance by deducting the payment amount
        Double currentBalance = creditCard.getBalance();
        if (currentBalance < amount) {
            return false; // Payment failed due to insufficient balance
        }

        creditCard.setBalance(currentBalance - amount);
        creditCard.setLoanamount(creditCard.getCreditcardlimit()-creditCard.getBalance());
        ccr.save(creditCard);
        return true; // Payment successful
    }
    
    public boolean processCreditCardEMIPayment(Users user, Double emiAmount, String creditCardNumber, String cvv, String expiryDate,int userid,int accid) {
        // Find the user's active credit card by credit card number
        CreditCard creditCard = ccr.findByCreditcardnumber(creditCardNumber);
        
        if (creditCard != null) {
            logger.info("Credit card found");
        } else {
            logger.info("Credit card not found");
        }
       

        // Perform necessary validations for credit card details
        if (creditCard == null || !creditCard.getCreditcardcvv().equals(cvv) ) {
            return false; // Payment failed due to incorrect credit card details
        }

        // Calculate EMI amount based on credit card limit, interest rate, and tenure
        double emiInterestRate = 0.12; // Assuming 12% interest rate (you can adjust this as needed)
        int emiTenureMonths = 6; // Assuming EMI tenure of 6 months (you can adjust this as needed)
        double loanAmount = creditCard.getCreditcardlimit() - creditCard.getBalance();
        double calculatedEMIAmount = calculateEMIAmount(loanAmount, emiInterestRate, emiTenureMonths);

//        // Perform additional check to ensure the provided EMI amount matches the calculated EMI amount
//        if (!emiAmount.equals(calculatedEMIAmount)) {
//            return false; // Payment failed due to incorrect EMI amount
//        }

        // Update the credit card balance by adding the EMI amount
        Double currentBalance = creditCard.getBalance();
        
        if(currentBalance==creditCard.getCreditcardlimit()) {
        	return false;
        }
        
        double addedamount=currentBalance + emiAmount;
        if (addedamount > creditCard.getCreditcardlimit()) {
            creditCard.setBalance(creditCard.getCreditcardlimit()); // Payment failed due to insufficient balance
            creditCard.setLoanamount(0);
        }else {
         
        creditCard.setBalance(addedamount);
        creditCard.setLoanamount(creditCard.getCreditcardlimit()-creditCard.getBalance());
        }
        
        ccr.save(creditCard);
        creditCardPayment(creditCardNumber,emiAmount,accid,userid) ;
        return true; // EMI payment successful
    }

    // Method to calculate EMI amount based on credit card limit, interest rate, and tenure
    public double calculateEMIAmount(double loanAmount, double interestRate, int tenureMonths) {
        double monthlyInterestRate = interestRate / 12.0;
        double emi = (loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, tenureMonths)) / (Math.pow(1 + monthlyInterestRate, tenureMonths) - 1);
        return emi;
    }
    
    public void creditCardPayment(String creditCardNumber,Double emiAmount,int accid,int userid) {
		ar.creditCardPayment(emiAmount,accid);
		Transaction t=new Transaction(generateTransactionId(), LocalDateTime.now(), emiAmount,  " EMI Withdrawal", "DEBIT", ar.findAccountByUserId(userid));
		ctr.save(t);
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

	public CreditCard findByCreditCardNumber(String creditCardNumber) {
		
		return ccr.findByCreditcardnumber(creditCardNumber);
	}
	
//	  public boolean checkIfUserHasActiveCreditCard(String creditCardName) {
//	        List<CreditCard> activeCreditCards = ccr.findByStatus("ACTIVE");
//
//	        for (CreditCard creditCard : activeCreditCards) {
//	            if (creditCard.getCreditcardname().equalsIgnoreCase(creditCardName)) {
//	                return true; // User has an active credit card with the given name
//	            }
//	        }
//	        return false; // User does not have an active credit card with the given name
//	    }
//	  
	public boolean checkIfUserHasCreditCard(String creditCardName) {
	    List<CreditCard> creditCards = ccr.findByCreditcardnameIgnoreCase(creditCardName);

	    for (CreditCard creditCard : creditCards) {
	        if (creditCard.getStatus().equals("ACTIVE")) {
	            return true; // User has an active credit card with the given name
	        }
	    }

	    return false; // User does not have an active credit card with the given name
	}

}
