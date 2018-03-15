package main;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
	//Optional<Cliente> findById(Long id);
	Cliente findByUid(String uid);
	Cliente findById(Long id);
	//Cliente findBykjlOrderByLocalizacao(Long id);
	
	
	
//	select POINT(-30,-10), localizacao, st_distance(POINT(-30,-11),localizacao) as dist
//	FROM jogos.cliente
//	order by dist
}
