package com.mb;

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

	public void setLogin(String email) {
		this.login = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String login() {
		UserFacade userFacade = new UserFacade();

		User user = new User();
		user = userFacade.isValidLogin(login, password);
		
		if(user != null){
			userMB.setUser(user);
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			request.getSession().setAttribute("user", user);
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user",user);
			MessagesView ms = new MessagesView();
	        ms.info(user.getName(), "Bem vindo!");
			return "/restrito/home.xhtml";
		
		}

		displayErrorMessageToUser("Check your email/password");
		
		return "/erro.xhtml";
	}

	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}	
}