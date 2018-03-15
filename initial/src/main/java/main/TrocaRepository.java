package main;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TrocaRepository extends CrudRepository<Troca, Long> {

	List<Troca> findByInteresse(JogoCliente jc);
	List<Troca> findByProposta(JogoCliente jc);
	//List<Troca> findByInteresseProposta(JogoCliente interesse, JogoCliente proposta);
	List<Troca> findByInteresseAndProposta(JogoCliente jcInteresse, JogoCliente jc);
	
	
	
	//List<Jogo> findByDataModificadoGreaterThanEqual(Date data);
	//List<Jogo> findAllByDataModificadoGreaterThanEqual(Date data);
//	@Query("SELECT jc FROM JogoCliente jc WHERE LOWER(p.lastName) = LOWER(:localizacao)")
//    public List<JogoCliente> procura(@Param("localizacao") String localizacao);
}
