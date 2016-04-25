package com.mb;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.facade.ParametrosFacade;
import com.facade.UserFacade;
import com.model.User;

@SessionScoped
@ManagedBean
public class LoginMB extends AbstractMB implements Serializable {

	private static final long	serialVersionUID	= 1L;

	private String				login;
	private String				password;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String login() {
		String home = "/restrito/home.xhtml";
		String error = "/erro.xhtml";
		return realizarLogin(home, error);
	}

	public String loginMobile() {
		String home = "/restrito/listarAuditorias.xhtml";
		String error = "/erro.xhtml";
		return realizarLogin(home, error);
	}

	private String realizarLogin(String home, String error) {
		ResourceBundle bundle = ResourceBundle.getBundle("messages");

		UserFacade userFacade = new UserFacade();

		User usuario = new User();
		usuario = userFacade.isValidLogin(login, password, false);

		if (usuario != null) {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			request.getSession().setAttribute("user", usuario);
			request.getSession().setAttribute("user_name", usuario.getName());
			request.getSession().setAttribute("parametros", new ParametrosFacade().findParametros(1));			
			MessagesView ms = new MessagesView();
			String mensagem = MessageFormat.format(bundle.getString("loginWelcomeMessage"), usuario.getName());
			ms.info(null, mensagem);
			return home;

		}

		displayErrorMessageToUser(bundle.getString("verificarUsuarioESenha"));

		return error;
	}
}