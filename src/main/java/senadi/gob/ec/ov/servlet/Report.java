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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import senadi.gob.ec.ov.model.ExploitedSelled;
import senadi.gob.ec.ov.model.Person;
import senadi.gob.ec.ov.model.PersonVegetable;
import senadi.gob.ec.ov.model.VegetableForms;
import senadi.gob.ec.ov.model.VegetablePriority;
import senadi.gob.ec.ov.model.VegetableProtection;
import senadi.gob.ec.ov.model.enums.ExplotationType;
import senadi.gob.ec.ov.model.enums.ProtectionType;
import senadi.gob.ec.ov.solicitudes.PaymentRates;
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

        } catch (ClassNotFoundException | SQLException ex) {
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

    public FileInputStream generatePDFVoucher(String path, InputStream rutaJrxml, String rutapdf, VegetableForms ve) throws JRException {

        JasperReport reportePrincipal = JasperCompileManager.compileReport(rutaJrxml);

        List<Person> applicants = ve.getPersonsType("APPLICANT");
        String clientes = "";
        for (Person applicant : applicants) {
            clientes += applicant.getName() + "; ";
        }
        clientes = clientes.substring(0, clientes.length() - 2);

        Controller c = new Controller();
        PaymentRates pr = c.getPaymentRatesById(ve.getFormPaymentRate().getPaymentRateId());

        Map parametro = new HashMap();
        parametro.put("idv", ve.getId());
        parametro.put("SUBREPORT_DIR", path + "/");
        parametro.put("clientes", clientes);
        parametro.put("concepto", pr.getDescription());
        parametro.put("tasa", pr.getPaymentCode());

        double value = pr.getValue();

        if (ve.getDiscountFile() != null && !ve.getDiscountFile().trim().isEmpty()) {
            double valor = value - (value * (pr.getDiscount() / 100));
            BigDecimal bd = BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP);
            value = bd.doubleValue();
        }

        parametro.put("unitario", value + "");
        parametro.put("total", value + "");

        return createFileInputStream(reportePrincipal, parametro, rutapdf);

    }

    public FileInputStream createFileInputStream(JasperReport jasper, Map parameters, String rutapdf) {
        FileInputStream entrada;

        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameters, conn);
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

    public FileInputStream generatePDFVegetableForms(String path, InputStream rutaJrxml, String rutapdf, Integer idVegetableForms) {
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
                        peraux.setPersonNumber(conta + "");
                        applicants.add(peraux);
                        conta++;
                        break;
                    case BREEDER:
                        peraux.setPersonNumber(conto + "");
                        obtentors.add(peraux);
                        conto++;
                        break;
                    default:
                        peraux.setPersonNumber(contn + "");
                        personn.add(peraux);
                        contn++;
                        break;
                }
            }
            //tab 7
            List<VegetableProtection> protections = vf.getVegetableProtections();
            List<VegetableProtection> protsBreeder = new ArrayList<>();
            List<VegetableProtection> protsPatent = new ArrayList<>();
            List<VegetableProtection> protsCult = new ArrayList<>();
            int contbreed = 1;
            int contpat = 1;
            int contcult = 1;
            for (int i = 0; i < protections.size(); i++) {
                protections.get(i).setCountry(c.getCountryById(protections.get(i).getSubmissionCountryId()).getName());

                ProtectionType pt = protections.get(i).getProtectionType();
                switch (pt) {
                    case THROUGH_BREEDER_RIGHT:
                        protections.get(i).setProtection("Protección mediante Derecho de Obtentor");
                        protections.get(i).setProtectionNumber(contbreed + "");
                        protsBreeder.add(protections.get(i));
                        contbreed++;
                        break;
                    case THROUGH_PATENT:
                        protections.get(i).setProtection("Protección mediante Patente");
                        protections.get(i).setProtectionNumber(contpat + "");
                        protsPatent.add(protections.get(i));
                        contpat++;
                        break;
                    default:
                        protections.get(i).setProtection("Registro de Cultivares");
                        protections.get(i).setProtectionNumber(contcult + "");
                        protsCult.add(protections.get(i));
                        contcult++;
                        break;
                }
                System.out.println("protection " + (i + 1) + ": " + protections.get(i).getProtection());
            }

            //tab 8
            VegetablePriority vp = vf.getVegetablePriority();
            List<ExploitedSelled> interr = new ArrayList<>();
            List<ExploitedSelled> outerr = new ArrayList<>();
            List<VegetablePriority> priorities = new ArrayList<>();
            if (vp != null && vp.getId() != null) {
                vp.setCountry(c.getCountryById(vp.getCountryId()).getName());
                priorities.add(vp);

                for (int i = 0; i < vf.getExploitedSelleds().size(); i++) {
                    vf.getExploitedSelleds().get(i).setCountry(c.getCountryById(vf.getExploitedSelleds().get(i).getCountryId()).getName());
                    if (vf.getExploitedSelleds().get(i).getExplotationType().equals(ExplotationType.IN_ANDEAN_SUBREGION)) {
                        interr.add(vf.getExploitedSelleds().get(i));
                    } else {
                        outerr.add(vf.getExploitedSelleds().get(i));
                    }
                }
            }

            JRBeanCollectionDataSource applicantsDS = new JRBeanCollectionDataSource(applicants);
            JRBeanCollectionDataSource obtentorsDS = new JRBeanCollectionDataSource(obtentors);
            JRBeanCollectionDataSource personnDS = new JRBeanCollectionDataSource(personn);

            JRBeanCollectionDataSource protectionBDS = new JRBeanCollectionDataSource(protsBreeder);
            JRBeanCollectionDataSource protectionPDS = new JRBeanCollectionDataSource(protsPatent);
            JRBeanCollectionDataSource protectionCDS = new JRBeanCollectionDataSource(protsCult);

            JRBeanCollectionDataSource priorityDS = new JRBeanCollectionDataSource(priorities);

            JRBeanCollectionDataSource interrDS = new JRBeanCollectionDataSource(interr);
            JRBeanCollectionDataSource outerrDS = new JRBeanCollectionDataSource(outerr);
            //fin lista de personas

            parametro.put("applicants", applicantsDS);
            parametro.put("obtentors", obtentorsDS);
            parametro.put("personn", personnDS);
            parametro.put("geographic_origin_country", c.getCountryById(vf.getGeographicOrigin()).getName());
            parametro.put("protectionsb", protectionBDS);
            parametro.put("protb", !protsBreeder.isEmpty());
            parametro.put("protectionsp", protectionPDS);
            parametro.put("protp", !protsPatent.isEmpty());
            parametro.put("protectionsc", protectionCDS);
            parametro.put("protc", !protsCult.isEmpty());
            parametro.put("priorities", priorityDS);
            parametro.put("interr", interrDS);
            parametro.put("outerr", outerrDS);
            parametro.put("country_exam", c.getCountryById(vf.getCountryExam()).getName());
            parametro.put("country_sample", c.getCountryById(vf.getCountryLivingSample()).getName());

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
