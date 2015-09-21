/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.disenho;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.inject.Inject;
import tesisweb.ejb.experimento.entity.CuestionarioUsabilidad;
import tesisweb.ejb.experimento.entity.PreguntaUsabilidad;
import tesisweb.ejb.experimento.facade.CuestionarioUsabilidadFacade;
import tesisweb.util.JSFutil;

/**
 *
 * @author root
 */
@Named(value = "CuestionarioUsabilidadController")
@SessionScoped
public class CuestionarioUsabilidadController implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(CuestionarioUsabilidadController.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("tesisweb.properties.bundle", JSFutil.getmyLocale());

    @Inject
    CuestionarioUsabilidadFacade cuestionarioUsabilidadFacade;
    private CuestionarioUsabilidad cuestionarioUsabilidad;
    private List<CuestionarioUsabilidad> listaCuestionarioUsabilidadPR;
    private List<CuestionarioUsabilidad> listaCuestionarioUsabilidadAB;
    private List<CuestionarioUsabilidad> listaCuestionarioUsabilidadFB;
    private List<CuestionarioUsabilidad> listaCuestionarioUsabilidadSUS;
    private List<PreguntaUsabilidad> listaPreguntaUsabilidad;

    /**
     * Creates a new instance of CuestionarioUsabilidadController
     */
    public CuestionarioUsabilidadController() {
    }

    public List<CuestionarioUsabilidad> getListaCuestionarioUsabilidadPR() {
        return listaCuestionarioUsabilidadPR;
    }

    public void setListaCuestionarioUsabilidadPR(List<CuestionarioUsabilidad> listaCuestionarioUsabilidadPR) {
        this.listaCuestionarioUsabilidadPR = listaCuestionarioUsabilidadPR;
    }

    public List<CuestionarioUsabilidad> getListaCuestionarioUsabilidadAB() {
        return listaCuestionarioUsabilidadAB;
    }

    public void setListaCuestionarioUsabilidadAB(List<CuestionarioUsabilidad> listaCuestionarioUsabilidadAB) {
        this.listaCuestionarioUsabilidadAB = listaCuestionarioUsabilidadAB;
    }

    public List<CuestionarioUsabilidad> getListaCuestionarioUsabilidadFB() {
        return listaCuestionarioUsabilidadFB;
    }

    public void setListaCuestionarioUsabilidadFB(List<CuestionarioUsabilidad> listaCuestionarioUsabilidadFB) {
        this.listaCuestionarioUsabilidadFB = listaCuestionarioUsabilidadFB;
    }

    public List<CuestionarioUsabilidad> getListaCuestionarioUsabilidadSUS() {
        return listaCuestionarioUsabilidadSUS;
    }

    public void setListaCuestionarioUsabilidadSUS(List<CuestionarioUsabilidad> listaCuestionarioUsabilidadSUS) {
        this.listaCuestionarioUsabilidadSUS = listaCuestionarioUsabilidadSUS;
    }

    public void init() {
        this.listaCuestionarioUsabilidadAB = new ArrayList<>();
        this.listaCuestionarioUsabilidadPR = new ArrayList<>();
        this.listaCuestionarioUsabilidadFB = new ArrayList<>();
        this.listaCuestionarioUsabilidadSUS = new ArrayList<>();

        for (PreguntaUsabilidad preg : cuestionarioUsabilidadFacade.findAllPreguntasUsabilidad(0)) {
            this.cuestionarioUsabilidad = new CuestionarioUsabilidad();
            this.cuestionarioUsabilidad.setIdPreguntaUsabilidad(preg);
            this.cuestionarioUsabilidad.setIdUsuario(JSFutil.getUsuarioConectado());
            this.listaCuestionarioUsabilidadSUS.add(cuestionarioUsabilidad);
        }
        for (PreguntaUsabilidad preg : cuestionarioUsabilidadFacade.findAllPreguntasUsabilidad(1)) {
            this.cuestionarioUsabilidad = new CuestionarioUsabilidad();
            this.cuestionarioUsabilidad.setIdPreguntaUsabilidad(preg);
            this.cuestionarioUsabilidad.setIdUsuario(JSFutil.getUsuarioConectado());
            this.listaCuestionarioUsabilidadPR.add(cuestionarioUsabilidad);
        }
        for (PreguntaUsabilidad preg : cuestionarioUsabilidadFacade.findAllPreguntasUsabilidad(2)) {
            this.cuestionarioUsabilidad = new CuestionarioUsabilidad();
            this.cuestionarioUsabilidad.setIdPreguntaUsabilidad(preg);
            this.cuestionarioUsabilidad.setIdUsuario(JSFutil.getUsuarioConectado());
            this.listaCuestionarioUsabilidadAB.add(cuestionarioUsabilidad);
        }
        for (PreguntaUsabilidad preg : cuestionarioUsabilidadFacade.findAllPreguntasUsabilidad(3)) {
            this.cuestionarioUsabilidad = new CuestionarioUsabilidad();
            this.cuestionarioUsabilidad.setIdPreguntaUsabilidad(preg);
            this.cuestionarioUsabilidad.setIdUsuario(JSFutil.getUsuarioConectado());
            this.listaCuestionarioUsabilidadFB.add(cuestionarioUsabilidad);
        }
    }

}
