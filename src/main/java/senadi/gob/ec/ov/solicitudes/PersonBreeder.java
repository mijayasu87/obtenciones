/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.solicitudes;

/**
 *
 * @author michael
 */
public class PersonBreeder {

    private Integer breederFormId;
    private Integer personId;
    private String typePerson;

    /**
     * @return the breederFormId
     */
    public Integer getBreederFormId() {
        return breederFormId;
    }

    /**
     * @param breederFormId the breederFormId to set
     */
    public void setBreederFormId(Integer breederFormId) {
        this.breederFormId = breederFormId;
    }

    /**
     * @return the personId
     */
    public Integer getPersonId() {
        return personId;
    }

    /**
     * @param personId the personId to set
     */
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    /**
     * @return the typePerson
     */
    public String getTypePerson() {
        return typePerson;
    }

    /**
     * @param typePerson the typePerson to set
     */
    public void setTypePerson(String typePerson) {
        this.typePerson = typePerson;
    }
    
    @Override
    public String toString(){
        return "("+getBreederFormId()+", "+getPersonId()+", "+getTypePerson()+")";
    }
}
