package br.com.mda.ws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mda.model.Funcionario;
import br.com.mda.ws.repository.FuncionarioRepository;

@Service
public class FuncionarioService {
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	private List <Funcionario> funcionarios = new ArrayList<Funcionario>();

	//Serve para inserir e alterar
	public Funcionario salvar(Funcionario funcionario){
		funcionarioRepository.save(funcionario);
		return funcionario;
	}
	
	public 	List<Funcionario> buscarFuncionarios(){
		return funcionarioRepository.buscarFuncionarios();
	}
	
	public void excluir(Funcionario funcionario){
		funcionario.setAtivo(false);
		funcionarioRepository.save(funcionario);
	}
	
	public Funcionario buscarPorId(Long id){
		return funcionarioRepository.findOne(id);
	}
	
	public Funcionario buscaPorLogin(String login) {
		return funcionarioRepository.findByLogin(login);
	}
	
	public Long proximoId() {
		return funcionarioRepository.nextId();
	}
	
	public 	List<Funcionario> buscarTodos(){
		return funcionarioRepository.findAll();
	}
	
	public 	List<Funcionario> buscarProfissionais(){
		return funcionarioRepository.buscarProfissionais();
	}

}
