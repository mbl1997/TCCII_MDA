package br.com.mda.ws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.mda.model.Evolucao;
@Repository
public interface EvolucaoRepository extends JpaRepository <Evolucao, Long>{

	@Query("SELECT e FROM Evolucao e "
			+ "WHERE e.cliente.idUsuario = ?1 order by data_formatada desc")
	public List<Evolucao> buscarPorPaciente(Long idPaciente);
	
}
