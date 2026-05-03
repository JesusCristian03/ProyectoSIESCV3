/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package vista;

import DAO.EstudianteFacade;
import DAO.EstudianteFacadeLocal;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Paths;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.file.UploadedFile;
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
   @EJB
   private EstudianteFacadeLocal estudianteFacade;

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
    private UploadedFile fotoFile;
private UploadedFile firmaFile;


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
    
    numeroControl = (numeroControl != null) ? numeroControl.trim() : null;

    // VALIDACIONES
    if (numeroControl == null || numeroControl.trim().isEmpty()) {
        addMessage(FacesMessage.SEVERITY_ERROR,
                "NÚMERO DE CONTROL REQUERIDO",
                "Debe capturar el número de control");
        return;
    }
    
    //  validar duplicado 
if (estudianteFacade.existeNoControl(numeroControl)) {
    addMessage(FacesMessage.SEVERITY_ERROR,
            "ERROR",
            "El número de control ya está registrado");
    return;
}

    if (sexo == null || sexo.isEmpty()) {
        addMessage(FacesMessage.SEVERITY_ERROR,
                "SEXO REQUERIDO",
                "Debe seleccionar el sexo");
        return;
    }

    if (estadoCivil == null || estadoCivil.isEmpty()) {
        addMessage(FacesMessage.SEVERITY_ERROR,
                "ESTADO CIVIL REQUERIDO",
                "Debe seleccionar el estado civil");
        return;
    }
    
  if (reticula == null || reticula == 0) {
    addMessage(FacesMessage.SEVERITY_ERROR,
            "CARRERA REQUERIDA",
            "Debe seleccionar una carrera");
    return;
}

Carrera carrera = carreraServicio.buscarPorId(reticula);

if (carrera == null) {
    addMessage(FacesMessage.SEVERITY_ERROR,
            "CARRERA INVÁLIDA",
            "No se encontró la carrera seleccionada");
    return;
}

    if (periodoIngresoIt == null || periodoIngresoIt.isEmpty()) {
        addMessage(FacesMessage.SEVERITY_ERROR,
                "PERIODO REQUERIDO",
                "Debe seleccionar el periodo de ingreso");
        return;
    }

    if (numeroMunicipioEstudiante == null || numeroMunicipioEstudiante == 0) {
        addMessage(FacesMessage.SEVERITY_ERROR,
                "ENTIDAD FEDERATIVA REQUERIDA",
                "Debe seleccionar una entidad federativa");
        return;
    }

    if (domicilioCalle == null || domicilioCalle.trim().isEmpty()) {
        addMessage(FacesMessage.SEVERITY_ERROR,
                "CALLE REQUERIDA",
                "Debe capturar la calle");
        return;
    }

    if (domicilioColonia == null || domicilioColonia.trim().isEmpty()) {
        addMessage(FacesMessage.SEVERITY_ERROR,
                "COLONIA REQUERIDA",
                "Debe capturar la colonia");
        return;
    }

  if (estudiante.getCiudadProcedencia() == null
        || estudiante.getCiudadProcedencia().trim().isEmpty()) {

    addMessage(FacesMessage.SEVERITY_ERROR,
            "CIUDAD REQUERIDA",
            "Debe capturar la ciudad");
    return;
}
  


// VALIDAR CURP
if (estudiante.getCurpAlumno() == null || estudiante.getCurpAlumno().trim().isEmpty()) {
    addMessage(FacesMessage.SEVERITY_ERROR,
            "CURP REQUERIDA",
            "Debe capturar la CURP");
    return;
}

String curpAlumno = estudiante.getCurpAlumno().trim().toUpperCase();

// VALIDAR LONGITUD
if (curpAlumno.length() != 18) {
    addMessage(FacesMessage.SEVERITY_ERROR,
            "CURP INVÁLIDA",
            "La CURP debe tener exactamente 18 caracteres");
    return;
}

// VALIDAR FORMATO
String regexCurp = "^[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[A-Z0-9]{2}$";

if (!curpAlumno.matches(regexCurp)) {
    addMessage(FacesMessage.SEVERITY_ERROR,
            "CURP INVÁLIDA",
            "El formato de la CURP no es válido");
    return;
}


