/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.general;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tesisweb.util.JSFutil;


/**
 *
 * @author root
 */
@ManagedBean(name = "ViewHandlerController")
@SessionScoped
public class ViewHandlerController {

    private Boolean esMenuVisible = Boolean.TRUE;

    /**
     * Creates a new instance of ViewHandler
     */
    public ViewHandlerController() {
    }

    public Boolean getEsMenuVisible() {
        return esMenuVisible;
    }

    public void setEsMenuVisible(Boolean esMenuVisible) {
        this.esMenuVisible = esMenuVisible;
    }
    
    public void calculateRenderKitId() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        String userAgent = req.getHeader("user-agent");
        String accept = req.getHeader("Accept");

        try {
            if (userAgent != null && accept != null) {
                UserAgentInfo agent = new UserAgentInfo(userAgent, accept);
                if (agent.isMobileDevice()) {

                    response.sendRedirect(req.getContextPath() + "/errorMobile.xhtml");
//                    return "PRIMEFACES_MOBILE";
                    return;
                }
            }
            response.sendRedirect(req.getContextPath() + "/experimento/index.xhtml");
            //      return "HTML_BASIC";
        } catch (IOException ex) {
            Logger.getLogger(ViewHandlerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Selecciona el navegador Clasico/Mobile
     */
    public void init() {
        this.calculateRenderKitId();
    }

    /**
     * Redirecciona al SIL mobile
     *
     */
    public void mobileVersion() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            response.sendRedirect(JSFutil.getServerUrl() + "/mobileSILpy");
        } catch (IOException ex) {
            Logger.getLogger(ViewHandlerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getServerURL() {
        return JSFutil.getServerUrl() + "/CONSULTASILpy-war/";
    }

    public Boolean esMobileVersion() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        String userAgent = req.getHeader("user-agent");
        String accept = req.getHeader("Accept");
        if (userAgent != null && accept != null) {
            UserAgentInfo agent = new UserAgentInfo(userAgent, accept);
            if (agent.isMobileDevice()) {

                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;

    }

}
