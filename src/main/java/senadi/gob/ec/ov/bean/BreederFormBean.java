/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import senadi.gob.ec.ov.bean.solicitudes.City;
import senadi.gob.ec.ov.bean.solicitudes.Country;
import senadi.gob.ec.ov.bean.solicitudes.Province;
import senadi.gob.ec.ov.model.ExploitedSelled;
import senadi.gob.ec.ov.model.Methodology;
import senadi.gob.ec.ov.model.Person;
import senadi.gob.ec.ov.model.PersonVegetable;
import senadi.gob.ec.ov.model.SimilaryVariety;
import senadi.gob.ec.ov.model.VarietyCharacters;
import senadi.gob.ec.ov.model.VegetableAnnexes;
import senadi.gob.ec.ov.model.VegetableForms;
import senadi.gob.ec.ov.model.VegetablePriority;
import senadi.gob.ec.ov.model.VegetableProtection;
import senadi.gob.ec.ov.model.enums.ExplotationType;
import senadi.gob.ec.ov.model.enums.ProtectionType;
import senadi.gob.ec.ov.util.Controller;
import senadi.gob.ec.ov.util.Operations;
import senadi.gob.ec.ov.util.ValidarIdentificacion;

/**
 *
 * @author michael
 */
@ManagedBean(name = "breederFormBean")
@ViewScoped
public class BreederFormBean implements Serializable {

    private List<Person> applicants;
    private List<Person> personsNotification;
    private Person applicantNotification;
    private PersonVegetable personVegetableNotification;

    private List<Person> obtentors;
//    private List<Person> obtentorsNotification;
    private Person obtentorNotification;

    private Person person;
    private UIData applicantData;
    private UIData obtentorData;
    private UIData personNotificationData;
    private Person personNotification;

    private List<Country> countries;
    private Country country;
    private List<Province> provinces;
    private Province province;
    private List<City> cities;
    private City city;

    private String tipoDocumentoNatural;
    private String tipoDocumentoJuridico;
    private String documento;
    private String generoSolicitante;

    private boolean cedula;
    private boolean passport;
    private boolean ruc;
    private boolean empresa;
    private boolean doc;
    private boolean nombresol;
    private boolean genero;
    private boolean paisnac;
    private boolean prov;
    private boolean cit;
    private boolean fechnac;
    private boolean ecuador;

    private String hashPerson;

    private LoginBean login;

    private String tipoNotificacion;
    private String personDialogTitle;

    private Integer activeIndex;
    //guarda la ciudad de la persona de la dirección para notificaciones
    private String cityNotification;
    private boolean otherpersonnot;

    private VegetableForms vegetableForms;
    private String denominationTypeSelected;
    private String varietyTransferSelected;
    private boolean withDescriptionVariety;
    private String varietyTransferObtentor;

    private Country countryOrigin;

    private List<VegetableProtection> vegetableProtections;
    private UIData protectionData;
    private String protectionType;
    private Country countrySubmission;
    private VegetableProtection vegetableProtection;

    private String previousRequest;
    private String priorityClaim;
    private Country countryPriority;
    private VegetablePriority vegetablePriority;

    private String interritory;
    private String outterritory;
    private ExploitedSelled exploitedSelled;

    private List<ExploitedSelled> inExploitedSelleds;
    private List<ExploitedSelled> outExploitedSelleds;
    private UIData inexploitedData;
    private UIData outexploitedData;
    private Country countryExploited;
    private String exploitedDilogTitle;

    private Country countryQuiz;
    private String technicalQuiz;

    private Country countryLivingSample;
    private String livingVarietySample;

    private List<Methodology> methodologies;
    private List<Methodology> selectedMethodologies;

    private String autogamousSpecies;
    private String allogamousSpecies;
    private boolean autogamousSelected;
    private boolean allogamousSelected;

    private List<VarietyCharacters> varietiesCharacters;
    private VarietyCharacters varietyCharacters;
    private UIData characterData;

    private List<SimilaryVariety> similaritiesVariety;
    private SimilaryVariety similaryVariety;
    private UIData similaryData;

    private String materialVarietyIdentification;
    private String productVarietyIdentification;

    private List<VegetableAnnexes> annexes;
    private VegetableAnnexes currentAnnex;
    private UIData annexeData;
    private UploadedFile currentFile;
    private String otherAnnexDesc;

    //validacion de formulario
    private boolean showApplicantsTableError = false;
    private boolean showObtentorsTableError = false;
    private boolean showTipoNotificacionError = false;
    private boolean showTab4Error = false;
    private boolean showTab5Error = false;

    private boolean showTab6Error = false;
    private boolean showTab6Vartr = false;

    private boolean showTab7Error = false;
    private boolean showTab7Prote = false;

    private boolean showTab8Error = false;
    private boolean showTab8Prior = false;

    private boolean showTab9Error = false;

    private boolean showTab10Error = false;

    private boolean showTab11Error = false;

    private boolean showTab12Error = false;

    private boolean showTab13Error = false;

    private boolean showTab14Error = false;

    private boolean showTab15Error = false;

    public BreederFormBean() {
        loadData();
    }

    private void loadData() {
        Controller c = new Controller();
        login = c.getLogin();
        countries = c.getCountries();
        countries.add(0, new Country(-1, "-- Seleccione --", "SEL"));
        applicants = new ArrayList<>();
        obtentors = new ArrayList<>();
        activeIndex = 0;
        denominationTypeSelected = "";
        withDescriptionVariety = false;
        //Le fijamos por defecto ECUADOR
        countryOrigin = c.getCountryById(64);
        countryPriority = c.getCountryById(64);
        countryLivingSample = c.getCountryById(64);
        vegetableProtections = new ArrayList<>();
        inExploitedSelleds = new ArrayList<>();
        outExploitedSelleds = new ArrayList<>();
        methodologies = c.getMethodologies();
        varietiesCharacters = new ArrayList<>();
        similaritiesVariety = new ArrayList<>();

        //marcamos los anexos que son obligatorios
        annexes = c.getVegetableAnnexes();
        for (int i = 0; i < annexes.size(); i++) {
            annexes.get(i).setCurrentFile(null);
            annexes.get(i).setError(false);
        }
        annexes.get(1).setRequired(true);
        annexes.get(3).setRequired(true);
        annexes.get(5).setRequired(true);
        annexes.get(6).setRequired(true);

        //Inicializo las variables por necesidad de los adjuntos
        personVegetableNotification = new PersonVegetable();
        vegetablePriority = new VegetablePriority();

        //se crea el objeto general
        vegetableForms = new VegetableForms();
    }

    public void prepareToNewPerson() {
        person = new Person();
        country = countries.get(0);
        cleanPersonForm();
        tipoDocumentoNatural = "";
        tipoDocumentoJuridico = "";
//        otherpersonnot = false;
        loadCleanForm();
    }

    public void prepareNewApplicant(ActionEvent ae) {
        personDialogTitle = "NUEVO SOLICITANTE";
        prepareToNewPerson();
        PrimeFaces.current().ajax().addCallbackParam("newperson", true);
    }

    public void prepareNewObtentor(ActionEvent ae) {
        personDialogTitle = "NUEVO OBTENTOR";
        prepareToNewPerson();
        PrimeFaces.current().ajax().addCallbackParam("newperson", true);
    }

    public void prepareNewInExploited(ActionEvent ae) {
        exploitedDilogTitle = "NUEVO REGISTRO EN EL TERRRITORIO DE LA SUBREGIÓN ANDINA";
        exploitedSelled = new ExploitedSelled();
        exploitedSelled.setExplotationType(ExplotationType.IN_ANDEAN_SUBREGION);
        PrimeFaces.current().ajax().addCallbackParam("newexplo", true);
    }

    public void prepareNewOutExploited(ActionEvent ae) {
        exploitedDilogTitle = "NUEVO REGISTRO FUERA DEL TERRRITORIO DE LA SUBREGIÓN ANDINA";
        exploitedSelled = new ExploitedSelled();
        exploitedSelled.setExplotationType(ExplotationType.OUT_ANDEAN_SUBREGION);
        PrimeFaces.current().ajax().addCallbackParam("newexplo", true);
    }

    public void prepareEditApplicant(ActionEvent ae) {
        person = (Person) applicantData.getRowData();
        if (person != null && person.getId() != null) {
            PrimeFaces.current().ajax().addCallbackParam("editperson", true);
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCEN LOS DATOS DE LA PERSONA SELECCIONADA");
        }
    }

    public void prepareEditObtentor(ActionEvent ae) {
        person = (Person) obtentorData.getRowData();
        if (person != null && person.getId() != null) {
            PrimeFaces.current().ajax().addCallbackParam("editperson", true);
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCEN LOS DATOS DE LA PERSONA SELECCIONADA");
        }
    }

    private void loadCleanForm() {
        cedula = false;
        passport = false;
        ruc = false;
        empresa = false;

        doc = false;
        nombresol = false;
        genero = false;
        paisnac = false;
        prov = false;
        cit = false;
        fechnac = false;
    }

    public void onNaturalSelected() {
        tipoDocumentoJuridico = "";
        documento = "";
//        System.out.println("tipodocnatural: " + tipoDocumentoNatural);
        cleanPersonForm();
        radioNaturalSelected();
    }

    public void onJuridicoSelected() {
        tipoDocumentoNatural = "";
        documento = "";
//        System.out.println("tipodocjuridico: " + tipoDocumentoJuridico);
        cleanPersonForm();
        radioJuridicoSelected();
    }

