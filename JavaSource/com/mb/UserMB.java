package com.mb;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import com.facade.UserFacade;
import com.model.Questionario;
import com.model.User;
import com.util.Criptografia;

@RequestScoped
@ManagedBean(name = "userMB")
public class UserMB extends AbstractMB implements Serializable {

	public static final String	INJECTION_NAME		= "#{userMB}";
	private static final long	serialVersionUID	= 1L;

	private User				user;
	private List<User>			usuarios;
	private UserFacade			userFacade;
	private String				novasenha;
	private String				senhaDigitada;
	private User				usuarioLogado;
	private boolean				menuCadastro;
	private boolean				menuAuditorias;
	private boolean				menuPreparacao;
	private List<Questionario>	questionarios;

	public UserMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

		habilitaMenuCadastros();
		habilitaMenuAuditorias();
		habilitaMenuPreparacao();
		loadQuestionarios();

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");
	}

	private void loadQuestionarios() {
		if (questionarios == null) {
			questionarios = new ArrayList<Questionario>();
			
			questionarios = usuarioLogado.getQuestionarios();
		}
	}

	private void habilitaMenuPreparacao() {
		if (!usuarioLogado.isMenuCadAuditorias() && !usuarioLogado.isMenuCadPerguntas() && !usuarioLogado.isMenuCadQuestionarios()) {
			menuPreparacao = false;
		} else {
			menuPreparacao = true;
		}
	}

	private void habilitaMenuAuditorias() {
		if (!usuarioLogado.isMenuAuditar() && !usuarioLogado.isMenuCadAuditorias() && !usuarioLogado.isMenuCadPerguntas()
				&& !usuarioLogado.isMenuCadQuestionarios()) {
			menuAuditorias = false;
		} else {
			menuAuditorias = true;
		}
	}

	private void habilitaMenuCadastros() {
		if (!usuarioLogado.isMenuCadUnidades() && !usuarioLogado.isMenuCadEstabelecimentos()) {
			menuCadastro = false;
		} else {
			menuCadastro = true;
		}
	}

	public boolean isMenuPreparacao() {
		return menuPreparacao;
	}

	public void setMenuPreparacao(boolean menuPreparacao) {
		this.menuPreparacao = menuPreparacao;
	}

	public boolean isMenuAuditorias() {
		return menuAuditorias;
	}

	public void setMenuAuditorias(boolean menuAuditorias) {
		this.menuAuditorias = menuAuditorias;
	}

	public boolean isMenuCadastro() {
		return menuCadastro;
	}

	public void setMenuCadastro(boolean menuCadastro) {
		this.menuCadastro = menuCadastro;
	}

	public User getUsuarioLogado() {
		return usuarioLogado;
	}

	public String getSenhaDigitada() {
		return senhaDigitada;
	}

	public void setSenhaDigitada(String senhaDigitada) {
		this.senhaDigitada = senhaDigitada;
	}

	public String getNovasenha() {
		return novasenha;
	}

	public void setNovasenha(String novasenha) {
		this.novasenha = novasenha;
	}

	public List<Questionario> getQuestionarios() {
		return questionarios;
	}

	public void setQuestionarios(List<Questionario> questionarios) {
		this.questionarios = questionarios;
	}

	public List<User> getAllUsuarios() {
		if (usuarios == null) {
			loadUsuarios();
		}

		return usuarios;
	}

	public UserFacade getUserFacade() {
		if (userFacade == null) {
			userFacade = new UserFacade();
		}

		return userFacade;
	}

	private void loadUsuarios() {
		usuarios = getUserFacade().listAll();
	}

	public List<User> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<User> usuarios) {
		this.usuarios = usuarios;
	}

	public boolean isAdmin() {
		return user.isAdmin();
	}
	
	public boolean isAuditor() {
		return user.isAuditor();
	}

	public void logOut() throws IOException {
		getRequest().getSession().invalidate();
		FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public User getUser() {
		if (user == null) {
			user = new User();
		}
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String createUser() {
		try {
			UserFacade uf = getUserFacade();
			user.setPassword(user.getLogin());
			user.setAtivo(true);
			uf.createUsuario(user);
			closeDialog();
			displayInfoMessageToUser("Registrado com sucesso!");
			loadUsers();
			resetUser();
		} catch (PersistenceException e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema ao salvar o registro! Verifique se já existe algum usuário com o login "
					+ user.getLogin() + ", ou tente novamente.");
			displayErrorMessageToUser("Caso o problema persista, entre em contato com o administrador do sistema.");
			e.printStackTrace();
			return "";
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			displayErrorMessageToUser("Caso o problema persista, entre em contato com o administrador do sistema.");
			e.printStackTrace();
			return "";
		}

		return "";
	}

	public void updateUser() {
		try {
			if (user.getId() == 0) {
				createUser();
			} else {
				UserFacade uf = getUserFacade();
				uf.updateUsuario(user);
				closeDialog();
				displayInfoMessageToUser("Atualizado com sucesso!");
				loadUsers();
				resetUser();

				// retorna para a listagem de usuários
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				context.redirect("listarUsuarios.xhtml");
			}
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void mudarMinhaSenha() {
		if (usuarioLogado.getPassword().equals(Criptografia.criptografa(senhaDigitada))) {
			usuarioLogado.setPasswordSemCriptografia(getNovasenha());
			user = usuarioLogado;
			UserFacade uf = getUserFacade();
			uf.updateUsuario(user);
			closeDialog();
			displayInfoMessageToUser("Atualizado com sucesso!");
			loadUsers();
			resetUser();
		} else {
			closeDialog();
			displayErrorMessageToUser("Você não digitou sua senha corretamente!");
		}
	}

	private void loadUsers() {
		usuarios = getUserFacade().listAll();
	}

	public void resetUser() {
		user = new User();
	}
}