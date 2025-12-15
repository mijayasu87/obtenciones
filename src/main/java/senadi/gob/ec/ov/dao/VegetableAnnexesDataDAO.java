/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.dao;

import java.util.List;
import javax.persistence.Query;
import senadi.gob.ec.ov.model.VegetableAnnexesData;

/**
 *
 * @author michael
 */
public class VegetableAnnexesDataDAO extends DAOAbstractM<VegetableAnnexesData>{

    public VegetableAnnexesDataDAO(VegetableAnnexesData t) {
        super(t);
    }

    @Override
    public List<VegetableAnnexesData> buscarTodos() {
        Query query = this.getEntityManager().createQuery("Select v from VegetableAnnexesData v");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return query.getResultList();
    }
    
}
