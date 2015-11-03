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
import org.primefaces.context.RequestContext;
import tesisweb.controller.frontend.CarritoFE;
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
    @Inject
    CarritoFE carritoFE;
    private int indexFormActual;
    private Boolean clickPopupPR = Boolean.FALSE;
    private Boolean clickPopupAB = Boolean.FALSE;
    private Boolean clickPopupFB = Boolean.FALSE;
    private Boolean clickPopupFicticiaPR = Boolean.FALSE;
    private Boolean debugMode = Boolean.FALSE;
    private Metrica metrica;
    private Metrica metricaGlobal;

    /**
     * Creates a new instance of ExperimentoController
     */
    public ExperimentoController() {
    }

    public int getIndexFormActual() {
        return indexFormActual;
    }

    public void setIndexFormActual(int indexFormActual) {
        this.indexFormActual = indexFormActual;
    }

    public Boolean getClickPopupFicticiaPR() {
        return clickPopupFicticiaPR;
    }

    public void setClickPopupFicticiaPR(Boolean clickPopupFicticiaPR) {
        this.clickPopupFicticiaPR = clickPopupFicticiaPR;
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
        if (!this.clickPopupFicticiaPR && !this.clickPopupAB && !this.clickPopupFB && !this.clickPopupPR && this.indexFormActual != -1) {
            JSFutil.addErrorMessage("No puedes continuar con la siguiente tarea sin haber abierto la Tienda y haber realizado la tarea que aquí se propone");
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
            this.stopMetricaGlobal();
            return "/experimento/cuestionarioFinal?faces-redirect=true";
        } else {
            return "/experimento/agradecimiento";
        }
    }

    public String gotoTareaFicticiaPRForm() {
        if (!this.clickPopupPR) {
            JSFutil.addErrorMessage("No puedes continuar con la siguiente tarea sin haber abierto la Tienda y haber realizado la tarea que aquí se propone");
            return "";
        }
        this.initPopup();
        this.stopMetrica();
        return "/experimento/tareaFicticaPR?faces-redirect=true";
    }

    public String gotoCuestionarioPRForm() {
        if (!this.clickPopupFicticiaPR) {
            JSFutil.addErrorMessage("No puedes continuar con la siguiente tarea sin haber abierto la Tienda y haber realizado la tarea que aquí se propone");
            return "";
        }
        //this.initPopup();
        this.stopMetrica();
        return "/experimento/cuestionarioPR?faces-redirect=true";
    }

    public String gotoCuestionarioABForm() {
        if (!this.clickPopupAB) {
            JSFutil.addErrorMessage("No puedes continuar con la siguiente tarea sin haber abierto la Tienda y haber realizado la tarea que aquí se propone");
            return "";
        }
        //this.initPopup();
        this.stopMetrica();
        return "/experimento/cuestionarioAB?faces-redirect=true";
    }

    public String gotoCuestionarioFBForm() {
        if (!this.clickPopupFB) {
            JSFutil.addErrorMessage("No puedes continuar con la siguiente tarea sin haber abierto la Tienda y haber realizado la tarea que aquí se propone");
            return "";
        }
        //this.initPopup();
        this.stopMetrica();
        return "/experimento/cuestionarioFB?faces-redirect=true";
    }

    public String doGetURLAPP() {
        return JSFutil.getServerUrl() + "/tesisweb/frontend/index.xhtml";
    }

    private void initPopup() {
        this.clickPopupAB = Boolean.FALSE;
        this.clickPopupPR = Boolean.FALSE;
        this.clickPopupFB = Boolean.FALSE;
        this.clickPopupFicticiaPR = Boolean.FALSE;
    }

    public void doActivatePR() {
        this.setClickPopupPR(Boolean.TRUE);
        this.carritoFE.doCerarCarrito();
        this.loginManager.setUsarPreferenciaUsuario(Boolean.TRUE);
        //Inicializar el contador de la ventana emergente
        this.loginManager.setContadorPR(0);
        this.startMetrica();
        this.abrirURLTienda();
    }

    private void abrirURLTienda() {
        RequestContext context = RequestContext.getCurrentInstance();
        String direccion = "openWin('" + this.doGetURLAPP() + "')";
        context.execute(direccion);
    }

    public void doActivateFicticiaPR() {
        this.setClickPopupFicticiaPR(Boolean.TRUE);
        this.carritoFE.doCerarCarrito();
        this.loginManager.setUsarPreferenciaUsuario(Boolean.TRUE);
        this.startMetrica();
        this.abrirURLTienda();
    }

    public void doActivateAB() {
        this.setClickPopupAB(Boolean.TRUE);
        this.carritoFE.doCerarCarrito();
        this.carritoFE.doAgregarCarritoInit();
        this.loginManager.setUsarPreferenciaUsuario(Boolean.FALSE);
        this.startMetrica();
        this.abrirURLTienda();
    }

    public void doActivateFB() {
        this.setClickPopupFB(Boolean.TRUE);
        this.carritoFE.doCerarCarrito();
        this.loginManager.setUsarPreferenciaUsuario(Boolean.FALSE);
        this.startMetrica();
        this.abrirURLTienda();
    }

    private void startMetrica() {
        Integer id;
        String info = "TAREA NORMAL";
        if (this.metrica != null && this.metrica.getStartTime() > 0) {
            System.out.print("ya se ha iniciado la metrica...");
        } else {
            this.metrica = new Metrica();
            if (this.clickPopupPR) {
                id = 1;
            } else if (this.clickPopupAB) {
                id = 2;
            } else if (this.clickPopupFB) {
                id = 3;
            } else if (this.clickPopupFicticiaPR) {
                id = 1;
                info = "TAREA FICTICIA";
            } else {
                JSFutil.addErrorMessage("No se puede establecer el mecanismo para capturar las metricas");
                return;
            }
            this.metrica.setIdMecanismoUsabilidad(mecanismoUsabilidadFacade.find(id));
            this.metrica.setStartTime(System.currentTimeMillis());
            this.metricaController.setTmpClickCounter(0);
            this.metrica.setIdUsuario(JSFutil.getUsuarioConectado());
            this.metrica.setInformacion(info);
            System.out.print("Start de métrica...");
        }
    }

    public void startMetricaGlobal() {
        if (this.metricaGlobal == null) {
            this.metricaGlobal = new Metrica();
            this.metricaGlobal.setStartTime(System.currentTimeMillis());
            this.metricaGlobal.setIdUsuario(JSFutil.getUsuarioConectado());
            this.metricaGlobal.setClickCounter(0);
            this.metricaGlobal.setInformacion("TAREA TOTAL");
            System.out.print("Start de métrica Global...");
        } else {
            System.out.print("Métrica Global inicializada...");
        }
    }

    private void stopMetricaGlobal() {
        if (this.metricaGlobal != null) {
            this.metricaGlobal.setStopTime(System.currentTimeMillis());
            try {
                this.metricaFacade.create(metricaGlobal);
                this.metricaGlobal = null;
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
            System.out.print("Stop de métrica Global...");
        } else {
            System.out.print("StopMetrica... Métrica Global no inicializada...");
        }
    }

    private void stopMetrica() {
        if (this.metrica != null) {
            this.metrica.setStopTime(System.currentTimeMillis());
            this.metrica.setClickCounter(this.metricaController.getTmpClickCounter());
            try {
                this.metricaFacade.create(metrica);
                this.metricaController.setTmpClickCounter(0);
                this.metrica = null;
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
