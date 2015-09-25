/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.experimento;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.inject.Inject;
import tesisweb.controller.frontend.LoginManager;
import tesisweb.ejb.experimento.entity.OrdenExposicionMuGrupo;
import tesisweb.util.JSFutil;

/**
 *
 * @author root
 */
@Named(value = "ExperimentoController")
@SessionScoped
public class ExperimentoController implements Serializable {

    @Inject
    LoginManager loginManager;
    private int indexFormActual;
    private Boolean clickPopupPR = Boolean.FALSE;
    private Boolean clickPopupAB = Boolean.FALSE;
    private Boolean clickPopupFB = Boolean.FALSE;

    /**
     * Creates a new instance of ExperimentoController
     */
    public ExperimentoController() {
    }

    public Boolean getClickPopupPR() {
        return clickPopupPR;
    }

    public void setClickPopupPR(Boolean clickPopupPR) {
        this.clickPopupPR = clickPopupPR;
    }

    public Boolean getClickPopupAB() {
        return clickPopupAB;
    }

    public void setClickPopupAB(Boolean clickPopupAB) {
        this.clickPopupAB = clickPopupAB;
    }

    public Boolean getClickPopupFB() {
        return clickPopupFB;
    }

    public void setClickPopupFB(Boolean clickPopupFB) {
        this.clickPopupFB = clickPopupFB;
    }

    public String gotoFirstForm() {
        this.indexFormActual = -1;
        this.loginManager.setContadorPRhabilitado(Boolean.TRUE);
        return this.gotoNextForm();
    }

    public String gotoNextForm() {
        //Se despliega un mensaje si no se abrió el popup en las tareas
        if (!this.clickPopupAB && !this.clickPopupFB && !this.clickPopupPR && this.indexFormActual != -1) {
            JSFutil.addErrorMessage("No puedes continuar con la siguiente tarea sin haber abierto la Tienda y hecho la tarea");
            return "";
        }
        this.initPopup();
        this.indexFormActual++;
        if (indexFormActual < 3) {
            OrdenExposicionMuGrupo orden = JSFutil.getUsuarioConectado().getIdGrupoExperimental().getOrdenExposicionMuGrupoList().get(indexFormActual);
            switch (orden.getIdMu().getIdMu()) {
                case 1: //PREFERENCE
                    return "/experimento/tareaPR?faces-redirect=true";
                case 2: //ABORT OPERATION
                    return "/experimento/tareaAB?faces-redirect=true";
                case 3: //PROGRESS FEEDBACK
                    return "/experimento/tareaFB?faces-redirect=true";
                default:
                    return "";
            }
        } else if (indexFormActual == 3) {
            return "/experimento/cuestionarioFinal?faces-redirect=true";
        } else {
            return "/experimento/agradecimiento";
        }
    }

    public String doGetURLAPP() {
        return JSFutil.getServerUrl() + "/tesisweb/frontend/index.xhtml";
    }

    private void initPopup() {
        this.clickPopupAB = Boolean.FALSE;
        this.clickPopupPR = Boolean.FALSE;
        this.clickPopupFB = Boolean.FALSE;
    }
}
