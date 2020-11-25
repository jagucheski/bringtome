package br.com.jagucheski.bringtome.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.jagucheski.bringtome.dto.RequisicaoNovoUsuario;

@Entity
@Table(name = "users")
public class User {

	@Id
	private String username;

	private String nome;
	private String password;
	private Boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	@JsonIgnore
	private List<Pedido> pedidos;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public RequisicaoNovoUsuario toRequisicaoNovoUsuario(RequisicaoNovoUsuario requisicaoNovoUsuario) {
		requisicaoNovoUsuario.setUserName(username);
		requisicaoNovoUsuario.setNome(nome);
		requisicaoNovoUsuario.setPassword(password);
		requisicaoNovoUsuario.setEnabled(enabled);
		return requisicaoNovoUsuario;
	}

	
}
