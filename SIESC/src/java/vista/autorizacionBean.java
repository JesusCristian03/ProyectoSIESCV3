/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package vista;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.AutorizacionInscripcion;
import modelo.Estudiante;
import modelo.Materia;
import modelo.MateriasCarreras;
import modelo.PeriodoEscolar;
import modelo.Reticula;
import modelo.ReticulaDatos;
import modelo.Usuario;
import servicio.AutorizacionInscripcionServicioLocal;
import servicio.EstudianteServicioLocal;
import servicio.MateriaServicioLocal;
import servicio.MateriasCarrerasServicioLocal;
import servicio.PeriodoEscolarServicioLocal;

/**
 *
 * @author cris_
 */
@Named(value = "autorizacionBean")
@SessionScoped
public class autorizacionBean implements Serializable {

    @EJB
    private MateriasCarrerasServicioLocal materiasCarrerasServicio;
    @EJB
    private MateriaServicioLocal materiaServicio;
    @EJB
    private AutorizacionInscripcionServicioLocal autorizacionInscripcionServicio;
    @EJB
    private PeriodoEscolarServicioLocal periodoEscolarServicio;
    @EJB
    private EstudianteServicioLocal estudianteServicio;

    /**
     * Creates a new instance of autorizacionBean
     */
    private Integer numeroControl = 0;
    private Estudiante estudiante;
    private Usuario usuario;
    private String nombreAlumno;
    private List<PeriodoEscolar> listaPeriodos;
    private List<AutorizacionInscripcion> listaAutorizaciones;
    private String periodoSeleccionado = "";
    private String tipoAutorizacion = "";
    private String motivoAutorizacion;
    private String asignaturaEscogida;
    private List<MateriasCarreras> listaMateria = new ArrayList<>();
    private Boolean deAcuerdo = false;
    private Boolean activarBotonAutorizacion = false;
    private ArrayList<Reticula> listaM = new ArrayList();
    List<int[]> listaAzules = new ArrayList<>();

    public autorizacionBean() {

    }

    public void inicializacion() {
        // 4. Obtiene el contexto actual de JSF (para trabajar con sesión, request, etc.)
        FacesContext contexto = FacesContext.getCurrentInstance();
        // 5. Recupera el objeto "usuario" guardado en la sesión
        usuario = (Usuario) contexto.getExternalContext().getSessionMap().get("usuario");
        listaPeriodos = periodoEscolarServicio.periodosEscolaresActivos();
        if (listaPeriodos != null) {
            System.out.println("listaPeriodos.get(0).getIdentificacionCorta()" + listaPeriodos.get(0).getIdentificacionCorta());
            periodoSeleccionado = listaPeriodos.get(0).getPeriodo();

        }
        listaAutorizaciones = new ArrayList();

    }

    public void insertar() {
        if (tipoAutorizacion.equals("-")) {
            addMessage(FacesMessage.SEVERITY_WARN, "TIPO AUTORIZACION", "No haz seleccionado ningún tipo de autorización.");
            return;
        }
        System.out.println("Valor variable deAcuerdo" + deAcuerdo);
        if (!deAcuerdo) {
            for (int i = 0; i < listaAutorizaciones.size(); i++) {
                AutorizacionInscripcion ai = listaAutorizaciones.get(i);

                if (ai.getTipoAutorizacion().equals(tipoAutorizacion)) {
                    addMessage(FacesMessage.SEVERITY_WARN, "TIPO AUTORIZACION", "No puedes autorizar nuevamente a "
                            + capitalizar(estudiante.getNombreAlumno()) + " con:" + convertirSigla(tipoAutorizacion));
                    return;
                }
            }
        } else {
            for (int i = 0; i < listaAutorizaciones.size(); i++) {
                AutorizacionInscripcion ai = listaAutorizaciones.get(i);
                System.out.println("Comparando:");
                System.out.println("ai.getTipoAutorizacion() = " + ai.getTipoAutorizacion());
                System.out.println("tipoAutorizacion = " + tipoAutorizacion);
                System.out.println("asignaturaEscogida = " + asignaturaEscogida);
                System.out.println("ai.getMateriaAfectada().getMateria():"+ai.getMateriaAfectada().getMateria());
                System.out.println("------------------------");
                if (ai.getTipoAutorizacion().equals(tipoAutorizacion)
                        && ai.getMateriaAfectada().getMateria().equals(asignaturaEscogida)) {
                   /* addMessage(FacesMessage.SEVERITY_WARN, "TIPO AUTORIZACION", "No puedes autorizar nuevamente a "
                            + capitalizar(estudiante.getNombreAlumno())  + " con: " + convertirSigla(tipoAutorizacion)+" con materia "+ai.getMateriaAfectada().getMateria());*/
                    addMessage(FacesMessage.SEVERITY_WARN, "TIPO AUTORIZACION", "Autorización repetida"); 
                   return;
                }
            }

        }

        AutorizacionInscripcion ai = new AutorizacionInscripcion();
        Materia materiaAfectada = materiaServicio.buscarMateria(asignaturaEscogida);
        PeriodoEscolar pe = periodoEscolarServicio.buscarPorId(periodoSeleccionado);
        // Estudiante e = estudianteServicio.buscarPorID(nombreAlumno);
        ai.setPeriodo(pe);
        ai.setNoDeControl(estudiante);
        ai.setTipoAutorizacion(tipoAutorizacion);
        ai.setMotivoAutorizacion(motivoAutorizacion);
        ai.setQuienAutoriza(usuario.getUsuario());
        Date fechaHoraActual = new Date();
        ai.setFechaHoraAutoriza(fechaHoraActual);
        ai.setMateriaAfectada(materiaAfectada);

        System.out.println("ai" + ai);

        listaAutorizaciones.add(ai);
        autorizacionInscripcionServicio.insertar(ai);
        addMessage(FacesMessage.SEVERITY_INFO, "AUTORIZACIÓN", "Se ha insertado nueva autorización");
    }
    public static String capitalizar(String texto) {
    if (texto == null || texto.isEmpty()) {
        return texto;
    }

    texto = texto.toLowerCase();
    String[] partes = texto.split(" ");
    StringBuilder sb = new StringBuilder();

    for (String p : partes) {
        if (!p.isEmpty()) {
            sb.append(Character.toUpperCase(p.charAt(0)))
              .append(p.substring(1))
              .append(" ");
        }
    }

    return sb.toString().trim();
}

