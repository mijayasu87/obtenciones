/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.table;

import net.sf.jasperreports.engine.JRDataSource;

/**
 *
 * @author michael
 */
/**
 * @author GABRIEL GONZÁLEZ ROJAS
 * @Phone (+595)986 709 035
 * @Facebook http://www.facebook.com/gabrigonzaro
 */
import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class DataSource implements JRDataSource {

    private int contador = 0;
    @SuppressWarnings("rawtypes")
    private ArrayList lista;
    @SuppressWarnings("rawtypes")
    private ArrayList lista2;

    public DataSource() {
        super();
    }

    public boolean next() throws JRException {
        this.contador++;
        if (this.contador == 1) {
            return true;
        }

        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
        if (field == null) {
            return null;
        }

        if (field.getName().equalsIgnoreCase("lista")) {
            return new JRBeanCollectionDataSource(lista);
        }
        if (field.getName().equalsIgnoreCase("lista2")) {
            return new JRBeanCollectionDataSource(lista2);
        }
        return null;
    }

    public Object getFieldVal(JRField campo) throws JRException {
        if (campo == null) {
            return null;
        }
        if (campo.getName().equalsIgnoreCase("lista2")) {
            return new JRBeanCollectionDataSource(lista2);
        }
        return null;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public ArrayList getLista() {
        return lista;
    }

    public void setLista(ArrayList lista) {
        this.lista = lista;
    }

    public List getLista2() {
        return lista2;
    }

    public void setLista2(ArrayList lista2) {
        this.lista2 = lista2;
    }

}
