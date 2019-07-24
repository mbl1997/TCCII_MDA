package br.com.mda.ws.controller;

import java.security.Principal;
import java.util.Date;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.mda.model.Funcionario;
import br.com.mda.model.Usuario;
import br.com.mda.util.LoginResponse;
import br.com.mda.ws.service.FuncionarioService;
import br.com.mda.ws.service.LoginService;
import br.com.mda.ws.service.UsuarioService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private FuncionarioService funcionarioService;

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}
	
//	public Funcionario usuarioLogado() {
//	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	String us = auth.getName();
//	
//	return funcionario;
//	}	
	
	@RequestMapping(method=RequestMethod.GET, value="/userLogado")
	public ResponseEntity<Usuario> userLogado(@PathParam("{nome}") String nome){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String user = auth.getName();
		Usuario usuario = usuarioService.buscaPorLogin(user);
	//	Funcionario funcionario = funcionarioService.buscaPorLogin(user);
		return new ResponseEntity<>(usuario , HttpStatus.CREATED);
	}
	

	@RequestMapping("/clientes")
	public String irParaClientes() {
		return "pages/cadastroCliente.html";
	}

}
