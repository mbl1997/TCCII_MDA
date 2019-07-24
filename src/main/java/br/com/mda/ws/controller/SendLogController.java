package br.com.mda.ws.controller;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.web.bind.annotation.RestController;

import br.com.mda.model.Email;
import br.com.mda.util.SalvarEnviarLogs;


@RestController
public class SendLogController {
	
		
		private static String corpo;
		private static String host;
		private static Properties properties;
		
		
		public SendLogController(Email email) {
			this.host = "smtp.gmail.com";
			this.properties = System.getProperties();
			//Aqui ele monta o email
			this.mountMail(email);
			//Aqui ele inicializa a api de email e faz o envio.
			this.sendMail(email);
		}

		/**
		 * Send mail from tccodontofacil@gmail.com
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
				//ISSO é o titulo do EMAIL
				message.setSubject("[Contato-OdontoFacil]Agendamento de Consulta");
				message.setContent(this.corpo, "text/html");
				MimeBodyPart mbp2 = new MimeBodyPart();  
                FileDataSource fds = new FileDataSource("C:\\logsmda\\erros.log");        
                mbp2.setDataHandler(new DataHandler(fds));        
                mbp2.setFileName(fds.getName());       
                Multipart mp = new MimeMultipart();        
                mp.addBodyPart(mbp2);   
                message.setContent(mp);
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
