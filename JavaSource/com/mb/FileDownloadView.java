package com.mb;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.util.AbreTelaDeIteracao;

@ManagedBean(name = "treeBasicView")
@ViewScoped
public class FileDownloadView implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_BUFFER_SIZE = 10240;
	private TreeNode root;
	
	public FileDownloadView() {
		AbreTelaDeIteracao abreTela = new AbreTelaDeIteracao();
		abreTela.abreTela();
	}

	@PostConstruct
	public void init() {
		root = new DefaultTreeNode("Root", null);
		TreeNode node0;

		List<String> lista = getListaDeArquivos();

		if (lista == null) {
			node0 = new DefaultTreeNode("", root);
		} else {
			for (String node : lista) {
				node0 = new DefaultTreeNode(node, root);
			}
		}
	}

	public TreeNode getRoot() {
		return root;
	}

	public List<String> getListaDeArquivos() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
		String dir = scontext.getRealPath("/WEB-INF/anexos/PendenciaId_" + AbreTelaDeIteracao.ID_PENDENCIA_PESQUISADA);

		List<String> listaDeArquivos = new ArrayList<String>();

		File diretorio = new File(dir);
		File fList[] = diretorio.listFiles();

		// System.out.println("Numero de arquivos no diretorio : " +
		// fList.length);

		if (fList == null) {
			return null;
		}

		for (int i = 0; i < fList.length; i++) {
			System.out.println(fList[i].getName());
			listaDeArquivos.add(fList[i].getName());
		}

		return listaDeArquivos;
	}

	public void downLoad(String path) throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		ServletContext scontext = (ServletContext) context.getExternalContext().getContext();
		String dir = scontext.getRealPath("/WEB-INF/anexos/PendenciaId_" + AbreTelaDeIteracao.ID_PENDENCIA_PESQUISADA);

		File file = new File(dir + path);
		if (!file.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		response.reset();
		response.setBufferSize(DEFAULT_BUFFER_SIZE);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		try {
			input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
			output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
		} finally {
			input.close();
			output.close();
		}
		context.responseComplete();
	}
}
