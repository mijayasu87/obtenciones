package senadi.gob.ec.ov.bean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import senadi.gob.ec.ov.bean.solicitudes.Owners;
import senadi.gob.ec.ov.util.Controller;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = -2152389656664659476L;
    private String documento;
    private String clave;
    private boolean logeado = false;

//    private Usuario usuario;
    private boolean shake;

    private boolean various;

    private boolean lectura;
    private String grupoActivo;
            
    private Owners owner;
    
    private Integer idVegetableForms;

    public LoginBean() {
        shake = true;
    }

    public void login(ActionEvent actionEvent) {
        FacesMessage msg = null;
        Controller c = new Controller();
        owner = c.getOwnersByLogin(documento, clave);
        if (owner.getId() != null && owner.getId() > 0) {
            shake = false;
            logeado = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", owner.getFirsName() + " " + owner.getLastName());
            PrimeFaces.current().ajax().addCallbackParam("estaLogeado", logeado);
            PrimeFaces.current().ajax().addCallbackParam("view", "breederform.xhtml");
        } else {
            shake = true;
            logeado = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Credenciales inválidas");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean estaLogeado() {
        return logeado;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }   

    public void logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        logeado = false;
        shake = false;
    }

    /**
     * @return the shake
     */
    public boolean isShake() {
        return shake;
    }

    /**
     * @param shake the shake to set
     */
    public void setShake(boolean shake) {
        this.shake = shake;
    }

    /**
     * @return the lectura
     */
    public boolean isLectura() {
        return lectura;
    }

    /**
     * @param lectura the lectura to set
     */
    public void setLectura(boolean lectura) {
        this.lectura = lectura;
    }

    /**
     * @return the various
     */
    public boolean isVarious() {
        return various;
    }

    /**
     * @param various the various to set
     */
    public void setVarious(boolean various) {
        this.various = various;
    }

    /**
     * @return the grupoActivo
     */
    public String getGrupoActivo() {
        return grupoActivo;
    }

    /**
     * @param grupoActivo the grupoActivo to set
     */
    public void setGrupoActivo(String grupoActivo) {
        this.grupoActivo = grupoActivo;
    }

    /**
     * @return the owner
     */
    public Owners getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(Owners owner) {
        this.owner = owner;
    }

    /**
     * @return the idVegetableForms
     */
    public Integer getIdVegetableForms() {
        return idVegetableForms;
    }

    /**
     * @param idVegetableForms the idVegetableForms to set
     */
    public void setIdVegetableForms(Integer idVegetableForms) {
        this.idVegetableForms = idVegetableForms;
    }
    
}
