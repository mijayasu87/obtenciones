/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.dao;

import java.util.List;
import javax.persistence.Query;
import senadi.gob.ec.ov.model.VegetableAnnexes;
import senadi.gob.ec.ov.model.VegetableAnnexesData;

/**
 *
 * @author michael
 */
public class VegetableAnnexesDAO extends DAOAbstractM<VegetableAnnexes> {

    public VegetableAnnexesDAO(VegetableAnnexes t) {
        super(t);
    }

    @Override
    public List<VegetableAnnexes> buscarTodos() {
        Query query = this.getEntityManager().createQuery("Select v from VegetableAnnexes v order by v.id");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return query.setMaxResults(300).getResultList();
    }

    public VegetableAnnexesData getVegetableAnnexesDataByIds(Integer idannexedata, Integer idVegetableForms) {
        Query query = this.getEntityManager().createQuery("Select v from VegetableAnnexesData v where v.id.vegetableAnnexesId = :idann and v.id.vegetableFormId = :idvef");
        query.setParameter("idann", idannexedata);
        query.setParameter("idvef", idVegetableForms);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<VegetableAnnexesData> annexes = query.getResultList();
        if (annexes.isEmpty()) {
            return new VegetableAnnexesData();
        } else {
            return annexes.get(0);
        }
    }

}
