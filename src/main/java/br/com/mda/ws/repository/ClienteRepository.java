package br.com.mda.ws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.mda.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository <Cliente, Long>{

			@Query("SELECT u FROM Usuario u WHERE "
					+ "MONTH(data_inclusao) = MONTH(now()) and dtype = 'Cliente'")
			public List<Cliente> listarNovosUsuarios();
			
			@Query("SELECT u FROM Usuario u WHERE "
					+ "tipo_usuario = 'C'")
			public List<Cliente> listarPacientes();
	
}
