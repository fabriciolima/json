package main;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface JogoClienteRepository extends CrudRepository<JogoCliente, Long> {
	//List<Jogo> findByDataModificadoGreaterThanEqual(Date data);
	//List<Jogo> findAllByDataModificadoGreaterThanEqual(Date data);
//	@Query("SELECT jc FROM JogoCliente jc WHERE LOWER(p.lastName) = LOWER(:localizacao)")
//    public List<JogoCliente> procura(@Param("localizacao") String localizacao);
}
