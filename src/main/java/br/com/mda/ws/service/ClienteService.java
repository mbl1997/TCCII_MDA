package br.com.mda.ws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mda.model.Cliente;
import br.com.mda.ws.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	private List <Cliente> clientes = new ArrayList<Cliente>();

	//Serve para inserir e alterar
	public Cliente salvar(Cliente cliente){
		clienteRepository.save(cliente);
		return cliente;
	}
	
	public void excluir(Cliente cliente){
		cliente.setAtivo(false);
		clienteRepository.save(cliente);
	}
	
	public Cliente buscarPorId(Long id){
		return clienteRepository.findOne(id);
		
	}
	
	public 	List<Cliente> buscarTodos(){
		return clienteRepository.listarPacientes();
	}
	
	public 	List<Cliente> listarNovosUsuarios(){
		return clienteRepository.listarNovosUsuarios();
	}

}
