/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package vista;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import modelo.AlumnosGenerales;
import modelo.Carrera;
import modelo.EntidadFederativa;
import modelo.Estudiante;
import servicio.AlumnosGeneralesServicioLocal;
import servicio.CarreraServicioLocal;
import servicio.EntidadFederativaServicioLocal;
import servicio.EstudianteServicioLocal;

/**
 *
 * @author cris_
 */
@Named(value = "altaEstudiantesBean")
@SessionScoped
public class altaEstudiantesBean implements Serializable {

    @EJB
    private AlumnosGeneralesServicioLocal alumnosGeneralesServicio;
    
    
  
    @EJB
    private EstudianteServicioLocal estudianteServicio;

    @EJB
    private CarreraServicioLocal carreraServicio;

    @EJB
    private EntidadFederativaServicioLocal entidadFederativaServicio;
    
    
    

    Estudiante estudiante = new Estudiante();
    List<Carrera> listaCarreras  = new ArrayList<>();
    List<EntidadFederativa> listaEntidadFederativa = new ArrayList<>();

    AlumnosGenerales alumnoGeneral= new AlumnosGenerales();

    private Integer anioIngreso=0;

    private String tipoIngreso = "";
    private String entidadProcedenciaPadre = "";
    private String entidadProcedenciaMadre = "";
    private String domicilioPadre="";
    private String domicilioMadre="";
    private String lugarNacimiento="";
    private String domicilioCalle="";
    private String domicilioColonia="";
    private String ciudad="";

    private Integer numeroMunicipioEstudiante=0;
    private Integer numeroMunicipioPadre=0;
    private Integer numeroMunicipioMadre=0;

    @PostConstruct
    public void init() {
        listaEntidadFederativa = entidadFederativaServicio.traerListaEF();
        listaCarreras =carreraServicio.buscarTodos();

    }

    public EntidadFederativaServicioLocal getEntidadFederativaServicio() {
        return entidadFederativaServicio;
    }

    public void setEntidadFederativaServicio(EntidadFederativaServicioLocal entidadFederativaServicio) {
        this.entidadFederativaServicio = entidadFederativaServicio;
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

    public List<EntidadFederativa> getListaEntidadFederativa() {
        return listaEntidadFederativa;
    }

    public void setListaEntidadFederativa(List<EntidadFederativa> listaEntidadFederativa) {
        this.listaEntidadFederativa = listaEntidadFederativa;
    }

    public Integer getNumeroMunicipioPadre() {
        return numeroMunicipioPadre;
    }

    public void setNumeroMunicipioPadre(Integer numeroMunicipioPadre) {
        this.numeroMunicipioPadre = numeroMunicipioPadre;
    }

    public Integer getNumeroMunicipioMadre() {
        return numeroMunicipioMadre;
    }

    public void setNumeroMunicipioMadre(Integer numeroMunicipioMadre) {
        this.numeroMunicipioMadre = numeroMunicipioMadre;
    }

    public Integer getNumeroMunicipioEstudiante() {
        return numeroMunicipioEstudiante;
    }

    public void setNumeroMunicipioEstudiante(Integer numeroMunicipioEstudiante) {
        this.numeroMunicipioEstudiante = numeroMunicipioEstudiante;
    }

    public String getEntidadProcedenciaPadre() {
        return entidadProcedenciaPadre;
    }

    public void setEntidadProcedenciaPadre(String entidadProcedenciaPadre) {
        this.entidadProcedenciaPadre = entidadProcedenciaPadre;
    }

    public String getEntidadProcedenciaMadre() {
        return entidadProcedenciaMadre;
    }

    public void setEntidadProcedenciaMadre(String entidadProcedenciaMadre) {
        this.entidadProcedenciaMadre = entidadProcedenciaMadre;
    }

    public String getDomicilioPadre() {
        return domicilioPadre;
    }

    public void setDomicilioPadre(String domicilioPadre) {
        this.domicilioPadre = domicilioPadre;
    }

    public String getDomicilioMadre() {
        return domicilioMadre;
    }

    /**
     * Creates a new instance of altaEstudiantesBean
     */
    public void setDomicilioMadre(String domicilioMadre) {
        this.domicilioMadre = domicilioMadre;
    }

    public altaEstudiantesBean() {
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public List<Carrera> getListaCarreras() {
        return listaCarreras;
    }

    public void setListaCarreras(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }

    public AlumnosGenerales getAlumnoGeneral() {
        return alumnoGeneral;
    }

    public void setAlumnoGeneral(AlumnosGenerales alumnoGeneral) {
        this.alumnoGeneral = alumnoGeneral;
    }

    public Integer getAnioIngreso() {
        return anioIngreso;
    }

    public void setAnioIngreso(Integer anioIngreso) {
        this.anioIngreso = anioIngreso;
    }

    public String getTipoIngreso() {
        return tipoIngreso;
    }

    public void setTipoIngreso(String tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
    }

    public void guardar() {
        EntidadFederativa entidadFederativa;
        Date fechaActualizacion = new Date();
        //Declaramos un caracter porque solo permite en las siguientes columnas
        alumnoGeneral.setCiudad('S');
        alumnoGeneral.setDomicilioColonia('S');
        alumnoGeneral.setDomicilioCalle('S');
        alumnoGeneral.setLugarNacimiento('S');

        //-------------------------
        //Por si lo necesitamos para armar el domicilio
        ciudad = estudiante.getCiudadProcedencia();
        alumnoGeneral.setNombre(estudiante.getNombreAlumno());

        //Asignamos la entidad Federativa. 
        System.out.println("numeroMunicipioEstudiante:"+numeroMunicipioEstudiante);
        entidadFederativa = entidadFederativaServicio.buscarEntidadFederativa(numeroMunicipioEstudiante);

        String domicilioCompleto = domicilioCalle + ","
                + domicilioColonia + ","
                + alumnoGeneral.getCodigoPostal() + ","
                + alumnoGeneral.getMunicipio() + ","
                + entidadFederativa.getNombreEntidad();

        alumnoGeneral.setDomicilio(domicilioCompleto.toUpperCase());

        estudiante.setEntidadProcedencia(entidadFederativa.getNombreEntidad());
        estudiante.setSemestre(0);
        //estudiante.setUltimoPeriodoInscrito(ultimoPeriodoInscrito);
        estudiante.setPromedioPeriodoAnterior((double)0);
        estudiante.setPromedioAritmeticoAcumulado((double)0);
        estudiante.setCreditosAprobados(0);
        estudiante.setCreditosCursados(0);
        estudiante.setPromedioFinalAlcanzado((double)0);
        estudiante.setFechaActualizacion(fechaActualizacion);
        alumnoGeneral.setEntidadFederativa(entidadFederativa);
        estudiante.setCarrera(estudiante.getReticula().getCarrera());

        entidadFederativa = entidadFederativaServicio.buscarEntidadFederativa(numeroMunicipioMadre);
        alumnoGeneral.setDomicilioEntidadFedMadre(entidadFederativa);

        entidadFederativa = entidadFederativaServicio.buscarEntidadFederativa(numeroMunicipioPadre);
        alumnoGeneral.setDomicilioEntidadFedPadre(entidadFederativa);

        // private String domicilioCalle;
        // private String domicilioColonia
        //LE damos al estudiante
        estudianteServicio.insertarEstudiante(estudiante);
        alumnoGeneral.setNoDeControl(estudiante);
        alumnosGeneralesServicio.insertarAlumnoGeneral(alumnoGeneral);
        System.out.println("INSERTO ALUMNO GENERAL Y ESTUDIANTE");
    }
}
