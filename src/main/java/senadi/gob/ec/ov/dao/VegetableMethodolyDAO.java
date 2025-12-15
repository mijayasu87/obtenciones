/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.dao;

import java.util.List;
import javax.persistence.Query;
import senadi.gob.ec.ov.model.VegetableMethodology;

/**
 *
 * @author michael
 */
public class VegetableMethodolyDAO extends DAOAbstractM<VegetableMethodology>{

    public VegetableMethodolyDAO(VegetableMethodology t) {
        super(t);
    }

    @Override
    public List<VegetableMethodology> buscarTodos() {
        Query query = this.getEntityManager().createQuery("Select v from VegetableMethodology v");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return query.getResultList();
    }
    
}