    public void cambioEstudiante() {
        System.out.println("AutorizacionBean {"
                + "\n  numeroControl = " + numeroControl
                + "\n  estudiante = " + (estudiante != null ? estudiante.toString() : "null")
                + "\n  nombreAlumno = '" + nombreAlumno + '\''
                + "\n  listaPeriodos = " + (listaPeriodos != null ? listaPeriodos.size() + " periodos" : "null")
                + "\n  periodoSeleccionado = '" + periodoSeleccionado + '\''
                + "\n  tipoAutorizacion = '" + tipoAutorizacion + '\''
                + "\n}");

        estudiante = estudianteServicio.buscarPorID("" + numeroControl);
        if (estudiante != null) {
            addMessage(FacesMessage.SEVERITY_INFO, "ESTUDIANTE", "Se ha encontrado alumno");
            nombreAlumno = estudiante.getNombreAlumno() + " " + estudiante.getApellidoPaterno() + " " + estudiante.getApellidoMaterno();

            if (numeroControl != 0 && !"-".equals(periodoSeleccionado)) {
                listaAutorizaciones = autorizacionInscripcionServicio.buscarAutorizacionesAlumno(periodoSeleccionado, "" + numeroControl);
                System.out.println("Lista Autorizados" + listaAutorizaciones.size());
            }
        } else {
            addMessage(FacesMessage.SEVERITY_WARN, "ESTUDIANTE", "No se encuentra alumno");
            listaAutorizaciones = new ArrayList();
            nombreAlumno = "NO SE ENCUENTRA";

        }
        validacionCampos();

    }

    public void cambioAutorizacion() {
        System.out.println("Valor " + tipoAutorizacion);
        if (tipoAutorizacion.equals("CR") || tipoAutorizacion.equals("RQ") || tipoAutorizacion.equals("SC")) {
            System.out.println("True");
            addMessage(FacesMessage.SEVERITY_INFO, "MATERIA", "Selecciona materia");
            if (estudiante != null) {
                System.out.println("Estudiante" + estudiante);
                listaMateria = new ArrayList<>();
                listaM = materiasCarrerasServicio.buscarMaterias(estudiante);
                guardandoMateriasDisponibles();
            }

            deAcuerdo = true;
            activarBotonAutorizacion = false;
        } else {
            validacionCampos();
            deAcuerdo = false;
        }

    }

    public void cambioPeriodo() {

        System.out.println("PeriodoSeleciconado:(" + periodoSeleccionado + ")");
        if (periodoSeleccionado.equals("-")) {
            System.out.println("PeriodoSeleccionadoTrue");
            listaAutorizaciones = new ArrayList();
        } else {
            if (numeroControl != 0) {
                listaAutorizaciones = autorizacionInscripcionServicio.buscarAutorizacionesAlumno(periodoSeleccionado, "" + numeroControl);
                System.out.println("Lista Autorizados" + listaAutorizaciones.size());
            }

        }
        validacionCampos();

    }

    public void validacionCampos() {
        if (!tipoAutorizacion.equals("-") && !periodoSeleccionado.equals("-") && numeroControl != 0
                && !nombreAlumno.equals("NO SE ENCUENTRA")) {
            System.out.println("ActivarBotonTrue");
            activarBotonAutorizacion = true;
        } else {
            System.out.println("ActivarBotonTrue");
            activarBotonAutorizacion = false;

        }

    }

