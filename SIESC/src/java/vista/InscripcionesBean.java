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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.AvisosReinscripcion;
import modelo.Estudiante;
import modelo.HistoriaAlumno;
import modelo.HorarioAsignatura;
import modelo.Horarios;
import modelo.MateriasCarreras;
import modelo.PeriodoEscolar;
import modelo.Reticula;
import modelo.ReticulaDatos;
import modelo.SeleccionMaterias;
import servicio.AvisosReinscripcionServicioLocal;
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
    private AvisosReinscripcionServicioLocal avisosReinscripcionServicio;

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
    private List<SeleccionMaterias> listaSM = new ArrayList();
    private List<String> listaGC = new ArrayList();
    private ArrayList<Reticula> listaM = new ArrayList();

    private HorarioAsignatura grupoSeleccionado;
    private AvisosReinscripcion vr;
    private Estudiante estudiante;
    private MateriasCarreras materiaSeleccionada;
    private PeriodoEscolar periodoActual;
    private ReticulaDatos materiaDeTabla;

    private String periodo;
    private String materia;
    private double promedio;
    private Boolean grupoBloqueado = false;
    private String grupo = "";
    private int creditosSeleccionados = 0;

    private int Mrojas = 0, Mamarillas = 0, Mnaranjas = 0, Mazules = 0;
    List<int[]> listaRojas = new ArrayList<>();
    List<int[]> listaNaranjas = new ArrayList<>();
    List<int[]> listaAmarillas = new ArrayList<>();
    List<int[]> listaAzules = new ArrayList<>();

    public AvisosReinscripcion getVr() {
        return vr;
    }

    public void setVr(AvisosReinscripcion vr) {
        this.vr = vr;
    }

    public List<int[]> getListaRojas() {
        return listaRojas;
    }

    public void setListaRojas(List<int[]> listaRojas) {
        this.listaRojas = listaRojas;
    }

    public List<int[]> getListaNaranjas() {
        return listaNaranjas;
    }

    public void setListaNaranjas(List<int[]> listaNaranjas) {
        this.listaNaranjas = listaNaranjas;
    }

    public List<int[]> getListaAmarillas() {
        return listaAmarillas;
    }

    public void setListaAmarillas(List<int[]> listaAmarillas) {
        this.listaAmarillas = listaAmarillas;
    }

    public List<int[]> getListaAzules() {
        return listaAzules;
    }

    public void setListaAzules(List<int[]> listaAzules) {
        this.listaAzules = listaAzules;
    }

    public int getMrojas() {
        return Mrojas;
    }

    public void setMrojas(int Mrojas) {
        this.Mrojas = Mrojas;
    }

    public int getMamarillas() {
        return Mamarillas;
    }

    public void setMamarillas(int Mamarillas) {
        this.Mamarillas = Mamarillas;
    }

    public int getMnaranjas() {
        return Mnaranjas;
    }

    public void setMnaranjas(int Mnaranjas) {
        this.Mnaranjas = Mnaranjas;
    }

    public int getMazules() {
        return Mazules;
    }

    public void setMazules(int Mazules) {
        this.Mazules = Mazules;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

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
        return listaHorarioAsignatura;
    }

    public void setListaHorarioAsignatura(List<HorarioAsignatura> listaHorarioAsignatura) {
        this.listaHorarioAsignatura = listaHorarioAsignatura;
    }

    // Metodos 
    public void iniciarInscripcion() {
        //Esa lista se asigna a tu bean (listaM) y luego se usa en la página JSF para mostrar la retícula de materias.
        listaM = materiasCarrerasServicio.buscarMaterias(estudiante);
        periodoActual = periodoEscolarServicio.buscarPorId(periodoEscolarServicio.periodoActual()); 
        ///-----------------------------------------------------------
        PeriodoEscolar prueba = periodoEscolarServicio.buscarPorId("20251");//Componer porque no hay como tal 
        //............................................................
        vr = avisosReinscripcionServicio.buscarAvisoReinscripcion(estudiante, prueba);//Para conseguir el limite de creditos
        guardandoPosicionesDeCadaTipoDeColor();//Contar cuantas materias hay de cada color.
        validaciones();
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        List<HistoriaAlumno> listaHA = historiaAlumnoServicio.buscarAsignaturas(estudiante.getNoDeControl());
        promedio = estudiante.getPromedioAritmeticoAcumulado();
        listaGC = gruposServicio.buscarGruposCompletos(estudiante.getReticula(), estudiante.getSemestre(), periodoActual);
        /*int contador = 0;
        double suma = 0;
        for (int i = 0; i < listaHA.size(); i++) {
            HistoriaAlumno ha = listaHA.get(i);
            if (ha.getCalificacion() != null) { // por si alguna materia no tiene calificación aún
                suma += ha.getCalificacion();
                contador++;
            }
        }
        
        System.out.println("----------------DATOS ENVIADOS PARA GC--------------");
        System.out.println("Tamaño lista listaGC.size:" + listaGC.size());
        System.out.println("Retícula: " + estudiante.getReticula());
        System.out.println("Semestre: " + estudiante.getSemestre());
        System.out.println("Periodo actual: " + periodoActual);
        System.out.println("-----------------------------------------------------");
        System.out.println("Promedio del alumno: " + promedio);
         */

    }

    public void validaciones() {

        int semestreAlumno = estudiante.getSemestre();
        int s = 0;
        if (Mrojas != 0) {
            grupoBloqueado = true;
            addMessage(FacesMessage.SEVERITY_ERROR, "CARRERA REPROBADA", "NO PUEDES SELECCIONAR NINGUNA MATERIA");
            return;
        }
        if (Mnaranjas != 0) {
            grupoBloqueado = true;
            addMessage(FacesMessage.SEVERITY_WARN, "PRIORIDAD  MATERIA(S) EN REPETICION", "SELECCIONA LAS MATERIAS EN REPETICION MAX. 2");
            for (int i = 0; i < listaNaranjas.size(); i++) {
                Reticula r = listaM.get(listaNaranjas.get(i)[0]);
                try {
                    s = listaNaranjas.get(i)[1];
                    // Obtener el semestre actual dinámicamente
                    ReticulaDatos rd = (ReticulaDatos) r.getClass()
                            .getMethod("getSemestre" + s)
                            .invoke(r);
                    if (rd != null) {
                        rd.setDisponible(true);

                    }
                } catch (Exception e) {
                    System.out.println("Error al obtener Semestre " + s + ": " + e.getMessage());
                }

            }

            if (listaNaranjas.size() >= 2) {
                return;
            }

        }
        if (Mamarillas != 0) {
            grupoBloqueado = true;
            addMessage(FacesMessage.SEVERITY_WARN, "PRIORIDAD MATERIA(S) EN ORDINARIO", "SELECCIONA TODAS LAS MATERIAS EN ORDINARIO ");
            for (int i = 0; i < listaAmarillas.size(); i++) {
                Reticula r = listaM.get(listaAmarillas.get(i)[0]);
                try {
                    s = listaAmarillas.get(i)[1];
                    // Obtener el semestre actual dinámicamente
                    ReticulaDatos rd = (ReticulaDatos) r.getClass()
                            .getMethod("getSemestre" + s)
                            .invoke(r);
                    if (rd != null) {
                        rd.setDisponible(true);
                    }
                } catch (Exception e) {
                    System.out.println("Error al obtener Semestre " + s + ": " + e.getMessage());
                }

            }
        }
        if (Mazules != 0) {
            addMessage(FacesMessage.SEVERITY_INFO, "MATERIAS DISPONIBLES ", "SELECCIONA TUS MATERIAS");
            // Caso 1: el semestre del alumno es PAR y s es IMPAR

            for (int i = 0; i < listaAzules.size(); i++) {
                Reticula r = listaM.get(listaAzules.get(i)[0]);
                try {
                    s = listaAzules.get(i)[1];

                    // Obtener el semestre actual dinámicamente
                    ReticulaDatos rd = (ReticulaDatos) r.getClass()
                            .getMethod("getSemestre" + s)
                            .invoke(r);

                    if (rd != null) {
                        // Caso 1: el semestre del alumno es PAR y el semestre de la materia es IMPAR
                        if (semestreAlumno % 2 == 0 && s % 2 != 0) {
                            if (rd.getDisponible() && !"blanco".equals(rd.getColor())) {
                                rd.setDisponible(false);
                                rd.setColor("nodisponible");
                                System.out.println("Desactivado semestre " + s + " (Alumno par, materia impar)");
                            }
                        } // Caso 2: el semestre del alumno es IMPAR y el semestre de la materia es PAR
                        else if (semestreAlumno % 2 != 0 && s % 2 == 0) {
                            if (rd.getDisponible() && !"blanco".equals(rd.getColor())) {
                                rd.setDisponible(false);
                                rd.setColor("nodisponible");
                                System.out.println("Desactivado semestre " + s + " (Alumno impar, materia par)");
                            }
                        } // Caso 3 (opcional): si coincide par-par o impar-impar, se activa
                        else {
                            rd.setDisponible(true);
                            rd.setColor("disponible");
                            System.out.println("Activado semestre " + s + " (coincide paridad)");
                        }

                    } else {
                        System.out.println("Semestre " + s + ": vacío");
                    }

                } catch (Exception e) {
                    System.out.println("Error al obtener Semestre " + s + ": " + e.getMessage());
                }
            }

        }
    }

    public void guardandoPosicionesDeCadaTipoDeColor() {//

        for (int j = 0; j < listaM.size(); j++) { // Recorro cada retícula
            Reticula r = listaM.get(j);
            System.out.println("===== Retícula #" + (j + 1) + " =====");

            for (int s = 1; s <= 9; s++) { // Recorro los 9 semestres
                try {
                    // Obtener el semestre actual dinámicamente
                    ReticulaDatos rd = (ReticulaDatos) r.getClass()
                            .getMethod("getSemestre" + s)
                            .invoke(r);

                    if (rd != null) {
                        System.out.println("Semestre " + s + ": " + rd.getNombreMateria());
                        rd.setDisponible(false);

                        if ("especial".equals(rd.getColor())) {
                            Mrojas++;
                            listaRojas.add(new int[]{j, s});
                        }

                        if ("repeticion".equals(rd.getColor())) {
                            Mnaranjas++;
                            listaNaranjas.add(new int[]{j, s});
                        }

                        if ("ordinario".equals(rd.getColor())) {
                            Mamarillas++;
                            listaAmarillas.add(new int[]{j, s});
                        }

                        if ("disponible".equals(rd.getColor())) {
                            Mazules++;
                            listaAzules.add(new int[]{j, s});
                        }

                    } else {
                        System.out.println("Semestre " + s + ": vacío");
                    }

                } catch (Exception e) {
                    System.out.println("Error al obtener Semestre " + s + ": " + e.getMessage());
                }
            }
            System.out.println("-----------------------------------");
        }
    }

    public void verHorarios(String grupo) {
        this.grupo = grupo;
        listaHorarioAsignatura = gruposServicio.buscarGruposPorCampoGrupoSeleccionada(estudiante.getReticula().getReticula(),
                estudiante.getSemestre(),
                periodoActual.getPeriodo(),
                grupo);
    }

    public void onMateriaClick(ReticulaDatos materia) {
        // Aquí recuperas la materia seleccionada desde el component binding
        System.out.println("Materia Escogida->" + materia + "disponible?:->" + materia.getDisponible());
        addMessage(FacesMessage.SEVERITY_INFO, "MATERIA SELECCIONADA", materia.getNombreMateria());
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

        grupoBloqueado = true;//Para bloquear los grupos si ya he seleccionado

        listaHorarioASeleccionadas = listaHorarioAsignatura;//Para actualizar la tabla que estoy seleccionando 
        restaurarHorarioReticula();
        //cambiarEstadoMateriasSeleccionadasPorGrupo();
        addMessage(FacesMessage.SEVERITY_INFO, "GRUPO SELECCIONADO", "HAZ SELECCIONADO:" + grupo);
    }

    /*public void cambiarEstadoMateriasSeleccionadasPorGrupo() {
        for (int i = 0; i < listaM.size(); i++) {//Para cambiar el estado y color despues de seleccionar el grupo al cual voy a iniciar.
            Reticula r = listaM.get(i);
            ReticulaDatos rd;
            switch (estudiante.getSemestre()) {
                case 1:
                    rd = r.getSemestre1();
                    if (rd.getDisponible()) {//Por si hay una materia que ya fue cursada. 
                        rd.setDisponible(false);
                        rd.setColor("cursando");
                    } else {

                        rd.setDisponible(true);
                        rd.setColor("disponible");

                    }

                    break;
                case 2:
                    rd = r.getSemestre2();
                    if (rd.getDisponible()) {
                        rd.setDisponible(false);
                        rd.setColor("cursando");
                    } else {

                        rd.setDisponible(true);
                        rd.setColor("disponible");
                    }
                    break;
                case 3:
                    rd = r.getSemestre3();
                    if (rd.getDisponible()) {
                        rd.setDisponible(false);
                        rd.setColor("cursando");
                    } else {

                        rd.setDisponible(true);
                        rd.setColor("disponible");
                    }
                    break;
                case 4:
                    rd = r.getSemestre4();
                    if (rd.getDisponible()) {
                        rd.setDisponible(false);
                        rd.setColor("cursando");
                    } else {

                        rd.setDisponible(true);
                        rd.setColor("disponible");
                    }
                    break;
                case 5:
                    rd = r.getSemestre5();
                    if (rd.getDisponible()) {
                        rd.setDisponible(false);
                        rd.setColor("cursando");
                    } else {

                        rd.setDisponible(true);
                        rd.setColor("disponible");
                    }
                    break;
                case 6:
                    rd = r.getSemestre6();
                    if (rd.getDisponible()) {
                        rd.setDisponible(false);
                        rd.setColor("cursando");
                    } else {

                        rd.setDisponible(true);
                        rd.setColor("disponible");
                    }
                    break;
                case 7:
                    rd = r.getSemestre7();
                    if (rd.getDisponible()) {
                        rd.setDisponible(false);
                        rd.setColor("cursando");
                    } else {

                        rd.setDisponible(true);
                        rd.setColor("disponible");
                    }
                    break;
                case 8:
                    rd = r.getSemestre8();
                    if (rd.getDisponible()) {
                        rd.setDisponible(false);
                        rd.setColor("cursando");
                    } else {

                        rd.setDisponible(true);
                        rd.setColor("disponible");
                    }
                    break;
                case 9:
                    rd = r.getSemestre9();
                    if (rd.getDisponible()) {
                        rd.setDisponible(false);
                        rd.setColor("cursando");
                    } else {
                        rd.setDisponible(true);
                        rd.setColor("disponible");
                    }
                    break;
                default:
                    // En caso de que el semestre no esté entre 1 y 9
                    break;
            }
        }

    }*/
    public void restaurarHorarioReticula() {

        for (int i = 0; i < listaHorarioASeleccionadas.size(); i++) {//Recorro para encontrar todas las materias 
            HorarioAsignatura ha = listaHorarioASeleccionadas.get(i);

            for (int j = 0; j < listaM.size(); j++) {//Recorro cada reticula de la tabla
                Reticula r = listaM.get(j);

                System.out.println("===== Retícula #" + (j + 1) + " =====");

                // Recorremos los 9 semestres dinámicamente
                for (int s = 1; s <= 9; s++) {//Recorro los semestres de cada reticula
                    try {
                        // Obtener el semestre actual usando reflexión
                        ReticulaDatos rd = (ReticulaDatos) r.getClass()
                                .getMethod("getSemestre" + s)
                                .invoke(r);

                        if (rd != null) {
                            // Aquí puedes guardar o procesar el objeto rd
                            System.out.println("Semestre " + s + ": " + rd.getNombreMateria());
                            System.out.println("HorarioAsignaturaGetMateria->" + ha.getMateria());
                            System.out.println("ReticulaDatosGetMateria->" + rd.getMateria());

                            if (ha.getMateria().equals(rd.getMateria())) {

                                if (grupoBloqueado) {
                                    rd.setDisponible(false);
                                    rd.setColor("cursando");
                                } else {
                                    rd.setDisponible(true);
                                    rd.setColor("disponible");
                                }

                            }
                        } else {
                            System.out.println("Semestre " + s + ": vacío");
                        }

                    } catch (Exception e) {
                        System.out.println("Error al obtener Semestre " + s + ": " + e.getMessage());
                    }
                }

                System.out.println("-----------------------------------");
            }

        }

    }

    public void guardarHorario() {

        for (int i = 0; i < listaHorarioASeleccionadas.size(); i++) {
            HorarioAsignatura hc = listaHorarioASeleccionadas.get(i);
            seleccionMateriasServicio.insertarNuevoSeleccionMaterias(
                    estudiante.getNoDeControl(),
                    hc);
        }

        addMessage(FacesMessage.SEVERITY_INFO, "HORARIO GUARDADO", "SE HA GUARDADO EL HORARIO");
        creditosSeleccionados = 0; //Para contabilizar de nuevo
    }

    public void eliminarHorario() {//Mañana 06-11-2025
        addMessage(FacesMessage.SEVERITY_INFO, "HORARIO ELIMINADO", "SE HA ELIMINADO EL HORARIO");
        /*if (grupoBloqueado) {//para avisar que solo sea por grupo
            cambiarEstadoMateriasSeleccionadasPorGrupo();
        }*/
        grupoBloqueado = false;//Para bloquear los grupos si ya he seleccionado
        restaurarHorarioReticula();
        listaHorarioASeleccionadas = new ArrayList();

        creditosSeleccionados = 0;//Para reinicializacion. 

    }

    public void onTabChange() { //Solo se activa Sleccion con grupos cuando todas las materias a escoger estan en azul en el semestre del alumno.
        int n = 0;
        for (int i = 0; i < listaM.size(); i++) {//Para cambiar el estado y color despues de seleccionar el grupo al cual voy a iniciar.
            Reticula r = listaM.get(i);
            ReticulaDatos rd = new ReticulaDatos();

            switch (estudiante.getSemestre()) {//contabilizamos cuantas materias existen seleccionadas porque solamente puedes seleccionar por grupo cuando todas las materias de un semestre esten en azul, 
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

        switch (estudiante.getSemestre()) {//Verificando si es necesario desactivar la seleccion por grupo o no despues de contabilizar.
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
                addMessage(FacesMessage.SEVERITY_INFO, "RESIDENCIAS", "SELECCIONA DESDE RETICULA");
                break;
            default:
                grupoBloqueado = true;
                addMessage(FacesMessage.SEVERITY_INFO, "GRUPOS NO DISPONIBLES", "SELECCIONA DESDE RETICULA");
                // En caso de que el semestre no esté entre 1 y 9
                break;
        }

        if (!grupoBloqueado) {
            addMessage(FacesMessage.SEVERITY_INFO, "HORARIO POR GRUPO", "SELECCIONA UN GRUPO");
        }
        if (estudiante.getCreditosAprobados()==estudiante.getCreditosCursados()) {
            grupoBloqueado=false;//Si ambos creditos aprobados o cursados son iguales si puedo seleccionar grupo. 
            
        }else {
            grupoBloqueado=true;
         addMessage(FacesMessage.SEVERITY_WARN, "CREDITOS NO ALCANZADOS", "NO HAZ ALCANZADO LOS CREDITOS SUFICIENTE");
        }

        // grupoBloqueado = true;//Para bloquear los grupos si ya he seleccionado
    }

    public void addMessage(FacesMessage.Severity severity, String titulo, String detalle) {//Para mostrar mensaje en ventana
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, titulo, detalle));
    }

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
          creditosSeleccionados += Integer.parseInt(materiaDeTabla.getCreditos());
        System.out.println("creditosSeleccionados->" + creditosSeleccionados + "creditosAutorizados->" + vr.getCreditosAutorizados());
       
        if (creditosSeleccionados <= vr.getCreditosAutorizados()) {
            materiaDeTabla.setColor("cursando");
            materiaDeTabla.setDisponible(false);
            addMessage(FacesMessage.SEVERITY_INFO, "HORARIO SELECCIONADO GRUPO " + grupoSeleccionado.getGrupo(), grupoSeleccionado.getAsignatura());
            listaHorarioASeleccionadas.add(grupoSeleccionado);
           
        } else {
            addMessage(FacesMessage.SEVERITY_WARN, "CREDITOS AUTORIZADOS ALCANZADOS", "NO PUEDES SELECCIONAR MAS MATERIAS");
        }

    }
}
