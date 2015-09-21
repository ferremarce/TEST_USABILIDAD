/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.ejb.experimento.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author root
 */
@Entity
@Table(name = "MECANISMO_USABILIDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MecanismoUsabilidad.findAll", query = "SELECT m FROM MecanismoUsabilidad m")})
public class MecanismoUsabilidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_MU")
    private Integer idMu;
    @Size(max = 255)
    @Column(name = "NOMBRE_MECANISMO")
    private String nombreMecanismo;
    @Lob
    @Column(name = "INFORMACION_ADICIONAL")
    private byte[] informacionAdicional;
    @OneToMany(mappedBy = "idMu")
    private List<OrdenExposicionMuGrupo> OrdenExposicionMuGrupoList;

    public MecanismoUsabilidad() {
    }

    public MecanismoUsabilidad(Integer idMu) {
        this.idMu = idMu;
    }

    public Integer getIdMu() {
        return idMu;
    }

    public void setIdMu(Integer idMu) {
        this.idMu = idMu;
    }

    public String getNombreMecanismo() {
        return nombreMecanismo;
    }

    public void setNombreMecanismo(String nombreMecanismo) {
        this.nombreMecanismo = nombreMecanismo;
    }

    public byte[] getInformacionAdicional() {
        return informacionAdicional;
    }

    public void setInformacionAdicional(byte[] informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    @XmlTransient
    public List<OrdenExposicionMuGrupo> getOrdenExposicionMuGrupoList() {
        return OrdenExposicionMuGrupoList;
    }

    public void setOrdenExposicionMuGrupoList(List<OrdenExposicionMuGrupo> OrdenExposicionMuGrupoList) {
        this.OrdenExposicionMuGrupoList = OrdenExposicionMuGrupoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMu != null ? idMu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MecanismoUsabilidad)) {
            return false;
        }
        MecanismoUsabilidad other = (MecanismoUsabilidad) object;
        if ((this.idMu == null && other.idMu != null) || (this.idMu != null && !this.idMu.equals(other.idMu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombreMecanismo;
    }
    
}
