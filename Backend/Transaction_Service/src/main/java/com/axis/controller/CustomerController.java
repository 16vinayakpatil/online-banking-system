package com.axis.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.axis.entity.Account;
import com.axis.entity.Transaction;
import com.axis.entity.TransactionResponse;
import com.axis.entity.Users;
import com.axis.pdf.TransactionPDFExporter;
import com.axis.repository.CustomerTransactionRepository;
import com.axis.service.UserDetailsServiceImpl;
import com.lowagie.text.DocumentException;

@RestController
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/transaction/customer")
public class CustomerController {

    @Autowired
    private UserDetailsServiceImpl service;

    @Autowired
    private CustomerTransactionRepository repo;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping("/show-all-my-transactions")
    public List<TransactionResponse> showAllMyTransactions(HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        logger.info("Fetching all transactions for user: {}", username);

        int userid = service.findUserIdByUsername(username);
        int accid = service.findAccountIdByUserId(userid);
        List<Transaction> tList = service.fetchTransactionsForAccId(accid);

        List<TransactionResponse> trList = new ArrayList<>();
        for (Transaction t : tList)
            trList.add(new TransactionResponse(t.getTransactionid(), t.getDatetime(),t.getAccount().getAccid(),t.getAccount().getAccno(), t.getAmount(), t.getDescription(), t.getTransactiontype()));

        return trList;
    }

    @GetMapping("/filterbydate")
    public ResponseEntity<List<Transaction>> getTransactionByDate(@RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        logger.info("Fetching transactions for date range. Start: {}, End: {}", start, end);

        return new ResponseEntity<>(repo.findByDatetimeBetween(start, end), HttpStatus.OK);
    }

    @GetMapping("/export")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        logger.info("Exporting all transactions to PDF.");

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transaction_" + currentDateTime + ".pdf";

        response.setHeader(headerKey, headerValue);
        List<Transaction> fetchAllTransactions = service.fetchAllTransactions();

