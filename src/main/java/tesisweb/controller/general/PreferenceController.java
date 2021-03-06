/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.general;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import tesisweb.controller.experimento.ExperimentoController;
import tesisweb.controller.frontend.LoginManager;
import tesisweb.ejb.tienda.entity.Preference;
import tesisweb.ejb.tienda.facade.PreferenceDAO;
import tesisweb.util.JSFutil;
import tesisweb.util.JSFutil.PersistAction;

/**
 *
 * @author root
 */
@Named(value = "PreferenceController")
@SessionScoped
public class PreferenceController implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(PreferenceController.class.getName());

    @Inject
    private PreferenceDAO preferenceDAO;
    @Inject
    private LoginManager loginManager;
    @Inject
    private ExperimentoController experimentoController;
    private Preference preference = new Preference();
    private List<Preference> listaPreference;
    private int sizeFont = 100;

    public PreferenceController() {
    }
    //********************************************
    // SETTERS Y GETTERS
    //********************************************

    public int getSizeFont() {
        return sizeFont;
    }

    public void setSizeFont(int sizeFont) {
        this.sizeFont = sizeFont;
    }

    public Preference getPreference() {
        return preference;
    }

    public List<Preference> getListaPreference() {
        return listaPreference;
    }

    public void setListaPreference(List<Preference> listaPreference) {
        this.listaPreference = listaPreference;
    }

    //********************************************
    // METODOS DE ACCIÓN
    //********************************************
    public String doCrearForm() {
        this.preference = new Preference();
        this.sizeFont = 100;
        return "/backend/preference/CrearPreference";
    }

    public String doListarForm() {
        this.listaPreference = preferenceDAO.findAll();
        return "/backend/preference/ListarPreference";
    }

    public String doGuardar() {
        this.preference.setTamanho(String.valueOf(sizeFont));
        if (this.preference.getIdPreference() != null) {
            persist(PersistAction.UPDATE);
            if (JSFutil.getUsuarioConectado().getIdPreference().getIdPreference().compareTo(this.preference.getIdPreference()) == 0) {
                JSFutil.putSessionVariable("language", this.preference.getIdioma());
            }
        } else {
            persist(PersistAction.CREATE);
        }

        try {
            //Se usó el PRF
            if (this.experimentoController.getClickPopupPR()) {
                this.experimentoController.getMetrica().addProgresoTarea("PRF-1");
            }
        } catch (Exception e) {
            //Evita que el sistema caiga sin ejecutarse desde el entorno experimental
        }
        return "/frontend/index";
    }

    public String doCancelar() {
        return "/frontend/index";
    }

    public String doAsignarStandarPreference() {
        this.preference.setFuente("Droid Sans");
        this.preference.setTamanho("100");
        this.preference.setTema("cupertino");
        persist(PersistAction.UPDATE);
        if (JSFutil.getUsuarioConectado().getIdPreference().getIdPreference().compareTo(this.preference.getIdPreference()) == 0) {
            JSFutil.putSessionVariable("language", this.preference.getIdioma());
        }
        this.doCambiarPreferencia(preference);
        return "/frontend/index";
    }

    private void persist(PersistAction persistAction) {
        try {
            if (persistAction.compareTo(PersistAction.CREATE) == 0) {
                preferenceDAO.create(this.preference);
            } else if (persistAction.compareTo(PersistAction.UPDATE) == 0) {
                preferenceDAO.update(this.preference);
            } else if (persistAction.compareTo(PersistAction.DELETE) == 0) {
                preferenceDAO.remove(this.preference);
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
        }
    }

    public String doEditarForm(Preference u) {
        this.preference = preferenceDAO.find(u.getIdPreference());
        this.sizeFont = Integer.parseInt(this.preference.getTamanho());
        return "/frontend/preference/CambiarPreference";
    }

    public String doCambiarPreferencia(Preference u) {
        this.preference = preferenceDAO.find(u.getIdPreference());
        this.sizeFont = Integer.parseInt(this.preference.getTamanho());
        return "/frontend/preference/CambiarPreference";
    }

    public void doCambiarPreferenciaUsuario() {
        this.doCambiarPreferencia(JSFutil.getUsuarioConectado().getIdPreference());
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('overlayPreference').show();");
    }

    public void doBorrar(Preference u) {
        this.preference = u;
        persist(PersistAction.DELETE);
        doListarForm();
    }

    public String obtenerTema() {
        String tema;
        //Si hay un error en las preferencias -> blitzer
        //Si es una tarea PR o PR ficticia -> se usa el theme del usuario
        //Si es no es una tarea PR/PR ficticia -> redmond
        try {
            //Verificamos si es la tarea PR o la ficticia de PR se usa el style guardado del usuario
            if (this.loginManager.getUsarPreferenciaUsuario()) {
                Preference p = preferenceDAO.find(JSFutil.getUsuarioConectado().getIdPreference().getIdPreference());
                if (p == null) {
                    tema = "blitzer";
                } else {
                    tema = p.getTema();
                }
            } else {
                tema = "redmond";
            }
            return tema;
        } catch (Exception ex) {
            return "blitzer";
        }
    }

    public Map<String, String> doGetThemes() {
        Map<String, String> themes = new TreeMap<>();
        themes.put("Aristo", "aristo");
        themes.put("Black-Tie", "black-tie");
        themes.put("Blitzer", "blitzer");
        themes.put("Bluesky", "bluesky");
        themes.put("Casablanca", "casablanca");
        themes.put("Cupertino", "cupertino");
        themes.put("Dark-Hive", "dark-hive");
        themes.put("Dot-Luv", "dot-luv");
        themes.put("Eggplant", "eggplant");
        themes.put("Excite-Bike", "excite-bike");
        themes.put("Flick", "flick");
        themes.put("Glass-X", "glass-x");
        themes.put("Hot-Sneaks", "hot-sneaks");
        themes.put("Humanity", "humanity");
        themes.put("Le-Frog", "le-frog");
        themes.put("Midnight", "midnight");
        themes.put("Mint-Choc", "mint-choc");
        themes.put("Overcast", "overcast");
        themes.put("Pepper-Grinder", "pepper-grinder");
        themes.put("Redmond", "redmond");
        themes.put("Rocket", "rocket");
        themes.put("Sam", "sam");
        themes.put("Smoothness", "smoothness");
        themes.put("South-Street", "south-street");
        themes.put("Start", "start");
        themes.put("Sunny", "sunny");
        themes.put("Swanky-Purse", "swanky-purse");
        themes.put("Trontastic", "trontastic");
        themes.put("UI-Darkness", "ui-darkness");
        themes.put("UI-Lightness", "ui-lightness");
        themes.put("Vader", "vader");
        return themes;
    }

    public Map<String, String> doGetSizes() {
        Map<String, String> sizes = new TreeMap<>();
        sizes.put("-50%", "90%");
        sizes.put("-60%", "92%");
        sizes.put("-70%", "94%");
        sizes.put("-80%", "96%");
        sizes.put("-90%", "98%");
        sizes.put("Normal", "100%");
        sizes.put("+20%", "102%");
        sizes.put("+40%", "104%");
        sizes.put("+60%", "106%");
        sizes.put("+80%", "108%");
        sizes.put("+100%", "110%");
        return sizes;

    }

    public Map<String, String> doGetFonts() {
        Map<String, String> fonts = new TreeMap<>();
        fonts.put("Droid Sans (Recomendado)", "Droid Sans");
        fonts.put("Exo", "Exo");
        fonts.put("Times New Roman", "Times New Roman");
        fonts.put("Verdana", "Verdana");
        fonts.put("courier", "courier");
        fonts.put("monospace", "monospace");
        fonts.put("Megrim", "Megrim");
        return fonts;
    }

    public Map<String, String> doGetLanguages() {
        Map<String, String> langs = new TreeMap<>();
        langs.put("Español", "es");
        langs.put("English", "en");
        langs.put("Zulú", "zu");
        return langs;
    }
    //********************************************
    // METODOS DEL LISTENER
    //********************************************
}
