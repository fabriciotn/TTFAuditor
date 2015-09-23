package com.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="SEQ_SETOR", sequenceName="SEQ_SETOR")
public class Setor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_SETOR")
	private int id;
	private String nome;
	@ManyToMany
    @JoinTable(joinColumns={@JoinColumn(name="setor_id")}, inverseJoinColumns={@JoinColumn(name="usuario_id")})
    private List<User> usuarios;
	
	public List<User> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<User> usuarios) {
		this.usuarios = usuarios;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		return id;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Setor) {
			Setor setor = (Setor) obj;
			return setor.getId() == id;
		}

		return false;
	}

}
