package com.axis.controller;

import java.util.ArrayList;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axis.entity.Account;
import com.axis.entity.GiftCard;
import com.axis.entity.GiftCardResponse;
import com.axis.entity.Users;
import com.axis.service.UserDetailsServiceImpl;

@RestController
//@CrossOrigin(origins = "http://localhost:3000",allowCredentials="true")
@RequestMapping("/giftcard/customer")
//@CrossOrigin("http://localhost:3000/")
public class CustomerController {

    @Autowired
    private UserDetailsServiceImpl service;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @PostMapping("/buy-giftcard")
    public String buyGiftCard(HttpServletRequest request, @RequestBody Map<String, Object> requestData) {
        String username = request.getAttribute("username").toString();
        Users user = service.findUser(username);
        Account account = service.findAccount(user.getId());

        String giftcardname = String.valueOf(requestData.get("giftcardname").toString());
        String recipientname = String.valueOf(requestData.get("recipientname").toString());
        String recipientemail = String.valueOf(requestData.get("recipientemail").toString());
        double giftcardamount = Double.valueOf(requestData.get("giftcardamount").toString());

        logger.info("Processing gift card purchase for user: {}", username);

        GiftCard giftCard = new GiftCard(giftcardname, recipientname, recipientemail, giftcardamount, account);
        service.buyGiftCard(giftCard);

        // Sending email
        String senderEmail = "dummyahealthcare22@gmail.com";
        String senderPassword = "gpcuphxksiekrqrp"; // Replace with the actual password
        String recipientEmail = recipientemail;
        String subject = "Gift Card Purchase Confirmation";
        String messageBody = "Dear " + recipientname + ",\n\n" + "You have received a gift card .\n\n" + "Gift Card Name: "
                + giftcardname + "\n" + "Amount: " + giftcardamount + "\n\n" + "Enjoy your gift!\n\n" + "Regards,\n"
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
            return "You've successfully purchased " + giftcardname
                    + ". An email confirmation has been sent to the recipient.";
        } catch (MessagingException e) {
            logger.error("Error sending the confirmation email for gift card purchase: {}", e.getMessage());
            return "You've successfully purchased " + giftcardname
                    + ", but there was an error sending the confirmation email.";
        }
    }

    @GetMapping("/my-giftcards-purchased")
    public List<GiftCardResponse> myGiftCardsPurchased(HttpServletRequest request) {
        String username = request.getAttribute("username").toString();
        Users user = service.findUser(username);
        Account account = service.findAccount(user.getId());

        logger.info("Fetching all purchased gift cards for user: {}", username);

        List<GiftCard> lGiftCard = service.showAllMyGiftCardsPurchased(account.getAccid());

        List<GiftCardResponse> lGiftCardResponse = new ArrayList<>();
        for (GiftCard g : lGiftCard) {
            GiftCardResponse gcr = new GiftCardResponse(g.getGiftcardname(), g.getRecipientname(),
                    g.getRecipientemail(), g.getGiftcardamount());
            lGiftCardResponse.add(gcr);
        }

        return lGiftCardResponse;
    }

}
