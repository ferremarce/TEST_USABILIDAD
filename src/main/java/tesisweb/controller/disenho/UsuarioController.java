/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.disenho;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import tesisweb.ejb.experimento.entity.GrupoMatrizExperimental;
import tesisweb.ejb.experimento.entity.Metrica;
import tesisweb.ejb.experimento.facade.GrupoMatrizExperimentalFacade;
import tesisweb.ejb.tienda.entity.Preference;
import tesisweb.ejb.tienda.entity.Usuario;
import tesisweb.ejb.tienda.facade.PreferenceDAO;
import tesisweb.ejb.tienda.facade.UsuarioDAO;
import tesisweb.util.JSFutil;

/**
 *
 * @author jmferreira1978@gmail.com
 */
@SessionScoped
@Named("UsuarioController")
public class UsuarioController implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(UsuarioController.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("tesisweb.properties.bundle", JSFutil.getmyLocale());

    @Inject
    UsuarioDAO usuarioDAO;
    @Inject
    PreferenceDAO preferenceDAO;
    @Inject
    GrupoMatrizExperimentalFacade grupoMatrizExperimentalDAO;

    private List<Usuario> listaUsuario;
    private Usuario usuario;
    private String tmpPasswd;

    /**
     * Creates a new instance of UsuarioController
     */
    public UsuarioController() {
    }

    /**
     * getter tmpPasswd
     *
     * @return
     */
    public String getTmpPasswd() {
        return tmpPasswd;
    }

    /**
     * setter tmpPasswd
     *
     * @param tmpPasswd
     */
    public void setTmpPasswd(String tmpPasswd) {
        this.tmpPasswd = tmpPasswd;
    }

    /**
     * getter Usuario
     *
     * @return
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * setter Usuario
     *
     * @param usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * getter ListaUsuario
     *
     * @return
     */
    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    /**
     * setter ListaUsuario
     *
     * @param listaUsuario
     */
    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    /**
     * Preparar el Formulario de Listado
     *
     * @return
     */
    public String doListarForm() {
        this.listaUsuario = usuarioDAO.findAll();
        return "/disenho/ListarUsuario";
    }

    public String doListarSujetosForm(Integer idDise) {
        this.listaUsuario = usuarioDAO.getAllUsuarioExperimento(idDise);
        return "/disenho/VerSujetos";
    }

    /**
     * Preparar el Formulario de Visualización
     *
     * @param u
     * @return
     */
    public String doVerForm(Usuario u) {
        this.usuario = u;
        return "/backend/usuario/VerUsuario";
    }

    /**
     * Preparar el Formulario de Edición
     *
     * @param u
     * @return
     */
    public String doEditarForm(Usuario u) {
        this.usuario = u;
        return "/backend/usuario/CrearUsuario";
    }

    /**
     * Preparar el Formulario de Creacion
     *
     * @return
     */
    public String doCrearForm() {
        this.tmpPasswd = "";
        this.usuario = new Usuario();
        return "/backend/usuario/CrearUsuario";
    }

    /**
     * Guardar un registro
     *
     * @return
     */
    public String doGuardar() {
        try {
            if (!this.tmpPasswd.isEmpty()) {
                usuario.setContrasenha(JSFutil.getSecurePassword(tmpPasswd));
            }
            if (this.usuario.getIdPreference() == null) {
                //Recuperar la preferencia predeterminada
                Preference p = preferenceDAO.find(0);
                Preference newP = new Preference();
                newP.setFuente(p.getFuente());
                newP.setIdioma(p.getIdioma());
                newP.setNombrePreferencia("Preferencia-" + this.usuario.getCuenta());
                newP.setTamanho(p.getTamanho());
                newP.setTema(p.getTema());
                //crear la nueva preferencia
                preferenceDAO.create(newP);
                //Asignar la preferencia creada al usuario
                this.usuario.setIdPreference(newP);
            }
            usuarioDAO.update(usuario);
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
        return doListarForm();

    }

    /**
     * Borrar un registro
     *
     * @param u
     */
    public void doBorrar(Usuario u) {
        try {
            GrupoMatrizExperimental grupo=u.getIdGrupoExperimental();
            usuarioDAO.remove(u);
            grupo.setCantidadParticipante(grupo.getCantidadParticipante()-1);
            grupoMatrizExperimentalDAO.edit(grupo);
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
        doListarForm();

    }

    public TimeZone getMyTimeZone() {
        return JSFutil.getMyTimeZone();
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

    }

    public String sumarioMetricas(Usuario u) {
        int prf = 0, abr = 0, pfb = 0, mt = 0, cuest = 0;
        //System.out.println(u.getCuenta());
        String cadena = "";
        try {
            for (Metrica m : u.getMetricaList()) {
                if (m.getIdMecanismoUsabilidad() != null) {
                    switch (m.getIdMecanismoUsabilidad().getIdMu()) {
                        case 1: //PRF
                            prf++;
                            break;
                        case 2: //ABR
                            abr++;
                            break;
                        case 3: //PFB
                            pfb++;
                            break;
                    }
                } else {
                    mt++;
                }
            }
            cuest=u.getCuestionarioUsabilidadList().size();
            if (prf == 2 && abr == 1 && pfb == 1 && mt > 0) {
                cadena = " SI ";
            } else {
                cadena = " NO ";
            }
            cadena += "PRF: " + prf + " ABR: " + abr + " PFB: " + pfb + " MT: " + mt + " CUE: " + cuest;
            return cadena;
        } catch (Exception e) {
            cadena = "ERROR";
            return cadena;
        }
    }
}
