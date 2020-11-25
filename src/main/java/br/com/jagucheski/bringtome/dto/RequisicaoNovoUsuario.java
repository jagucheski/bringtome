package br.com.jagucheski.bringtome.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.jagucheski.bringtome.model.User;

public class RequisicaoNovoUsuario {

	@NotBlank
	private String userName;

	@NotBlank
	private String password;
	
	@NotBlank
	private String nome;

	private Boolean enabled;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public User toUser() {
		BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();
		User user = new User();
		user.setUsername(userName);
		user.setNome(nome);
		user.setPassword(enconder.encode(password));
		user.setEnabled(enabled);
		return user;
	}

	

}
