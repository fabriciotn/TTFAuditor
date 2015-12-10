package com.Reports;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.view.JasperViewer;

/* Primeira parte */
public class RelatorioAuditoria {


   private Connection con = null;
   private String driver = "oracle.jdbc.driver.OracleDriver";
   private String endereco = "jdbc:oracle:thin:@host:1521:sid";
   private String user = "user";
   private String pass = "passw";
   private ResultSet rs = null;
   private String valores[] = new String[10];
   private int chamada = 1;
   private String dir = System.getProperty("user.dir") + "/web/";
   private StringReader stream;
   /*Segunda parte */

   public RelatorioAuditoria() {
      /* Efetua a conexao a base de dados e coleta os valores da base de dados armazenando-os
       em um array para ser futuramente utilizado */
      try {
         if (con == null) {
            Class.forName(driver);
            con = DriverManager.getConnection(endereco, user, pass);
            Statement statement = con.createStatement();
            rs = statement.executeQuery("select * from chamado");
            HashMap teste = new HashMap();
            while(rs.next()) {
               //for até o numero de campos da tabela
               for (int i = 1; i < 10; i++) {
                  valores[i] = rs.getString(i);
               }
            }
         }
      }
      catch (Exception e) {
         System.err.println("Problemas apresentados na operacao de conexao");
         e.printStackTrace();
      }
      /* Inicio do bloco que ira gerar nossos relatorios e 3ª parte */
      try {
         //String array[] = valores;
         JasperDesign design = JasperManager.loadXmlDesign(dir + "relatorios/relatorio.jrxml");
         JasperReport jr = JasperManager.compileReport(design);
         HashMap parameters = new HashMap();
         parameters.put("PAR_PEDID",1);
         //parameters.put("PARAMETRO_2", array[2]);
         //parameters.put("PARAMETRO_3", array[5]);
         //parameters.put("PARAMETRO_4", array[4]);
         //parameters.put("PARAMETRO_5",array[9]);
         JasperPrint impressao = JasperManager.fillReport(jr,parameters,con);
         JasperViewer jrviewer = new JasperViewer(impressao,false);
         jrviewer.show();
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }
   /* Aqui chamamos o construtor de nossa classe para exibirmos o relatorio e 4ª parte*/
   public static void main (String args[]) {
      new RelatorioAuditoria();
      System.out.println("ok");
   }
}