        TransactionPDFExporter exporter = new TransactionPDFExporter(fetchAllTransactions);
        exporter.export(response);
    }

    @GetMapping("/exportfilter")
    public void exportFilteredToPDF(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                    @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                    HttpServletResponse response) throws DocumentException, IOException {
        logger.info("Exporting filtered transactions to PDF. Start: {}, End: {}", start, end);

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transaction_" + currentDateTime + ".pdf";

        response.setHeader(headerKey, headerValue);
        List<Transaction> filteredTransactions = repo.findByDatetimeBetween(start, end);

        TransactionPDFExporter exporter = new TransactionPDFExporter(filteredTransactions);
        exporter.export(response);
    }

    @PutMapping("/deposit")
    public String deposit(HttpServletRequest request, @RequestBody Map<String, Object> requestData) {
        String username = request.getAttribute("username").toString();
        logger.info("Deposit request for user: {}", username);

        double amount = Double.valueOf(requestData.get("amount").toString());
        int userid = service.findUserIdByUsername(username);
        Account account = service.findAccountByUserId(userid);

        if (account.getStatus().equals("PENDING")) {
            logger.info("Deposit failed for user: {}. Account status: PENDING", username);
            return "Sorry, your account is not active yet. You can't perform transactions.";
        }

        if (amount < 0) {
            logger.info("Deposit failed for user: {}. Negative amount entered.", username);
            return "Sorry, you entered a negative amount. Please enter a positive amount.";
        }

        double newBalance = account.getBalance() + amount;
        service.deposit(userid, newBalance, amount);

        // Sending email
        logger.info("Sending confirmation email for deposit to user: {}", username);
        String senderEmail = "dummyahealthcare22@gmail.com";
        String senderPassword = "gpcuphxksiekrqrp"; // Replace with the actual password

        Users recipientUser = service.findUser(username);
        String recipientEmail = recipientUser.getEmail(); // Assuming the email is stored in the 'email' field of the Users entity

        String subject = "Deposit Confirmation";
        String messageBody = "Amount " + amount + " is successfully deposited.";

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
            logger.info("Deposit confirmation email sent to user: {}", username);
            return "Amount is successfully deposited. Confirmation email has been sent.";
        } catch (MessagingException e) {
            logger.error("Deposit confirmation email failed for user: {}", username);
            return "Amount is successfully deposited, but there was an error sending the confirmation email.";
        }
    }

    @PutMapping("/withdraw")
    public String withdraw(HttpServletRequest request, @RequestBody Map<String, Object> requestData) {
        String username = request.getAttribute("username").toString();
        logger.info("Withdrawal request for user: {}", username);

        double amount = Double.valueOf(requestData.get("amount").toString());
        int userid = service.findUserIdByUsername(username);
        Account account = service.findAccountByUserId(userid);

        if (account.getStatus().equals("PENDING")) {
            logger.info("Withdrawal failed for user: {}. Account status: PENDING", username);
            return "Sorry, your account is not active yet. You can't perform transactions.";
        }

        if (amount < 0) {
            logger.info("Withdrawal failed for user: {}. Negative amount entered.", username);
            return "Sorry, you entered a negative amount. Please enter a positive amount.";
        }

        if (account.getBalance() - amount < 10000) {
            logger.info("Withdrawal failed for user: {}. Insufficient balance.", username);
            return "You don't have sufficient balance. Minimum balance should be 10000.";
        }

        service.withdraw(userid, account.getBalance() - amount, amount);

        // Sending email
        logger.info("Sending confirmation email for withdrawal to user: {}", username);
        String senderEmail = "dummyahealthcare22@gmail.com";
        String senderPassword = "gpcuphxksiekrqrp"; // Replace with the actual password

        Users recipientUser = service.findUser(username);
        String recipientEmail = recipientUser.getEmail(); // Assuming the email is stored in the 'email' field of the Users entity

        String subject = "Withdrawal Confirmation";
        String messageBody = "Amount " + amount + " is successfully withdrawn.";

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
            logger.info("Withdrawal confirmation email sent to user: {}", username);
            return "Amount is successfully withdrawn. Confirmation email has been sent.";
        } catch (MessagingException e) {
            logger.error("Withdrawal confirmation email failed for user: {}", username);
            return "Amount is successfully withdrawn, but there was an error sending the confirmation email.";
        }
    }

    @PutMapping("/bank-transfer")
    public String bankTransfer(HttpServletRequest request, @RequestBody Map<String, Object> requestData) {
        String username = request.getAttribute("username").toString();
        logger.info("Bank transfer request from user: {}", username);

        int userIdSender = service.findUserIdByUsername(username);
        Users senderuser=service.findUserById(userIdSender);
        Account accountSender = service.findAccountByUserId(userIdSender);

        if (accountSender.getStatus().equals("PENDING")) {
            logger.info("Bank transfer failed for user: {}. Account status: PENDING", username);
            return "Sorry, your account is not active yet. You can't perform transactions.";
        }

        String accNoReceiver = String.valueOf(requestData.get("accno").toString());
        String ifscCodeReceiver = String.valueOf(requestData.get("ifsccode").toString());
        String usernameReceiver = String.valueOf(requestData.get("username").toString());
        double amount = Double.valueOf(requestData.get("amount").toString());

        int userid=service.findUseridByAccNo(accNoReceiver);
      
        Users receiverUser = service.findUserById(userid);
        if (receiverUser == null) {
            logger.info("Bank transfer failed for user: {}. Receiver user not found.", username);
            return "Sorry, the receiver's account does not exist.";
        }

        Account accountReceiver = service.findAccountByUserId(receiverUser.getId());

        if (!accountReceiver.getAccno().equals(accNoReceiver) || !accountReceiver.getIfsccode().equals(ifscCodeReceiver)) {
            logger.info("Bank transfer failed for user: {}. Invalid receiver account details entered.", username);
            return "Sorry, you entered invalid receiver account details. Please check the account number and IFSC code.";
        }

        if (accountReceiver.getStatus().equals("PENDING")) {
            logger.info("Bank transfer failed for user: {}. Receiver account status: PENDING", username);
            return "Sorry, the receiver's account is not active yet. You can't perform transactions.";
        }

        if (amount < 0) {
            logger.info("Bank transfer failed for user: {}. Negative amount entered.", username);
            return "Sorry, you entered a negative amount. Please enter a positive amount.";
        }

        if (accountSender.getBalance() - amount < 10000) {
            logger.info("Bank transfer failed for user: {}. Insufficient balance.", username);
            return "You don't have sufficient balance. Minimum balance should be 10000.";
        }

        service.bankTransferWithdraw(userIdSender, accountSender.getBalance() - amount, amount);
        service.bankTransferDeposit(receiverUser.getId(), accountReceiver.getBalance() + amount, amount);

        // Sending email
        logger.info("Sending confirmation email for bank transfer to user: {}", username);
        String senderEmail = "dummyahealthcare22@gmail.com";
        String senderPassword = "gpcuphxksiekrqrp"; // Replace with the actual password

        String recipientEmail = senderuser.getEmail(); // Replace with the recipient's email address

        String subject = "Bank Transfer Confirmation";
        String messageBody = "Amount " + amount + " is successfully transferred to the account: " + accNoReceiver;

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
            logger.info("Bank transfer confirmation email sent to user: {}", username);
            return "Amount is successfully transferred. Confirmation email has been sent.";
        } catch (MessagingException e) {
            logger.error("Bank transfer confirmation email failed for user: {}", username);
            return "Amount is successfully transferred, but there was an error sending the confirmation email.";
        }
    }
}
