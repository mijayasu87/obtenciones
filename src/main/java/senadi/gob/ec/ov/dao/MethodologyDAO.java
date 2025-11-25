/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.dao;

import java.util.List;
import javax.persistence.Query;
import senadi.gob.ec.ov.model.Methodology;

/**
 *
 * @author michael
 */
public class MethodologyDAO extends DAOAbstractM<Methodology>{

    public MethodologyDAO(Methodology t) {
        super(t);
    }

    @Override
    public List<Methodology> buscarTodos() {
        Query query = this.getEntityManager().createQuery("SELECT m from Methodology m");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return query.getResultList();
    }
    
}
