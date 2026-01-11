/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

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


@Entity
@Table(name = "alumnos_generales", catalog = "scit", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlumnosGenerales.findAll", query = "SELECT a FROM AlumnosGenerales a"),
    @NamedQuery(name = "AlumnosGenerales.findById", query = "SELECT a FROM AlumnosGenerales a WHERE a.id = :id"),
    @NamedQuery(name = "AlumnosGenerales.findByLugarNacimiento", query = "SELECT a FROM AlumnosGenerales a WHERE a.lugarNacimiento = :lugarNacimiento"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioCalle", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioCalle = :domicilioCalle"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioColonia", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioColonia = :domicilioColonia"),
    @NamedQuery(name = "AlumnosGenerales.findByCiudad", query = "SELECT a FROM AlumnosGenerales a WHERE a.ciudad = :ciudad"),
    @NamedQuery(name = "AlumnosGenerales.findByCodigoPostal", query = "SELECT a FROM AlumnosGenerales a WHERE a.codigoPostal = :codigoPostal"),
    @NamedQuery(name = "AlumnosGenerales.findByTelefono", query = "SELECT a FROM AlumnosGenerales a WHERE a.telefono = :telefono"),
    @NamedQuery(name = "AlumnosGenerales.findByNombrePadre", query = "SELECT a FROM AlumnosGenerales a WHERE a.nombrePadre = :nombrePadre"),
    @NamedQuery(name = "AlumnosGenerales.findByOcupacionPadre", query = "SELECT a FROM AlumnosGenerales a WHERE a.ocupacionPadre = :ocupacionPadre"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioCallePadre", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioCallePadre = :domicilioCallePadre"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioColoniaPadre", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioColoniaPadre = :domicilioColoniaPadre"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioCiudadPadre", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioCiudadPadre = :domicilioCiudadPadre"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioTelefonoPadre", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioTelefonoPadre = :domicilioTelefonoPadre"),
    @NamedQuery(name = "AlumnosGenerales.findByNombreMadre", query = "SELECT a FROM AlumnosGenerales a WHERE a.nombreMadre = :nombreMadre"),
    @NamedQuery(name = "AlumnosGenerales.findByOcupacionMadre", query = "SELECT a FROM AlumnosGenerales a WHERE a.ocupacionMadre = :ocupacionMadre"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioCalleMadre", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioCalleMadre = :domicilioCalleMadre"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioColoniaMadre", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioColoniaMadre = :domicilioColoniaMadre"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioCiudadMadre", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioCiudadMadre = :domicilioCiudadMadre"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioTelefonoMadre", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioTelefonoMadre = :domicilioTelefonoMadre"),
    @NamedQuery(name = "AlumnosGenerales.findByNombreEmpresa", query = "SELECT a FROM AlumnosGenerales a WHERE a.nombreEmpresa = :nombreEmpresa"),
    @NamedQuery(name = "AlumnosGenerales.findByCargoDesempenado", query = "SELECT a FROM AlumnosGenerales a WHERE a.cargoDesempenado = :cargoDesempenado"),
    @NamedQuery(name = "AlumnosGenerales.findByIngresoMensual", query = "SELECT a FROM AlumnosGenerales a WHERE a.ingresoMensual = :ingresoMensual"),
    @NamedQuery(name = "AlumnosGenerales.findByTurno", query = "SELECT a FROM AlumnosGenerales a WHERE a.turno = :turno"),
    @NamedQuery(name = "AlumnosGenerales.findByAntiguedad", query = "SELECT a FROM AlumnosGenerales a WHERE a.antiguedad = :antiguedad"),
    @NamedQuery(name = "AlumnosGenerales.findByNombreJefe", query = "SELECT a FROM AlumnosGenerales a WHERE a.nombreJefe = :nombreJefe"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioCalleEmpresa", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioCalleEmpresa = :domicilioCalleEmpresa"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioColoniaEmpresa", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioColoniaEmpresa = :domicilioColoniaEmpresa"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioCiudadEmpresa", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioCiudadEmpresa = :domicilioCiudadEmpresa"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioTelefonoEmpresa", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioTelefonoEmpresa = :domicilioTelefonoEmpresa"),
    @NamedQuery(name = "AlumnosGenerales.findByNombreEsposa", query = "SELECT a FROM AlumnosGenerales a WHERE a.nombreEsposa = :nombreEsposa"),
    @NamedQuery(name = "AlumnosGenerales.findByOcupacionEsposa", query = "SELECT a FROM AlumnosGenerales a WHERE a.ocupacionEsposa = :ocupacionEsposa"),
    @NamedQuery(name = "AlumnosGenerales.findByNoDependientes", query = "SELECT a FROM AlumnosGenerales a WHERE a.noDependientes = :noDependientes"),
    @NamedQuery(name = "AlumnosGenerales.findByComunidadIndigena", query = "SELECT a FROM AlumnosGenerales a WHERE a.comunidadIndigena = :comunidadIndigena"),
    @NamedQuery(name = "AlumnosGenerales.findByMunicipioNac", query = "SELECT a FROM AlumnosGenerales a WHERE a.municipioNac = :municipioNac"),
    @NamedQuery(name = "AlumnosGenerales.findByMunicipioDom", query = "SELECT a FROM AlumnosGenerales a WHERE a.municipioDom = :municipioDom"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioCalleTutor", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioCalleTutor = :domicilioCalleTutor"),
    @NamedQuery(name = "AlumnosGenerales.findByNombreTutor", query = "SELECT a FROM AlumnosGenerales a WHERE a.nombreTutor = :nombreTutor"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioCiudadTutor", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioCiudadTutor = :domicilioCiudadTutor"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioColoniaTutor", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioColoniaTutor = :domicilioColoniaTutor"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioTelefonoTutor", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioTelefonoTutor = :domicilioTelefonoTutor"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilioEntidadFeredativaT", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilioEntidadFeredativaT = :domicilioEntidadFeredativaT"),
    @NamedQuery(name = "AlumnosGenerales.findByClavePreparatoria", query = "SELECT a FROM AlumnosGenerales a WHERE a.clavePreparatoria = :clavePreparatoria"),
    @NamedQuery(name = "AlumnosGenerales.findByMunicipio", query = "SELECT a FROM AlumnosGenerales a WHERE a.municipio = :municipio"),
    @NamedQuery(name = "AlumnosGenerales.findByTipoSangre", query = "SELECT a FROM AlumnosGenerales a WHERE a.tipoSangre = :tipoSangre"),
    @NamedQuery(name = "AlumnosGenerales.findByNss", query = "SELECT a FROM AlumnosGenerales a WHERE a.nss = :nss"),
    @NamedQuery(name = "AlumnosGenerales.findByDomicilio", query = "SELECT a FROM AlumnosGenerales a WHERE a.domicilio = :domicilio"),
    @NamedQuery(name = "AlumnosGenerales.findByTelefonoEmergencia", query = "SELECT a FROM AlumnosGenerales a WHERE a.telefonoEmergencia = :telefonoEmergencia"),
    @NamedQuery(name = "AlumnosGenerales.findByNombre", query = "SELECT a FROM AlumnosGenerales a WHERE a.nombre = :nombre")})
