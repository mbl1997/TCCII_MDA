package br.com.mda.ws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mda.model.Cliente;
import br.com.mda.model.Evolucao;
import br.com.mda.ws.repository.EvolucaoRepository;

@Service
public class EvolucaoService {
	
	@Autowired
	EvolucaoRepository evolucaoRepository;
	
	private List <Evolucao> evolucao = new ArrayList<Evolucao>();

	//Serve para inserir e alterar
	public Evolucao salvar(Evolucao evolucao){
		evolucaoRepository.save(evolucao);
		return evolucao;
	}
	
	public void excluir(Evolucao evolucao){
		evolucaoRepository.delete(evolucao);
	}
	
	public Evolucao buscarPorId(Long id){
		return evolucaoRepository.findOne(id);
		
	}
	
	public 	List<Evolucao> buscarTodos(){
		return evolucaoRepository.findAll();
	}
	
	public 	List<Evolucao> buscarPorPaciente(Long idPaciente){
		return evolucaoRepository.buscarPorPaciente(idPaciente);
	}

}
