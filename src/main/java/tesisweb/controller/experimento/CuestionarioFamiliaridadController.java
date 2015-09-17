/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.experimento;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.inject.Inject;
import tesisweb.ejb.entity.CuestionarioFamiliaridad;
import tesisweb.ejb.facade.CuestionarioFamiliaridadDAO;
import tesisweb.util.JSFutil;

/**
 *
 * @author root
 */
@Named(value = "CuestionarioFamiliaridadController")
@SessionScoped
public class CuestionarioFamiliaridadController implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(CuestionarioFamiliaridadController.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("tesisweb.properties.bundle", JSFutil.getmyLocale());

    @Inject
    CuestionarioFamiliaridadDAO cuestionarioFamiliaridadDAO;
    private CuestionarioFamiliaridad cuestionarioFamiliaridad;
    private List<CuestionarioFamiliaridad> listaCuestionarioFamiliaridad;

    /**
     * Creates a new instance of CuestionarioFamiliaridadController
     */
    public CuestionarioFamiliaridadController() {
    }

    public CuestionarioFamiliaridad getCuestionarioFamiliaridad() {
        return cuestionarioFamiliaridad;
    }

    public void setCuestionarioFamiliaridad(CuestionarioFamiliaridad cuestionarioFamiliaridad) {
        this.cuestionarioFamiliaridad = cuestionarioFamiliaridad;
    }

    public List<CuestionarioFamiliaridad> getListaCuestionarioFamiliaridad() {
        return listaCuestionarioFamiliaridad;
    }

    public void setListaCuestionarioFamiliaridad(List<CuestionarioFamiliaridad> listaCuestionarioFamiliaridad) {
        this.listaCuestionarioFamiliaridad = listaCuestionarioFamiliaridad;
    }

}
