package main;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Jogo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique=true)
	private String uid;
	private String nome;
	private String nomePesquisa;
	private Date dataModificado;
	private Boolean apagado;
	private Boolean aprovado;
	private Integer revisao;

//	@OneToMany(mappedBy="jogo")
//	private List<JogoCliente> listaJogoCliente;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public String getNomePesquisa() {
		return nomePesquisa;
	}
	public void setNomePesquisa(String nomePesquisa) {
		this.nomePesquisa = nomePesquisa;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}
