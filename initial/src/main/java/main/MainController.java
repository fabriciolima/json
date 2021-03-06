package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.cloud.firestore.Firestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import com.nimbusds.jose.EncryptionMethod;
//import com.nimbusds.jose.JWEAlgorithm;
//import com.nimbusds.jose.JWEHeader;
//import com.nimbusds.jose.JWEObject;
//import com.nimbusds.jose.Payload;
//import com.nimbusds.jose.crypto.DirectEncrypter;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import main.entidade.Cliente;
import main.entidade.Jogo;
import main.entidade.JogoCliente;
import main.entidade.JogoClienteVO;
import main.entidade.Plataforma;
import main.entidade.PropostaVO;
import main.entidade.Troca;
import main.repositorio.ClienteRepository;
import main.repositorio.ClienteRepositoryJPA;
import main.repositorio.JogoClienteRepository;
import main.repositorio.PlataformaRepository;
import main.repositorio.TrocaRepository;

@Controller// This means that this class is a Controller
@RequestMapping(path="/json") // This means URL's start with /demo (after Application path)
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class MainController {
	
	Firestore db;

	@Autowired private JogoRepository jogoRepository;
	@Autowired private JogoClienteRepository jogoClienteRepository;
	@Autowired private ClienteRepository clienteRepository;
	@Autowired private TrocaRepository trocaRepository;
	@Resource private ClienteRepositoryJPA clienteRepositoryJPA;
	@Autowired private PlataformaRepository plataformaRepository;

	//@GetMapping(path="/add") // Map ONLY GET Requests
	@GetMapping(path="/jogo/add") // Map ONLY GET Requests
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@Transactional
	public @ResponseBody String adicionaJogoCliente (
			@RequestParam String idPlataforma
			, @RequestParam String idCliente
			, @RequestParam String estado
			, @RequestParam String nomeJogoSelecionado
			, @RequestParam String nomeJogo
			, @RequestParam String dinheiro )
	{

		
		Jogo jogo = null;
		if(nomeJogoSelecionado.equals(nomeJogo)) {
			jogo=jogoRepository.findByNome(nomeJogoSelecionado);
		}
				
		if(jogo==null) {
			jogo = new Jogo();
			jogo.setNome(nomeJogo);
			jogoRepository.save(jogo);
		}
		Plataforma plataforma = plataformaRepository.findById(Long.decode(idPlataforma));
		
		JogoCliente jogoClienteSQL = new JogoCliente();
		Cliente cliente = clienteRepository.findById(Long.decode(idCliente));
		jogoClienteSQL.setCliente(cliente);
		jogoClienteSQL.setJogo(jogo);
		jogoClienteSQL.setPlataforma(plataforma);
		jogoClienteSQL.setDataCadastro(new Date());
		jogoClienteSQL.setDataUltimaAbertura(new Date());
		jogoClienteSQL.setEstadoDoJogo(estado.equals("novo")?1:2);
		jogoClienteSQL.setComentario(dinheiro);
		jogoClienteRepository.save(jogoClienteSQL);

		return "Sucesso";
	}

	
	@GetMapping(path="/cliente/add") 
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	public @ResponseBody String adicionaCliente(
			@RequestParam String nome, 
			@RequestParam String uid, 
			@RequestParam String lat, 
			@RequestParam String lon) {

		String retorno="";
		
		try {
			
			//GeoPoint localizacao = new GeoPoint(Double.parseDouble(lat), Double.parseDouble(lon));
			WKTReader reader = new WKTReader();
			Geometry ponto= reader.read("Point(".concat(lon).concat(" ").concat(lat).concat(")").replaceAll(",", "."));

			Cliente c = null;
			if(uid.equals("0") || uid.length() <5) {
				uid = nomeRandom();
			}else c = clienteRepository.findByUid(uid);
			
			if(c == null) {
				c= new Cliente();
				
				c.setNome(nome);
				c.setUid(uid);
				c.setLocalizacao(ponto);
				c.setConfirmado(1);
				c.setDataCadastro(new Date());
			
				clienteRepository.save(c);
			}
			retorno = String.valueOf(c.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro inserindo");
			
		}
		return retorno;
	}
	
	@GetMapping(path="/cliente/addemail") 
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	public @ResponseBody String adicionaClienteEmail(
			@RequestParam String nome, 
			@RequestParam String email, 
			@RequestParam String password,
			@RequestParam String esqueci) {

		String retorno="";
		
		try {
			//String passwdDecrypt = Util.decrypt(password);
			Cliente c = clienteRepository.findByEmail(email);
			
			if(c == null) {
				c= new Cliente();
				c.setNome(nome);
				
				c.setEmail(email);
				c.setPassword(password);
				c.setDataCadastro(new Date());
				c.setConfirmado(0);
			
				//clienteRepository.save(c);
				
				String link=Util.crypt(String.valueOf(c.getId()));
				Util.sendMail(email, link);
			}else if(esqueci.equals("true")) {
				c.setNovoNome(nome);
				c.setNovoPassword(password);
				Util.relembrarPassord(c);
			}else {
				if(c.getPassword().equals(password))
					retorno = String.valueOf(c.getId());
			}
			retorno = String.valueOf(c.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro inserindo");
			
		}
		return retorno;
	}

	
	@GetMapping(path="/confirmemail") 
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	public @ResponseBody String adicionaClienteEmail(@RequestParam String token) {

		String retorno="";
		
		try {
			Long id = Util.decryptToken(token);
			
			Cliente c = clienteRepository.findById(id);
			if(c == null) {
				retorno = "Nothing found.";				
			}else {
				if(c.getNovoPassword()!=null && c.getNovoPassword().length()>3) {
					c.setConfirmado(1);
					c.setPassword(c.getNovoPassword());
					c.setNovoPassword(null);
					c.setNome(c.getNovoNome());
					c.setNovoNome(null);
					clienteRepository.save(c);
				}else {
					c.setConfirmado(1);
					clienteRepository.save(c);
					retorno = "Agora voce pode aproveitar os recursos de WePlay.";
				}
			}
			
			
		} catch (Exception e) {
			
			retorno="Something is wrong";
			
		}
		return retorno;
	}
	

	@GetMapping(path="/cliente/verificaemail") 
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	public @ResponseBody String verificaClienteEmail(
			@RequestParam String nome, 
			@RequestParam String email, 
			@RequestParam String password) {

		String retorno="";
		
		try {
			Cliente c = clienteRepository.findByEmail(email);
			
			if(c == null) {
				retorno = "erro";				
			}else {
				if(c.getConfirmado().equals(1)) {
					c.setNome(nome);
					clienteRepository.save(c);
					retorno = "ok";				
				}
			}
		} catch (Exception e) {
			retorno="Something is wrong";
		}
		return retorno;
	}

	
	@GetMapping(path="/plataforma")
	public @ResponseBody Iterable<Plataforma> getAllPlataforma() {
		// This returns a JSON or XML with the users
		return plataformaRepository.findAll();
	}
	
	@GetMapping(path="/jogo")
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	public @ResponseBody Page<Jogo> getJogoDatas(Pageable pageable) {
		//List<Jogo> retorno = JogoDAO.procura();
		//List<Jogo> findByDataModificadoGreaterThanEqual = jogoRepository.findByDataModificadoGreaterThanEqual(new Date());
		Page<Jogo> retorno = jogoRepository.findAll(pageable);
		
		return retorno;
	}
	
	
	@GetMapping(path="/jogosperto")
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	public @ResponseBody List<Map<String,String>> jogosPerto(@RequestParam String pos,String listaPlataforma, String id, Pageable page) {

		String position = pos.substring(0, 5).toLowerCase().equals("point")?pos:"Point(".concat(pos).concat(")");
		List<Map<String, String>> retorno = new ArrayList<Map<String, String>>();
		
		try {
			if(!(id == null || id.equals("") ||id.equals("null"))){
				Cliente cliente = clienteRepository.findById(Long.decode(id));
				cliente.setUltimaVez(new Date());
				clienteRepository.save(cliente);
			}
			
			Gson gson = new Gson();
			WKTReader reader = new WKTReader();
			Geometry ponto= reader.read(position);

			//ArrayList<ArrayList<String>> list2 = gson.fromJson(listaPlataforma, new TypeToken<ArrayList<ArrayList<String>>>() {}.getType());
			ArrayList<String> listPlataforma = gson.fromJson(listaPlataforma, new TypeToken<ArrayList<String>>() {}.getType());
			
			List<Object[]> list = null;
			if(id.equals(""))
				id = "0";
			if(ponto != null)
				list = clienteRepositoryJPA.procuraJogosPerto(ponto,id,listPlataforma, page);
			for(Object[] obj:list) {
				if(!obj[1].toString().equals(id)) {
					Map<String, String> item = new HashMap<>();
					
					item.put("nomecliente",obj[0].toString());
//					item.put("idcliente",obj[1].toString());
					item.put("localizacao",obj[2].toString());
					item.put("idJogo",obj[3].toString());
					item.put("nomeJogo",obj[4].toString());
					item.put("idPlataforma",obj[5].toString());
					item.put("nomePlataforma",obj[6].toString());
					item.put("distancia",obj[7].toString().replaceAll(".0", ""));
					//System.out.println(obj[8]);
					item.put("estaDoJogo",obj[8]==null?"3":obj[8].toString());
					item.put("idJogoCliente",obj[9].toString());
					item.put("comentario",obj[10]==null?"":obj[10].toString());
					retorno.add(item);
					
//					JogoClienteVO jc = new JogoClienteVO();
//					jc.setIdJogo(idjogo);
//					jc.setIdPlataforma(idplataforma);
//	//				jc.setNomeCliente(nomecliente);
//					jc.setNomeJogo(nomejogo);
//					jc.setNomePlataforma(nomeplataforma);
//					//Integer distancia = Integer.valueOf(dist);
//					jc.setDistancia(dist);	
//					jc.setEstadoDoJogo(estadojogo);
//					jc.setId(Long.decode( idJogoCliente));
//					jc.setComentario(comentario);
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return null;
		}
		return retorno;
	}
	

	@GetMapping(path="/jogoscliente")
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	public @ResponseBody List<Map<String,String>> jogosVisitar(@RequestParam String idCliente,@RequestParam String idClienteVisita) throws Exception {

		List<Map<String, String>> retorno = new ArrayList<Map<String, String>>();
		
		idClienteVisita = Util.decrypt(idClienteVisita.replaceAll(" ", "+"));
	    
		Cliente clienteVisita = clienteRepository.findById(Long.decode(idClienteVisita));
		Cliente cliente = clienteRepository.findById(Long.decode(idCliente));
		
		List<JogoCliente> jogoCliente = jogoClienteRepository.findByCliente(clienteVisita);
		String distanciaCliente = clienteRepositoryJPA.distancia(cliente.getLocalizacao(), idCliente);
		
		for(JogoCliente jc:jogoCliente) {
			Map<String, String> item = new HashMap<>();
			
			item.put("nomeCliente",jc.getCliente().getNome().substring(0,10));
			//item.put("localizacao",jc[2].toString());
			item.put("idJogo",String.valueOf(jc.getJogo().getId()));
			item.put("nomeJogo",jc.getJogo().getNome());
			item.put("idPlataforma",String.valueOf(jc.getPlataforma().getId()));
			item.put("nomePlataforma",jc.getPlataforma().getNome());
			item.put("distancia",distanciaCliente.replaceAll(".0", ""));
			//System.out.println(obj[8]);
			item.put("estaDoJogo",String.valueOf(jc.getEstadoDoJogo()));
			item.put("idJogoCliente",String.valueOf(jc.getId()));
			item.put("comentario",jc.getComentario());
			retorno.add(item);
			
			
		}
		
		return retorno;
	}
	
	

//public @ResponseBody void processa2(@RequestParam String qtde) throws ParseException {
//	double minLat = -89.00;
//	double maxLat = 89.00;      
//	double minLon = -178.00;
//	double maxLon = 178.00;     
//	DecimalFormat df = new DecimalFormat("###.######");
//	WKTReader reader = new WKTReader();
//
//	//Iterable<Cliente> listaCliente = clienteRepository.findAll();
//	//for(Cliente c:listaCliente) {
//	for(int cont =0 ; cont< Integer.parseInt(qtde); cont++) {
//		double longitude = minLon + (double)(Math.random() * ((maxLon - minLon) + 1));
//		double latitude = minLat + (double)(Math.random() * ((maxLat - minLat) + 1));
//		Geometry ponto= reader.read("Point(".concat(df.format(longitude)).concat(" ").concat(df.format(latitude).concat(")")).replaceAll(",", "."));
//		Cliente c = new Cliente();
//		c.setLocalizacao(ponto);
//		c.setNome(nomeRandom());
//		c.setTelefone(telRandom());
//		System.out.println(cont);
//		try {
//			clienteRepository.save(c);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}


public String nomeRandom() {
	final java.util.Random rand = new java.util.Random();
	final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
    StringBuilder builder = new StringBuilder();
    int length = 20 ; //rand.nextInt(5)+5;
    for(int i = 0; i < length; i++) {
        builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
    }
    return builder.toString();
}

public String telRandom() {
	final java.util.Random rand = new java.util.Random();
	final String lexicon = "12345674890";
    StringBuilder builder = new StringBuilder();
    int length = 10;
    for(int i = 0; i < length; i++) {
        builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
    }
    return builder.toString();
}

//public String getEncrypt(String dado) throws Exception {
//	KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//	keyGen.init(128);
//	SecretKey key = keyGen.generateKey();
//
//	// Create the header
//	JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128GCM);
//
//	// Set the plain text
//	Payload payload = new Payload(dado);
//
//	// Create the JWE object and encrypt it
//	JWEObject jweObject = new JWEObject(header, payload);
//	jweObject.encrypt(new DirectEncrypter(key));
//
//	// Serialise to compact JOSE form...
//	String jweString = jweObject.serialize();
//
//	return jweString;
//	
//}

@GetMapping(path="/teste")
public @ResponseBody String testes() {

	return "server ok3";
}

@GetMapping(path="/jogo/troca/add") 
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@Transactional
public @ResponseBody String adicionaJogoTroca(
		@RequestParam String idJogoCliente,
		@RequestParam ArrayList<String> jogosTroca)
{

	if(db==null) {
		FireBaseDB fire = new FireBaseDB();
		try {
			db = fire.getDb();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//salva no firebase
//	Map<String, Object> jogoCliente = new HashMap<>();
//	jogoCliente.put("idplataforma", idPlataforma);
//	jogoCliente.put("idjogo", jogo.getUid());
//	jogoCliente.put("idcliente",uidCliente);
//	jogoCliente.put("estado", estado);
//	jogoCliente.put("nomepesquisa",nomePesquisa );
//	jogoCliente.put("nomejogo", nomeJogo);
//	jogoCliente.put("dinheiro",dinheiro);
//	jogoCliente.put("dataApagado","");
//	
//	ApiFuture<DocumentReference> jogoClienteRef = db.collection("jogocliente").add(jogoCliente);
	return null;
	
}

@GetMapping(path="/fazproposta") // Map ONLY GET Requests
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public @ResponseBody String fazProposta(
		@RequestParam String funcao, 
		@RequestParam Long idinteresse, 
		@RequestParam Long idproposta) {
	
	String retorno = "erro";
	JogoCliente jcInteresse = jogoClienteRepository.findById(idinteresse);
	JogoCliente jcProposta = jogoClienteRepository.findById(idproposta);
	if(jcInteresse!= null && jcProposta != null) {
		List<Troca> byInteresseAndProposta = trocaRepository.findByInteresseAndProposta(jcInteresse, jcProposta);
		if(byInteresseAndProposta.size() > 0 && funcao.equals("remove")) {
			trocaRepository.delete(byInteresseAndProposta.get(0));
			retorno = "del";
		}else {
			if(byInteresseAndProposta.size() == 0 && funcao.equals("adiciona")) {
				Troca troca = new Troca();
				troca.setDataCadastro(new Date());
				troca.setInteresse(jcInteresse);
				troca.setProposta(jcProposta);
				troca.setIdClienteInteresse(jcInteresse.getCliente().getId());
				troca.setIdClienteProposta(jcProposta.getCliente().getId());
				trocaRepository.save(troca);
				retorno = "add";
			} 
		}
	}
	return retorno;
}


@GetMapping(path="/meusjogos")
//@CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public @ResponseBody List<JogoClienteVO> getmeusJogo(
		@RequestParam String idcliente,
		@RequestParam String idinteresse) {
	if (idcliente == null || idcliente.equals("null"))
		return null;
	Cliente cliente = clienteRepository.findById(Long.parseLong(idcliente));
	List<JogoCliente> jogoCliente = jogoClienteRepository.findByCliente(cliente);

	List<JogoClienteVO> retorno = new ArrayList<JogoClienteVO>();
	if(jogoCliente.size()>0) {
		for(JogoCliente jc:jogoCliente)
		{
			JogoClienteVO jogoClienteVO =new JogoClienteVO();
			jogoClienteVO.setId(jc.getId());
			jogoClienteVO.setIdJogo(jc.getJogo().getId().toString());
			jogoClienteVO.setNomeJogo(jc.getJogo().getNome());
			jogoClienteVO.setIdPlataforma(jc.getPlataforma().getId().toString());
			jogoClienteVO.setNomePlataforma(jc.getPlataforma().getNome());
			List<Troca> byInteresse = trocaRepository.findByInteresse(jc);
			jogoClienteVO.setQtdInteressados(String.valueOf(byInteresse.size()));
			
			if(idinteresse != null && !idinteresse.equals("undefined") && idinteresse.length() >0) {//usado na tela de propostas
				JogoCliente jcInteresse = jogoClienteRepository.findById(Long.parseLong(idinteresse));
//				List<Troca> listaInteressado = trocaRepository.findByInteressado(jcInteresse);
				List<Troca> listaInteresseInteressado = trocaRepository.findByInteresseAndProposta(jcInteresse, jc);
				if(listaInteresseInteressado.size() > 0) {
					jogoClienteVO.setPossuiPropostaCom(idinteresse);
					//jogoClienteVO.set
				}
				
			}
			retorno.add(jogoClienteVO);
		}
	}
	//List<Jogo> findByDataModificadoGreaterThanEqual = jogoRepository.findByDataModificadoGreaterThanEqual(new Date());
	
	return retorno;
}

@GetMapping(path="/jogocliente")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public @ResponseBody JogoClienteVO getJogoCliente(
		@RequestParam String idjogocliente) {
	JogoCliente jc = jogoClienteRepository.findById(Long.parseLong(idjogocliente));

	JogoClienteVO jogoClienteVO = new JogoClienteVO();
	if(jc != null) {
			jogoClienteVO.setId(jc.getId());
			jogoClienteVO.setIdJogo(jc.getJogo().getId().toString());
			jogoClienteVO.setNomeJogo(jc.getJogo().getNome());
			jogoClienteVO.setIdPlataforma(jc.getPlataforma().getId().toString());
			jogoClienteVO.setNomePlataforma(jc.getPlataforma().getNome());
			List<Troca> byInteresse = trocaRepository.findByInteresse(jc);
			jogoClienteVO.setQtdInteressados(String.valueOf(byInteresse.size()));
		}
	
	return jogoClienteVO;
}

@GetMapping(path="/listaproposta")
//@CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public @ResponseBody List<PropostaVO> listaProposta(@RequestParam String idinteresse) {
	if (idinteresse == null || idinteresse.equals("null"))
		return null;
	
	JogoCliente jc = jogoClienteRepository.findById(Long.decode(idinteresse));
	List<Troca> listaTroca = trocaRepository.findByInteresse(jc);

	List<PropostaVO> retorno = new ArrayList<PropostaVO>();
	if(listaTroca.size()>0) {
		for(Troca t:listaTroca)
		{
			PropostaVO propostaVO =new PropostaVO();
			propostaVO.setIdTroca(t.getId());
			propostaVO.setIdJogo(t.getProposta().getJogo().getId().toString());
			propostaVO.setNomeJogo(t.getProposta().getJogo().getNome());
			propostaVO.setIdPlataforma(t.getProposta().getPlataforma().getId().toString());
			propostaVO.setNomePlataforma(t.getProposta().getPlataforma().getNome());
			propostaVO.setDistancia(String.valueOf(t.getInteresse().getCliente().getLocalizacao().distance(t.getProposta().getCliente().getLocalizacao())));
			retorno.add(propostaVO);
			}
		}
	return retorno;
}


@GetMapping(path="/chat/add")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public @ResponseBody Long adicionaChat(@RequestParam String idTroca) {
	if (idTroca == null || idTroca.equals("null"))
		return null;
	Long idChat =null;
	
	Troca troca = trocaRepository.findById(Long.parseLong(idTroca));
	if(troca.getIdChat()==null) {
		troca.setIdChat(troca.getId());
		trocaRepository.save(troca);
	}
	idChat = troca.getIdChat();
	
	return idChat;
}

@GetMapping(path="/chat/verifica")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public @ResponseBody String verificaChat(@RequestParam String idCliente) {
	if (idCliente == null || idCliente.equals("null"))
		return null;
	String retorno = "false";
	
	List<Troca> listaTrocaInteresse = trocaRepository.findByIdClienteInteresse(Long.parseLong(idCliente));
	if (listaTrocaInteresse.size()>0) {
		retorno = " true";
	}else {
		List<Troca> listaTrocaProposta = trocaRepository.findByIdClienteProposta(Long.parseLong(idCliente));
		if(listaTrocaProposta.size()>0)
			retorno="true";
	}
	
	return retorno;
}

@GetMapping(path="/chat/lista")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public @ResponseBody List<Map<String, String>> listaChat(@RequestParam String idCliente) throws Exception {
	if (idCliente == null || idCliente.equals("null"))
		return null;
	
	List<Map<String, String>> retorno = new ArrayList<Map<String, String>>();
	
	List<Troca> listaTrocaInteresse = trocaRepository.findByIdClienteInteresse(Long.parseLong(idCliente));
	if (listaTrocaInteresse.size()>0) {
		List<Troca> listaTroca = listaTrocaInteresse
					.stream()
					.filter(t -> t.getIdChat() != null)
					.collect(Collectors.toList());
		for(Troca t: listaTroca) {
			Map<String, String> item = new HashMap<>();
			item.put("idTroca", String.valueOf(t.getId()));
			item.put("idChat", String.valueOf(t.getIdChat()));
			item.put("interesseNomeJogo", t.getInteresse().getJogo().getNome());
			item.put("interesseIdJogo", String.valueOf(t.getInteresse().getJogo().getId()));
			item.put("interesseIdPlataforma", String.valueOf(t.getInteresse().getPlataforma().getId()));
			item.put("interesseNomePlataforma", t.getInteresse().getPlataforma().getNome());
			
			item.put("propostaNomeJogo", t.getProposta().getJogo().getNome());
			item.put("propostaIdJogo", String.valueOf(t.getProposta().getJogo().getId()));
			item.put("propostaIdPlataforma", String.valueOf(t.getProposta().getPlataforma().getId()));
			item.put("propostaNomePlataforma", t.getProposta().getPlataforma().getNome());
		
			retorno.add(item);
		}

	}
		List<Troca> listaTrocaProposta = trocaRepository.findByIdClienteProposta(Long.parseLong(idCliente));
		
		if (listaTrocaProposta.size()>0) {
			List<Troca> listaTroca = listaTrocaProposta
						.stream()
						.filter(t -> t.getIdChat() != null)
						.collect(Collectors.toList());
			for(Troca t: listaTroca) {
				Map<String, String> item = new HashMap<>();
				item.put("id", String.valueOf(t.getId()));
				item.put("idTroca", String.valueOf(t.getId()));
				item.put("idChat", String.valueOf(t.getIdChat()));
				item.put("interesseNomeJogo", t.getInteresse().getJogo().getNome());
				item.put("interesseIdJogo", String.valueOf(t.getInteresse().getJogo().getId()));
				item.put("interesseIdPlataforma", String.valueOf(t.getInteresse().getPlataforma().getId()));
				item.put("interesseNomePlataforma", t.getInteresse().getPlataforma().getNome());
				
				item.put("propostaNomeJogo", t.getProposta().getJogo().getNome());
				item.put("propostaIdJogo", String.valueOf(t.getProposta().getJogo().getId()));
				item.put("propostaIdPlataforma", String.valueOf(t.getProposta().getJogo().getId()));
				item.put("propostaNomePlataforma", t.getProposta().getPlataforma().getNome());
				retorno.add(item);
			}
		}

			return retorno;
}
		


@GetMapping(path="/jogo/d")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public @ResponseBody String apagaJogoCliente(@RequestParam String jc,@RequestParam String i) {
	
	String retorno="Erro";

	
	JogoCliente jcliente = jogoClienteRepository.findById(Long.valueOf(jc));
	if(jcliente.getCliente().getId().equals(Long.decode(i))) {
		jogoClienteRepository.delete(jcliente);
		retorno = "Sucesso";
	}
	
	return retorno;
}

@GetMapping(path="/cliente/d")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public @ResponseBody String apagaCliente(@RequestParam String u,@RequestParam String i) {
	
	String retorno="Erro";
	
	Cliente cliente = clienteRepository.findById(Long.valueOf(i));
	if(cliente != null)
	if(cliente.getUid().equals(u)) {
		clienteRepository.delete(cliente);
		retorno = "Sucesso";
	}
	
	return retorno;
}

@GetMapping(path="/jogo/nome")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public @ResponseBody List<Map<String, String>> buscaJogoNome(@RequestParam String nome) {
	
	List<Map<String, String>> retorno= new ArrayList<Map<String, String>>();
	List<Jogo> listaNome = jogoRepository.findFirst3ByNomeContainingIgnoreCaseOrderByNome("%".concat(nome).concat("%"));

	for(Jogo j: listaNome) {
		Map<String, String> item = new HashMap<>();
		item.put("id", String.valueOf(j.getId()));
		item.put("nome", j.getNome());
		item.put("img","http://54.94.219.84:8080/jogo90/".concat(String.valueOf(j.getId())).concat("_90.png"));
		retorno.add(item);
	}
	
	return retorno;
}

}
