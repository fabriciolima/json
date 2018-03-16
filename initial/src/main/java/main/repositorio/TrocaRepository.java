package main.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import main.entidade.Cliente;
import main.entidade.JogoCliente;
import main.entidade.Troca;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TrocaRepository extends CrudRepository<Troca, Long> {

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
	
	
	
	//List<Jogo> findByDataModificadoGreaterThanEqual(Date data);
	//List<Jogo> findAllByDataModificadoGreaterThanEqual(Date data);
//	@Query("SELECT jc FROM JogoCliente jc WHERE LOWER(p.lastName) = LOWER(:localizacao)")
//    public List<JogoCliente> procura(@Param("localizacao") String localizacao);
}
