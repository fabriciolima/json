package main.entidade;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class JogoCliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Integer estadoDoJogo;
	@ManyToOne @NotNull
	private Cliente cliente;
	@ManyToOne @NotNull
	private Jogo jogo;
	@ManyToOne @NotNull 
	private Plataforma plataforma;
	private Date dataModificado;
	private Date dataCadastro;
	private Date dataApagado;
	private Date dataUltimaAbertura;
	//@Column(columnDefinition="default 0")
	private Boolean apagado=false;
	//@Column(columnDefinition="default 0")
	private Boolean aprovado=false;
	
	public Long getId() {
		return id;
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
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Jogo getJogo() {
		return jogo;
	}
	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}
	public Plataforma getPlataforma() {
		return plataforma;
	}
	public void setPlataforma(Plataforma plataforma) {
		this.plataforma = plataforma;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDataApagado() {
		return dataApagado;
	}
	public void setDataApagado(Date dataApagado) {
		this.dataApagado = dataApagado;
	}

	public Date getDataUltimaAbertura() {
		return dataUltimaAbertura;
	}

	public void setDataUltimaAbertura(Date dataUltimaAbertura) {
		this.dataUltimaAbertura = dataUltimaAbertura;
	}


}
