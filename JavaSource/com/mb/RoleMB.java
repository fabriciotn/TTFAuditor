package com.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import com.model.Role;

@ManagedBean
public class RoleMB {

	private List<Role> roleList = new ArrayList<Role>();
	private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@PostConstruct
	public void init(){
			
	}
	
	public List<Role> getRoleList() {
		roleList = new ArrayList<Role>(Arrays.asList(Role.values()));
		return roleList;
	}
}
