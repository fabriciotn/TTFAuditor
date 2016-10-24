package com.mb;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import com.facade.QuestionarioFacade;
import com.facade.UserFacade;
import com.model.Questionario;
import com.model.User;
import com.util.Criptografia;

/**
 * Managed Bean para gestão dos usuários
 * @author TTF Informática
 *
 */
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
	private boolean offLine;

	/**
	 * Verifica se o método é off-line
	 * @return
	 */
	public boolean isOffLine() {
		return offLine;
	}

	public void setOffLine(boolean offLine) {
		this.offLine = offLine;
	}

	public UserMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		
		ResourceBundle bundle = ResourceBundle.getBundle("messages");
		setOffLine(bundle.getString("modulo").equals("off"));

		habilitaMenuCadastros();
		habilitaMenuAuditorias();
		habilitaMenuPreparacao();
		
		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");
	}

	/**
	 * Carrega os questionários de acordo com a permissão do usuário
	 */
	private void loadQuestionarios() {
		if (questionarios == null) {
			questionarios = new ArrayList<Questionario>();
			
			if(usuarioLogado.isAdmin())
				questionarios = new QuestionarioFacade().listAll();
			else
				questionarios = usuarioLogado.getQuestionarios();
		}
	}

	private void habilitaMenuPreparacao() {
		if (usuarioLogado != null && !usuarioLogado.isMenuCadAuditorias() && !usuarioLogado.isMenuCadPerguntas() && !usuarioLogado.isMenuCadQuestionarios()) {
			menuPreparacao = false;
		} else {
			menuPreparacao = true;
		}
	}

	private void habilitaMenuAuditorias() {
		if (usuarioLogado != null && !usuarioLogado.isMenuAuditar() && !usuarioLogado.isMenuCadAuditorias() && !usuarioLogado.isMenuCadPerguntas()
				&& !usuarioLogado.isMenuCadQuestionarios()) {
			menuAuditorias = false;
		} else {
			menuAuditorias = true;
		}
	}

	private void habilitaMenuCadastros() {
		if (usuarioLogado != null && !usuarioLogado.isMenuCadUnidades() && !usuarioLogado.isMenuCadEstabelecimentos()) {
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
		loadQuestionarios();
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

	/**
	 * Método para realizar a troca da senha do usuário logado
	 */
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