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
public class BreederForm {
    private Integer id;
    private Timestamp createDate;
    private Timestamp applicationDate;
    private String applicationNumber;
    private String status;
    private Integer paymentReceiptId;
    private String discountFile;

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
     * @return the applicationDate
     */
    public Timestamp getApplicationDate() {
        return applicationDate;
    }

    /**
     * @param applicationDate the applicationDate to set
     */
    public void setApplicationDate(Timestamp applicationDate) {
        this.applicationDate = applicationDate;
    }

    /**
     * @return the applicationNumber
     */
    public String getApplicationNumber() {
        return applicationNumber;
    }

    /**
     * @param applicationNumber the applicationNumber to set
     */
    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the paymentReceiptId
     */
    public Integer getPaymentReceiptId() {
        return paymentReceiptId;
    }

    /**
     * @param paymentReceiptId the paymentReceiptId to set
     */
    public void setPaymentReceiptId(Integer paymentReceiptId) {
        this.paymentReceiptId = paymentReceiptId;
    }

    /**
     * @return the discountFile
     */
    public String getDiscountFile() {
        return discountFile;
    }

    /**
     * @param discountFile the discountFile to set
     */
    public void setDiscountFile(String discountFile) {
        this.discountFile = discountFile;
    }

    /**
     * @return the createDate
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}
