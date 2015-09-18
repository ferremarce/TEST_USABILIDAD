/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.experimento;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author root
 */
@Named(value = "ExperimentoController")
@SessionScoped
public class ExperimentoController implements Serializable {

    /**
     * Creates a new instance of ExperimentoController
     */
    public ExperimentoController() {
    }

    public String gotoNextForm(Integer orden) {
        switch (orden) {
            case 1:
                return "/experimento/tareaPR";
            case 2:
                return "/experimento/tareaAB";
            case 3:
                return "/experimento/tareaFB";
            case 4:
                return "/experimento/cuestionarioFinal";
            case 5:
                return "/experimento/agradecimiento";
        }
        return "/experimento/tareaPR";
    }
}
