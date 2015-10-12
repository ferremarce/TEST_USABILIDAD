/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.ejb.experimento.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import tesisweb.ejb.tienda.entity.Usuario;

/**
 *
 * @author root
 */
@Entity
@Table(name = "METRICA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Metrica.findAll", query = "SELECT m FROM Metrica m")})
public class Metrica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_METRICA")
    private Integer idMetrica;
    @Column(name = "START_TIME")
    private long startTime;
    @Size(max = 3000)
    @Column(name = "PARTIAL_TIME")
    private String partialTime;
     @Size(max = 255)
    @Column(name = "INFORMACION")
    private String informacion;
    @Column(name = "STOP_TIME")
    private long stopTime;
    @Column(name = "CLICK_COUNTER")
    private Integer clickCounter;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne
    private Usuario idUsuario;
    @JoinColumn(name = "ID_MECANISMO_USABILIDAD", referencedColumnName = "ID_MU")
    @ManyToOne
    private MecanismoUsabilidad idMecanismoUsabilidad;

    public Metrica() {
    }

    public Metrica(Integer idMetrica) {
        this.idMetrica = idMetrica;
    }

    public Integer getIdMetrica() {
        return idMetrica;
    }

    public void setIdMetrica(Integer idMetrica) {
        this.idMetrica = idMetrica;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    public String getPartialTime() {
        return partialTime;
    }

    public void setPartialTime(String partialTime) {
        this.partialTime = partialTime;
    }

    public Integer getClickCounter() {
        return clickCounter;
    }

    public void setClickCounter(Integer clickCounter) {
        this.clickCounter = clickCounter;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public MecanismoUsabilidad getIdMecanismoUsabilidad() {
        return idMecanismoUsabilidad;
    }

    public void setIdMecanismoUsabilidad(MecanismoUsabilidad idMecanismoUsabilidad) {
        this.idMecanismoUsabilidad = idMecanismoUsabilidad;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMetrica != null ? idMetrica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Metrica)) {
            return false;
        }
        Metrica other = (Metrica) object;
        if ((this.idMetrica == null && other.idMetrica != null) || (this.idMetrica != null && !this.idMetrica.equals(other.idMetrica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Metrica[ idMetrica=" + idMetrica + " ]";
    }

}
