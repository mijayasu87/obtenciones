/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.model.discount;

import java.util.List;
import javax.persistence.Query;
import senadi.gob.ec.ov.util.Operations;

/**
 *
 * @author micharesp
 */
public class CodigoDescuentoDAO extends DAOAbstractD<CodigoDescuento> {

    public CodigoDescuentoDAO(CodigoDescuento t) {
        super(t);
    }

    @Override
    public List<CodigoDescuento> buscarTodos() {
        Query query = this.getEntityManager().createQuery("Select c from CodigoDescuento c");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return query.getResultList();
    }

    public boolean existeCodigoVigente(String numero, int owner_id, String identificacion, boolean usado) {
        String fechaActual = Operations.getCurrentTimeStamp();
        Query query = this.getEntityManager().createQuery("Select c from CodigoDescuento c where c.identificacion = '" + identificacion + "' and c.numero = '" + numero + "' "
                + "and c.ownerId = " + owner_id + " and "
                + "c.usado = " + usado + " and c.fechaCreacion <= '" + fechaActual + "' and c.fechaCaduca >= '" + fechaActual + "'");
        List<CodigoDescuento> codigos = query.getResultList();
        if (codigos.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public CodigoDescuento getCodigoDescuentoByCode(String code) {
        String fechaActual = Operations.getCurrentTimeStamp();
        Query query = this.getEntityManager().createQuery("Select c from CodigoDescuento c where c.codigoGenerado = :code "
                + "and c.fechaCreacion <= '"+fechaActual+"' and c.fechaCaduca >= '"+fechaActual+"'");
        query.setParameter("code", code);
//        query.setParameter("fechaActual", fechaActual);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<CodigoDescuento> codigos = query.getResultList();
        if (codigos.isEmpty()) {
            return new CodigoDescuento();
        } else {
            return codigos.get(0);
        }
    }

    public CodigoDescuento getCodigoDescuento(String numero, int owner_id, String identificacion, boolean usado) {
        String fechaActual = Operations.getCurrentTimeStamp();
        Query query = this.getEntityManager().createQuery("Select c from CodigoDescuento c where c.identificacion = :idenficacion "
                + "and c.numero = :numero and c.ownerId = :owner_id and "
                + "c.usado = :usado and c.fechaCreacion <= :fechaActual and c.fechaCaduca >= :fechaActual");
        query.setParameter("identificacion", identificacion);
        query.setParameter("numero", numero);
        query.setParameter("owner_id", owner_id);
        query.setParameter("usado", usado);
        query.setParameter("fechaActual", fechaActual);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<CodigoDescuento> codigos = query.getResultList();
        if (codigos.isEmpty()) {
            return new CodigoDescuento();
        } else {
            return codigos.get(0);
        }
    }
    
    public Descuento getDescuentoByNumero(String numero){
        Query query = this.getEntityManager().createQuery("Select d from Descuento d where d.numero = :numero");
        query.setParameter("numero", numero);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<Descuento> descuentos = query.getResultList();
        if (descuentos.isEmpty()) {
            return new Descuento();
        } else {
            return descuentos.get(0);
        }
    }
}
