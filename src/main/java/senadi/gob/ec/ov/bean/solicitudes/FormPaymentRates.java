/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.bean.solicitudes;

import java.sql.Timestamp;

/**
 *
 * @author Michael
 */
public class FormPaymentRates {
    private Integer id;
    private Integer voucherId;
    private Integer paymentRateId;
    private Integer serialForm;
    private Timestamp date;
    private Double paidValue;

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
     * @return the voucherId
     */
    public Integer getVoucherId() {
        return voucherId;
    }

    /**
     * @param voucherId the voucherId to set
     */
    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    /**
     * @return the paymentRateId
     */
    public Integer getPaymentRateId() {
        return paymentRateId;
    }

    /**
     * @param paymentRateId the paymentRateId to set
     */
    public void setPaymentRateId(Integer paymentRateId) {
        this.paymentRateId = paymentRateId;
    }

    /**
     * @return the serialForm
     */
    public Integer getSerialForm() {
        return serialForm;
    }

    /**
     * @param serialForm the serialForm to set
     */
    public void setSerialForm(Integer serialForm) {
        this.serialForm = serialForm;
    }

    /**
     * @return the date
     */
    public Timestamp getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Timestamp date) {
        this.date = date;
    }

    /**
     * @return the paidValue
     */
    public Double getPaidValue() {
        return paidValue;
    }

    /**
     * @param paidValue the paidValue to set
     */
    public void setPaidValue(Double paidValue) {
        this.paidValue = paidValue;
    }
}
