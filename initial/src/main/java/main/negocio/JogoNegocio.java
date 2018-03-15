package main.negocio;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;

import main.FireBaseDB;
import main.JogoRepository;
import main.entidade.Jogo;

@Service
public class JogoNegocio {

	
	//@Autowired private JogoRepository jogoRepository;
	
	
	public Jogo getJogo(Firestore db, JogoRepository jogoRepository, String nomeJogo, String nomePesquisa) throws Exception {
	
		Jogo jogoRetorno=null;
		if(db==null) {
			FireBaseDB fire = new FireBaseDB();
			db = fire.getDb();
		}
		
		ApiFuture<QuerySnapshot> snapshot = db.collection("jogo").whereEqualTo("nomepesquisa", nomePesquisa).limit(1).get();
		Collection<DocumentSnapshot> listaJogo = snapshot.get().getDocuments();
		
		if(listaJogo.isEmpty()) {//jogo nao existe no google
			Map<String, Object> jogo = new HashMap<>();
			jogo.put("nomepesquisa",nomePesquisa );
			jogo.put("nome", nomeJogo);
			ApiFuture<DocumentReference> jogoRef = db.collection("jogo").add(jogo);
			System.out.println("novojogo ID: " + jogoRef.get().getId());
			String uidJogo = jogoRef.get().getId();
			
			jogoRetorno = jogoRepository.findByUid(uidJogo);
			
			if(jogoRetorno==null) {
				jogoRetorno = new Jogo();
				jogoRetorno.setNome(nomeJogo);
				jogoRetorno.setNomePesquisa(nomePesquisa);
				jogoRetorno.setUid(uidJogo);
				jogoRetorno.setDataModificado(new Date());
				jogoRepository.save(jogoRetorno);
			}
			
		} else			
			for (DocumentSnapshot jogo : listaJogo) {
				String uidJogo = jogo.getId();
				
				jogoRetorno = jogoRepository.findByUid(uidJogo);
				
				if(jogoRetorno==null) {
					jogoRetorno = new Jogo();
					jogoRetorno.setNome(nomeJogo);
					jogoRetorno.setNomePesquisa(nomePesquisa);
					jogoRetorno.setUid(uidJogo);
					jogoRetorno.setDataModificado(new Date());
					jogoRepository.save(jogoRetorno);
				}
			}
		return jogoRetorno;
	}
}
