package main;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;

public abstract class JogoDAO implements JogoRepository{
	@PersistenceContext
	private static EntityManager entityManager;
	
	public static List<Jogo> procura(){
		List<Jogo> retorno = new ArrayList<Jogo>();
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    
		CriteriaQuery<Jogo> query = builder.createQuery(Jogo.class);
        Root<Jogo> r = query.from(Jogo.class);
        
        Predicate predicate = builder.conjunction();
 

//        predicate = builder.and(predicate, 
//                  builder.greaterThanOrEqualTo(r.get(param.getKey()), 
//                  param.getValue().toString()));
//        
//	} else if (param.getOperation().equalsIgnoreCase("<")) {
//                predicate = builder.and(predicate, 
//                  builder.lessThanOrEqualTo(r.get(param.getKey()), 
//                  param.getValue().toString()));
//            } else if (param.getOperation().equalsIgnoreCase(":")) {
//                if (r.get(param.getKey()).getJavaType() == String.class) {
//                    predicate = builder.and(predicate, 
//                      builder.like(r.get(param.getKey()), 
//                      "%" + param.getValue() + "%"));
//                } else {
//                    predicate = builder.and(predicate, 
//                      builder.equal(r.get(param.getKey()), param.getValue()));
//                }
//            }
//        }
        query.where(predicate);
 
        retorno = entityManager.createQuery(query).getResultList();
		
		return retorno;
		
	}
	
	
}
