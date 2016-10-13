package com.Reports;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Classe respons�vel pela gera��o do Relat�rio de auditorias
 * @author TTF Inform�tica
 *
 */
public class RelatorioAuditoria {

	private Connection connection;
	Calendar data = Calendar.getInstance();

	/**
	 * Metodo respos�vel pela gera��o do relat�rio de auditorias
	 * @param usuarioLogado
	 * @param id_auditoria
	 * @param id_estabelecimento
	 * @return arrayDeBytes
	 */
	public byte[] imprimeRelatorio(String usuarioLogado, int id_auditoria, int id_estabelecimento) {
		byte[] relatorio = null;
		try {
			URL arquivo = getClass().getResource("relatorioAuditoriaGeral.jasper");
			JasperReport arquivoJasper = (JasperReport) JRLoader
					.loadObject(arquivo);
			this.connection = getConection();
			
			String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("SUBREPORT_DIR", path + "/WEB-INF/classes/com/Reports/");
			map.put("usuario_logado", usuarioLogado);
			map.put("id_auditoria", id_auditoria);
			map.put("id_estabelecimento", id_estabelecimento);
			map.put("caminho_imagem", path + "images/logoHemominas.png");
			relatorio = JasperRunManager.runReportToPdf(arquivoJasper, map,
					this.connection);
			//JasperPrint jp = JasperFillManager.fillReport(arquivoJasper, map, this.connection);

            //JasperViewer.viewReport(jp, false);
            //JasperPrintManager.printReport(jp,true);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return relatorio;
	}

	/**
	 * Realiza a conex�o com o banco exclusivamente para o relat�rio
	 * @return Connection
	 */
	public Connection getConection() {
		ResourceBundle bundle = ResourceBundle.getBundle("messages");
		String senhaBanco = bundle.getString("senhaBanco");
		String urlBanco = bundle.getString("url");
		
		String url = urlBanco;
		String usuario = "root";
		String senha = senhaBanco;
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, usuario, senha);

			// a conex�o foi obtida com sucesso?
			if (conn != null) {
				//System.out.println("conexao ok");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}