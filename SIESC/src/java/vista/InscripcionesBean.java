/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package vista;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
//import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Estudiante;
import modelo.HistoriaAlumno;
import modelo.HorarioAsignatura;
import modelo.Horarios;
import modelo.MateriasCarreras;
import modelo.PeriodoEscolar;
import modelo.Reticula;
import modelo.ReticulaDatos;
import modelo.SeleccionMaterias;
import servicio.GruposServicioLocal;
import servicio.HistoriaAlumnoServicioLocal;
import servicio.HorarioAsignaturaServicioLocal;
import servicio.HorarioServicioLocal;
import servicio.MateriasCarrerasServicioLocal;
import servicio.PeriodoEscolarServicioLocal;
import servicio.SeleccionMateriasServicioLocal;

/**
 *
 * @author gacev
 */
@Named(value = "inscripcionesBean")
@SessionScoped
public class InscripcionesBean implements Serializable {

    @EJB
    private HistoriaAlumnoServicioLocal historiaAlumnoServicio;

    @EJB
    private SeleccionMateriasServicioLocal seleccionMateriasServicio;

    @EJB
    private PeriodoEscolarServicioLocal periodoEscolarServicio;

    @EJB
    private GruposServicioLocal gruposServicio;

    @EJB
    private HorarioAsignaturaServicioLocal horarioAsignaturaServicio;

    @EJB
    private HorarioServicioLocal horarioServicio;

    @EJB
    private MateriasCarrerasServicioLocal materiasCarrerasServicio;

    private List<HorarioAsignatura> listaHorarioAsignatura = new ArrayList<>();//Para llenar mi tabla de seleccion materias.
    private List<HorarioAsignatura> listaHorarioASeleccionadas = new ArrayList<>();//Para llenar mi tabla de seleccion materias.
    private List<Horarios> listaHorariosGenerados;
    private List<PeriodoEscolar> listaPeriodoEscolar;//para sacar los periodos escolares. 
    private ArrayList<Reticula> listaM = new ArrayList();

    private List<SeleccionMaterias> listaSM = new ArrayList();

    private List<String> listaGC = new ArrayList();

    private HorarioAsignatura grupoSeleccionado;

    private Estudiante estudiante;

    private MateriasCarreras materiaSeleccionada;
    private String periodo;
    private String materia;
    private double promedio;
    private PeriodoEscolar periodoActual;
    private ReticulaDatos materiaDeTabla;
    private Boolean grupoBloqueado = false;

    public Boolean getGrupoBloqueado() {
        return grupoBloqueado;
    }

    public void setGrupoBloqueado(Boolean grupoBloqueado) {
        this.grupoBloqueado = grupoBloqueado;
    }

    public List<String> getListaGC() {
        return listaGC;
    }

    public void setListaGC(List<String> listaGC) {
        this.listaGC = listaGC;
    }

    public PeriodoEscolar getPeriodoActual() {
        return periodoActual;
    }

