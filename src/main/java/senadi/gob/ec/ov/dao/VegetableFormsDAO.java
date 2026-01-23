/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.dao;

import java.util.List;
import javax.persistence.Query;
import senadi.gob.ec.ov.model.VegetableForms;
import senadi.gob.ec.ov.model.enums.Status;

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

    public List<VegetableForms> getVegetableFormsByOwnerId(Integer id) {
        Query query = this.getEntityManager().createQuery("Select v from VegetableForms v where v.ownerId = :id order by v.id desc");
        query.setParameter("id", id);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return query.getResultList();
    }

    public VegetableForms getVegetableFormsById(Integer id) {
        Query query = this.getEntityManager().createQuery("Select v from VegetableForms v where v.id = :id");
        query.setParameter("id", id);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<VegetableForms> vfs = query.getResultList();
        if (vfs.isEmpty()) {
            return new VegetableForms();
        } else {
            return vfs.get(0);
        }
    }

    public VegetableForms getVegetableFormsByApplicationNumber(String applicationNumber) {
        Query query = this.getEntityManager().createQuery("Select v from VegetableForms v where v.applicationNumber = :appnumber");
        query.setParameter("appnumber", applicationNumber);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<VegetableForms> vegs = query.getResultList();
        if (vegs.isEmpty()) {
            return new VegetableForms();
        } else {
            return vegs.get(0);
        }
    }
    
    public List<VegetableForms> getVegetableFormsPaymentByOwnerId(Integer ownerId){
        Query query = this.getEntityManager().createQuery("Select v from VegetableForms v where v.ownerId = :ownerid and v.paymentReceiptId is not null and v.status = :status");
        query.setParameter("ownerid", ownerId);
        query.setParameter("status", Status.FINISHED);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return query.getResultList();
    }
}
