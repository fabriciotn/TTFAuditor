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

/* Primeira parte */
public class RelatorioAuditoria {

	private Connection connection;
	Calendar data = Calendar.getInstance();

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

	public Connection getConection() {
		ResourceBundle bundle = ResourceBundle.getBundle("messages");
		String senhaBanco = bundle.getString("senhaBanco");
		
		String url = "jdbc:mysql://localhost/auditoria_db";
		String usuario = "root";
		String senha = senhaBanco;
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, usuario, senha);
			System.out.println("Conexão obtida com sucesso.");

			// a conexão foi obtida com sucesso?
			if (conn != null) {
				System.out.println("conexao ok");
			}
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		} catch (Exception e) {
			System.out
					.println("Problemas ao tentar conectar com o banco de dados: "
							+ e);
		}
		
		return conn;
	}
}