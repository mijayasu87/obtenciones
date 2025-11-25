/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.dao;

import java.util.List;
import javax.persistence.Query;
import senadi.gob.ec.ov.model.VegetableForms;

/**
 *
 * @author michael
 */
public class VegetableFormsDAO extends DAOAbstractM<VegetableForms> {

    public VegetableFormsDAO(VegetableForms t) {
        super(t);
    }

    @Override
    public List<VegetableForms> buscarTodos() {
        Query query = this.getEntityManager().createQuery("Select v from VegetableForms v order by v.id");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return query.setMaxResults(300).getResultList();
    }
        
    public List<VegetableForms> getVegetableFormsByOwnerId(Integer id){
        Query query = this.getEntityManager().createQuery("Select v from VegetableForms v where v.id = :id order by v.id desc");
        query.setParameter("id", id);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return query.getResultList();
    }
}
