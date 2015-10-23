/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.disenho;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Inject;
import tesisweb.ejb.experimento.entity.PreguntaUsabilidad;
import tesisweb.ejb.experimento.facade.PreguntaUsabilidadFacade;
import tesisweb.util.JSFutil;
import tesisweb.util.JSFutil.PersistAction;

/**
 *
 * @author root
 */
@Named(value = "PreguntaUsabilidadController")
@SessionScoped
public class PreguntaUsabilidadController implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(UsuarioController.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("tesisweb.properties.bundle", JSFutil.getmyLocale());

    @Inject
    PreguntaUsabilidadFacade preguntaUsabilidadFacade;

    private List<PreguntaUsabilidad> listaPreguntaUsabilidad;
    private PreguntaUsabilidad preguntaUsabilidad;

    /**
     * Creates a new instance of PreguntaUsabilidadController
     */
    public PreguntaUsabilidadController() {
    }
//SETTER Y GETTER

    public List<PreguntaUsabilidad> getListaPreguntaUsabilidad() {
        return listaPreguntaUsabilidad;
    }

    public void setListaPreguntaUsabilidad(List<PreguntaUsabilidad> listaPreguntaUsabilidad) {
        this.listaPreguntaUsabilidad = listaPreguntaUsabilidad;
    }

    public PreguntaUsabilidad getPreguntaUsabilidad() {
        return preguntaUsabilidad;
    }

    public void setPreguntaUsabilidad(PreguntaUsabilidad preguntaUsabilidad) {
        this.preguntaUsabilidad = preguntaUsabilidad;
    }

    //METODOS DE ACCION
    public String doListarForm() {
        this.listaPreguntaUsabilidad = this.preguntaUsabilidadFacade.findAllbyCriterio("%");
        return "/disenho/ListarPreguntaUsabilidad";
    }

    public String doCrearForm() {
        this.preguntaUsabilidad = new PreguntaUsabilidad();
        return "/disenho/CrearPreguntaUsabilidad";
    }

    public String doEditarForm(PreguntaUsabilidad u) {
        this.preguntaUsabilidad = u;
        return "/disenho/CrearPreguntaUsabilidad";
    }

    public void doBorrar(PreguntaUsabilidad u) {
        try {
            preguntaUsabilidadFacade.remove(u);
            JSFutil.addSuccessMessage(JSFutil.getMyBundle().getString("UpdateSuccess"));
        } catch (EJBException ex) {
            String msg = "";
            Throwable cause = ex.getCause();
            if (cause != null) {
                msg = cause.getLocalizedMessage();
            }
            if (msg.length() > 0) {
                JSFutil.addErrorMessage(msg);
            } else {
                JSFutil.addErrorMessage(ex, JSFutil.getMyBundle().getString("UpdateError"));
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            JSFutil.addErrorMessage(ex, JSFutil.getMyBundle().getString("UpdateError"));
        }
        doListarForm();
    }

    public String doGuardar() {
        if (this.preguntaUsabilidad.getIdPreguntaUsabilidad() != null) {
            persist(PersistAction.UPDATE);
            return this.doListarForm();
        } else {
            persist(PersistAction.CREATE);
            return this.doListarForm();
        }
    }

    private void persist(PersistAction persistAction) {
        try {
            if (persistAction.compareTo(PersistAction.CREATE) == 0) {
                preguntaUsabilidadFacade.create(this.preguntaUsabilidad);
            } else if (persistAction.compareTo(PersistAction.UPDATE) == 0) {
                preguntaUsabilidadFacade.edit(this.preguntaUsabilidad);
            } else if (persistAction.compareTo(PersistAction.DELETE) == 0) {
                preguntaUsabilidadFacade.remove(this.preguntaUsabilidad);
            }
            JSFutil.addSuccessMessage(JSFutil.getMyBundle().getString("UpdateSuccess"));
        } catch (EJBException ex) {
            String msg = "";
            Throwable cause = ex.getCause();
            if (cause != null) {
                msg = cause.getLocalizedMessage();
            }
            if (msg.length() > 0) {
                JSFutil.addErrorMessage(msg);
            } else {
                JSFutil.addErrorMessage(ex, JSFutil.getMyBundle().getString("UpdateError"));
            }
        }
    }

    public String doHtmlToText(String html) {
        return JSFutil.html2text(html);
    }
}
