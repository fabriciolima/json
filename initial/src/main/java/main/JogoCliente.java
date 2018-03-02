package main;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class JogoCliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Integer estadoDoJogo;
	private Long idCliente;
	private Long idJogo;
	private Long idPlataforma;
	private Date dataModificado;
	private Date dataCadastro;
	//@Column(columnDefinition="default 0")
	private Boolean apagado=false;
	//@Column(columnDefinition="default 0")
	private Boolean aprovado=false;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Integer getEstadoDoJogo() {
		return estadoDoJogo;
	}
	public void setEstadoDoJogo(Integer estadoDoJogo) {
		this.estadoDoJogo = estadoDoJogo;
	}
	public Long getIdjogo() {
		return idJogo;
	}
	public void setIdJogo(Long idJogo) {
		this.idJogo = idJogo;
	}
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idcliente) {
		this.idCliente = idcliente;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdPlataforma() {
		return idPlataforma;
	}
	public void setIdPlataforma(Long idPlataforma) {
		this.idPlataforma = idPlataforma;
	}
	
	
}
