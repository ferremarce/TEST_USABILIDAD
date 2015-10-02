/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.experimento;

import tesisweb.clase.*;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import tesisweb.ejb.experimento.entity.MecanismoUsabilidad;
import tesisweb.ejb.tienda.entity.Usuario;

/**
 *
 * @author root
 */
@Named(value = "MetricaController")
@SessionScoped
public class MetricaController implements Serializable {

    private Metrica metrica;
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
    //METODOS DE ACCIÓN

    public void increment() {
        tmpClickCounter++;
    }

}
