/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.bean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.event.ActionEvent;
import org.primefaces.PrimeFaces;
import senadi.gob.ec.ov.model.VegetableForms;
import senadi.gob.ec.ov.model.enums.Status;
import senadi.gob.ec.ov.solicitudes.BreederForm;
import senadi.gob.ec.ov.util.Controller;
import senadi.gob.ec.ov.util.Operations;
import senadi.gob.ec.ov.util.Parameter;

/**
 *
 * @author michael
 */
@ManagedBean(name = "vegetableFormBean")
@ViewScoped
public class VegetableFormsBean implements Serializable {

    private List<VegetableForms> vegetablesFormses;
    private List<VegetableForms> vegetablesFormsFilter;
    private VegetableForms vegetableForms;
    private UIData vegetableFormsData;

    private LoginBean login;

    private String previewPath;        

    public VegetableFormsBean() {
        loadData();
    }

    private void loadData() {
        Controller c = new Controller();
        login = c.getLogin();
        vegetablesFormses = c.getVegetableFormsByOwnerId(login.getOwner().getId());
        loadMainData();
    }

    private void loadMainData() {
        Controller c = new Controller();
        c.createMethodologies();
        c.createVegetableAnnexes();
        System.out.println("Revisando pagos realizados en bp");
        //actualiza los pagos que se hacen desde el banco de pacífico
        c.updateVegetableFormsPaymentByOnwerId(login.getOwner().getId());
        //borra el pago (payment_receipt_id) de vegetable_forms en caso que se haya cancelado en el banco pacífico
        System.out.println("Revisando pagos cancelados por bp");
        c.removeVegetablePayWhenCancelledByOwnerId(login.getOwner().getId());
        
    }

    public void prepareEditRecord(ActionEvent ae) {
        vegetableForms = (VegetableForms) vegetableFormsData.getRowData();
        if (vegetableForms != null && vegetableForms.getId() != null) {
//            System.out.println("si llegooooo vf");
            PrimeFaces.current().ajax().addCallbackParam("viewr", true);
            PrimeFaces.current().ajax().addCallbackParam("pagveg", "createbreed.xhtml?editId=" + vegetableForms.getId());
        } else {
            Operations.mensaje(Operations.ERROR, "HAY UN PROBLEMA CON EL REGISTRO SELECCIONADO");
        }
    }

    public void viewVoucher(ActionEvent ae) {
        vegetableForms = (VegetableForms) vegetableFormsData.getRowData();
        if (vegetableForms != null && vegetableForms.getId() != null) {
            previewPath = Parameter.RUTA_URL + vegetableForms.getId() + "/pdf_voucher_breederfrm_" + vegetableForms.getId() + ".pdf";
            System.out.println("preview path: " + previewPath);
            //Operations.mensaje(Operations.INFORMACION, "ENVIADO A IMPRIMIR");
            PrimeFaces.current().ajax().addCallbackParam("url", previewPath);
            PrimeFaces.current().ajax().addCallbackParam("doit", true);
        } else {
            Operations.mensaje(Operations.ERROR, "HAY UN PROBLEMA CON EL REGISTRO SELECCIONADO");
        }
    }

    public void viewFormulario(ActionEvent ae) {
        vegetableForms = (VegetableForms) vegetableFormsData.getRowData();
        if (vegetableForms != null && vegetableForms.getId() != null) {
            previewPath = Parameter.RUTA_URL + vegetableForms.getId() + "/pdf_breederfrm_" + vegetableForms.getId() + ".pdf";
            System.out.println("preview path: " + previewPath);
            //Operations.mensaje(Operations.INFORMACION, "ENVIADO A IMPRIMIR");
            PrimeFaces.current().ajax().addCallbackParam("url", previewPath);
            PrimeFaces.current().ajax().addCallbackParam("doit", true);
        } else {
            Operations.mensaje(Operations.ERROR, "HAY UN PROBLEMA CON EL REGISTRO SELECCIONADO");
        }
    }

    public void viewFormularioPrueba(ActionEvent ae) {
        vegetableForms = (VegetableForms) vegetableFormsData.getRowData();
        if (vegetableForms != null && vegetableForms.getId() != null) {
            login.setIdVegetableForms(vegetableForms.getId());
            PrimeFaces.current().ajax().addCallbackParam("doit", true);
        } else {
            Operations.mensaje(Operations.ERROR, "HAY UN PROBLEMA CON EL REGISTRO SELECCIONADO");
        }
    }

    public void startProcess(ActionEvent ae) throws IOException {
        vegetableForms = (VegetableForms) vegetableFormsData.getRowData();
        if (vegetableForms != null && vegetableForms.getId() != null) {
            BreederForm bf = new BreederForm();
            bf.setApplicationDate(new Timestamp(System.currentTimeMillis()));
            bf.setApplicationNumber(vegetableForms.getApplicationNumber());
            bf.setStatus(Status.DELIVERED.toString());
            Controller c = new Controller();
            if (c.updateBreederForm(bf)) {
                vegetableForms.setApplicationDate(bf.getApplicationDate());
                vegetableForms.setStatus(Status.DELIVERED);
                if (c.updateVegetableForms(vegetableForms)) {
                    if (c.generateVegetableFormsPdfPreview(vegetableForms.getId())) {
                        loadData();
                        Operations.mensaje(Operations.INFORMACION, "INICIO DE PROCESO REALIZADO CON ÉXITO");
                    } else {
                        Operations.mensaje(Operations.AVISO, "SE INICIO EL PROCESO PERO NO SE PUDO FIJAR LA FECHA DE INICIO DE PROCESO");
                    }
                } else {
                    Operations.mensaje(Operations.ERROR, "NO SE PUDO INICIAR EL PROCESO, INTENTE MÁS TARDE");
                }
            } else {
                Operations.mensaje(Operations.ERROR, "NO SE PUDO INICIAR EL PROCESO, INTENTE MÁS TARDE (bf)");
            }
        }
    }

    /**
     * @return the vegetablesFormsFilter
     */
    public List<VegetableForms> getVegetablesFormsFilter() {
        return vegetablesFormsFilter;
    }

    /**
     * @param vegetablesFormsFilter the vegetablesFormsFilter to set
     */
    public void setVegetablesFormsFilter(List<VegetableForms> vegetablesFormsFilter) {
        this.vegetablesFormsFilter = vegetablesFormsFilter;
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
     * @return the vegetablesFormses
     */
    public List<VegetableForms> getVegetablesFormses() {
        return vegetablesFormses;
    }

    /**
     * @param vegetablesFormses the vegetablesFormses to set
     */
    public void setVegetablesFormses(List<VegetableForms> vegetablesFormses) {
        this.vegetablesFormses = vegetablesFormses;
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
     * @return the vegetableFormsData
     */
    public UIData getVegetableFormsData() {
        return vegetableFormsData;
    }

    /**
     * @param vegetableFormsData the vegetableFormsData to set
     */
    public void setVegetableFormsData(UIData vegetableFormsData) {
        this.vegetableFormsData = vegetableFormsData;
    }

    /**
     * @return the previewPath
     */
    public String getPreviewPath() {
        return previewPath;
    }

    /**
     * @param previewPath the previewPath to set
     */
    public void setPreviewPath(String previewPath) {
        this.previewPath = previewPath;
    }

}
