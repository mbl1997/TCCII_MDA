package br.com.mda.ws.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.mda.model.Cliente;
import br.com.mda.pojo.CredenciaisCliente;
import br.com.mda.util.Util;
import br.com.mda.ws.service.ClienteService;

@RestController
public class ClienteController {

	@Autowired
	ClienteService clienteService;
	
	
	//Serve para inserir e alterar
		@RequestMapping(method=RequestMethod.POST, value="/salvarClientes", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
		public Cliente salvar(@RequestBody CredenciaisCliente cc) throws Exception{
			
			Cliente clienteCadastrado = new Cliente();
			Cliente cliente = cc.getCliente();
			cliente.setSenha((new BCryptPasswordEncoder().encode(cc.getSenha())).getBytes());
			
			Cliente clienteExiste = this.clienteService.buscarPorId(cc.getCliente().getIdUsuario());
			
				if (cc.getCliente() != null) {
					if (cc.getCliente().getCpf_cnpj() != null && !cc.getCliente().getCpf_cnpj().trim().isEmpty()) {
						try {
							Util.validarCPF(cc.getCliente().getCpf_cnpj());
						} catch (Exception ex) {
							System.out.println("CPF " + cc.getCliente().getCpf_cnpj() + " do cliente é inválido");
							//comentado para defesa do tcc para que caso não tenha conexão com a internet, não quebre o sistema.
							//	SalvarEnviarLogs.gravarArquivo(ex);
							throw new Exception("O CPF é inválido!");
						}
					}
				}
				cc.getCliente().setAtivo(true);
				cc.getCliente().setTipoUsuario("C");
			if(clienteExiste == null){
				cc.getCliente().setDataInclusao(Calendar.getInstance());
			}
			clienteCadastrado = clienteService.salvar(cc.getCliente());
			return clienteCadastrado;
		}
		
		
		@RequestMapping(method=RequestMethod.GET, value="/listarClientes", produces=MediaType.APPLICATION_JSON_VALUE)
		public List<Cliente> listar() {
			List<Cliente> clientes = clienteService.buscarTodos();
			List<Cliente> clientesAtivos = new ArrayList<Cliente>();
			for(Cliente cliente: clientes){
				if(cliente.getAtivo().equals(true)){
					clientesAtivos.add(cliente);
				}
			}
			return (clientesAtivos);
		}
		
		@RequestMapping(method=RequestMethod.POST, value = "/excluirClientes")
		public ResponseEntity<Cliente> excluirCliente(@RequestBody Cliente cliente) {
			
			Cliente clienteEncontrado = clienteService.buscarPorId(cliente.getIdUsuario());
			
			if(clienteEncontrado == null){
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			clienteService.excluir(clienteEncontrado);
			
			return new ResponseEntity<>(HttpStatus.OK);
			
		}
		
		@RequestMapping(method=RequestMethod.GET, value="/listarNovosUsuarios", produces=MediaType.APPLICATION_JSON_VALUE)
		public int listarNovosUsuarios() {
			List<Cliente> clientes = clienteService.listarNovosUsuarios();
			return (clientes.size());
		}

	}