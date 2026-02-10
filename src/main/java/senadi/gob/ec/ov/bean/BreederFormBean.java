/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.bean;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import senadi.gob.ec.ov.solicitudes.BreederForm;
import senadi.gob.ec.ov.solicitudes.City;
import senadi.gob.ec.ov.solicitudes.Country;
import senadi.gob.ec.ov.solicitudes.Province;
import senadi.gob.ec.ov.model.Declaration;
import senadi.gob.ec.ov.model.ExploitedSelled;
import senadi.gob.ec.ov.model.FormPaymentRate;
import senadi.gob.ec.ov.model.Methodology;
import senadi.gob.ec.ov.model.Person;
import senadi.gob.ec.ov.model.PersonVegetable;
import senadi.gob.ec.ov.model.SimilaryVariety;
import senadi.gob.ec.ov.model.VarietyCharacters;
import senadi.gob.ec.ov.model.VegetableAnnexes;
import senadi.gob.ec.ov.model.VegetableAnnexesData;
import senadi.gob.ec.ov.model.VegetableForms;
import senadi.gob.ec.ov.model.VegetableMethodology;
import senadi.gob.ec.ov.model.VegetablePriority;
import senadi.gob.ec.ov.model.VegetableProtection;
import senadi.gob.ec.ov.model.discount.CodigoDescuento;
import senadi.gob.ec.ov.model.discount.Descuento;
import senadi.gob.ec.ov.model.embed.PersonVegetableId;
import senadi.gob.ec.ov.model.embed.VegetableAnnexesDataId;
import senadi.gob.ec.ov.model.embed.VegetableMethodologyId;
import senadi.gob.ec.ov.model.enums.DenominationType;
import senadi.gob.ec.ov.model.enums.ExplotationType;
import senadi.gob.ec.ov.model.enums.PersonType;
import senadi.gob.ec.ov.model.enums.ProtectionType;
import senadi.gob.ec.ov.model.enums.Status;
import senadi.gob.ec.ov.model.enums.VarietyTransferType;
import senadi.gob.ec.ov.servlet.Report;
import senadi.gob.ec.ov.solicitudes.FormPaymentRates;
import senadi.gob.ec.ov.solicitudes.PersonBreeder;
import senadi.gob.ec.ov.util.Controller;
import senadi.gob.ec.ov.util.Operations;
import senadi.gob.ec.ov.util.Parameter;
import senadi.gob.ec.ov.util.SFTPUtil;
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

    private String formTitle;

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

    private boolean showTab17Error = false;

    //varietal group
    private boolean showTab18Error = false;

    private Integer action; //1: save, 2: preview, 3: finished, 4:delivered

    private Integer editId;

    private VegetableAnnexesData annexeAux;

    private Declaration declaration;

    private String varietalGroupText;

    private boolean applyDiscountLink;
    private String discountCode;

    private Descuento descuento;

