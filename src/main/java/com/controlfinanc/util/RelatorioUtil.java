package com.controlfinanc.util;

import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.util.Map;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class RelatorioUtil {  
    public static void abrirPDF(String caminho, Map<String, Object> parametros, Connection con) {
    try {
        // Carregar o relatório .jasper
        JasperReport relatorio = (JasperReport) JRLoader.loadObjectFromFile(caminho);

        // Preenche os dados
        JasperPrint print = JasperFillManager.fillReport(relatorio, parametros, con);

        // Criar o arquivo temporario
        File pdfTemp = File.createTempFile("relatorio_", ".pdf");
        pdfTemp.deleteOnExit();

        // Exportar o relatório para o arquivo temporário
        JasperExportManager.exportReportToPdfFile(print, pdfTemp.getAbsolutePath());

        // Abrir o pdf no aplicativo padrão do usuário
        Desktop.getDesktop().open(pdfTemp);
        
    } catch (Exception e) {
        e.printStackTrace();
    }
  }
}
