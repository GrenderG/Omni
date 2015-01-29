/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.visual;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import omni.controller.AccessReportInfo;

/**
 *
 * @author Campus
 */
public class ReportViewer {
    
    private Enumeration <String> nameEnum;
    private Enumeration <Integer> timesEnum;
    
    private ArrayList <AccessReportInfo> accessReportInfo = new ArrayList <AccessReportInfo>();
    
    /**
     * Creates new form ReportViewer
     */
    public ReportViewer(Hashtable <String, Integer> accesosCount) {
        
        nameEnum = accesosCount.keys();
        timesEnum = accesosCount.elements();
        
        while (nameEnum.hasMoreElements()) {
            accessReportInfo.add(new AccessReportInfo
                (nameEnum.nextElement(), timesEnum.nextElement()));
        }       
        
        generatePDF();
        
    }
       
    private void generatePDF() {
        try {
            JasperReport report = (JasperReport) JRLoader.loadObjectFromFile("viewProfileReport.jasper");
            
            JasperPrint jasperPrint = JasperFillManager.fillReport
                (report, null, new JRBeanCollectionDataSource(accessReportInfo));

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }
}
