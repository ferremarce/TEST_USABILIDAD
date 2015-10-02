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
    private Integer tmpClickCounter;
    private long tmpStartTime;
    private long tmpStopTime;

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

    public long getTmpStartTime() {
        return tmpStartTime;
    }

    public void setTmpStartTime(long tmpStartTime) {
        this.tmpStartTime = tmpStartTime;
    }

    public long getTmpStopTime() {
        return tmpStopTime;
    }

    public void setTmpStopTime(long tmpStopTime) {
        this.tmpStopTime = tmpStopTime;
    }

    public List<Metrica> getListaMetrica() {
        return listaMetrica;
    }

    public void setListaMetrica(List<Metrica> listaMetrica) {
        this.listaMetrica = listaMetrica;
    }

    //METODOS DE ACCIÓN
    public void increment() {
        tmpClickCounter++;
    }

    public List<Metrica> doGetListaMetrica() {
        if (JSFutil.getUsuarioConectado() != null) {
            return metricaFacade.findAllbyUsuario(JSFutil.getUsuarioConectado().getIdUsuario());
        } else {
            return new ArrayList<>();
        }
    }
}
