/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.ejb.entity;

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

/**
 *
 * @author root
 */
@Entity
@Table(name = "CUESTIONARIO_FAMILIARIDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuestionarioFamiliaridad.findAll", query = "SELECT c FROM CuestionarioFamiliaridad c")})
public class CuestionarioFamiliaridad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CUESTIONARIO")
    private Integer idCuestionario;
    @Size(max = 255)
    @Column(name = "ALIAS")
    private String alias;
    @Size(max = 255)
    @Column(name = "SEXO")
    private String sexo;
    @Size(max = 255)
    @Column(name = "EDAD")
    private String edad;
    @Size(max = 255)
    @Column(name = "IDIOMA")
    private String idioma;
    @Size(max = 255)
    @Column(name = "ES_PROFESIONAL")
    private String esProfesional;
    @Size(max = 255)
    @Column(name = "PROFESION")
    private String profesion;
    @Size(max = 255)
    @Column(name = "DONDE_USAS_INTERNET")
    private String dondeUsasInternet;
    @Size(max = 255)
    @Column(name = "PORQUE_USAS_INTERNET")
    private String porqueUsasInternet;
    @Size(max = 255)
    @Column(name = "QUE_APP_USA")
    private String queAppUsa;
    @Size(max = 255)
    @Column(name = "COMPRAS_PERSONALES")
    private String comprasPersonales;
    @Size(max = 255)
    @Column(name = "COMPRAS_INTERMEDIARIO")
    private String comprasIntermediario;
    @Size(max = 255)
    @Column(name = "TIENDAS_PREFERIDAS")
    private String tiendasPreferidas;
    @Size(max = 255)
    @Column(name = "PROBLEMAS_PERCIBIDOS")
    private String problemasPercibidos;
    @Size(max = 255)
    @Column(name = "ES_ATRACTIVO_COMPRA")
    private String esAtractivoCompra;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne
    private Usuario idUsuario;

    public CuestionarioFamiliaridad() {
    }

    public CuestionarioFamiliaridad(Integer idCuestionario) {
        this.idCuestionario = idCuestionario;
    }

    public Integer getIdCuestionario() {
        return idCuestionario;
    }

    public void setIdCuestionario(Integer idCuestionario) {
        this.idCuestionario = idCuestionario;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getEsProfesional() {
        return esProfesional;
    }

    public void setEsProfesional(String esProfesional) {
        this.esProfesional = esProfesional;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getDondeUsasInternet() {
        return dondeUsasInternet;
    }

    public void setDondeUsasInternet(String dondeUsasInternet) {
        this.dondeUsasInternet = dondeUsasInternet;
    }

    public String getPorqueUsasInternet() {
        return porqueUsasInternet;
    }

    public void setPorqueUsasInternet(String porqueUsasInternet) {
        this.porqueUsasInternet = porqueUsasInternet;
    }

    public String getQueAppUsa() {
        return queAppUsa;
    }

    public void setQueAppUsa(String queAppUsa) {
        this.queAppUsa = queAppUsa;
    }

    public String getComprasPersonales() {
        return comprasPersonales;
    }

    public void setComprasPersonales(String comprasPersonales) {
        this.comprasPersonales = comprasPersonales;
    }

    public String getComprasIntermediario() {
        return comprasIntermediario;
    }

    public void setComprasIntermediario(String comprasIntermediario) {
        this.comprasIntermediario = comprasIntermediario;
    }

    public String getTiendasPreferidas() {
        return tiendasPreferidas;
    }

    public void setTiendasPreferidas(String tiendasPreferidas) {
        this.tiendasPreferidas = tiendasPreferidas;
    }

    public String getProblemasPercibidos() {
        return problemasPercibidos;
    }

    public void setProblemasPercibidos(String problemasPercibidos) {
        this.problemasPercibidos = problemasPercibidos;
    }

    public String getEsAtractivoCompra() {
        return esAtractivoCompra;
    }

    public void setEsAtractivoCompra(String esAtractivoCompra) {
        this.esAtractivoCompra = esAtractivoCompra;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuestionario != null ? idCuestionario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuestionarioFamiliaridad)) {
            return false;
        }
        CuestionarioFamiliaridad other = (CuestionarioFamiliaridad) object;
        if ((this.idCuestionario == null && other.idCuestionario != null) || (this.idCuestionario != null && !this.idCuestionario.equals(other.idCuestionario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.CuestionarioFamiliariodad[ idCuestionario=" + idCuestionario + " ]";
    }
    
}
