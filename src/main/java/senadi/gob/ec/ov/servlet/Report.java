/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package senadi.gob.ec.ov.servlet;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import senadi.gob.ec.ov.model.Person;
import senadi.gob.ec.ov.util.Parameter;


/**
 *
 * @author michael
 */
public class Report implements Serializable {

    private Connection conn;

    public Report() {
        try {
            
            Class.forName("com.mysql.jdbc.Driver"); //se carga el driver
            String url = "jdbc:mysql://" + Parameter.HOST_DB + "/" + Parameter.DATABASE + "?serverTimezone=GMT-5&autoReconnect=true&useSSL=false";
            conn = DriverManager.getConnection(url, Parameter.USER_DB, Parameter.PASSWORD_DB);

        } catch (Exception ex) {
            System.out.println("Error conexion: " + ex);
            ex.printStackTrace();
        }
    }

    /*Cierra la conexión a la base de datos mysql*/
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public FileInputStream viewVegetableForms(String path, InputStream rutaJrxml, String rutapdf, Integer idVegetableForms) {
        try {
            FileInputStream entrada;
            JasperReport reportePrincipal = JasperCompileManager.compileReport(rutaJrxml);

            Map parametro = new HashMap();
            parametro.put("id", idVegetableForms);
            parametro.put("SUBREPORT_DIR", path + "/");

            JasperPrint jasperPrint = JasperFillManager.fillReport(reportePrincipal, parametro, conn);
            if (jasperPrint.getPages().isEmpty()) {
                return null;
            }
            
            List<Person> applicants = new ArrayList<>();
            

            DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance();

            try (OutputStream out = new FileOutputStream(rutapdf + ".pdf")) {
                JRPdfExporter exporter = new JRPdfExporter();
                SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                ExporterInput inp = new SimpleExporterInput(jasperPrint);
                configuration.setCreatingBatchModeBookmarks(true);
                configuration.set128BitKey(Boolean.TRUE);

                exporter.setConfiguration(configuration);
                exporter.setExporterInput(inp);
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));

                exporter.exportReport();

            }
            entrada = new FileInputStream(rutapdf + ".pdf");
            return entrada;
        } catch (Exception ex) {
            System.out.println("Error print breeder_form: " + ex);
            return null;
        }
    } 
}
