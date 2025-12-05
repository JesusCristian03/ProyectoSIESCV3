/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import modelo.Estudiante;
import modelo.Materia;
import modelo.PeriodoEscolar;

/**
 *
 * @author cris_
 */
@Entity
@Table(name = "autorizacion_inscripcion", catalog = "scit", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AutorizacionInscripcion.findAll", query = "SELECT a FROM AutorizacionInscripcion a"),
    @NamedQuery(name = "AutorizacionInscripcion.findByTipoAutorizacion", query = "SELECT a FROM AutorizacionInscripcion a WHERE a.tipoAutorizacion = :tipoAutorizacion"),
    @NamedQuery(name = "AutorizacionInscripcion.findByMotivoAutorizacion", query = "SELECT a FROM AutorizacionInscripcion a WHERE a.motivoAutorizacion = :motivoAutorizacion"),
    @NamedQuery(name = "AutorizacionInscripcion.findByQuienAutoriza", query = "SELECT a FROM AutorizacionInscripcion a WHERE a.quienAutoriza = :quienAutoriza"),
    @NamedQuery(name = "AutorizacionInscripcion.findByFechaHoraAutoriza", query = "SELECT a FROM AutorizacionInscripcion a WHERE a.fechaHoraAutoriza = :fechaHoraAutoriza"),
    @NamedQuery(name = "AutorizacionInscripcion.findByCantidad", query = "SELECT a FROM AutorizacionInscripcion a WHERE a.cantidad = :cantidad"),
    @NamedQuery(name = "AutorizacionInscripcion.findByIdAutorizacion", query = "SELECT a FROM AutorizacionInscripcion a WHERE a.idAutorizacion = :idAutorizacion")})
public class AutorizacionInscripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "tipo_autorizacion")
    private String tipoAutorizacion;
    @Column(name = "motivo_autorizacion")
    private String motivoAutorizacion;
    @Column(name = "quien_autoriza")
    private String quienAutoriza;
    @Column(name = "fecha_hora_autoriza")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraAutoriza;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autorizacion")
    private Integer idAutorizacion;
    @JoinColumn(name = "no_de_control", referencedColumnName = "no_de_control")
    @ManyToOne(optional = false)
    private Estudiante noDeControl;
    @JoinColumn(name = "materia_afectada", referencedColumnName = "materia")
    @ManyToOne
    private Materia materiaAfectada;
    @JoinColumn(name = "periodo", referencedColumnName = "periodo")
    @ManyToOne(optional = false)
    private PeriodoEscolar periodo;

    public AutorizacionInscripcion() {
    }

    public AutorizacionInscripcion(Integer idAutorizacion) {
        this.idAutorizacion = idAutorizacion;
    }

    public AutorizacionInscripcion(Integer idAutorizacion, String tipoAutorizacion) {
        this.idAutorizacion = idAutorizacion;
        this.tipoAutorizacion = tipoAutorizacion;
    }

    public String getTipoAutorizacion() {
        return tipoAutorizacion;
    }

    public void setTipoAutorizacion(String tipoAutorizacion) {
        this.tipoAutorizacion = tipoAutorizacion;
    }

    public String getMotivoAutorizacion() {
        return motivoAutorizacion;
    }

    public void setMotivoAutorizacion(String motivoAutorizacion) {
        this.motivoAutorizacion = motivoAutorizacion;
    }

    public String getQuienAutoriza() {
        return quienAutoriza;
    }

    public void setQuienAutoriza(String quienAutoriza) {
        this.quienAutoriza = quienAutoriza;
    }

    public Date getFechaHoraAutoriza() {
        return fechaHoraAutoriza;
    }

    public void setFechaHoraAutoriza(Date fechaHoraAutoriza) {
        this.fechaHoraAutoriza = fechaHoraAutoriza;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIdAutorizacion() {
        return idAutorizacion;
    }

    public void setIdAutorizacion(Integer idAutorizacion) {
        this.idAutorizacion = idAutorizacion;
    }

    public Estudiante getNoDeControl() {
        return noDeControl;
    }

    public void setNoDeControl(Estudiante noDeControl) {
        this.noDeControl = noDeControl;
    }

    public Materia getMateriaAfectada() {
        return materiaAfectada;
    }

    public void setMateriaAfectada(Materia materiaAfectada) {
        this.materiaAfectada = materiaAfectada;
    }

    public PeriodoEscolar getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoEscolar periodo) {
        this.periodo = periodo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAutorizacion != null ? idAutorizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AutorizacionInscripcion)) {
            return false;
        }
        AutorizacionInscripcion other = (AutorizacionInscripcion) object;
        if ((this.idAutorizacion == null && other.idAutorizacion != null) || (this.idAutorizacion != null && !this.idAutorizacion.equals(other.idAutorizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAO.AutorizacionInscripcion[ idAutorizacion=" + idAutorizacion + " ]";
    }

}
