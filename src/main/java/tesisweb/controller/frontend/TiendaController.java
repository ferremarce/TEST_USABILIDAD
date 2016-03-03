/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.frontend;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.inject.Inject;
import tesisweb.controller.experimento.ExperimentoController;

/**
 *
 * @author root
 */
@Named(value = "TiendaController")
@SessionScoped
public class TiendaController implements Serializable {

    private Integer tipoGarantia;
    @Inject
    private ExperimentoController experimentoController;

    /**
     * Creates a new instance of TiendaController
     */
    public TiendaController() {
    }

    public Integer getTipoGarantia() {
        return tipoGarantia;
    }

    public void setTipoGarantia(Integer tipoGarantia) {
        this.tipoGarantia = tipoGarantia;
    }

    public String doGarantiaForm(Integer id) {
        this.tipoGarantia = id;
        try {
            //Se usó el PRF FICTICIA
            if (this.experimentoController.getClickPopupFicticiaPR()) {
                this.experimentoController.getMetrica().addProgresoTarea("PRFF-1");
            }
        } catch (Exception e) {
            //Evita que el sistema caiga sin ejecutarse desde el entorno experimental
        }
        return "/frontend/legal/garantia?faces-redirect=true";
    }
}
