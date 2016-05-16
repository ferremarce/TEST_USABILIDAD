/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.experimento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import tesisweb.ejb.experimento.entity.DisenhoExperimental;
import tesisweb.ejb.experimento.entity.MecanismoUsabilidad;
import tesisweb.ejb.experimento.entity.Metrica;
import tesisweb.ejb.experimento.facade.MetricaFacade;
import tesisweb.util.JSFutil;

/**
 *
 * @author root
 */
@Named(value = "MetricaController")
@SessionScoped
public class MetricaController implements Serializable {

    @Inject
    MetricaFacade metricaFacade;
    private Metrica metrica;
    private List<Metrica> listaMetrica;
    private Integer tmpClickCounter = 0;
    private DisenhoExperimental disenhoExperimental;
    private MecanismoUsabilidad idMu;

    public MetricaController() {
    }
    //SETTER AND GETTER

    public Metrica getMetrica() {
        return metrica;
    }

    public void setMetrica(Metrica metrica) {
        this.metrica = metrica;
    }

    public Integer getTmpClickCounter() {
        return tmpClickCounter;
    }

    public void setTmpClickCounter(Integer tmpClickCounter) {
        this.tmpClickCounter = tmpClickCounter;
    }

    public List<Metrica> getListaMetrica() {
        return listaMetrica;
    }

    public void setListaMetrica(List<Metrica> listaMetrica) {
        this.listaMetrica = listaMetrica;
    }

    public DisenhoExperimental getDisenhoExperimental() {
        return disenhoExperimental;
    }

    public void setDisenhoExperimental(DisenhoExperimental disenhoExperimental) {
        this.disenhoExperimental = disenhoExperimental;
    }

    public MecanismoUsabilidad getIdMu() {
        return idMu;
    }

    public void setIdMu(MecanismoUsabilidad idMu) {
        this.idMu = idMu;
    }

    //METODOS DE ACCIÓN
    public void increment() {
        tmpClickCounter++;
        System.out.println("Click counter: " + tmpClickCounter);

    }

    public List<Metrica> doGetListaMetrica() {
        if (JSFutil.getUsuarioConectado() != null) {
            return metricaFacade.findAllbyUsuario(JSFutil.getUsuarioConectado().getIdUsuario());
        } else {
            return new ArrayList<>();
        }
    }

    public String doListMetricaDisenho(DisenhoExperimental de) {
        this.disenhoExperimental = de;
        this.idMu = null;
        this.listaMetrica = new ArrayList<>();
        return "/disenho/VerMetricas";
    }

    public void doUpdateListaMetricaDiseǹoMecanismo() {
        this.listaMetrica = this.doListaMetricaDisenhoMecanismo();
    }

    public List<Metrica> doListaMetricaDisenhoMecanismo() {
        if (this.disenhoExperimental != null && this.idMu != null) {
            return this.metricaFacade.findAllbyDisenhoMecanismo(this.disenhoExperimental.getIdDisenho(), this.idMu.getIdMu());
        } else if (this.disenhoExperimental != null && this.idMu == null) {
            return this.metricaFacade.findAllbyDisenho(this.disenhoExperimental.getIdDisenho());
        } else {
            return null;
        }
    }

    public Integer doGetValorRespuestaFinalizacion(Metrica m) {
        Integer valorRespuesta;
        Integer idPreg = 0;
        if (m.getIdMecanismoUsabilidad() != null) {
            if (m.getIdMecanismoUsabilidad().getIdMu().compareTo(1) == 0) { //PREFERENCE
                if (m.getInformacion().compareTo("TAREA NORMAL") == 0) {
                    idPreg = 1;
                } else {
                    idPreg = 2;
                }
            } else if (m.getIdMecanismoUsabilidad().getIdMu().compareTo(2) == 0) { //ABORT OPERATION
                idPreg = 5;
            } else if (m.getIdMecanismoUsabilidad().getIdMu().compareTo(3) == 0) { //PROGRESS FEEDBACK
                idPreg = 8;
            }
        }
        valorRespuesta = metricaFacade.getValorRespuestaCuestionario(m.getIdUsuario().getIdUsuario(), idPreg);
        return valorRespuesta;
    }
}
