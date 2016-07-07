package com.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.BatchSize;

import com.util.Criptografia;

@Entity
@Table(name = "USERS")
@NamedQuery(name = "User.findUserByLogin", query = "select u from User u where u.login = :login")
@BatchSize(size = 500)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
//@SequenceGenerator(name="SEQ_USER", sequenceName="SEQ_USER")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_LOGIN = "User.findUserByLogin";

	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_USER")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(unique = true)
	private String login;
	private String password;
	private String name;
	@Enumerated(EnumType.STRING)
	private Role role;
	private String email;
	private String telefone;
	private String celular;
	private String cargo;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar ultimoAcesso;
	private boolean ativo;
	@ManyToMany(mappedBy="auditores", fetch=FetchType.LAZY)
	@XmlTransient
    private List<Auditoria> auditorias;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "auditor_questionario",
	joinColumns = @JoinColumn(name = "user_id"), 
	inverseJoinColumns = @JoinColumn(name = "questionario_id"))
	@OrderBy(value = "id ASC")
	@XmlTransient
	private Set<Questionario> questionarios;
	
	@OneToOne
	private Unidade unidade;
	
	/*
	 * Campos para gerenciar as permissões
	 */
	private boolean menuCadastros;
	private boolean menuCadUnidades;
	private boolean menuCadEstabelecimentos;
	
	private boolean menuAuditoria;
	private boolean menuPreparacao;
	private boolean menuCadQuestionarios;
	private boolean menuCadPerguntas;
	private boolean menuCadAuditorias;
	private boolean menuAuditar;
	
	private boolean menuRelatorios;
	
	private boolean menuConfiguracoes;
	private boolean menuConfigGerais;
	private boolean menuUsuarios;
	private boolean menuGerenciarUsuarios;
	private boolean menuMudarMinhaSenha;
	private boolean menuAuditoriaOff;
	private boolean menuAuditoriaOffExportar;
	private boolean menuAuditoriaOffImportar;
	
	//Getters e Settes Permissões
	public boolean isMenuCadastros() {
		return menuCadastros;
	}
	public void setMenuCadastros(boolean menuCadastros) {
		this.menuCadastros = menuCadastros;
	}
	public boolean isMenuCadUnidades() {
		return menuCadUnidades;
	}
	public void setMenuCadUnidades(boolean menuCadUnidades) {
		this.menuCadUnidades = menuCadUnidades;
	}
	public boolean isMenuCadEstabelecimentos() {
		return menuCadEstabelecimentos;
	}
	public void setMenuCadEstabelecimentos(boolean menuCadEstabelecimentos) {
		this.menuCadEstabelecimentos = menuCadEstabelecimentos;
	}
	public boolean isMenuAuditoria() {
		return menuAuditoria;
	}
	public void setMenuAuditoria(boolean menuAuditoria) {
		this.menuAuditoria = menuAuditoria;
	}
	public boolean isMenuPreparacao() {
		return menuPreparacao;
	}
	public void setMenuPreparacao(boolean menuPreparacao) {
		this.menuPreparacao = menuPreparacao;
	}
	public boolean isMenuCadQuestionarios() {
		return menuCadQuestionarios;
	}
	public void setMenuCadQuestionarios(boolean menuCadQuestionarios) {
		this.menuCadQuestionarios = menuCadQuestionarios;
	}
	public boolean isMenuCadPerguntas() {
		return menuCadPerguntas;
	}
	public void setMenuCadPerguntas(boolean menuCadPerguntas) {
		this.menuCadPerguntas = menuCadPerguntas;
	}
	public boolean isMenuCadAuditorias() {
		return menuCadAuditorias;
	}
	public void setMenuCadAuditorias(boolean menuCadAuditorias) {
		this.menuCadAuditorias = menuCadAuditorias;
	}
	public boolean isMenuAuditar() {
		return menuAuditar;
	}
	public void setMenuAuditar(boolean menuAuditar) {
		this.menuAuditar = menuAuditar;
	}
	public boolean isMenuRelatorios() {
		return menuRelatorios;
	}
	public void setMenuRelatorios(boolean menuRelatorios) {
		this.menuRelatorios = menuRelatorios;
	}
	public boolean isMenuConfiguracoes() {
		return menuConfiguracoes;
	}
	public void setMenuConfiguracoes(boolean menuConfiguracoes) {
		this.menuConfiguracoes = menuConfiguracoes;
	}
	public boolean isMenuConfigGerais() {
		return menuConfigGerais;
	}
	public void setMenuConfigGerais(boolean menuConfigGerais) {
		this.menuConfigGerais = menuConfigGerais;
	}
	public boolean isMenuUsuarios() {
		return menuUsuarios;
	}
	public void setMenuUsuarios(boolean menuUsuarios) {
		this.menuUsuarios = menuUsuarios;
	}
	public boolean isMenuGerenciarUsuarios() {
		return menuGerenciarUsuarios;
	}
	public void setMenuGerenciarUsuarios(boolean menuGerenciarUsuarios) {
		this.menuGerenciarUsuarios = menuGerenciarUsuarios;
	}
	public boolean isMenuMudarMinhaSenha() {
		return menuMudarMinhaSenha;
	}
	public void setMenuMudarMinhaSenha(boolean menuMudarMinhaSenha) {
		this.menuMudarMinhaSenha = menuMudarMinhaSenha;
	}
	public boolean isMenuAuditoriaOff() {
		return menuAuditoriaOff;
	}
	public void setMenuAuditoriaOff(boolean menuAuditoriaOff) {
		this.menuAuditoriaOff = menuAuditoriaOff;
	}
	public boolean isMenuAuditoriaOffExportar() {
		return menuAuditoriaOffExportar;
	}
	public void setMenuAuditoriaOffExportar(boolean menuAuditoriaOffExportar) {
		this.menuAuditoriaOffExportar = menuAuditoriaOffExportar;
	}
	public boolean isMenuAuditoriaOffImportar() {
		return menuAuditoriaOffImportar;
	}
	public void setMenuAuditoriaOffImportar(boolean menuAuditoriaOffImportar) {
		this.menuAuditoriaOffImportar = menuAuditoriaOffImportar;
	}
	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
	/*
	 * Fim dos getters e setters das permissões.
	 */
	
	public boolean getAtivo() {
		return ativo;
	}
	public List<Questionario> getQuestionarios() {
		if(questionarios != null)
			return new ArrayList<Questionario>(questionarios);
		
		return null;
	}
	public void setQuestionarios(List<Questionario> questionarios) {
		if(questionarios != null)
			this.questionarios = new HashSet<Questionario>(questionarios);
	}
	public void setAtivo(boolean status) {
		this.ativo = status;
	}

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
		this.password = Criptografia.criptografa(password);
	}
	
	public void setPasswordSemCriptografia(String password) {
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

	public boolean isAuditor() {
		return Role.AUDITOR.equals(role);
	}
	
	public boolean isGestor() {
		return Role.GESTOR.equals(role);
	}


	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	public List<Auditoria> getAuditorias() {
		return auditorias;
	}
	
	public void setAuditorias(List<Auditoria> auditorias) {
		this.auditorias = auditorias;
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}