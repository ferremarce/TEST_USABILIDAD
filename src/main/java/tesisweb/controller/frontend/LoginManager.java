/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.frontend;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.event.ToggleEvent;
import tesisweb.ejb.tienda.entity.Usuario;
import tesisweb.ejb.tienda.facade.UsuarioDAO;
import tesisweb.util.JSFutil;

/**
 *
 * @author jmferreira1978@gmail.com
 */
@SessionScoped
@Named("LoginManager")
public class LoginManager implements Serializable {

    @Inject
    UsuarioDAO usuarioDAO;

    private final String USER_SESSION_KEY = "user";
    private final String USER_SESSION_LANGUAGE = "language";
    private String cuenta;
    private String contrasenha;
    private String contrasenha2;
    private Integer intento = 1;
    private Boolean adminCollapse = Boolean.FALSE;
    private Boolean clientCollapse = Boolean.FALSE;
    private Integer cantidadClick;

    /**
     * Creates a new instance of LoginManager
     */
    public LoginManager() {
    }

    public Integer getIntento() {
        return intento;
    }

    public void setIntento(Integer intento) {
        this.intento = intento;
    }

    public Integer getCantidadClick() {
        return cantidadClick;
    }

    public void setCantidadClick(Integer cantidadClick) {
        this.cantidadClick = cantidadClick;
    }

    /**
     * getter Cuenta
     *
     * @return
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * setter Cuenta
     *
     * @param cuenta
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * getter Contrasenha
     *
     * @return
     */
    public String getContrasenha() {
        return contrasenha;
    }

    /**
     * setter Contrasenha
     *
     * @param contrasenha
     */
    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    /**
     * getter contrasenha2
     *
     * @return
     */
    public String getContrasenha2() {
        return contrasenha2;
    }

    /**
     * setter contrasenha2
     *
     * @param contrasenha2
     */
    public void setContrasenha2(String contrasenha2) {
        this.contrasenha2 = contrasenha2;
    }

    public Boolean getAdminCollapse() {
        return adminCollapse;
    }

    public void setAdminCollapse(Boolean adminCollapse) {
        this.adminCollapse = adminCollapse;
    }

    public Boolean getClientCollapse() {
        return clientCollapse;
    }

    public void setClientCollapse(Boolean clientCollapse) {
        this.clientCollapse = clientCollapse;
    }

    /**
     * Ejecutar la acción de login
     *
     * @return
     */
    public String doLogin() {
        try {
            Usuario usuario = usuarioDAO.getUsuario(cuenta);
            if (usuario != null && usuario.getEsActivo() && this.puedeLogearse(usuario)) {
                if (JSFutil.checkSecurePassword(this.contrasenha, usuario.getContrasenha())) {
                    JSFutil.addSuccessMessage("Acceso concedido");
                    JSFutil.putSessionVariable(USER_SESSION_KEY, usuario);
                    JSFutil.putSessionVariable(USER_SESSION_LANGUAGE, usuario.getIdPreference().getIdioma());
                    return "";
                } else {
                    this.intento++;
                    JSFutil.addErrorMessage("Acceso incorrecto!... credenciales no válidas.");
                    return null;
                }
            } else {
                this.intento++;
                JSFutil.addErrorMessage("Acceso incorrecto!... Usuario '" + cuenta + "' no existe o se encuentra deshabilitado.");
                return null;
            }
        } catch (Exception e) {
            this.intento++;
            return null;
        }
    }

    public String doAutoLogin() {
        this.cuenta="marcelo";
        this.doLoginNoPass();
        return "/experimento/tareaPR";
    }

    public String doGetURLAPP() {
        return JSFutil.getServerUrl() + "/tesisapp/frontend/index.xhtml";
    }

