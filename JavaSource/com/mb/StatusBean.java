package com.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import com.model.Status;

@ManagedBean
public class StatusBean {

	//private Map<String, String> statusList = new HashMap<String, String>();
	private List<Status> statusList = new ArrayList<Status>();
	

	private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@PostConstruct
	public void init(){
			
	}
	
	public List<Status> getStatusList() {
		statusList = new ArrayList<Status>(Arrays.asList(Status.values()));
		return statusList;
	}
	
	public List<Status> getStatusAberto() {
		statusList = new ArrayList<Status>(Arrays.asList(Status.ABERTO));
		return statusList;
	}

/*	public void setStatusList(Map<String, String> statusList) {
		this.statusList = statusList;
	}*/
}
