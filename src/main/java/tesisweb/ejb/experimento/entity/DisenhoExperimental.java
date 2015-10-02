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
@Table(name = "DISENHO_EXPERIMENTAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DisenhoExperimental.findAll", query = "SELECT d FROM DisenhoExperimental d")})
public class DisenhoExperimental implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_DISENHO")
    private Integer idDisenho;
    @Size(max = 255)
    @Column(name = "DESCRIPCION_DISENHO")
    private String descripcionDisenho;
    @Size(max = 255)
    @Column(name = "CONTEXTO")
    private String contexto;
    @Column(name = "ESTADO")
    private Boolean estado;
    @Size(max = 255)
    @Column(name = "INFORMACION_DISENHO")
    private String informacionDisenho;
    @OneToMany(mappedBy = "idDisenho")
    private List<GrupoMatrizExperimental> grupoMatrizExperimentalList;

    public DisenhoExperimental() {
    }

    public DisenhoExperimental(Integer idDisenho) {
        this.idDisenho = idDisenho;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getIdDisenho() {
        return idDisenho;
    }

    public void setIdDisenho(Integer idDisenho) {
        this.idDisenho = idDisenho;
    }

    public String getDescripcionDisenho() {
        return descripcionDisenho;
    }

    public void setDescripcionDisenho(String descripcionDisenho) {
        this.descripcionDisenho = descripcionDisenho;
    }

    public String getContexto() {
        return contexto;
    }

    public void setContexto(String contexto) {
        this.contexto = contexto;
    }

    public String getInformacionDisenho() {
        return informacionDisenho;
    }

    public void setInformacionDisenho(String informacionDisenho) {
        this.informacionDisenho = informacionDisenho;
    }

    @XmlTransient
    public List<GrupoMatrizExperimental> getGrupoMatrizExperimentalList() {
        return grupoMatrizExperimentalList;
    }

    public void setGrupoMatrizExperimentalList(List<GrupoMatrizExperimental> grupoMatrizExperimentalList) {
        this.grupoMatrizExperimentalList = grupoMatrizExperimentalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDisenho != null ? idDisenho.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DisenhoExperimental)) {
            return false;
        }
        DisenhoExperimental other = (DisenhoExperimental) object;
        if ((this.idDisenho == null && other.idDisenho != null) || (this.idDisenho != null && !this.idDisenho.equals(other.idDisenho))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.informacionDisenho;
    }

}
