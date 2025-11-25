/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.util;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import senadi.gob.ec.ov.bean.LoginBean;
import senadi.gob.ec.ov.bean.solicitudes.City;
import senadi.gob.ec.ov.bean.solicitudes.Country;
import senadi.gob.ec.ov.bean.solicitudes.Owners;
import senadi.gob.ec.ov.bean.solicitudes.OwnersDAO;
import senadi.gob.ec.ov.bean.solicitudes.PersonDAO;
import senadi.gob.ec.ov.bean.solicitudes.Province;
import senadi.gob.ec.ov.dao.MethodologyDAO;
import senadi.gob.ec.ov.dao.VegetableAnnexesDAO;
import senadi.gob.ec.ov.dao.VegetableFormsDAO;
import senadi.gob.ec.ov.model.Methodology;
import senadi.gob.ec.ov.model.Person;
import senadi.gob.ec.ov.model.VegetableAnnexes;
import senadi.gob.ec.ov.model.VegetableForms;

/**
 *
 * @author michael
 */
public class Controller {

    public Owners getOwnersByLogin(String document, String pass) {
        OwnersDAO od = new OwnersDAO();
        return od.getOwnersByLogin(document, pass);
    }

    public List<VegetableForms> getVegetableFormsByOwnerId(Integer ownerId) {
        VegetableFormsDAO vd = new VegetableFormsDAO(null);
        return vd.getVegetableFormsByOwnerId(ownerId);
    }

