/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import senadi.gob.ec.ov.model.VegetableForms;
import senadi.gob.ec.ov.util.Controller;

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

    public VegetableFormsBean(){
        loadData();
    }
    
    private void loadData(){
        Controller c = new Controller();
        login = c.getLogin();
        vegetablesFormses = c.getVegetableFormsByOwnerId(login.getOwner().getId());
        loadMainData();
    }
    
    private void loadMainData(){
        Controller c = new Controller();
        c.createMethodologies();
        c.createVegetableAnnexes();
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

}
