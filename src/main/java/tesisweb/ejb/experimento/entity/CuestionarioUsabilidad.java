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
@Table(name = "CUESTIONARIO_USABILIDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuestionarioUsabilidad.findAll", query = "SELECT c FROM CuestionarioUsabilidad c")})
public class CuestionarioUsabilidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CUESTIONARIO_USABILIDAD")
    private Integer idCuestionarioUsabilidad;
    @Column(name = "VALOR_PREGUNTA")
    private Integer valorPregunta;
    @Size(max = 1000)
    @Column(name = "COMENTARIO")
    private String comentario;
    @JoinColumn(name = "ID_PREGUNTA_USABILIDAD", referencedColumnName = "ID_PREGUNTA_USABILIDAD")
    @ManyToOne
    private PreguntaUsabilidad idPreguntaUsabilidad;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne
    private Usuario idUsuario;

    public CuestionarioUsabilidad() {
    }

    public CuestionarioUsabilidad(Integer idCuestionarioUsabilidad) {
        this.idCuestionarioUsabilidad = idCuestionarioUsabilidad;
    }

    public Integer getIdCuestionarioUsabilidad() {
        return idCuestionarioUsabilidad;
    }

    public void setIdCuestionarioUsabilidad(Integer idCuestionarioUsabilidad) {
        this.idCuestionarioUsabilidad = idCuestionarioUsabilidad;
    }

    public Integer getValorPregunta() {
        return valorPregunta;
    }

    public void setValorPregunta(Integer valorPregunta) {
        this.valorPregunta = valorPregunta;
    }

    public PreguntaUsabilidad getIdPreguntaUsabilidad() {
        return idPreguntaUsabilidad;
    }

    public void setIdPreguntaUsabilidad(PreguntaUsabilidad idPreguntaUsabilidad) {
        this.idPreguntaUsabilidad = idPreguntaUsabilidad;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuestionarioUsabilidad != null ? idCuestionarioUsabilidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuestionarioUsabilidad)) {
            return false;
        }
        CuestionarioUsabilidad other = (CuestionarioUsabilidad) object;
        if ((this.idCuestionarioUsabilidad == null && other.idCuestionarioUsabilidad != null) || (this.idCuestionarioUsabilidad != null && !this.idCuestionarioUsabilidad.equals(other.idCuestionarioUsabilidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.CuestionarioUsabilidad[ idCuestionarioUsabilidad=" + idCuestionarioUsabilidad + " ]";
    }

}
