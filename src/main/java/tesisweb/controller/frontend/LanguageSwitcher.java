/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.frontend;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import tesisweb.ejb.tienda.entity.Preference;
import tesisweb.ejb.tienda.facade.PreferenceDAO;
import tesisweb.ejb.tienda.facade.UsuarioDAO;
import tesisweb.util.JSFutil;

/**
 *
 * @author root
 */
@Named(value = "LanguageSwitcher")
@SessionScoped
public class LanguageSwitcher implements Serializable {

    private static final Logger LOG = Logger.getLogger(LanguageSwitcher.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("tesisweb.properties.bundle", JSFutil.getmyLocale());

    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    @Inject
    PreferenceDAO preferenceDAO;
    @Inject
    UsuarioDAO usuarioDAO;

    /**
     * Creates a new instance of LanguageController
     */
    public LanguageSwitcher() {
    }

    public void changeLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        if (JSFutil.getUsuarioConectado() != null) {
            Preference p = preferenceDAO.find(JSFutil.getUsuarioConectado().getIdPreference().getIdPreference());
            if (p.getIdioma().compareTo(language) != 0) {
                p.setIdioma(language);
                preferenceDAO.update(p);
                System.out.println("------ Actualizando idioma del usuario desde LanguageSwitcher...");
            }
        }
        System.out.println("------ Cambiando idioma desde LanguageSwitcher..." + language);
        JSFutil.putSessionVariable("language", language);
        JSFutil.addSuccessMessage(JSFutil.getMyBundle().getString("IdiomaSuccess"));
    }

    public void changeUserLanguage() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(JSFutil.getIdiomaSesion()));
    }
}
