package main.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import main.entidade.Troca;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TrocaRepositoryJPA extends JpaRepository<Troca, Long> {

	 @Query("select t from Troca t where t.idClienteInteresse = :interesse or t.idClienteProposta= :proposta")
	 List<Troca[]>  findByIdClienteInteresseOrIdClienteProposta(@Param("interesse") String interesse,
	                                 @Param("proposta") String proposta);
	 
	

}
