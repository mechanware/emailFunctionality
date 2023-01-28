package org.geekster.email;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailTemplate {
    public static void main(String[] args)  {

        String fromAdd= "nrjsrivastav9@gmail.com";
        String toAdd= "nrjsrivastav100@yahoo.com";
        String ccAdd= "avatiraj1000@gmail.com";
        String body= "Welcome to Geekster Email Demo Session !";
        try {
            sendMailWithoutAttachment(fromAdd,toAdd,ccAdd,body);
            //sendMailWithAttachment(fromAdd,toAdd,ccAdd,body);

        }catch (MessagingException e) {
            e.printStackTrace();
        }
        /*catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public static void sendMailWithoutAttachment(String fromAddress,String toAddress,String ccAddress,String messageBody) throws MessagingException {
        Session session = getSession();
        //2. Compose the mail
        //addFrom & setFrom
        MimeMessage message = new MimeMessage(session);
        message.setFrom("noreply@gmail.com");
        message.addRecipients(Message.RecipientType.TO,toAddress);
        message.addRecipients(Message.RecipientType.CC,ccAddress);
        message.setSubject("Geekster Email Demo");
        //setting body
        message.setText(messageBody);

        //3. Send the mail
        Transport.send(message);
        System.out.println("Email Send successfully ....");

    }
    private static Session getSession(){
        Properties properties = System.getProperties();
         /*  We need below properties for establishing connection with email service provider --Gmail
        1. host name
        2. port number
        3. ssl level
        4. authentication param */

        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");

        //1. Creating session for establishing connection with Gmail server
       return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("nrjsrivastav9@gmail.com",Constant.PASSWORD);
            }
        });
        //.return session;
    }

    public static void sendMailWithAttachment(String fromAddress,String toAddress,String ccAddress,String messageBody) throws MessagingException, IOException {
        Session session = getSession();
        //2. Compose the mail
        //addFrom & setFrom
        MimeMessage message = new MimeMessage(session);
        message.setFrom("noreply@gmail.com");
        message.addRecipients(Message.RecipientType.TO,toAddress);
        message.addRecipients(Message.RecipientType.CC,ccAddress);
        message.setSubject("Geekster Email Demo with attachment");

        MimeMultipart mimeMultipart = new MimeMultipart();
        //for body test
        MimeBodyPart bodyText = new MimeBodyPart();
        bodyText.setText(messageBody);
        //for file
        MimeBodyPart bodyAttachment = new MimeBodyPart();
        //Setting body with Attachment
        String path = "C://Users//nrjsr//OneDrive//Documents//Vacantionpedia.png";
        File file = new File(path);
        bodyAttachment.attachFile(file);

        //Setting body part to multipart object
        mimeMultipart.addBodyPart(bodyText);
        mimeMultipart.addBodyPart(bodyAttachment);

        message.setContent(mimeMultipart);

        // 3. Send the mail
        Transport.send(message);

        System.out.println("Email sent successfully...");

    }
}