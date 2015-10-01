package com.mb;

import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.facade.UserFacade;
import com.model.User;

@RequestScoped
@ManagedBean
public class LoginMB extends AbstractMB {
	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;

	private String login;
	private String password;

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
		ResourceBundle bundle = ResourceBundle.getBundle("messages");
		
		UserFacade userFacade = new UserFacade();

		User usuario = new User();
		usuario = userFacade.isValidLogin(login, password);
		
		if(usuario != null){
			userMB.setUser(usuario);
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			request.getSession().setAttribute("user", usuario);
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user",user);
			MessagesView ms = new MessagesView();
	        ms.info(usuario.getName(), "Bem vindo!");
			return "/restrito/home.xhtml";
		
		}

		displayErrorMessageToUser(bundle.getString("verificarUsuarioESenha"));
		
		return "/erro.xhtml";
	}

	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}	
}