    public void onNotificationSelected() {
        Controller c = new Controller();
        if (tipoNotificacion != null && tipoNotificacion.equals("SOLICITANTE")) {
            otherpersonnot = false;
            if (applicants.isEmpty()) {
                Operations.mensaje(Operations.ERROR, "DEBE INGRESAR AL MENOS UN SOLICITANTE PREVIAMENTE");
                cleanPersonNotification();
            } else {
                if (applicants.size() == 1) {
                    personNotification = applicants.get(0);
                    cityNotification = c.getCityByCityId(personNotification.getCityAddress()).getName();
                    System.out.println("Persona notificación: " + personNotification.toString());
                    personVegetableNotification = new PersonVegetable();
                    showTipoNotificacionError = false;
                } else {
                    personsNotification = applicants;
                    cityNotification = c.getCityByCityId(personNotification.getCityAddress()).getName();
                    PrimeFaces.current().ajax().addCallbackParam("pernotsel", true);
                    System.out.println("Debe escoger un solicitante de la lista de solicitantes.");
                }
            }
        } else if (tipoNotificacion != null && tipoNotificacion.equals("OBTENTOR")) {
            otherpersonnot = false;
            if (obtentors.isEmpty()) {
                Operations.mensaje(Operations.ERROR, "DEBE INGRESAR AL MENOS UN OBTENTOR PREVIAMENTE");
                cleanPersonNotification();
            } else {
                if (obtentors.size() == 1) {
                    personNotification = obtentors.get(0);
                    cityNotification = c.getCityByCityId(personNotification.getCityAddress()).getName();
                    System.out.println("Persona notificación: " + personNotification.toString());
                    personVegetableNotification = new PersonVegetable();
                    showTipoNotificacionError = false;
                } else {
                    personsNotification = obtentors;
                    cityNotification = c.getCityByCityId(personNotification.getCityAddress()).getName();
                    PrimeFaces.current().ajax().addCallbackParam("pernotsel", true);
                    System.out.println("Debe escoger un obtentor de la lista de solicitantes.");
                }
            }
        } else if (tipoNotificacion != null && tipoNotificacion.equals("REPRESENTANTE")) {
            otherpersonnot = true;
            personDialogTitle = "NUEVO REPRESENTANTE";
            prepareToNewPerson();

            PrimeFaces.current().ajax().addCallbackParam("perdial", true);
        } else {
            otherpersonnot = true;
            personDialogTitle = "NUEVO APODERADO";
            prepareToNewPerson();
            PrimeFaces.current().ajax().addCallbackParam("perdial", true);
        }
    }

    public void closePersonDialog(ActionEvent ae) {
        if (personDialogTitle.contains("REPRESENTANTE") || personDialogTitle.contains("APODERADO")) {
            tipoNotificacion = "";
            cleanPersonNotification();
        }
    }

    public void onVarietySelected() {
        if (varietyTransferSelected != null) {
            if (varietyTransferSelected.equals("CONTRACT_EMPLOY") || varietyTransferSelected.equals("TRANSFER_RIGHTS")
                    || varietyTransferSelected.equals("ANOTHER")) {
                withDescriptionVariety = true;
            } else {
                withDescriptionVariety = false;
            }
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCE LA VARIEDAD SELECCIONADA");
        }
    }

    public void onVarietyTransferSelected() {
        if (varietyTransferObtentor != null) {
            if (varietyTransferObtentor.equals("SI")) {
                vegetableForms.setVarietyTransfer(true);
            } else {
                vegetableForms.setVarietyTransfer(false);
            }
            showTab6Error = false;
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCE LA OPCIÓN SELECCIONADA");
        }
    }

    public void onInTerritory() {
        if (interritory != null) {
            if (interritory.equals("SI")) {
                vegetableForms.setInTerritory(true);
            } else {
                vegetableForms.setInTerritory(false);
            }
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCE LA OPCIÓN SELECCIONADA");
        }
    }

    public void onOutTerritory() {
        if (outterritory != null) {
            if (outterritory.equals("SI")) {
                vegetableForms.setOutTerritory(true);
            } else {
                vegetableForms.setOutTerritory(false);
            }
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCE LA OPCIÓN SELECCIONADA");
        }
    }

    public void onPriorityClaim() {
        if (priorityClaim != null) {
            if (priorityClaim.equals("SI")) {
                vegetableForms.setPriorityClaim(true);
            } else {
                vegetableForms.setPriorityClaim(false);
            }
            showTab8Error = false;
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCE LA OPCIÓN SELECCIONADA");
        }
    }

    public void onPreviousRequestSelected() {
        if (previousRequest != null) {
            if (previousRequest.equals("SI")) {
                vegetableForms.setHasOtherApplications(true);
            } else {
                vegetableForms.setHasOtherApplications(false);
            }
            showTab7Error = false;
        }
    }

    public void cleanPersonForm() {
        person = new Person();
        generoSolicitante = "";
        country = countries.get(0);
        provinces = new ArrayList<>();
        province = new Province();
        cities = new ArrayList<>();
        city = new City();
//        tipoDocumentoJuridico = "";
//        tipoDocumentoNatural = "";
    }

    public void radioNaturalSelected() {
        if (tipoDocumentoNatural.equals("CI")) {
            cedula = true;
            passport = false;
            ruc = false;
            empresa = false;

            doc = true;
            nombresol = true;
            genero = true;
            paisnac = false;
            prov = true;
            cit = true;
            fechnac = true;
            ecuador = true;
            PrimeFaces.current().executeScript("PF('focusCedula').focus()");
        } else if (tipoDocumentoNatural.equals("PASSPORT")) {
            cedula = false;
            passport = true;
            ruc = false;
            empresa = false;

            doc = true;
            nombresol = false;
            genero = true;
            paisnac = true;
            prov = true;
            cit = true;
            fechnac = true;
            ecuador = true;
        } else if (tipoDocumentoNatural.equals("RUCN")) {
            cedula = false;
            passport = false;
            ruc = true;
            empresa = false;

            doc = true;
            nombresol = false;
            genero = false;
            paisnac = false;
            prov = true;
            cit = true;
            fechnac = false;
        } else {
            loadCleanForm();
        }
    }

    public void radioJuridicoSelected() {
        if (tipoDocumentoJuridico.equals("RUCJ")) {
            cedula = false;
            passport = false;
            ruc = true;
            empresa = false;

            doc = true;
            nombresol = false;
            genero = false;
            paisnac = false;
            prov = true;
            cit = true;
            fechnac = false;
        } else if (tipoDocumentoJuridico.equals("COMPANY")) {
            cedula = false;
            passport = false;
            ruc = false;
            empresa = true;

            doc = false;
            nombresol = true;
            genero = false;
            paisnac = true;
            prov = false;
            cit = false;
            fechnac = false;
            ecuador = false;
        } else {
            loadCleanForm();
        }
    }

    public void onCountryChange() {
        if (country.getName().equals("Ecuador")) {
            Controller c = new Controller();
            provinces = c.getProvincesByCountryId(country.getId());
            provinces.add(0, new Province(-2, country.getId(), "-- Seleccione --"));
            ecuador = true;
        } else {
            provinces = new ArrayList<>();
            province = new Province();
            cities = new ArrayList<>();
            city = new City();
            ecuador = false;
        }
    }

    public void onProvinceChange() {
        if (province.getId() != null && province.getId() != -2) {
            Controller c = new Controller();
            cities = c.getCitiesByProvinceId(province.getId());
            cities.add(0, new City(-3, province.getId(), "-- Seleccione --"));
        } else {
            cities = new ArrayList<>();
            city = new City();
        }
    }

    public void prepareSaveVegetableForms(ActionEvent ae) {
        if (vegetableForms != null) {
            PrimeFaces.current().ajax().addCallbackParam("savevf", true);
        } else {
            Operations.mensaje(Operations.ERROR, "HAY UN ERROR DESCONOCIDO EN EL FORMULARIO");
        }
    }

