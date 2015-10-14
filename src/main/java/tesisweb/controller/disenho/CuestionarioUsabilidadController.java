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
import javax.ejb.EJBException;
import javax.inject.Inject;
import tesisweb.controller.frontend.LoginManager;
import tesisweb.ejb.experimento.entity.CuestionarioUsabilidad;
import tesisweb.ejb.experimento.entity.MecanismoUsabilidad;
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
    @Inject
    LoginManager loginManager;
    private CuestionarioUsabilidad cuestionarioUsabilidad;
    private List<CuestionarioUsabilidad> listaCuestionarioUsabilidadPR;
    private List<CuestionarioUsabilidad> listaCuestionarioUsabilidadAB;
    private List<CuestionarioUsabilidad> listaCuestionarioUsabilidadFB;
    private List<CuestionarioUsabilidad> listaCuestionarioUsabilidadSUS;
    private List<PreguntaUsabilidad> listaPreguntaUsabilidad;

    private List<CuestionarioUsabilidad> listaCuestionarioUsabilidad;
    private MecanismoUsabilidad idMu;
    private String comentarioFinal;
    private Integer tipoCuestionario;

    /**
     * Creates a new instance of CuestionarioUsabilidadController
     */
    public CuestionarioUsabilidadController() {
    }

    public Integer getTipoCuestionario() {
        return tipoCuestionario;
    }

    public void setTipoCuestionario(Integer tipoCuestionario) {
        this.tipoCuestionario = tipoCuestionario;
    }

    public String getComentarioFinal() {
        return comentarioFinal;
    }

    public void setComentarioFinal(String comentarioFinal) {
        this.comentarioFinal = comentarioFinal;
    }

    public MecanismoUsabilidad getIdMu() {
        return idMu;
    }

    public void setIdMu(MecanismoUsabilidad idMu) {
        this.idMu = idMu;
    }

    public List<CuestionarioUsabilidad> getListaCuestionarioUsabilidad() {
        return listaCuestionarioUsabilidad;
    }

    public void setListaCuestionarioUsabilidad(List<CuestionarioUsabilidad> listaCuestionarioUsabilidad) {
        this.listaCuestionarioUsabilidad = listaCuestionarioUsabilidad;
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

    public void initCuestionarioPR() {
        this.listaCuestionarioUsabilidadPR = new ArrayList<>();
        for (PreguntaUsabilidad preg : cuestionarioUsabilidadFacade.findAllPreguntasUsabilidad(1)) {
            this.cuestionarioUsabilidad = new CuestionarioUsabilidad();
            this.cuestionarioUsabilidad.setIdPreguntaUsabilidad(preg);
            this.cuestionarioUsabilidad.setIdUsuario(JSFutil.getUsuarioConectado());
            this.listaCuestionarioUsabilidadPR.add(cuestionarioUsabilidad);
        }
    }

    public void initCuestionarioAB() {
        this.listaCuestionarioUsabilidadAB = new ArrayList<>();
        for (PreguntaUsabilidad preg : cuestionarioUsabilidadFacade.findAllPreguntasUsabilidad(2)) {
            this.cuestionarioUsabilidad = new CuestionarioUsabilidad();
            this.cuestionarioUsabilidad.setIdPreguntaUsabilidad(preg);
            this.cuestionarioUsabilidad.setIdUsuario(JSFutil.getUsuarioConectado());
            this.listaCuestionarioUsabilidadAB.add(cuestionarioUsabilidad);
        }
    }

    public void initCuestionarioFB() {
        this.listaCuestionarioUsabilidadFB = new ArrayList<>();
        for (PreguntaUsabilidad preg : cuestionarioUsabilidadFacade.findAllPreguntasUsabilidad(3)) {
            this.cuestionarioUsabilidad = new CuestionarioUsabilidad();
            this.cuestionarioUsabilidad.setIdPreguntaUsabilidad(preg);
            this.cuestionarioUsabilidad.setIdUsuario(JSFutil.getUsuarioConectado());
            this.listaCuestionarioUsabilidadFB.add(cuestionarioUsabilidad);
        }

    }

    public void initCuestionarioSUS() {
        this.listaCuestionarioUsabilidadSUS = new ArrayList<>();
        for (PreguntaUsabilidad preg : cuestionarioUsabilidadFacade.findAllPreguntasUsabilidad(0)) {
            this.cuestionarioUsabilidad = new CuestionarioUsabilidad();
            this.cuestionarioUsabilidad.setIdPreguntaUsabilidad(preg);
            this.cuestionarioUsabilidad.setIdUsuario(JSFutil.getUsuarioConectado());
            this.listaCuestionarioUsabilidadSUS.add(cuestionarioUsabilidad);
        }
    }

    public String doGuardar() {
        try {
            for (CuestionarioUsabilidad cu : this.listaCuestionarioUsabilidadAB) {
                cuestionarioUsabilidadFacade.create(cu);
            }
            for (CuestionarioUsabilidad cu : this.listaCuestionarioUsabilidadPR) {
                cuestionarioUsabilidadFacade.create(cu);
            }
            for (CuestionarioUsabilidad cu : this.listaCuestionarioUsabilidadFB) {
                cuestionarioUsabilidadFacade.create(cu);
            }
            for (CuestionarioUsabilidad cu : this.listaCuestionarioUsabilidadSUS) {
                cuestionarioUsabilidadFacade.create(cu);
            }
            //Guardar el comentario Final de usabilidad si existe
            if (this.comentarioFinal.length() > 0) {
                this.cuestionarioUsabilidad = new CuestionarioUsabilidad();
                this.cuestionarioUsabilidad.setComentarioFinal(comentarioFinal);
                this.cuestionarioUsabilidad.setIdUsuario(JSFutil.getUsuarioConectado());
                cuestionarioUsabilidadFacade.create(cuestionarioUsabilidad);
            }
            //Invalidar la sesión
            this.loginManager.doLogout();
            return "/experimento/agradecimiento?faces-redirect=true";
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
            return "";
        }
    }

    public void doListaDisenhoCuestionario(Integer idDise) {
        this.listaCuestionarioUsabilidad = cuestionarioUsabilidadFacade.findAllCuestionarioMuDisenho(idDise, this.idMu.getIdMu());
    }

    public void doListaDisenhoCuestionarioSUS(Integer idDise) {

        this.listaCuestionarioUsabilidad = cuestionarioUsabilidadFacade.findAllCuestionarioMuDisenho(idDise, this.tipoCuestionario);
    }

}
