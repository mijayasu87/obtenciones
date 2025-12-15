/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.table;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import senadi.gob.ec.ov.model.Person;

/**
 *
 * @author michael
 */
public class PersonTableModel extends AbstractTableModel{
    String titulo[] = {"tipo_documento", "documento", "nombre", "direccion", "provincia", "ciudad", "email", "telefono","pais"};
    private List<Person> filas;
    private Person person;

    public PersonTableModel(List<Person> filas) {
        this.filas = filas;
    }

    @Override
    public int getRowCount() {
        return getFilas() != null ? getFilas().size() : 0;//retorna el numero de filas
    }

    @Override
    public int getColumnCount() {

        return titulo.length;
    }

    @Override
    public String getColumnName(int column) {
        return titulo[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        setPerson(getFilas().get(rowIndex));

        switch (columnIndex) {
            case 0:
                return getPerson().getIdentificationType();
            case 1:
                return getPerson().getIdentificationNumber();
            case 2:
                return getPerson().getName();
            case 3:
                return getPerson().getAddress();
            case 4:
                return "llenar provincia";
            case 5:
                return "llenar ciudad";
            case 6:
                return getPerson().getEmail();
            case 7:
                return getPerson().getPhone();
            case 8: 
                return getPerson().getCountryId()+"";
        }

        return null;
    }

    /**
     * @return the filas
     */
    public List<Person> getFilas() {
        return filas;
    }

    /**
     * @param filas the filas to set
     */
    public void setFilas(List<Person> filas) {
        this.filas = filas;
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

}
