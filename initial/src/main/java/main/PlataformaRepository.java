package main;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PlataformaRepository extends CrudRepository<Plataforma, Long> {
	List<Plataforma> findByDataModificadoGreaterThanEqual(Date data);
	//List<Plataforma> findAllByDataModificadoGreaterThanEqual(Date data);

	Plataforma findById(Long decode);
}
