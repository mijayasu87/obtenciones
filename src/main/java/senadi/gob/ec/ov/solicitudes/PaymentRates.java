/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.solicitudes;

/**
 *
 * @author michael
 */
public class PaymentRates {
    private Integer id;
    private Integer formId;
    private Integer formTypeId;
    private Integer subFormType;
    private String description;
    private String paymentCode;
    private Double discount;
    private Double value;
    private String lawDescription;
    private String shortPaymentCode;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the formId
     */
    public Integer getFormId() {
        return formId;
    }

    /**
     * @param formId the formId to set
     */
    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    /**
     * @return the formTypeId
     */
    public Integer getFormTypeId() {
        return formTypeId;
    }

    /**
     * @param formTypeId the formTypeId to set
     */
    public void setFormTypeId(Integer formTypeId) {
        this.formTypeId = formTypeId;
    }

    /**
     * @return the subFormType
     */
    public Integer getSubFormType() {
        return subFormType;
    }

    /**
     * @param subFormType the subFormType to set
     */
    public void setSubFormType(Integer subFormType) {
        this.subFormType = subFormType;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the paymentCode
     */
    public String getPaymentCode() {
        return paymentCode;
    }

    /**
     * @param paymentCode the paymentCode to set
     */
    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    /**
     * @return the discount
     */
    public Double getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    /**
     * @return the value
     */
    public Double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * @return the lawDescription
     */
    public String getLawDescription() {
        return lawDescription;
    }

    /**
     * @param lawDescription the lawDescription to set
     */
    public void setLawDescription(String lawDescription) {
        this.lawDescription = lawDescription;
    }

    /**
     * @return the shortPaymentCode
     */
    public String getShortPaymentCode() {
        return shortPaymentCode;
    }

    /**
     * @param shortPaymentCode the shortPaymentCode to set
     */
    public void setShortPaymentCode(String shortPaymentCode) {
        this.shortPaymentCode = shortPaymentCode;
    }
}
