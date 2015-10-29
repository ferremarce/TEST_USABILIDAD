/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.disenho;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Inject;
import tesisweb.ejb.experimento.entity.DisenhoExperimental;
import tesisweb.ejb.experimento.entity.GrupoMatrizExperimental;
import tesisweb.ejb.experimento.entity.MecanismoUsabilidad;
import tesisweb.ejb.experimento.entity.OrdenExposicionMuGrupo;
import tesisweb.ejb.experimento.facade.DisenhoExperimentalFacade;
import tesisweb.ejb.experimento.facade.GrupoMatrizExperimentalFacade;
import tesisweb.ejb.experimento.facade.MecanismoUsabilidadFacade;
import tesisweb.ejb.experimento.facade.OrdenExposicionMuGrupoFacade;
import tesisweb.util.JSFutil;

/**
 *
 * @author root
 */
@Named(value = "DisenhoExperimentalController")
@SessionScoped
public class DisenhoExperimentalController implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(DisenhoExperimentalController.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("tesisweb.properties.bundle", JSFutil.getmyLocale());

    @Inject
    private DisenhoExperimentalFacade disenhoExperimentalFacade;
    @Inject
    private GrupoMatrizExperimentalFacade grupoMatrizExperimentalFacade;
    @Inject
    MecanismoUsabilidadFacade mecanismoUsabilidadFacade;
    @Inject
    OrdenExposicionMuGrupoFacade ordenExposicionMuGrupoFacade;
    private DisenhoExperimental disenhoExperimental;
    private List<DisenhoExperimental> listaDisenhoExperimental;
    private Integer cantidadGrupo;

    /**
     * Creates a new instance of DisenhoExperimental
     */
    public DisenhoExperimentalController() {
    }

    //SETTER Y GETTER
    public DisenhoExperimental getDisenhoExperimental() {
        return disenhoExperimental;
    }

    public void setDisenhoExperimental(DisenhoExperimental disenhoExperimental) {
        this.disenhoExperimental = disenhoExperimental;
    }

    public List<DisenhoExperimental> getListaDisenhoExperimental() {
        return listaDisenhoExperimental;
    }

    public void setListaDisenhoExperimental(List<DisenhoExperimental> listaDisenhoExperimental) {
        this.listaDisenhoExperimental = listaDisenhoExperimental;
    }

    public Integer getCantidadGrupo() {
        return cantidadGrupo;
    }

    public void setCantidadGrupo(Integer cantidadGrupo) {
        this.cantidadGrupo = cantidadGrupo;
    }

    //METODOS DE ACCION
    public String doListar() {
        this.listaDisenhoExperimental = disenhoExperimentalFacade.findAll();
        if (this.listaDisenhoExperimental.size() > 0) {
            JSFutil.addSuccessMessage(this.listaDisenhoExperimental.size() + " registro/s recuperado/s");
        } else {
            JSFutil.addSuccessMessage("Sin registros");
        }
        return "/disenho/ListarDisenho";
    }

    public String doEditarForm(DisenhoExperimental de) {
        this.disenhoExperimental = de;
        this.cantidadGrupo = 0;
        return "/disenho/EditarDisenho";
    }

    public String doCrearForm() {
        this.disenhoExperimental = new DisenhoExperimental();
        this.cantidadGrupo = 8;
        return "/disenho/EditarDisenho";
    }

    public String doCrear() {
        try {
            if (this.disenhoExperimental.getIdDisenho() != null) {
                this.disenhoExperimentalFacade.edit(this.disenhoExperimental);
            } else {
                if (this.cantidadGrupo <= 0) {
                    JSFutil.addErrorMessage("La cantidad de grupos debe ser mayor a Cero");
                    return "";
                }
                this.disenhoExperimentalFacade.create(disenhoExperimental);
                GrupoMatrizExperimental gme;
                OrdenExposicionMuGrupo orden;
                for (int i = 1; i <= this.cantidadGrupo; i++) {
                    gme = new GrupoMatrizExperimental();
                    gme.setCantidadParticipante(0);
                    gme.setIdDisenho(disenhoExperimental);
                    gme.setNombreGrupo("GRUPO " + i);
                    this.grupoMatrizExperimentalFacade.create(gme);
                    for(MecanismoUsabilidad mu: this.mecanismoUsabilidadFacade.findAll()){
                        orden=new OrdenExposicionMuGrupo();
                        orden.setEstado(Boolean.TRUE);
                        orden.setIdGrupo(gme);
                        orden.setIdMu(mu);
                        orden.setOrden(0);
                        this.ordenExposicionMuGrupoFacade.create(orden);
                    }
                }
            }
            JSFutil.addSuccessMessage(JSFutil.getMyBundle().getString("UpdateSuccess"));
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
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            JSFutil.addErrorMessage(ex, JSFutil.getMyBundle().getString("UpdateError"));
        }
        return this.doListar();
    }

    public void doUpdateEstadoDisenho(DisenhoExperimental de) {
        try {
            this.disenhoExperimentalFacade.edit(de);
            JSFutil.addSuccessMessage(JSFutil.getMyBundle().getString("UpdateSuccess"));
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
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            JSFutil.addErrorMessage(ex, JSFutil.getMyBundle().getString("UpdateError"));
        }
    }

    public void doBorrar(DisenhoExperimental de) {
        try {
            this.disenhoExperimental = de;
            this.disenhoExperimentalFacade.remove(de);
            JSFutil.addSuccessMessage(JSFutil.getMyBundle().getString("UpdateSuccess"));
            this.doListar();
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
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            JSFutil.addErrorMessage(ex, JSFutil.getMyBundle().getString("UpdateError"));
        }
    }

    //METODOS LISTENER
}
