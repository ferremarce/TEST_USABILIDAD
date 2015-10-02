/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.experimento;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJBException;
import javax.inject.Inject;
import tesisweb.controller.frontend.LoginManager;
import tesisweb.ejb.experimento.entity.Metrica;
import tesisweb.ejb.experimento.entity.OrdenExposicionMuGrupo;
import tesisweb.ejb.experimento.facade.MecanismoUsabilidadFacade;
import tesisweb.ejb.experimento.facade.MetricaFacade;
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
    @Inject
    MetricaController metricaController;
    @Inject
    MetricaFacade metricaFacade;
    private int indexFormActual;
    private Boolean clickPopupPR = Boolean.FALSE;
    private Boolean clickPopupAB = Boolean.FALSE;
    private Boolean clickPopupFB = Boolean.FALSE;
    private Boolean debugMode = Boolean.TRUE;
    private Metrica metrica;

    /**
     * Creates a new instance of ExperimentoController
     */
    public ExperimentoController() {
    }

    public Boolean getDebugMode() {
        return debugMode;
    }

    public void setDebugMode(Boolean debugMode) {
        this.debugMode = debugMode;
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
            this.stopMetrica(); //Se detiene la métrica cuando se pasa al siguiente formulario de evaluacion de MU
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
            this.stopMetrica(); //Se detiene la métrica cuando se pasa al siguiente formulario de evaluacion de MU

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

    public void doActivatePR() {
        this.setClickPopupPR(Boolean.TRUE);
        this.startMetrica();
    }

    public void doActivateAB() {
        this.setClickPopupAB(Boolean.TRUE);
        this.startMetrica();
    }

    public void doActivateFB() {
        this.setClickPopupFB(Boolean.TRUE);
        this.startMetrica();
    }

    private void startMetrica() {
        Integer id;
        this.metrica = new Metrica();
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
        this.metrica.setIdMecanismoUsabilidad(mecanismoUsabilidadFacade.find(id));
        this.metrica.setStartTime(System.currentTimeMillis());
        this.metricaController.setTmpClickCounter(0);
        this.metrica.setIdUsuario(JSFutil.getUsuarioConectado());
        System.out.print("Start de métrica...");
    }

    private void stopMetrica() {
        if (this.metrica != null) {
            this.metrica.setStopTime(System.currentTimeMillis());
            this.metrica.setClickCounter(this.metricaController.getTmpClickCounter());
            try {
                this.metricaFacade.create(metrica);
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
            System.out.print("Stop de métrica...");
        } else {
            System.out.print("StopMetrica... Métrica no inicializada...");
        }
    }
}
