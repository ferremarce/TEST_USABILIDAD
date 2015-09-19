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
    private OrdenExposicionMuGrupoFacade OrdenExposicionMuGrupoFacade;
    private OrdenExposicionMuGrupo OrdenExposicionMuGrupo;
    private List<OrdenExposicionMuGrupo> listaOrdenExposicionMuGrupo;

    /**
     * Creates a new instance of OrdenExposicionMuGrupo
     */
    public OrdenExposicionMuGrupoController() {
    }

    //SETTER Y GETTER
    public OrdenExposicionMuGrupo getOrdenExposicionMuGrupo() {
        return OrdenExposicionMuGrupo;
    }

    public void setOrdenExposicionMuGrupo(OrdenExposicionMuGrupo OrdenExposicionMuGrupo) {
        this.OrdenExposicionMuGrupo = OrdenExposicionMuGrupo;
    }

    public List<OrdenExposicionMuGrupo> getListaOrdenExposicionMuGrupo() {
        return listaOrdenExposicionMuGrupo;
    }

    public void setListaOrdenExposicionMuGrupo(List<OrdenExposicionMuGrupo> listaOrdenExposicionMuGrupo) {
        this.listaOrdenExposicionMuGrupo = listaOrdenExposicionMuGrupo;
    }
    //METODOS DE ACCION

    public String doListar() {
        this.listaOrdenExposicionMuGrupo = OrdenExposicionMuGrupoFacade.findAll();
        if (this.listaOrdenExposicionMuGrupo.size() > 0) {
            JSFutil.addSuccessMessage(this.listaOrdenExposicionMuGrupo.size() + " registro/s recuperado/s");
        } else {
            JSFutil.addSuccessMessage("Sin registros");
        }
        return "/disenho/ListarDisenho";
    }

    //METODOS LISTENER
    public void doCrearFormDialog() {
        this.OrdenExposicionMuGrupo = new OrdenExposicionMuGrupo();
    }
}
