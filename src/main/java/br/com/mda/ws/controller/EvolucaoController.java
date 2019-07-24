package br.com.mda.ws.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.mda.model.Agendamento;
import br.com.mda.model.Cliente;
import br.com.mda.model.Evolucao;
import br.com.mda.model.Funcionario;
import br.com.mda.model.Usuario;
import br.com.mda.util.SalvarEnviarLogs;
import br.com.mda.ws.service.ClienteService;
import br.com.mda.ws.service.EvolucaoService;
import br.com.mda.ws.service.FuncionarioService;
import br.com.mda.ws.service.UsuarioService;

@RestController
public class EvolucaoController {
	
	@Autowired
	EvolucaoService evolucaoService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	FuncionarioService funcionarioService; 
	
	@Autowired
	ClienteService clienteService;

	//Serve para inserir e alterar
			@RequestMapping(method=RequestMethod.POST, value="/salvarevolucoes", consumes=MediaType.APPLICATION_JSON_VALUE)
			public ResponseEntity<Evolucao> salvar(@RequestBody Evolucao evolucao, Principal p){
				evolucao.setFuncionario((Funcionario) usuarioService.buscaPorLogin(p.getName()));
				String data = "dd/MM/yyyy";
				String hora = "HH:mm:ss";
				String data1, hora1;
				
				SimpleDateFormat formata = new SimpleDateFormat(data);
				data1 = formata.format(evolucao.getData());	
				evolucao.setDataFormatada(data1);
				formata = new SimpleDateFormat(hora);
				hora1 = formata.format(evolucao.getHora());
				evolucao.setHoraFormatada(hora1);
				
				
				Evolucao evolucaoCadastrada = evolucaoService.salvar(evolucao);
				
				return new ResponseEntity<>(evolucaoCadastrada, HttpStatus.CREATED);
			}
			
			
	@RequestMapping(method = RequestMethod.GET, value = "/listarevolucoes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Evolucao> listar(Principal user) {

		Funcionario funcionario = new Funcionario();
		Usuario usuario = new Usuario();

		if (user != null) {
			usuario = usuarioService.buscaPorLogin(user.getName());
			if (usuario.getTipoUsuario().equals("F")) {
				funcionario = funcionarioService.buscaPorLogin(user.getName());

				List<Evolucao> lstEvolucoes = this.evolucaoService.buscarTodos();

				System.out.println("EvolucaoController.listarEvolucoes: fim");
				return FormataDataHora(lstEvolucoes);
			} else if (usuario.getTipoUsuario().equals("C")) {
				Cliente cliente;
				cliente = clienteService.buscarPorId(usuario.getIdUsuario());

				List<Evolucao> lstEvolucoes = this.evolucaoService.buscarPorPaciente(cliente.getIdUsuario());


				System.out.println("EvolucaoController.listarEvolucoes: fim");
				return FormataDataHora(lstEvolucoes);

			}

		}
		return null;
	}
			
			public List<Evolucao> FormataDataHora(List <Evolucao> evolucoes){
				String data = "dd/MM/yyyy";
				String hora = "HH:mm:ss";
				String data1, hora1;

				for(Evolucao evolucao : evolucoes){
					
					SimpleDateFormat formata = new SimpleDateFormat(data);
					data1 = formata.format(evolucao.getData());	
					evolucao.setDataFormatada(data1);
					formata = new SimpleDateFormat(hora);
					hora1 = formata.format(evolucao.getHora());
					evolucao.setHoraFormatada(hora1);
				}
				return evolucoes;
			}
			
			@RequestMapping(method=RequestMethod.POST, value = "/excluirevolucoes")
			public ResponseEntity<Evolucao> excluirevolucao(@RequestBody Evolucao evolucao) {
				
				Evolucao evolucaoEncontrado = evolucaoService.buscarPorId(evolucao.getIdEvolucao());
				
				if(evolucaoEncontrado == null){
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
				
				evolucaoService.excluir(evolucaoEncontrado);
				
				return new ResponseEntity<>(HttpStatus.OK);
				
			}
	
}
