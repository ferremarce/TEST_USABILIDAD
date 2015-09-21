/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.experimento;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import tesisweb.ejb.experimento.entity.OrdenExposicionMuGrupo;
import tesisweb.util.JSFutil;

/**
 *
 * @author root
 */
@Named(value = "ExperimentoController")
@SessionScoped
public class ExperimentoController implements Serializable {

    private int indexFormActual;

    /**
     * Creates a new instance of ExperimentoController
     */
    public ExperimentoController() {
    }

    public String gotoFirstForm() {
        this.indexFormActual = -1;
        return this.gotoNextForm();
    }

    public String gotoNextForm() {
        this.indexFormActual++;
        if (indexFormActual < 3) {
            OrdenExposicionMuGrupo orden = JSFutil.getUsuarioConectado().getIdGrupoExperimental().getOrdenExposicionMuGrupoList().get(indexFormActual);
            switch (orden.getIdMu().getIdMu()) {
                case 1: //PREFERENCE
                    return "/experimento/tareaPR";
                case 2: //ABORT OPERATION
                    return "/experimento/tareaAB";
                case 3: //PROGRESS FEEDBACK
                    return "/experimento/tareaFB";
                default:
                    return "";
            }
        } else if (indexFormActual == 3) {
            return "/experimento/cuestionarioFinal";
        } else {
            return "/experimento/agradecimiento";
        }
    }
}
