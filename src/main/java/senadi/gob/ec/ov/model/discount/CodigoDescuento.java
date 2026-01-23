/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.model.discount;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author michael
 */
@Entity
@Table(name="codigo_descuento")
public class CodigoDescuento implements Serializable{
    @Id
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "codigo_generado")
    private String codigoGenerado;
    
    @Column(name = "owner_id")
    private Integer ownerId;
        
    @Column(name = "fecha_creacion")
    private Timestamp fechaCreacion;
    
    @Column(name = "fecha_caduca")
    private Timestamp fechaCaduca;
    
    @Column(name = "usado")
    private boolean usado;
    
    @Column(name = "numero_descuento")
    private String numero;
    
    @Column(name = "estado")
    private String estado;
    
    @Column(name = "identificacion")
    private String identificacion;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the codigoGenerado
     */
    public String getCodigoGenerado() {
        return codigoGenerado;
    }

    /**
     * @param codigoGenerado the codigoGenerado to set
     */
    public void setCodigoGenerado(String codigoGenerado) {
        this.codigoGenerado = codigoGenerado;
    }

    /**
     * @return the ownerId
     */
    public Integer getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId the ownerId to set
     */
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * @return the fechaCreacion
     */
    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the fechaCaduca
     */
    public Timestamp getFechaCaduca() {
        return fechaCaduca;
    }

    /**
     * @param fechaCaduca the fechaCaduca to set
     */
    public void setFechaCaduca(Timestamp fechaCaduca) {
        this.fechaCaduca = fechaCaduca;
    }

    /**
     * @return the usado
     */
    public boolean isUsado() {
        return usado;
    }

    /**
     * @param usado the usado to set
     */
    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the identificacion
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion the identificacion to set
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

}