public class AlumnosGenerales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "lugar_nacimiento")
    private String lugarNacimiento;
    @Column(name = "domicilio_calle")
    private String domicilioCalle;
    @Column(name = "domicilio_colonia")
    private String domicilioColonia;
    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "codigo_postal")
    private Integer codigoPostal;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "nombre_padre")
    private String nombrePadre;
    @Column(name = "ocupacion_padre")
    private String ocupacionPadre;
    @Column(name = "domicilio_calle_padre")
    private String domicilioCallePadre;
    @Column(name = "domicilio_colonia_padre")
    private String domicilioColoniaPadre;
    @Column(name = "domicilio_ciudad_padre")
    private String domicilioCiudadPadre;
    @Column(name = "domicilio_telefono_padre")
    private String domicilioTelefonoPadre;
    @Column(name = "nombre_madre")
    private String nombreMadre;
    @Column(name = "ocupacion_madre")
    private String ocupacionMadre;
    @Column(name = "domicilio_calle_madre")
    private String domicilioCalleMadre;
    @Column(name = "domicilio_colonia_madre")
    private String domicilioColoniaMadre;
    @Column(name = "domicilio_ciudad_madre")
    private String domicilioCiudadMadre;
    @Column(name = "domicilio_telefono_madre")
    private String domicilioTelefonoMadre;
    @Column(name = "nombre_empresa")
    private String nombreEmpresa;
    @Column(name = "cargo_desempenado")
    private String cargoDesempenado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ingreso_mensual")
    private Double ingresoMensual;
    @Column(name = "turno")
    private Integer turno;
    @Column(name = "antiguedad")
    private String antiguedad;
    @Column(name = "nombre_jefe")
    private String nombreJefe;
    @Column(name = "domicilio_calle_empresa")
    private String domicilioCalleEmpresa;
    @Column(name = "domicilio_colonia_empresa")
    private String domicilioColoniaEmpresa;
    @Column(name = "domicilio_ciudad_empresa")
    private String domicilioCiudadEmpresa;
    @Column(name = "domicilio_telefono_empresa")
    private String domicilioTelefonoEmpresa;
    @Column(name = "nombre_esposa")
    private String nombreEsposa;
    @Column(name = "ocupacion_esposa")
    private String ocupacionEsposa;
    @Column(name = "no_dependientes")
    private Integer noDependientes;
    @Column(name = "comunidad_indigena")
    private String comunidadIndigena;
    @Column(name = "municipio_nac")
    private String municipioNac;
    @Column(name = "municipio_dom")
    private String municipioDom;
    @Column(name = "domicilio_calle_tutor")
    private String domicilioCalleTutor;
    @Column(name = "nombre_tutor")
    private String nombreTutor;
    @Column(name = "domicilio_ciudad_tutor")
    private String domicilioCiudadTutor;
    @Column(name = "domicilio_colonia_tutor")
    private String domicilioColoniaTutor;
    @Column(name = "domicilio_telefono_tutor")
    private String domicilioTelefonoTutor;
    @Column(name = "domicilio_entidad_feredativa_t")
    private Integer domicilioEntidadFeredativaT;
    @Column(name = "clave_preparatoria")
    private String clavePreparatoria;
    @Column(name = "municipio")
    private String municipio;
    @Column(name = "tipo_sangre")
    private String tipoSangre;
    @Column(name = "nss")
    private String nss;
    @Column(name = "domicilio")
    private String domicilio;
    @Column(name = "telefono_emergencia")
    private String telefonoEmergencia;
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "domicilio_entidad_fed_madre", referencedColumnName = "identidad")
    @ManyToOne
    private EntidadFederativa domicilioEntidadFedMadre;
    @JoinColumn(name = "domicilio_entidad_fed_padre", referencedColumnName = "identidad")
    @ManyToOne
    private EntidadFederativa domicilioEntidadFedPadre;
    @JoinColumn(name = "entidad_federativa", referencedColumnName = "identidad")
    @ManyToOne
    private EntidadFederativa entidadFederativa;
    @JoinColumn(name = "entidad_federativa_prepa", referencedColumnName = "identidad")
    @ManyToOne
    private EntidadFederativa entidadFederativaPrepa;
    @JoinColumn(name = "no_de_control", referencedColumnName = "no_de_control")
    @ManyToOne(optional = false)
    private Estudiante noDeControl;

    public AlumnosGenerales() {
    }

    public AlumnosGenerales(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public String getDomicilioCalle() {
        return domicilioCalle;
    }

    public void setDomicilioCalle(String domicilioCalle) {
        this.domicilioCalle = domicilioCalle;
    }

    public String getDomicilioColonia() {
        return domicilioColonia;
    }

    public void setDomicilioColonia(String domicilioColonia) {
        this.domicilioColonia = domicilioColonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombrePadre() {
        return nombrePadre;
    }

    public void setNombrePadre(String nombrePadre) {
        this.nombrePadre = nombrePadre;
    }

    public String getOcupacionPadre() {
        return ocupacionPadre;
    }

    public void setOcupacionPadre(String ocupacionPadre) {
        this.ocupacionPadre = ocupacionPadre;
    }

    public String getDomicilioCallePadre() {
        return domicilioCallePadre;
    }

    public void setDomicilioCallePadre(String domicilioCallePadre) {
        this.domicilioCallePadre = domicilioCallePadre;
    }

    public String getDomicilioColoniaPadre() {
        return domicilioColoniaPadre;
    }

    public void setDomicilioColoniaPadre(String domicilioColoniaPadre) {
        this.domicilioColoniaPadre = domicilioColoniaPadre;
    }

    public String getDomicilioCiudadPadre() {
        return domicilioCiudadPadre;
    }

    public void setDomicilioCiudadPadre(String domicilioCiudadPadre) {
        this.domicilioCiudadPadre = domicilioCiudadPadre;
    }

    public String getDomicilioTelefonoPadre() {
        return domicilioTelefonoPadre;
    }

    public void setDomicilioTelefonoPadre(String domicilioTelefonoPadre) {
        this.domicilioTelefonoPadre = domicilioTelefonoPadre;
    }

    public String getNombreMadre() {
        return nombreMadre;
    }

    public void setNombreMadre(String nombreMadre) {
        this.nombreMadre = nombreMadre;
    }

    public String getOcupacionMadre() {
        return ocupacionMadre;
    }

    public void setOcupacionMadre(String ocupacionMadre) {
        this.ocupacionMadre = ocupacionMadre;
    }

    public String getDomicilioCalleMadre() {
        return domicilioCalleMadre;
    }

    public void setDomicilioCalleMadre(String domicilioCalleMadre) {
        this.domicilioCalleMadre = domicilioCalleMadre;
    }

    public String getDomicilioColoniaMadre() {
        return domicilioColoniaMadre;
    }

    public void setDomicilioColoniaMadre(String domicilioColoniaMadre) {
        this.domicilioColoniaMadre = domicilioColoniaMadre;
    }

    public String getDomicilioCiudadMadre() {
        return domicilioCiudadMadre;
    }

    public void setDomicilioCiudadMadre(String domicilioCiudadMadre) {
        this.domicilioCiudadMadre = domicilioCiudadMadre;
    }

    public String getDomicilioTelefonoMadre() {
        return domicilioTelefonoMadre;
    }

    public void setDomicilioTelefonoMadre(String domicilioTelefonoMadre) {
        this.domicilioTelefonoMadre = domicilioTelefonoMadre;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getCargoDesempenado() {
        return cargoDesempenado;
    }

    public void setCargoDesempenado(String cargoDesempenado) {
        this.cargoDesempenado = cargoDesempenado;
    }

    public Double getIngresoMensual() {
        return ingresoMensual;
    }

    public void setIngresoMensual(Double ingresoMensual) {
        this.ingresoMensual = ingresoMensual;
    }

    public Integer getTurno() {
        return turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
    }

    public String getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(String antiguedad) {
        this.antiguedad = antiguedad;
    }

    public String getNombreJefe() {
        return nombreJefe;
    }

    public void setNombreJefe(String nombreJefe) {
        this.nombreJefe = nombreJefe;
    }

    public String getDomicilioCalleEmpresa() {
        return domicilioCalleEmpresa;
    }

    public void setDomicilioCalleEmpresa(String domicilioCalleEmpresa) {
        this.domicilioCalleEmpresa = domicilioCalleEmpresa;
    }

    public String getDomicilioColoniaEmpresa() {
        return domicilioColoniaEmpresa;
    }

    public void setDomicilioColoniaEmpresa(String domicilioColoniaEmpresa) {
        this.domicilioColoniaEmpresa = domicilioColoniaEmpresa;
    }

    public String getDomicilioCiudadEmpresa() {
        return domicilioCiudadEmpresa;
    }

    public void setDomicilioCiudadEmpresa(String domicilioCiudadEmpresa) {
        this.domicilioCiudadEmpresa = domicilioCiudadEmpresa;
    }

    public String getDomicilioTelefonoEmpresa() {
        return domicilioTelefonoEmpresa;
    }

    public void setDomicilioTelefonoEmpresa(String domicilioTelefonoEmpresa) {
        this.domicilioTelefonoEmpresa = domicilioTelefonoEmpresa;
    }

    public String getNombreEsposa() {
        return nombreEsposa;
    }

    public void setNombreEsposa(String nombreEsposa) {
        this.nombreEsposa = nombreEsposa;
    }

    public String getOcupacionEsposa() {
        return ocupacionEsposa;
    }

    public void setOcupacionEsposa(String ocupacionEsposa) {
        this.ocupacionEsposa = ocupacionEsposa;
    }

    public Integer getNoDependientes() {
        return noDependientes;
    }

    public void setNoDependientes(Integer noDependientes) {
        this.noDependientes = noDependientes;
    }

    public String getComunidadIndigena() {
        return comunidadIndigena;
    }

    public void setComunidadIndigena(String comunidadIndigena) {
        this.comunidadIndigena = comunidadIndigena;
    }

    public String getMunicipioNac() {
        return municipioNac;
    }

    public void setMunicipioNac(String municipioNac) {
        this.municipioNac = municipioNac;
    }

    public String getMunicipioDom() {
        return municipioDom;
    }

    public void setMunicipioDom(String municipioDom) {
        this.municipioDom = municipioDom;
    }

    public String getDomicilioCalleTutor() {
        return domicilioCalleTutor;
    }

    public void setDomicilioCalleTutor(String domicilioCalleTutor) {
        this.domicilioCalleTutor = domicilioCalleTutor;
    }

    public String getNombreTutor() {
        return nombreTutor;
    }

    public void setNombreTutor(String nombreTutor) {
        this.nombreTutor = nombreTutor;
    }

    public String getDomicilioCiudadTutor() {
        return domicilioCiudadTutor;
    }

    public void setDomicilioCiudadTutor(String domicilioCiudadTutor) {
        this.domicilioCiudadTutor = domicilioCiudadTutor;
    }

    public String getDomicilioColoniaTutor() {
        return domicilioColoniaTutor;
    }

    public void setDomicilioColoniaTutor(String domicilioColoniaTutor) {
        this.domicilioColoniaTutor = domicilioColoniaTutor;
    }

    public String getDomicilioTelefonoTutor() {
        return domicilioTelefonoTutor;
    }

    public void setDomicilioTelefonoTutor(String domicilioTelefonoTutor) {
        this.domicilioTelefonoTutor = domicilioTelefonoTutor;
    }

    public Integer getDomicilioEntidadFeredativaT() {
        return domicilioEntidadFeredativaT;
    }

    public void setDomicilioEntidadFeredativaT(Integer domicilioEntidadFeredativaT) {
        this.domicilioEntidadFeredativaT = domicilioEntidadFeredativaT;
    }

    public String getClavePreparatoria() {
        return clavePreparatoria;
    }

    public void setClavePreparatoria(String clavePreparatoria) {
        this.clavePreparatoria = clavePreparatoria;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefonoEmergencia() {
        return telefonoEmergencia;
    }

    public void setTelefonoEmergencia(String telefonoEmergencia) {
        this.telefonoEmergencia = telefonoEmergencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EntidadFederativa getDomicilioEntidadFedMadre() {
        return domicilioEntidadFedMadre;
    }

    public void setDomicilioEntidadFedMadre(EntidadFederativa domicilioEntidadFedMadre) {
        this.domicilioEntidadFedMadre = domicilioEntidadFedMadre;
    }

    public EntidadFederativa getDomicilioEntidadFedPadre() {
        return domicilioEntidadFedPadre;
    }

    public void setDomicilioEntidadFedPadre(EntidadFederativa domicilioEntidadFedPadre) {
        this.domicilioEntidadFedPadre = domicilioEntidadFedPadre;
    }

    public EntidadFederativa getEntidadFederativa() {
        return entidadFederativa;
    }

    public void setEntidadFederativa(EntidadFederativa entidadFederativa) {
        this.entidadFederativa = entidadFederativa;
    }

    public EntidadFederativa getEntidadFederativaPrepa() {
        return entidadFederativaPrepa;
    }

    public void setEntidadFederativaPrepa(EntidadFederativa entidadFederativaPrepa) {
        this.entidadFederativaPrepa = entidadFederativaPrepa;
    }

    public Estudiante getNoDeControl() {
        return noDeControl;
    }

    public void setNoDeControl(Estudiante noDeControl) {
        this.noDeControl = noDeControl;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlumnosGenerales)) {
            return false;
        }
        AlumnosGenerales other = (AlumnosGenerales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.AlumnosGenerales[ id=" + id + " ]";
    }
    
}
