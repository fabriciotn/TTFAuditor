package com.mb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.facade.ProjetoFacade;
import com.model.Projeto;
import com.model.Setor;

@ViewScoped
@ManagedBean
public class ProjetoMB extends AbstractMB implements Serializable {
	public List<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

	private static final long serialVersionUID = 1L;

	private Projeto projeto;
	private List<Projeto> projetos;
	private ProjetoFacade projetoFacade;

	public ProjetoFacade getProjetoFacade() {
		if (projetoFacade == null) {
			projetoFacade = new ProjetoFacade();
		}

		return projetoFacade;
	}

	public Projeto getProjeto() {
		if (projeto == null) {
			projeto = new Projeto();
		}

		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public void createProjeto() {
		try {
			getProjetoFacade().createProjeto(projeto);
			closeDialog();
			displayInfoMessageToUser("Created With Sucess");
			loadProjetos();
			resetProjeto();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}
	
	public void updateProjeto() {
		try {
			getProjetoFacade().updateProjeto(projeto);
			closeDialog();
			displayInfoMessageToUser("Updated With Sucess");
			loadProjetos();
			resetProjeto();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}
	
	public void deleteProjeto() {
		try {
			getProjetoFacade().deleteProjeto(projeto);
			closeDialog();
			displayInfoMessageToUser("Deleted With Sucess");
			loadProjetos();
			resetProjeto();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}

	public List<Projeto> getAllProjetos() {
		if (projetos == null) {
			loadProjetos();
		}

		return projetos;
	}
	
	public Map<String, String> exibeTotosProjetos(){
		Map<String, String> lista = new HashMap<String, String>();
		List<Projeto> listaProjetos = getAllProjetos();
		for (Projeto projeto : listaProjetos) {
			lista.put(projeto.getNome(), projeto.getNome());
		}
		
		return lista;
	}

	private void loadProjetos() {
		projetos = getProjetoFacade().listAll();
	}

	public void resetProjeto() {
		projeto = new Projeto();
	}
}