    public boolean validarCampos() {
        if (applicants == null || applicants.isEmpty()) {
            showApplicantsTableError = true;
            activeIndex = 0;
            Operations.mensaje(Operations.ERROR, "DEBE INGRESAR AL MENOS UN SOLICITANTE");
            return false;
        }

        if (obtentors == null || obtentors.isEmpty()) {
            showObtentorsTableError = true;
            activeIndex = 1;
            Operations.mensaje(Operations.ERROR, "DEBE INGRESAR AL MENOS UN OBTENTOR");
            return false;
        }
        if (tipoNotificacion == null || tipoNotificacion.trim().isEmpty()) {
            showTipoNotificacionError = true;
            activeIndex = 2;
            Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR LA DIRECCIÓN PARA LAS NOTIFICACIONES");
            return false;
        }
        if (vegetableForms.getBotanicalTaxon() == null || vegetableForms.getBotanicalTaxon().trim().isEmpty()) {
            showTab4Error = true;
            activeIndex = 3;
            Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR EL NOMBRE EN LATÍN DEL GÉNERO");
            return false;
        }
        if (vegetableForms.getCommonName() == null || vegetableForms.getCommonName().trim().isEmpty()) {
            showTab4Error = true;
            activeIndex = 3;
            Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR EL NOMBRE COMÚN DEL TAXÓN BOTÁNICO");
            return false;
        }
        if (vegetableForms.getProvitionalDesignation() == null || vegetableForms.getProvitionalDesignation().trim().isEmpty()) {
            showTab5Error = true;
            activeIndex = 4;
            Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR UNA DESIGNACIÓN PROVISIONAL");
            return false;

        }
        if (vegetableForms.getGenericDenomination() == null || vegetableForms.getGenericDenomination().trim().isEmpty()) {
            showTab5Error = true;
            activeIndex = 4;
            Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR UNA DENOMINACIÓN GENÉRICA");
            return false;
        }
        if (denominationTypeSelected == null || denominationTypeSelected.trim().isEmpty()) {
            showTab5Error = true;
            activeIndex = 4;
            Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR EL TIPO DE DENOMINACIÓN");
            return false;
        }
        if (varietyTransferObtentor == null || varietyTransferObtentor.trim().isEmpty()) {
            showTab6Error = true;
            activeIndex = 5;
            Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR SI EL OBTENTOR NO ES EL SOLICITANTE");
            return false;
        }
        if (varietyTransferObtentor.equals("SI")) {
            if (varietyTransferSelected == null || varietyTransferSelected.trim().isEmpty()) {
                showTab6Vartr = true;
                activeIndex = 5;
                Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR UN TIPO DE TRANSFERENCIA DE LA VARIEDAD");
                return false;
            }
        }
        if (withDescriptionVariety && varietyTransferObtentor.equals("SI")) {
            showTab6Vartr = false;
            if (vegetableForms.getVarietyTransferDescription() == null || vegetableForms.getVarietyTransferDescription().trim().isEmpty()) {
                activeIndex = 5;
                Operations.mensaje(Operations.ERROR, "CAMPO VACÍO");
                return false;
            }
        }

        if (previousRequest == null || previousRequest.trim().isEmpty()) {
            showTab7Error = true;
            activeIndex = 6;
            Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR SI SE HAN REALIZADO O NO OTRAS SOLICITUDES");
            return false;
        }

        if (previousRequest.equals("SI") && (vegetableProtections == null || vegetableProtections.isEmpty())) {
            showTab7Prote = true;
            activeIndex = 6;
            Operations.mensaje(Operations.ERROR, "LA TABLA DE SOLICITUDES ANTERIORES DEBE TENER AL MENOS UN REGISTRO");
            return false;
        }
        if (priorityClaim == null || priorityClaim.trim().isEmpty()) {
            showTab8Error = true;
            activeIndex = 7;
            Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR SI HAY O NO UNA REIVINDICACIÓN DE PRIORIDAD");
            return false;
        }

        if (priorityClaim.equals("SI")) {
            showTab8Error = false;
            if (countryPriority == null || countryPriority.getId() < 1) {
                showTab8Prior = true;
                activeIndex = 7;
                Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR UN PAÍS VÁLIDO");
                return false;
            }
            if (vegetablePriority != null) {
                System.out.println("País: " + countryPriority.getName());
                if (!Operations.validarFecha(vegetablePriority.getApplicationDate())) {
                    showTab8Prior = true;
                    activeIndex = 7;
                    Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR LA FECHA DE SOLICITUD");
                    return false;
                }
                System.out.println("Fecha: " + Operations.formatDate(vegetablePriority.getApplicationDate()));
                if (vegetablePriority.getApplicantName() != null && vegetablePriority.getApplicantName().trim().isEmpty()) {
                    showTab8Prior = true;
                    activeIndex = 7;
                    Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR EL NOMBRE DEL SOLICITANTE");
                    return false;
                }
                System.out.println("AplicantName: " + vegetablePriority.getApplicantName());
                if (vegetablePriority.getGenericDenomination() != null && vegetablePriority.getGenericDenomination().trim().isEmpty()) {
                    showTab8Prior = true;
                    activeIndex = 7;
                    Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR LA DENOMINACIÓN GENÉRICA");
                    return false;
                }
                System.out.println("Denomination: " + vegetablePriority.getGenericDenomination());
                if (vegetablePriority.getApplicationNumber() != null && vegetablePriority.getApplicationNumber().trim().isEmpty()) {
                    showTab8Prior = true;
                    activeIndex = 7;
                    Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR EL NÚMERO DE SOLICITUD");
                    return false;
                }
                System.out.println("Generic " + vegetablePriority.getGenericDenomination());
            }
        }
        //tab 9
        if (interritory == null || interritory.trim().isEmpty()) {
            showTab9Error = true;
            activeIndex = 8;
            Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR SI SE HA VENDIDO O EXPLOTADO EN LA SUBREGIÓN ANDINA");
            return false;
        }

        if (interritory.equals("SI")) {
            if (inExploitedSelleds == null || inExploitedSelleds.isEmpty()) {
                showTab9Error = true;
                activeIndex = 8;
                Operations.mensaje(Operations.ERROR, "DEBE AGREGAR UN REGISTRO EN EL TERRITORIO DE LA SUBREGIÓN ANDINA");
                return false;
            }
        }

        if (outterritory == null || outterritory.trim().isEmpty()) {
            showTab9Error = true;
            activeIndex = 8;
            Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR SI SE HA VENDIDO O EXPLOTADO FUERA DE LA SUBREGIÓN ANDINA");
            return false;
        }

        if (outterritory.equals("SI")) {
            if (outExploitedSelleds == null || outExploitedSelleds.isEmpty()) {
                showTab9Error = true;
                activeIndex = 8;
                Operations.mensaje(Operations.ERROR, "DEBE AGREGAR UN REGISTRO FUERA DEL TERRITORIO DE LA SUBREGIÓN ANDINA");
                return false;
            }
        }
        //tab 10
        if (technicalQuiz != null && !technicalQuiz.trim().isEmpty()) {
            if (countryQuiz == null || countryQuiz.getId() < 1) {
                showTab10Error = true;
                activeIndex = 9;
                Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR UN PAÍS VÁLIDO");
                return false;
            }
        }

        if (livingVarietySample == null || livingVarietySample.trim().isEmpty()) {
            showTab10Error = true;
            activeIndex = 9;
            Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR SI SE CUENTA O NO CON EL DEPÓSITO DE MUESTRA VIVA");
            return false;
        }

        if (livingVarietySample.equals("SI")) {
            if (countryLivingSample == null || countryLivingSample.getId() < 1) {
                showTab10Error = true;
                activeIndex = 9;
                Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR EL PAÍS QUE CORRESPONDE AL DEPÓSITO DE LA MUESTRA VIVA");
                return false;
            }
            if (vegetableForms.getSamplePlace() == null || vegetableForms.getSamplePlace().trim().isEmpty()) {
                showTab10Error = true;
                activeIndex = 9;
                Operations.mensaje(Operations.ERROR, "ESPECIFIQUE EL LUGAR EXÁCTO DEL DEPÓSITO DE MUESTRA VIVA");
                return false;
            }
        }
        //tab 11
        if (vegetableForms.getGenealogy() == null || vegetableForms.getGenealogy().trim().isEmpty()) {
            showTab11Error = true;
            activeIndex = 10;
            Operations.mensaje(Operations.ERROR, "ESPECIFIQUE LA GENEALOGÍA");
            return false;
        }

        if (selectedMethodologies == null || selectedMethodologies.isEmpty()) {
            showTab11Error = true;
            activeIndex = 10;
            Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR AL MENOS UN MÉTODO DE MEJORAMIENTO VEGETAL");
            return false;
        }

        for (int i = 0; i < selectedMethodologies.size(); i++) {
            Methodology metaux = selectedMethodologies.get(i);
            if (metaux.getId() == 14) {//Otro: por favor, especifíquese (Especies Autógamas)
                if (autogamousSpecies == null || autogamousSpecies.trim().isEmpty()) {
                    showTab11Error = true;
                    activeIndex = 10;
                    Operations.mensaje(Operations.ERROR, "ESPECIFIQUE ESPECIES AUTÓGAMAS");
                    return false;
                }
            }
            if (metaux.getId() == 18) {//Otro: por favor, especifíquese (Especies Autógamas)
                if (allogamousSpecies == null || allogamousSpecies.trim().isEmpty()) {
                    showTab11Error = true;
                    activeIndex = 10;
                    Operations.mensaje(Operations.ERROR, "ESPECIFIQUE ESPECIES ALÓGAMAS");
                    return false;
                }
            }
        }

        if (vegetableForms.getProcessHistory() == null || vegetableForms.getProcessHistory().trim().isEmpty()) {
            showTab11Error = true;
            activeIndex = 10;
            Operations.mensaje(Operations.ERROR, "ESPECIFIQUE EL HISTORIAL DE PROCESOS DE MEJORAMIENTO VEGETAL");
            return false;
        }
        //tab 12
        if (vegetableForms.getGeographicalMaterialOrigin() == null || vegetableForms.getGeographicalMaterialOrigin().trim().isEmpty()) {
            showTab12Error = true;
            activeIndex = 11;
            Operations.mensaje(Operations.ERROR, "ESPECIFIQUE LA PROCEDENCIA GEOGRÁFICA DEL MATERIAL GENÉTICO");
            return false;
        }

        if (vegetableForms.getGeographicalVarietyOrigin() == null || vegetableForms.getGeographicalVarietyOrigin().trim().isEmpty()) {
            showTab12Error = true;
            activeIndex = 11;
            Operations.mensaje(Operations.ERROR, "ESPECIFIQUE LA PROCEDENCIA GEOGRÁFICA DE LA VARIEDAD A SER PROTEGIDA");
            return false;
        }

        if (vegetableForms.getReproductionMechanism() == null || vegetableForms.getReproductionMechanism().trim().isEmpty()) {
            showTab12Error = true;
            activeIndex = 11;
            Operations.mensaje(Operations.ERROR, "ESPECIFIQUE EL MECANISMO DE REPRODUCCIÓN, PROPAGACIÓN O MULTIPLICACIÓN");
            return false;
        }

        if (vegetableForms.getAdditionalInformation() == null || vegetableForms.getAdditionalInformation().trim().isEmpty()) {
            showTab12Error = true;
            activeIndex = 11;
            Operations.mensaje(Operations.ERROR, "ESPECIFIQUE LA INFORMACIÓN ADICIONAL DE LA VARIEDAD");
            return false;
        }

        if (materialVarietyIdentification == null || materialVarietyIdentification.trim().isEmpty()) {
            showTab12Error = true;
            activeIndex = 11;
            Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR SI SE TRATA DE UNA VARIEDAD DE PATRIMONIO BIOLÓGICO");
            return false;
        }

        if (productVarietyIdentification == null || productVarietyIdentification.trim().isEmpty()) {
            showTab12Error = true;
            activeIndex = 11;
            Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR SI SE TRATA DE UNA VARIEDAD DE PATRIMONIO BIOLÓGICO");
            return false;
        }
        //tab 13
        if (varietiesCharacters == null || varietiesCharacters.isEmpty()) {
            showTab13Error = true;
            activeIndex = 12;
            Operations.mensaje(Operations.ERROR, "DEBE INGRESAR AL MENOS UN REGISTRO DE CARACTERES DE LA VARIEDAD");
            return false;
        }
        //tab 14
        if (similaritiesVariety == null || similaritiesVariety.isEmpty()) {
            showTab14Error = true;
            activeIndex = 13;
            Operations.mensaje(Operations.ERROR, "DEBE INGRESAR AL MENOS UN REGISTRO DE COMPARACIÓN CON VARIEDADES SIMILARES");
            return false;
        }
        if (annexes != null) {
            boolean missing = false;
            for (VegetableAnnexes a : annexes) {
                if (a.isRequired() && a.getCurrentFile() == null) {
                    a.setError(true); // <- para pintarlo
                    missing = true;
                } else {
                    a.setError(false);
                }
            }
            if (missing) {
                showTab15Error = true;
                activeIndex = 14;  // tab del datalist
                Operations.mensaje(Operations.ERROR, "DEBE ADJUNTAR AL MENOS LOS DOCUMENTOS OBLIGATORIOS");
                return false;
            }
        }
        return true;
    }

    public void saveVegetableForms(ActionEvent ae) {
        if (vegetableForms != null) {
            if (validarCampos()) {
                showApplicantsTableError = false;
                showObtentorsTableError = false;
                showTipoNotificacionError = false;
                showTab4Error = false;
                showTab5Error = false;

                showTab6Error = false;
                showTab6Vartr = false;

                showTab7Error = false;
                showTab7Prote = false;

                showTab8Error = false;
                showTab8Prior = false;

                showTab9Error = false;

                showTab10Error = false;

                showTab11Error = false;

                showTab12Error = false;

                showTab13Error = false;

                showTab14Error = false;
                showTab15Error = false;
            }

            System.out.println("realizar el guardado del formulario");
            PrimeFaces.current().ajax().addCallbackParam("doit", true);

        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCE EL FORMULARIO ACTUAL");
        }
    }

