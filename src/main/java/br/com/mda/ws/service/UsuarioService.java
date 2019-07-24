package br.com.mda.ws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.mda.model.Funcionario;
import br.com.mda.model.Usuario;
import br.com.mda.ws.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private List <Usuario> usuarios = new ArrayList<Usuario>();

	//Serve para inserir e alterar
	public Usuario salvar(Usuario usuario){
		usuarioRepository.save(usuario);
		return usuario;
	}
	
	public List<Usuario> listarAniversariantesDoMes() {
		return usuarioRepository.listarAniversariantesDoMes();
	}
	
	public void excluir(Usuario usuario){
		usuarioRepository.delete(usuario);
	}
	
	public Usuario buscarPorId(Long id){
		return usuarioRepository.findOne(id);
		
	}
	
	public Usuario buscaPorLogin(String login) {
		return usuarioRepository.findByLogin(login);
	}
	
	public 	List<Usuario> buscarTodos(){
		return usuarioRepository.findAll();
	}
	

}
