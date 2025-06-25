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
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.axis.entity.Account;
import com.axis.entity.CreditCard;
import com.axis.entity.Users;
import com.axis.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/creditcard/customer")
public class CustomerController {

    @Autowired
    private UserDetailsServiceImpl service;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @PostMapping("/apply-for-credit-card")
    public String applyForCreditCard(HttpServletRequest request, @RequestBody Map<String, Object> requestData) {
        // Get the user's email from the request attribute
        String email = request.getAttribute("email").toString();

        // Find the user based on the email
        Users user = service.findUserByUsername(email);

        // Extract credit card name from the request body
        String creditcardname = String.valueOf(requestData.get("creditcardname").toString());

        logger.info("Received credit card application request for user: {}", email);

        // Check if the user already has an active credit card with the same name
        boolean hasActiveCreditCard = service.checkIfUserHasCreditCard(creditcardname);
        if (hasActiveCreditCard) {
            logger.info("User already has an active credit card with the same name: {}", creditcardname);
            return "You already have an active credit card with the name " + creditcardname + ".";
        }

        // Apply for the credit card using the service method
        service.applyForCreditCard(user, creditcardname);

        // Sending email
        // Configure the email properties
        String senderEmail = "dummyahealthcare22@gmail.com";
        String senderPassword = "gpcuphxksiekrqrp"; // Replace with the actual password
        String recipientEmail = user.getEmail(); // Use the user's email as the recipient's email address
        String subject = "Credit Card Application Confirmation";
        String messageBody = "Dear " + user.getUsername() + ",\n\n"
                + "Your credit card application for " + creditcardname + " has been received and is under review.\n\n"
                + "We will get back to you soon with further details.\n\n"
                + "Thank you,\n"
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
            logger.info("Credit card application confirmation email sent to: {}", recipientEmail);
            return "You've successfully applied for " + creditcardname + ". Confirmation email has been sent.";
        } catch (MessagingException e) {
            logger.error("Error sending credit card application confirmation email to: {}", recipientEmail, e);
            return "You've successfully applied for " + creditcardname + ", but there was an error sending the confirmation email.";
        }
    }


    @PutMapping("/close-credit-card-request")
    public String closeCreditCardRequest(HttpServletRequest request, @RequestBody Map<String, Object> requestData) {
        String email = request.getAttribute("email").toString();
        Users user = service.findUserByUsername(email);

        String creditcardnumber = String.valueOf(requestData.get("creditcardnumber").toString());
        CreditCard card=service.findByCreditCardNumber(creditcardnumber);

        logger.info("Received credit card closure request for user: {}", email);
        
        // Check if the user has any pending loan amount
        
        if (card.getLoanamount() != 0) {
            logger.info("Cannot close credit card. User has a pending loan amount: {}", card.getLoanamount());
            return "Cannot close credit card. You have a pending loan amount of $" + card.getLoanamount() + ".";
        }


        service.closeCreditCardRequest(user,creditcardnumber);

        String senderEmail = "dummyahealthcare22@gmail.com";
        String senderPassword = "gpcuphxksiekrqrp";
        String recipientEmail = user.getEmail();
        String subject = "Credit Card Closure Request";
        String messageBody = "Dear " + user.getUsername() + ",\n\n"
                + "Your request for closing the credit card " + creditcardnumber + " has been received and is under review.\n\n"
                + "We will get back to you soon with further details.\n\n"
                + "Thank you,\n"
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
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message);
            logger.info("Credit card closure request email sent to: {}", recipientEmail);
            return "Your request for closing " + creditcardnumber + " has been sent to the employee. Confirmation email has been sent.";
        } catch (MessagingException e) {
            logger.error("Error sending credit card closure request email to: {}", recipientEmail, e);
            return "Your request for closing " + creditcardnumber + " has been sent to the employee, but there was an error sending the confirmation email.";
        }
    }

    @GetMapping("/my-credit-cards")
    public List<CreditCard> getAllMyCreditCards(HttpServletRequest request) {
        String email = request.getAttribute("email").toString();
        Users user = service.findUserByUsername(email);
        logger.info("Fetching all credit cards for user: {}", email);
        return service.getAllMyCreditCards(user);
    }

    @PostMapping("/make-payment")
    public String makeCreditCardPayment(HttpServletRequest request, @RequestBody Map<String, Object> requestData) {
        String email = request.getAttribute("email").toString();
        Users user = service.findUserByUsername(email);
        Double amount = Double.valueOf(requestData.get("amount").toString());
        String creditCardNumber = requestData.get("creditcardnumber").toString();
        String cvv = requestData.get("creditcardcvv").toString();
        String expiryDate = requestData.get("expirydate").toString();
        CreditCard creditCard = service.findByCreditCardNumber(creditCardNumber);

        logger.info("Processing credit card payment for user: {}", email);

        // Check if the credit card is active before processing the payment
        if ("active".equalsIgnoreCase(creditCard.getStatus())) {
            // Process the payment using the service method
            boolean paymentSuccess = service.processCreditCardPayment(user, amount, creditCardNumber, cvv, expiryDate);

            if (paymentSuccess) {
                logger.info("Credit card payment successful for user: {}", email);
                return "Payment of $" + amount + " made successfully.";
            } else {
                logger.info("Credit card payment failed for user: {}", email);
                return "Payment failed. Please check your credit card details and try again.";
            }
        } else {
            logger.info("Credit card is not active for user: {}", email);
            return "Payment failed. Your credit card is not active.";
        }
    }

    @PostMapping("/pay-emi")
    public String payCreditCardEMI(HttpServletRequest request, @RequestBody Map<String, Object> requestData) {
        String email = request.getAttribute("email").toString();
        Users user = service.findUserByUsername(email);
        int userId = user.getId();
        Account account = service.findAccountByUserId(userId);
        int accid = account.getAccid();

        Double emiAmount = Double.valueOf(requestData.get("emiAmount").toString());
        String creditCardNumber = requestData.get("creditcardnumber").toString();
        String cvv = requestData.get("creditcardcvv").toString();
        String expiryDate = requestData.get("expirydate").toString();

        logger.info("Processing credit card EMI payment for user: {}", email);

        // Process the EMI payment using the service method
        boolean paymentSuccess = service.processCreditCardEMIPayment(user, emiAmount, creditCardNumber, cvv, expiryDate, userId, accid);

        if (paymentSuccess) {
            logger.info("Credit card EMI payment successful for user: {}", email);
            return "EMI payment of $" + emiAmount + " made successfully.";
        } else {
            logger.info("Credit card EMI payment failed for user: {}", email);
            return "EMI payment failed. Please check your credit card details and try again.";
        }
    }

    @GetMapping("/calculate-emi")
    public ResponseEntity<Double> calculateEMI(
            @RequestParam("loanAmount") double loanAmount,
            @RequestParam("interestRate") double interestRate,
            @RequestParam("tenureMonths") int tenureMonths
    ) {
        double emiAmount = service.calculateEMIAmount(loanAmount, interestRate, tenureMonths);
        return ResponseEntity.ok(emiAmount);
    }
}
