package br.com.mda.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mda.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
