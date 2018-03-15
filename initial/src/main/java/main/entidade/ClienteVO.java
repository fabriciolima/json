package main.entidade;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

//@Spatial @Indexed 
//@Entity
//@NamedQuery(name = "Cliente.findByLocal",
//query = "select c from Cliente c where c.nome like '%?1%'")
public class ClienteVO {
	
	private Long id;
	private String nome;
	private String telefone;
	//@Type(type="org.hibernate.spatial.GeometryType")
	private Geometry localizacao;
	
//	@OneToMany(mappedBy="cliente")
//	private List<JogoCliente> listaJogoCliente;
	

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
	
	

	
}
