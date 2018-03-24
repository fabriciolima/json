package main.entidade;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
	//@Column(unique=true)
	private String telefone;
	private Date dataDescadastro;
	@Column(unique=true)
	private String uid;
	private LocalDate ultimaVez;
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

	public LocalDate getUltimaVez() {
		return ultimaVez;
	}

	public void setUltimaVez(LocalDate ultimaVez) {
		this.ultimaVez = ultimaVez;
	}	
}
