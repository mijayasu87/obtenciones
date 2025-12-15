/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.dao;

import java.util.List;
import javax.persistence.Query;
import senadi.gob.ec.ov.model.PersonVegetable;

/**
 *
 * @author michael
 */
public class PersonVegetableDAO extends DAOAbstractM<PersonVegetable>{

    public PersonVegetableDAO(PersonVegetable t) {
        super(t);
    }

    @Override
    public List<PersonVegetable> buscarTodos() {
        Query query = this.getEntityManager().createQuery("Select p from PersonVegetable p");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return query.getResultList();
    }
    
}