    public void savePerson(ActionEvent ae) {
        if (person != null) {
            Controller c = new Controller();
            List<Person> personsaux = new ArrayList<>();
            String ente = "";
            if (personDialogTitle.contains("SOLICITANTE")) {
                personsaux = applicants;
                ente = "SOLICITANTE";
                // Limpia mensajes de validación cuando está vacía la tabla de solicitantes
                showApplicantsTableError = false;
            } else if (personDialogTitle.contains("OBTENTOR")) {
                personsaux = obtentors;
                ente = "OBTENTOR";
                showObtentorsTableError = false;
            }
            if (person.getId() != null) {
                System.out.println("--------------> " + otherpersonnot);
                if (otherpersonnot) {
                    System.out.println("que onda con esto");
                    loadPersonReferences();
                    if (person.getNationality() == null && country.getId() != null) {
                        person.setNationality(country.getName());
                    }
                    String hashnow = loadHashPerson();
                    if (!hashPerson.equals(hashnow)) {
                        System.out.println("se hicieron cambios en la persona " + person.getName());
                        if (!c.updatePerson(person)) {
                            System.err.println("No se pudo actualizar los datos de la persona " + person.getName());
                        }
                    }
                    personNotification = person;
                    cityNotification = c.getCityByCityId(personNotification.getCityAddress()).getName();
                    PrimeFaces.current().ajax().addCallbackParam("saveapp", true);
                } else {
                    //Editar si hay algun cambio en la person
                    boolean personexists = false;

                    for (int i = 0; i < personsaux.size(); i++) {
                        Person aux = personsaux.get(i);
                        if (aux.getId().equals(person.getId())) {
                            personexists = true;
                            break;
                        }
                    }
                    if (personexists) {
                        Operations.mensaje(Operations.AVISO, "EL " + ente + " " + person.getName() + " YA EXISTE EN LA TABLA");
                    } else {
                        loadPersonReferences();
                        if (person.getNationality() == null && country.getId() != null) {
                            person.setNationality(country.getName());
                        }
                        String hashnow = loadHashPerson();
                        if (!hashPerson.equals(hashnow)) {
                            System.out.println("se hicieron cambios en la persona " + person.getName());
                            if (!c.updatePerson(person)) {
                                System.err.println("No se pudo actualizar los datos de la persona " + person.getName());
                            }
                        }
                        personsaux.add(person);
                        cleanPersonNotification();
                        Operations.mensaje(Operations.INFORMACION, ente + " ASIGNADO AL FORMULARIO");
                        PrimeFaces.current().ajax().addCallbackParam("saveapp", true);
                    }
                }

            } else {
                //guardar una nueva person
                loadPersonReferences();

                Integer idPerson = c.savePerson(person);
                if (idPerson != null) {
                    person = c.getPersonById(idPerson);
                    if (otherpersonnot) {
                        personNotification = person;
                        cityNotification = c.getCityByCityId(personNotification.getCityAddress()).getName();
                    } else {
                        personsaux.add(person);
                        cleanPersonNotification();
                    }
                    PrimeFaces.current().ajax().addCallbackParam("saveapp", true);
                    Operations.mensaje(Operations.INFORMACION, ente + "ASIGNADO AL FORMULARIO");
                } else {
                    Operations.mensaje(Operations.ERROR, "NO SE PUDO GUARDAR LA PERSONA");
                }
            }
            if (personDialogTitle.contains("SOLICITANTE")) {
                activeIndex = 0;
            } else if (personDialogTitle.contains("OBTENTOR")) {
                activeIndex = 1;
            } else {
                showTipoNotificacionError = false;
                System.out.println("repre o apoder");
                activeIndex = 2;
            }
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCE LA PERSONA INGRESADA");
        }
    }

    public void saveVegetableAnnexe(ActionEvent ae) {
        if (currentAnnex != null && !currentAnnex.getName().trim().isEmpty()) {
            if (currentFile != null && !currentFile.getFileName().trim().isEmpty()) {
                if (currentFile.getSize() < (16 * 1024 * 1024)) {
                    currentAnnex.setCurrentFile(currentFile);
                    for (int i = 0; i < annexes.size(); i++) {
                        if (annexes.get(i).getId().equals(currentAnnex.getId())) {
                            annexes.set(i, currentAnnex);
                        }
                    }
                    readAnnexes();
                    PrimeFaces.current().ajax().addCallbackParam("saveanx", true);
                } else {
                    Operations.mensaje(Operations.ERROR, "El archivo supera el límite de 16 MB");
                }
            } else {
                Operations.mensaje(Operations.ERROR, "INGRESE UN DOCUMENTO VÁLIDO");
            }
        } else {
            Operations.mensaje(Operations.ERROR, "ERROR AL GUARDAR ANEXO");
        }
    }

    public void readAnnexes() {
        for (int i = 0; i < annexes.size(); i++) {
            System.out.println((i + 1) + ": " + (annexes.get(i).getCurrentFile() != null) + " " + annexes.get(i).getName());
        }
    }

    public void cleanPersonNotification() {
        personVegetableNotification = new PersonVegetable();
        personNotification = new Person();
        tipoNotificacion = "";
        cityNotification = "";
    }

    public void loadPersonReferences() {
        if (tipoDocumentoNatural != null && !tipoDocumentoNatural.trim().isEmpty()) {
//            System.out.println("load person reference 1a");
            if (tipoDocumentoNatural.contains("RUC")) {
                person.setIdentificationType("RUC");
            } else {
//                System.out.println("load person reference 1b");
                person.setIdentificationType(tipoDocumentoNatural);
            }
        } else {
//            System.out.println("load person reference 1c");
            if (tipoDocumentoJuridico != null && tipoDocumentoJuridico.contains("RUC")) {
                person.setIdentificationType("RUC");
            } else {
//                System.out.println("load person reference 1d");
                person.setIdentificationType(tipoDocumentoJuridico);
            }
        }
//        System.out.println("load person reference 2");
        if (generoSolicitante != null && !generoSolicitante.trim().isEmpty()) {
            person.setGender(generoSolicitante);
        }
//        System.out.println("load person reference 3");
        if (country != null && country.getId() != null && country.getId() > 0) {
            person.setCountryId(country.getId());
        }
//        System.out.println("load person reference 4");
        if (city != null && city.getId() != null && city.getId() > 0) {
            person.setCityAddress(city.getId());
        }
//        System.out.println("load person reference 5");
    }

    public String getIdentificationType(String typeIdent) {
        switch (typeIdent) {
            case "CI":
                return "Cédula";
            case "COMPANY":
                return "Empresa";
            case "PASSPORT":
                return "Pasaporte";
            default:
                return typeIdent;
        }
    }

    public void removeApplicant(ActionEvent ae) {
        System.out.println("lleeeeeeeego remove");
        person = (Person) applicantData.getRowData();
        if (person != null && person.getId() != null) {
            applicants.remove(person);
            if (applicants.isEmpty()) {
                showApplicantsTableError = true;
            }
            Operations.mensaje(Operations.INFORMACION, "SE HA REMOVIDO DE LA TABLA EL SOLICITANTE " + person.getName());
        } else {
            Operations.mensaje(Operations.ERROR, "HAY UN PROBLEMA CON EL SOLICITANTE SELECCIONADO");
        }
    }

    public void removeObtentor(ActionEvent ae) {
        person = (Person) obtentorData.getRowData();
        if (person != null && person.getId() != null) {
            obtentors.remove(person);
            if (obtentors.isEmpty()) {
                showObtentorsTableError = true;
            }
            Operations.mensaje(Operations.INFORMACION, "SE HA REMOVIDO DE LA TABLA EL OBTENTOR " + person.getName());
        } else {
            Operations.mensaje(Operations.ERROR, "HAY UN PROBLEMA CON EL OBTENTOR SELECCIONADO");
        }
    }

    public void removeProtection(ActionEvent ae) {
        vegetableProtection = (VegetableProtection) protectionData.getRowData();
        if (vegetableProtection != null && vegetableProtection.getProtectionType() != null) {
            vegetableProtections.remove(vegetableProtection);
            Operations.mensaje(Operations.INFORMACION, "EL REGISTRO SELECCIONADO SE HA REMOVIDO CORRECTAMENTE DE LA TABLA");
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCE EL REGISTRO SELECCIONADO");
        }
    }

    public void removeSimilaryVariety(ActionEvent ae) {
        similaryVariety = (SimilaryVariety) similaryData.getRowData();
        if (similaryVariety != null) {
            similaritiesVariety.remove(similaryVariety);
            Operations.mensaje(Operations.INFORMACION, "EL REGISTRO SELECCIONADO SE HA REMOVIDO CORRECTAMENTE DE LA TABLA");
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCE EL REGISTRO SELECCIONADO");
        }
    }

    public void removeVarietyCharacter(ActionEvent ae) {
        varietyCharacters = (VarietyCharacters) characterData.getRowData();
        if (varietyCharacters != null) {
            varietiesCharacters.remove(varietyCharacters);
            Operations.mensaje(Operations.INFORMACION, "EL REGISTRO SELECCIONADO SE HA REMOVIDO CORRECTAMENTE DE LA TABLA");
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCE EL REGISTRO SELECCIONADO");
        }
    }

    public void removeInExploited(ActionEvent ae) {
        exploitedSelled = (ExploitedSelled) inexploitedData.getRowData();
        if (exploitedSelled != null && exploitedSelled.getCountryId() != null) {
            inExploitedSelleds.remove(exploitedSelled);
            showTab9Error = true;
            Operations.mensaje(Operations.INFORMACION, "EL REGISTRO SELECCIONADO SE HA REMOVIDO CORRECTAMENTE DE LA TABLA");
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCE EL REGISTRO SELECCIONADO");
        }
    }

    public void removeOutExploited(ActionEvent ae) {
        exploitedSelled = (ExploitedSelled) outexploitedData.getRowData();
        if (exploitedSelled != null && exploitedSelled.getCountryId() != null) {
            outExploitedSelleds.remove(exploitedSelled);
            showTab9Error = true;
            Operations.mensaje(Operations.INFORMACION, "EL REGISTRO SELECCIONADO SE HA REMOVIDO CORRECTAMENTE DE LA TABLA");
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCE EL REGISTRO SELECCIONADO");
        }
    }

