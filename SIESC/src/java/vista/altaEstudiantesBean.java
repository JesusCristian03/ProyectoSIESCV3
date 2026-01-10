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
import modelo.PeriodoEscolar;
import servicio.AlumnosGeneralesServicioLocal;
import servicio.CarreraServicioLocal;
import servicio.EntidadFederativaServicioLocal;
import servicio.EstudianteServicioLocal;
import servicio.PeriodoEscolarServicioLocal;


@Named(value = "altaEstudiantesBean")
@SessionScoped
public class altaEstudiantesBean implements Serializable {

    @EJB
    private PeriodoEscolarServicioLocal periodoEscolarServicio;
    @EJB
    private AlumnosGeneralesServicioLocal alumnosGeneralesServicio;
    @EJB
    private EstudianteServicioLocal estudianteServicio;
    @EJB
    private CarreraServicioLocal carreraServicio;
    @EJB
    private EntidadFederativaServicioLocal entidadFederativaServicio;

    List<Carrera> listaCarreras = new ArrayList<>();
    List<EntidadFederativa> listaEntidadFederativa = new ArrayList<>();

    AlumnosGenerales alumnoGeneral = new AlumnosGenerales();
    Estudiante estudiante = new Estudiante();

    private String tipoIngreso = "";
    private String entidadProcedenciaPadre = "";
    private String entidadProcedenciaMadre = "";
    private String domicilioPadre = "";
    private String domicilioMadre = "";
    private String lugarNacimiento = "";
    private String domicilioCalle = "";
    private String domicilioColonia = "";
    private String ciudad = "";
    private String firma = "";
    private String numeroControl = "";
    private String sexo = "";
    private String estadoCivil = "";
    private String periodoIngresoIt = "";

    private Integer numeroMunicipioEstudiante = 0;
    private Integer numeroMunicipioPadre = 0;
    private Integer numeroMunicipioMadre = 0;
    private String anioIngreso;
    private Integer reticula = 0;

    @PostConstruct
    public void init() {
       inicializacion(); 

    }

    public void inicializacion() {
        listaEntidadFederativa = entidadFederativaServicio.traerListaEF();
        listaCarreras = carreraServicio.buscarTodos();
         System.out.println(" LISTAS CARGADAS: " + listaEntidadFederativa.size() + " entidades federativas");

    }

    
    
    
    
public void guardar() {

    System.out.println("=== INICIO GUARDAR ===");
    System.out.println("No Control: " + numeroControl);
    System.out.println("Entidad Estudiante ID: " + numeroMunicipioEstudiante);
    System.out.println("Sexo: " + sexo);
    System.out.println("Estado civil: " + estadoCivil);
    System.out.println("Periodo ingreso IT: " + periodoIngresoIt);
    System.out.println("=====================");
    
    
    
    System.out.println("AlumnoGeneral noControl: " + alumnoGeneral.getNoDeControl());


    // vlaidaciones 
    if (sexo == null || sexo.isEmpty()) {
        System.out.println("ERROR: sexo vacío");
        return;
    }

    if (estadoCivil == null || estadoCivil.isEmpty()) {
        System.out.println("ERROR: estado civil vacío");
        return;
    }

    // ENTIDAD FEDERATIVA DEL ESTUDIANTE
    EntidadFederativa entidadFederativa =
            entidadFederativaServicio.buscarEntidadFederativa(numeroMunicipioEstudiante);

    if (entidadFederativa == null) {
        System.out.println("ERROR: Entidad federativa no encontrada");
        return;
    }

    // PERIODO ESCOLAR
    PeriodoEscolar p = periodoEscolarServicio.buscarPorId(periodoIngresoIt);
    if (p == null) {
        System.out.println("ERROR: Periodo escolar no encontrado");
        return;
    }

    Date fechaActualizacion = new Date();

  // Estudiante
    estudiante.setNoDeControl(numeroControl);
    estudiante.setSexo(sexo.charAt(0));
    estudiante.setEstadoCivil(estadoCivil.charAt(0));
    estudiante.setPeriodoIngresoIt(p);
    estudiante.setSemestre(0);
    estudiante.setPromedioPeriodoAnterior(0.0);
    estudiante.setPromedioAritmeticoAcumulado(0.0);
    estudiante.setCreditosAprobados(0);
    estudiante.setCreditosCursados(0);
    estudiante.setPromedioFinalAlcanzado(0.0);
    estudiante.setFechaActualizacion(fechaActualizacion);
    estudiante.setEntidadProcedencia(entidadFederativa.getNombreEntidad());

    estudiante.setCarrera(
            carreraServicio.buscarPorId(reticula).getCarrera()
    );

  // Alumno general
    alumnoGeneral.setNoDeControl(estudiante);
    alumnoGeneral.setEntidadFederativa(entidadFederativa);

    String domicilioCompleto = domicilioCalle + ", "
            + domicilioColonia + ", "
            + entidadFederativa.getNombreEntidad();

    alumnoGeneral.setDomicilio(domicilioCompleto.toUpperCase());

    // Familiares PADRE / MADRE
    alumnoGeneral.setDomicilioEntidadFedMadre(
            entidadFederativaServicio.buscarEntidadFederativa(numeroMunicipioMadre)
    );

    alumnoGeneral.setDomicilioEntidadFedPadre(
            entidadFederativaServicio.buscarEntidadFederativa(numeroMunicipioPadre)
    );
    
  // Guardar
    estudianteServicio.insertarEstudiante(estudiante);
    alumnosGeneralesServicio.insertarAlumnoGeneral(alumnoGeneral);

    System.out.println("ESTUDIANTE Y ALUMNO GENERAL INSERTADOS");
}


    
    
