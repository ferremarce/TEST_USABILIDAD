/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tesisweb.ejb.tienda.entity.Preference;
import tesisweb.ejb.tienda.facade.PreferenceDAO;

/**
 *
 * @author jmferreira
 */
@WebServlet(name = "PreferenceCSS", urlPatterns = {"/PreferenceCSS"})
public class PreferenceCSS extends HttpServlet {

    @Inject
    PreferenceDAO preferenceDAO;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/css");
        PrintWriter out = response.getWriter();
        String parametro = request.getParameter("object");
        String cadena;
        System.out.println("Preference id: " + parametro);
        try {
            Preference p = preferenceDAO.find(Integer.parseInt(parametro));
            String size = p.getTamanho() + "%";
            String family = "'" + p.getFuente() + "'";

            cadena = "label, .texto-no-tachado"
                    + "{\n"
                    + "    font-size: " + size + " !important;\n"
                    + "    font-family: " + family + " !important; \n"
                    + "}\n"
                    + ".ui-widget,"
                    + ".ui-widget .ui-widget,"
                    + ".ui-datalist-item,"
                    + ".ui-panel-title "
                    + "{\n"
                    + "    font-size: " + size + " !important;\n"
                    + "    font-family: " + family + " !important; \n"
                    //+ "     background-color: #c2c2c2 ; \n"
                    + "}";

//            cadena = "body, h1, h2, h3, span, .ui-layout \n"
//                    + "{\n"
//                    + "    font-family: " + family + " !important; \n"
//                    + "}\n"
//                    + "table, td, tr \n"
//                    + "{\n"
//                    + "    font-size: " + size + " !important;\n"
//                    + "    font-family: " + family + " !important; \n"
//                    + "}\n"
//                    + ".ui-widget,\n"
//                    + ".ui-widget-header,\n"
//                    + ".ui-widget-content,\n"
//                    + ".ui-datatable ui-widget,\n"
//                    + ".ui-datatable-data ui-widget-content,\n"
//                    + ".ui-column-title,\n"
//                    + ".ui-toolbar,\n"
//                    + ".ui-menuitem,\n"
//                    + ".ui-menuitem-text,\n"
//                    + ".ui-menu-list,\n"
//                    + ".ui-c,\n"
//                    + ".ui-selectonemenu-label,\n"
//                    + ".ui-selectonemenu-item\n"
//                    + ".ui-state-default\n"
//                    + ".ui-datatable-tablewrapper\n"
//                    + ".ui-carousel-header\n"
//                    + ".ui-corner-all\n"
//                    + "{\n"
//                    + "    font-size: " + size + " !important;\n"
//                    + "    font-family: " + family + " !important; \n"
//                    //+ "     background-color: #c2c2c2 ; \n"
//                    + "}";
//                    
            out.println(cadena);
        } catch (Exception ex) {
//            cadena = ".ui-widget,\n"
//                    + ".ui-widget-header,\n"
//                    + ".ui-widget-content,\n"
//                    + ".ui-datatable ui-widget,\n"
//                    + ".ui-datatable-data ui-widget-content,\n"
//                    + ".ui-column-title,\n"
//                    + ".ui-toolbar\n"
//                    + "{\n"
//                    + "    font-size: 1em !important;\n"
//                    + "}";
            String size="100%";
            cadena = "label, .texto-no-tachado"
                    + "{\n"
                    + "    font-size: " + size + " !important;\n"
                    + "}\n"
                    + ".ui-widget,"
                    + ".ui-widget .ui-widget,"
                    + ".ui-datalist-item,"
                    + ".ui-panel-title "
                    + "{\n"
                    + "    font-size: " + size + " !important;\n"
                    //+ "     background-color: #c2c2c2 ; \n"
                    + "}";
            out.println(cadena);
        }
        out.close();
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
