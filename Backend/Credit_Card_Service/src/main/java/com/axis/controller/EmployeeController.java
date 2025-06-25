package com.axis.controller;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axis.entity.CreditCard;

import com.axis.entity.Users;
import com.axis.service.UserDetailsServiceImpl;

@RestController
//@CrossOrigin(origins = "http://localhost:3000",allowCredentials="true")
@RequestMapping("/creditcard/employee")
public class EmployeeController {

	@Autowired
	private UserDetailsServiceImpl service;
	
	@PutMapping("/activate-credit-card")
	public String activateCreditCard(@RequestBody Map<String, Object> requestData) {
	    int creditcardid = Integer.valueOf(requestData.get("creditcardid").toString());
	    service.activateCreditCard(creditcardid);
	    
 CreditCard creditcard = service.findCreditCardById(creditcardid);
	    
	    Users user = service.findUserById(creditcard.account.getUserid());
   // Check if the user exists
	    if (user == null) {
	        return "Locker activation failed. User not found.";
	    }
	    // Sending email
	    // Configure the email properties
	    String senderEmail = "dummyahealthcare22@gmail.com";
	    String senderPassword = "gpcuphxksiekrqrp"; // Replace with the actual password
	    String recipientEmail = user.getEmail(); // Replace with the recipient's email address
	    String subject = "Credit Card Activation Confirmation";
	    String messageBody = "Dear User,\n\n"
	                        + "Your credit card with ID " + creditcardid + " has been successfully activated.\n\n"
	                        + "Thank you for choosing Bank.\n\n"
	                        + "Best regards,\n"
	                        + " Bank";

	    Properties properties = System.getProperties();
	    properties.put("mail.smtp.auth", "true");
	    properties.put("mail.smtp.ssl.enable", "true");
	    properties.put("mail.smtp.host", "smtp.gmail.com");
	    properties.put("mail.smtp.port", "465");

	    Session session = Session.getInstance(properties, new Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(senderEmail, senderPassword);
	        }
	    });

	    session.setDebug(true);

	    try {
	        // Create a MimeMessage and set the necessary attributes
	        MimeMessage message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(senderEmail));
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
	        message.setSubject(subject);
	        message.setText(messageBody);

	        // Send the message using Transport
	        Transport.send(message);
	        return "Credit card with ID " + creditcardid + " is successfully activated now. Confirmation email has been sent.";
	    } catch (MessagingException e) {
	        return "Credit card with ID " + creditcardid + " is successfully activated now, but there was an error sending the confirmation email.";
	    }
	}

	
	@PutMapping("/foreclose-credit-card")
	public String foreCloseCreditCard(@RequestBody Map<String, Object> requestData) {
	    int creditcardid = Integer.valueOf(requestData.get("creditcardid").toString());
	    service.foreCloseCreditCard(creditcardid);
	    
	    CreditCard creditcard = service.findCreditCardById(creditcardid);
	    
	    Users user = service.findUserById(creditcard.account.getUserid());
   // Check if the user exists
	    if (user == null) {
	        return "Locker activation failed. User not found.";
	    
	    }
	    // Sending email
	    // Configure the email properties
	    String senderEmail = "dummyahealthcare22@gmail.com";
	    String senderPassword = "gpcuphxksiekrqrp"; // Replace with the actual password
	    String recipientEmail = user.getEmail(); // Replace with the recipient's email address
	    String subject = "Credit Card Closure Confirmation";
	    String messageBody = "Dear User,\n\n"
	                        + "Your credit card with ID " + creditcardid + " has been successfully closed.\n\n"
	                        + "Thank you for choosing Bank.\n\n"
	                        + "Best regards,\n"
	                        + "Bank";

	    Properties properties = System.getProperties();
	    properties.put("mail.smtp.auth", "true");
	    properties.put("mail.smtp.ssl.enable", "true");
	    properties.put("mail.smtp.host", "smtp.gmail.com");
	    properties.put("mail.smtp.port", "465");

	    Session session = Session.getInstance(properties, new Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(senderEmail, senderPassword);
	        }
	    });

	    session.setDebug(true);

	    try {
	        // Create a MimeMessage and set the necessary attributes
	        MimeMessage message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(senderEmail));
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
	        message.setSubject(subject);
	        message.setText(messageBody);

	        // Send the message using Transport
	        Transport.send(message);
	        return "Credit card with ID " + creditcardid + " is successfully closed now. Confirmation email has been sent.";
	    } catch (MessagingException e) {
	        return "Credit card with ID " + creditcardid + " is successfully closed now, but there was an error sending the confirmation email.";
	    }
	}

	
	@GetMapping("/credit-card-pending-requests")
	public List<CreditCard> pendingCreditCardRequests() {
		return service.pendingCreditCardRequests();
	}
	
	@GetMapping("/credit-card-closing-requests")
	public List<CreditCard> closingCreditCardRequests() {
		return service.closingCreditCardRequests();
	}
	
	@GetMapping("/all-credit-cards")
	public List<CreditCard> allCreditCards() {
		return service.allCreditCards();
	}
	
}
