package com.mb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.facade.AuditoriaFacade;
import com.model.Auditoria;
import com.model.Questionario;
import com.model.User;

@RequestScoped
@ManagedBean
public class AuditoriaMB extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Auditoria auditoria;
	private List<Auditoria> auditorias;
	private AuditoriaFacade auditoriaFacade;
	private User usuarioLogado;
	private TreeNode root;

	public AuditoriaMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");
		
		carrgaTreeNode();
	}

	private void carrgaTreeNode() {
		root = new DefaultTreeNode("Root", null);
		TreeNode node0 = new DefaultTreeNode("INFORMAÇÕES GERAIS", root);
		TreeNode node1 = new DefaultTreeNode("CAPTAÇÃO", root);
		TreeNode node2 = new DefaultTreeNode("COLETA DE AMOSTRAS E CADASTRO", root);
		TreeNode node3 = new DefaultTreeNode("IMUNO RECEPTOR", root);
		TreeNode node4 = new DefaultTreeNode("TRANSFUSÃO", root);
		TreeNode node5 = new DefaultTreeNode("GARANTIA DA QUALIDADE", root);
		TreeNode node6 = new DefaultTreeNode("COMITÊ - HEMOVIGILÂNCIA", root);
		TreeNode node7 = new DefaultTreeNode("ESTRUTURA FÍSICA - INSTALAÇÕES", root);
		TreeNode node8 = new DefaultTreeNode("FATURAMENTO", root);

		node0.getChildren().add(new DefaultTreeNode("PERGUNTA 1"));
		node0.getChildren().add(new DefaultTreeNode("PERGUNTA 2"));
		node0.getChildren().add(new DefaultTreeNode("PERGUNTA 3"));
		
		node1.getChildren().add(new DefaultTreeNode("PERGUNTA 1"));
		node1.getChildren().add(new DefaultTreeNode("PERGUNTA 2"));
		node1.getChildren().add(new DefaultTreeNode("PERGUNTA 3"));
		
		node2.getChildren().add(new DefaultTreeNode("PERGUNTA 1"));
		node2.getChildren().add(new DefaultTreeNode("PERGUNTA 2"));
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public AuditoriaFacade getAuditoriaFacade() {
		if (auditoriaFacade == null) {
			auditoriaFacade = new AuditoriaFacade();
		}

		return auditoriaFacade;
	}

	public Auditoria getAuditoria() {
		if (auditoria == null) {
			auditoria = new Auditoria();
		}

		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public String createAuditoria() {
		try {
			auditoria.setUser(usuarioLogado);

			getAuditoriaFacade().createAuditoria(auditoria);
			closeDialog();
			displayInfoMessageToUser("Auditoria gravada com sucesso!");
			loadAuditorias();
			resetAuditoria();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
			return "/restrito/erro.xhtml";
		}
		return "/restrito/cadastrarAuditoria.xhtml";
	}

	public void updateAuditoria() {
		try {
			// auditoria.setTelefone(RetiraMascaras.retirar(auditoria.getTelefone()));
			// auditoria.setCelular(RetiraMascaras.retirar(auditoria.getCelular()));

			if (auditoria.getId() == 0) {
				createAuditoria();
			} else {
				auditoria.setUser(usuarioLogado);
				getAuditoriaFacade().updateAuditoria(auditoria);
				closeDialog();
				displayInfoMessageToUser("Atualizado com sucesso!");
				loadAuditorias();
				resetAuditoria();

				// retorna para a listagem de Auditorias
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				context.redirect("gerenciarAuditorias.xhtml");
			}
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteAuditoria() {
		try {
			getAuditoriaFacade().deleteAuditoria(auditoria);
			closeDialog();
			displayInfoMessageToUser("Deletado com sucesso!");
			loadAuditorias();
			resetAuditoria();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteAuditoria(String id) {
		int idAuditoria = Integer.parseInt(id);
		auditoria = auditoriaFacade.findAuditoria(idAuditoria);
		deleteAuditoria();
	}

	public List<Auditoria> getAllAuditorias() {
		if (auditorias == null) {
			loadAuditorias();
		}
		
		return auditorias;
	}

	private void loadAuditorias() {
		auditorias = getAuditoriaFacade().listAll();
	}

	public void resetAuditoria() {
		auditoria = new Auditoria();
	}

	public Auditoria pesquisaAuditoria() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

		int id = (Integer) session.getAttribute("id");
		int auditoriaId = id;
		auditoria = auditoriaFacade.findAuditoria(auditoriaId);

		return auditoria;
	}

	public Date getHoje() {
		return new Date();
	}
}
