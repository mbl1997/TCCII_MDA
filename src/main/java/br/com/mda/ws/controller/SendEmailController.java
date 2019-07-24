package br.com.mda.ws.controller;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RestController;

import br.com.mda.model.Email;
import br.com.mda.util.SalvarEnviarLogs;

@RestController
public class SendEmailController {
	
	private static String corpo;
	private static String host;
	private static Properties properties;
	
	
	public SendEmailController(Email email) {
		this.host = "smtp.gmail.com";
		this.properties = System.getProperties();
		//Aqui ele monta o email
		this.mountMail(email);
		//Aqui ele inicializa a api de email e faz o envio.
		this.sendMail(email);
	}

	/**
	 * Send mail from sistema.mda.tcc@gmail.com
	 */
	private void sendMail(Email email){
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.port", "587");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		Session session = Session.getInstance(properties,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(email.getFrom(), email.getPass());
					}
				  });
		try {
			MimeMessage message = new MimeMessage(session);
			if(email.getFrom() != null){
			message.setFrom(new InternetAddress(email.getFrom()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(email.getCc()));
			//ISSO é o titulo do EMAIL
			message.setSubject("[Contato-MDA] Agendamento de Consulta");
			message.setContent(this.corpo, "text/html");

			Transport.send(message);
			}
		} catch (MessagingException e) {
			e.printStackTrace();
			SalvarEnviarLogs.gravarArquivo(e);
		}
	}
	
	private void mountMail(Email email){
		this.corpo = email.getEmailFormatado();
	}
}