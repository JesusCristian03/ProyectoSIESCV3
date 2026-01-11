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
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Paths;
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
    
    if (fotoFile != null) {
    String nombreFoto = guardarArchivo(fotoFile);
    estudiante.setFoto(nombreFoto);
}

if (firmaFile != null) {
    String nombreFirma = guardarArchivo(firmaFile);
    estudiante.setFirma(nombreFirma);
}


    
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
    alumnoGeneral.setDomicilioCalle(domicilioCalle);
    alumnoGeneral.setDomicilioColonia(domicilioColonia);
    alumnoGeneral.setCiudad(ciudad);
    alumnoGeneral.setNombre(estudiante.getNombreAlumno());
    alumnoGeneral.setLugarNacimiento(lugarNacimiento);

// Firma y archivos
// estudiante.setFirma(firma);


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


}
