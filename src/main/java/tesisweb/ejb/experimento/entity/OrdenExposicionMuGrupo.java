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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "ORDEN_EXPOSICION_MU_GRUPO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenExposicionMuGrupo.findAll", query = "SELECT o FROM OrdenExposicionMuGrupo o")})
public class OrdenExposicionMuGrupo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ORDEN_EXPOSICION")
    private Integer idOrdenExposicion;
    @Column(name = "ESTADO")
    private Boolean estado;
    @Column(name = "ORDEN")
    private Integer orden;
    @JoinColumn(name = "ID_GRUPO", referencedColumnName = "ID_GRUPO")
    @ManyToOne
    private GrupoMatrizExperimental idGrupo;
    @JoinColumn(name = "ID_MU", referencedColumnName = "ID_MU")
    @ManyToOne
    private MecanismoUsabilidad idMu;

    public OrdenExposicionMuGrupo() {
    }

    public OrdenExposicionMuGrupo(Integer idOrdenExposicion) {
        this.idOrdenExposicion = idOrdenExposicion;
    }

    public Integer getIdOrdenExposicion() {
        return idOrdenExposicion;
    }

    public void setIdOrdenExposicion(Integer idOrdenExposicion) {
        this.idOrdenExposicion = idOrdenExposicion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public GrupoMatrizExperimental getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(GrupoMatrizExperimental idGrupo) {
        this.idGrupo = idGrupo;
    }

    public MecanismoUsabilidad getIdMu() {
        return idMu;
    }

    public void setIdMu(MecanismoUsabilidad idMu) {
        this.idMu = idMu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrdenExposicion != null ? idOrdenExposicion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenExposicionMuGrupo)) {
            return false;
        }
        OrdenExposicionMuGrupo other = (OrdenExposicionMuGrupo) object;
        if ((this.idOrdenExposicion == null && other.idOrdenExposicion != null) || (this.idOrdenExposicion != null && !this.idOrdenExposicion.equals(other.idOrdenExposicion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tesisweb.ejb.experimento.entity.OrdenExposicionMuGrupo[ idOrdenExposicion=" + idOrdenExposicion + " ]";
    }
    
}
