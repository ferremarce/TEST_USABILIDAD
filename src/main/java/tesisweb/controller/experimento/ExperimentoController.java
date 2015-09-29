/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.experimento;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import tesisweb.clase.Metrica;
import tesisweb.controller.frontend.LoginManager;
import tesisweb.ejb.experimento.entity.MecanismoUsabilidad;
import tesisweb.ejb.experimento.entity.OrdenExposicionMuGrupo;
import tesisweb.ejb.experimento.facade.MecanismoUsabilidadFacade;
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
    @Inject
    MecanismoUsabilidadFacade mecanismoUsabilidadFacade;
    private int indexFormActual;
    private Boolean clickPopupPR = Boolean.FALSE;
    private Boolean clickPopupAB = Boolean.FALSE;
    private Boolean clickPopupFB = Boolean.FALSE;
    long time_start, time_end;
    private Integer clickCounter;
    private MecanismoUsabilidad mecanismoUsabilidad;
    private List<Metrica> listaMetrica = new ArrayList<>();

    /**
     * Creates a new instance of ExperimentoController
     */
    public ExperimentoController() {
    }

    public List<Metrica> getListaMetrica() {
        return listaMetrica;
    }

    public void setListaMetrica(List<Metrica> listaMetrica) {
        this.listaMetrica = listaMetrica;
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

    public void startMetrica() {
        Integer id = null;
        if (this.clickPopupPR) {
            id = 1;
        } else if (this.clickPopupAB) {
            id = 2;
        } else if (this.clickPopupFB) {
            id = 3;
        } else {
            JSFutil.addErrorMessage("No se puede establecer el mecanismo para capturar las metricas");
            return;
        }
        this.mecanismoUsabilidad = mecanismoUsabilidadFacade.find(id);
        this.time_start = System.currentTimeMillis();
        this.clickCounter = 0;
    }

    public void stopMetrica() {
        Metrica m = new Metrica();
        m.setIdMecanismoUsabilidad(mecanismoUsabilidad);
        m.setIdUsuario(JSFutil.getUsuarioConectado());
        m.setTiempoInicio(this.time_start);
        m.setTiempoFin(System.currentTimeMillis());
        this.listaMetrica.add(m);
    }
}
