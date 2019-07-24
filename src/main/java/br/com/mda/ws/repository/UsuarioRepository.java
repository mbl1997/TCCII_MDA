package br.com.mda.ws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.mda.model.Funcionario;
import br.com.mda.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	@Query("Select u from Usuario u where nome_Completo = ?1")
	public Usuario autenticar(String nome);
	
	@Query("SELECT u FROM Usuario u "
			+ "WHERE MONTH(u.dataNascimento) = MONTH(NOW()) "
			+ "ORDER BY DAY(u.dataNascimento) ASC")
	public List<Usuario> listarAniversariantesDoMes();
	
	@Query("SELECT u FROM Usuario u "
			+ "WHERE u.login = ?1 ")
	public Usuario findByLogin(String login);
}
