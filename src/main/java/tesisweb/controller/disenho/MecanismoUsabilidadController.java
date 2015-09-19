/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.disenho;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import tesisweb.ejb.experimento.facade.MecanismoUsabilidadFacade;
import tesisweb.util.JSFutil;

/**
 *
 * @author root
 */
@Named(value = "MecanismoUsabilidadController")
@SessionScoped
public class MecanismoUsabilidadController implements Serializable {

    @Inject
    MecanismoUsabilidadFacade mecanismoUsabilidadFacade;

    /**
     * Creates a new instance of MecanismoUsabilidaController
     */
    public MecanismoUsabilidadController() {
    }

    public MecanismoUsabilidadFacade getMecanismoUsabilidadFacade() {
        return mecanismoUsabilidadFacade;
    }

    public void setMecanismoUsabilidadFacade(MecanismoUsabilidadFacade mecanismoUsabilidadFacade) {
        this.mecanismoUsabilidadFacade = mecanismoUsabilidadFacade;
    }
 public SelectItem[] getMecanismoUsabilidadSet() {
        return JSFutil.getSelectItems(mecanismoUsabilidadFacade.findAll(), Boolean.TRUE);
    }
}
