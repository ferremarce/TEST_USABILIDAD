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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author root
 */
@Entity
@Table(name = "PREGUNTA_USABILIDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreguntaUsabilidad.findAll", query = "SELECT p FROM PreguntaUsabilidad p")})
public class PreguntaUsabilidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PREGUNTA_USABILIDAD")
    private Integer idPreguntaUsabilidad;
    @Lob
    @Column(name = "PREGUNTA_POSITIVO")
    private String preguntaPositivo;
    @Lob
    @Column(name = "PREGUNTA_NEGATIVO")
    private String preguntaNegativo;
    @OneToMany(mappedBy = "idPreguntaUsabilidad")
    private List<CuestionarioUsabilidad> cuestionarioUsabilidadList;
    @JoinColumn(name = "ID_MECANISMO_USABILIDAD", referencedColumnName = "ID_MU")
    @ManyToOne
    private MecanismoUsabilidad idMecanismoUsabilidad;
    @Column(name = "ES_ACTIVO")
    private Boolean esActivo;

    public PreguntaUsabilidad() {
    }

    public Boolean getEsActivo() {
        return esActivo;
    }

    public void setEsActivo(Boolean esActivo) {
        this.esActivo = esActivo;
    }

    public PreguntaUsabilidad(Integer idPreguntaUsabilidad) {
        this.idPreguntaUsabilidad = idPreguntaUsabilidad;
    }

    public Integer getIdPreguntaUsabilidad() {
        return idPreguntaUsabilidad;
    }

    public void setIdPreguntaUsabilidad(Integer idPreguntaUsabilidad) {
        this.idPreguntaUsabilidad = idPreguntaUsabilidad;
    }

    public String getPreguntaPositivo() {
        return preguntaPositivo;
    }

    public void setPreguntaPositivo(String preguntaPositivo) {
        this.preguntaPositivo = preguntaPositivo;
    }

    public String getPreguntaNegativo() {
        return preguntaNegativo;
    }

    public void setPreguntaNegativo(String preguntaNegativo) {
        this.preguntaNegativo = preguntaNegativo;
    }

    @XmlTransient
    public List<CuestionarioUsabilidad> getCuestionarioUsabilidadList() {
        return cuestionarioUsabilidadList;
    }

    public void setCuestionarioUsabilidadList(List<CuestionarioUsabilidad> cuestionarioUsabilidadList) {
        this.cuestionarioUsabilidadList = cuestionarioUsabilidadList;
    }

    public MecanismoUsabilidad getIdMecanismoUsabilidad() {
        return idMecanismoUsabilidad;
    }

    public void setIdMecanismoUsabilidad(MecanismoUsabilidad idMecanismoUsabilidad) {
        this.idMecanismoUsabilidad = idMecanismoUsabilidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPreguntaUsabilidad != null ? idPreguntaUsabilidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreguntaUsabilidad)) {
            return false;
        }
        PreguntaUsabilidad other = (PreguntaUsabilidad) object;
        if ((this.idPreguntaUsabilidad == null && other.idPreguntaUsabilidad != null) || (this.idPreguntaUsabilidad != null && !this.idPreguntaUsabilidad.equals(other.idPreguntaUsabilidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PreguntaUsabilidad[ idPreguntaUsabilidad=" + idPreguntaUsabilidad + " ]";
    }

}
