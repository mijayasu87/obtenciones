/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.model.discount;

/**
 *
 * @author michael
 */

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import senadi.gob.ec.ov.util.Operations;

/**
 *
 * @author micharesp
 */
@Entity
@Table(name="descuento")
public class Descuento implements Serializable{
    
    @Id
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "numero")
    private String numero;
    
    @Column(name = "mes_elaboracion")
    private String mesElaboracion;
    
    @Column(name = "ruc")
    private String ruc;
    
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    
    @Column(name = "fecha_recepcion_documento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaRecepcionDocumento;
    
    @Column(name = "fecha_caducidad")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCaducidad;
    
    @Column(name = "estatus_documentos")
    private String estatusDocumentos;
    
    @Column(name = "tipo_documento")
    private String tipoDocumento;
    
    @Column(name = "causaL_descuento")
    private String causalDescuento;
    
    @Column(name = "f_eov")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date Feov;
    
    @Column(name = "ciudad")
    private String ciudad;
    
    @Column(name = "usuario_elabora")
    private String usuarioElabora;
    
    @Column(name = "fecha_creacion")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    
    @Column(name = "estado")
    private boolean estado;
        
    @Column(name = "realizado")
    private boolean realizado;
    
    @Column(name = "ruta")
    private String ruta;
    
    @Column(name = "system_user")
    private String sysUser;
    
    @Column(name = "casillero")
    private Integer casillero;
    
    @Transient
    private String fechaCaducidadText;
    
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
     * @return the mesElaboracion
     */
    public String getMesElaboracion() {
        return mesElaboracion;
    }

    /**
     * @param mesElaboracion the mesElaboracion to set
     */
    public void setMesElaboracion(String mesElaboracion) {
        this.mesElaboracion = mesElaboracion;
    }

    /**
     * @return the ruc
     */
    public String getRuc() {
        return ruc;
    }

    /**
     * @param ruc the ruc to set
     */
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return the fechaRecepcionDocumento
     */
    public Date getFechaRecepcionDocumento() {
        return fechaRecepcionDocumento;
    }

    /**
     * @param fechaRecepcionDocumento the fechaRecepcionDocumento to set
     */
    public void setFechaRecepcionDocumento(Date fechaRecepcionDocumento) {
        this.fechaRecepcionDocumento = fechaRecepcionDocumento;
    }

    /**
     * @return the fechaCaducidad
     */
    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    /**
     * @param fechaCaducidad the fechaCaducidad to set
     */
    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    /**
     * @return the estatusDocumentos
     */
    public String getEstatusDocumentos() {
        return estatusDocumentos;
    }

    /**
     * @param estatusDocumentos the estatusDocumentos to set
     */
    public void setEstatusDocumentos(String estatusDocumentos) {
        this.estatusDocumentos = estatusDocumentos;
    }

    /**
     * @return the tipoDocumento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * @return the causalDescuento
     */
    public String getCausalDescuento() {
        return causalDescuento;
    }

    /**
     * @param causalDescuento the causalDescuento to set
     */
    public void setCausalDescuento(String causalDescuento) {
        this.causalDescuento = causalDescuento;
    }

    /**
     * @return the Feov
     */
    public Date getFeov() {
        return Feov;
    }

    /**
     * @param Feov the Feov to set
     */
    public void setFeov(Date Feov) {
        this.Feov = Feov;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the usuarioElabora
     */
    public String getUsuarioElabora() {
        return usuarioElabora;
    }

    /**
     * @param usuarioElabora the usuarioElabora to set
     */
    public void setUsuarioElabora(String usuarioElabora) {
        this.usuarioElabora = usuarioElabora;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the estado
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * @return the realizado
     */
    public boolean isRealizado() {
        return realizado;
    }

    /**
     * @param realizado the realizado to set
     */
    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    /**
     * @return the ruta
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    /**
     * @return the sysUser
     */
    public String getSysUser() {
        return sysUser;
    }

    /**
     * @param sysUser the sysUser to set
     */
    public void setSysUser(String sysUser) {
        this.sysUser = sysUser;
    }

    /**
     * @return the casillero
     */
    public Integer getCasillero() {
        return casillero;
    }

    /**
     * @param casillero the casillero to set
     */
    public void setCasillero(Integer casillero) {
        this.casillero = casillero;
    }

    /**
     * @return the fechaCaducidadText
     */
    public String getFechaCaducidadText() {
        return Operations.formatDateToLarge(fechaCaducidad);
    }

    /**
     * @param fechaCaducidadText the fechaCaducidadText to set
     */
    public void setFechaCaducidadText(String fechaCaducidadText) {
        this.fechaCaducidadText = fechaCaducidadText;
    }
}

