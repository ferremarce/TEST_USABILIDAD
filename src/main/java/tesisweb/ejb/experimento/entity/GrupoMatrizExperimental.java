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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import tesisweb.ejb.tienda.entity.Usuario;

/**
 *
 * @author root
 */
@Entity
@Table(name = "GRUPO_MATRIZ_EXPERIMENTAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoMatrizExperimental.findAll", query = "SELECT g FROM GrupoMatrizExperimental g")})
public class GrupoMatrizExperimental implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_GRUPO")
    private Integer idGrupo;
    @Size(max = 255)
    @Column(name = "NOMBRE_GRUPO")
    private String nombreGrupo;
    @Column(name = "CANTIDAD_PARTICIPANTE")
    private Integer cantidadParticipante;
    @OneToMany(mappedBy = "idGrupo")
    @OrderBy("orden ASC")
    private List<OrdenExposicionMuGrupo> OrdenExposicionMuGrupoList;
    @JoinColumn(name = "ID_DISENHO", referencedColumnName = "ID_DISENHO")
    @ManyToOne
    private DisenhoExperimental idDisenho;
    @OneToMany(mappedBy = "idGrupoExperimental")
    private List<Usuario> usuarioList;

    public GrupoMatrizExperimental() {
    }

    public GrupoMatrizExperimental(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public Integer getCantidadParticipante() {
        return cantidadParticipante;
    }

    public void setCantidadParticipante(Integer cantidadParticipante) {
        this.cantidadParticipante = cantidadParticipante;
    }

    @XmlTransient
    public List<OrdenExposicionMuGrupo> getOrdenExposicionMuGrupoList() {
        return OrdenExposicionMuGrupoList;
    }

    public void setOrdenExposicionMuGrupoList(List<OrdenExposicionMuGrupo> OrdenExposicionMuGrupoList) {
        this.OrdenExposicionMuGrupoList = OrdenExposicionMuGrupoList;
    }

    public DisenhoExperimental getIdDisenho() {
        return idDisenho;
    }

    public void setIdDisenho(DisenhoExperimental idDisenho) {
        this.idDisenho = idDisenho;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrupo != null ? idGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoMatrizExperimental)) {
            return false;
        }
        GrupoMatrizExperimental other = (GrupoMatrizExperimental) object;
        if ((this.idGrupo == null && other.idGrupo != null) || (this.idGrupo != null && !this.idGrupo.equals(other.idGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombreGrupo;
    }

}
