
package com.workpal.util;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    public static void envoyerEmail(String toEmail, String temporaryPassword) {
        // Sender's email and credentials
        final String expediteur = "k130003188@gmail.com";
        final String password = "xncjvprqsmfnimee"; // Gmail App Password (not your account password)

        // Set up the SMTP server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Create session with authentication
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(expediteur, password);
            }
        });

        try {
            // Create the email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(expediteur));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Réinitialisation de votre mot de passe");
            message.setText("Votre mot de passe temporaire est : " + temporaryPassword + "\nVeuillez le changer après vous être connecté.");

            // Send the email
            Transport.send(message);
            System.out.println("E-mail envoyé avec succès à " + toEmail);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public static void envoyerConfirmationReservation(String toEmail, String spaceName, String startDateTime, String endDateTime) {
        final String expediteur = "k130003188@gmail.com";
        final String password = "xncjvprqsmfnimee";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(expediteur, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(expediteur));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Confirmation de Réservation");
            message.setText("Votre réservation pour l'espace '" + spaceName + "' a été confirmée.\n" +
                    "Heure de début : " + startDateTime + "\n" +
                    "Heure de fin : " + endDateTime + "\nMerci pour votre réservation.");

            Transport.send(message);
            System.out.println("E-mail de confirmation envoyé avec succès à " + toEmail);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
