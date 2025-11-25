/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import senadi.gob.ec.ov.model.enums.ProtectionType;

/**
 *
 * @author michael
 */
@Entity
@Table(name = "vegetable_protection")
public class VegetableProtection implements Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "submission_country_id")
    private Integer submissionCountryId;
    
    @Column(name = "submission_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date submissionDate;
    
    @Column(name = "application_number")
    private String applicationNumber;
    
    @Column(name = "register_number")
    private String registerNumber;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "denomination")
    private String denomination;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "protection_type", nullable = false)
    private ProtectionType protectionType;
    
    @ManyToOne
    private VegetableForms vegetableForms;

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
     * @return the submissionCountryId
     */
    public Integer getSubmissionCountryId() {
        return submissionCountryId;
    }

    /**
     * @param submissionCountryId the submissionCountryId to set
     */
    public void setSubmissionCountryId(Integer submissionCountryId) {
        this.submissionCountryId = submissionCountryId;
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
     * @return the registerNumber
     */
    public String getRegisterNumber() {
        return registerNumber;
    }

    /**
     * @param registerNumber the registerNumber to set
     */
    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    /**
     * @return the denomination
     */
    public String getDenomination() {
        return denomination;
    }

    /**
     * @param denomination the denomination to set
     */
    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    /**
     * @return the protectionType
     */
    public ProtectionType getProtectionType() {
        return protectionType;
    }

    /**
     * @param protectionType the protectionType to set
     */
    public void setProtectionType(ProtectionType protectionType) {
        this.protectionType = protectionType;
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
     * @return the submissionDate
     */
    public Date getSubmissionDate() {
        return submissionDate;
    }

    /**
     * @param submissionDate the submissionDate to set
     */
    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }
}
