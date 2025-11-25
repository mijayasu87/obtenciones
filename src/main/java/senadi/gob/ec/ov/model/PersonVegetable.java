/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import senadi.gob.ec.ov.model.embed.PersonVegetableId;
import senadi.gob.ec.ov.model.enums.PersonType;

/**
 *
 * @author michael
 */
@Entity
@Table(name = "person_vegetable")
public class PersonVegetable implements Serializable {

    @EmbeddedId
    private PersonVegetableId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("vegetableFormId")
    @JoinColumn(name = "vegetable_form_id", nullable = false)
    private VegetableForms vegetableForms;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("personId")
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Enumerated(EnumType.STRING)
    @Column(name = "person_type", nullable = false, length = 30)
    private PersonType personType;
    
    @Column(name = "judicial_locker", length = 30)
    private String judicialLocker;
    
    @Column(name = "power_code", length = 30)
    private String powerCode;        

    // Getters y setters
    public PersonVegetableId getId() {
        return id;
    }

    public void setId(PersonVegetableId id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
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
     * @return the judicialLocker
     */
    public String getJudicialLocker() {
        return judicialLocker;
    }

    /**
     * @param judicialLocker the judicialLocker to set
     */
    public void setJudicialLocker(String judicialLocker) {
        this.judicialLocker = judicialLocker;
    }

    /**
     * @return the powerCode
     */
    public String getPowerCode() {
        return powerCode;
    }

    /**
     * @param powerCode the powerCode to set
     */
    public void setPowerCode(String powerCode) {
        this.powerCode = powerCode;
    }
}
