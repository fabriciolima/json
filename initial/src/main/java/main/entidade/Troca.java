package main.entidade;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Index;

@Entity
public class Troca {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	private JogoCliente interesse;
//	@JoinColumn(name="jogoCliente_id")
	@ManyToOne
	private JogoCliente proposta;
//	@JoinColumn(name="jogoCliente_id")
	@Index(name = "idclienteinteresse")
	private Long idClienteInteresse;
	@Index(name = "idclienteproposta")
	private Long idClienteProposta;
	private Date dataCadastro;
	private Long idChat;
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
	public Long getIdClienteInteresse() {
		return idClienteInteresse;
	}
	public void setIdClienteInteresse(Long idClienteInteresse) {
		this.idClienteInteresse = idClienteInteresse;
	}
	public Long getIdClienteProposta() {
		return idClienteProposta;
	}
	public void setIdClienteProposta(Long idClienteProposta) {
		this.idClienteProposta = idClienteProposta;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Long getIdChat() {
		return idChat;
	}
	public void setIdChat(Long idChat) {
		this.idChat = idChat;
	}
	
	}
