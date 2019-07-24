package br.com.mda.ws.controller;

import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import br.com.mda.model.Agendamento;

@RestController
public class SmsClientController {

	// Find your Account Sid and Token at twilio.com/user/account
	public static final String ACCOUNT_SID = "ACc97ab339262fdb10b50a65681b5e7ce7";
	public static final String AUTH_TOKEN = "78c6ee22b4c8e35bfeda8c88690221dc";
	

	public void EnviaSMS(Agendamento agendamento) {
		// Initialize the Twilio client.
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

	    // Declare To and From numbers and the Body of the SMS message
	    PhoneNumber to = new PhoneNumber("+"+agendamento.getCliente().getTelefonePrincipal()); // Replace with a valid phone number for your account.
	    PhoneNumber from = new PhoneNumber("+17273121425"); // Replace with a valid phone number for your account.
	    String body = "Consulta agendada para "+ agendamento.getStart().getTime();

	    // Create a Message creator passing From, To and Body values
	    // then send the SMS message by calling the create() method
	    Message sms = Message.creator(to, from, body).create();
	    
	    System.out.println(sms.getSid());
	}
	
}