    public void verificarDocumento() throws Exception {
        FacesMessage msg = null;
        Controller c = new Controller();
        if (person != null && person.getIdentificationNumber() != null && !person.getIdentificationNumber().trim().isEmpty()) {
            if (cedula && person.getIdentificationNumber().length() == 10) {
                ValidarIdentificacion vi = new ValidarIdentificacion();
                if (vi.validarCedula(person.getIdentificationNumber())) {
                    Person personaux = c.getPersonByIdentificationAndType(person.getIdentificationNumber(), "CI");
                    msg = validatePerson(personaux);
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "LA CÉDULA INGRESADA ES INCORRECTA");
                }
            } else if (passport) {
                Person personaux = c.getPersonByIdentificationAndType(person.getIdentificationNumber(), "PASSPORT");
                msg = validatePerson(personaux);
            } else if (ruc && person.getIdentificationNumber().length() == 13) {
                ValidarIdentificacion vi = new ValidarIdentificacion();
                boolean flag = false;
                String msj = "";
                if (tipoDocumentoNatural.trim().isEmpty()) {
                    if (vi.validarRucSociedadPrivada(person.getIdentificationNumber())) {
                        flag = true;
                    } else {

                        msj = "EL RUC INGRESADO NO ES JURÍDICO";
                    }
                } else {
                    if (vi.validarRucPersonaNatural(person.getIdentificationNumber())) {
                        flag = true;
                    } else {
                        msj = "EL RUC INGRESADO NO ES NATURAL";
                    }
                }

                if (flag) {
                    Person personaux = c.getPersonByIdentificationAndType(person.getIdentificationNumber(), "RUC");
                    msg = validatePerson(personaux);
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", msj);
                }

            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "DOCUMENTO INCORRECTO");
            }
        } else {

        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public FacesMessage validatePerson(Person personaux) {
        Controller c = new Controller();
        System.out.println("llego 2");
        if (personaux.getId() != null) {
            generoSolicitante = personaux.getGender();
            if (personaux.getCountryId() != null && personaux.getCountryId() != 0) {
                country = c.getCountryById(personaux.getCountryId());
                System.out.println("llego 3");
                if (country.getName().equals("Ecuador")) {
                    ecuador = true;
                    provinces = c.getProvincesByCountryId(country.getId());
                    provinces.add(0, new Province(-2, country.getId(), "-- Seleccione --"));
                    if (personaux.getCityAddress() != null && personaux.getCityAddress() != 0) {
                        int provinceId = c.getProvinceIdByCityId(personaux.getCityAddress());
                        province = c.getProvinceIdByProvinceId(provinceId);

                        cities = c.getCitiesByProvinceId(province.getId());
                        cities.add(0, new City(-3, province.getId(), "-- Seleccione --"));
                        city = c.getCityByCityId(personaux.getCityAddress());
                    }
                } else {
                    ecuador = false;

                }
                System.out.println("llego 4");
            } else {
                country = countries.get(0);
                provinces = new ArrayList<>();
                province = new Province();
                cities = new ArrayList<>();
                city = new City();
            }
            System.out.println("llego 5");
            person = personaux;
            hashPerson = loadHashPerson();
            return new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACIÓN", "DOCUMENTO VÁLIDO");
        } else {
            nombresol = true;
            fechnac = true;
            if (ruc) {
                country = c.getCountryById(64); //64 es ecuador
                generoSolicitante = "";
                person = new Person(person.getIdentificationNumber());
                provinces = c.getProvincesByCountryId(country.getId());
                provinces.add(0, new Province(-2, country.getId(), "-- Seleccione --"));
                province = provinces.get(0);
                cities = new ArrayList<>();
                city = new City();

            } else if (cedula) {
                country = c.getCountryById(64); //64 es ecuador
                provinces = c.getProvincesByCountryId(country.getId());
                provinces.add(0, new Province(-2, country.getId(), "-- Seleccione --"));
            }
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "NO SE ENCONTRÓ A LA PERSONA, DEBE CREARLA");
        }
    }

    public String loadHashPerson() {
        System.out.println("name " + person.getName());
        System.out.println(person.getGender());
        System.out.println(person.getDateBirth());
        System.out.println(person.getIdentificationNumber());
        String estadoactual = "name=" + person.getName() + "&identificacion=" + person.getIdentificationNumber() + "&"
                + "type=" + person.getIdentificationType() + "&address=" + person.getAddress() + "&"
                + "email=" + person.getEmail() + "&phone=" + person.getPhone() + "&"
                + "city=" + person.getCityAddress() + "&country=" + person.getCountryId() + "&"
                + "gender=" + person.getGender() + "&"
                + "datebirth=" + (person.getDateBirth() != null ? Operations.formatDate(person.getDateBirth()) : "null");
        System.out.println("here 2");
        String hashaux = Operations.calcularHash(estadoactual);
        return hashaux;
    }

    public List<Person> completeCompany(String query) {
        String queryLowerCase = query.toLowerCase();
        System.out.println(queryLowerCase);
        Controller c = new Controller();
        List<Person> companies = c.getPersonByCriteriaAndType(queryLowerCase, "COMPANY");

//        cleanPersonForm();
        if (companies.isEmpty()) {
            person = new Person();
            person.setName(query);
        }

        return companies.stream().filter(t -> t.getName().toLowerCase().contains(queryLowerCase)).collect(Collectors.toList());
    }

    public void onTechnicalExam() {
        if (technicalQuiz != null && !technicalQuiz.trim().isEmpty()) {
            Controller c = new Controller();
            countryQuiz = c.getCountryById(64);
            switch (technicalQuiz) {
                case "PERFORMED":
                    vegetableForms.setExamPerformed(true);
                    vegetableForms.setExamInProcess(false);
                    vegetableForms.setNoExamYet(false);
                    break;
                case "INPROCESS":
                    vegetableForms.setExamPerformed(false);
                    vegetableForms.setExamInProcess(true);
                    vegetableForms.setNoExamYet(false);
                    break;
                default:
                    vegetableForms.setExamPerformed(false);
                    vegetableForms.setExamInProcess(false);
                    vegetableForms.setNoExamYet(true);
                    break;
            }
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCE EL EXÁMEN TÉCNICO");
        }
    }

    public void onMethodologiesSelected() {
        if (selectedMethodologies != null && !selectedMethodologies.isEmpty()) {
            boolean flagmethodaut = false;
            boolean flagmethodall = false;
            for (int i = 0; i < selectedMethodologies.size(); i++) {
                Methodology maux = selectedMethodologies.get(i);
                if (maux.getName().equals("Otro: por favor, especifíquese (Especies Autógamas)")) {
                    autogamousSelected = true;
                    flagmethodaut = true;
//                    System.out.println("Se eligió autógamas");
                }
                if (maux.getName().equals("Otro: por favor, especifique (Especies Alógamas)")) {
                    allogamousSelected = true;
                    flagmethodall = true;
//                    System.out.println("Se eligió alógamas");
                }
            }
            if (!flagmethodaut) {
                autogamousSelected = false;
//                System.out.println("No se seleccionó autógamas");
            }
            if (!flagmethodall) {
                allogamousSelected = false;
//                System.out.println("No se seleccionó alógamas");
            }
        } else {
            autogamousSelected = false;
            allogamousSelected = false;
        }
    }

    public void onLivingSample() {
        if (livingVarietySample != null && !livingVarietySample.trim().isEmpty()) {
            if (livingVarietySample.equals("SI")) {
                vegetableForms.setLivingSample(true);
            } else {
                vegetableForms.setLivingSample(false);
            }
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCE LA OPCIÓN SELECCIONADA");
        }
    }

    public void onCompanySelect() {
        FacesMessage msg;
        if (person != null && person.getId() != null) {
            msg = validatePerson(person);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "NO SE ENCONTRÓ LA COMPAÑIA");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void selectedPersonNotification(ActionEvent ae) {
        personNotification = (Person) personNotificationData.getRowData();
        if (personNotification != null && personNotification.getId() != null) {
            System.out.println("person notificacion: " + personNotification.toString());
            PrimeFaces.current().ajax().addCallbackParam("pernot", true);
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCE EL SOLICITANTE SELECCIONADO");
        }
    }

    public void closeApplicantNotDialog(ActionEvent ae) {
        cleanPersonNotification();
        System.out.println("llegamos por aquí --------------------");
        System.out.println("tipo solnot: " + tipoNotificacion);
    }

    public void prepareNewApplication(ActionEvent ae) {
        Controller c = new Controller();
        vegetableProtection = new VegetableProtection();
        //FIJAMOS POR DEFECTO ECUADOR
        countrySubmission = c.getCountryById(64);
        protectionType = "";
        PrimeFaces.current().ajax().addCallbackParam("newapppro", true);
    }

    public void prepareNewSimilary(ActionEvent ae) {
        similaryVariety = new SimilaryVariety();
        PrimeFaces.current().ajax().addCallbackParam("newsim", true);
    }

    public void prepareNewCharacter(ActionEvent ae) {
        varietyCharacters = new VarietyCharacters();
        PrimeFaces.current().ajax().addCallbackParam("newch", true);
    }

    public void saveProtection(ActionEvent ae) {
        if (vegetableProtection != null) {

            switch (protectionType) {
                case "DERECHO OBTENTOR":
                    vegetableProtection.setProtectionType(ProtectionType.THROUGH_BREEDER_RIGHT);
                    break;
                case "PATENTE":
                    vegetableProtection.setProtectionType(ProtectionType.THROUGH_PATENT);
                    break;
                default:
                    vegetableProtection.setProtectionType(ProtectionType.CULTIVAR_REGISTRY);
                    break;
            }

            if (countrySubmission != null && countrySubmission.getId() > 0) {
                vegetableProtection.setSubmissionCountryId(countrySubmission.getId());
                vegetableProtections.add(vegetableProtection);
                showTab7Prote = false;
                PrimeFaces.current().ajax().addCallbackParam("savepro", true);
                Operations.mensaje(Operations.INFORMACION, protectionType + " ASIGNADA AL FORMULARIO");
            } else {
                Operations.mensaje(Operations.ERROR, "SELECCIONE UN PAÍS CORRECTO");
            }
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE PUDO GUARDAR LA PROTECCIÓN");
        }
    }

    public void saveSimilaryVariety(ActionEvent ae) {
        if (similaryVariety != null) {
            if (!similaryVariety.getDenomination().trim().isEmpty()) {
                similaritiesVariety.add(similaryVariety);
                PrimeFaces.current().ajax().addCallbackParam("savesim", true);
            } else {
                Operations.mensaje(Operations.ERROR, "NO SE RECONOCEN LOS DATOS INGRESADOS");
            }
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE PUDO GUARDAR LA PROTECCIÓN");
        }
    }

    public void saveVarietyCharacters(ActionEvent ae) {
        if (varietyCharacters != null) {
            if (!varietyCharacters.getCharacters().trim().isEmpty()) {
                varietiesCharacters.add(varietyCharacters);
                PrimeFaces.current().ajax().addCallbackParam("savechar", true);
            } else {
                Operations.mensaje(Operations.ERROR, "NO SE RECONOCEN LOS DATOS INGRESADOS");
            }
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE PUDO GUARDAR LA PROTECCIÓN");
        }
    }

    public void saveExploitedSelled(ActionEvent ae) {
        if (exploitedSelled != null) {
            if (countryExploited != null && countryExploited.getId() > 0) {
                exploitedSelled.setCountryId(countryExploited.getId());
                if (exploitedSelled.getExplotationType().equals(ExplotationType.IN_ANDEAN_SUBREGION)) {
                    inExploitedSelleds.add(exploitedSelled);
                } else {
                    outExploitedSelleds.add(exploitedSelled);
                }
                PrimeFaces.current().ajax().addCallbackParam("saveexp", true);
                Operations.mensaje(Operations.INFORMACION, "REGISTRO EN EL TERRITORIO ASIGNADO AL FORMULARIO");
            } else {
                Operations.mensaje(Operations.ERROR, "SELECCIONE UN PAÍS VÁLIDO");
            }
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE PUDO GUARDAR EL REGISTRO");
        }
    }

    public String getProtectionTypeLabel(ProtectionType type) {
        if (type == null) {
            return "";
        }
        switch (type) {
            case THROUGH_BREEDER_RIGHT:
                return "Protección mediante Derecho de Obtentor";
            case THROUGH_PATENT:
                return "Protección mediante Patente";
            case CULTIVAR_REGISTRY:
                return "Registro de Cultivares";
            default:
                return type.name(); // valor por defecto
        }
    }

    public String getCountryName(Integer idCountry) {
        Controller c = new Controller();
        return c.getCountryById(idCountry).getName();
    }

    public void prepareVegetableAnnexe(ActionEvent ae) {
        currentAnnex = (VegetableAnnexes) annexeData.getRowData();
        if (currentAnnex != null && currentAnnex.getId() != null) {
            currentFile = currentAnnex.getCurrentFile();
            PrimeFaces.current().ajax().addCallbackParam("newcu", true);
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCE LA OPCIÓN SELECCIONADA");
        }
    }

    public void uploadAnnex(FileUploadEvent event) {
        try {
            byte[] pdf = event.getFile().getContent();
            Operations.mensaje(Operations.INFORMACION, "PDF cargado correctamente");
        } catch (Exception e) {
            Operations.mensaje(Operations.ERROR, "Error al cargar el PDF");
        }
    }

    /**
     * @return the applicants
     */
    public List<Person> getApplicants() {
        return applicants;
    }

    /**
     * @param applicants the applicants to set
     */
    public void setApplicants(List<Person> applicants) {
        this.applicants = applicants;
    }

    /**
     * @return the applicantData
     */
    public UIData getApplicantData() {
        return applicantData;
    }

    /**
     * @param applicantData the applicantData to set
     */
    public void setApplicantData(UIData applicantData) {
        this.applicantData = applicantData;
    }

    public Country getCountryById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (Country countryaux : countries) {
            if (id.equals(countryaux.getId())) {
                return countryaux;
            }
        }
        return null;
    }

    /**
     * @return the countries
     */
    public List<Country> getCountries() {
        return countries;
    }

    /**
     * @param countries the countries to set
     */
    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public Province getProvinceById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (Province provaux : provinces) {
            if (id.equals(provaux.getId())) {
                return provaux;
            }
        }
        return null;
    }

    public City getCityById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (City citaux : cities) {
            if (id.equals(citaux.getId())) {
                return citaux;
            }
        }
        return null;
    }

    /**
     * @return the provinces
     */
    public List<Province> getProvinces() {
        return provinces;
    }

    /**
     * @param provinces the provinces to set
     */
    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    /**
     * @return the cities
     */
    public List<City> getCities() {
        return cities;
    }

    /**
     * @param cities the cities to set
     */
    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    /**
     * @return the tipoDocumentoNatural
     */
    public String getTipoDocumentoNatural() {
        return tipoDocumentoNatural;
    }

    /**
     * @param tipoDocumentoNatural the tipoDocumentoNatural to set
     */
    public void setTipoDocumentoNatural(String tipoDocumentoNatural) {
        this.tipoDocumentoNatural = tipoDocumentoNatural;
    }

    /**
     * @return the tipoDocumentoJuridico
     */
    public String getTipoDocumentoJuridico() {
        return tipoDocumentoJuridico;
    }

    /**
     * @param tipoDocumentoJuridico the tipoDocumentoJuridico to set
     */
    public void setTipoDocumentoJuridico(String tipoDocumentoJuridico) {
        this.tipoDocumentoJuridico = tipoDocumentoJuridico;
    }

    /**
     * @return the documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * @param documento the documento to set
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * @return the person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * @return the generoSolicitante
     */
    public String getGeneroSolicitante() {
        return generoSolicitante;
    }

    /**
     * @param generoSolicitante the generoSolicitante to set
     */
    public void setGeneroSolicitante(String generoSolicitante) {
        this.generoSolicitante = generoSolicitante;
    }

    /**
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * @return the province
     */
    public Province getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(Province province) {
        this.province = province;
    }

    /**
     * @return the city
     */
    public City getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * @return the hashPerson
     */
    public String getHashPerson() {
        return hashPerson;
    }

    /**
     * @param hashPerson the hashPerson to set
     */
    public void setHashPerson(String hashPerson) {
        this.hashPerson = hashPerson;
    }

    /**
     * @return the cedula
     */
    public boolean isCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(boolean cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the passport
     */
    public boolean isPassport() {
        return passport;
    }

    /**
     * @param passport the passport to set
     */
    public void setPassport(boolean passport) {
        this.passport = passport;
    }

    /**
     * @return the ruc
     */
    public boolean isRuc() {
        return ruc;
    }

    /**
     * @param ruc the ruc to set
     */
    public void setRuc(boolean ruc) {
        this.ruc = ruc;
    }

    /**
     * @return the empresa
     */
    public boolean isEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(boolean empresa) {
        this.empresa = empresa;
    }

    /**
     * @return the doc
     */
    public boolean isDoc() {
        return doc;
    }

    /**
     * @param doc the doc to set
     */
    public void setDoc(boolean doc) {
        this.doc = doc;
    }

    /**
     * @return the nombresol
     */
    public boolean isNombresol() {
        return nombresol;
    }

    /**
     * @param nombresol the nombresol to set
     */
    public void setNombresol(boolean nombresol) {
        this.nombresol = nombresol;
    }

    /**
     * @return the genero
     */
    public boolean isGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(boolean genero) {
        this.genero = genero;
    }

    /**
     * @return the paisnac
     */
    public boolean isPaisnac() {
        return paisnac;
    }

    /**
     * @param paisnac the paisnac to set
     */
    public void setPaisnac(boolean paisnac) {
        this.paisnac = paisnac;
    }

    /**
     * @return the prov
     */
    public boolean isProv() {
        return prov;
    }

    /**
     * @param prov the prov to set
     */
    public void setProv(boolean prov) {
        this.prov = prov;
    }

    /**
     * @return the cit
     */
    public boolean isCit() {
        return cit;
    }

    /**
     * @param cit the cit to set
     */
    public void setCit(boolean cit) {
        this.cit = cit;
    }

    /**
     * @return the fechnac
     */
    public boolean isFechnac() {
        return fechnac;
    }

    /**
     * @param fechnac the fechnac to set
     */
    public void setFechnac(boolean fechnac) {
        this.fechnac = fechnac;
    }

    /**
     * @return the ecuador
     */
    public boolean isEcuador() {
        return ecuador;
    }

    /**
     * @param ecuador the ecuador to set
     */
    public void setEcuador(boolean ecuador) {
        this.ecuador = ecuador;
    }

    /**
     * @return the login
     */
    public LoginBean getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(LoginBean login) {
        this.login = login;
    }

    /**
     * @return the tipoNotificacion
     */
    public String getTipoNotificacion() {
        return tipoNotificacion;
    }

    /**
     * @param tipoNotificacion the tipoNotificacion to set
     */
    public void setTipoNotificacion(String tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }

    /**
     * @return the personNotification
     */
    public Person getPersonNotification() {
        return personNotification;
    }

    /**
     * @param personNotification the personNotification to set
     */
    public void setPersonNotification(Person personNotification) {
        this.personNotification = personNotification;
    }

    /**
     * @return the personsNotification
     */
    public List<Person> getPersonsNotification() {
        return personsNotification;
    }

    /**
     * @param personsNotification the personsNotification to set
     */
    public void setPersonsNotification(List<Person> personsNotification) {
        this.personsNotification = personsNotification;
    }

    /**
     * @return the applicantNotification
     */
    public Person getApplicantNotification() {
        return applicantNotification;
    }

    /**
     * @param applicantNotification the applicantNotification to set
     */
    public void setApplicantNotification(Person applicantNotification) {
        this.applicantNotification = applicantNotification;
    }

    /**
     * @return the personNotificationData
     */
    public UIData getPersonNotificationData() {
        return personNotificationData;
    }

    /**
     * @param personNotificationData the personNotificationData to set
     */
    public void setPersonNotificationData(UIData personNotificationData) {
        this.personNotificationData = personNotificationData;
    }

    /**
     * @return the personVegetableNotification
     */
    public PersonVegetable getPersonVegetableNotification() {
        return personVegetableNotification;
    }

    /**
     * @param personVegetableNotification the personVegetableNotification to set
     */
    public void setPersonVegetableNotification(PersonVegetable personVegetableNotification) {
        this.personVegetableNotification = personVegetableNotification;
    }

    /**
     * @return the obtentors
     */
    public List<Person> getObtentors() {
        return obtentors;
    }

    /**
     * @param obtentors the obtentors to set
     */
    public void setObtentors(List<Person> obtentors) {
        this.obtentors = obtentors;
    }

    /**
     * @return the obtentorNotification
     */
    public Person getObtentorNotification() {
        return obtentorNotification;
    }

    /**
     * @param obtentorNotification the obtentorNotification to set
     */
    public void setObtentorNotification(Person obtentorNotification) {
        this.obtentorNotification = obtentorNotification;
    }

    /**
     * @return the obtentorData
     */
    public UIData getObtentorData() {
        return obtentorData;
    }

    /**
     * @param obtentorData the obtentorData to set
     */
    public void setObtentorData(UIData obtentorData) {
        this.obtentorData = obtentorData;
    }

    /**
     * @return the personDialogTitle
     */
    public String getPersonDialogTitle() {
        return personDialogTitle;
    }

    /**
     * @param personDialogTitle the personDialogTitle to set
     */
    public void setPersonDialogTitle(String personDialogTitle) {
        this.personDialogTitle = personDialogTitle;
    }

    /**
     * @return the activeIndex
     */
    public Integer getActiveIndex() {
        return activeIndex;
    }

    /**
     * @param activeIndex the activeIndex to set
     */
    public void setActiveIndex(Integer activeIndex) {
        this.activeIndex = activeIndex;
    }

    /**
     * @return the cityNotification
     */
    public String getCityNotification() {
        return cityNotification;
    }

    /**
     * @param cityNotification the cityNotification to set
     */
    public void setCityNotification(String cityNotification) {
        this.cityNotification = cityNotification;
    }

    /**
     * @return the otherpersonnot
     */
    public boolean isOtherpersonnot() {
        return otherpersonnot;
    }

    /**
     * @param otherpersonnot the otherpersonnot to set
     */
    public void setOtherpersonnot(boolean otherpersonnot) {
        this.otherpersonnot = otherpersonnot;
    }

    /**
     * @return the vegetableForms
     */
    public VegetableForms getVegetableForms() {
        return vegetableForms;
    }

    /**
     * @param vegetableForms the vegetableForms to set
     */
    public void setVegetableForms(VegetableForms vegetableForms) {
        this.vegetableForms = vegetableForms;
    }

    /**
     * @return the denominationTypeSelected
     */
    public String getDenominationTypeSelected() {
        return denominationTypeSelected;
    }

    /**
     * @param denominationTypeSelected the denominationTypeSelected to set
     */
    public void setDenominationTypeSelected(String denominationTypeSelected) {
        this.denominationTypeSelected = denominationTypeSelected;
    }

    /**
     * @return the varietyTransferSelected
     */
    public String getVarietyTransferSelected() {
        return varietyTransferSelected;
    }

    /**
     * @param varietyTransferSelected the varietyTransferSelected to set
     */
    public void setVarietyTransferSelected(String varietyTransferSelected) {
        this.varietyTransferSelected = varietyTransferSelected;
    }

    /**
     * @return the withDescriptionVariety
     */
    public boolean isWithDescriptionVariety() {
        return withDescriptionVariety;
    }

    /**
     * @param withDescriptionVariety the withDescriptionVariety to set
     */
    public void setWithDescriptionVariety(boolean withDescriptionVariety) {
        this.withDescriptionVariety = withDescriptionVariety;
    }

    /**
     * @return the countryOrigin
     */
    public Country getCountryOrigin() {
        return countryOrigin;
    }

    /**
     * @param countryOrigin the countryOrigin to set
     */
    public void setCountryOrigin(Country countryOrigin) {
        this.countryOrigin = countryOrigin;
    }

    /**
     * @return the vegetableProtections
     */
    public List<VegetableProtection> getVegetableProtections() {
        return vegetableProtections;
    }

    /**
     * @param vegetableProtections the vegetableProtections to set
     */
    public void setVegetableProtections(List<VegetableProtection> vegetableProtections) {
        this.vegetableProtections = vegetableProtections;
    }

    /**
     * @return the protectionData
     */
    public UIData getProtectionData() {
        return protectionData;
    }

    /**
     * @param protectionData the protectionData to set
     */
    public void setProtectionData(UIData protectionData) {
        this.protectionData = protectionData;
    }

    /**
     * @return the protectionType
     */
    public String getProtectionType() {
        return protectionType;
    }

    /**
     * @param protectionType the protectionType to set
     */
    public void setProtectionType(String protectionType) {
        this.protectionType = protectionType;
    }

    /**
     * @return the countrySubmission
     */
    public Country getCountrySubmission() {
        return countrySubmission;
    }

    /**
     * @param countrySubmission the countrySubmission to set
     */
    public void setCountrySubmission(Country countrySubmission) {
        this.countrySubmission = countrySubmission;
    }

    /**
     * @return the vegetableProtection
     */
    public VegetableProtection getVegetableProtection() {
        return vegetableProtection;
    }

    /**
     * @param vegetableProtection the vegetableProtection to set
     */
    public void setVegetableProtection(VegetableProtection vegetableProtection) {
        this.vegetableProtection = vegetableProtection;
    }

    /**
     * @return the previousRequest
     */
    public String getPreviousRequest() {
        return previousRequest;
    }

    /**
     * @param previousRequest the previousRequest to set
     */
    public void setPreviousRequest(String previousRequest) {
        this.previousRequest = previousRequest;
    }

    /**
     * @return the priorityClaim
     */
    public String getPriorityClaim() {
        return priorityClaim;
    }

    /**
     * @param priorityClaim the priorityClaim to set
     */
    public void setPriorityClaim(String priorityClaim) {
        this.priorityClaim = priorityClaim;
    }

    /**
     * @return the countryPriority
     */
    public Country getCountryPriority() {
        return countryPriority;
    }

    /**
     * @param countryPriority the countryPriority to set
     */
    public void setCountryPriority(Country countryPriority) {
        this.countryPriority = countryPriority;
    }

    /**
     * @return the vegetablePriority
     */
    public VegetablePriority getVegetablePriority() {
        return vegetablePriority;
    }

    /**
     * @param vegetablePriority the vegetablePriority to set
     */
    public void setVegetablePriority(VegetablePriority vegetablePriority) {
        this.vegetablePriority = vegetablePriority;
    }

    /**
     * @return the interritory
     */
    public String getInterritory() {
        return interritory;
    }

    /**
     * @param interritory the interritory to set
     */
    public void setInterritory(String interritory) {
        this.interritory = interritory;
    }

    /**
     * @return the outterritory
     */
    public String getOutterritory() {
        return outterritory;
    }

    /**
     * @param outterritory the outterritory to set
     */
    public void setOutterritory(String outterritory) {
        this.outterritory = outterritory;
    }

    /**
     * @return the exploitedSelled
     */
    public ExploitedSelled getExploitedSelled() {
        return exploitedSelled;
    }

    /**
     * @param exploitedSelled the exploitedSelled to set
     */
    public void setExploitedSelled(ExploitedSelled exploitedSelled) {
        this.exploitedSelled = exploitedSelled;
    }

    /**
     * @return the inExploitedSelleds
     */
    public List<ExploitedSelled> getInExploitedSelleds() {
        return inExploitedSelleds;
    }

    /**
     * @param inExploitedSelleds the inExploitedSelleds to set
     */
    public void setInExploitedSelleds(List<ExploitedSelled> inExploitedSelleds) {
        this.inExploitedSelleds = inExploitedSelleds;
    }

    /**
     * @return the outExploitedSelleds
     */
    public List<ExploitedSelled> getOutExploitedSelleds() {
        return outExploitedSelleds;
    }

    /**
     * @param outExploitedSelleds the outExploitedSelleds to set
     */
    public void setOutExploitedSelleds(List<ExploitedSelled> outExploitedSelleds) {
        this.outExploitedSelleds = outExploitedSelleds;
    }

    /**
     * @return the inexploitedData
     */
    public UIData getInexploitedData() {
        return inexploitedData;
    }

    /**
     * @param inexploitedData the inexploitedData to set
     */
    public void setInexploitedData(UIData inexploitedData) {
        this.inexploitedData = inexploitedData;
    }

    /**
     * @return the outexploitedData
     */
    public UIData getOutexploitedData() {
        return outexploitedData;
    }

    /**
     * @param outexploitedData the outexploitedData to set
     */
    public void setOutexploitedData(UIData outexploitedData) {
        this.outexploitedData = outexploitedData;
    }

    /**
     * @return the countryExploited
     */
    public Country getCountryExploited() {
        return countryExploited;
    }

    /**
     * @param countryExploited the countryExploited to set
     */
    public void setCountryExploited(Country countryExploited) {
        this.countryExploited = countryExploited;
    }

    /**
     * @return the exploitedDilogTitle
     */
    public String getExploitedDilogTitle() {
        return exploitedDilogTitle;
    }

    /**
     * @param exploitedDilogTitle the exploitedDilogTitle to set
     */
    public void setExploitedDilogTitle(String exploitedDilogTitle) {
        this.exploitedDilogTitle = exploitedDilogTitle;
    }

    /**
     * @return the countryQuiz
     */
    public Country getCountryQuiz() {
        return countryQuiz;
    }

    /**
     * @param countryQuiz the countryQuiz to set
     */
    public void setCountryQuiz(Country countryQuiz) {
        this.countryQuiz = countryQuiz;
    }

    /**
     * @return the technicalQuiz
     */
    public String getTechnicalQuiz() {
        return technicalQuiz;
    }

    /**
     * @param technicalQuiz the technicalQuiz to set
     */
    public void setTechnicalQuiz(String technicalQuiz) {
        this.technicalQuiz = technicalQuiz;
    }

    /**
     * @return the countryLivingSample
     */
    public Country getCountryLivingSample() {
        return countryLivingSample;
    }

    /**
     * @param countryLivingSample the countryLivingSample to set
     */
    public void setCountryLivingSample(Country countryLivingSample) {
        this.countryLivingSample = countryLivingSample;
    }

    /**
     * @return the livingVarietySample
     */
    public String getLivingVarietySample() {
        return livingVarietySample;
    }

    /**
     * @param livingVarietySample the livingVarietySample to set
     */
    public void setLivingVarietySample(String livingVarietySample) {
        this.livingVarietySample = livingVarietySample;
    }

    /**
     * @return the varietyTransferObtentor
     */
    public String getVarietyTransferObtentor() {
        return varietyTransferObtentor;
    }

    /**
     * @param varietyTransferObtentor the varietyTransferObtentor to set
     */
    public void setVarietyTransferObtentor(String varietyTransferObtentor) {
        this.varietyTransferObtentor = varietyTransferObtentor;
    }

    /**
     * @return the methodologies
     */
    public List<Methodology> getMethodologies() {
        return methodologies;
    }

    /**
     * @param methodologies the methodologies to set
     */
    public void setMethodologies(List<Methodology> methodologies) {
        this.methodologies = methodologies;
    }

    /**
     * @return the selectedMethodologies
     */
    public List<Methodology> getSelectedMethodologies() {
        return selectedMethodologies;
    }

    /**
     * @param selectedMethodologies the selectedMethodologies to set
     */
    public void setSelectedMethodologies(List<Methodology> selectedMethodologies) {
        this.selectedMethodologies = selectedMethodologies;
    }

    /**
     * @return the autogamousSpecies
     */
    public String getAutogamousSpecies() {
        return autogamousSpecies;
    }

    /**
     * @param autogamousSpecies the autogamousSpecies to set
     */
    public void setAutogamousSpecies(String autogamousSpecies) {
        this.autogamousSpecies = autogamousSpecies;
    }

    /**
     * @return the allogamousSpecies
     */
    public String getAllogamousSpecies() {
        return allogamousSpecies;
    }

    /**
     * @param allogamousSpecies the allogamousSpecies to set
     */
    public void setAllogamousSpecies(String allogamousSpecies) {
        this.allogamousSpecies = allogamousSpecies;
    }

    /**
     * @return the autogamousSelected
     */
    public boolean isAutogamousSelected() {
        return autogamousSelected;
    }

    /**
     * @param autogamousSelected the autogamousSelected to set
     */
    public void setAutogamousSelected(boolean autogamousSelected) {
        this.autogamousSelected = autogamousSelected;
    }

    /**
     * @return the allogamousSelected
     */
    public boolean isAllogamousSelected() {
        return allogamousSelected;
    }

    /**
     * @param allogamousSelected the allogamousSelected to set
     */
    public void setAllogamousSelected(boolean allogamousSelected) {
        this.allogamousSelected = allogamousSelected;
    }

    /**
     * @return the varietiesCharacters
     */
    public List<VarietyCharacters> getVarietiesCharacters() {
        return varietiesCharacters;
    }

    /**
     * @param varietiesCharacters the varietiesCharacters to set
     */
    public void setVarietiesCharacters(List<VarietyCharacters> varietiesCharacters) {
        this.varietiesCharacters = varietiesCharacters;
    }

    /**
     * @return the varietyCharacters
     */
    public VarietyCharacters getVarietyCharacters() {
        return varietyCharacters;
    }

    /**
     * @param varietyCharacters the varietyCharacters to set
     */
    public void setVarietyCharacters(VarietyCharacters varietyCharacters) {
        this.varietyCharacters = varietyCharacters;
    }

    /**
     * @return the characterData
     */
    public UIData getCharacterData() {
        return characterData;
    }

    /**
     * @param characterData the characterData to set
     */
    public void setCharacterData(UIData characterData) {
        this.characterData = characterData;
    }

    /**
     * @return the similaritiesVariety
     */
    public List<SimilaryVariety> getSimilaritiesVariety() {
        return similaritiesVariety;
    }

    /**
     * @param similaritiesVariety the similaritiesVariety to set
     */
    public void setSimilaritiesVariety(List<SimilaryVariety> similaritiesVariety) {
        this.similaritiesVariety = similaritiesVariety;
    }

    /**
     * @return the similaryVariety
     */
    public SimilaryVariety getSimilaryVariety() {
        return similaryVariety;
    }

    /**
     * @param similaryVariety the similaryVariety to set
     */
    public void setSimilaryVariety(SimilaryVariety similaryVariety) {
        this.similaryVariety = similaryVariety;
    }

    /**
     * @return the similaryData
     */
    public UIData getSimilaryData() {
        return similaryData;
    }

    /**
     * @param similaryData the similaryData to set
     */
    public void setSimilaryData(UIData similaryData) {
        this.similaryData = similaryData;
    }

    /**
     * @return the materialVarietyIdentification
     */
    public String getMaterialVarietyIdentification() {
        return materialVarietyIdentification;
    }

    /**
     * @param materialVarietyIdentification the materialVarietyIdentification to
     * set
     */
    public void setMaterialVarietyIdentification(String materialVarietyIdentification) {
        this.materialVarietyIdentification = materialVarietyIdentification;
    }

    /**
     * @return the productVarietyIdentification
     */
    public String getProductVarietyIdentification() {
        return productVarietyIdentification;
    }

    /**
     * @param productVarietyIdentification the productVarietyIdentification to
     * set
     */
    public void setProductVarietyIdentification(String productVarietyIdentification) {
        this.productVarietyIdentification = productVarietyIdentification;
    }

    /**
     * @return the annexes
     */
    public List<VegetableAnnexes> getAnnexes() {
        return annexes;
    }

    /**
     * @param annexes the annexes to set
     */
    public void setAnnexes(List<VegetableAnnexes> annexes) {
        this.annexes = annexes;
    }

    /**
     * @return the currentAnnex
     */
    public VegetableAnnexes getCurrentAnnex() {
        return currentAnnex;
    }

    /**
     * @param currentAnnex the currentAnnex to set
     */
    public void setCurrentAnnex(VegetableAnnexes currentAnnex) {
        this.currentAnnex = currentAnnex;
    }

    /**
     * @return the annexeData
     */
    public UIData getAnnexeData() {
        return annexeData;
    }

    /**
     * @param annexeData the annexeData to set
     */
    public void setAnnexeData(UIData annexeData) {
        this.annexeData = annexeData;
    }

    /**
     * @return the currentFile
     */
    public UploadedFile getCurrentFile() {
        return currentFile;
    }

    /**
     * @param currentFile the currentFile to set
     */
    public void setCurrentFile(UploadedFile currentFile) {
        this.currentFile = currentFile;
    }

    /**
     * @return the otherAnnexDesc
     */
    public String getOtherAnnexDesc() {
        return otherAnnexDesc;
    }

    /**
     * @param otherAnnexDesc the otherAnnexDesc to set
     */
    public void setOtherAnnexDesc(String otherAnnexDesc) {
        this.otherAnnexDesc = otherAnnexDesc;
    }

    /**
     * @return the showApplicantsTableError
     */
    public boolean isShowApplicantsTableError() {
        return showApplicantsTableError;
    }

    /**
     * @param showApplicantsTableError the showApplicantsTableError to set
     */
    public void setShowApplicantsTableError(boolean showApplicantsTableError) {
        this.showApplicantsTableError = showApplicantsTableError;
    }

    /**
     * @return the showObtentorsTableError
     */
    public boolean isShowObtentorsTableError() {
        return showObtentorsTableError;
    }

    /**
     * @param showObtentorsTableError the showObtentorsTableError to set
     */
    public void setShowObtentorsTableError(boolean showObtentorsTableError) {
        this.showObtentorsTableError = showObtentorsTableError;
    }

    /**
     * @return the showTipoNotificacionError
     */
    public boolean isShowTipoNotificacionError() {
        return showTipoNotificacionError;
    }

    /**
     * @param showTipoNotificacionError the showTipoNotificacionError to set
     */
    public void setShowTipoNotificacionError(boolean showTipoNotificacionError) {
        this.showTipoNotificacionError = showTipoNotificacionError;
    }

    /**
     * @return the showTab4Error
     */
    public boolean isShowTab4Error() {
        return showTab4Error;
    }

    /**
     * @param showTab4Error the showTab4Error to set
     */
    public void setShowTab4Error(boolean showTab4Error) {
        this.showTab4Error = showTab4Error;
    }

    /**
     * @return the showTab5Error
     */
    public boolean isShowTab5Error() {
        return showTab5Error;
    }

    /**
     * @param showTab5Error the showTab5Error to set
     */
    public void setShowTab5Error(boolean showTab5Error) {
        this.showTab5Error = showTab5Error;
    }

    /**
     * @return the showTab6Error
     */
    public boolean isShowTab6Error() {
        return showTab6Error;
    }

    /**
     * @param showTab6Error the showTab6Error to set
     */
    public void setShowTab6Error(boolean showTab6Error) {
        this.showTab6Error = showTab6Error;
    }

    /**
     * @return the showTab6Vartr
     */
    public boolean isShowTab6Vartr() {
        return showTab6Vartr;
    }

    /**
     * @param showTab6Vartr the showTab6Vartr to set
     */
    public void setShowTab6Vartr(boolean showTab6Vartr) {
        this.showTab6Vartr = showTab6Vartr;
    }

    /**
     * @return the showTab7Error
     */
    public boolean isShowTab7Error() {
        return showTab7Error;
    }

    /**
     * @param showTab7Error the showTab7Error to set
     */
    public void setShowTab7Error(boolean showTab7Error) {
        this.showTab7Error = showTab7Error;
    }

    /**
     * @return the showTab7Prote
     */
    public boolean isShowTab7Prote() {
        return showTab7Prote;
    }

    /**
     * @param showTab7Prote the showTab7Prote to set
     */
    public void setShowTab7Prote(boolean showTab7Prote) {
        this.showTab7Prote = showTab7Prote;
    }

    /**
     * @return the showTab8Error
     */
    public boolean isShowTab8Error() {
        return showTab8Error;
    }

    /**
     * @param showTab8Error the showTab8Error to set
     */
    public void setShowTab8Error(boolean showTab8Error) {
        this.showTab8Error = showTab8Error;
    }

    /**
     * @return the showTab8Prior
     */
    public boolean isShowTab8Prior() {
        return showTab8Prior;
    }

    /**
     * @param showTab8Prior the showTab8Prior to set
     */
    public void setShowTab8Prior(boolean showTab8Prior) {
        this.showTab8Prior = showTab8Prior;
    }

    /**
     * @return the showTab9Error
     */
    public boolean isShowTab9Error() {
        return showTab9Error;
    }

    /**
     * @param showTab9Error the showTab9Error to set
     */
    public void setShowTab9Error(boolean showTab9Error) {
        this.showTab9Error = showTab9Error;
    }

    /**
     * @return the showTab10Error
     */
    public boolean isShowTab10Error() {
        return showTab10Error;
    }

    /**
     * @param showTab10Error the showTab10Error to set
     */
    public void setShowTab10Error(boolean showTab10Error) {
        this.showTab10Error = showTab10Error;
    }

    /**
     * @return the showTab11Error
     */
    public boolean isShowTab11Error() {
        return showTab11Error;
    }

    /**
     * @param showTab11Error the showTab11Error to set
     */
    public void setShowTab11Error(boolean showTab11Error) {
        this.showTab11Error = showTab11Error;
    }

    /**
     * @return the showTab12Error
     */
    public boolean isShowTab12Error() {
        return showTab12Error;
    }

    /**
     * @param showTab12Error the showTab12Error to set
     */
    public void setShowTab12Error(boolean showTab12Error) {
        this.showTab12Error = showTab12Error;
    }

    /**
     * @return the showTab13Error
     */
    public boolean isShowTab13Error() {
        return showTab13Error;
    }

    /**
     * @param showTab13Error the showTab13Error to set
     */
    public void setShowTab13Error(boolean showTab13Error) {
        this.showTab13Error = showTab13Error;
    }

    /**
     * @return the showTab14Error
     */
    public boolean isShowTab14Error() {
        return showTab14Error;
    }

    /**
     * @param showTab14Error the showTab14Error to set
     */
    public void setShowTab14Error(boolean showTab14Error) {
        this.showTab14Error = showTab14Error;
    }

    /**
     * @return the showTab15Error
     */
    public boolean isShowTab15Error() {
        return showTab15Error;
    }

    /**
     * @param showTab15Error the showTab15Error to set
     */
    public void setShowTab15Error(boolean showTab15Error) {
        this.showTab15Error = showTab15Error;
    }
}
