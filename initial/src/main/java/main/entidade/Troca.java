package main.entidade;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Troca {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	private JogoCliente interesse;
	@ManyToOne
	private JogoCliente proposta;
	private Date dataCadastro;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public JogoCliente getInteresse() {
		return interesse;
	}
	public void setInteresse(JogoCliente interesse) {
		this.interesse = interesse;
	}
	public JogoCliente getProposta() {
		return proposta;
	}
	public void setProposta(JogoCliente proposta) {
		this.proposta = proposta;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
			
}
