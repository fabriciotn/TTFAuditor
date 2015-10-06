package com.mb;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.http.HttpServletRequest;

import com.facade.PendenciaFacade;
import com.facade.UserFacade;
import com.model.Pendencia;
import com.model.User;

@SessionScoped
@ManagedBean(name="userMB")
public class UserMB implements Serializable {
	public static final String INJECTION_NAME = "#{userMB}";
	private static final long serialVersionUID = 1L;

	private User user;
	private List<User> usuarios;
	private UserFacade userFacade;
	
	
	public List<User> getAllUsuarios() {
		if (usuarios == null) {
			loadUsuarios();
		}

		return usuarios;	}
	
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

	public boolean isDefaultUser() {
		return user.isUser();
	}

	public void logOut() throws IOException {
		getRequest().getSession().invalidate();
		FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		//return "/restrito/index.xhtml";
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}