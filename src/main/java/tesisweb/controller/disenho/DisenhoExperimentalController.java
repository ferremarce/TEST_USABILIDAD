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
import javax.inject.Inject;
import tesisweb.ejb.experimento.entity.DisenhoExperimental;
import tesisweb.ejb.experimento.entity.GrupoMatrizExperimental;
import tesisweb.ejb.experimento.facade.DisenhoExperimentalFacade;
import tesisweb.util.JSFutil;

/**
 *
 * @author root
 */
@Named(value = "DisenhoExperimentalController")
@SessionScoped
public class DisenhoExperimentalController implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(DisenhoExperimentalController.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("tesisweb.properties.bundle", JSFutil.getmyLocale());

    @Inject
    private DisenhoExperimentalFacade disenhoExperimentalFacade;
    private DisenhoExperimental disenhoExperimental;
    private List<DisenhoExperimental> listaDisenhoExperimental;

    /**
     * Creates a new instance of DisenhoExperimental
     */
    public DisenhoExperimentalController() {
    }

    //SETTER Y GETTER
    public DisenhoExperimental getDisenhoExperimental() {
        return disenhoExperimental;
    }

    public void setDisenhoExperimental(DisenhoExperimental disenhoExperimental) {
        this.disenhoExperimental = disenhoExperimental;
    }

    public List<DisenhoExperimental> getListaDisenhoExperimental() {
        return listaDisenhoExperimental;
    }

    public void setListaDisenhoExperimental(List<DisenhoExperimental> listaDisenhoExperimental) {
        this.listaDisenhoExperimental = listaDisenhoExperimental;
    }
    //METODOS DE ACCION

    public String doListar() {
        this.listaDisenhoExperimental = disenhoExperimentalFacade.findAll();
        if (this.listaDisenhoExperimental.size() > 0) {
            JSFutil.addSuccessMessage(this.listaDisenhoExperimental.size() + " registro/s recuperado/s");
        } else {
            JSFutil.addSuccessMessage("Sin registros");
        }
        return "/disenho/ListarDisenho";
    }
    
    //METODOS LISTENER

}
