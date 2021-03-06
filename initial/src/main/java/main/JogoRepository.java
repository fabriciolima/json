package main;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import main.entidade.Jogo;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface JogoRepository extends CrudRepository<Jogo, Long> {
//	List<Jogo> findByDataModificadoGreaterThanEqual(Date data);
	//List<Jogo> findAllByDataModificadoGreaterThanEqual(Date data);
	List<Jogo> findByNomeLike(String Nome);
	Jogo findByNome(String Nome);
	Page<Jogo> findAll(Pageable pageable);
//	Jogo findByUid(String uid);
	Jogo findById(Long idJogo);
	List<Jogo> findFirst3ByNomeContainingIgnoreCaseOrderByNome(String u);
	Jogo findFirst1ByNomeContainingIgnoreCase(String nomeJogo);
}
