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
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import tesisweb.controller.frontend.LoginManager;
import tesisweb.ejb.experimento.entity.GrupoMatrizExperimental;
import tesisweb.ejb.experimento.entity.OrdenExposicionMuGrupo;
import tesisweb.ejb.experimento.facade.GrupoMatrizExperimentalFacade;
import tesisweb.ejb.tienda.entity.CuestionarioFamiliaridad;
import tesisweb.ejb.tienda.entity.Preference;
import tesisweb.ejb.tienda.entity.Usuario;
import tesisweb.ejb.tienda.facade.CuestionarioFamiliaridadDAO;
import tesisweb.ejb.tienda.facade.PreferenceDAO;
import tesisweb.ejb.tienda.facade.RolDAO;
import tesisweb.ejb.tienda.facade.UsuarioDAO;
import tesisweb.usabilidad.MUController;
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
    private final String USER_SESSION_KEY = "user";
    private final String USER_SESSION_LANGUAGE = "language";

    @Inject
    CuestionarioFamiliaridadDAO cuestionarioFamiliaridadDAO;
    @Inject
    UsuarioDAO usuarioDAO;
    @Inject
    PreferenceDAO preferenceDAO;
    @Inject
    RolDAO rolDAO;
    @Inject
    LoginManager loginManager;
    @Inject
    private GrupoMatrizExperimentalFacade grupoMatrizExperimentalFacade;
    @Inject
    MUController muController;
    private CuestionarioFamiliaridad cuestionarioFamiliaridad;
    private List<CuestionarioFamiliaridad> listaCuestionarioFamiliaridad;
    private GrupoMatrizExperimental grupoUsuarioLogin;
    private List<String> listAppUsadasFrecuente;
    private List<String> listParaQueUsasInternet;
    private List<String> listIdiomas;
    private List<String> listTiendasPreferidas;

    /**
     * Creates a new instance of CuestionarioFamiliaridadController
     */
    public CuestionarioFamiliaridadController() {

    }

    public List<String> getListTiendasPreferidas() {
        return listTiendasPreferidas;
    }

    public void setListTiendasPreferidas(List<String> listTiendasPreferidas) {
        this.listTiendasPreferidas = listTiendasPreferidas;
    }

    public List<String> getListIdiomas() {
        return listIdiomas;
    }

    public void setListIdiomas(List<String> listIdiomas) {
        this.listIdiomas = listIdiomas;
    }

    public List<String> getListParaQueUsasInternet() {
        return listParaQueUsasInternet;
    }

    public void setListParaQueUsasInternet(List<String> listParaQueUsasInternet) {
        this.listParaQueUsasInternet = listParaQueUsasInternet;
    }

    public List<String> getListAppUsadasFrecuente() {
        return listAppUsadasFrecuente;
    }

    public void setListAppUsadasFrecuente(List<String> listAppUsadasFrecuente) {
        this.listAppUsadasFrecuente = listAppUsadasFrecuente;
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

    /**
     * Guardar un registro
     *
     * @return
     */
    public String doGuardar() {
        try {
            //Guardar las opciones que tienen multiples selecciones
            this.cuestionarioFamiliaridad.setParaqueUsasInternet(this.listParaQueUsasInternet.toString());
            this.cuestionarioFamiliaridad.setQueAppUsa(this.listAppUsadasFrecuente.toString());
            this.cuestionarioFamiliaridad.setIdioma(this.listIdiomas.toString());
            this.cuestionarioFamiliaridad.setTiendasPreferidas(this.listTiendasPreferidas.toString());
            //Logout si existise ya un usuario
            loginManager.doLogout();
            Usuario u = new Usuario();
            u.setCuenta(this.cuestionarioFamiliaridad.getAliases());
            u.setEsActivo(Boolean.TRUE);

            Preference p = preferenceDAO.find(0);
            p.setIdPreference(null);
            preferenceDAO.create(p);
            u.setIdPreference(p); //PREFERENCIA SUCIA
            u.setIdRol(rolDAO.find(5)); //SUJETO EXPERIMENTAL
            u.setNombres(this.cuestionarioFamiliaridad.getAliases());
            u.setApellidos("SUJETO EXPERIMENTAL");
            u.setFechaHoraConexion(JSFutil.getFechaHoraActual());
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            u.setIpConexion(JSFutil.getClientIpAddr(request));
            this.grupoUsuarioLogin = grupoMatrizExperimentalFacade.findSiguienteGrupoExperimental();
            u.setIdGrupoExperimental(this.grupoUsuarioLogin);
            //Crear el usuario autologeable
            usuarioDAO.create(u);
            this.cuestionarioFamiliaridad.setIdUsuario(u);
            //Crear el cuestionario asociado a este usuario
            cuestionarioFamiliaridadDAO.create(cuestionarioFamiliaridad);
            //Activar o desactivar los mecanismos de usabilidad
            for (OrdenExposicionMuGrupo orden : this.grupoUsuarioLogin.getOrdenExposicionMuGrupoList()) {
                switch (orden.getIdMu().getIdMu()) {
                    case 1: //PREFERENCE
                        this.muController.setMuPreference(orden.getEstado());
                        break;
                    case 2: //ABORT OPERATION
                        this.muController.setMuAbortOperation(orden.getEstado());
                        break;
                    case 3: //PROGRESS FEEDBACK
                        this.muController.setMuProgressFeedBack(orden.getEstado());
                        break;
                }
            }
            JSFutil.addSuccessMessage("Acceso concedido");
            JSFutil.putSessionVariable(USER_SESSION_KEY, u);
            JSFutil.putSessionVariable(USER_SESSION_LANGUAGE, u.getIdPreference().getIdioma());
            return "/experimento/inicio?faces-redirect=true";
        } catch (EJBException ex) {
            String msg = "";
            Throwable cause = ex.getCause();
            if (cause != null) {
                msg = cause.getLocalizedMessage();
            }
            JSFutil.addErrorMessage("Se ha producido un error durante la inicialización del experimento. Es probable que el Entorno Experimental esté temporalmente inactivo. Favor póngase en contacto con el administrador al pie de la página");
            if (msg.length() > 0) {
                JSFutil.addErrorMessage(msg);
            } else {
                JSFutil.addErrorMessage(ex, JSFutil.getMyBundle().getString("UpdateError"));
            }
            return "";
        }
    }

    @PostConstruct
    public void init() {
        this.cuestionarioFamiliaridad = new CuestionarioFamiliaridad();
    }
}