estudiante.setCurpAlumno(curpAlumno);

    // BUSCAR ENTIDAD FEDERATIVA
    EntidadFederativa entidadFederativa =
            entidadFederativaServicio.buscarEntidadFederativa(numeroMunicipioEstudiante);

    if (entidadFederativa == null) {
        addMessage(FacesMessage.SEVERITY_ERROR,
                "ENTIDAD NO ENCONTRADA",
                "La entidad federativa seleccionada no existe");
        return;
    }

    // GUARDAR FOTO
    if (fotoFile != null) {
        String nombreFoto = guardarArchivo(fotoFile);
        estudiante.setFoto(nombreFoto);
    }

    // GUARDAR FIRMA
    if (firmaFile != null) {
        String nombreFirma = guardarArchivo(firmaFile);
        estudiante.setFirma(nombreFirma);
    }

    // BUSCAR PERIODO
    PeriodoEscolar p = periodoEscolarServicio.buscarPorId(periodoIngresoIt);

    if (p == null) {
        addMessage(FacesMessage.SEVERITY_ERROR,
                "PERIODO NO ENCONTRADO",
                "El periodo escolar seleccionado no existe");
        return;
    }
    
   if (numeroMunicipioPadre == null || numeroMunicipioPadre == 0) {
    addMessage(FacesMessage.SEVERITY_ERROR,
            "ENTIDAD DEL PADRE REQUERIDA",
            "Debe seleccionar una entidad federativa para el padre");
    return;
}

if (numeroMunicipioMadre == null || numeroMunicipioMadre == 0) {
    addMessage(FacesMessage.SEVERITY_ERROR,
            "ENTIDAD DE LA MADRE REQUERIDA",
            "Debe seleccionar una entidad federativa para la madre");
    return;
}

   // validar plan
// validar plan
if (estudiante.getPlanDeEstudios() == null 
        || estudiante.getPlanDeEstudios().toString().trim().isEmpty()) {

    addMessage(FacesMessage.SEVERITY_WARN,
            "ADVERTENCIA",
            "Debe seleccionar un plan de estudios");
    return;
}






    Date fechaActualizacion = new Date();

    // ESTUDIANTE
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

    estudiante.setCarrera(carrera.getCarrera());

    // ALUMNO GENERAL
    alumnoGeneral.setNoDeControl(estudiante);
    alumnoGeneral.setEntidadFederativa(entidadFederativa);
    alumnoGeneral.setDomicilioCalle(domicilioCalle);
    alumnoGeneral.setDomicilioColonia(domicilioColonia);
   
    alumnoGeneral.setCiudad(estudiante.getCiudadProcedencia());
    alumnoGeneral.setNombre(estudiante.getNombreAlumno());
    alumnoGeneral.setLugarNacimiento(lugarNacimiento);
  
    


    String domicilioCompleto = domicilioCalle + ", "
            + domicilioColonia + ", "
            + entidadFederativa.getNombreEntidad();

    alumnoGeneral.setDomicilio(domicilioCompleto.toUpperCase());

EntidadFederativa entidadPadre =
        entidadFederativaServicio.buscarEntidadFederativa(numeroMunicipioPadre);

EntidadFederativa entidadMadre =
        entidadFederativaServicio.buscarEntidadFederativa(numeroMunicipioMadre);
    
alumnoGeneral.setDomicilioEntidadFedPadre(entidadPadre);
alumnoGeneral.setDomicilioEntidadFedMadre(entidadMadre);
    // GUARDAR
    estudianteServicio.insertarEstudiante(estudiante);
    alumnosGeneralesServicio.insertarAlumnoGeneral(alumnoGeneral);

    // MENSAJE DE EXITO
    addMessage(FacesMessage.SEVERITY_INFO,
            "REGISTRO EXITOSO",
            "El alumno fue dado de alta correctamente");

    System.out.println("ESTUDIANTE Y ALUMNO GENERAL INSERTADOS");
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

    
    private String guardarArchivo(UploadedFile archivo) {

    try {
        // Carpeta física donde se guardarán los archivos
        String ruta = "C:/scit/archivos/";

        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        // Nombre único para evitar sobreescrituras
       // String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getFileName();
 // nomas el nombre  del archivo
        String nombreArchivo = Paths.get(archivo.getFileName()).getFileName().toString();

        File destino = new File(ruta + nombreArchivo);

        try (InputStream in = archivo.getInputStream();
             FileOutputStream out = new FileOutputStream(destino)) {

            byte[] buffer = new byte[1024];
            int bytes;

            while ((bytes = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytes);
            }
        }

        return nombreArchivo;

    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}
    
    public UploadedFile getFirmaFile() {
    return firmaFile;
}

public void setFirmaFile(UploadedFile firmaFile) {
    this.firmaFile = firmaFile;
}

public UploadedFile getFotoFile() {
    return fotoFile;
}

public void setFotoFile(UploadedFile fotoFile) {
    this.fotoFile = fotoFile;
}

public void addMessage(FacesMessage.Severity severity, String resumen, String detalle) {
    FacesContext.getCurrentInstance().addMessage(
            null,
            new FacesMessage(severity, resumen, detalle)
    );
}

}
