/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.dao;

import java.util.List;
import javax.persistence.Query;
import senadi.gob.ec.ov.model.VegetableProtection;

/**
 *
 * @author michael
 */
public class VegetableProtectionDAO extends DAOAbstractM<VegetableProtection>{

    public VegetableProtectionDAO(VegetableProtection t) {
        super(t);
    }

    @Override
    public List<VegetableProtection> buscarTodos() {
        Query query = this.getEntityManager().createQuery("Select v from VegetableForms v");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return query.setMaxResults(300).getResultList();
    }
    
}
