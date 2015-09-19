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
import tesisweb.ejb.experimento.entity.GrupoMatrizExperimental;
import tesisweb.ejb.experimento.facade.GrupoMatrizExperimentalFacade;
import tesisweb.util.JSFutil;

/**
 *
 * @author root
 */
@Named(value = "GrupoMatrizExperimentalController")
@SessionScoped
public class GrupoMatrizExperimentalController implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(GrupoMatrizExperimentalController.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("tesisweb.properties.bundle", JSFutil.getmyLocale());

    @Inject
    private GrupoMatrizExperimentalFacade grupoMatrizExperimentalFacade;
    private GrupoMatrizExperimental grupoMatrizExperimental;
    private List<GrupoMatrizExperimental> listaGrupoMatrizExperimental;

    /**
     * Creates a new instance of GrupoMatrizExperimental
     */
    public GrupoMatrizExperimentalController() {
    }

    //SETTER Y GETTER
    public GrupoMatrizExperimental getGrupoMatrizExperimental() {
        return grupoMatrizExperimental;
    }

    public void setGrupoMatrizExperimental(GrupoMatrizExperimental grupoMatrizExperimental) {
        this.grupoMatrizExperimental = grupoMatrizExperimental;
    }

    public List<GrupoMatrizExperimental> getListaGrupoMatrizExperimental() {
        return listaGrupoMatrizExperimental;
    }

    public void setListaGrupoMatrizExperimental(List<GrupoMatrizExperimental> listaGrupoMatrizExperimental) {
        this.listaGrupoMatrizExperimental = listaGrupoMatrizExperimental;
    }
    //METODOS DE ACCION

    public String doListar() {
        this.listaGrupoMatrizExperimental = grupoMatrizExperimentalFacade.findAll();
        if (this.listaGrupoMatrizExperimental.size() > 0) {
            JSFutil.addSuccessMessage(this.listaGrupoMatrizExperimental.size() + " registro/s recuperado/s");
        } else {
            JSFutil.addSuccessMessage("Sin registros");
        }
        return "/disenho/ListarDisenho";
    }
    
    //METODOS LISTENER

}
