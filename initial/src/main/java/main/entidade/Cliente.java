package main.entidade;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.json.zip.BitInputStream;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
//@Spatial @Indexed 
@Entity
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String novoNome;
	private String email;
	private String password;
	private String novoPassword;
	private Integer confirmado;	
	//@Column(unique=true)
	private String telefone;
	private Date dataDescadastro;
	private Date dataCadastro;
	@Index(name = "iduidcliente")
	private String uid;
	private Date ultimaVez;
	@Column(columnDefinition="Geometry")
	//@Type(type="org.hibernate.spatial.GeometryType")
	private Geometry localizacao;
	
//	@OneToMany(mappedBy="cliente")
//	@Cascade(CascadeType.ALL)
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "cliente")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<JogoCliente> listaJogoCliente;
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

//	public List<JogoCliente> getListaJogoCliente() {
//		return listaJogoCliente;
//	}
//
//	public void setListaJogoCliente(List<JogoCliente> listaJogoCliente) {
//		this.listaJogoCliente = listaJogoCliente;
//	}

	public Geometry getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(Geometry localizacao) {
		this.localizacao = localizacao;
	}
	
	public void setLocalizacao(String localizacao) throws ParseException {
		WKTReader reader = new WKTReader();
//		String temp = localizacao;
		String lon = localizacao.substring(0,localizacao.indexOf(" "));
		String lat = localizacao.substring(localizacao.indexOf(" ")+1);
		Double lonDouble = Double.valueOf(lon);
		Double latDouble = Double.valueOf(lat);
//		Integer lonInt = lonDouble.intValue();
//		Integer latInt = latDouble.intValue();
//		
//		System.out.println(lon +"==>"+lonInt);
//		System.out.println(lat +"==>"+latInt);
//		localizacao = "Geometry(".concat(lonInt.toString()).concat(" ").concat(latInt.toString()).concat(")");
//		System.out.println(localizacao);
//		//this.localizacao = reader.read(localizacao);// new Geometry(Double.parseDouble(lon),Double.parseDouble(lat));
//		System.out.println(this.localizacao );
//		System.out.println("temp==>"+temp);
//		System.out.println(reader.read("Geometry(".concat(temp).concat(")")));
		System.out.println(this.localizacao);
		this.localizacao = reader.read("Point(".concat(localizacao).concat(")"));
		//this.localizacao = new Point(lonDouble,  latDouble);
		System.out.println(this.localizacao);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public List<JogoCliente> getListaJogoCliente() {
		return listaJogoCliente;
	}

	public void setListaJogoCliente(List<JogoCliente> listaJogoCliente) {
		this.listaJogoCliente = listaJogoCliente;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Date getDataDescadastro() {
		return dataDescadastro;
	}

	public void setDataDescadastro(Date dataDescadastro) {
		this.dataDescadastro = dataDescadastro;
	}

	public Date getUltimaVez() {
		return ultimaVez;
	}

	public void setUltimaVez(Date ultimaVez) {
		this.ultimaVez = ultimaVez;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Integer getConfirmado() {
		return confirmado;
	}

	public void setConfirmado(Integer confirmado) {
		this.confirmado = confirmado;
	}

	public String getNovoPassword() {
		return novoPassword;
	}

	public void setNovoPassword(String novoPassword) {
		this.novoPassword = novoPassword;
	}

	public String getNovoNome() {
		return novoNome;
	}

	public void setNovoNome(String novoNome) {
		this.novoNome = novoNome;
	}	
}
