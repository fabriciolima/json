package main.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import main.entidade.JogoCliente;
import main.entidade.Troca;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TrocaRepository extends CrudRepository<Troca, Long> {

	@Query("select t from Troca t where t.idClienteInteresse = :interesse or t.idClienteProposta= :proposta")
	 List<Troca[]>  findByIdClienteInteresseOrIdClienteProposta(@Param("interesse") String interesse,
	                                 @Param("proposta") String proposta);	 
	List<Troca> findByInteresse(JogoCliente jc);
	List<Troca> findByProposta(JogoCliente jc);
	//List<Troca> findByInteresseProposta(JogoCliente interesse, JogoCliente proposta);
	List<Troca> findByInteresseAndProposta(JogoCliente jcInteresse, JogoCliente jc);
	Troca findById(Long idTroca);
//	List<Troca> findByClienteInteresseOrClientePropostaByIdChatNotNull(Cliente cliente, Cliente cliente2);
	List<Troca> findAllByIdClienteInteresseOrIdClienteProposta(String idCliente, String idCliente2);
	List<Troca> findByIdClienteInteresse(String idCliente);
	List<Troca> findByIdClienteInteresse(Long parseLong);
	List<Troca> findByIdClienteProposta(Long parseLong);
//	List<Troca> findByIdClienteInteresseOrByIdClienteProposta(Long parseLong, Long parseLong2);
	
	
	


}
