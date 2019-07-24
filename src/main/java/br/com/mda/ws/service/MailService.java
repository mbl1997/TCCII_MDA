package br.com.mda.ws.service;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.mda.model.Email;
import br.com.mda.ws.controller.SendEmailController;

/**
 * WebService to send Mail
 * @author Paulo Henrique S. Faria
 */

public class MailService {

	private Logger logger = Logger.getLogger("cloud");
	
	@RequestMapping(method=RequestMethod.POST, value="/contact", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void sendMail(Email email) {
		logger.info("WebService 'mail/contact'");
		
		email.setTo("contato@keepupload.com");
		email.setFrom("contato@keepupload.com");
		email.setPass("SENHA");
		email.setEmailFormatado("<h2 style=\"text-align: left;\">Nome: "+email.getNome()+"</h2>"
								+"<h2 style=\"text-align: left;\">Email: "+email.getEmail()+"</h2>"
								+"<h2 style=\"text-align: center;\">Texto&nbsp;</h2>"
								+"<h2 style=\"text-align: center;\">"+email.getTexto()+"</h2>");
		
//		SendEmailController sendMail = new SendEmailController(email);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/sendMail", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void sendMailFromUpload(Email email) {
		
		logger.info("WebService 'mail/sendMail'");
		email.setFrom("noreply@keepupload.com");
		email.setPass("SENHA");
		email.setEmailFormatado("<h2 style=\"text-align: center;\">Ol&aacute;!</h2>"
				+"<p>&nbsp;</p>"
				+"<h3 style=\"text-align: center;\">Voc&ecirc; acaba de criar um reposit&oacute;rio no servi&ccedil;o KeepUpload.</h3>"
				+"<h3 style=\"text-align: center;\">Abaixo o link para acessar seu diret&oacute;rio.</h3>"
				+"<h3 style=\"text-align: center;\"><span style=\"text-decoration: underline;\">http://keepupload.com/folder.html?key="+email.getUrl()+"</span></h3>"
				+"<h3 style=\"text-align: center;\">A equipe KeepUpload agradece por utilizar nosso servi&ccedil;o.</h3>"
				+"<p style=\"text-align: center;\">&nbsp;</p>"
				+"<h3 style=\"text-align: center;\"><strong>KeepUpload.com</strong></h3>");
			
//		SendEmailController sendMail = new SendEmailController(email);
	}
}