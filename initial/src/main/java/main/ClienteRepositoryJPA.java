package main;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vividsolutions.jts.geom.Geometry;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete 

public interface ClienteRepositoryJPA extends JpaRepository<Cliente, Long> {
	@Query("SELECT c FROM Cliente c WHERE c.nome like ?1%")
    List<Cliente> procura(String nome);
	
	@Query("SELECT c FROM Cliente c order by st_distance(?1,localizacao)")
    List<Cliente> findByLocalizacao(Geometry localizacao);
	
	//@Query(value = "SELECT c.id, localizacao, st_distance(POINT(-30,-11),localizacao) as dist  FROM cliente c order by st_distance(?1,localizacao)", nativeQuery = true)
	@Query(value = "SELECT c.nome nomecliente, c.id idcliente, localizacao, j.id as idjogo, j.nome as nomejogo, p.id idplataforma, p.nome nomeplataforma,"
			+"	TRUNCATE(st_distance_sphere(?,localizacao)/1000,0) as dist, jc.estado_do_jogo estadojogo,jc.id idjogocliente " 
			+"FROM cliente c, jogo j, plataforma p ,jogo_cliente jc "
			+"WHERE c.id = jc.cliente_id "
			+"and j.id = jc.jogo_id "
			+"and p.id = jc.plataforma_id "
			+"order by dist \n#pageable\n ", 
			countQuery = "SELECT count(*) from cliente\n",
			nativeQuery = true)	
    List<Object[]> procuraJogosPerto(Geometry localizacao, Pageable pageable);

	//	select POINT(-30,-10), localizacao, st_distance(POINT(-30,-11),localizacao) as dist
//	FROM jogos.cliente
//	order by dist
}