    public LoginBean getLogin() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        LoginBean loginB = (LoginBean) session.getAttribute("loginBean");
        return loginB;
    }

    public Person getPersonFromSolicitudes(Person person) {
        PersonDAO pd = new PersonDAO();
        return pd.getPersonFromSolicitudes(person);
    }

    public List<Country> getCountries() {
        PersonDAO fd = new PersonDAO();
        return fd.getCountries();
    }

    public List<Province> getProvincesByCountryId(Integer countryId) {
        PersonDAO fd = new PersonDAO();
        return fd.getProvincesByCountryId(countryId);
    }

    public List<City> getCitiesByProvinceId(Integer provinceId) {
        PersonDAO fd = new PersonDAO();
        return fd.getCitiesByCountryId(provinceId);
    }

    public Person getPersonByIdentificationAndType(String identification, String type) {
        PersonDAO md = new PersonDAO();
        return md.getPersonByIdentificationAndType(identification, type);
    }

    public Country getCountryById(Integer countryId) {
        PersonDAO fd = new PersonDAO();
        return fd.getCountryById(countryId);
    }

    public Integer getProvinceIdByCityId(Integer cityId) {
        PersonDAO fd = new PersonDAO();
        return fd.getProvinceIdByCityId(cityId);
    }

    public Province getProvinceIdByProvinceId(Integer provinceId) {
        PersonDAO fd = new PersonDAO();
        return fd.getProvinceByProvinceId(provinceId);
    }

    public City getCityByCityId(Integer cityId) {
        PersonDAO fd = new PersonDAO();
        return fd.getCityByCityId(cityId);
    }

    public List<Person> getPersonByCriteriaAndType(String criteria, String type) {
        PersonDAO fd = new PersonDAO();
        return fd.getPersonByCriteriaAndType(criteria, type);
    }

    public Person getPersonById(Integer id) {
        PersonDAO md = new PersonDAO();
        return md.getPersonById(id);
    }

    public Integer savePerson(Person person) {
        PersonDAO pd = new PersonDAO();
        return pd.savePerson(person);
    }

    public boolean updatePerson(Person person) {
        PersonDAO pd = new PersonDAO();
        return pd.updatePerson(person);
    }

    public List<Methodology> getMethodologies() {
        MethodologyDAO md = new MethodologyDAO(null);
        return md.buscarTodos();
    }

    public List<VegetableAnnexes> getVegetableAnnexes() {
        VegetableAnnexesDAO vd = new VegetableAnnexesDAO(null);
        return vd.buscarTodos();
    }

    public boolean createMethodologies() {
        if (getMethodologies().isEmpty()) {
            String[] metodologies = {
                "Selección masal (Especies Autógamas)", "Selección individual o genealógica (Especies Autógamas)",
                "Hibridación en especies autógamas (Especies Autógamas)", "Método genealógico o pedigrí (Especies Autógamas)",
                "Método de la descendencia de semilla única o S.S.D (Especies Autógamas)",
                "Método de la descendencia de semilla múltiple o M.S.D (Especies Autógamas)",
                "Método de la descendencia de semilla colina o S.H.D (Especies Autógamas)",
                "Método de poblaciones híbridas o bulk population (Especies Autógamas)",
                "Producción de dobles haploides o di-haploides (Especies Autógamas)",
                "Mejoramiento por retrocruzamiento (BACK CROSS) (Especies Autógamas)",
                "Variedades multilíneas (Especies Autógamas)", "Selección recurrente (Especies Autógamas)",
                "Selección recurrente usando androesterilidad-macho-esterilidad (Especies Autógamas)",
                "Otro: por favor, especifíquese (Especies Autógamas)",
                "Selección intrapoblacional (Especies Alógamas)", "Selección interpoblacional (Especies Alógamas)",
                "Hibridación entre líneas endocriadas (Especies Alógamas)",
                "Otro: por favor, especifique (Especies Alógamas)", "Mutaciones inducidas",
                "OGM (Transgénico)", "Nuevas Técnicas de Mejoramiento - NBT (SDN, ODM, Cisgénesis, RdDM)"
            };

            for (int i = 0; i < metodologies.length; i++) {
                Methodology methodology = new Methodology();
                methodology.setName(metodologies[i]);
                if (!saveMethodology(methodology)) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    public boolean createVegetableAnnexes() {
        if (getVegetableAnnexes().isEmpty()) {
            String[] annexes_name = {
                "Contrato de trabajo", "Cesión de derechos", "Certificado de depósito", "Documentación de prueba",
                "Reivindicación de prioridad", "Comprobante de pago", "Fotografías", "Copia de Solicitud",
                "Declaración Juramentada examen DHE", "Declaración Juramentada de la novedad", "Nombramiento del representate",
                "Copia del permiso", "Otros"
            };
            String[] annexes_full_name = {
                "Contrato de trabajo o su equivalente.",
                "Cesión de derechos*.",
                "Certificado de depósito de la muestra viva, de ser el caso.",
                "Documentación de prueba de posesión de los derechos para presentar una solicitud (Poder)*.",
                "Reivindicación de prioridad (copia certificada de la primera solicitud).",
                "Comprobante de pago de tasa*.",
                "Fotografías (color, nítidas, tamaño A4, papel fotográfico) *.",
                "Copia de la solicitud presentada a la autoridad competente según corresponda, cuando se trate de variedades OGM, con el fin de que la autoridad competente informe sobre los riesgos a la salud humana, animal o vegetal, soberanía alimentaria, seguridad alimentaria y seguridad ambiental.",
                "Declaración juramentada, cuando el examen DHE se realizó por cuenta del obtentor, declaración elaborada en base a los parámetros de la minuta propuesta por el SENADI.",
                "Declaración juramentada de la novedad.",
                "Nombramiento de representante legal, si el solicitante es una persona jurídica y adjunta la acreditación para firmar en su nombre.",
                "Copia del permiso de acceso al recurso biológico y genético del país, si se trata de una variedad producto de material obtenido en cualquiera de los países miembros de la CAN.",
                "Otros: por favor, especifíquese."
            };
            for (int i = 0; i < annexes_name.length; i++) {
                VegetableAnnexes annexe = new VegetableAnnexes();
                annexe.setName(annexes_name[i]);
                annexe.setFullName(annexes_full_name[i]);
                if(!saveVegetableAnnexe(annexe)){
                    return false;
                }
            }
            return true;
        }else{
            return true;
        }       
    }

    public boolean saveMethodology(Methodology methodology) {
        MethodologyDAO md = new MethodologyDAO(methodology);
        try {
            md.persist();
            return true;
        } catch (Exception ex) {
            System.err.println("Error al guardar Metodología " + methodology.getName() + ": " + ex);
            return false;
        }
    }
    
    public boolean saveVegetableAnnexe(VegetableAnnexes annexe){
        VegetableAnnexesDAO vd = new VegetableAnnexesDAO(annexe);
        try {
            vd.persist();
            return true;
        } catch (Exception ex) {
            System.err.println("Error al guardar Anexo "+annexe.getName()+": "+ex);
            return false;
        }
    }
}
