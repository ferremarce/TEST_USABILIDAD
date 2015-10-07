/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.frontend;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author root
 */
@Named(value = "TiendaController")
@SessionScoped
public class TiendaController implements Serializable {

    private Integer tipoGarantia;

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
        return "/frontend/legal/garantia?faces-redirect=true";
    }
}
