package br.com.mda.ws.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.mda.model.Funcionario;
import br.com.mda.model.Usuario;
import br.com.mda.util.SalvarEnviarLogs;
import br.com.mda.ws.service.UsuarioService;

@RestController
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
private final static Logger logger = Logger.getLogger(FuncionarioController.class);
	
	private static void logMessage(String msg, boolean error) {
    	if(!error && logger.isDebugEnabled()){
    	    logger.debug(msg);
    	}

    	//logs an error message with parameter
    	if (error) {
    		logger.error(msg);
    	}
    }
	
	//Serve para inserir e alterar
	@RequestMapping(method=RequestMethod.POST, value="/salvar", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){
		Usuario usuarioCadastrado = usuarioService.salvar(usuario);
		return new ResponseEntity<>(usuarioCadastrado, HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/listar", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Usuario>> listar() {
		Collection<Usuario> usuarios = usuarioService.buscarTodos();
		return new ResponseEntity<>(usuarios, HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value = "/listarAniversariantesDoMes", 
			method={RequestMethod.GET},
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public List<Usuario> listarAniversariantesDoMes(Principal user) throws Exception {
		logMessage("FuncionarioController.listarAniversariantesDoMes: início", false);
		try {
			
			List<Usuario> lstAniversariantes = 
					usuarioService.listarAniversariantesDoMes(); 
			logMessage("UsuarioController.listarAniversariantesDoMes: fim", false);
			return lstAniversariantes;
		} catch(Exception ex) {
			logMessage("Erro ao buscar aniversariantes: " + ex.getMessage(), true);
			SalvarEnviarLogs.gravarArquivo(ex); 
			throw new Exception();
		}
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/deletar/{id}")
	public ResponseEntity<Usuario> excluirUsuario(@PathVariable Long id) {
		
		Usuario usuarioEncontrado = usuarioService.buscarPorId(id);
		
		if(usuarioEncontrado == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		usuarioService.excluir(usuarioEncontrado);
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	

}
