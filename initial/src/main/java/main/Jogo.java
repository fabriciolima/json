package main;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Jogo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private Date dataModificado;
	private Boolean apagado;
	private Boolean aprovado;
	private Integer revisao;

//	@OneToMany(mappedBy="jogo")
//	private List<JogoCliente> listaJogoCliente;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Boolean getApagado() {
		return apagado;
	}
	public void setApagado(Boolean apagado) {
		this.apagado = apagado;
	}
	public Boolean getAprovado() {
		return aprovado;
	}
	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}
	
	public Date getDataModificado() {
		return dataModificado;
	}
	public void setDataModificado(Date dataModificado) {
		this.dataModificado = dataModificado;
	}
	
	public Integer getRevisao() {
		return revisao;
	}
	public void setRevisao(Integer revisao) {
		this.revisao = revisao;
	}
	
	
}
