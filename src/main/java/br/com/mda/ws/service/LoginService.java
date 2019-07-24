package br.com.mda.ws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mda.model.Usuario;
import br.com.mda.ws.repository.UsuarioRepository;
@Service
public class LoginService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario autenticar(String nome){
		Usuario usuarioAutenticado = usuarioRepository.autenticar(nome);
		return usuarioAutenticado;
	}

}