    public void cambioAsignatura() {
        if (!asignaturaEscogida.equals("-")) {
            activarBotonAutorizacion = true;
            validacionCampos();

        } else {
            activarBotonAutorizacion = false;

        }

    }

    public void guardandoMateriasDisponibles() {//

        for (int j = 0; j < listaM.size(); j++) { // Recorro cada retícula
            Reticula r = listaM.get(j);
            System.out.println("===== Retícula #" + (j + 1) + " =====");

            for (int s = 1; s <= 10; s++) { // Recorro los 10 semestres
                try {
                    // Obtener el semestre actual dinámicamente
                    ReticulaDatos rd = (ReticulaDatos) r.getClass()
                            .getMethod("getSemestre" + s)
                            .invoke(r);

                    if (rd != null) {
                        System.out.println("Semestre " + s + ": " + rd.getNombreMateria());

                        if ("disponible".equals(rd.getColor())) {
                            System.out.println("Valor encontrado" + rd.getMateria());

                            MateriasCarreras x = materiasCarrerasServicio.buscarMateriasCarreraPorMateria(rd.getMateria());
                            System.out.println("X:" + x);
                            listaMateria.add(x);

                        }

                    } else {
                        System.out.println("Semestre " + s + ": vacío");
                    }

                } catch (Exception e) {
                    System.out.println("Error al obtener Semestre " + s + ": " + e.getMessage());
                    e.printStackTrace(); // <-- imprime la excepción completa
                }
            }
            System.out.println("-----------------------------------");
        }
    }

    public void eliminar(AutorizacionInscripcion ai) {

        if (ai != null) {
            addMessage(FacesMessage.SEVERITY_INFO, "AUTORIZACIÓN", "Se ha eliminado autorización");
            //autorizacionInscripcionServicio.eliminar(ai);
            listaAutorizaciones.remove(ai);
            autorizacionInscripcionServicio.eliminar(ai);
        }
    }

    public String convertirSigla(String sigla) {
        System.out.println("Sigla encontrada" + sigla);
        if (sigla == null) {
            return "";
        }

        switch (sigla) {
            case "CM":
                return "CARGA MÍNIMA";
            case "CR":
                return "CRUCE DE MATERIAS";
            case "RQ":
                return "CURSAR MATERIA REQUISITADA";
            case "SC":
                return "SOBRECARGA";
            case "12":
                return "MÁS DE 12 SEMESTRES";
            case "OE":
                return "OMITIR CURSOS DE REPETICIÓN/ESPECIALES";
            case "-":
                return "";
            default:
                return sigla; // por si viene algo inesperado
        }
    }

    public ArrayList<Reticula> getListaM() {
        return listaM;
    }

    public void setListaM(ArrayList<Reticula> listaM) {
        this.listaM = listaM;
    }

    private void addMessage(FacesMessage.Severity severity, String titulo, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, titulo, detalle));
    }

    public List<MateriasCarreras> getListaMateria() {
        return listaMateria;
    }

    public void setListaMateria(List<MateriasCarreras> listaMateria) {
        this.listaMateria = listaMateria;
    }

    public String getAsignaturaEscogida() {
        return asignaturaEscogida;
    }

    public void setAsignaturaEscogida(String asignaturaEscogida) {
        this.asignaturaEscogida = asignaturaEscogida;
    }

    public Integer getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(Integer numeroControl) {
        this.numeroControl = numeroControl;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public List<PeriodoEscolar> getListaPeriodos() {
        return listaPeriodos;
    }

    public void setListaPeriodos(List<PeriodoEscolar> listaPeriodos) {
        this.listaPeriodos = listaPeriodos;
    }

    public String getPeriodoSeleccionado() {
        return periodoSeleccionado;
    }

    public void setPeriodoSeleccionado(String periodoSeleccionado) {
        this.periodoSeleccionado = periodoSeleccionado;
    }

    public String getTipoAutorizacion() {
        return tipoAutorizacion;
    }

    public void setTipoAutorizacion(String tipoAutorizacion) {
        this.tipoAutorizacion = tipoAutorizacion;
    }

    public List<AutorizacionInscripcion> getListaAutorizaciones() {
        return listaAutorizaciones;
    }

    public void setListaAutorizaciones(List<AutorizacionInscripcion> listaAutorizaciones) {
        this.listaAutorizaciones = listaAutorizaciones;
    }

    public String getMotivoAutorizacion() {
        return motivoAutorizacion;
    }

    public void setMotivoAutorizacion(String motivoAutorizacion) {
        this.motivoAutorizacion = motivoAutorizacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Boolean getDeAcuerdo() {
        return deAcuerdo;
    }

    public void setDeAcuerdo(Boolean deAcuerdo) {
        this.deAcuerdo = deAcuerdo;
    }

    public Boolean getActivarBotonAutorizacion() {
        return activarBotonAutorizacion;
    }

    public void setActivarBotonAutorizacion(Boolean activarBotonAutorizacion) {
        this.activarBotonAutorizacion = activarBotonAutorizacion;
    }

}