//    public BreederFormBean() {
//        preloadEdit();
//    }
    public void preloadEdit() {
        if (FacesContext.getCurrentInstance().isPostback()) {
            return; // evitar recargar en submits
        }

        Controller c = new Controller();
        login = c.getLogin();
        countries = c.getCountries();
        countries.add(0, new Country(-1, "-- Seleccione --", "SEL"));
        methodologies = c.getMethodologies();
        //marcamos los anexos que son obligatorios
        annexes = new ArrayList<>();
//        System.out.println("si se reinicia anexos");
        annexes = c.getVegetableAnnexes();
        for (int i = 0; i < annexes.size(); i++) {
            annexes.get(i).setCurrentFile(null);
            annexes.get(i).setFileContent(null);
            annexes.get(i).setError(false);
            annexes.get(i).setWithFile(false);
            annexes.get(i).setIdAnnexes(null);
            annexes.get(i).setIdVegatableForms(null);
        }
        annexes.get(1).setRequired(true);
        annexes.get(4).setRequired(true);
        annexes.get(5).setRequired(true);
        annexes.get(6).setRequired(true);
        annexes.get(8).setRequired(true);

        if (editId != null) {
            // 🔹 MODO EDICIÓN
            vegetableForms = c.getVegetableFormsById(editId);

            if (vegetableForms == null) {
                Operations.mensaje(Operations.ERROR, "No se encontró el formulario con ID " + editId);
                vegetableForms = new VegetableForms(); // fallback
            } else {
                if (login.getOwner().getId().equals(vegetableForms.getOwnerId())) {
                    loadDataToEditForm(c);
                } else {
                    Operations.mensaje(Operations.ERROR, "EL REGISTRO QUE INTENTA EDITAR NO PERTENECE AL CASILLERO ACTUAL");
                }
            }

        } else {
            // 🔹 MODO NUEVO
            loadDataToNewForm(c);
        }
    }

    private void loadDataToEditForm(Controller c) {

        List<PersonVegetable> persons = vegetableForms.getPersonVegetables();

        //tab 1, tab 2, tab 3
        applicants = new ArrayList<>();
        obtentors = new ArrayList<>();
        personsNotification = new ArrayList<>();
        personVegetableNotification = new PersonVegetable();
        for (int i = 0; i < persons.size(); i++) {
            PersonVegetable peraux = persons.get(i);
            switch (peraux.getPersonType()) {
                case APPLICANT:
                    applicants.add(c.getPersonById(peraux.getPerson().getId()));
                    break;
                case BREEDER:
                    obtentors.add(c.getPersonById(peraux.getPerson().getId()));
                    break;
                default:
                    switch (peraux.getPersonType()) {
                        case APPLICANT_DIR:
                            tipoNotificacion = "SOLICITANTE";
                            break;
                        case BREEDER_DIR:
                            tipoNotificacion = "OBTENTOR";
                            break;
                        case ATTORNEY:
                            tipoNotificacion = "APODERADO";
                            break;
                        default:
                            tipoNotificacion = "REPRESENTANTE";
                            break;
                    }
                    personVegetableNotification = peraux;
                    personNotification = c.getPersonById(peraux.getPerson().getId());
                    cityNotification = c.getCityByCityId(personNotification.getCityAddress()).getName();
                    break;

            }
        }

        //tab 4: se carga automáticamente
        //tab 5:
        if (vegetableForms.getVarietalGroup() != null && !vegetableForms.getVarietalGroup().trim().isEmpty()) {
            if (vegetableForms.getVarietalGroup().equals("A")) {
                varietalGroupText = "GROUP_A";
            } else {
                varietalGroupText = "GROUP_B";
            }
        }
        //tab 6
        if (vegetableForms.getDenominationType() != null) {
            denominationTypeSelected = vegetableForms.getDenominationType().toString();
        }
        //tab 7
        if (vegetableForms.getVarietyTransfer() != null) {
            varietyTransferObtentor = vegetableForms.getVarietyTransfer() ? "SI" : "NO";
            if (varietyTransferObtentor.equals("SI")) {
                if (vegetableForms.getVarietyTransferType() != null) {
                    varietyTransferSelected = vegetableForms.getVarietyTransferType().toString();
                    if (varietyTransferSelected.equals("EMPLOYMENT_CONTRACT") || varietyTransferSelected.equals("TRANSFER_RIGHTS")
                            || varietyTransferSelected.equals("OTHER")) {
                        withDescriptionVariety = true;
                    } else {
                        withDescriptionVariety = false;
                    }
                }
                if (vegetableForms.getGeographicOrigin() != null && vegetableForms.getGeographicOrigin() > 0) {
                    countryOrigin = c.getCountryById(vegetableForms.getGeographicOrigin());
                }
            }
        }

        //tab 8
        vegetableProtections = new ArrayList<>();
        if (vegetableForms.getHasOtherApplications() != null) {
            previousRequest = vegetableForms.getHasOtherApplications() ? "SI" : "NO";
            if (previousRequest.equals("SI")) {
                vegetableProtections = vegetableForms.getVegetableProtections();
            }
        }
        //tab 9
        vegetablePriority = new VegetablePriority();
        if (vegetableForms.getPriorityClaim() != null) {
            priorityClaim = vegetableForms.getPriorityClaim() ? "SI" : "NO";
            if (priorityClaim.equals("SI")) {
                if (vegetableForms.getVegetablePriority() != null) {
                    vegetablePriority = vegetableForms.getVegetablePriority();
                    if (vegetablePriority.getCountryId() != null && vegetablePriority.getCountryId() > 0) {
                        countryPriority = c.getCountryById(vegetablePriority.getCountryId());
                    }
                }
            }
        }
        //tab 10
        inExploitedSelleds = new ArrayList<>();
        if (vegetableForms.getInTerritory() != null) {
            interritory = vegetableForms.getInTerritory() ? "SI" : "NO";
//            System.out.println("Interritory: " + interritory);
            List<ExploitedSelled> exps = vegetableForms.getExploitedSelleds();
            if (interritory.equals("SI")) {
                for (int i = 0; i < exps.size(); i++) {
                    if (exps.get(i).getExplotationType().equals(ExplotationType.IN_ANDEAN_SUBREGION)) {
                        inExploitedSelleds.add(exps.get(i));
                    }
                }
            }
        }
        outExploitedSelleds = new ArrayList<>();
        if (vegetableForms.getOutTerritory() != null) {
            outterritory = vegetableForms.getOutTerritory() ? "SI" : "NO";
//            System.out.println("Outterritory: " + outterritory);
            List<ExploitedSelled> exps = vegetableForms.getExploitedSelleds();
            if (outterritory.equals("SI")) {
                for (int i = 0; i < exps.size(); i++) {
                    if (exps.get(i).getExplotationType().equals(ExplotationType.OUT_ANDEAN_SUBREGION)) {
                        outExploitedSelleds.add(exps.get(i));
                    }
                }
            }
        }
        //tab 11
        if (vegetableForms.getExamPerformed() != null && vegetableForms.getExamInProcess() != null
                && vegetableForms.getNoExamYet() != null) {
            technicalQuiz = vegetableForms.getExamPerformed() ? "PERFORMED" : (vegetableForms.getExamInProcess() ? "INPROCESS" : (vegetableForms.getNoExamYet() ? "NOEXAMYET" : ""));
            if (!technicalQuiz.trim().isEmpty()) {
                if (vegetableForms.getCountryExam() != null && vegetableForms.getCountryExam() > 0) {
                    countryQuiz = c.getCountryById(vegetableForms.getCountryExam());
                }
            }
        }

        if (vegetableForms.getLivingSample() != null) {
            livingVarietySample = vegetableForms.getLivingSample() ? "SI" : "NO";
            if (livingVarietySample.equals("SI")) {
                if (vegetableForms.getCountryLivingSample() != null && vegetableForms.getCountryLivingSample() > 0) {
                    countryLivingSample = c.getCountryById(vegetableForms.getCountryLivingSample());
                }
            }
        }

        //tab 12
        selectedMethodologies = new ArrayList<>();
        for (int i = 0; i < vegetableForms.getVegetableMethodologies().size(); i++) {
            VegetableMethodology vmaux = vegetableForms.getVegetableMethodologies().get(i);
            if (vmaux.getMethodology().getId() == 14) { // Especies Autógamas otros
                autogamousSelected = true;
                autogamousSpecies = vmaux.getDescription();
            }
            if (vmaux.getMethodology().getId() == 18) { // Especies Alógamas otros
                allogamousSelected = true;
                allogamousSpecies = vmaux.getDescription();
            }
            selectedMethodologies.add(vmaux.getMethodology());
        }

        //tab 13
        if (vegetableForms.getMaterialVarietyIdentification() != null) {
            materialVarietyIdentification = vegetableForms.getMaterialVarietyIdentification() ? "SI" : "NO";
        }
        if (vegetableForms.getProductVarietyIdentification() != null) {
            productVarietyIdentification = vegetableForms.getProductVarietyIdentification() ? "SI" : "NO";
        }

        //tab 14
        varietiesCharacters = new ArrayList<>();
        for (int i = 0; i < vegetableForms.getVarietiesCharacters().size(); i++) {
            varietiesCharacters.add(vegetableForms.getVarietiesCharacters().get(i));
        }

        //tab 15
        similaritiesVariety = new ArrayList<>();
        for (int i = 0; i < vegetableForms.getSimilaritiesVariety().size(); i++) {
            similaritiesVariety.add(vegetableForms.getSimilaritiesVariety().get(i));
        }

        //tab 16
        List<VegetableAnnexesData> veanx = vegetableForms.getAnnexesData();
        for (int i = 0; i < annexes.size(); i++) {
            for (int j = 0; j < veanx.size(); j++) {
                if (annexes.get(i).getId().equals(veanx.get(j).getVegetableAnnexes().getId())) {
                    annexes.get(i).setWithFile(true);
                    annexes.get(i).setIdAnnexes(veanx.get(j).getId().getVegetableAnnexesId());
                    annexes.get(i).setIdVegatableForms(veanx.get(j).getId().getVegetableFormId());
//                    System.out.println("(" + (i + 1) + "," + (j + 1) + ") " + annexes.get(i).getName());
                    break;
                }
            }
        }

        //tab 18
        declaration = new Declaration();
        declaration.setDeclarationDate(new Timestamp(System.currentTimeMillis()));
        if (vegetableForms.getDeclaration() != null && vegetableForms.getDeclaration().getId() != null) {
            declaration = vegetableForms.getDeclaration();
            if (!Operations.validarFecha(declaration.getDeclarationDate())) {
                declaration.setDeclarationDate(new Timestamp(System.currentTimeMillis()));
                System.out.println("declaration -----> " + declaration.getDeclarationDate());
            }
        }

        formTitle = "EDITAR REGISTRO DE OBTENCIONES VEGETALES";
        activeIndex = 3;
    }

    private void loadDataToNewForm(Controller c) {
        applicants = new ArrayList<>();
        obtentors = new ArrayList<>();
        activeIndex = 0;
        denominationTypeSelected = "";
        varietalGroupText = "";
        withDescriptionVariety = false;
        //Le fijamos por defecto ECUADOR
        countryOrigin = c.getCountryById(64);
        countryPriority = c.getCountryById(64);
        countryLivingSample = c.getCountryById(64);
        vegetableProtections = new ArrayList<>();
        inExploitedSelleds = new ArrayList<>();
        outExploitedSelleds = new ArrayList<>();
        selectedMethodologies = new ArrayList<>();
        varietiesCharacters = new ArrayList<>();
        similaritiesVariety = new ArrayList<>();
        applyDiscountLink = false;

//        currentFile = null;
        //Inicializo las variables por necesidad de los adjuntos
        personVegetableNotification = new PersonVegetable();
        vegetablePriority = new VegetablePriority();

        declaration = new Declaration();
        declaration.setDeclarationDate(new Timestamp(System.currentTimeMillis()));
        //se crea el objeto general
        vegetableForms = new VegetableForms();
        formTitle = "REGISTRO DE OBTENCIONES VEGETALES";
    }

    public void applyDiscount(ActionEvent ae) {
        System.out.println("Aplicar descuento");
        applyDiscountLink = true;
        discountCode = "";
    }

    public void validateDiscount(ActionEvent ae) {
        System.out.println("llegoooooo " + discountCode);
        if (discountCode != null && !discountCode.trim().isEmpty()) {
            if (!applicants.isEmpty()) {
                discountCode = discountCode.trim();
                Controller c = new Controller();
                CodigoDescuento code = c.getCodigoDescuentoByCode(discountCode);
                if (code.getId() != null) {
                    if (code.getEstado().equals("VIGENTE") && !code.isUsado()) {
                        if (Objects.equals(code.getOwnerId(), login.getOwner().getId())) {
                            String identificacion = code.getIdentificacion();
                            boolean flagapplicant = false;
                            for (int i = 0; i < applicants.size(); i++) {
                                if (applicants.get(i).getIdentificationNumber().equals(identificacion)) {
                                    flagapplicant = true;
                                    break;
                                }
                            }
                            if (flagapplicant) {
                                code.setEstado("USADO");
                                code.setUsado(true);
                                if (c.updateCodigoDescuento(code)) {

                                    descuento = c.getDescuentoByNumero(code.getNumero());
                                    vegetableForms.setDiscountFile(descuento.getRuta());

                                    Operations.mensaje(Operations.INFORMACION, "CÓDIGO VERIFICADO, DESCUENTO APLICADO");
                                } else {
                                    Operations.mensaje(Operations.ERROR, "NO SE PUDO ASOCIAR EL DESCUENTO A ESTE FORMULARIO, INTÉNTE MÁS TARDE");
                                }
                            } else {
                                Operations.mensaje(Operations.ERROR, "DEBE EXISTIR EN EL APARTADO DE SOLICITANTES, EL DUEÑO DEL CERTIFICADO DE DESCUENTO");
                            }
                        } else {
                            Operations.mensaje(Operations.ERROR, "EL CÓDIGO DEBIÓ GENERARSE DESDE EL MISMO CASILLERO (MISMAS CREDENCIALES) DESDE EL QUE SE ESTÁ GENERANDO ESTE FORMULARIO");
                        }
                    } else {
                        Operations.mensaje(Operations.ERROR, "EL CÓDIGO YA HA SIDO USADO Ó HA CADUCADO");
                    }
                } else {
                    Operations.mensaje(Operations.ERROR, "CÓDIGO INCORRECTO Ó NO EXISTE");
                }

//                if (c.existeCodigoVigente(discountCode, login.getOwner().getId(), "poner identificación correcta", false)) {
//                    Operations.mensaje(Operations.INFORMACION, "DESCUENTO APLICADO");
//                }else{
//                    Operations.mensaje(Operations.ERROR, "CÓDIGO INCORRECTO Ó NO EXISTE, ASEGÚRESE QUE EL SOLICITANTE DUEÑO DEL CERTIFICADO ESTÉ EN ESTE FORMULARIO");
//                }
            } else {
                Operations.mensaje(Operations.ERROR, "DEBE EXISTIR EN ESTE FORMULARIO EL SOLICITANTE DUEÑO DEL CERTIFICADO DE DESCUENTO");
            }
        } else {
            Operations.mensaje(Operations.ERROR, "INGRESE UN CÓDIGO DE DESCUENTO VÁLIDO");
        }
    }

    public void discountPdf(ActionEvent ae) {
        if (vegetableForms != null) {
            if (vegetableForms.getDiscountFile() != null && !vegetableForms.getDiscountFile().trim().isEmpty()) {
//                if (descuento != null && descuento.getId() != null) {
                PrimeFaces.current().ajax().addCallbackParam("descpath", Parameter.RUTA_CERT_DESC_URL + vegetableForms.getDiscountFile());
                PrimeFaces.current().ajax().addCallbackParam("dodesc", true);
                Operations.mensaje(Operations.INFORMACION, "SE HA REMOVIDO EL DESCUENTO DE ESTE FORMULARIO");
//                } else {
//                    Operations.mensaje(Operations.ERROR, "NO SE ENCONTRÓ EL CERTIFICADO DE DESCUENTO");
//                }
            } else {
                Operations.mensaje(Operations.ERROR, "HAY UN PROBLEMA CON EL CERTIFICADO DE DESCUENTO ACTUAL");
            }
        } else {
            Operations.mensaje(Operations.ERROR, "HAY UN PROBLEMA CON EL FORMULARIO ACTUAL, REFRESQUE LA PÁGINA");
        }
    }

    public void removeDiscount(ActionEvent ae) {
        if (vegetableForms != null) {
            if (vegetableForms.getDiscountFile() != null && !vegetableForms.getDiscountFile().trim().isEmpty()) {
                vegetableForms.setDiscountFile(null);
                applyDiscountLink = false;
            } else {
                Operations.mensaje(Operations.ERROR, "HAY UN PROBLEMA CON EL CERTIFICADO DE DESCUENTO ACTUAL");
            }
        } else {
            Operations.mensaje(Operations.ERROR, "HAY UN PROBLEMA CON EL FORMULARIO ACTUAL, REFRESQUE LA PÁGINA");
        }
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
                    if (personNotification.getCityAddress() != null) {
                        cityNotification = c.getCityByCityId(personNotification.getCityAddress()).getName();
                    }
                    System.out.println("Persona notificación: " + personNotification.toString());
                    personVegetableNotification = new PersonVegetable();
                    showTipoNotificacionError = false;
                } else {                    
                    personsNotification = applicants;                    
                    if (personNotification.getCityAddress() != null) {
                        cityNotification = c.getCityByCityId(personNotification.getCityAddress()).getName();
                    }                    
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
            if (varietyTransferSelected.equals("EMPLOYMENT_CONTRACT") || varietyTransferSelected.equals("TRANSFER_RIGHTS")
                    || varietyTransferSelected.equals("OTHER")) {
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
            action = 1;
            PrimeFaces.current().ajax().addCallbackParam("savevf", true);
        } else {
            Operations.mensaje(Operations.ERROR, "HAY UN ERROR DESCONOCIDO EN EL FORMULARIO");
        }
    }

    public void preparePreviewVegetableForms(ActionEvent ae) {
        if (vegetableForms != null) {
            action = 2;
            PrimeFaces.current().ajax().addCallbackParam("previewvf", true);
        } else {
            Operations.mensaje(Operations.ERROR, "HAY UN ERROR DESCONOCIDO EN EL FORMULARIO");
        }
    }

    public void prepareGenerateVoucher(ActionEvent ae) {
        System.out.println("generate voucher llegando");
        if (vegetableForms != null && vegetableForms.getId() != null) {
            action = 3;
            PrimeFaces.current().ajax().addCallbackParam("generatev", true);
        } else {
            Operations.mensaje(Operations.ERROR, "EL FORMULARIO ACTUAL PRESENTA UN PROBLEMA, INTENTE MÁS TARDE");
        }
    }

    public boolean validarTaxon() {
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
        return true;
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
        //tab 4
        if (!validarTaxon()) {
            return false;
        }
        //tab 5
        if (varietalGroupText == null || varietalGroupText.trim().isEmpty()) {
            showTab18Error = true;
            activeIndex = 4;
            Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR UN TIPO DE GRUPO VARIETAL");
            return false;
        }
        //tab 6
        if (vegetableForms.getProvitionalDesignation() == null || vegetableForms.getProvitionalDesignation().trim().isEmpty()) {
            showTab5Error = true;
            activeIndex = 5;
            Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR UNA DESIGNACIÓN PROVISIONAL");
            return false;

        }
        if (vegetableForms.getGenericDenomination() == null || vegetableForms.getGenericDenomination().trim().isEmpty()) {
            showTab5Error = true;
            activeIndex = 5;
            Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR UNA DENOMINACIÓN GENÉRICA");
            return false;
        }
        if (denominationTypeSelected == null || denominationTypeSelected.trim().isEmpty()) {
            showTab5Error = true;
            activeIndex = 5;
            Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR EL TIPO DE DENOMINACIÓN");
            return false;
        }
        //tab 7
        if (varietyTransferObtentor == null || varietyTransferObtentor.trim().isEmpty()) {
            showTab6Error = true;
            activeIndex = 6;
            Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR SI EL OBTENTOR NO ES EL SOLICITANTE");
            return false;
        }
        if (varietyTransferObtentor.equals("SI")) {
            if (varietyTransferSelected == null || varietyTransferSelected.trim().isEmpty()) {
                showTab6Vartr = true;
                activeIndex = 6;
                Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR UN TIPO DE TRANSFERENCIA DE LA VARIEDAD");
                return false;
            }
        }
        if (withDescriptionVariety && varietyTransferObtentor.equals("SI")) {
            showTab6Vartr = false;
            if (vegetableForms.getVarietyTransferDescription() == null || vegetableForms.getVarietyTransferDescription().trim().isEmpty()) {
                activeIndex = 6;
                Operations.mensaje(Operations.ERROR, "CAMPO VACÍO");
                return false;
            }
        }
        //tab 8
        if (previousRequest == null || previousRequest.trim().isEmpty()) {
            showTab7Error = true;
            activeIndex = 7;
            Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR SI SE HAN REALIZADO O NO OTRAS SOLICITUDES");
            return false;
        }

        if (previousRequest.equals("SI") && (vegetableProtections == null || vegetableProtections.isEmpty())) {
            showTab7Prote = true;
            activeIndex = 7;
            Operations.mensaje(Operations.ERROR, "LA TABLA DE SOLICITUDES ANTERIORES DEBE TENER AL MENOS UN REGISTRO");
            return false;
        }
        //tab 9
        if (priorityClaim == null || priorityClaim.trim().isEmpty()) {
            showTab8Error = true;
            activeIndex = 8;
            Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR SI HAY O NO UNA REIVINDICACIÓN DE PRIORIDAD");
            return false;
        }

        if (priorityClaim.equals("SI")) {
            showTab8Error = false;
            if (countryPriority == null || countryPriority.getId() < 1) {
                showTab8Prior = true;
                activeIndex = 8;
                Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR UN PAÍS VÁLIDO");
                return false;
            }
            if (vegetablePriority != null) {
                System.out.println("País: " + countryPriority.getName());
                if (!Operations.validarFecha(vegetablePriority.getApplicationDate())) {
                    showTab8Prior = true;
                    activeIndex = 8;
                    Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR LA FECHA DE SOLICITUD");
                    return false;
                }
                System.out.println("Fecha: " + Operations.formatDate(vegetablePriority.getApplicationDate()));
                if (vegetablePriority.getApplicantName() != null && vegetablePriority.getApplicantName().trim().isEmpty()) {
                    showTab8Prior = true;
                    activeIndex = 8;
                    Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR EL NOMBRE DEL SOLICITANTE");
                    return false;
                }
                System.out.println("AplicantName: " + vegetablePriority.getApplicantName());
                if (vegetablePriority.getGenericDenomination() != null && vegetablePriority.getGenericDenomination().trim().isEmpty()) {
                    showTab8Prior = true;
                    activeIndex = 8;
                    Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR LA DENOMINACIÓN GENÉRICA");
                    return false;
                }
                System.out.println("Denomination: " + vegetablePriority.getGenericDenomination());
                if (vegetablePriority.getApplicationNumber() != null && vegetablePriority.getApplicationNumber().trim().isEmpty()) {
                    showTab8Prior = true;
                    activeIndex = 8;
                    Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR EL NÚMERO DE SOLICITUD");
                    return false;
                }
                System.out.println("Generic " + vegetablePriority.getGenericDenomination());
            }
        }
        //tab 10
        if (interritory == null || interritory.trim().isEmpty()) {
            showTab9Error = true;
            activeIndex = 9;
            Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR SI SE HA VENDIDO O EXPLOTADO EN LA SUBREGIÓN ANDINA");
            return false;
        }

        if (interritory.equals("SI")) {
            if (inExploitedSelleds == null || inExploitedSelleds.isEmpty()) {
                showTab9Error = true;
                activeIndex = 9;
                Operations.mensaje(Operations.ERROR, "DEBE AGREGAR UN REGISTRO EN EL TERRITORIO DE LA SUBREGIÓN ANDINA");
                return false;
            }
        }

        if (outterritory == null || outterritory.trim().isEmpty()) {
            showTab9Error = true;
            activeIndex = 9;
            Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR SI SE HA VENDIDO O EXPLOTADO FUERA DE LA SUBREGIÓN ANDINA");
            return false;
        }

        if (outterritory.equals("SI")) {
            if (outExploitedSelleds == null || outExploitedSelleds.isEmpty()) {
                showTab9Error = true;
                activeIndex = 9;
                Operations.mensaje(Operations.ERROR, "DEBE AGREGAR UN REGISTRO FUERA DEL TERRITORIO DE LA SUBREGIÓN ANDINA");
                return false;
            }
        }
        //tab 11
        if (technicalQuiz != null && !technicalQuiz.trim().isEmpty()) {
            if (countryQuiz == null || countryQuiz.getId() < 1) {
                showTab10Error = true;
                activeIndex = 10;
                Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR UN PAÍS VÁLIDO");
                return false;
            }
        }

        if (livingVarietySample == null || livingVarietySample.trim().isEmpty()) {
            showTab10Error = true;
            activeIndex = 10;
            Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR SI SE CUENTA O NO CON EL DEPÓSITO DE MUESTRA VIVA");
            return false;
        }

        if (livingVarietySample.equals("SI")) {
            if (countryLivingSample == null || countryLivingSample.getId() < 1) {
                showTab10Error = true;
                activeIndex = 10;
                Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR EL PAÍS QUE CORRESPONDE AL DEPÓSITO DE LA MUESTRA VIVA");
                return false;
            }
            if (vegetableForms.getSamplePlace() == null || vegetableForms.getSamplePlace().trim().isEmpty()) {
                showTab10Error = true;
                activeIndex = 10;
                Operations.mensaje(Operations.ERROR, "ESPECIFIQUE EL LUGAR EXÁCTO DEL DEPÓSITO DE MUESTRA VIVA");
                return false;
            }
        }
        //tab 12
        if (vegetableForms.getGenealogy() == null || vegetableForms.getGenealogy().trim().isEmpty()) {
            showTab11Error = true;
            activeIndex = 11;
            Operations.mensaje(Operations.ERROR, "ESPECIFIQUE LA GENEALOGÍA");
            return false;
        }

        if (selectedMethodologies == null || selectedMethodologies.isEmpty()) {
            showTab11Error = true;
            activeIndex = 11;
            Operations.mensaje(Operations.ERROR, "DEBE SELECCIONAR AL MENOS UN MÉTODO DE MEJORAMIENTO VEGETAL");
            return false;
        }

        for (int i = 0; i < selectedMethodologies.size(); i++) {
            Methodology metaux = selectedMethodologies.get(i);
            if (metaux.getId() == 14) {//Otro: por favor, especifíquese (Especies Autógamas)
                if (autogamousSpecies == null || autogamousSpecies.trim().isEmpty()) {
                    showTab11Error = true;
                    activeIndex = 11;
                    Operations.mensaje(Operations.ERROR, "ESPECIFIQUE ESPECIES AUTÓGAMAS");
                    return false;
                }
            }
            if (metaux.getId() == 18) {//Otro: por favor, especifíquese (Especies Autógamas)
                if (allogamousSpecies == null || allogamousSpecies.trim().isEmpty()) {
                    showTab11Error = true;
                    activeIndex = 11;
                    Operations.mensaje(Operations.ERROR, "ESPECIFIQUE ESPECIES ALÓGAMAS");
                    return false;
                }
            }
        }

        if (vegetableForms.getProcessHistory() == null || vegetableForms.getProcessHistory().trim().isEmpty()) {
            showTab11Error = true;
            activeIndex = 11;
            Operations.mensaje(Operations.ERROR, "ESPECIFIQUE EL HISTORIAL DE PROCESOS DE MEJORAMIENTO VEGETAL");
            return false;
        }
        //tab 13
        if (vegetableForms.getGeographicalMaterialOrigin() == null || vegetableForms.getGeographicalMaterialOrigin().trim().isEmpty()) {
            showTab12Error = true;
            activeIndex = 12;
            Operations.mensaje(Operations.ERROR, "ESPECIFIQUE LA PROCEDENCIA GEOGRÁFICA DEL MATERIAL GENÉTICO");
            return false;
        }

        if (vegetableForms.getGeographicalVarietyOrigin() == null || vegetableForms.getGeographicalVarietyOrigin().trim().isEmpty()) {
            showTab12Error = true;
            activeIndex = 12;
            Operations.mensaje(Operations.ERROR, "ESPECIFIQUE LA PROCEDENCIA GEOGRÁFICA DE LA VARIEDAD A SER PROTEGIDA");
            return false;
        }

        if (vegetableForms.getReproductionMechanism() == null || vegetableForms.getReproductionMechanism().trim().isEmpty()) {
            showTab12Error = true;
            activeIndex = 12;
            Operations.mensaje(Operations.ERROR, "ESPECIFIQUE EL MECANISMO DE REPRODUCCIÓN, PROPAGACIÓN O MULTIPLICACIÓN");
            return false;
        }

        if (vegetableForms.getAdditionalInformation() == null || vegetableForms.getAdditionalInformation().trim().isEmpty()) {
            showTab12Error = true;
            activeIndex = 12;
            Operations.mensaje(Operations.ERROR, "ESPECIFIQUE LA INFORMACIÓN ADICIONAL DE LA VARIEDAD");
            return false;
        }

        if (materialVarietyIdentification == null || materialVarietyIdentification.trim().isEmpty()) {
            showTab12Error = true;
            activeIndex = 12;
            Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR SI SE TRATA DE UNA VARIEDAD DE PATRIMONIO BIOLÓGICO");
            return false;
        }

        if (productVarietyIdentification == null || productVarietyIdentification.trim().isEmpty()) {
            showTab12Error = true;
            activeIndex = 12;
            Operations.mensaje(Operations.ERROR, "DEBE ESPECIFICAR SI SE TRATA DE UNA VARIEDAD DE PATRIMONIO BIOLÓGICO");
            return false;
        }
        //tab 14
        if (varietiesCharacters == null || varietiesCharacters.isEmpty()) {
            showTab13Error = true;
            activeIndex = 13;
            Operations.mensaje(Operations.ERROR, "DEBE INGRESAR AL MENOS UN REGISTRO DE CARACTERES DE LA VARIEDAD");
            return false;
        }
        //tab 15
        if (similaritiesVariety == null || similaritiesVariety.isEmpty()) {
            showTab14Error = true;
            activeIndex = 14;
            Operations.mensaje(Operations.ERROR, "DEBE INGRESAR AL MENOS UN REGISTRO DE COMPARACIÓN CON VARIEDADES SIMILARES");
            return false;
        }
        //tab 16
        if (annexes != null) {
            boolean missing = false;
            for (VegetableAnnexes a : annexes) {
                if (a.isRequired() && !a.isWithFile()) {
                    a.setError(true); // <- para pintarlo
                    missing = true;
                } else {
                    a.setError(false);
                }
            }
            if (missing) {
                showTab15Error = true;
                activeIndex = 15;  // tab del datalist
                Operations.mensaje(Operations.ERROR, "DEBE ADJUNTAR AL MENOS LOS DOCUMENTOS OBLIGATORIOS");
                return false;
            }
        }

        //tab 18
        if (declaration != null) {
            if (declaration.getPlace() == null || declaration.getPlace().trim().isEmpty()) {
                showTab17Error = true;
                activeIndex = 17;
                Operations.mensaje(Operations.ERROR, "DEBE INGRESAR EL LUGAR DE LA DECLARACIÓN");
                return false;
            }
            if (declaration.getName() == null || declaration.getName().trim().isEmpty()) {
                showTab17Error = true;
                activeIndex = 17;
                Operations.mensaje(Operations.ERROR, "DEBE INGRESAR EL NOMBRE EN LA DECLARACIÓN");
                return false;
            }
            if (!Operations.validarFecha(declaration.getDeclarationDate())) {
                showTab17Error = true;
                activeIndex = 17;
                Operations.mensaje(Operations.ERROR, "DEBE INGRESAR LA FECHA EN LA DECLARACIÓN");
                return false;
            }
        }

        removeErrors();
        return true;
    }

    public void generateAsociationPerson(List<Person> persons, PersonType personType,
            String judicialLocker, String powerCode, String emailLawyer) {
        Controller c = new Controller();

        // Obtén los PersonVegetable actuales de este tipo
        List<PersonVegetable> actuales = new ArrayList<>();
        if (personType.equals(PersonType.APPLICANT_DIR) || personType.equals(PersonType.BREEDER_DIR)
                || personType.equals(PersonType.LEGAL_REPRESENTATIVE) || personType.equals(PersonType.ATTORNEY)) {
            for (int i = 0; i < vegetableForms.getPersonVegetables().size(); i++) {
                if (personType.equals(PersonType.APPLICANT_DIR) || personType.equals(PersonType.BREEDER_DIR)
                        || personType.equals(PersonType.LEGAL_REPRESENTATIVE) || personType.equals(PersonType.ATTORNEY)) {
                    actuales.add(vegetableForms.getPersonVegetables().get(i));
                }
            }
        } else {
            for (int i = 0; i < vegetableForms.getPersonVegetables().size(); i++) {
                if (vegetableForms.getPersonVegetables().get(i).getPersonType().equals(personType)) {
                    actuales.add(vegetableForms.getPersonVegetables().get(i));
                }
            }
        }

        // Construye la lista de IDs que viene del formulario
        Set<Integer> nuevosIds = persons.stream()
                .map(Person::getId)
                .collect(Collectors.toSet());

        // Construye la lista de IDs actuales
        Set<Integer> actualesIds = actuales.stream()
                .map(pv -> pv.getPerson().getId())
                .collect(Collectors.toSet());

        System.out.println("nuevos ids: " + nuevosIds + ", actualesIds: " + actualesIds);
        // Si son iguales, no hay cambios → salimos sin hacer nada
        if (nuevosIds.equals(actualesIds)) {
            System.out.println("No hay cambios en " + personType);
            return;
        } else {
            System.out.println("Se encontraron cambios");
            List<PersonVegetable> pervegaux = new ArrayList<>();
            if (!vegetableForms.getPersonVegetables().isEmpty()) {
                for (int i = 0; i < vegetableForms.getPersonVegetables().size(); i++) {
                    for (int j = 0; j < actuales.size(); j++) {
                        if (vegetableForms.getPersonVegetables().get(i).toString().equals(actuales.get(j).toString())) {//revisar aquí
                            if (personType.equals(PersonType.APPLICANT_DIR) || personType.equals(PersonType.BREEDER_DIR)
                                    || personType.equals(PersonType.LEGAL_REPRESENTATIVE) || personType.equals(PersonType.ATTORNEY)) {
                                PersonType peraux = vegetableForms.getPersonVegetables().get(i).getPersonType();
                                if (peraux.equals(PersonType.APPLICANT_DIR) || peraux.equals(PersonType.BREEDER_DIR)
                                        || peraux.equals(PersonType.LEGAL_REPRESENTATIVE) || peraux.equals(PersonType.ATTORNEY)) {
                                    System.out.println("Si encontré not: " + actuales.get(j).getPerson().getName());
                                    pervegaux.add(actuales.get(j));
                                    c.removePersonVegetable(vegetableForms.getPersonVegetables().get(i));
                                    break;
                                }
                            } else {
                                System.out.println("Si encontré " + actuales.get(j).getPerson().getName());
                                pervegaux.add(actuales.get(j));
                                c.removePersonVegetable(vegetableForms.getPersonVegetables().get(i));
                                break;
                            }
                        }
                    }
                }
                vegetableForms.getPersonVegetables().removeAll(pervegaux);//actuales
                if (c.updateVegetableForms(vegetableForms)) {
                    vegetableForms = c.getVegetableFormsById(vegetableForms.getId());
                }
            }
        }

        System.out.println(personType + "S: " + persons.size());
        for (Person per : persons) {
            System.out.println(personType + ": " + per.getName() + " - " + per.getIdentificationNumber());
            if (!c.personExists(per)) {
                per.setCreateDate(new Timestamp(System.currentTimeMillis()));
                System.out.println("hay que crear a " + per.toString());
                per = c.savePersonVF(per);
            }
            PersonVegetable personVegetable = new PersonVegetable();

            PersonVegetableId pvId = new PersonVegetableId();
            pvId.setVegetableFormId(vegetableForms.getId());
            pvId.setPersonId(per.getId());
            pvId.setPersonType(personType);

            personVegetable.setId(pvId);

            if (judicialLocker != null && !judicialLocker.trim().isEmpty()) {
                personVegetable.setJudicialLocker(judicialLocker);
            }
            if (powerCode != null && !powerCode.trim().isEmpty()) {
                personVegetable.setPowerCode(powerCode);
            }
            if (emailLawyer != null && !emailLawyer.trim().isEmpty()) {
                personVegetable.setEmailLawyerAttorney(emailLawyer);
            }

            personVegetable.setVegetableForms(vegetableForms);
            personVegetable.setPerson(per);
            personVegetable.setPersonType(personType);

//            System.out.println("per_veg: " + personVegetable.getPersonType());
            per.getPersonVegetables().add(personVegetable);
            vegetableForms.getPersonVegetables().add(personVegetable);
        }
    }

    /**
     * Guarda todos los datos proporcionados en el formulario en base de datos,
     * también edita un formulario en caso de que ya exista
     *
     * @return true si se guardaron correctamente caso contrarios devuelve false
     * si no se guardó
     * @throws java.io.IOException
     */
    public boolean preliminarSave() throws IOException, JRException {
        Controller c = new Controller();
        vegetableForms.setOwnerId(login.getOwner().getId());
        vegetableForms.setCreateDate(new Timestamp(System.currentTimeMillis()));

        //tab 5
        if (varietalGroupText != null && !varietalGroupText.trim().isEmpty()) {
            if (varietalGroupText.equals("GROUP_A")) {
                vegetableForms.setVarietalGroup("A");
            } else {
                vegetableForms.setVarietalGroup("B");
            }
        }

        //tab 6
        if (denominationTypeSelected != null && !denominationTypeSelected.trim().isEmpty()) {
            if (denominationTypeSelected.equals("FANTASY_NAME")) {
                vegetableForms.setDenominationType(DenominationType.FANTASY_NAME);
            } else {
                vegetableForms.setDenominationType(DenominationType.CODE);
            }
        }
        //tab 7
        if (vegetableForms.getVarietyTransfer() != null && vegetableForms.getVarietyTransfer()) {
            boolean flagvariety = false;
            if (varietyTransferSelected != null && !varietyTransferSelected.trim().isEmpty()) {
                switch (varietyTransferSelected) {
                    case "SUCCESSION":
                        vegetableForms.setVarietyTransferType(VarietyTransferType.SUCCESSION);
                        break;
                    case "CONTRACT":
                        vegetableForms.setVarietyTransferType(VarietyTransferType.CONTRACT);
                        break;
                    case "EMPLOYMENT_CONTRACT":
                        vegetableForms.setVarietyTransferType(VarietyTransferType.EMPLOYMENT_CONTRACT);
                        flagvariety = true;
                        break;
                    case "TRANSFER_RIGHTS":
                        vegetableForms.setVarietyTransferType(VarietyTransferType.TRANSFER_RIGHTS);
                        flagvariety = true;
                        break;
                    default:
                        vegetableForms.setVarietyTransferType(VarietyTransferType.OTHER);
                        flagvariety = true;
                        break;
                }
                if (!flagvariety) {
                    vegetableForms.setVarietyTransferDescription("");
                }
            }
        }
        if (countryOrigin != null && countryOrigin.getId() > 0) {
            vegetableForms.setGeographicOrigin(countryOrigin.getId());
        }

        //tab 8
        if (previousRequest != null && !previousRequest.trim().isEmpty()) {
            if (previousRequest.equals("SI")) {
                vegetableForms.setHasOtherApplications(true);
            } else {
                vegetableForms.setHasOtherApplications(false);
            }
        }

        //tab 9
        if (priorityClaim != null && !priorityClaim.trim().isEmpty()) {
            if (priorityClaim.equals("SI")) {
                vegetableForms.setPriorityClaim(true);
            } else {
                vegetableForms.setPriorityClaim(false);
            }
        }

        //tab 10
        if (interritory != null && !interritory.trim().isEmpty()) {
            if (interritory.equals("SI")) {
                vegetableForms.setInTerritory(true);
            } else {
                vegetableForms.setInTerritory(false);
            }
        }

        if (outterritory != null && !outterritory.trim().isEmpty()) {
            if (outterritory.equals("SI")) {
                vegetableForms.setOutTerritory(true);
            } else {
                vegetableForms.setOutTerritory(false);
            }
        }

        //tab 11
        if (technicalQuiz != null && !technicalQuiz.trim().isEmpty()) {
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
            if (countryQuiz != null && countryQuiz.getId() > 0) {
                vegetableForms.setCountryExam(countryQuiz.getId());
            }
        }

        //tab 12
        if (livingVarietySample != null && !livingVarietySample.trim().isEmpty()) {
            if (livingVarietySample.equals("SI")) {
                vegetableForms.setLivingSample(true);
                if (countryLivingSample != null && countryLivingSample.getId() > 0) {
                    vegetableForms.setCountryLivingSample(countryLivingSample.getId());
                }
            } else {
                vegetableForms.setLivingSample(false);
                vegetableForms.setSamplePlace("");
            }
        }
        if (materialVarietyIdentification != null && !materialVarietyIdentification.trim().isEmpty()) {
            if (materialVarietyIdentification.equals("SI")) {
                vegetableForms.setMaterialVarietyIdentification(true);
            } else {
                vegetableForms.setMaterialVarietyIdentification(false);
            }
        }
        if (productVarietyIdentification != null && !productVarietyIdentification.trim().isEmpty()) {
            if (productVarietyIdentification.equals("SI")) {
                vegetableForms.setProductVarietyIdentification(true);
            } else {
                vegetableForms.setProductVarietyIdentification(false);
            }
        }

        //cambia el estado de acuerdo al botón presionado
        if (action == 1) {
            vegetableForms.setStatus(Status.SAVED);
        } else if (action == 2) {
            vegetableForms.setStatus(Status.PREVIEW);
        }

        //guardamos el formulario
        if (vegetableForms.getId() != null) {
            System.out.println("**************** editar formulario ****************");
            if (vegetableProtections != null && !vegetableProtections.isEmpty()) {
                System.out.println(vegetableProtections.removeIf(p -> p.getVegetableForms() == null));
            }
            if (c.updateVegetableForms(vegetableForms)) {
                vegetableForms = c.getVegetableFormsById(vegetableForms.getId());
            }
        } else {
            vegetableForms = c.saveVegetableForms(vegetableForms);
        }

        if (vegetableForms.getId() != null) {

            generateAsociationPerson(applicants, PersonType.APPLICANT, "", "", "");
            generateAsociationPerson(obtentors, PersonType.BREEDER, "", "", "");

            if (personNotification != null && personNotification.getIdentificationNumber() != null) {
                List<Person> personsNotifications = new ArrayList<>();
                personsNotifications.add(personNotification);
                switch (tipoNotificacion) {
                    case "SOLICITANTE":
                        generateAsociationPerson(personsNotifications, PersonType.APPLICANT_DIR, personVegetableNotification.getJudicialLocker(), personVegetableNotification.getPowerCode(), personVegetableNotification.getEmailLawyerAttorney());
                        break;
                    case "OBTENTOR":
                        generateAsociationPerson(personsNotifications, PersonType.BREEDER_DIR, personVegetableNotification.getJudicialLocker(), personVegetableNotification.getPowerCode(), personVegetableNotification.getEmailLawyerAttorney());
                        break;
                    case "REPRESENTANTE":
                        generateAsociationPerson(personsNotifications, PersonType.LEGAL_REPRESENTATIVE, personVegetableNotification.getJudicialLocker(), personVegetableNotification.getPowerCode(), personVegetableNotification.getEmailLawyerAttorney());
                        break;
                    default:
                        generateAsociationPerson(personsNotifications, PersonType.ATTORNEY, personVegetableNotification.getJudicialLocker(), personVegetableNotification.getPowerCode(), personVegetableNotification.getEmailLawyerAttorney());
                        break;
                }
            }
            //tab 8: protections
            System.out.println("has other applications - now: " + vegetableForms.getHasOtherApplications());
            if (vegetableForms.getHasOtherApplications() != null && vegetableForms.getHasOtherApplications()) {
                if (vegetableForms.getVegetableProtections() != null && !vegetableForms.getVegetableProtections().isEmpty()) {
                    // 🔥 Eliminar objetos que llegan sin padre — son temporales del UI y causan el error                                        
                    vegetableForms.getVegetableProtections().clear();
                }

                if (vegetableProtections != null && !vegetableProtections.isEmpty()) {
                    for (VegetableProtection protection : vegetableProtections) {
                        protection.setVegetableForms(vegetableForms);
                        vegetableForms.getVegetableProtections().add(protection);
                    }
                }
            } else {
                if (vegetableForms.getHasOtherApplications() != null && !vegetableForms.getHasOtherApplications()) {
                    if (!vegetableForms.getVegetableProtections().isEmpty()) {
                        vegetableForms.getVegetableProtections().clear();
                    }
                }
            }

            //tab 9: priority            
            if (vegetableForms.getPriorityClaim() != null && vegetableForms.getPriorityClaim()) {
                if (vegetablePriority != null) {
                    if (countryPriority != null && countryPriority.getId() > 0) {
//                        if (vegetableForms.getVegetablePriority().getId() != null) {
//                            vegetableForms.getVegetablePriority().setCountryId(countryPriority.getId());
//                        } else {
                        vegetablePriority.setCountryId(countryPriority.getId());
//                        }
                    }
                    vegetablePriority.setVegetableForms(vegetableForms);
                    vegetableForms.setVegetablePriority(vegetablePriority);
                }
            }

            //tab 10: exploitedselled
            if (vegetableForms.getInTerritory() != null && vegetableForms.getInTerritory()) {

                if (vegetableForms.getExploitedSelleds() != null && !vegetableForms.getExploitedSelleds().isEmpty()) {
                    vegetableForms.getExploitedSelleds().clear();
                }

                if (inExploitedSelleds != null && !inExploitedSelleds.isEmpty()) {
                    for (ExploitedSelled inexp : inExploitedSelleds) {
                        inexp.setVegetableForms(vegetableForms);
                        vegetableForms.getExploitedSelleds().add(inexp);
                    }
                }
            }

            if (vegetableForms.getOutTerritory() != null && vegetableForms.getOutTerritory()) {
                if (outExploitedSelleds != null && !outExploitedSelleds.isEmpty()) {
                    for (ExploitedSelled outexp : outExploitedSelleds) {
                        outexp.setVegetableForms(vegetableForms);
                        vegetableForms.getExploitedSelleds().add(outexp);
                    }
                }
            }

            //tab 12: metodología
            if (selectedMethodologies != null && !selectedMethodologies.isEmpty()) {
                // Obtener los VegetableMehodology actuales de este tipo
                List<VegetableMethodology> actuales = new ArrayList<>();

                for (int i = 0; i < vegetableForms.getVegetableMethodologies().size(); i++) {
                    actuales.add(vegetableForms.getVegetableMethodologies().get(i));

                }

                // Construye la lista de IDs que viene del formulario
                Set<Integer> nuevosIds = selectedMethodologies.stream()
                        .map(Methodology::getId)
                        .collect(Collectors.toSet());

                // Construye la lista de IDs actuales
                Set<Integer> actualesIds = actuales.stream()
                        .map(pv -> pv.getMethodology().getId())
                        .collect(Collectors.toSet());

                System.out.println("nuevos ids met: " + nuevosIds + ", actualesIds met: " + actualesIds);
                // Si son iguales, no hay cambios → salimos sin hacer nada
                if (nuevosIds.equals(actualesIds)) {
                    System.out.println("No hay cambios en las metodologías");
                } else {
                    System.out.println("Se encontraron cambios");
                    List<VegetableMethodology> vegmetsgaux = new ArrayList<>();
                    if (!vegetableForms.getVegetableMethodologies().isEmpty()) {
                        for (int i = 0; i < vegetableForms.getVegetableMethodologies().size(); i++) {
                            for (int j = 0; j < actuales.size(); j++) {
                                if (vegetableForms.getVegetableMethodologies().get(i).toString().equals(actuales.get(j).toString())) {//revisar aquí
                                    System.out.println("Si encontré met: " + actuales.get(j).getMethodology().getName());
                                    vegmetsgaux.add(actuales.get(j));
                                    c.removeVegetableMethodology(vegetableForms.getVegetableMethodologies().get(i));
                                    break;
                                }
                            }
                        }
                        vegetableForms.getVegetableMethodologies().removeAll(vegmetsgaux);//actuales
                        if (c.updateVegetableForms(vegetableForms)) {
                            vegetableForms = c.getVegetableFormsById(vegetableForms.getId());
                        }
                    }
                }

                for (Methodology selectedMethodology : selectedMethodologies) {
                    VegetableMethodology method = new VegetableMethodology();

                    VegetableMethodologyId vmi = new VegetableMethodologyId();
                    vmi.setMethodologyId(selectedMethodology.getId());
                    vmi.setVegetableFormId(vegetableForms.getId());

                    method.setId(vmi);

                    if (selectedMethodology.getId() == 14) {
                        if (autogamousSpecies != null && !autogamousSpecies.trim().isEmpty()) {
                            method.setDescription(autogamousSpecies);
                        }
                    }
                    if (selectedMethodology.getId() == 18) {
                        if (allogamousSpecies != null && !allogamousSpecies.trim().isEmpty()) {
                            method.setDescription(allogamousSpecies);
                        }
                    }

                    method.setMethodology(selectedMethodology);
                    method.setVegetableForms(vegetableForms);

                    selectedMethodology.getVegetableMethodologies().add(method);
                    vegetableForms.getVegetableMethodologies().add(method);
                }
            }

            //tab 14
            if (vegetableForms.getVarietiesCharacters() != null && !vegetableForms.getVarietiesCharacters().isEmpty()) {
                vegetableForms.getVarietiesCharacters().clear();
            }
            if (varietiesCharacters != null && !varietiesCharacters.isEmpty()) {
                for (VarietyCharacters character : varietiesCharacters) {
                    character.setVegetableForms(vegetableForms);
                    vegetableForms.getVarietiesCharacters().add(character);
                }
            }

            //tab 15
            if (vegetableForms.getSimilaritiesVariety() != null && !vegetableForms.getSimilaritiesVariety().isEmpty()) {
                vegetableForms.getSimilaritiesVariety().clear();
            }
            if (similaritiesVariety != null && !similaritiesVariety.isEmpty()) {
                for (SimilaryVariety similary : similaritiesVariety) {
                    similary.setVegetableForms(vegetableForms);
                    vegetableForms.getSimilaritiesVariety().add(similary);
                }
            }

            //tab 16 Anexos
            // 1) Cargar actuales (desde vegetableForms ya cargado)
            List<VegetableAnnexesData> actuales = new ArrayList<>(vegetableForms.getAnnexesData());

            // 2) IDs actuales en BD
            Set<Integer> actualesIds = actuales.stream()
                    .map(vad -> vad.getVegetableAnnexes().getId())
                    .collect(Collectors.toSet());

            // 3) IDs seleccionados ahora en UI (annexes con withFile == true OR con fileContent != null)
            Set<Integer> selectedIds = annexes.stream()
                    .filter(a -> a.isWithFile() || a.getFileContent() != null)
                    .map(VegetableAnnexes::getId)
                    .collect(Collectors.toSet());

            System.out.println("actualesIds: " + actualesIds);
            System.out.println("selectedIds: " + selectedIds);

            // 4) Calcula cuáles fueron eliminados (existían antes y ya no están)
            Set<Integer> removedIds = new HashSet<>(actualesIds);
            removedIds.removeAll(selectedIds);

            // 5) Calcula cuáles son nuevos (están seleccionados ahora y no estaban antes)
            Set<Integer> addedIds = new HashSet<>(selectedIds);
            addedIds.removeAll(actualesIds);

            // 6) IDs que se mantienen (por si quieres algo extra)
            Set<Integer> keptIds = new HashSet<>(selectedIds);
            keptIds.retainAll(actualesIds);

            System.out.println("removedIds: " + removedIds);
            System.out.println("addedIds: " + addedIds);
            System.out.println("keptIds: " + keptIds);

            // 7) BORRAR explícitamente los que fueron removidos
            for (Integer annId : removedIds) {
                // busca el VegetableAnnexesData correspondiente en 'actuales'
                VegetableAnnexesData vad = actuales.stream()
                        .filter(x -> x.getVegetableAnnexes().getId().equals(annId))
                        .findFirst().orElse(null);

                if (vad != null) {
                    System.out.println("Eliminando VAD idAnnex=" + annId);

                    if (eliminarArchivoFisico(vad)) {
                        c.removeVegetableAnnexesData(vad); // tu DAO / Controller
                        // también quitar de la colección local por si la usas más abajo
                        actuales.remove(vad);
                    }
                }
            }

            // 8) Ahora reconstruir la colección administrada vegetableForms.getAnnexesData()
            //    Empezamos vaciando (si se quiere), o limpiar y luego añadir los que quedan + nuevos
            vegetableForms.getAnnexesData().clear();

            // 9) Añadir los que se mantienen (keptIds), reutilizando los 'actuales' que quedan
            for (Integer annId : keptIds) {
                VegetableAnnexesData vad = actuales.stream()
                        .filter(x -> x.getVegetableAnnexes().getId().equals(annId))
                        .findFirst().orElse(null);
                if (vad != null) {
                    vegetableForms.getAnnexesData().add(vad);
                }
            }

            // 10) Crear y añadir los nuevos (addedIds)
            for (Integer annId : addedIds) {
                VegetableAnnexes annexe = annexes.stream()
                        .filter(a -> a.getId().equals(annId))
                        .findFirst().orElse(null);
                if (annexe == null) {
                    continue; // protección
                }
                // crea nuevo VAD (usa tu método)
                VegetableAnnexesData vad = crearNuevoAnnexData(annexe, vegetableForms);

                // subir archivo (si corresponde) ya lo hace el crearNuevoAnnexData
                vegetableForms.getAnnexesData().add(vad);
            }
            //tab 18
            if (declaration != null) {
                System.out.println("declaration: " + declaration.getDeclarationDate());
                System.out.println("vegetablef: " + vegetableForms.getId());

                declaration.setVegetableForms(vegetableForms);
                vegetableForms.setDeclaration(declaration);
            }

            if (c.updateVegetableForms(vegetableForms)) {
                if (action == 2) {
                    //aquí hacer el reporte de vista previa
                    if (c.generateVegetableFormsPdfPreview(vegetableForms.getId())) {
                        Operations.mensaje(Operations.INFORMACION, "SE HA GENERADO LA VISTA PREVIA CORRECTAMENTE");//                        
                        return true;
                    } else {
                        Operations.mensaje(Operations.AVISO, "NO SE PUDO CREAR EL DOCUMENTO DE VISTA PREVIA, INTENTE DE NUEVO");
                        return false;
                    }
                }
                if (action == 3) {
                    if (generateVoucherDocument()) {
                        if (c.generateVegetableFormsPdfPreview(vegetableForms.getId())) {
                            Operations.mensaje(Operations.INFORMACION, "SE HA GENERADO CORRECTAMENTE EL COMPROBANTE");
                            return true;
                        } else {
                            Operations.mensaje(Operations.AVISO, "NO SE PUDO CREAR EL DOCUMENTO DE VISTA PREVIA, INTENTE DE NUEVO");
                            return false;
                        }
                    } else {
                        Operations.mensaje(Operations.ERROR, "NO SE GENERÓ EL DOCUMENTO PDF DEL COMPROBANTE, INTENTE MÁS TARDE");
                        return false;
                    }
                }
                return true;
            } else {
                Operations.mensaje(Operations.ERROR, "HUBO UN ERROR AL INTENTAR GUARDAR LOS SOLICITANTES");
                return false;
            }
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE HA PODIDO GUARDAR EL REGISTRO");
            return false;
        }
    }

    public boolean generateVoucherDocument() throws IOException, JRException {
        Controller c = new Controller();
        vegetableForms = c.getVegetableFormsById(vegetableForms.getId());

        //obtengo el siguiente número de trámite (solo el entero) del período actual
        int next = c.getNextApplicationNumber();
        System.out.println("Siguiente número de trámite: " + next);
        int year = LocalDate.now().getYear();

        String applicationNumber = "SENADI-" + year + "-" + next;
        String tableName = "BreederForm";
        int ownerId = login.getOwner().getId();
        String status = "ACTIVE";
        String serviceWindow = "NO";

        //creo el nuevo registro con el nuevo número de trámite en la tabla applications
        if (c.createApplications(applicationNumber, tableName, ownerId, status, year, next, serviceWindow)) {
            System.out.println("applications creado: " + applicationNumber);
            //creación del registro en breederForm            
            BreederForm breeder = new BreederForm();
            breeder.setProposedName(vegetableForms.getBotanicalTaxon());
            breeder.setCommercialName(vegetableForms.getCommonName());
            breeder.setStatus("FINISHED");
            breeder.setApplicationNumber(applicationNumber);
            breeder.setOwnerId(login.getOwner().getId());
            breeder.setGroup(vegetableForms.getVarietalGroup());
            breeder.setDiscountFile(vegetableForms.getDiscountFile());

            int idb = c.saveBreederForm(breeder);
            if (idb != 0) {
                System.out.println("breeder_form creado");

                List<Person> applicantsvf = vegetableForms.getPersonsType("APPLICANT");
                int contapp = 0;
                for (int i = 0; i < applicantsvf.size(); i++) {
                    PersonBreeder personBreeder = new PersonBreeder();
                    personBreeder.setBreederFormId(idb);
                    personBreeder.setPersonId(applicantsvf.get(i).getId());
                    personBreeder.setTypePerson("APPLICANT");
                    if (c.savePersonBreeder(personBreeder)) {
                        contapp++;
                    }
                }
                if (contapp == applicantsvf.size()) {
                    if (breeder.getDiscountFile() != null && !breeder.getDiscountFile().trim().isEmpty()) {
                        //guardar pdf certificado de descuento en expediente
                        System.out.println("llegando 11111 a");
                        SFTPUtil sftp = new SFTPUtil();
                        if (sftp.copiarArchivoEnServidorRemoto(Parameter.RUTA_CERTDESC, Parameter.RUTA_SERVER + vegetableForms.getId(), breeder.getDiscountFile())) {
                            System.out.println("llegando 11111 b");
                            System.out.println("Se ha copiado satisfactoriamente el certificado de descuento " + breeder.getDiscountFile() + " en el expediente " + idb);
                        } else {
                            System.out.println("Hubo un problema al copiar el certificado de descuento " + breeder.getDiscountFile() + " en " + idb);
                        }
                    }

                    //crear el comprobante de pago en solicitudes
                    FormPaymentRates formpay = new FormPaymentRates();
                    formpay.setDate(new Timestamp(System.currentTimeMillis()));
                    if (vegetableForms.getVarietalGroup().equals("A")) {
                        formpay.setPaymentRateId(528);
                    } else {
                        formpay.setPaymentRateId(10);
                    }
                    formpay.setSerialForm(idb);

                    int idfpr = c.saveFormPaymentRates(formpay);
                    if (idfpr != 0) {
                        //crear comprobante de pago en vegetable                    
                        FormPaymentRate fpr = new FormPaymentRate();
                        fpr.setCreateDate(formpay.getDate());
                        fpr.setFormPaymentRateId(idfpr);
                        fpr.setPaymentRateId(formpay.getPaymentRateId());
                        fpr.setVegetableForms(vegetableForms);
                        vegetableForms.setFormPaymentRate(fpr);

                        vegetableForms.setStatus(Status.FINISHED);
                        vegetableForms.setApplicationNumber(applicationNumber);

                        if (c.updateVegetableForms(vegetableForms)) {
                            if (generateVoucherPdf()) {
                                return true;
                            } else {
                                System.err.println("No se pudo generar el documento pdf voucher del vf " + vegetableForms.getId());
                                return false;
                            }
                        } else {
                            System.err.println("No se actualizó el vegetable_forms: " + vegetableForms.getId());
                            return false;
                        }
                    } else {
                        System.err.println("No se creó el form_payment_rates");
                        return false;
                    }
                }else{
                    System.err.println("No se crearon todos los aplicantes de vf "+vegetableForms.getId());
                    return false;
                }

            } else {
                System.err.println("No se creó el BreederForm en Solicitudes");
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean generateVoucherPdf() throws IOException, JRException {
        Report report = new Report();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String path = ec.getRealPath("/WEB-INF/report/");
        InputStream is = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getResourceAsStream("/WEB-INF/report/VoucherReport.jrxml");

        FileInputStream in = report.generatePDFVoucher(path, is, "archivo.xls", vegetableForms);
        SFTPUtil sftp = new SFTPUtil();
        ByteArrayInputStream input = new ByteArrayInputStream(in.readAllBytes());
        return sftp.guardarArchivoEnServidorRemoto(input, vegetableForms.getId(), "pdf_voucher_breederfrm_" + vegetableForms.getId() + ".pdf");
    }

    private boolean eliminarArchivoFisico(VegetableAnnexesData vad) {
        try {
            String fileName = vad.getFileName();
            Integer formId = vad.getVegetableForms().getId();

            SFTPUtil sftp = new SFTPUtil();
            if (sftp.eliminarArchivoEnServidorRemoto(fileName, formId)) {
                System.out.println("Archivo eliminado del filesystem: " + fileName);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.err.println("⚠ Error eliminando archivo del filesystem: " + e.getMessage());
            return false;
        }
    }

    private VegetableAnnexesData crearNuevoAnnexData(VegetableAnnexes annexe, VegetableForms vegetableForms) {

        VegetableAnnexesData vad = new VegetableAnnexesData();

        VegetableAnnexesDataId vaid = new VegetableAnnexesDataId();
        vaid.setVegetableAnnexesId(annexe.getId());
        vaid.setVegetableFormId(vegetableForms.getId());

        vad.setId(vaid);

        vad.setFileName("annexe_" + vegetableForms.getId() + "_" + annexe.getId() + "_" + System.currentTimeMillis() + ".pdf");

        if (annexe.getId() == 13) {
            if (otherAnnexDesc != null && !otherAnnexDesc.trim().isEmpty()) {
                vad.setAnotherAnnexe(otherAnnexDesc);
            }
        }

        // Guardar archivo
        SFTPUtil sftp = new SFTPUtil();
        ByteArrayInputStream input = new ByteArrayInputStream(annexe.getFileContent());
        sftp.guardarArchivoEnServidorRemoto(input, vegetableForms.getId(), vad.getFileName());

        vad.setVegetableAnnexes(annexe);
        vad.setVegetableForms(vegetableForms);

        return vad;
    }

    public void saveVegetableForms(ActionEvent ae) throws IOException, JRException {
        if (vegetableForms != null) {
            if (action == 1) {
                if (validarTaxon()) {
                    removeErrors();
                    if (preliminarSave()) {
                        PrimeFaces.current().ajax().addCallbackParam("doit", true);
                    }
                }
            } else if (action == 2 || action == 3) {
                if (validarCampos()) {
                    preliminarSave();
                    PrimeFaces.current().ajax().addCallbackParam("doit", true);
                }
            }
//            else if (action == 3) {
//                if (validarCampos()) {
//                    preliminarSave();
//                    PrimeFaces.current().ajax().addCallbackParam("doit", true);
//                }
//            }
        } else {
            Operations.mensaje(Operations.ERROR, "NO SE RECONOCE EL FORMULARIO ACTUAL");
        }
    }

    public void removeErrors() {
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

        showTab17Error = false;
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

    public void removeVegetableAnnexe(ActionEvent ae) {
        if (currentAnnex != null && !currentAnnex.getName().trim().isEmpty()) {
            currentAnnex.setCurrentFile(null);
            currentAnnex.setFileContent(null);
            currentAnnex.setWithFile(false);
            for (int i = 0; i < annexes.size(); i++) {
                if (annexes.get(i).getId().equals(currentAnnex.getId())) {
                    annexes.set(i, currentAnnex);
                }
            }
            PrimeFaces.current().ajax().addCallbackParam("remanx", true);
            Operations.mensaje(Operations.INFORMACION, "SE HA REMOVIDO EL ANEXO SATISFACTORIAMENTE");
        } else {
            Operations.mensaje(Operations.ERROR, "ERROR AL REMOVER ANEXO");
        }
    }

    public void saveVegetableAnnexe(ActionEvent ae) throws IOException {
        if (currentAnnex != null && !currentAnnex.getName().trim().isEmpty()) {
            if (currentFile != null && !currentFile.getFileName().trim().isEmpty()) {
                if (currentFile.getSize() < (16 * 1024 * 1024)) {
                    currentAnnex.setCurrentFile(currentFile);
                    InputStream input = currentFile.getInputStream();
                    currentAnnex.setFileContent(input.readAllBytes());
                    currentAnnex.setWithFile(true);

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
        person = (Person) applicantData.getRowData();
        if (person != null && person.getId() != null) {
            applicants.remove(person);
            if (applicants.isEmpty()) {
                showApplicantsTableError = true;
            }
            activeIndex = 0;
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
            activeIndex = 1;
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
                vegetableProtection.setVegetableForms(vegetableForms);
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
            if (currentAnnex.getCurrentFile() != null) {
                currentFile = currentAnnex.getCurrentFile();
            } else if (currentAnnex.getIdAnnexes() != null && currentAnnex.getIdVegatableForms() != null) {
                Controller c = new Controller();
                annexeAux = c.getVegetableAnnexesDataByIds(currentAnnex.getIdAnnexes(), currentAnnex.getIdVegatableForms());
                if (currentAnnex.getId() == 13) {
                    otherAnnexDesc = annexeAux.getAnotherAnnexe();
                }
//                System.out.println("Si, ya existe en annexesdata");
            }

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

    public void validateAnnexe(VegetableAnnexesData vaux) {
        if (vaux.getId() != null) {
            if (vaux.getFileName() != null && !vaux.getFileName().trim().isEmpty()) {
                PrimeFaces.current().ajax().addCallbackParam("viewan", true);
                PrimeFaces.current().ajax().addCallbackParam("filean", Parameter.RUTA_URL + vaux.getId().getVegetableFormId() + "/" + vaux.getFileName());
            }
        } else {
            Operations.mensaje(Operations.ERROR, "HAY UN PROBLEMA CON EL ANEXO");
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

    /**
     * @return the action
     */
    public Integer getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(Integer action) {
        this.action = action;
    }

    /**
     * @return the formTitle
     */
    public String getFormTitle() {
        return formTitle;
    }

    /**
     * @param formTitle the formTitle to set
     */
    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    /**
     * @return the editId
     */
    public Integer getEditId() {
        return editId;
    }

    /**
     * @param editId the editId to set
     */
    public void setEditId(Integer editId) {
        this.editId = editId;
    }

    /**
     * @return the annexeAux
     */
    public VegetableAnnexesData getAnnexeAux() {
        return annexeAux;
    }

    /**
     * @param annexeAux the annexeAux to set
     */
    public void setAnnexeAux(VegetableAnnexesData annexeAux) {
        this.annexeAux = annexeAux;
    }

    /**
     * @return the declaration
     */
    public Declaration getDeclaration() {
        return declaration;
    }

    /**
     * @param declaration the declaration to set
     */
    public void setDeclaration(Declaration declaration) {
        this.declaration = declaration;
    }

    /**
     * @return the showTab17Error
     */
    public boolean isShowTab17Error() {
        return showTab17Error;
    }

    /**
     * @param showTab17Error the showTab17Error to set
     */
    public void setShowTab17Error(boolean showTab17Error) {
        this.showTab17Error = showTab17Error;
    }

    /**
     * @return the showTab18Error
     */
    public boolean isShowTab18Error() {
        return showTab18Error;
    }

    /**
     * @param showTab18Error the showTab18Error to set
     */
    public void setShowTab18Error(boolean showTab18Error) {
        this.showTab18Error = showTab18Error;
    }

    /**
     * @return the varietalGroupText
     */
    public String getVarietalGroupText() {
        return varietalGroupText;
    }

    /**
     * @param varietalGroupText the varietalGroupText to set
     */
    public void setVarietalGroupText(String varietalGroupText) {
        this.varietalGroupText = varietalGroupText;
    }

    /**
     * @return the applyDiscountLink
     */
    public boolean isApplyDiscountLink() {
        return applyDiscountLink;
    }

    /**
     * @param applyDiscountLink the applyDiscountLink to set
     */
    public void setApplyDiscountLink(boolean applyDiscountLink) {
        this.applyDiscountLink = applyDiscountLink;
    }

    /**
     * @return the discountCode
     */
    public String getDiscountCode() {
        return discountCode;
    }

    /**
     * @param discountCode the discountCode to set
     */
    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    /**
     * @return the descuento
     */
    public Descuento getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
    }
}
