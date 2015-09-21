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
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Inject;
import tesisweb.ejb.experimento.entity.GrupoMatrizExperimental;
import tesisweb.ejb.experimento.entity.OrdenExposicionMuGrupo;
import tesisweb.ejb.experimento.facade.OrdenExposicionMuGrupoFacade;
import tesisweb.util.JSFutil;

/**
 *
 * @author root
 */
@Named(value = "OrdenExposicionMuGrupoController")
@SessionScoped
public class OrdenExposicionMuGrupoController implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(OrdenExposicionMuGrupoController.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("tesisweb.properties.bundle", JSFutil.getmyLocale());

    @Inject
    private OrdenExposicionMuGrupoFacade ordenExposicionMuGrupoFacade;
    @Inject
    GrupoMatrizExperimentalController grupoMatrizExperimentalController;
    private OrdenExposicionMuGrupo ordenExposicionMuGrupo;
    private List<OrdenExposicionMuGrupo> listaOrdenExposicionMuGrupo;

    /**
     * Creates a new instance of OrdenExposicionMuGrupo
     */
    public OrdenExposicionMuGrupoController() {
        this.ordenExposicionMuGrupo = new OrdenExposicionMuGrupo();
    }

    public OrdenExposicionMuGrupo getOrdenExposicionMuGrupo() {
        return ordenExposicionMuGrupo;
    }

    //SETTER Y GETTER
    public void setOrdenExposicionMuGrupo(OrdenExposicionMuGrupo ordenExposicionMuGrupo) {
        this.ordenExposicionMuGrupo = ordenExposicionMuGrupo;
    }

    public List<OrdenExposicionMuGrupo> getListaOrdenExposicionMuGrupo() {
        return listaOrdenExposicionMuGrupo;
    }

    public void setListaOrdenExposicionMuGrupo(List<OrdenExposicionMuGrupo> listaOrdenExposicionMuGrupo) {
        this.listaOrdenExposicionMuGrupo = listaOrdenExposicionMuGrupo;
    }
    //METODOS DE ACCION

    public String doListar() {
        this.listaOrdenExposicionMuGrupo = ordenExposicionMuGrupoFacade.findAll();
        if (this.listaOrdenExposicionMuGrupo.size() > 0) {
            JSFutil.addSuccessMessage(this.listaOrdenExposicionMuGrupo.size() + " registro/s recuperado/s");
        } else {
            JSFutil.addSuccessMessage("Sin registros");
        }
        return "/disenho/ListarDisenho";
    }

    public void doGuardar() {
        try {
            ordenExposicionMuGrupoFacade.create(ordenExposicionMuGrupo);
            grupoMatrizExperimentalController.doListar();
            JSFutil.addSuccessMessage("Orden agregado exitosamente");
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

    public void doBorrar(OrdenExposicionMuGrupo o) {
        try {
            ordenExposicionMuGrupoFacade.remove(o);
            this.grupoMatrizExperimentalController.doListar();
            JSFutil.addSuccessMessage("Orden eliminado exitosamente");
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

    //METODOS LISTENER
    public void doCrearFormDialog(GrupoMatrizExperimental g) {
        this.ordenExposicionMuGrupo = new OrdenExposicionMuGrupo();
        this.ordenExposicionMuGrupo.setIdGrupo(g);
    }
}
