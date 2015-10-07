/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesisweb.ejb.tienda.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import tesisweb.ejb.experimento.entity.CuestionarioUsabilidad;
import tesisweb.ejb.experimento.entity.GrupoMatrizExperimental;
import tesisweb.ejb.experimento.entity.Metrica;

/**
 *
 * @author jmferreira
 */
@Entity
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Integer idUsuario;
    @Column(name = "ACTIVO")
    private Boolean esActivo;
    @Size(max = 255)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Size(max = 255)
    @Column(name = "CONTRASENHA")
    private String contrasenha;
    @Size(max = 255)
    @Column(name = "CUENTA")
    private String cuenta;
    @Size(max = 255)
    @Column(name = "NOMBRES")
    private String nombres;
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
    @ManyToOne
    private Rol idRol;
    @JoinColumn(name = "ID_PREFERENCE", referencedColumnName = "ID_PREFERENCE")
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Preference idPreference;
    @JoinColumn(name = "ID_SUB_ROL", referencedColumnName = "ID_SUB_TIPO")
    @ManyToOne
    private SubTipo idSubRol;
    @Column(name = "LOGIN_EXTERNO")
    private Boolean loginExterno;
    @JoinColumn(name = "ID_DATOS_USUARIO", referencedColumnName = "ID_DATOS_USUARIO")
    @ManyToOne
    private DatosUsuario idDatosUsuario;
    @OneToMany(mappedBy = "idUsuario", cascade = CascadeType.REMOVE)
    private List<CuestionarioFamiliaridad> cuestionarioFamiliaridadList;
    @JoinColumn(name = "ID_GRUPO_EXPERIMENTAL", referencedColumnName = "ID_GRUPO")
    @ManyToOne
    private GrupoMatrizExperimental idGrupoExperimental;
    @OneToMany(mappedBy = "idUsuario", cascade = CascadeType.REMOVE)
    private List<CuestionarioUsabilidad> cuestionarioUsabilidadList;
    @Column(name = "FECHA_HORA_CONEXION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraConexion;
    @Size(max = 255)
    @Column(name = "IP_CONEXION")
    private String ipConexion;
    @OneToMany(mappedBy = "idUsuario", cascade = CascadeType.REMOVE)
    private List<Metrica> metricaList;

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Boolean getEsActivo() {
        return esActivo;
    }

    public void setEsActivo(Boolean esActivo) {
        this.esActivo = esActivo;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    public Preference getIdPreference() {
        return idPreference;
    }

    public void setIdPreference(Preference idPreference) {
        this.idPreference = idPreference;
    }

    public SubTipo getIdSubRol() {
        return idSubRol;
    }

    public void setIdSubRol(SubTipo idSubRol) {
        this.idSubRol = idSubRol;
    }

    public Boolean getLoginExterno() {
        return loginExterno;
    }

    public void setLoginExterno(Boolean loginExterno) {
        this.loginExterno = loginExterno;
    }

    public DatosUsuario getIdDatosUsuario() {
        return idDatosUsuario;
    }

    public void setIdDatosUsuario(DatosUsuario idDatosUsuario) {
        this.idDatosUsuario = idDatosUsuario;
    }

    public List<CuestionarioFamiliaridad> getCuestionarioFamiliaridadList() {
        return cuestionarioFamiliaridadList;
    }

    public void setCuestionarioFamiliaridadList(List<CuestionarioFamiliaridad> cuestionarioFamiliaridadList) {
        this.cuestionarioFamiliaridadList = cuestionarioFamiliaridadList;
    }

    public GrupoMatrizExperimental getIdGrupoExperimental() {
        return idGrupoExperimental;
    }

    public void setIdGrupoExperimental(GrupoMatrizExperimental idGrupoExperimental) {
        this.idGrupoExperimental = idGrupoExperimental;
    }

    public List<CuestionarioUsabilidad> getCuestionarioUsabilidadList() {
        return cuestionarioUsabilidadList;
    }

    public void setCuestionarioUsabilidadList(List<CuestionarioUsabilidad> cuestionarioUsabilidadList) {
        this.cuestionarioUsabilidadList = cuestionarioUsabilidadList;
    }

    public Date getFechaHoraConexion() {
        return fechaHoraConexion;
    }

    public void setFechaHoraConexion(Date fechaHoraConexion) {
        this.fechaHoraConexion = fechaHoraConexion;
    }

    public String getIpConexion() {
        return ipConexion;
    }

    public void setIpConexion(String ipConexion) {
        this.ipConexion = ipConexion;
    }

    public List<Metrica> getMetricaList() {
        return metricaList;
    }

    public void setMetricaList(List<Metrica> metricaList) {
        this.metricaList = metricaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        return !((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario)));
    }

    @Override
    public String toString() {
        return this.getNombres() + "-" + this.getApellidos();
    }
}
