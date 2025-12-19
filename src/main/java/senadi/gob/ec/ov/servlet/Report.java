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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import senadi.gob.ec.ov.model.Person;
import senadi.gob.ec.ov.model.PersonVegetable;
import senadi.gob.ec.ov.model.VegetableForms;
import senadi.gob.ec.ov.util.Controller;
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

            //aquí estoy obteniendo la lista de personas
            Controller c = new Controller();
            List<Person> applicants = new ArrayList<>();
            List<Person> obtentors = new ArrayList<>();
            List<Person> personn = new ArrayList<>();
            VegetableForms vf = c.getVegetableFormsById(idVegetableForms);
            int conta = 1;
            int conto = 1;
            int contn = 1;
            for (int i = 0; i < vf.getPersonVegetables().size(); i++) {
                PersonVegetable pv = vf.getPersonVegetables().get(i);
                Person peraux = c.getPersonById(pv.getPerson().getId());
                if (peraux.getCityAddress() != null && peraux.getCityAddress() > 0) {
                    peraux.setProvince(c.getProvinceIdByProvinceId(c.getProvinceIdByCityId(peraux.getCityAddress())).getName());
                    peraux.setCity(c.getCityByCityId(peraux.getCityAddress()).getName());
                }
                switch (pv.getPersonType()) {
                    case APPLICANT:
                        peraux.setPersonNumber(conta+"");
                        applicants.add(peraux);
                        conta++;
                        break;
                    case BREEDER:
                        peraux.setPersonNumber(conto+"");
                        obtentors.add(peraux);
                        conto++;
                        break;
                    default:
                        peraux.setPersonNumber(contn+"");
                        personn.add(peraux);
                        contn++;
                        break;
                }
            }
            
            
            JRBeanCollectionDataSource applicantsDS = new JRBeanCollectionDataSource(applicants);
            JRBeanCollectionDataSource obtentorsDS = new JRBeanCollectionDataSource(obtentors);
            JRBeanCollectionDataSource personnDS = new JRBeanCollectionDataSource(personn);
            //fin lista de personas

            parametro.put("applicants", applicantsDS);
            parametro.put("obtentors", obtentorsDS);
            parametro.put("personn", personnDS);
            parametro.put("geographic_origin_country",c.getCountryById(vf.getGeographicOrigin()).getName());
            
            

            JasperPrint jasperPrint = JasperFillManager.fillReport(reportePrincipal, parametro, conn);
            if (jasperPrint.getPages().isEmpty()) {
                return null;
            }

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
