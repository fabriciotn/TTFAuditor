package com.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "USERS")
@NamedQuery(name = "User.findUserByLogin", query = "select u from User u where u.login = :login")
@SequenceGenerator(name="SEQ_USER", sequenceName="SEQ_USER")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_LOGIN = "User.findUserByLogin";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_USER")
	private int id;
	@Column(unique = true)
	private String login;
	private String password;
	private String name;
	@Enumerated(EnumType.STRING)
	private Role role;
	private String email;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar ultimoAcesso;
	

	public Calendar getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(Calendar ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isAdmin() {
		return Role.ADMIN.equals(role);
	}

	public boolean isUser() {
		return Role.USER.equals(role);
	}

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User user = (User) obj;
			return user.getId() == id;
		}

		return false;
	}

	public String getMasp() {
		return login;
	}

	public void setMasp(String masp) {
		this.login = masp;
	}
}