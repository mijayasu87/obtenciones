/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.dao;

import java.util.List;
import javax.persistence.Query;
import senadi.gob.ec.ov.model.Person;

/**
 *
 * @author michael
 */
public class PersonVDAO extends DAOAbstractM<Person>{

    public PersonVDAO(Person t) {
        super(t);
    }

    @Override
    public List<Person> buscarTodos() {
        Query query = this.getEntityManager().createQuery("Select p from Person p");
        return query.getResultList();
    }
    
    public boolean personExists(Person person){
        Query query = this.getEntityManager().createQuery("Select p from Person p where p.id =:idPerson");
        query.setParameter("idPerson", person.getId());
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<Person> persons = query.getResultList();
        return !persons.isEmpty();
    }
    
}