    public void setPeriodoActual(PeriodoEscolar periodoActual) {
        this.periodoActual = periodoActual;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public ReticulaDatos getMateriaDeTabla() {
        return materiaDeTabla;
    }

    public List<HorarioAsignatura> getListaHorarioASeleccionadas() {
        return listaHorarioASeleccionadas;
    }

    public void setListaHorarioASeleccionadas(List<HorarioAsignatura> listaHorarioASeleccionadas) {
        this.listaHorarioASeleccionadas = listaHorarioASeleccionadas;
    }

    public HorarioAsignatura getGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    public List<SeleccionMaterias> getListaSM() {
        return listaSM;
    }

    public void setListaSM(List<SeleccionMaterias> listaSM) {
        this.listaSM = listaSM;
    }

    /**
     * Creates a new instance of InscripcionesBean
     */
    public void setGrupoSeleccionado(HorarioAsignatura grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
    }

    public void setMateriaDeTabla(ReticulaDatos materiaDeTabla) {
        this.materiaDeTabla = materiaDeTabla;
    }

    public InscripcionesBean() {
        try {
            FacesContext contexto = FacesContext.getCurrentInstance();
            estudiante = (Estudiante) contexto.getExternalContext().getSessionMap().get("estudiante");

            if (estudiante != null) {
                //System.out.println("Buscarrrrrrrr Materias" + estudiante.getReticula().getReticula());
                //

            } else {

            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace() + "\nError" + e.getCause().toString());
        }
    }

    public List<PeriodoEscolar> getListaPeriodoEscolar() {
        return listaPeriodoEscolar;
    }

    public void setListaPeriodoEscolar(List<PeriodoEscolar> listaPeriodoEscolar) {
        this.listaPeriodoEscolar = listaPeriodoEscolar;
    }

    public MateriasCarreras getMateriaSeleccionada() {
        return materiaSeleccionada;
    }

    public void setMateriaSeleccionada(MateriasCarreras materiaSeleccionada) {
        this.materiaSeleccionada = materiaSeleccionada;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public ArrayList<Reticula> getListaM() {
        return listaM;
    }

    public void setListaM(ArrayList<Reticula> listaM) {
        this.listaM = listaM;
    }

    public List<HorarioAsignatura> getListaHorarioAsignatura() {
        //listaHorarioAsignatura = horarioAsignaturaServicio.buscarHorarioAsignatura(periodo, materia);
        System.out.println("listaHorarioAsignatura size:" + listaHorarioAsignatura.size());
        return listaHorarioAsignatura;
    }

    public void setListaHorarioAsignatura(List<HorarioAsignatura> listaHorarioAsignatura) {
        this.listaHorarioAsignatura = listaHorarioAsignatura;
    }

    // Metodos 
    public void iniciarInscripcion() {
        //Esa lista se asigna a tu bean (listaM) y luego se usa en la página JSF para mostrar la retícula de materias.
        listaM = materiasCarrerasServicio.buscarMaterias(estudiante);
        List<HistoriaAlumno> listaHA = historiaAlumnoServicio.buscarAsignaturas(estudiante.getNoDeControl());
        double suma = 0;
        int contador = 0;

        for (int i = 0; i < listaHA.size(); i++) {
            HistoriaAlumno ha = listaHA.get(i);
            if (ha.getCalificacion() != null) { // por si alguna materia no tiene calificación aún
                suma += ha.getCalificacion();
                contador++;
            }
        }

        promedio = (contador > 0) ? (suma / contador) : 0;
        periodoActual = periodoEscolarServicio.buscarPorId(periodoEscolarServicio.periodoActual());
        listaGC = gruposServicio.buscarGruposCompletos(estudiante.getReticula(), estudiante.getSemestre(), periodoActual);

        System.out.println("----------------DATOS ENVIADOS PARA GC--------------");
        System.out.println("Tamaño lista listaGC.size:" + listaGC.size());
        System.out.println("Retícula: " + estudiante.getReticula());
        System.out.println("Semestre: " + estudiante.getSemestre());
        System.out.println("Periodo actual: " + periodoActual);
        System.out.println("-----------------------------------------------------");
        System.out.println("Promedio del alumno: " + promedio);

    }

    public void verHorarios(String grupo) {
        listaHorarioAsignatura = gruposServicio.buscarGruposPorCampoGrupoSeleccionada(estudiante.getReticula().getReticula(),
                estudiante.getSemestre(),
                periodoActual.getPeriodo(),
                grupo);
    }

    public void onMateriaClick(ReticulaDatos materia) {
        // Aquí recuperas la materia seleccionada desde el component binding
        System.out.println("Materia Escogida->" + materia + "disponible?:->" + materia.getDisponible());
        materiaDeTabla = materia;
        materiaDeTabla.setDisponible(true);

        System.out.println("Nombre:" + materiaDeTabla.getNombreMateria()
                + "Id:" + materiaDeTabla.getMateria()
                + "Creditos" + materiaDeTabla.getCreditos()
                + "Color:" + materiaDeTabla.getColor()
                + "Clave:" + materiaDeTabla.getClave()
                + "Calificacion" + materiaDeTabla.getCalificacion());

        materiaSeleccionada = materiasCarrerasServicio.buscarMateriasCarreraPorMateria(materiaDeTabla.getMateria());
        listaPeriodoEscolar = periodoEscolarServicio.periodosEscolaresActivos();
        System.out.println("---------------DATOS---------------------");
        System.out.println("Encontre:" + materiaSeleccionada);
        System.out.println("Retícula: " + materiaSeleccionada.getReticula().getReticula());
        System.out.println("Semestre: " + materiaSeleccionada.getSemestreReticula());
        System.out.println("Periodo: " + listaPeriodoEscolar.get(0).getPeriodo());
        System.out.println("Materia: " + materiaSeleccionada.getMateria().getMateria());

        listaHorarioAsignatura = gruposServicio.buscarGruposPorCampoMateriaSeleccionada(materiaSeleccionada.getReticula().getReticula(),
                materiaSeleccionada.getSemestreReticula(),
                listaPeriodoEscolar.get(0).getPeriodo(),
                materiaSeleccionada.getMateria().getMateria());
    }

    public void confirmarSeleccionGrupo() {

        for (int i = 0; i < listaHorarioAsignatura.size(); i++) {
            HorarioAsignatura hc = listaHorarioAsignatura.get(i);
            seleccionMateriasServicio.insertarNuevoSeleccionMaterias(
                    periodoEscolarServicio.periodoActual(),
                    hc);
        }

        grupoBloqueado = true;//Para bloquear los grupos si ya he seleccionado

        listaHorarioASeleccionadas = listaHorarioAsignatura;//Para actualizar la tabla que estoy seleccionando 

        for (int i = 0; i < listaM.size(); i++) {//Para cambiar el estado y color despues de seleccionar el grupo al cual voy a iniciar.
            Reticula r = listaM.get(i);
            ReticulaDatos rd = new ReticulaDatos();

            switch (estudiante.getSemestre()) {
                case 1:
                    rd = r.getSemestre1();
                    if (rd.getDisponible()) {//Por si hay una materia que ya fue cursada. 
                        rd.setDisponible(false);
                        rd.setColor("cursando");
                    }

                    break;
                case 2:
                    rd = r.getSemestre2();
                    if (rd.getDisponible()) {
                        rd.setDisponible(false);
                        rd.setColor("cursando");
                    }
                    break;
                case 3:
                    rd = r.getSemestre3();
                    if (rd.getDisponible()) {
                        rd.setDisponible(false);
                        rd.setColor("cursando");
                    }
                    break;
                case 4:
                    rd = r.getSemestre4();
                    if (rd.getDisponible()) {
                        rd.setDisponible(false);
                        rd.setColor("cursando");
                    }
                    break;
                case 5:
                    rd = r.getSemestre5();
                    if (rd.getDisponible()) {
                        rd.setDisponible(false);
                        rd.setColor("cursando");
                    }
                    break;
                case 6:
                    rd = r.getSemestre6();
                    if (rd.getDisponible()) {
                        rd.setDisponible(false);
                        rd.setColor("cursando");
                    }
                    break;
                case 7:
                    rd = r.getSemestre7();
                    if (rd.getDisponible()) {
                        rd.setDisponible(false);
                        rd.setColor("cursando");
                    }
                    break;
                case 8:
                    rd = r.getSemestre8();
                    if (rd.getDisponible()) {
                        rd.setDisponible(false);
                        rd.setColor("cursando");
                    }
                    break;
                case 9:
                    rd = r.getSemestre9();
                    if (rd.getDisponible()) {
                        rd.setDisponible(false);
                        rd.setColor("cursando");
                    }
                    break;
                default:
                    // En caso de que el semestre no esté entre 1 y 9
                    break;
            }
        }

    }

    public void onTabChange() { //Solo se activa Sleccion con grupos cuando todas las materias a escoger estan en azul en el semestre del alumno.
        int n = 0;
        for (int i = 0; i < listaM.size(); i++) {//Para cambiar el estado y color despues de seleccionar el grupo al cual voy a iniciar.
            Reticula r = listaM.get(i);
            ReticulaDatos rd = new ReticulaDatos();

            switch (estudiante.getSemestre()) {
                case 1:
                    rd = r.getSemestre1();
                    if (rd.getDisponible()) {
                        n++;
                    }

                    break;
                case 2:
                    rd = r.getSemestre2();
                    if (rd.getDisponible()) {
                        n++;
                    }

                    break;
                case 3:
                    rd = r.getSemestre3();
                    if (rd.getDisponible()) {
                        n++;
                    }

                    break;
                case 4:
                    rd = r.getSemestre4();
                    if (rd.getDisponible()) {
                        n++;
                    }
                    break;
                case 5:
                    rd = r.getSemestre5();
                    if (rd.getDisponible()) {
                        n++;
                    }
                    break;
                case 6:
                    rd = r.getSemestre6();
                    if (rd.getDisponible()) {
                        n++;
                    }
                    break;
                case 7:
                    rd = r.getSemestre7();
                    if (rd.getDisponible()) {
                        n++;
                    }
                    break;
                case 8:
                    rd = r.getSemestre8();
                    if (rd.getDisponible()) {
                        n++;
                    }

                    break;
                case 9:
                    rd = r.getSemestre9();
                    if (rd.getDisponible()) {
                        n++;
                    }
                    break;
                default:
                    // En caso de que el semestre no esté entre 1 y 9
                    break;
            }

        }

        switch (estudiante.getSemestre()) {
            case 1:
                if (n == 6) {
                    grupoBloqueado = false;
                } else {
                    grupoBloqueado = true;
                }
                break;
            case 2:

                if (n == 6) {
                    grupoBloqueado = false;
                } else {
                    grupoBloqueado = true;
                }

                break;
            case 3:
                if (n == 7) {
                    grupoBloqueado = false;
                } else {
                    grupoBloqueado = true;
                }
                break;
            case 4:
                if (n == 7) {
                    grupoBloqueado = false;
                } else {
                    grupoBloqueado = true;
                }

                break;
            case 5:
                if (n == 6) {
                    grupoBloqueado = false;
                } else {
                    grupoBloqueado = true;
                }

                break;
            case 6:
                if (n == 7) {
                    grupoBloqueado = false;
                } else {
                    grupoBloqueado = true;
                }

                break;
            case 7:
                if (n == 7) {
                    grupoBloqueado = false;
                } else {
                    grupoBloqueado = true;
                }
                break;
            case 8:
                if (n == 7) {
                    grupoBloqueado = false;
                } else {
                    grupoBloqueado = true;
                }
                break;
            case 9:
                grupoBloqueado = true;
               // addMessage(FacesMessage.SEVERITY_INFO, "RESIDENCIAS", "SELECCIONA DESDE RETICULA");
                break;
            default:
                grupoBloqueado = true;
               // addMessage(FacesMessage.SEVERITY_INFO, "GRUPOS NO DISPONIBLES", "SELECCIONA DESDE RETICULA");
                // En caso de que el semestre no esté entre 1 y 9
                break;
        }
        // grupoBloqueado = true;//Para bloquear los grupos si ya he seleccionado

    }

   /* public void addMessage(FacesMessage.Severity severity, String titulo, String detalle) {//Para mostrar mensaje en ventana
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, titulo, detalle));
    }*/

    public void onSeleccionarGrupo() {

        System.out.println("===============SELECCIONADO==================");
        System.out.println("Seleccionado: " + grupoSeleccionado);
        System.out.println("Grupo: " + grupoSeleccionado.getGrupo());
        System.out.println("IdGrupo: " + grupoSeleccionado.getId());
        System.out.println("Materia: " + grupoSeleccionado.getMateria());
        System.out.println("Asignatura: " + grupoSeleccionado.getAsignatura());
        System.out.println("Docente: " + grupoSeleccionado.getDocente());
        System.out.println("Lunes: " + grupoSeleccionado.getLunes());
        System.out.println("Martes: " + grupoSeleccionado.getMartes());
        System.out.println("Miércoles: " + grupoSeleccionado.getMiercoles());
        System.out.println("Jueves: " + grupoSeleccionado.getJueves());
        System.out.println("Viernes: " + grupoSeleccionado.getViernes());
        System.out.println("Sábado: " + grupoSeleccionado.getSabado());
        System.out.println("Global: " + grupoSeleccionado.getGlobal());

        System.out.println("================SELECCIONADO=================");
        materiaDeTabla.setColor("cursando");
        materiaDeTabla.setDisponible(false);

        seleccionMateriasServicio.insertarNuevoSeleccionMaterias(
                listaPeriodoEscolar.get(0).getPeriodo(),
                grupoSeleccionado);

        listaHorarioASeleccionadas.add(grupoSeleccionado);

    }
}