    public String doLoginNoPass() {
        try {
            Usuario usuario = usuarioDAO.getUsuario(cuenta);
            if (usuario != null) {
                JSFutil.addSuccessMessage("Acceso concedido");
                JSFutil.putSessionVariable(USER_SESSION_KEY, usuario);
                JSFutil.putSessionVariable(USER_SESSION_LANGUAGE, usuario.getIdPreference().getIdioma());
                return "";
            } else {
                JSFutil.addErrorMessage("Acceso incorrecto!... Usuario '" + cuenta + "' no existe o se encuentra deshabilitado.");
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public void gotoMain() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            response.sendRedirect(req.getContextPath() + "/frontend/index2.pmf");
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String doLoginTienda() {
        try {
            Usuario usuario = usuarioDAO.getUsuario(cuenta);
            if (usuario != null && usuario.getEsActivo() && this.puedeLogearse(usuario)) {
                if (JSFutil.checkSecurePassword(this.contrasenha, usuario.getContrasenha())) {
                    JSFutil.addSuccessMessage("Acceso concedido");
                    JSFutil.putSessionVariable(USER_SESSION_KEY, usuario);
                    return "";
                } else {
                    this.intento++;
                    JSFutil.addErrorMessage("Acceso incorrecto!... credenciales no válidas.");
                    return "";
                }
            } else {
                this.intento++;
                JSFutil.addErrorMessage("Acceso incorrecto!... Usuario '" + cuenta + "' no existe o se encuentra deshabilitado.");
                return "";
            }
        } catch (Exception e) {
            this.intento++;
            return "";
        }
    }

    private Boolean puedeLogearse(Usuario u) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ip = JSFutil.getClientIpAddr(request);
        if (u.getLoginExterno()) {
            return Boolean.TRUE;
        }
        if (JSFutil.esValidoIPIntranet(ip)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

    }

    /**
     * Invalidar la sesión y hacer logout
     *
     * @return
     */
    public String doLogout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "/backend/index";
    }

    public String doLogoutTienda() {
        JSFutil.removeSessionVariable(this.USER_SESSION_KEY);
        JSFutil.addSuccessMessage("Ha cerrado exitosamente la sesión");
        return "/frontend/index2";
    }

    /**
     * getter del usuario conectado actualmente
     *
     * @return
     */
    public Usuario getUsuarioLogeado() {
        return JSFutil.getUsuarioConectado();
    }

    public Integer getIdPreferenciaUsuario() {
        try {
            if (JSFutil.getUsuarioConectado() != null) {
                return JSFutil.getUsuarioConectado().getIdPreference().getIdPreference();
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public String doCambiarContrasenhaForm() {
        this.contrasenha = "";
        this.contrasenha2 = "";
        return "/backend/usuario/CambiarContrasenha";
    }

    public String doCambiarContrasenha() {
        if (this.getContrasenha().length() < 8) {
            JSFutil.addErrorMessage("Contraseña insegura. Debe proporcionar una contraseña de al menos 8 letras/numeros");
            return "";
        }
        if (this.getContrasenha().compareTo(this.contrasenha2) != 0) {
            JSFutil.addErrorMessage("Las contraseñas no coinciden. Por favor verifique y vuelva a intentar");
            return "";
        }

        try {
            Usuario u = JSFutil.getUsuarioConectado();
            u.setContrasenha(JSFutil.getSecurePassword(this.contrasenha));
            usuarioDAO.update(u);
            JSFutil.addSuccessMessage("Contraseña cambiada exitosamente.");
        } catch (Exception e) {
            JSFutil.addErrorMessage(e, "Ocurrió un error de persistencia.");
        }
        return "";
    }

    /**
     * getter timeZone
     *
     * @return
     */
    public TimeZone getMyTimeZone() {
        return JSFutil.getMyTimeZone();
    }

    public String doLoginFrom() {
        return "/login";
    }

    public void doToggleHandler(ToggleEvent event) {
        String idLayout = event.getComponent().getId();
        if (idLayout.compareTo("admin") == 0) {
            if (this.adminCollapse) {
                this.adminCollapse = Boolean.FALSE;
            } else {
                this.adminCollapse = Boolean.TRUE;
            }

        } else if (idLayout.compareTo("client") == 0) {
            if (this.clientCollapse) {
                this.clientCollapse = Boolean.FALSE;
            } else {
                this.clientCollapse = Boolean.TRUE;
            }

        }
    }

    public void incrementar() {
        this.cantidadClick++;
    }

    public void init() {
        try {
            /**
             * Formato del URL q=usuario
             */
            FacesContext context = FacesContext.getCurrentInstance();
            Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
            String usuarioSujeto = paramMap.get("q");
            HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            String userAgent = req.getHeader("user-agent");
            String accept = req.getHeader("Accept");

            if (usuarioSujeto != null) {
                this.cuenta = usuarioSujeto;
                this.contrasenha = "123456789";
                this.doLoginNoPass();
                //Redirect al main
                response.sendRedirect(req.getContextPath() + "/frontend/index.xhtml");
            } else if (JSFutil.getUsuarioConectado() == null) {
                //No se puede hacer el experimento si el usuario no esta logeado en el sistema              
                response.sendRedirect(req.getContextPath() + "/404.xhtml");
            }

        } catch (NumberFormatException | NullPointerException e) {
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
