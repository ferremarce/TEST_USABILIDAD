/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.controller.frontend;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.inject.Inject;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import tesisweb.ejb.entity.Articulo;
import tesisweb.ejb.entity.Carrito;
import tesisweb.ejb.entity.Cliente;
import tesisweb.ejb.entity.Direccion;
import tesisweb.ejb.entity.DireccionCliente;
import tesisweb.ejb.entity.MetodoPagoCliente;
import tesisweb.ejb.entity.OrdenCarrito;
import tesisweb.ejb.entity.TarjetaCredito;
import tesisweb.ejb.facade.CarritoDAO;
import tesisweb.ejb.facade.ClienteDAO;
import tesisweb.ejb.facade.DireccionClienteDAO;
import tesisweb.ejb.facade.DireccionDAO;
import tesisweb.ejb.facade.MetodoPagoClienteDAO;
import tesisweb.ejb.facade.TarjetaCreditoDAO;
import tesisweb.util.JSFutil;

/**
 *
 * @author root
 */
@Named(value = "CarritoFE")
@SessionScoped
public class CarritoFE implements Serializable {

    /**
     * Configuraciones varias para Log y Bundle*
     */
    private static final Logger LOG = Logger.getLogger(CarritoFE.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("tesisweb.properties.bundle", JSFutil.getmyLocale());

    @Inject
    private CarritoDAO carritoDAO;
    @Inject
    private MetodoPagoClienteDAO metodoPagoClienteDAO;
    @Inject
    private TarjetaCreditoDAO tarjetaCreditoDAO;
    @Inject
    private DireccionClienteDAO direccionClienteDAO;
    @Inject
    private DireccionDAO direccionDAO;
    @Inject
    private ClienteDAO clienteDAO;
    private Carrito carrito;
    private List<OrdenCarrito> listaOrdenCarrito;
    private Boolean aceptaPolitica;
    private DireccionCliente direccionSeleccionada;
    private MetodoPagoCliente metodoPagoClienteSeleccionado;
    private TarjetaCredito tarjetaCredito;
    private Cliente cliente;
    private Direccion direccion;
    private Boolean estoyDeAcuerdo;

    /**
     * Creates a new instance of CarritoController
     */
    public CarritoFE() {
    }
    //********************************************
    // SETTERS Y GETTERS
    //********************************************

    public List<OrdenCarrito> getListaOrdenCarrito() {
        return listaOrdenCarrito;
    }

    public void setListaOrdenCarrito(List<OrdenCarrito> listaOrdenCarrito) {
        this.listaOrdenCarrito = listaOrdenCarrito;
    }

    public Boolean getAceptaPolitica() {
        return aceptaPolitica;
    }

    public void setAceptaPolitica(Boolean aceptaPolitica) {
        this.aceptaPolitica = aceptaPolitica;
    }

    public DireccionCliente getDireccionSeleccionada() {
        return direccionSeleccionada;
    }

    public void setDireccionSeleccionada(DireccionCliente direccionSeleccionada) {
        this.direccionSeleccionada = direccionSeleccionada;
    }

    public TarjetaCredito getTarjetaCredito() {
        return tarjetaCredito;
    }

    public void setTarjetaCredito(TarjetaCredito tarjetaCredito) {
        this.tarjetaCredito = tarjetaCredito;
    }

    public MetodoPagoCliente getMetodoPagoClienteSeleccionado() {
        return metodoPagoClienteSeleccionado;
    }

    public void setMetodoPagoClienteSeleccionado(MetodoPagoCliente metodoPagoClienteSeleccionado) {
        this.metodoPagoClienteSeleccionado = metodoPagoClienteSeleccionado;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Boolean getEstoyDeAcuerdo() {
        return estoyDeAcuerdo;
    }

    public void setEstoyDeAcuerdo(Boolean estoyDeAcuerdo) {
        this.estoyDeAcuerdo = estoyDeAcuerdo;
    }

    //********************************************
    // METODOS DE ACCIÓN
    //********************************************
    public void doAgregarCarrito(Articulo a) {
        OrdenCarrito ocarrito = new OrdenCarrito();
        ocarrito.setCantidad(1);
        ocarrito.setIdArticulo(a);
        Boolean existe = Boolean.FALSE;
        for (int i = 0; i < this.listaOrdenCarrito.size(); i++) {

            if (this.listaOrdenCarrito.get(i).getIdArticulo().getIdArticulo().compareTo(a.getIdArticulo()) == 0) {
                this.listaOrdenCarrito.get(i).setCantidad(this.listaOrdenCarrito.get(i).getCantidad() + 1);
                existe = Boolean.TRUE;
            }
        }
        if (!existe) {
            this.listaOrdenCarrito.add(ocarrito);
        }
        JSFutil.addSuccessMessage("Artículo agregado exitosamente al Carrito");
    }

    public String doProcesarCarritoFrom() {
        this.aceptaPolitica = null;
        return "/frontend/carrito/ProcesarCarrito";
    }

    public String doProcesarDatosPago() {
        if (this.direccionSeleccionada == null) {
            JSFutil.addErrorMessage("Debe seleccionar una dirección para el proceso de envío");
            return "";
        }
        this.tarjetaCredito = new TarjetaCredito();
        return "/frontend/carrito/ProcesarDatosPago";
    }

    @PostConstruct
    public void init() {
        if (this.listaOrdenCarrito == null) {
            this.listaOrdenCarrito = new ArrayList<>();
        }
    }

    public void doCrearMetodoPagoForm() {
        this.metodoPagoClienteSeleccionado = new MetodoPagoCliente();
    }

    public String doProcesarResumenForm() {
        if (this.metodoPagoClienteSeleccionado == null) {
            JSFutil.addErrorMessage("Debe seleccionar un método de pago");
            return "";
        }
        return "/frontend/carrito/ProcesarResumen";
    }

    public void doCrearDireccionForm() {
        this.direccion = new Direccion();
    }

    public String doCancelarCarrito() {
        this.direccionSeleccionada = null;
        this.metodoPagoClienteSeleccionado = null;
        return "/frontend/index2";
    }

    //********************************************
    // METODOS DEL LISTENER
    //********************************************
    public Float calcularTotal() {
        Float total = Float.valueOf("0");
        for (OrdenCarrito oc : this.listaOrdenCarrito) {
            total += oc.getCantidad() * this.calcularPrecioFinal(oc.getIdArticulo());
        }
        return total;
    }

    public Float calcularPrecioFinal(Articulo a) {
        if (a.getEsPromocion()) {
            return a.getPrecio() - a.getPrecio() * a.getPorcentajeDescuento() / 100;
        } else {
            return a.getPrecio();
        }
    }

    public void doSacarArticuloOrdenCompra(OrdenCarrito orden) {
        boolean remove = this.listaOrdenCarrito.remove(orden);
        if (remove) {
            JSFutil.addSuccessMessage(JSFutil.getMyBundle().getString("UpdateSuccess"));
        } else {
            JSFutil.addSuccessMessage(JSFutil.getMyBundle().getString("UpdateError"));
        }
    }

    public String onFlowProcess(FlowEvent event) {
        switch (event.getOldStep().toLowerCase()) {
            case "carrito":
                if (this.listaOrdenCarrito.size() > 0) {
                    return event.getNewStep();
                } else {
                    JSFutil.addErrorMessage("El Carrito está vacío");
                    return "carrito";
                }
            case "entrega":
                if (this.aceptaPolitica.compareTo(Boolean.FALSE) == 0 && event.getNewStep().toLowerCase().compareTo("pago") == 0) {
                    JSFutil.addErrorMessage("Debe aceptar las políticas de privacidad");
                    return "entrega";
                } else {
                    return event.getNewStep();
                }
            case "pago":
                return event.getNewStep();
            default:
                JSFutil.addErrorMessage("El Asistente no se ha inicializado correctamente");
                return "";
        }
    }

    public void onRowSelect(SelectEvent event) {
        this.direccionSeleccionada = (DireccionCliente) event.getObject();
        System.out.println("Seleccionado: " + this.direccionSeleccionada.getIdDireccion().getCalle());
    }

    public void onRowSelectTarjeta(SelectEvent event) {
        this.metodoPagoClienteSeleccionado = (MetodoPagoCliente) event.getObject();
        System.out.println("Seleccionado: " + this.metodoPagoClienteSeleccionado.getIdTarjetaCredito().getNumeroTarjeta());
    }

    public String verificarCorreoExistente() {
        if (clienteDAO.getCliente(this.cliente.getEmail()) != null) {
            String msg = " El mail ya ha sido registrado";
            return msg;
        } else {
            return "";
        }
    }

}
