/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import senadi.gob.ec.ov.bean.LoginBean;
import senadi.gob.ec.ov.solicitudes.City;
import senadi.gob.ec.ov.solicitudes.Country;
import senadi.gob.ec.ov.solicitudes.Owners;
import senadi.gob.ec.ov.solicitudes.OwnersDAO;
import senadi.gob.ec.ov.solicitudes.PersonDAO;
import senadi.gob.ec.ov.solicitudes.Province;
import senadi.gob.ec.ov.dao.MethodologyDAO;
import senadi.gob.ec.ov.dao.PersonVDAO;
import senadi.gob.ec.ov.dao.PersonVegetableDAO;
import senadi.gob.ec.ov.dao.VegetableAnnexesDAO;
import senadi.gob.ec.ov.dao.VegetableAnnexesDataDAO;
import senadi.gob.ec.ov.dao.VegetableFormsDAO;
import senadi.gob.ec.ov.dao.VegetableMethodolyDAO;
import senadi.gob.ec.ov.dao.VegetableProtectionDAO;
import senadi.gob.ec.ov.model.Methodology;
import senadi.gob.ec.ov.model.Person;
import senadi.gob.ec.ov.model.PersonVegetable;
import senadi.gob.ec.ov.model.VegetableAnnexes;
import senadi.gob.ec.ov.model.VegetableAnnexesData;
import senadi.gob.ec.ov.model.VegetableForms;
import senadi.gob.ec.ov.model.VegetableMethodology;
import senadi.gob.ec.ov.model.VegetableProtection;
import senadi.gob.ec.ov.model.discount.CodigoDescuento;
import senadi.gob.ec.ov.model.discount.CodigoDescuentoDAO;
import senadi.gob.ec.ov.model.discount.Descuento;
import senadi.gob.ec.ov.servlet.Report;
import senadi.gob.ec.ov.solicitudes.BreederDAO;
import senadi.gob.ec.ov.solicitudes.BreederForm;
import senadi.gob.ec.ov.solicitudes.FormPaymentRates;
import senadi.gob.ec.ov.solicitudes.PaymentRates;
import senadi.gob.ec.ov.solicitudes.PaymentReceiptDAO;
import senadi.gob.ec.ov.solicitudes.PersonBreeder;

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
                "Documentación de prueba de posesión de los derechos para presentar una solicitud (Poder).",
                "Reivindicación de prioridad (copia certificada de la primera solicitud)*.",
                "Comprobante de pago de tasa*.",
                "Fotografías (color, nítidas, tamaño A4, papel fotográfico) *.",
                "Copia de la solicitud presentada a la autoridad competente según corresponda, cuando se trate de variedades OGM, con el fin de que la autoridad competente informe sobre los riesgos a la salud humana, animal o vegetal, soberanía alimentaria, seguridad alimentaria y seguridad ambiental.",
                "Declaración juramentada, cuando el examen DHE se realizó por cuenta del obtentor, declaración elaborada en base a los parámetros de la minuta propuesta por el SENADI.*",
                "Declaración juramentada de la novedad.",
                "Nombramiento de representante legal, si el solicitante es una persona jurídica y adjunta la acreditación para firmar en su nombre.",
                "Copia del permiso de acceso al recurso biológico y genético del país, si se trata de una variedad producto de material obtenido en cualquiera de los países miembros de la CAN.",
                "Otros: por favor, especifíquese."
            };
            for (int i = 0; i < annexes_name.length; i++) {
                VegetableAnnexes annexe = new VegetableAnnexes();
                annexe.setName(annexes_name[i]);
                annexe.setFullName(annexes_full_name[i]);
                if (!saveVegetableAnnexe(annexe)) {
                    return false;
                }
            }
            return true;
        } else {
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

    public boolean saveVegetableAnnexe(VegetableAnnexes annexe) {
        VegetableAnnexesDAO vd = new VegetableAnnexesDAO(annexe);
        try {
            vd.persist();
            return true;
        } catch (Exception ex) {
            System.err.println("Error al guardar Anexo " + annexe.getName() + ": " + ex);
            return false;
        }
    }

    public VegetableForms saveVegetableForms(VegetableForms vegetableForms) {
        VegetableFormsDAO vd = new VegetableFormsDAO(vegetableForms);
        try {
            vd.persist();
            return vd.getInstancia();
        } catch (Exception ex) {
            System.err.println("No se ha podido guardar vegetable_forms: " + ex);
            return new VegetableForms();
        }
    }

    public boolean updateVegetableForms(VegetableForms vegetableForms) {
        VegetableFormsDAO vd = new VegetableFormsDAO(vegetableForms);
        try {
            if (!vd.getEntityManager().contains(vegetableForms)) {
                System.out.println("merge update vegetableforms");
                vegetableForms = vd.getEntityManager().merge(vegetableForms);
                vd = new VegetableFormsDAO(vegetableForms);
            }
            vd.update();
            return true;
        } catch (Exception ex) {
            System.err.println("No se ha podido editar vegetable_forms de id " + vegetableForms.getId() + ": " + ex);
            return false;
        }
    }

    public boolean personExists(Person person) {
        PersonVDAO pd = new PersonVDAO(person);
        return pd.personExists(person);
    }

    public Person savePersonVF(Person person) {
        PersonVDAO pd = new PersonVDAO(person);
        try {
            pd.persist();
            return pd.getInstancia();
        } catch (Exception ex) {
            System.err.println("No se pudo guardar la person con id: " + person.getId());
            return new Person();
        }
    }

    public PersonVegetable savePersonVegetable(PersonVegetable personVegetable) {
        PersonVegetableDAO pd = new PersonVegetableDAO(personVegetable);
        try {
            pd.persist();
            return pd.getInstancia();
        } catch (Exception ex) {
            System.err.println("No se pudo guardar el person vegetable: " + ex);
            return new PersonVegetable();
        }
    }

    public VegetableForms getVegetableFormsById(Integer id) {
        VegetableFormsDAO vd = new VegetableFormsDAO(null);
        return vd.getVegetableFormsById(id);
    }

    public List<Person> getPersonsById(String ids) {
        PersonDAO pd = new PersonDAO();
        return pd.getPersonsByIds(ids);
    }

    public VegetableAnnexesData getVegetableAnnexesDataByIds(Integer idannexedata, Integer idVegetableForms) {
        VegetableAnnexesDAO vd = new VegetableAnnexesDAO(null);
        return vd.getVegetableAnnexesDataByIds(idannexedata, idVegetableForms);
    }

    public boolean removePersonVegetable(PersonVegetable person) {
        PersonVegetableDAO pd = new PersonVegetableDAO(person);
        try {
            if (!pd.getEntityManager().contains(person)) {
                System.out.println("merge remove person vegetable");
                person = pd.getEntityManager().merge(person);
                pd = new PersonVegetableDAO(person);
            }
            pd.remove();
            return true;

        } catch (Exception ex) {
            return false;
        }
    }

    public boolean removeVegetableMethodology(VegetableMethodology methodology) {
        VegetableMethodolyDAO vd = new VegetableMethodolyDAO(methodology);
        try {
            if (!vd.getEntityManager().contains(methodology)) {
                System.out.println("merge remove vegetable methodology");
                methodology = vd.getEntityManager().merge(methodology);
                vd = new VegetableMethodolyDAO(methodology);
            }
            vd.remove();
            return true;

        } catch (Exception ex) {
            return false;
        }
    }

    public boolean removeVegetableAnnexesData(VegetableAnnexesData annexe) {
        VegetableAnnexesDataDAO vd = new VegetableAnnexesDataDAO(annexe);
        try {
            if (!vd.getEntityManager().contains(annexe)) {
                System.out.println("merge remove vegetable annexe data");
                annexe = vd.getEntityManager().merge(annexe);
                vd = new VegetableAnnexesDataDAO(annexe);
            }
            vd.remove();
            return true;

        } catch (Exception ex) {
            return false;
        }
    }

    public boolean removeVegetableProtection(VegetableProtection protection) {
        VegetableProtectionDAO vd = new VegetableProtectionDAO(protection);
        try {
            if (!vd.getEntityManager().contains(protection)) {
                System.out.println("merge remove protection");
                protection = vd.getEntityManager().merge(protection);
                vd = new VegetableProtectionDAO(protection);
            }
            vd.remove();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public int getNextApplicationNumber() {
        BreederDAO bd = new BreederDAO();
        return bd.getNextApplicationNumber();
    }

    public boolean createApplications(String applicationNumber, String tableName, Integer ownerId,
            String status, Integer year, Integer number, String serviceWindow) {
        BreederDAO bd = new BreederDAO();
        return bd.createApplications(applicationNumber, tableName, ownerId, status, year, number, serviceWindow);
    }

    public int saveBreederForm(BreederForm bf) {
        BreederDAO bd = new BreederDAO();
        return bd.saveBreederForm(bf);
    }

    public boolean savePersonBreeder(PersonBreeder pb) {
        BreederDAO bd = new BreederDAO();
        return bd.savePersonBreeder(pb);
    }

    public int saveFormPaymentRates(FormPaymentRates fpr) {
        BreederDAO bd = new BreederDAO();
        return bd.saveFormPaymentRates(fpr);
    }

    public PaymentRates getPaymentRatesById(Integer id) {
        BreederDAO bd = new BreederDAO();
        return bd.getPaymentRatesById(id);
    }

    public boolean updateBreederForm(BreederForm bf) {
        BreederDAO bd = new BreederDAO();
        return bd.updateBreederForm(bf);
    }

    public boolean generateVegetableFormsPdfPreview(Integer vegetableFormId) throws IOException {
        Report report = new Report();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String path = ec.getRealPath("/WEB-INF/report/");
        InputStream is = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getResourceAsStream("/WEB-INF/report/BreederReport.jrxml");

        FileInputStream in = report.generatePDFVegetableForms(path, is, "archivo.xls", vegetableFormId);
        SFTPUtil sftp = new SFTPUtil();
        ByteArrayInputStream input = new ByteArrayInputStream(in.readAllBytes());
        return sftp.guardarArchivoEnServidorRemoto(input, vegetableFormId, "pdf_breederfrm_" + vegetableFormId + ".pdf");
    }

    public boolean existeCodigoVigente(String numero, int owner_id, String identificacion, boolean usado) {
        CodigoDescuentoDAO cd = new CodigoDescuentoDAO(null);
        return cd.existeCodigoVigente(numero, owner_id, identificacion, usado);
    }

    public CodigoDescuento getCodigoDescuento(String numero, int owner_id, String identificacion, boolean usado) {
        CodigoDescuentoDAO cd = new CodigoDescuentoDAO(null);
        return cd.getCodigoDescuento(numero, owner_id, identificacion, usado);
    }

    public CodigoDescuento getCodigoDescuentoByCode(String code) {
        CodigoDescuentoDAO cd = new CodigoDescuentoDAO(null);
        return cd.getCodigoDescuentoByCode(code);
    }

    public boolean updateCodigoDescuento(CodigoDescuento code) {
        CodigoDescuentoDAO cd = new CodigoDescuentoDAO(code);
        try {
            if (!cd.getEntityManager().contains(code)) {
                System.out.println("merge update vegetableforms");
                code = cd.getEntityManager().merge(code);
                cd = new CodigoDescuentoDAO(code);
            }

            cd.update();
            return true;
        } catch (Exception ex) {
            System.err.println("No se ha podido editar codigo_descuento de id " + code.getId() + ": " + ex);
            return false;
        }
    }

    public Descuento getDescuentoByNumero(String numero) {
        CodigoDescuentoDAO cd = new CodigoDescuentoDAO(null);
        return cd.getDescuentoByNumero(numero);
    }

    /**
     * realiza actualización de pagos realizados por el banco pacífico
     *
     * @param ownerId : id del owner del casillero
     * @return true si el proceso se completó correctamente.
     */
    public boolean updateVegetableFormsPaymentByOnwerId(Integer ownerId) {
        List<BreederForm> breeders = getBreederFormsWithPayByOwnerId(ownerId);
//        System.out.println("Se encontraron "+breeders.size()+" pagos a revisar del ownerid "+ownerId);
        return insertBreederPayInVegetableForms(breeders);
    }

    public List<BreederForm> getBreederFormsWithPayByOwnerId(Integer ownerId) {
        PaymentReceiptDAO pd = new PaymentReceiptDAO();
        return pd.getBreederFormsWithPayByOwnerId(ownerId);
    }

    public VegetableForms getVegetableFormsByApplicationNumber(String applicationNumber) {
        VegetableFormsDAO vd = new VegetableFormsDAO(null);
        return vd.getVegetableFormsByApplicationNumber(applicationNumber);
    }

    public boolean insertBreederPayInVegetableForms(List<BreederForm> breeders) {
        for (int i = 0; i < breeders.size(); i++) {
            BreederForm breeder = breeders.get(i);
            VegetableForms vegetable = getVegetableFormsByApplicationNumber(breeder.getApplicationNumber());
            if (vegetable.getId() != null) {
                if (vegetable.getPaymentReceiptId() == null || vegetable.getPaymentReceiptId() == 0) {
                    vegetable.setPaymentReceiptId(breeder.getPaymentReceiptId());
                    if (!updateVegetableForms(vegetable)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public List<VegetableForms> getVegetableFormsPaymentByOwnerId(Integer ownerId) {
        VegetableFormsDAO vd = new VegetableFormsDAO(null);
        return vd.getVegetableFormsPaymentByOwnerId(ownerId);
    }

    public BreederForm getBreederFormByApplicationNumber(String applicationNumber) {
        PaymentReceiptDAO pd = new PaymentReceiptDAO();
        return pd.getBreederFormByApplicationNumber(applicationNumber);
    }

    public boolean removeVegetablePayWhenCancelledByOwnerId(Integer ownerId) {
        List<VegetableForms> vegetables = getVegetableFormsPaymentByOwnerId(ownerId);
        for (int i = 0; i < vegetables.size(); i++) {
            VegetableForms ve = vegetables.get(i);
            BreederForm bf = getBreederFormByApplicationNumber(ve.getApplicationNumber());
            if (bf.getId() != null) {
                if (bf.getPaymentReceiptId() == null) {
                    ve.setPaymentReceiptId(null);
                    if (!updateVegetableForms(ve)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