    public void guardarPrueba() {

        estudiante.setSexo(sexo.charAt(0));
        estudiante.setEstadoCivil(estadoCivil.charAt(0));
        estudiante.setNoDeControl(numeroControl);
        EntidadFederativa entidadFederativa = entidadFederativaServicio.buscarEntidadFederativa(numeroMunicipioEstudiante);
        estudiante.setEntidadProcedencia(entidadFederativa.getNombreEntidad());

        PeriodoEscolar p = periodoEscolarServicio.buscarPorId(periodoIngresoIt);
        estudiante.setPeriodoIngresoIt(p);
        estudiante.setCarrera(carreraServicio.buscarPorId(reticula).getCarrera());
        entidadFederativa = entidadFederativaServicio.buscarEntidadFederativa(numeroMunicipioMadre);
        alumnoGeneral.setDomicilioEntidadFedMadre(entidadFederativa);

        

        entidadFederativa = entidadFederativaServicio.buscarEntidadFederativa(numeroMunicipioPadre);
        alumnoGeneral.setDomicilioEntidadFedPadre(entidadFederativa);

        System.out.println("Periodo Ingreso Codigo");
        System.out.println("===== DEBUG ESTUDIANTE =====");
        System.out.println("Número de control: " + estudiante.getNoDeControl());

        System.out.println("Apellido paterno: " + estudiante.getApellidoPaterno());
        System.out.println("Apellido materno: " + estudiante.getApellidoMaterno());
        System.out.println("Nombre: " + estudiante.getNombreAlumno());

        System.out.println("Fecha de nacimiento: " + estudiante.getFechaNacimiento());

        System.out.println("Sexo: " + estudiante.getSexo());
        System.out.println("Estado civil: " + estudiante.getEstadoCivil());
        System.out.println("CURP: " + estudiante.getCurpAlumno());
        System.out.println("Correo electrónico: " + estudiante.getCorreoElectronico());

        System.out.println("\n===== DEBUG: DOMICILIO =====");

        System.out.println("Calle y número: " + domicilioCalle);
        System.out.println("Colonia: " + domicilioColonia);
        System.out.println("Código postal: " + alumnoGeneral.getCodigoPostal());
        System.out.println("Ciudad procedencia: " + estudiante.getCiudadProcedencia());

        System.out.println("Entidad federativa (ID): " + estudiante.getEntidadProcedencia());

        System.out.println("Teléfono: " + alumnoGeneral.getTelefono());
        System.out.println("Municipio: " + alumnoGeneral.getMunicipio());
        System.out.println("Teléfono emergencia: " + alumnoGeneral.getTelefonoEmergencia());

        System.out.println("\n===== DEBUG: DATOS ESCOLARES =====");
        System.out.println("Retícula/Carrera: " + estudiante.getCarrera());
        System.out.println("Especialidad: " + estudiante.getEspecialidad());

        System.out.println("Periodo Ingreso IT: " + estudiante.getPeriodoIngresoIt());
        
        
        
     
        System.out.println("Año Ingreso: " + anioIngreso);
        System.out.println("Tipo de Ingreso: " + tipoIngreso);
        
        
        
        
        
        

        System.out.println("Plan de Estudio: " + estudiante.getPlanDeEstudios());
        System.out.println("Nivel Escolar: " + estudiante.getNivelEscolar());
        System.out.println("Estatus Alumno: " + estudiante.getEstatusAlumno());

        System.out.println("Tipo Servicio Médico: " + estudiante.getTipoServicioMedico());
        System.out.println("Clave Servicio Médico: " + estudiante.getClaveServicioMedico());

        System.out.println("Escuela Procedencia: " + estudiante.getEscuelaProcedencia());
        System.out.println("Domicilio Escuela: " + estudiante.getDomicilioEscuela());

        //System.out.println("Firma (base64 o binario): " + firma);
        System.out.println("\n===== DEBUG: DATOS FAMILIARES PADRE=====");
        System.out.println("Nombre del Padre: " + alumnoGeneral.getNombrePadre());
        System.out.println("Domicilio Calle Padre: " + alumnoGeneral.getDomicilioCallePadre());
        System.out.println("Colonia Padre: " + alumnoGeneral.getDomicilioColoniaPadre());
        System.out.println("Estado Padre (id municipio): " + alumnoGeneral.getDomicilioEntidadFedPadre());
        System.out.println("Teléfono Padre: " + alumnoGeneral.getDomicilioTelefonoPadre());
        System.out.println("Ocupación Padre: " + alumnoGeneral.getOcupacionPadre());
        System.out.println("Domicilio Ciudad Padre: " + alumnoGeneral.getDomicilioCiudadPadre());

        System.out.println("\n===== DEBUG: DATOS FAMILIARES MADRE =====");
        System.out.println("Nombre de la Madre: " + alumnoGeneral.getNombreMadre());
        System.out.println("Domicilio Calle Madre: " + alumnoGeneral.getDomicilioCalleMadre());
        System.out.println("Colonia Madre: " + alumnoGeneral.getDomicilioColoniaMadre());
        System.out.println("Estado Madre (id municipio): " + alumnoGeneral.getDomicilioEntidadFedMadre());
        System.out.println("Teléfono Madre: " + alumnoGeneral.getDomicilioTelefonoMadre());
        System.out.println("Ocupación Madre: " + alumnoGeneral.getOcupacionMadre());
        System.out.println("Domicilio Ciudad Madre: " + alumnoGeneral.getDomicilioCiudadMadre());

        System.out.println("\n===== FIN DEBUG =====");

    }

    public String getPeriodoIngresoIt() {
        return periodoIngresoIt;
    }

    public void setPeriodoIngresoIt(String periodoIngresoIt) {
        this.periodoIngresoIt = periodoIngresoIt;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public Integer getReticula() {
        return reticula;
    }

    public void setReticula(Integer reticula) {
        this.reticula = reticula;
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

    public String getAnioIngreso() {
        return anioIngreso;
    }

    public void setAnioIngreso(String anioIngreso) {
        this.anioIngreso = anioIngreso;
    }

    public String getTipoIngreso() {
        return tipoIngreso;
    }

    public void setTipoIngreso(String tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

}
