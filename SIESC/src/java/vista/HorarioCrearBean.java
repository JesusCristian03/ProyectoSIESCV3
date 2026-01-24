/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package vista;

import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import modelo.Aulas;
import modelo.Carrera;
import modelo.Grupos;
import modelo.HorarioCrear;
import modelo.Horarios;
import modelo.Materia;
import modelo.MateriasCarreras;
import modelo.Organigrama;
import modelo.PeriodoEscolar;
import modelo.Permisos;
import modelo.Personal;
import modelo.Usuario;
import org.primefaces.PrimeFaces;
import servicio.AulasServicioLocal;
import servicio.CarreraServicioLocal;
import servicio.GruposServicioLocal;
import servicio.HorarioServicioLocal;
import servicio.MateriaServicioLocal;
import servicio.MateriasCarrerasServicioLocal;
import servicio.OrganigramaServicioLocal;
import servicio.PeriodoEscolarServicioLocal;
import servicio.PermisoServicioLocal;
import servicio.PersonalServicioLocal;

/**
 *
 * @author gacev
 */
@Named(value = "horarioCrearBean")
@ViewScoped
public class HorarioCrearBean implements Serializable {

    @EJB
    private MateriaServicioLocal materiaServicio;

    @EJB
    private PersonalServicioLocal personalServicio;
    @EJB
    private OrganigramaServicioLocal organigramaServicio;
    @EJB
    private AulasServicioLocal aulasServicio;
    @EJB
    private PermisoServicioLocal permisoServicio;
    @EJB
    private MateriasCarrerasServicioLocal materiasCarrerasServicio;
    @EJB
    private PeriodoEscolarServicioLocal periodoEscolarServicio;
    @EJB
    private CarreraServicioLocal carreraServicio;
    @EJB
    private HorarioServicioLocal horarioServicio;
    @EJB
    private GruposServicioLocal gruposServicio;

    public void resetearSiEsRecarga() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (!fc.isPostback()) {
            // Listas
            System.out.println("Me reinicie");
            listaHorarioCrear = new ArrayList<>();
            listaHorarioBorrar = new ArrayList<>();
            listaCarrera = new ArrayList<>();
            listaPermisos = new ArrayList<>();
            listaPeriodoEscolar = new ArrayList<>();
            listaMateria = new ArrayList<>();
            listaSemestres = new ArrayList<>();
            listaAulas = new ArrayList<>();
            listagrupos = new ArrayList<>();
            listagrupogenerado = new ArrayList<>();
            listapersonal = new ArrayList<>();
            listaMateriaI = new ArrayList<>();
            listaHorarios = null;
            listaHorariosGenerados = new ArrayList<>();
            listaHorariosEmpalme = new ArrayList<>();
            listacoordenadas = new ArrayList<>();
            horariosAEliminar = new ArrayList<>();

            // Optional
            horarioExacto = Optional.empty();

            // Objetos
            usuario = new Usuario();
            grupo = new Grupos();
            horario = new Horarios();
            carreraI = new Carrera();
            materia = new Materia();
            materiasCarreras = new MateriasCarreras();
            periodo = new PeriodoEscolar();
            aula = new Aulas();
            aulaBuscada = new Aulas();
            horariocrear = new HorarioCrear();
            organigrama = new Organigrama();
            materiaSeleccionada = new Horarios();

            // Banderas booleanas
            puedeguardar = false;
            camposllenos = false;
            modoIntercambio = false;
            modoSeleccionMateria = false;

            booleanBuscarAula = false;
            booleanBuscarGrupo = false;

            booleanBotonInsertar = false;
            booleanBotonEliminar = false;
            booleanBotonModificar = false;
            booleanBotonIntercambiar = false;
            booleanBotonAula = false;
            booleanBotonGrupo = false;

            booleanCampoCarrera = false;
            booleanCampoSemestre = false;
            booleanCampoAula = false;
            booleanCampoPeriodo = false;
            booleanCampoGrupo = false;

            seleccionadocarreramateria2 = false;
            eliminando = false;
            desactivarCampos = false;
            activarModificar = false;

            // Variables String
            nombreMateriaSeleccionada = "";
            aulaMateriaSeleccionada = "";
            nuevaAulaSeleccionada = "";
            carreraS = "";
            asignaturaS = "";
            posicionesSeleccionadas = "";
            periodoS = "";
            semestreS = "";
            aulas = "";
            grupoS = "";
            personal = "";
            aulaDestino = "";
            valorgrupo = "";

            carreraSeleccionada = "";
            semestreSeleccionada = "";
            valorGrupoSeleccionada = "";

            // Variables numéricas
            filaMateriaSeleccionada = 0;
            columnaMateriaSeleccionada = 0;
            filaSeleccionadaAnterior = -1;
            columnaSeleccionadaAnterior = -1;

            filaDestino = 0;
            columnaDestino = 0;

            creditos = 0;
            numestudiantes = 0;

            // Fechas
            horainicioMateria = null;
            horainiciofinMateria = null;
            nuevoHorario();
        }

    }

    private List<HorarioCrear> listaHorarioCrear;
    private List<HorarioCrear> listaHorarioBorrar;//Muestra los horarios que va a eliminar.

    private List<Carrera> listaCarrera;
    private List<Permisos> listaPermisos;
    private List<PeriodoEscolar> listaPeriodoEscolar;
    private List<MateriasCarreras> listaMateria;
    private ArrayList<String> listaSemestres;
    private List<Aulas> listaAulas;
    private List<Grupos> listagrupos;
    private List<Grupos> listagrupogenerado;
    private List<Personal> listapersonal;
    private List<Materia> listaMateriaI;
    private List<Horarios> listaHorarios = null; //Para guardar los horarios en la BD
    private List<Horarios> listaHorariosGenerados; //Para guardar los horarios en la BD
    private List<Horarios> listaHorariosEmpalme; //Para guardar los horarios en la BD
    private List<String> listacoordenadas;
    private List<Horarios> horariosAEliminar;
    private Optional<Horarios> horarioExacto;// Busco el horario el cual voy a actualizar. 

    private Usuario usuario;
    private Grupos grupo;
    private Horarios horario;
    private Carrera carreraI;
    private Materia materia;
    private MateriasCarreras materiasCarreras;
    private PeriodoEscolar periodo;
    private Aulas aula, aulaBuscada;
    private HorarioCrear horariocrear;
    private Boolean puedeguardar = false;
    private Boolean camposllenos = false;//Verifico que todos los campos tengan algo en la vista.
    private Organigrama organigrama;
    private Horarios materiaSeleccionada;
    private boolean modoIntercambio = false;//Voy a intercambiar a modo horarios
    private Boolean modoSeleccionMateria = false;//La uso para seleccionar o deseleccionar la materia de la tabla. 

    private String nombreMateriaSeleccionada;//Sirve para el modo eliminar y modificar materia obtiene nombre de la materiaSeleccionada
    private String aulaMateriaSeleccionada;//Sirve para el modo eliminar y modificar materia obtiene aula de la materiaselccionada
    private String carreraSeleccionada;
    private String semestreSeleccionada;
    private String valorGrupoSeleccionada;

    private String nuevaAulaSeleccionada;//Es el aula que se ha escogido dentro de la ventana emergente de modificar materia. 
    private int filaMateriaSeleccionada;//Fila seleccionada para modificar horario
    private int columnaMateriaSeleccionada;  //Columna seleccionada para modificar horario
    private int filaSeleccionadaAnterior = -1;
    private int columnaSeleccionadaAnterior = -1;

    private int filaDestino;//Columnas donde sera pasado la materia.
    private int columnaDestino;//Columnas donde será pasado la materia. 

    private int creditos = 0;
    private String carreraS = "";//CarreraS de la lista de carreras 
    private String asignaturaS = "";//AsignaturaS de la lista asignaturas
    private String posicionesSeleccionadas = "";//Posicones que se han seleccionado en la tabla para guardar los horarios
    private String periodoS = "";//PeriodoS sirve para guardar el periodo 20252
    private String semestreS = "";//SemestreS sirve para guardar el numero del semestre el cual se ha seleccionado
    private String aulas = "";//Aulas sirve para guardar el valor del aula que se ha seleccionado 
    private String grupoS = "";//Grupos sirve para guardar el valor del grupo que se ha seleccionado. 
    private String personal = "";
    private String aulaDestino = "";
    private int numestudiantes = 0;
    private String valorgrupo = "";
    private Boolean seleccionadocarreramateria2 = false;
    private boolean eliminando = false;
    private Boolean desactivarCampos = false;
    private Boolean activarModificar = false;
    private Boolean booleanBuscarAula = false, booleanBuscarGrupo = false; //Para buscar el horario por grupo o por aula. 

    private Boolean booleanBotonInsertar = false, booleanBotonEliminar = false, booleanBotonModificar = false,
            booleanBotonIntercambiar = false, booleanBotonAula = false, booleanBotonGrupo = false;
    private Boolean booleanCampoCarrera = false, booleanCampoSemestre = false, booleanCampoAula = false,
            booleanCampoPeriodo = false, booleanCampoGrupo = false;//Son todas las validaciones de los campos.

    Date horainicioMateria;
    Date horainiciofinMateria;

    public String getCarreraSeleccionada() {
        return carreraSeleccionada;
    }

    public void setCarreraSeleccionada(String carreraSeleccionada) {
        this.carreraSeleccionada = carreraSeleccionada;
    }

    public String getSemestreSeleccionada() {
        return semestreSeleccionada;
    }

    public void setSemestreSeleccionada(String semestreSeleccionada) {
        this.semestreSeleccionada = semestreSeleccionada;
    }

    public String getValorGrupoSeleccionada() {
        return valorGrupoSeleccionada;
    }

    public void setValorGrupoSeleccionada(String valorGrupoSeleccionada) {
        this.valorGrupoSeleccionada = valorGrupoSeleccionada;
    }

    public Boolean getDesactivarCampos() {
        return desactivarCampos;
    }

    public void setDesactivarCampos(Boolean desactivarCampos) {
        this.desactivarCampos = desactivarCampos;
    }

    public Aulas getAulaBuscada() {
        return aulaBuscada;
    }

    public void setAulaBuscada(Aulas aulaBuscada) {
        this.aulaBuscada = aulaBuscada;
    }

    public String getAulaDestino() {
        return aulaDestino;
    }

    public void setAulaDestino(String aulaDestino) {
        this.aulaDestino = aulaDestino;
    }

    public List<Horarios> getListaHorariosEmpalme() {
        return listaHorariosEmpalme;
    }

    public void setListaHorariosEmpalme(List<Horarios> listaHorariosEmpalme) {
        this.listaHorariosEmpalme = listaHorariosEmpalme;
    }

    public Boolean getBooleanBotonAula() {
        return booleanBotonAula;
    }

    public void setBooleanBotonAula(Boolean booleanBotonAula) {
        this.booleanBotonAula = booleanBotonAula;
    }

    public Boolean getBooleanBotonGrupo() {
        return booleanBotonGrupo;
    }

    public void setBooleanBotonGrupo(Boolean booleanBotonGrupo) {
        this.booleanBotonGrupo = booleanBotonGrupo;
    }

    public Boolean getBooleanBotonInsertar() {
        return booleanBotonInsertar;
    }

    public void setBooleanBotonInsertar(Boolean booleanBotonInsertar) {
        this.booleanBotonInsertar = booleanBotonInsertar;
    }

    public Boolean getBooleanBotonEliminar() {
        return booleanBotonEliminar;
    }

    public void setBooleanBotonEliminar(Boolean booleanBotonEliminar) {
        this.booleanBotonEliminar = booleanBotonEliminar;
    }

    public Boolean getBooleanBotonModificar() {
        return booleanBotonModificar;
    }

    public void setBooleanBotonModificar(Boolean booleanBotonModificar) {
        this.booleanBotonModificar = booleanBotonModificar;
    }

    public Boolean getBooleanBotonIntercambiar() {
        return booleanBotonIntercambiar;
    }

    public void setBooleanBotonIntercambiar(Boolean booleanBotonIntercambiar) {
        this.booleanBotonIntercambiar = booleanBotonIntercambiar;
    }

    public Boolean getBooleanBuscarAula() {
        return booleanBuscarAula;
    }

    public void setBooleanBuscarAula(Boolean booleanBuscarAula) {
        this.booleanBuscarAula = booleanBuscarAula;
    }

    public Boolean getBooleanBuscarGrupo() {
        return booleanBuscarGrupo;
    }

    public void setBooleanBuscarGrupo(Boolean booleanBuscarGrupo) {
        this.booleanBuscarGrupo = booleanBuscarGrupo;
    }

    public List<HorarioCrear> getListaHorarioBorrar() {
        return listaHorarioBorrar;
    }

    public void setListaHorarioBorrar(List<HorarioCrear> listaHorarioBorrar) {
        this.listaHorarioBorrar = listaHorarioBorrar;
    }

    public List<Horarios> getHorariosAEliminar() {
        return horariosAEliminar;
    }

    public void setHorariosAEliminar(List<Horarios> horariosAEliminar) {
        this.horariosAEliminar = horariosAEliminar;
    }

    public int getFilaDestino() {
        return filaDestino;
    }

    public void setFilaDestino(int filaDestino) {
        this.filaDestino = filaDestino;
    }

    public int getColumnaDestino() {
        return columnaDestino;
    }

    public void setColumnaDestino(int columnaDestino) {
        this.columnaDestino = columnaDestino;
    }

    public Optional<Horarios> getHorarioExacto() {
        return horarioExacto;
    }

    public void setHorarioExacto(Optional<Horarios> horarioExacto) {
        this.horarioExacto = horarioExacto;
    }

    public Boolean getModoSeleccionMateria() {
        return modoSeleccionMateria;
    }

    public void setModoSeleccionMateria(Boolean modoSeleccionMateria) {
        this.modoSeleccionMateria = modoSeleccionMateria;
    }

    public boolean isModoIntercambio() {
        return modoIntercambio;
    }

    public void setModoIntercambio(boolean modoIntercambio) {
        this.modoIntercambio = modoIntercambio;
    }

    public Boolean getActivarModificar() {
        return activarModificar;
    }

    public void setActivarModificar(Boolean activarModificar) {
        this.activarModificar = activarModificar;
    }

    public int getFilaMateriaSeleccionada() {
        return filaMateriaSeleccionada;
    }

    public void setFilaMateriaSeleccionada(int filaMateriaSeleccionada) {
        this.filaMateriaSeleccionada = filaMateriaSeleccionada;
    }

    public int getColumnaMateriaSeleccionada() {
        return columnaMateriaSeleccionada;
    }

    public void setColumnaMateriaSeleccionada(int columnaMateriaSeleccionada) {
        this.columnaMateriaSeleccionada = columnaMateriaSeleccionada;
    }

    public String getNuevaAulaSeleccionada() {
        return nuevaAulaSeleccionada;
    }

    public void setNuevaAulaSeleccionada(String nuevaAulaSeleccionada) {
        this.nuevaAulaSeleccionada = nuevaAulaSeleccionada;
    }

    public String getAulaMateriaSeleccionada() {
        System.out.println("GetAulaMateriaSeleccionada" + aulaMateriaSeleccionada);
        return aulaMateriaSeleccionada;
    }

    public void setAulaMateriaSeleccionada(String aulaMateriaSeleccionada) {
        this.aulaMateriaSeleccionada = aulaMateriaSeleccionada;
    }

    public String getNombreMateriaSeleccionada() {
        System.out.println("GetNombreMateriaSeleccionada" + nombreMateriaSeleccionada);
        return nombreMateriaSeleccionada;
    }

    public void setNombreMateriaSeleccionada(String nombreMateriaSeleccionada) {
        this.nombreMateriaSeleccionada = nombreMateriaSeleccionada;
    }

    public boolean isEliminando() {
        return eliminando;
    }

    public void setEliminando(boolean eliminando) {
        this.eliminando = eliminando;
    }

    public Horarios getMateriaSeleccionada() {
        return materiaSeleccionada;
    }

    public void setMateriaSeleccionada(Horarios materiaSeleccionada) {
        this.materiaSeleccionada = materiaSeleccionada;
    }

    public Organigrama getOrganigrama() {
        return organigrama;
    }

    public void setOrganigrama(Organigrama organigrama) {
        this.organigrama = organigrama;
    }

    public Boolean getPuedeguardar() {
        return puedeguardar;
    }

    public void setPuedeguardar(Boolean puedeguardar) {
        this.puedeguardar = puedeguardar;
    }

    public Boolean getSeleccionadocarreramateria2() {
        return seleccionadocarreramateria2;
    }

    public void setSeleccionadocarreramateria2(Boolean seleccionadocarreramateria2) {
        this.seleccionadocarreramateria2 = seleccionadocarreramateria2;
    }

    private void addMessage(FacesMessage.Severity severity, String titulo, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, titulo, detalle));
    }

    public HorarioCrear getHorariocrear() {
        return horariocrear;
    }

    public void setHorariocrear(HorarioCrear horariocrear) {
        this.horariocrear = horariocrear;
    }

    public String getValorgrupo() {

        return valorgrupo;
    }

    public void setValorgrupo(String valorgrupo) {
        this.valorgrupo = valorgrupo;
    }

    public int getNumestudiantes() {
        return numestudiantes;
    }

    public void setNumestudiantes(int numestudiantes) {

        this.numestudiantes = numestudiantes;
    }

    public List<Grupos> getListagrupos() {
        return listagrupos;
    }

    public void setListagrupos(List<Grupos> listagrupos) {
        this.listagrupos = listagrupos;
    }

    public List<Personal> getListapersonal() {
        return listapersonal;
    }

    public void setListapersonal(List<Personal> listapersonal) {
        this.listapersonal = listapersonal;
    }

    public String getGrupoS() {
        return grupoS;
    }

    public void setGrupoS(String grupoS) {
        this.grupoS = grupoS;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public List<Aulas> getListaAulas() {
        return listaAulas;
    }

    public void setListaAulas(List<Aulas> listaAulas) {
        this.listaAulas = listaAulas;
    }

    public String getAulas() {
        return aulas;
    }

    public void setAulas(String aulas) {
        this.aulas = aulas;
    }

    /**
     * Creates a new instance of HorarioCrearBean
     */
    public HorarioCrearBean() {

    }

    public String getSemestreS() {
        return semestreS;
    }

    public void setSemestreS(String semestreS) {
        this.semestreS = semestreS;
    }

    public String getPeriodoS() {
        return periodoS;
    }

    public void setPeriodoS(String periodoS) {
        this.periodoS = periodoS;
    }

    public List<Permisos> getListaPermisos() {
        return listaPermisos;
    }

    public void setListaPermisos(List<Permisos> listaPermisos) {
        this.listaPermisos = listaPermisos;
    }

    public List<Carrera> getListaCarrera() {
        return listaCarrera;
    }

    public void setListaCarrera(List<Carrera> listaCarrera) {
        this.listaCarrera = listaCarrera;
    }

    public List<PeriodoEscolar> getListaPeriodoEscolar() {
        return listaPeriodoEscolar;
    }

    public void setListaPeriodoEscolar(List<PeriodoEscolar> listaPeriodoEscolar) {
        this.listaPeriodoEscolar = listaPeriodoEscolar;
    }

    public List<MateriasCarreras> getListaMateria() {
        return listaMateria;
    }

    public void setListaMateria(List<MateriasCarreras> listaMateria) {
        this.listaMateria = listaMateria;
    }

    public String getCarreraS() {
        return carreraS;
    }

    public void setCarreraS(String carreraS) {
        this.carreraS = carreraS;
    }

    public List<HorarioCrear> getListaHorarioCrear() {
        return listaHorarioCrear;
    }

    public void setListaHorarioCrear(List<HorarioCrear> listaHorarioCrear) {
        this.listaHorarioCrear = listaHorarioCrear;
    }

    public void onCellEdit(ActionEvent e) {
        //System.out.println(e.getComponent().getClientId());
    }

    public int getCreditos() {

        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getPosicionesSeleccionadas() {
        return posicionesSeleccionadas;
    }

    public void setPosicionesSeleccionadas(String posicionesSeleccionadas) {
        this.posicionesSeleccionadas = posicionesSeleccionadas;
    }

    public String getAsignaturaS() {
        return asignaturaS;
    }

    public void setAsignaturaS(String asignaturaS) {
        this.asignaturaS = asignaturaS;
    }

    public ArrayList<String> getListaSemestres() {
        return listaSemestres;
    }

    public void setListaSemestres(ArrayList<String> listaSemestres) {
        this.listaSemestres = listaSemestres;
    }

    public void valoresCelda() {//Mostrar mensajes de lo que se ha obtenido

        System.out.println("=== ENTRE A CONFIRMAR/INTERCAMBIAR===");
        System.out.println("Nombre: " + nombreMateriaSeleccionada);
        System.out.println("Aula actual: " + aulaMateriaSeleccionada);
        System.out.println("Fila: " + filaMateriaSeleccionada);
        System.out.println("Columna: " + columnaMateriaSeleccionada);
    }

    // ********************** Metodos 
    public void nuevoHorario() {
        inicializarHorario();//Inicializamos horario

        // 4. Obtiene el contexto actual de JSF (para trabajar con sesión, request, etc.)
        FacesContext contexto = FacesContext.getCurrentInstance();
        // 5. Recupera el objeto "usuario" guardado en la sesión
        usuario = (Usuario) contexto.getExternalContext().getSessionMap().get("usuario");
        // 6. Usa el servicio permisoServicio para buscar carreras relacionadas al usuario
        listaPermisos = permisoServicio.buscarCarreras(usuario.getUsuario());
        listaPeriodoEscolar = periodoEscolarServicio.periodosEscolaresActivos();
        // opcional: listaPeriodoEscolar ya inicializada
        if (!listaPeriodoEscolar.isEmpty()) {
            periodoS = listaPeriodoEscolar.get(0).getPeriodo();
            System.out.println("PeriodoS" + periodoS);
        }

        listaAulas = aulasServicio.aulasActivos();
        listagrupos = gruposServicio.gruposActivos();
        listapersonal = personalServicio.personalActivos();
        listaMateria = null;

    }

    public void inicializarHorario() {
        listaHorarioCrear = new ArrayList();
        // 1. Inicializa la lista de semestres (vacía)
        listaSemestres = new ArrayList();
        // 2. Agrega del 1 al 9 como Strings ("1", "2", ..., "9")
        for (int i = 1; i <= 9; i++) {
            listaSemestres.add(String.valueOf(i).trim());
        }// 3. Genera horas de 7 a 19 y las guarda en objetos HorarioCrear
        for (int i = 7; i <= 19; i++) {
            horariocrear = new HorarioCrear();
            horariocrear.setHora(i + ":00-" + (i + 1) + ":00");// ejemplo: "7:8", "8:9", ...
            listaHorarioCrear.add(horariocrear);
        }
    }

    public void encontrarMateriaDeCelda() {//Encuentra la materia de la celda en la lista de horarios generados
        // Buscar el horario exacto
        horarioExacto = listaHorariosGenerados.stream()
                .filter(h -> h.getMateria().getNombreCompletoMateria().equalsIgnoreCase(nombreMateriaSeleccionada.trim())
                && h.getAula().getAula().equalsIgnoreCase(aulaMateriaSeleccionada.trim())
                && ConvertirDateAInt(h) == filaMateriaSeleccionada - 1
                && (h.getDiaSemana() - 1) == columnaMateriaSeleccionada)
                .findFirst();

    }

    public int ConvertirDateAInt(Horarios h) {//Convierto el tipo date a un entero mejor 

        Calendar cal = Calendar.getInstance();
        Date horainicial = new Date();
        int hora, fila;

        horainicial = h.getHoraInicial();
        cal.setTime(horainicial);
        hora = cal.get(Calendar.HOUR_OF_DAY); // obtiene la hora en formato 24h
        fila = obtenerFilaHorario(hora);
        return fila;

    }

    public int obtenerFilaHorario(int hora) {

        switch (hora) {
            case 7:
                return 0;
            case 8:
                return 1;
            case 9:
                return 2;
            case 10:
                return 3;
            case 11:
                return 4;
            case 12:
                return 5;
            case 13:
                return 6;
            case 14:
                return 7;
            case 15:
                return 8;
            case 16:
                return 9;
            case 17:
                return 10;
            case 18:
                return 11;
            case 19:
                return 12;
            case 20:
                return 13;
            default:
                return -1; // hora fuera de rango
        }

    }

    public String ConvertirDateAHora(Horarios h) {//Convierto el tipo date a un 7:00-8:00;

        Calendar cal = Calendar.getInstance();
        Date horainicial = new Date();
        int hora;
        String horaMateria;

        horainicial = h.getHoraInicial();
        cal.setTime(horainicial);
        hora = cal.get(Calendar.HOUR_OF_DAY); // obtiene la hora en formato 24h
        horaMateria = obtenerStringHorario(hora);
        return horaMateria;

    }

    public String obtenerStringHorario(int hora) {

        switch (hora) {
            case 7:
                return "7:00 - 8:00";
            case 8:
                return "8:00 - 9:00";
            case 9:
                return "9:00 - 10:00";
            case 10:
                return "10:00 - 11:00";
            case 11:
                return "11:00 - 12:00";
            case 12:
                return "12:00 - 13:00";
            case 13:
                return "13:00 - 14:00";
            case 14:
                return "14:00 - 15:00";
            case 15:
                return "15:00 - 16:00";
            case 16:
                return "16:00 - 17:00";
            case 17:
                return "17:00 - 18:00";
            case 18:
                return "18:00 - 19:00";
            case 19:
                return "19:00 - 20:00";
            case 29:
                return "20:00 - 21:00";
            default:
                return "00:00 - 00:00"; // hora fuera de rango
        }

    }

    public String obtenerStringDeHoraPorFila(int hora) {

        switch (hora) {
            case 0:
                return "07:00:00";
            case 1:
                return "08:00:00";
            case 2:
                return "09:00:00";
            case 3:
                return "10:00:00";
            case 4:
                return "11:00:00";
            case 5:
                return "12:00:00";
            case 6:
                return "13:00:00";
            case 7:
                return "14:00:00";
            case 8:
                return "15:00:00";
            case 9:
                return "16:00:00";
            case 10:
                return "17:00:00";
            case 11:
                return "18:00:00";
            case 12:
                return "19:00:00";
            case 13:
                return "20:00:00";
            default:
                return "00:00:00"; // hora fuera de rango
        }

    }

    public void activarEliminar() {//Activo el color de fondo del modo eliminar
        eliminando = !eliminando;
        //Para evitar que se empalmen los horarios en colores.
        modoIntercambio = false;
        activarModificar = false;
        modoSeleccionMateria = false;
        desactivarTodoPor3(); //Es para desactivar los campos inecesarios.
        if (eliminando) {
            cambiarModoColorTabla("F8D5D4");
        } else {
            actualizarTabla();
        }
    }

    // 🔹 BOTÓN MODIFICAR
    public void activarModoModificar() {//Para poner todo en naranja para modificar
        activarModificar = !activarModificar;
        //Para evitar que se empalmen los horarios en colores.
        modoIntercambio = false;
        eliminando = false;
        modoSeleccionMateria = false;
        desactivarTodoPor3(); //Es para desactivar los campos inecesarios.
        if (activarModificar) {
            System.out.println("Va a pintar");
            cambiarModoColorTabla("FEA785");
        } else {
            actualizarTabla();
        }

    }

    // 🔹 BOTÓN INTERCAMBIO
    public void activarModoIntercambio() {
        modoIntercambio = !modoIntercambio; // si ya estaba, lo desactiva
        //Para evitar que se empalmen los horarios en colores.
        activarModificar = false;
        eliminando = false;
        modoSeleccionMateria = false;
        desactivarTodoPor3(); //Es para desactivar los campos inecesarios.

        if (modoIntercambio) {
            System.out.println("VariableModoIntercambio" + modoIntercambio);
            cambiarModoColorTabla("A6E8CC");
        } else {
            actualizarTabla();

            /* if (booleanBuscarAula) {//Esto sirve cuando este en el modo aula
                booleanBotonEliminar = false;//Desactivo el boton Eliminar
                booleanBotonInsertar = false;//Desactivar el boton Insertar
                booleanBotonModificar = false;//Desactivo el boton Modificar
            }*/
        }

    }

    public void desactivarTodoPor3() {
        if (modoIntercambio || activarModificar || eliminando) {
            desactivarCampos = true;
        } else {
            desactivarCampos = false;
        }

        /*if (activarModificar) {
            desactivarCampos = true;
          return;
            
        }else{
        
        
        }
        
        if (eliminando) {
            
        }else{
        
        
        }*/
    }

    public void cambiarModoColorTabla(String x) {//Cambia todo la tabla por un solo color pero primero tengo que darle j  color yo.
        System.out.println("listaHorariosGenerados.size" + listaHorariosGenerados.size());
        if (listaHorariosGenerados.size() != 0) {
            Calendar cal = Calendar.getInstance();
            Date horainicial = new Date();
            int hora, fila, columna;

            for (int i = 0; i < listaHorariosGenerados.size(); i++) {
                horario = listaHorariosGenerados.get(i);

                horainicial = horario.getHoraInicial();
                cal.setTime(horainicial);
                hora = cal.get(Calendar.HOUR_OF_DAY); // obtiene la hora en formato 24h
                fila = obtenerFilaHorario(hora);
                columna = horario.getDiaSemana() - 1;
                for (int j = 0; j < listaHorarioCrear.size(); j++) {//Recorrer la tabla primero por las filas
                    horariocrear = listaHorarioCrear.get(j);
                    if (j == fila) {
                        //  horariocrear.setColor("#3B0000");//Poner el color que le corresponde a las materias. 
                        asignarDatoSemana(horariocrear, columna, horario, "#" + x, "#3B0000");
                        break;
                    }

                }
            }

        }
    }

    /*  public void asignarMateriaSeleccionadaI() {//Sacar todas las variables de la materia que voy a modificar. 
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        nombreMateriaSeleccionada = params.get("nombre");
        aulaMateriaSeleccionada = params.get("aula");
        filaMateriaSeleccionada = Integer.parseInt(params.get("fila"));
        columnaMateriaSeleccionada = Integer.parseInt(params.get("columna"));

        System.out.println("IMateria seleccionada: " + nombreMateriaSeleccionada);
        System.out.println("IAula: " + aulaMateriaSeleccionada);
        System.out.println("IFila: " + filaMateriaSeleccionada);
        System.out.println("IColumna: " + columnaMateriaSeleccionada);

    }*/
    public void asignarMateriaSeleccionadaRCG() {

        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();

        nombreMateriaSeleccionada = params.get("nombre");
        aulaMateriaSeleccionada = params.get("aula");
        System.out.println("MateriaSeleccionada:" + nombreMateriaSeleccionada);

        /*MateriasCarreras materiaC;
        Boolean x = false;
        materiaC = materiasCarrerasServicio.buscarMateriaPorNombre(nombreMateriaSeleccionada, Integer.parseInt(carreraS));

        for (int i = 0; i < listaPermisos.size(); i++) {
            Permisos p = listaPermisos.get(i);

            System.out.println("MateriaC.size" + materiaC);

            if (Objects.equals(p.getClaveArea().getClaveArea(), materiaC.getMateria().getClaveArea().getClaveArea())) {
                x = true;
                break;
            }
        }

        // ❌ NO permitido → NO abrir ventana
        if (x) {
            // ✅ Todo permitido
            PrimeFaces.current().ajax().addCallbackParam("abrirDialogo", true);
        } else {
            // ❌ Alguna restricción falló
            addMessage(FacesMessage.SEVERITY_INFO,
                    "MATERIA NO PERMITIDA",
                    "No se permite borrar, area no disponible");

            PrimeFaces.current().ajax().addCallbackParam("abrirDialogo", false);
            return;
        }*/

        modoEliminarSeleccionadoG();//Pintar las seleccionadas para eliminar
        obtenerHorasDeMateriaEliminarG();//Contabilizar cuantas materias estan realmente en la base de datos por grupo
        HorarioCrear h = new HorarioCrear();
        int[] conteoDias = new int[6];
        listaHorarioBorrar = new ArrayList<>();

        h.setHora(nombreMateriaSeleccionada);

        for (Horarios y : horariosAEliminar) {
            insertarValoresListaHorariosBorrarColumna(
                    h,
                    (int) (y.getDiaSemana() - 1),
                    ConvertirDateAHora(y),
                    conteoDias
            );
        }

        listaHorarioBorrar.add(h);
    }

    public void asignarMateriaSeleccionadaRCA() {

        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();

        nombreMateriaSeleccionada = params.get("nombre");
        aulaMateriaSeleccionada = params.get("aula");

        carreraSeleccionada = params.get("carrera").replace("C:", "").trim();
        semestreSeleccionada = params.get("semestre").replace("S:", "").trim();
        valorGrupoSeleccionada = params.get("valorgrupo").replace("G:", "").trim();

        obtenerHorasDeMateriaEliminarA();//Contabilizar cuantas materias estan realmente en la base de datos por grupo
        contabilizarHorariosSeleccionados();//Cuantas realmente tengo insertadas en la vista por aula. 

        System.out.println("numEliminarSeleccionados:" + numEliminarSeleccionados);
        System.out.println("horariosAEliminar.size():" + horariosAEliminar.size());

        System.out.println("MateriaSeleccionada:" + nombreMateriaSeleccionada);

       /* MateriasCarreras materiaC;
        Boolean x = false;
        materiaC = materiasCarrerasServicio.buscarMateriaPorNombre(nombreMateriaSeleccionada, Integer.parseInt(carreraS));

        for (int i = 0; i < listaPermisos.size(); i++) {
            Permisos p = listaPermisos.get(i);

            System.out.println("MateriaC.size" + materiaC);

            if (Objects.equals(p.getClaveArea().getClaveArea(), materiaC.getMateria().getClaveArea().getClaveArea())) {
                x = true;
                break;
            }
        }
        // ❌ NO permitido → NO abrir ventana
        if (x && horariosAEliminar.size() == numEliminarSeleccionados) {
            // ✅ Todo permitido
            PrimeFaces.current().ajax().addCallbackParam("abrirDialogo", true);
        } else {
            // ❌ Alguna restricción falló
            addMessage(FacesMessage.SEVERITY_INFO,
                    "MATERIA NO PERMITIDA",
                    "No se permite borrar, area no disponible");

            PrimeFaces.current().ajax().addCallbackParam("abrirDialogo", false);
            return;
        }*/

        modoEliminarSeleccionadoA();//Pintar las seleccionadas para eliminar

        HorarioCrear h = new HorarioCrear();
        int[] conteoDias = new int[6];
        listaHorarioBorrar = new ArrayList<>();

        h.setHora(nombreMateriaSeleccionada);

        for (Horarios y : horariosAEliminar) {
            insertarValoresListaHorariosBorrarColumna(
                    h,
                    (int) (y.getDiaSemana() - 1),
                    ConvertirDateAHora(y),
                    conteoDias
            );
        }

        listaHorarioBorrar.add(h);
    }

    public void insertarValoresListaHorariosBorrarColumna(HorarioCrear h, int diaSemana, String horaString, int[] conteoDias) {
        String guardarValorAnterior;
        switch (diaSemana) {
            case 1: // Lunes
                if (conteoDias[0] == 0) {
                    h.setLunes(horaString);
                } else {
                    guardarValorAnterior = h.getLunes();
                    h.setLunes(guardarValorAnterior + "  " + horaString);
                }
                conteoDias[0]++;
                break;
            case 2: // Martes
                if (conteoDias[1] == 0) {
                    h.setMartes(horaString);
                } else {
                    guardarValorAnterior = h.getMartes();
                    h.setMartes(guardarValorAnterior + "  " + horaString);
                }
                conteoDias[1]++;
                break;
            case 3: // Miércoles
                if (conteoDias[2] == 0) {
                    h.setMiercoles(horaString);
                } else {
                    guardarValorAnterior = h.getMiercoles();
                    h.setMiercoles(guardarValorAnterior + "  " + horaString);
                }
                conteoDias[2]++;
                break;
            case 4: // Jueves
                if (conteoDias[3] == 0) {
                    h.setJueves(horaString);
                } else {
                    guardarValorAnterior = h.getJueves();
                    h.setJueves(guardarValorAnterior + "  " + horaString);
                }
                conteoDias[3]++;
                break;
            case 5: // Viernes
                if (conteoDias[4] == 0) {
                    h.setViernes(horaString);
                } else {
                    guardarValorAnterior = h.getViernes();
                    h.setViernes(guardarValorAnterior + "  " + horaString);
                }
                conteoDias[4]++;
                break;
            case 6: // Sábado
                if (conteoDias[5] == 0) {
                    h.setSabado(horaString);
                } else {
                    guardarValorAnterior = h.getSabado();
                    h.setSabado(guardarValorAnterior + "  " + horaString);
                }
                conteoDias[5]++;
                break;
            default:
                System.out.println("Día de la semana inválido");
                break; // hora fuera de rango
        }

    }

    public void asignarMateriaSeleccionadaM() {//Sacar todas las variables de la materia que voy a modificar para el cuadro de dialogo emergente de modificar
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        nombreMateriaSeleccionada = params.get("nombre");
        aulaMateriaSeleccionada = params.get("aula");
        filaMateriaSeleccionada = Integer.parseInt(params.get("fila"));
        columnaMateriaSeleccionada = Integer.parseInt(params.get("columna"));

        System.out.println("MMateria seleccionada: " + nombreMateriaSeleccionada);
        System.out.println("MAula: " + aulaMateriaSeleccionada);
        System.out.println("MFila: " + filaMateriaSeleccionada);
        System.out.println("MColumna: " + columnaMateriaSeleccionada);

       /* MateriasCarreras materiaC;
        Boolean x = false;
        materiaC = materiasCarrerasServicio.buscarMateriaPorNombre(nombreMateriaSeleccionada, Integer.parseInt(carreraS));

        for (int i = 0; i < listaPermisos.size(); i++) {
            Permisos p = listaPermisos.get(i);

            if (Objects.equals(p.getClaveArea().getClaveArea(), materiaC.getMateria().getClaveArea().getClaveArea())) {
                x = true;
                break;
            }
        }

        // ❌ NO permitido → NO abrir ventana
        if (x) {
            // ✅ Todo permitido
            PrimeFaces.current().ajax().addCallbackParam("abrirDialogo", true);
        } else {
            // ❌ Alguna restricción falló
            addMessage(FacesMessage.SEVERITY_INFO,
                    "MATERIA NO PERMITIDA",
                    "No se permite modificar, area no disponible");

            PrimeFaces.current().ajax().addCallbackParam("abrirDialogo", false);
            return;
        }*/

        asignarValorDirectoEnCelda("#FC5110");
        addMessage(FacesMessage.SEVERITY_INFO, "MATERIA SELECCIONADA", nombreMateriaSeleccionada + "  " + aulaMateriaSeleccionada);

    }

    public void asignarValorDirectoEnCelda(String colorFondo) {

        // 1. Obtener la fila directa
        HorarioCrear hc = listaHorarioCrear.get(filaMateriaSeleccionada - 1);
        // 3. Cambiar color
        modificarColor(hc, columnaMateriaSeleccionada, colorFondo, "#FFFFFF");

        System.out.println("Asignado directo en fila " + filaMateriaSeleccionada
                + ", columna " + columnaMateriaSeleccionada
        );

    }

    public void limpiarVariablesDeEstadoModificar() {
        // Limpiar variables y estado
        // nombreMateriaSeleccionada = null;
        //aulaMateriaSeleccionada = null;
        //    eliminando = false;
        // modoSeleccionMateria = false;
        actualizarTabla();
        cambiarModoColorTabla("FEA785");
        System.out.println("Cambiando color naranja a normal");
        //asignarValorDirectoEnCelda("FEA785");
    }

    public void confirmarModificarMateria() {//Se actualiza el aula que he escogido en la ventana emergente de modificar dentro de la BD y del bean y la vista 
        valoresCelda();

        listacoordenadas = new ArrayList<>();
        listacoordenadas.add("" + filaMateriaSeleccionada + "," + columnaMateriaSeleccionada + "");//une 1,1 en ["1,1"]
        aulaBuscada = aulasServicio.buscarPorId(nuevaAulaSeleccionada);
        if (comprobacionEmpalmesPorGrupos()) {
            // activarModoModificar();//Para devolver al color original. 
            // 1. Obtener la fila directa
            //REGRESAR EL COLOR DE ORIGEN LA MATERIA SELECCIONADA
            HorarioCrear hc = listaHorarioCrear.get(filaMateriaSeleccionada - 1);
            // 3. Cambiar color
            modificarColor(hc, columnaMateriaSeleccionada, "#FEA785", "#3B0000");
            System.out.println("Asignado directo en fila " + filaMateriaSeleccionada
                    + ", columna " + columnaMateriaSeleccionada
            );
            return;
        }

        encontrarMateriaDeCelda();//Encuentra la materia de la celda en la lista de horarios generados

        // Usar el resultado
        if (horarioExacto.isPresent()) {
            Horarios horario = horarioExacto.get();
            System.out.println("NuevaAulasSeleccionada" + nuevaAulaSeleccionada);
            // Buscar la nueva aula seleccionada en el combobox
            Aulas nuevaAula = aulasServicio.buscarPorId(nuevaAulaSeleccionada);
            // (usa el ID real que el usuario seleccionó del combobox)

            if (nuevaAula == null) {

                addMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El aula seleccionada no existe.");
                return;
            }

            // Actualizamos el aula
            horario.setAula(nuevaAula);
            horarioServicio.actualizar(horario);

            // Refrescar la lista local (opcional, si quieres ver reflejado el cambio en la vista)
            Carrera carrera = carreraServicio.buscarPorId(Integer.parseInt(carreraS));
            this.listaHorariosGenerados = horarioServicio.buscarHorariosPorGrupos(carrera, Integer.parseInt(semestreS), periodoEscolarServicio.buscarPorId(periodoS), valorgrupo);
            //actualizarTabla()

            addMessage(FacesMessage.SEVERITY_INFO, "ÉXITO", "EL AULA SE MODIFICÓ CORRECTAMENTE.");
            //activarModoModificar();
            aulas = nuevaAulaSeleccionada;
            actualizarTabla();
            cambiarModoColorTabla("FEA785");
            // Actualizar la tabla visualmente
            PrimeFaces.current().ajax().update("horarioG:grupo");
            PrimeFaces.current().ajax().update("horarioG:aulas");
        } else {
            System.out.println("No se encontró el horario exacto.");
        }

    }

    public void cambiarMateriaSeleccionadaXG() {//Metodo por si quiero cambiar el color pero por el Bean por Grupo
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();

        int fila = Integer.parseInt(params.get("fila"));
        int columna = Integer.parseInt(params.get("columna"));
        String nombre = params.get("nombre");
        String aula = params.get("aula");

        System.out.println("Materia seleccionada: " + nombre + " | Aula: " + aula);


        // Nueva selección
        filaMateriaSeleccionada = fila;
        columnaMateriaSeleccionada = columna;
        nombreMateriaSeleccionada = nombre;
        aulaMateriaSeleccionada = aula;

      /*  MateriasCarreras materiaC;
        Boolean x = false;
        materiaC = materiasCarrerasServicio.buscarMateriaPorNombre(nombreMateriaSeleccionada, Integer.parseInt(carreraS));

        for (int i = 0; i < listaPermisos.size(); i++) {
            Permisos p = listaPermisos.get(i);

            System.out.println("MateriaC.size" + materiaC);

            if (Objects.equals(p.getClaveArea().getClaveArea(), materiaC.getMateria().getClaveArea().getClaveArea())) {
                x = true;
                break;
            }
        }

        // ❌ NO permitido → NO abrir ventana
        if (x) {
        } else {
               addMessage(FacesMessage.SEVERITY_INFO,
                    "MATERIA NO PERMITIDA",
                    "No se permite borrar, area no disponible");
            return;
        }*/

        addMessage(FacesMessage.SEVERITY_INFO, "MATERIA SELECCIONADA", "MATERIA: " + nombre + " AULA: " + aula);
        // Si es la misma materia que ya estaba seleccionada → deselecciona
        // Cambia color de la nueva seleccionada
        modificarSeleccion();

    }

    public void cambiarMateriaSeleccionadaXA() {//Metodo por si quiero cambiar el color pero por el Bean por Grupo
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();

        filaMateriaSeleccionada = Integer.parseInt(params.get("fila"));
        columnaMateriaSeleccionada = Integer.parseInt(params.get("columna"));
        nombreMateriaSeleccionada = params.get("nombre");
        aulaMateriaSeleccionada = params.get("aula");

        carreraSeleccionada = params.get("carrera").replace("C:", "").trim();
        semestreSeleccionada = params.get("semestre").replace("S:", "").trim();
        valorGrupoSeleccionada = params.get("valorgrupo").replace("G:", "").trim();

        System.out.println("Materia seleccionada: " + nombreMateriaSeleccionada + " | Aula: " + aulaMateriaSeleccionada);
        addMessage(FacesMessage.SEVERITY_INFO, "MATERIA SELECCIONADA", "MATERIA: " + nombreMateriaSeleccionada + " AULA: " + aulaMateriaSeleccionada);
        // Cambia color de la nueva seleccionada

      /*  MateriasCarreras materiaC;
        Boolean x = false;
        materiaC = materiasCarrerasServicio.buscarMateriaPorNombre(nombreMateriaSeleccionada, Integer.parseInt(carreraS));

        for (int i = 0; i < listaPermisos.size(); i++) {
            Permisos p = listaPermisos.get(i);

            System.out.println("MateriaC.size" + materiaC);

            if (Objects.equals(p.getClaveArea().getClaveArea(), materiaC.getMateria().getClaveArea().getClaveArea())) {
                x = true;
                break;
            }
        }

        // ❌ NO permitido → NO abrir ventana
        if (x) {
        } else {
            addMessage(FacesMessage.SEVERITY_INFO,
                    "MATERIA NO PERMITIDA",
                    "No se permite borrar, area no disponible");
            return;
        }*/
        modificarSeleccion();

    }

    private void deseleccionarAnterior() {
        if (filaSeleccionadaAnterior == -1 || columnaSeleccionadaAnterior == -1) {
            return;
        }
        HorarioCrear hc = listaHorarioCrear.get(filaSeleccionadaAnterior - 1);
        modificarColor(hc, columnaSeleccionadaAnterior, "#A6E8CC", "#3B0000");
    }

    public void modificarSeleccion() {

        for (int i = 0; i < listaHorarioCrear.size(); i++) {//Recorro la tabla ListaHorariosGenerar
            if (i == (filaMateriaSeleccionada - 1)) {//Si encuentro la fila a la cual corresponde.
                HorarioCrear hc = listaHorarioCrear.get(i);//Tomo la fila
                if (!modoSeleccionMateria) {//Verifica que 
                    modificarColor(hc, columnaMateriaSeleccionada, "#04BC86", "#FFFFFF");
                    //hc.setColor("#FFFFFF");
                    modoSeleccionMateria = true;
                    //System.out.println("ENTRO->*" + i);

                    addMessage(FacesMessage.SEVERITY_INFO, "INFORMACION", "SE HA SELECCIONADO MATERIA");
                    System.out.println("SE HA SELECCIONADO MATERIA");
                    return;
                }

            }
        }
    }

    public void modificarColor(HorarioCrear x, int y, String colorFondo, String colorLetra) {//Le agrego el color de fondo y color de letra a mi dia de la semana.
        String materiayaula;
        String grupoysemestreyreticula;

        switch (y) {
            case 1:
                materiayaula = getSepararAtributos(x.getLunes(), 0);
                grupoysemestreyreticula = getSepararAtributos(x.getLunes(), 5);
                x.setLunes(materiayaula + "  " + colorFondo + "  " + colorLetra + "  " + grupoysemestreyreticula);

                /*x.setLunes(z.getMateria().getNombreCompletoMateria() + "  " + z.getAula().getAula() + "  " + colorFondo + "  " + colorLetra
                        + "  G:" + z.getGrupo() + "  S:" + z.getIdGrupo().getIdMateriaCarrera().getSemestreReticula());*/
                //System.out.println("ValorMetodoModificarColor" + x.getLunes());
                break;
            case 2:
                materiayaula = getSepararAtributos(x.getMartes(), 0);
                grupoysemestreyreticula = getSepararAtributos(x.getMartes(), 5);
                x.setMartes(materiayaula + "  " + colorFondo + "  " + colorLetra + "  " + grupoysemestreyreticula);

                //System.out.println("ValorMetodoModificarColor" + x.getMartes() + "  " + colorLetra);
                break;
            case 3:
                materiayaula = getSepararAtributos(x.getMiercoles(), 0);
                grupoysemestreyreticula = getSepararAtributos(x.getMiercoles(), 5);
                x.setMiercoles(materiayaula + "  " + colorFondo + "  " + colorLetra + "  " + grupoysemestreyreticula);
                //System.out.println("ValorMetodoModificarColor" + x.getMiercoles() + "  " + colorLetra);
                break;
            case 4:
                materiayaula = getSepararAtributos(x.getJueves(), 0);
                grupoysemestreyreticula = getSepararAtributos(x.getJueves(), 5);
                x.setJueves(materiayaula + "  " + colorFondo + "  " + colorLetra + "  " + grupoysemestreyreticula);
                // System.out.println("ValorMetodoModificarColor" + x.getJueves() + "  " + colorLetra);
                break;
            case 5:
                materiayaula = getSepararAtributos(x.getViernes(), 0);
                grupoysemestreyreticula = getSepararAtributos(x.getViernes(), 5);
                x.setViernes(materiayaula + "  " + colorFondo + "  " + colorLetra + "  " + grupoysemestreyreticula);
                // System.out.println("ValorMetodoModificarColor" + x.getViernes() + "  " + colorLetra);
                break;
            case 6:
                materiayaula = getSepararAtributos(x.getSabado(), 0);
                grupoysemestreyreticula = getSepararAtributos(x.getSabado(), 5);
                x.setSabado(materiayaula + "  " + colorFondo + "  " + colorLetra + "  " + grupoysemestreyreticula);
                // System.out.println("ValorMetodoModificarColor" + x.getSabado() + "  " + colorLetra);
                break;
            default:
                break;
        }
    }

    public void moverMateriaSeleccionada() {//Cuando se intercambie la materia dentro de la tabla. 

        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        filaDestino = Integer.parseInt(params.get("filaDestino"));
        columnaDestino = Integer.parseInt(params.get("columnaDestino"));

        System.out.println("MovMateria seleccionada: " + nombreMateriaSeleccionada);
        System.out.println("MovAula: " + aulaMateriaSeleccionada);
        // System.out.println("MovFila: " + filaMateriaSeleccionada);
        // System.out.println("MovColumna: " + columnaMateriaSeleccionada);
        System.out.println("MovMover a fila " + filaDestino + ", columna " + columnaDestino);

        listacoordenadas = new ArrayList<>();
        listacoordenadas.add("" + filaDestino + "," + columnaDestino + "");//une 1,1 en ["1,1"]
        aulaBuscada = aulasServicio.buscarPorId(aulaMateriaSeleccionada);

        if (comprobacionEmpalmesPorGrupos()) {

            return;
        }

        if (comprobacionEmpalmesPorAulaIntercambio()) {

            return;
        }

        encontrarMateriaDeCelda();

        if (horarioExacto.isPresent()) {
            Horarios horario = horarioExacto.get();
            horario.setDiaSemana((short) (columnaDestino + 1));//Guarda el dia de la semana con numero. 
            asignarHoras(filaDestino);//Elige primero el rango que va a convertir, por ejemplo "7:00-8:00" los transforma a date 7 y 8. 
            horario.setHoraInicial(horainicioMateria);
            horario.setHoraFinal(horainiciofinMateria);
            horarioServicio.actualizar(horario);

            // Refrescar la lista local (opcional, si quieres ver reflejado el cambio en la vista)
            //Carrera carrera = carreraServicio.buscarPorId(Integer.parseInt(carreraS));
            //this.listaHorariosGenerados = horarioServicio.buscarHorariosPorGrupos(carrera, Integer.parseInt(semestreS), periodoEscolarServicio.buscarPorId(periodoS), valorgrupo);
            actualizarTabla();
            cambiarModoColorTabla("A6E8CC");
            modoSeleccionMateria = false;
            filaSeleccionadaAnterior = -1;
            columnaSeleccionadaAnterior = -1;
            //activarModoIntercambio();
            //activarModificar();
            System.out.println("Se ha actualizado intercambio de materia");
            addMessage(FacesMessage.SEVERITY_INFO, "INFO", "SE HA ACTUALIZADO INTERCAMBIO DE MATERIA");

        } else {
            System.out.println("No se encontró el horario exacto.");
            addMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO SE ENCONTRÓ EL HORARIOE EXACTO");
        }

        // Aquí puedes hacer la lógica de intercambio real:
        // 1. Verificar que el destino está vacío.
        // 2. Modificar los valores en listaHorariosGenerados.
        // 3. Actualizar la tabla si es necesario.
    }

    public void limpiarVariablesDeEstadoEliminar() {

        // Limpiar variables y estado
        nombreMateriaSeleccionada = null;
        aulaMateriaSeleccionada = null;
        //    eliminando = false;
        modoSeleccionMateria = false;

        actualizarTabla();
        cambiarModoColorTabla("F8D5D4");

        //addMessage(FacesMessage.SEVERITY_WARN, "CANCELAR", "LIMPIANDO CANCELAR.");
    }

    public void confirmarEliminarMateria() {

        System.out.println("Ejecutar Nombre materia seleccionada:" + nombreMateriaSeleccionada);
        System.out.println("Ejecutar Aula materia seleccionada:" + aulaMateriaSeleccionada);

        if (nombreMateriaSeleccionada == null || aulaMateriaSeleccionada == null) {
            System.out.println("⚠ No hay materia o aula seleccionada.");
            return;
        }

        obtenerHorasDeMateriaEliminarG();// Filtrar todos los horarios que coincidan con materia

        // Eliminar de la base de datos
        for (Horarios h : horariosAEliminar) {//Cuando acabe de eliminar en la BD 
            horarioServicio.eliminar(h);
        }

        for (Horarios h : horariosAEliminar) {
            gruposServicio.eliminar(h.getIdGrupo());
        }

        // Eliminar de la lista en memoria
        listaHorariosGenerados.removeAll(horariosAEliminar);

        // eliminando =false;//En cuanto 
        limpiarVariablesDeEstadoEliminar();
        actualizarTabla();
        cambiarModoColorTabla("F8D5D4");
        listaHorarios = null;//Para evitar que mande mensajes que no.

        addMessage(FacesMessage.SEVERITY_WARN, "MATERIA ELIMINADA", "SE ELIMINARON " + horariosAEliminar.size() + " COINCIDENCIAS DE LA MATERIA SLECCIONADA.");
        // Actualiza la tabla en pantalla (si tienes método para ello)

    }

    public void obtenerHorasDeMateriaEliminarG() {//Metodo para buscar por grupo.

        System.out.println("=== PARÁMETROS DE FILTRO ===");
        System.out.println("Materia seleccionada: " + nombreMateriaSeleccionada);
        System.out.println("Carrera (reticula): " + carreraS);
        System.out.println("Periodo: " + periodoS);
        System.out.println("Semestre: " + semestreS);
        System.out.println("Grupo: " + valorgrupo);
        System.out.println("===========================");

        horariosAEliminar = listaHorariosGenerados.stream()
                .filter(h
                        -> h.getMateria().getNombreCompletoMateria()
                        .equalsIgnoreCase(nombreMateriaSeleccionada.trim())
                && h.getIdGrupo().getReticula().getReticula()
                        .equals(Integer.parseInt(carreraS))
                && h.getPeriodo().getPeriodo().equals(periodoS)
                && h.getIdGrupo().getIdMateriaCarrera().getSemestreReticula()
                        .equals(Integer.parseInt(semestreS))
                && h.getGrupo().equals(valorgrupo)
                )
                .collect(Collectors.toList());

        if (horariosAEliminar.isEmpty()) {

            addMessage(FacesMessage.SEVERITY_WARN, "SIN COINCIDENCIAS", "No se encontraron horarios con esa materia y aula.");
            return;
        }

    }

    public void obtenerHorasDeMateriaEliminarA() {//Metodo para buscar por grupo.

        listaHorariosGenerados = horarioServicio.buscarHorariosPorGrupos(
                carreraServicio.buscarPorId(Integer.parseInt(carreraSeleccionada)),
                Integer.parseInt(semestreSeleccionada),
                periodoEscolarServicio.buscarPorId(periodoS),
                valorGrupoSeleccionada);

        System.out.println("=== PARÁMETROS DE FILTRO ===");
        System.out.println("Materia seleccionada: " + nombreMateriaSeleccionada);
        System.out.println("Carrera (reticula): " + carreraSeleccionada);
        System.out.println("Periodo: " + periodoS);
        System.out.println("Semestre: " + semestreSeleccionada);
        System.out.println("Grupo: " + valorGrupoSeleccionada);
        System.out.println("===========================");

        horariosAEliminar = listaHorariosGenerados.stream()
                .filter(h
                        -> h.getMateria().getNombreCompletoMateria()
                        .equalsIgnoreCase(nombreMateriaSeleccionada.trim())
                && h.getIdGrupo().getReticula().getReticula()
                        .equals(Integer.parseInt(carreraSeleccionada))
                && h.getPeriodo().getPeriodo().equals(periodoS)
                && h.getIdGrupo().getIdMateriaCarrera().getSemestreReticula()
                        .equals(Integer.parseInt(semestreSeleccionada))
                && h.getGrupo().equals(valorGrupoSeleccionada)
                )
                .collect(Collectors.toList());

        if (horariosAEliminar.isEmpty()) {

            addMessage(FacesMessage.SEVERITY_WARN, "SIN COINCIDENCIAS", "No se encontraron horarios con esa materia y aula.");
            return;
        }

    }

    public void modoEliminarSeleccionadoG() {//Aplicarle color rojo pastel cuando seleccione una materia.

        columnaMateriaSeleccionada = 0;

        for (int i = 0; i < listaHorarioCrear.size(); i++) {//Recorro la tabla ListaHorariosGenerar para encontrar coincidencias
            //Es decir encuentro CALCULO DIFERENCIAL compara en todo los horarios 
            HorarioCrear hc = listaHorarioCrear.get(i);//Tomo la fila
            if (!modoSeleccionMateria) {//Verifica que 
                String[] dias = {hc.getLunes(), hc.getMartes(), hc.getMiercoles(), hc.getJueves(), hc.getViernes(), hc.getSabado()};
                for (int j = 0; j < dias.length; j++) {
                    String dia = dias[j];
                    if (dia != null && !dia.isEmpty()) {
                        String valor = getSepararAtributos(dia, 3);

                        if (valor.equals(nombreMateriaSeleccionada)) {
                            columnaMateriaSeleccionada = j + 1; // j=0 → lunes = 1
                            modificarColor(hc, columnaMateriaSeleccionada, "#AD1C09", "#FFFFFF");

                            System.out.println("Posicion i:->" + i + "Posicion j:->" + j);
                        }
                    }
                }
                columnaMateriaSeleccionada = 0;

            }

        }
        modoSeleccionMateria = true;
        //Agregar una de modoSeleccionMateria;
        //Comparar con el tamaño encontrado 
        addMessage(FacesMessage.SEVERITY_INFO, "MATERIA", nombreMateriaSeleccionada);
    }

    public void modoEliminarSeleccionadoA() {//Aplicarle color rojo pastel cuando seleccione una materia.

        columnaMateriaSeleccionada = 0;

        for (int i = 0; i < listaHorarioCrear.size(); i++) {//Recorro la tabla ListaHorariosGenerar para encontrar coincidencias
            //Es decir encuentro CALCULO DIFERENCIAL compara en todo los horarios 
            HorarioCrear hc = listaHorarioCrear.get(i);//Tomo la fila
            if (!modoSeleccionMateria) {//Verifica que 
                String[] dias = {hc.getLunes(), hc.getMartes(), hc.getMiercoles(), hc.getJueves(), hc.getViernes(), hc.getSabado()};
                for (int j = 0; j < dias.length; j++) {
                    String dia = dias[j];
                    if (dia != null && !dia.isEmpty()) {
                        //String valor = getSepararAtributos(dia, 3);
                        String[] contenido = dia.split("  ");

                        // 🔴 Validación defensiva
                        if (contenido.length < 7) {
                            System.out.println("⚠ CONTENIDO INCOMPLETO: " + dia);
                            continue;
                        }
                        String nombreMateria = contenido[0];
                        String grupo = contenido[4];
                        String semestre = contenido[5];
                        String reticula = contenido[6];

                        // 🔍 Imprimir comparación
                        System.out.println("----- COMPARANDO COLORES-----");
                        System.out.println("Materia  UI: " + nombreMateria + " | PAR: " + nombreMateriaSeleccionada);
                        System.out.println("Grupo    UI: " + grupo + " | PAR: " + valorGrupoSeleccionada);
                        System.out.println("Semestre UI: " + semestre + " | PAR: " + semestreSeleccionada);
                        System.out.println("Carrera  UI: " + reticula + " | PAR: " + carreraSeleccionada);

                        String grupoBD = grupo.replace("G:", "").trim();
                        String semestreBD = semestre.replace("S:", "").trim();
                        String reticulaBD = reticula.replace("C:", "").trim();

                        boolean matchMateria = nombreMateria.equals(nombreMateriaSeleccionada);
                        boolean matchGrupo = grupoBD.equals(valorGrupoSeleccionada);
                        boolean matchSemestre = semestreBD.equals(semestreSeleccionada);
                        boolean matchReticula = reticulaBD.equals(carreraSeleccionada);

                        System.out.println("✔ Materia coincide: " + matchMateria);
                        System.out.println("✔ Grupo coincide: " + matchGrupo);
                        System.out.println("✔ Semestre coincide: " + matchSemestre);
                        System.out.println("✔ Carrera coincide: " + matchReticula);

                        if (matchMateria && matchGrupo && matchSemestre && matchReticula) {

                            columnaMateriaSeleccionada = j + 1; // j=0 → lunes = 1
                            modificarColor(hc, columnaMateriaSeleccionada, "#AD1C09", "#FFFFFF");

                            System.out.println("Posicion i:->" + i + "Posicion j:->" + j);

                        }
                        /* if (nombreMateria.equals(nombreMateriaSeleccionada)) {
                           
                        }*/
                    }
                }
                columnaMateriaSeleccionada = 0;

            }

        }
        modoSeleccionMateria = true;
        //Agregar una de modoSeleccionMateria;
        //Comparar con el tamaño encontrado 
        addMessage(FacesMessage.SEVERITY_INFO, "MATERIA", nombreMateriaSeleccionada);
    }
    int numEliminarSeleccionados = 0;

    public void contabilizarHorariosSeleccionados() {

        numEliminarSeleccionados = 0;

        for (int i = 0; i < listaHorarioCrear.size(); i++) {

            HorarioCrear hc = listaHorarioCrear.get(i);

            if (!modoSeleccionMateria) {

                String[] dias = {
                    hc.getLunes(),
                    hc.getMartes(),
                    hc.getMiercoles(),
                    hc.getJueves(),
                    hc.getViernes(),
                    hc.getSabado()
                };

                for (int j = 0; j < dias.length; j++) {

                    String dia = dias[j];

                    if (dia != null && !dia.isEmpty()) {

                        String[] contenido = dia.split("  ");

                        // 🔴 Validación defensiva
                        if (contenido.length < 7) {
                            System.out.println("⚠ CONTENIDO INCOMPLETO: " + dia);
                            continue;
                        }

                        String nombreMateria = contenido[0];
                        String grupo = contenido[4];
                        String semestre = contenido[5];
                        String reticula = contenido[6];

                        // 🔍 Imprimir comparación
                        System.out.println("----- COMPARANDO -----");
                        System.out.println("Materia  UI: " + nombreMateria + " | PAR: " + nombreMateriaSeleccionada);
                        System.out.println("Grupo    UI: " + grupo + " | PAR: " + valorGrupoSeleccionada);
                        System.out.println("Semestre UI: " + semestre + " | PAR: " + semestreSeleccionada);
                        System.out.println("Carrera  UI: " + reticula + " | PAR: " + carreraSeleccionada);

                        String grupoBD = grupo.replace("G:", "").trim();
                        String semestreBD = semestre.replace("S:", "").trim();
                        String reticulaBD = reticula.replace("C:", "").trim();

                        boolean matchMateria = nombreMateria.equals(nombreMateriaSeleccionada);
                        boolean matchGrupo = grupoBD.equals(valorGrupoSeleccionada);
                        boolean matchSemestre = semestreBD.equals(semestreSeleccionada);
                        boolean matchReticula = reticulaBD.equals(carreraSeleccionada);

                        System.out.println("✔ Materia coincide: " + matchMateria);
                        System.out.println("✔ Grupo coincide: " + matchGrupo);
                        System.out.println("✔ Semestre coincide: " + matchSemestre);
                        System.out.println("✔ Carrera coincide: " + matchReticula);

                        if (matchMateria && matchGrupo && matchSemestre && matchReticula) {
                            numEliminarSeleccionados++;
                            System.out.println("✅ COINCIDENCIA TOTAL → contador = " + numEliminarSeleccionados);
                        } else {
                            System.out.println("❌ NO COINCIDE");
                        }

                        System.out.println("----------------------");
                    }
                }
            }
        }

        System.out.println("TOTAL COINCIDENCIAS: " + numEliminarSeleccionados);
        System.out.println("====================================");
    }

    public void mostrarNotificacionCeldaMateria() {
        addMessage(FacesMessage.SEVERITY_WARN, "EMPALME MATERIA", "NO PUEDES PONER LA MATERIA EN UN HORARIO OCUPADO");
        actualizarTabla();
        cambiarModoColorTabla("A6E8CC");
        modoSeleccionMateria = false;
        filaSeleccionadaAnterior = -1;
        columnaSeleccionadaAnterior = -1;
    }

    public void actualizarTablaPorGrupo() {//Cuando presiono el boton modoGrupo

        booleanBotonAula = !booleanBotonAula; //Para activar o desacctivar el modo
        System.out.println("Valor booleanBotonAula: " + booleanBotonAula);

        if (booleanBotonGrupo && booleanBotonAula) {//Borra la tabla si salgo del modo
            inicializarHorario();
            activarodesactivarBotones(false);
            System.out.println("---G1----");
            //Vamos a desactivar todos los modos por si alguien se queda dentro. 
            modoIntercambio = false;
            activarModificar = false;
            eliminando = false;
            modoSeleccionMateria = false;

        } else {//Entrando al modo 
            booleanBuscarAula = false;//Indico internamente en cual modo estamos
            booleanBuscarGrupo = true;//Indico internamente que estamos en modo grupo(esto nos sirve para que busque la consulta correcta).             

            activarodesactivarBotones(true);
            actualizarTabla();
            //booleanBotonAula = !booleanBotonAula;
            System.out.println("---G2----");
        }

        System.out.println("Valor booleanBotonAula: " + booleanBotonAula);

    }

    public void actualizarTablaPorAula() {//Cuando presiono el boton modoAula
        booleanBotonGrupo = !booleanBotonGrupo;//Me aseguro que desactive el boton grupo cuando 

        if (booleanBotonGrupo && booleanBotonAula) {//Borra si el boton se inactiva
            inicializarHorario();
            System.out.println("---A1----");
            booleanBotonIntercambiar = false;
            //Por si se queda en modo intercambio y presiona modoAula para salir
            modoIntercambio = false;
        } else {
            booleanBuscarAula = true;//Indico en cual modo estamos en modoBuscarAula
            booleanBuscarGrupo = false;//Me aseguro de desactivar el boton

            actualizarTabla();//Busco actualizar la tabla si estoy en modo aula
            activarodesactivarBotones(true);

        }

        System.out.println("Valor booleanBotonGrupo:" + booleanBotonGrupo);

    }

    public void actualizarTabla() {

        //Verifica que los campos esten llenos antes de actualizar la tabla por los datos actuales
        if (verificacionDeCamposActualizado()) {
            inicializarHorario();//Asi evitamos que se sobreescriba la informacion que se actualiza en la tabla.
            //Busco los objetos en la BD por su id lo que esta puesto en el formulario
            Carrera carrera = carreraServicio.buscarPorId(Integer.parseInt(carreraS));// Busco el objeto Carrera por su id
            PeriodoEscolar periodoescolar = periodoEscolarServicio.buscarPorId(periodoS);//Busco el periodoEscolar por su id
            int semestre = Integer.parseInt(semestreS);//Paso el semestre String -> int
            aula = aulasServicio.buscarPorId(aulas);//Busco el objeto por su id

            if (booleanBuscarAula) {//Dependiendo si busco por grupo o por horario
                listaHorariosGenerados = horarioServicio.buscarHorariosPorAulas(periodoescolar,
                        aula);

                addMessage(FacesMessage.SEVERITY_INFO, "HORARIO POR AULA",
                        " \nAULA: " + aulas
                        + " " + carrera.getNombreCarrera()
                        + " \nPERIODO: " + periodoescolar.getIdentificacionCorta()
                );

            } else if (booleanBuscarGrupo) {
                listaHorariosGenerados = horarioServicio.buscarHorariosPorGrupos(carrera, semestre, periodoescolar,
                        valorgrupo);

                addMessage(FacesMessage.SEVERITY_INFO, "HORARIO POR GRUPO",
                        " \nGRUPO: " + valorgrupo
                        + " " + carrera.getNombreCarrera()
                        + " \nSEMESTRE: " + semestreS
                        + " \nPERIODO: " + periodoescolar.getIdentificacionCorta()
                );
            }

            if (listaHorariosGenerados.size() != 0) {
                Calendar cal = Calendar.getInstance();
                Date horainicial;
                int hora, fila, columna;
                for (int i = 0; i < listaHorariosGenerados.size(); i++) {
                    horario = listaHorariosGenerados.get(i);
                    materiasCarreras = materiasCarrerasServicio.buscarPorId(horario.getIdGrupo().getIdMateriaCarrera().getIdMateriaCarrera());
                    organigrama = organigramaServicio.buscarPorId(materiasCarreras.getMateria().getClaveArea().getClaveArea());
                    horainicial = horario.getHoraInicial();
                    cal.setTime(horainicial);
                    hora = cal.get(Calendar.HOUR_OF_DAY); // obtiene la hora en formato 24h
                    fila = obtenerFilaHorario(hora);
                    columna = horario.getDiaSemana() - 1;
                    for (int j = 0; j < listaHorarioCrear.size(); j++) {//Recorrer la tabla primero por las filas
                        horariocrear = listaHorarioCrear.get(j);
                        if (j == fila) {
                            //horariocrear.setColor("#3B0000");//Poner el color que le corresponde a las materias. 
                            asignarDatoSemana(horariocrear, columna, horario, "#" + organigrama.getColor(), "#3B0000");
                            break;
                        }

                    }
                }
            }
        }
    }

    public void generarGrupo() throws ParseException {
        grupo = new Grupos();
        Carrera carrera = carreraServicio.buscarPorId(Integer.parseInt(carreraS));
        materiasCarreras = materiasCarrerasServicio.buscarPorId(Integer.parseInt(asignaturaS));
        periodo = periodoEscolarServicio.buscarPorId(periodoS);

        listagrupogenerado = gruposServicio.buscarGrupoSii(//Busca el grupo en la base de datos
                materiasCarreras.getReticula().getReticula(),
                materiasCarreras.getIdMateriaCarrera(),
                periodoS,
                valorgrupo);

        if (listagrupogenerado != null && !listagrupogenerado.isEmpty()) {//Verificamos antes si la misma materia no ha sido insertada antes. 
            grupo = listagrupogenerado.get(0);
            addMessage(FacesMessage.SEVERITY_INFO, "INFO", "GRUPO ENCONTRADO");
        } else {
            grupo.setEstatusGrupo('A');
            grupo.setCapacidadGrupo(numestudiantes);
            grupo.setAlumnosInscritos(0);
            grupo.setCarrera(convertirsiglasCarrerra(carrera.getNombreCarrera()));
            grupo.setTipoPersonal('D');
            grupo.setReticula(materiasCarreras.getReticula());
            grupo.setIdMateriaCarrera(materiasCarreras);
            grupo.setPeriodo(periodo);
            grupo.setMateria(materiasCarreras.getMateria().getMateria());
            grupo.setGrupo(valorgrupo);
            System.out.println("Entro-----------------------------2");
            gruposServicio.insertarNuevoGrupo(grupo);
            // addMessage(FacesMessage.SEVERITY_INFO, "LISTO", "SE HA COMPLETADO  DE INSERTAR GRUPO");
        }

    }

    public Boolean comprobacionEmpalmesPorGrupos() {//Comprueba que las horas y aula no choque.
        listaHorariosEmpalme = new ArrayList<>();//Vamos ir buscando al menos todos los horarios que son empalme
        Horarios horarioempalme;
        for (int i = 0; i < listacoordenadas.size(); i++) {
            String celda = listacoordenadas.get(i);//Voy a recorrer todos estos ["1,1"]["1,2"]["1,3"]["1,4"]["1,5"]
            String[] indices = celda.split(",");//tienen el formato ["1,1"] entonces los separa respectivamente fila: 1 y columna: 1
            int fila = Integer.parseInt(indices[0]);
            int columna = Integer.parseInt(indices[1]);

            System.out.println("DiaSemana:" + (columna + 1) + "Hora Inicial:"
                    + obtenerStringDeHoraPorFila(fila - 1)
                    + "Hora Final:" + obtenerStringDeHoraPorFila(fila)
                    + "AulaBuscada:" + aulaBuscada + (aulaBuscada.getAula())
                    + "PeriodoS:" + periodoS);

            horarioempalme = horarioServicio.buscarHorarioPorEmpalme((short) (columna + 1),
                    obtenerStringDeHoraPorFila(fila - 1),
                    obtenerStringDeHoraPorFila(fila),
                    aulaBuscada, periodoS);

            if (horarioempalme != null) {
                listaHorariosEmpalme.add(horarioempalme);

            }

        }

        if (!listaHorariosEmpalme.isEmpty()) {
            addMessage(FacesMessage.SEVERITY_WARN, "EMPALME HORARIO", listaHorariosEmpalme.size() + " HORARIO(S) QUE SE EMPALMAN POR AULA");
            return true;
        } else {
            return false;
        }

    }

    public Boolean comprobacionEmpalmesPorAulaIntercambio() {//Para comprobar si hay empalme por cupo. (Aula)
        listaHorariosEmpalme = new ArrayList<>();//Vamos ir buscando al menos todos los horarios que son empalme
        Horarios horarioempalme;

        for (int i = 0; i < listacoordenadas.size(); i++) {
            String celda = listacoordenadas.get(i);//Voy a recorrer todos estos ["1,1"]["1,2"]["1,3"]["1,4"]["1,5"]
            String[] indices = celda.split(",");//tienen el formato ["1,1"] entonces los separa respectivamente fila: 1 y columna: 1
            int fila = Integer.parseInt(indices[0]);
            int columna = Integer.parseInt(indices[1]);

            System.out.println("DiaSemana:" + (columna + 1) + "Hora Inicial:"
                    + obtenerStringDeHoraPorFila(fila - 1)
                    + "Hora Final" + obtenerStringDeHoraPorFila(fila)
                    + "Grupo:" + valorGrupoSeleccionada
                    + "Numero Carrera:" + carreraSeleccionada
                    + "Semestres:" + semestreSeleccionada
                    + "PeriodoS" + periodoS
            );

            horarioempalme = horarioServicio.buscarHorarioPorEmpalmePorAula((short) (columna + 1),
                    obtenerStringDeHoraPorFila(fila - 1),
                    obtenerStringDeHoraPorFila(fila),
                    valorGrupoSeleccionada,
                    Integer.valueOf(carreraSeleccionada),
                    Integer.parseInt(semestreSeleccionada),
                    periodoS);

            if (horarioempalme != null) {
                listaHorariosEmpalme.add(horarioempalme);

            }

        }

        if (!listaHorariosEmpalme.isEmpty()) {
            addMessage(FacesMessage.SEVERITY_WARN, "EMPALME", listaHorariosEmpalme.size() + " HORARIOS QUE SE EMPALMAN POR GRUPO");
            return true;
        } else {
            return false;
        }

    }

    public Boolean comprobacionEmpalmesPorAulaHorario() {//Para comprobar si hay empalme por cupo. (Aula)
        listaHorariosEmpalme = new ArrayList<>();//Vamos ir buscando al menos todos los horarios que son empalme
        Horarios horarioempalme;

        for (int i = 0; i < listacoordenadas.size(); i++) {
            String celda = listacoordenadas.get(i);//Voy a recorrer todos estos ["1,1"]["1,2"]["1,3"]["1,4"]["1,5"]
            String[] indices = celda.split(",");//tienen el formato ["1,1"] entonces los separa respectivamente fila: 1 y columna: 1
            int fila = Integer.parseInt(indices[0]);
            int columna = Integer.parseInt(indices[1]);

            System.out.println("DiaSemana:" + (columna + 1) + "Hora Inicial:"
                    + obtenerStringDeHoraPorFila(fila - 1)
                    + "Hora Final" + obtenerStringDeHoraPorFila(fila)
                    + "Grupo:" + valorgrupo
                    + "Numero Carrera:" + carreraS
                    + "Semestres:" + semestreS
                    + "PeriodoS" + periodoS
            );

            horarioempalme = horarioServicio.buscarHorarioPorEmpalmePorAula((short) (columna + 1),
                    obtenerStringDeHoraPorFila(fila - 1),
                    obtenerStringDeHoraPorFila(fila),
                    valorgrupo, Integer.valueOf(carreraS), Integer.parseInt(semestreS), periodoS);

            if (horarioempalme != null) {
                listaHorariosEmpalme.add(horarioempalme);

            }

        }

        if (!listaHorariosEmpalme.isEmpty()) {
            addMessage(FacesMessage.SEVERITY_WARN, "EMPALME", listaHorariosEmpalme.size() + " HORARIOS QUE SE EMPALMAN POR GRUPO");
            return true;
        } else {
            return false;
        }

    }

    public void generarHorario() throws ParseException {
        if (verificacionDeCampos()) {
            //VERIFICA QUE LOS CAMPOS TENGAN ALGO

            materiasCarreras = materiasCarrerasServicio.buscarPorId(Integer.parseInt(asignaturaS));

            modoSeleccionMateria = false;//¨Por si paso de modo eliminar a modo Insertar Materia.
            aula = aulasServicio.buscarPorId(aulas);
            aulaBuscada = aulasServicio.buscarPorId(aulas);
            transformarCoordenadas();// pasa de esto 1,1;1,2;1,3;1,4;1,5 a ["1,1"]["1,2"]["1,3"]["1,4"]["1,5"]

            if (booleanBotonGrupo) {
                if (comprobacionEmpalmesPorGrupos()) {//Compruebo si no hay empalmes entre las elegidas
                    return;
                }
            }

            if (booleanBotonAula) {
                if (comprobacionEmpalmesPorAulaHorario()) {//Verifico que no haya ningun empalmen al generar el horario que voy a insertar.
                    return;
                }
                Carrera r = carreraServicio.buscarPorId(Integer.parseInt(carreraS));
                int s = Integer.parseInt(semestreS);
                PeriodoEscolar pe = periodoEscolarServicio.buscarPorId(periodoS);

                listaHorariosGenerados = horarioServicio.buscarHorariosPorGrupos(r,
                        s, pe,
                        valorgrupo);

                /* addMessage(FacesMessage.SEVERITY_INFO, "HORARIO MATERIA INSERTADA POR GRUPO",
                        " \nGRUPO: " + valorgrupo
                        + " " + r .getNombreCarrera()
                        + " \nSEMESTRE: " + semestreS
                        + " \nPERIODO: " +  pe.getIdentificacionCorta()
                );*/
            }

            if (listaHorariosGenerados != null) {//Verifico que no ya este la materia que voy a insertar en la base de datos. 
                for (Horarios h : listaHorariosGenerados) {
                    if (materiasCarreras.getMateria().getNombreCompletoMateria().equals(h.getMateria().getNombreCompletoMateria())) {
                        addMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "MATERIA YA INSERTADA");
                        return;
                    }
                }
            }

            for (int i = 0; i < listacoordenadas.size(); i++) {//Devuelo a la posicion original en fila
                String celda = listacoordenadas.get(i);
                String[] indices = celda.split(",");
                int fila = Integer.parseInt(indices[0]) - 1;
                int columna = Integer.parseInt(indices[1]);
                // Crea el nuevo valor
                String nuevoValor = fila + "," + columna;
                // Reemplaza en la misma posición
                listacoordenadas.set(i, nuevoValor);
            }

            if (camposllenos) {//COMPRUEBA QUE TODOS LOS CAMPOS TENGAN ALGO Y QUE HAYAN SIDO SELECCIOANDAS AL MENOS UNA CELDA
                generarGrupo();//Genera el grupo.         
                organigrama = organigramaServicio.buscarPorId(materiasCarreras.getMateria().getClaveArea().getClaveArea());
                System.out.println("listaHorarios{" + listaHorarios + "}");
                if (listaHorarios == null) {//VERIFICA TODOS LOS HORARIOS QUE SE HAN LLENADO}

                    listaHorarios = new ArrayList<>();//Vamos a guardar todos los horarios que se van a guardar en la base de datos
                    for (int i = 0; i < listacoordenadas.size(); i++) {
                        String celda = listacoordenadas.get(i);//Voy a recorrer todos estos ["1,1"]["1,2"]["1,3"]["1,4"]["1,5"]
                        String[] indices = celda.split(",");//tienen el formato ["1,1"] entonces los separa respectivamente fila: 1 y columna: 1
                        int fila = Integer.parseInt(indices[0]);
                        int columna = Integer.parseInt(indices[1]);

                        horario = new Horarios();
                        horario.setPeriodo(periodo);//Insertar periodo
                        horario.setTipoHorario('V');//Preguntar al maestro

                        horario.setDiaSemana((short) (columna + 1));//Guarda el dia de la semana con numero. 
                        asignarHoras(fila + 1);//Elige primero el rango que va a convertir, por ejemplo "7:00-8:00" los transforma a date 7 y 8. 
                        horario.setHoraInicial(horainicioMateria);
                        horario.setHoraFinal(horainiciofinMateria);
                        horario.setMateria(materiasCarreras.getMateria());
                        horario.setGrupo(valorgrupo);
                        horario.setAula(aula);
                        horario.setTipoPersonal('D');
                        horario.setIdGrupo(grupo);
                        System.out.println("i:" + i);
                        listaHorarios.add(horario);
                        horarioServicio.insertarHorario(horario);

                    }

                    addMessage(FacesMessage.SEVERITY_INFO, "ÉXITO", "SE HAN AGREGADO TODAS LOS HORARIOS A LA BD");
                    //ACOMODAR EN LA TABLA LA INFORMACION 
                    for (int i = 0; i < listaHorarios.size(); i++) {//Obtener el objeto donde tengo la informacion de las materias.
                        horario = listaHorarios.get(i);
                        String celda = listacoordenadas.get(i);//Voy a recorrer todos estos ["1,1"]["1,2"]["1,3"]["1,4"]["1,5"]
                        String[] indices = celda.split(",");//tienen el formato ["1,1"] entonces los separa respectivamente fila: 1 y columna: 1
                        int fila = Integer.parseInt(indices[0]);
                        int columna = Integer.parseInt(indices[1]);

                        for (int j = 0; j < listaHorarioCrear.size(); j++) {//Recorrer la tabla primero por las filas
                            horariocrear = listaHorarioCrear.get(j);
                            if (j == fila) {
                                //horariocrear.setColor("#3B0000");//Poner el color que le corresponde a las materias. 
                                asignarDatoSemana(horariocrear, columna, horario, "#" + organigrama.getColor(), "#3B0000");
                                break;
                            }

                        }

                    }

                    actualizarTabla();//para evitar que se pierdan los datos al momento de eliminar
                    //Ya al momento de insertar si quiero insertar otra para que no me mande mensaje de error despues de eliminar
                    listaHorarios = null;
                    //addMessage(FacesMessage.SEVERITY_INFO, "INFO", "LIMPIANDO LISTA 1" );
                }

            }
        }

    }

    public void transformarCoordenadas() {
        System.out.println("CELDAS SELECCIONADAS:" + posicionesSeleccionadas);

        // Crear ArrayList para guardar las coordenadas
        listacoordenadas = new ArrayList<>();

        if (posicionesSeleccionadas != null && !posicionesSeleccionadas.isEmpty()) {
            String[] pares = posicionesSeleccionadas.split(";");
            for (String par : pares) {
                listacoordenadas.add(par);
                System.out.println(par);
            }
        }

        // Mostrar el ArrayList
        System.out.println("Coordenadas guardadas en ArrayList: " + listacoordenadas);
    }

    public Boolean verificacionDeCampos() {//Verifica que los campos todos tengan algo
        System.out.println("CarreraS: " + carreraS + " PeriodosS: " + periodoS + " SemestreS: " + semestreS + " AsignaturaS: " + asignaturaS
                + " Aulas: " + aulas + " Valorgrupo: " + valorgrupo + " Numestudiantes " + numestudiantes);
        System.out.println("Celdas seleccionadas: " + posicionesSeleccionadas);

        String[] pares = null;
        camposllenos = false;
        // Aquí puedes convertirlo en una lista si lo deseas
        if (!posicionesSeleccionadas.equals("")) {
            pares = posicionesSeleccionadas.split(";");
        }

        if (carreraS == null || "".equals(carreraS)) {
            addMessage(FacesMessage.SEVERITY_WARN, "INFO", "CAMPO CARRERA VACIO");
            return false;
        } else if ("0".equals(semestreS)) {
            addMessage(FacesMessage.SEVERITY_WARN, "INFO", "CAMPO SEMESTRE VACIO");
            return false;
        } else if (aulas == null || "".equals(aulas)) {
            addMessage(FacesMessage.SEVERITY_WARN, "INFO", "CAMPO AULA VACIO");
            return false;
        } else if (numestudiantes == 0) {
            addMessage(FacesMessage.SEVERITY_WARN, "INFO", "CAMPO ESTUDIANTES EN 0");
            return false;
        } else if (periodoS == null || "".equals(periodoS)) {
            addMessage(FacesMessage.SEVERITY_WARN, "INFO", "CAMPO PERIODO VACIO");
            return false;
        } else if (asignaturaS == null || "".equals(asignaturaS)) {
            addMessage(FacesMessage.SEVERITY_WARN, "INFO", "CAMPO ASIGNATURA VACIO");
        } else if (valorgrupo == null || "".equals(valorgrupo)) {
            addMessage(FacesMessage.SEVERITY_WARN, "INFO", "CAMPO NOMBRE GRUPO VACIO");
            return false;
        } else if (pares == null) {
            addMessage(FacesMessage.SEVERITY_WARN, "INFO", "NO TIENES NINGUNA SELECCIONADA");
            return false;
        } else if (pares.length == 0) {
            addMessage(FacesMessage.SEVERITY_WARN, "INFO", "SELECCIONA TODAS LAS HORAS TOTAL: " + creditos);
            return false;
        } else if (pares.length != creditos) {
            addMessage(FacesMessage.SEVERITY_WARN, "INFO", "TE FALTAN: " + (creditos - pares.length) + " HORAS");
            return false;
        } else {
            camposllenos = true;

            // addMessage(FacesMessage.SEVERITY_INFO, "INFO", "CAMPOS COMPLETOS");
            return true;
        }
        return false;
    }

    public boolean verificacionDeCamposActualizado() {//Verifica que los campos todos tengan algo
        System.out.println("CarreraS: " + carreraS + " PeriodosS: " + periodoS + " SemestreS: " + semestreS + " AsignaturaS: " + asignaturaS
                + " Aulas: " + aulas + " Valorgrupo: " + valorgrupo + " Numestudiantes " + numestudiantes);
        System.out.println("Celdas seleccionadas: " + posicionesSeleccionadas);

        if (carreraS == null || "".equals(carreraS)) {
            addMessage(FacesMessage.SEVERITY_WARN, "INFO", "CAMPO CARRERAs VACIO");
            booleanCampoCarrera = false;
            activarodesactivarBotones(false);

            return false;
        } else if ("0".equals(semestreS)) {
            addMessage(FacesMessage.SEVERITY_WARN, "INFO", "CAMPO SEMESTRE VACIO");
            booleanCampoSemestre = false;
            activarodesactivarBotones(false);
            return false;
        } else if (periodoS == null || "".equals(periodoS)) {
            addMessage(FacesMessage.SEVERITY_WARN, "INFO", "CAMPO PERIODO VACIO");
            booleanCampoPeriodo = false;
            activarodesactivarBotones(false);
            return false;
        } else if (valorgrupo == null || "".equals(valorgrupo)) {
            addMessage(FacesMessage.SEVERITY_WARN, "INFO", "CAMPO NOMBRE GRUPO VACIO");
            booleanCampoGrupo = false;
            activarodesactivarBotones(false);
            return false;
        } else if (aulas == null || "".equals(aulas)) {
            addMessage(FacesMessage.SEVERITY_WARN, "INFO", "CAMPO NOMBRE AULA VACIO");
            booleanCampoAula = false;
            activarodesactivarBotones(false);
            return false;
        } else if (asignaturaS == null || "".equals(asignaturaS)) {
            addMessage(FacesMessage.SEVERITY_WARN, "INFO", "CAMPO NOMBRE AULA VACIO");
            booleanCampoAula = false;
            activarodesactivarBotones(false);
            return false;
        }
        // addMessage(FacesMessage.SEVERITY_INFO, "INFO", "CAMPOS COMPLETOS");

        //Booleans para controlar si tienen aunque sea algo los booleanos
        booleanCampoCarrera = true;
        booleanCampoSemestre = true;
        booleanCampoAula = true;
        booleanCampoPeriodo = true;
        booleanCampoGrupo = true;

        return true;
    }

    public void activarBotones() {
        if (booleanCampoCarrera && booleanCampoSemestre && booleanCampoAula && booleanCampoPeriodo && booleanCampoGrupo) {
            activarodesactivarBotones(true);
        }
    }

    public void activarodesactivarBotones(Boolean x) {
        booleanBotonInsertar = x;
        booleanBotonEliminar = x;
        booleanBotonModificar = x;
        booleanBotonIntercambiar = x;
    }

    public void transformarHorasDate(int x, int y) {
        Calendar calInicio = Calendar.getInstance();
        calInicio.set(Calendar.HOUR_OF_DAY, x); // 7 AM
        calInicio.set(Calendar.MINUTE, 0);
        calInicio.set(Calendar.SECOND, 0);
        calInicio.set(Calendar.MILLISECOND, 0);

        horainicioMateria = calInicio.getTime();

        Calendar calFin = Calendar.getInstance();
        calFin.set(Calendar.HOUR_OF_DAY, y); // 8 AM
        calFin.set(Calendar.MINUTE, 0);
        calFin.set(Calendar.SECOND, 0);
        calFin.set(Calendar.MILLISECOND, 0);

        horainiciofinMateria = calFin.getTime();

    }

    public String convertirsiglasCarrerra(String nombrecarrera) {
        switch (nombrecarrera) {
            case "INGENIERIA INDUSTRIAL":
                return "II";
            case "INGENIERIA ELECTRONICA":
                return "IE";
            case "INGENIERIA EN SISTEMAS COMPUTACIONALES":
                return "ISC";
            case "INGENIERIA EN GESTION EMPRESARIAL":
                return "IGE";
            case "INGENIERIA MECATRONICA":
                return "IM";
            case "CONTADOR PUBLICO":
                return "CP";
            case "MAESTRIA EN INGENIERIA ADMINISTRATIVA":
                return "MIA";
            case "INGENIERIA CIVIL":
                return "IC";
        }
        return "";
    }

    public void asignarDatoSemana(HorarioCrear x, int y, Horarios z, String colorFondo, String colorLetra) {

        switch (y) {
            case 1:
                x.setLunes(z.getMateria().getNombreCompletoMateria() + "  " + z.getAula().getAula() + "  " + colorFondo + "  " + colorLetra
                        + "  G:" + z.getGrupo() + "  S:" + z.getIdGrupo().getIdMateriaCarrera().getSemestreReticula() + "  C:" + z.getIdGrupo().getReticula().getReticula());
                break;
            case 2:
                x.setMartes(z.getMateria().getNombreCompletoMateria() + "  " + z.getAula().getAula() + "  " + colorFondo + "  " + colorLetra
                        + "  G:" + z.getGrupo() + "  S:" + z.getIdGrupo().getIdMateriaCarrera().getSemestreReticula() + "  C:" + z.getIdGrupo().getReticula().getReticula());
                break;
            case 3:
                x.setMiercoles(z.getMateria().getNombreCompletoMateria() + "  " + z.getAula().getAula() + "  " + colorFondo + "  " + colorLetra
                        + "  G:" + z.getGrupo() + "  S:" + z.getIdGrupo().getIdMateriaCarrera().getSemestreReticula() + "  C:" + z.getIdGrupo().getReticula().getReticula());
                break;
            case 4:
                x.setJueves(z.getMateria().getNombreCompletoMateria() + "  " + z.getAula().getAula() + "  " + colorFondo + "  " + colorLetra
                        + "  G:" + z.getGrupo() + "  S:" + z.getIdGrupo().getIdMateriaCarrera().getSemestreReticula() + "  C:" + z.getIdGrupo().getReticula().getReticula());
                break;
            case 5:
                x.setViernes(z.getMateria().getNombreCompletoMateria() + "  " + z.getAula().getAula() + "  " + colorFondo + "  " + colorLetra
                        + "  G:" + z.getGrupo() + "  S:" + z.getIdGrupo().getIdMateriaCarrera().getSemestreReticula() + "  C:" + z.getIdGrupo().getReticula().getReticula());
                break;
            case 6:
                x.setSabado(z.getMateria().getNombreCompletoMateria() + "  " + z.getAula().getAula() + "  " + colorFondo + "  " + colorLetra
                        + "  G:" + z.getGrupo() + "  S:" + z.getIdGrupo().getIdMateriaCarrera().getSemestreReticula() + "  C:" + z.getIdGrupo().getReticula().getReticula());
                break;
            default:
                System.out.println("Rango inválido");
        }
    }

    public void asignarHoras(int rango) {
        switch (rango) {
            case 1:
                transformarHorasDate(7, 8);
                break;
            case 2:
                transformarHorasDate(8, 9);
                break;
            case 3:
                transformarHorasDate(9, 10);
                break;
            case 4:
                transformarHorasDate(10, 11);
                break;
            case 5:
                transformarHorasDate(11, 12);
                break;
            case 6:
                transformarHorasDate(12, 13);
                break;
            case 7:
                transformarHorasDate(13, 14);
                break;
            case 8:
                transformarHorasDate(14, 15);
                break;
            case 9:
                transformarHorasDate(15, 16);
                break;
            case 10:
                transformarHorasDate(16, 17);
                break;
            case 11:
                transformarHorasDate(17, 18);
                break;
            case 12:
                transformarHorasDate(18, 19);
                break;
            case 13:
                transformarHorasDate(19, 20);
                break;
            default:
                System.out.println("Rango inválido");
        }
    }

    public String getSepararAtributos(String valor, int x) {//Sirve para separar MATEMATICAS DISCRETAS A1 #FAS31A
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }

        String[] partes = valor.split("  "); // o split("\\s+") si quieres separar por cualquier espacio
        if (partes.length > 0 && x == 0) {
            return partes[0] + "  " + partes[1];
        } else if (x == 1) {
            return partes[2];
        } else if (x == 2) {
            return partes[3];
        } else if (x == 3) {
            return partes[0];
        } else if (x == 4) {
            return partes[0] + "  " + partes[1] + "  " + partes[4] + "  " + partes[5] + "  " + partes[6];//Para poner la informacion a aula modificada.
        } else if (x == 5) {//Para cambiar color a celda modificada
            return partes[4] + "  " + partes[5] + "  " + partes[6];
        } else {
            return null;

        }
    }

    public void cambioCarrera() {
        inicializarHorario();

        if (verificacionDeCamposActualizado()) { //Esto sirve al inicio cuando apenas estoy llenando los campos verifica que todos esten llenos si no no ocurre nada.
            //Se evita que se desactive cualquier boton al cambiar un campo por culpa del ajax. Manteniendo el modo grupo y modo aula.
            if (booleanBotonGrupo && !booleanBotonAula) {//Si esta activado el boton
                activarodesactivarBotones(true);
                actualizarTabla();
            } else if (booleanBotonAula && !booleanBotonGrupo) {
                activarodesactivarBotones(true);
                actualizarTabla();

            } else {
                booleanBotonAula = true;
                booleanBotonGrupo = true;
                activarodesactivarBotones(false);
            }

        } else {
            booleanBotonAula = false;
            booleanBotonGrupo = false;
            activarodesactivarBotones(false);
        }

        if (carreraS != null && !"".equals(carreraS)) {
            listaMateria = materiasCarrerasServicio.buscarPorCarrera(Integer.parseInt(carreraS));

            //seleccionadocarreramateria2 = true;
        } else {

        }
    }

    public void cambioSemestre() {

        inicializarHorario();
        if (verificacionDeCamposActualizado()) { //Esto sirve al inicio cuando apenas estoy llenando los campos verifica que todos esten llenos si no no ocurre nada.
            //Se evita que se desactive cualquier boton al cambiar un campo por culpa del ajax. Manteniendo el modo grupo y modo aula.
            if (booleanBotonGrupo && !booleanBotonAula) {//Se evita que se desactive cualquier boton al cambiar un campo por culpa del ajax. 
                activarodesactivarBotones(true);
                actualizarTabla();
            } else if (booleanBotonAula && !booleanBotonGrupo) {
                activarodesactivarBotones(true);
                actualizarTabla();
            } else {
                booleanBotonAula = true;
                booleanBotonGrupo = true;
                activarodesactivarBotones(false);
            }
        } else {
            booleanBotonAula = false;
            booleanBotonGrupo = false;
            activarodesactivarBotones(false);
        }
        if (carreraS != null && !"".equals(carreraS)) {
            listaMateria = materiasCarrerasServicio.buscarAsinaturaSemestre(Integer.parseInt(carreraS), Integer.parseInt(semestreS));
        } else {

        }
    }

    public void actualizarCreditos() {
        if (verificacionDeCamposActualizado()) { //Esto sirve al inicio cuando apenas estoy llenando los campos verifica que todos esten llenos si no no ocurre nada.
            System.out.println("Entro a actualizar creditos true valor asignaturaS{" + asignaturaS + "}");
            //Se evita que se desactive cualquier boton al cambiar un campo por culpa del ajax. Manteniendo el modo grupo y modo aula.
            if (booleanBotonGrupo && !booleanBotonAula) {//Si esta activado el boton
                activarodesactivarBotones(true);
                actualizarTabla();
            } else if (booleanBotonAula && !booleanBotonGrupo) {
                activarodesactivarBotones(true);
                actualizarTabla();
            } else {
                booleanBotonAula = true;
                booleanBotonGrupo = true;
                activarodesactivarBotones(false);
            }
        } else {
            System.out.println("Entro a actualizar creditos false");
            booleanBotonAula = false;
            booleanBotonGrupo = false;
            activarodesactivarBotones(false);
        }
        if (!"".equals(asignaturaS) && asignaturaS != null) {//POr si esta activo inactivo.
            System.out.println("Entre para asignar creditos");
            materiasCarreras = materiasCarrerasServicio.buscarPorId(Integer.parseInt(asignaturaS));
            if (materiasCarreras != null) {
                creditos = materiasCarreras.getHorasPracticas() + materiasCarreras.getHorasTeoricas();
                // Enviar el valor actualizado al cliente ANTES del update
                PrimeFaces.current().ajax().addCallbackParam("creditos", this.creditos);
            }
        }

    }

    public void cambioPeriodo() {//Cauando cambie el periodo

        inicializarHorario();
        if (verificacionDeCamposActualizado()) { //Esto sirve al inicio cuando apenas estoy llenando los campos verifica que todos esten llenos si no no ocurre nada.
            //Se evita que se desactive cualquier boton al cambiar un campo por culpa del ajax. Manteniendo el modo grupo y modo aula.
            if (booleanBotonGrupo && !booleanBotonAula) {//Si esta activado el boton
                activarodesactivarBotones(true);
                actualizarTabla();
            } else if (booleanBotonAula && !booleanBotonGrupo) {
                activarodesactivarBotones(true);
                actualizarTabla();
            } else {
                booleanBotonAula = true;
                booleanBotonGrupo = true;
                activarodesactivarBotones(false);
            }
        } else {
            booleanBotonAula = false;
            booleanBotonGrupo = false;
            activarodesactivarBotones(false);
        }

    }

    public void cambioAula() {//Cuando cambie el campo aula
        System.out.println("Valor booleanBoton grupo" + booleanBotonGrupo);
        //Esto sirve al inicio cuando apenas estoy llenando los campos verifica que todos esten llenos si no no ocurre nada.

        if (verificacionDeCamposActualizado()) {//Verifico que todos los campos tengan algo, si lo tienen activamos los botones.
            //Se evita que se desactive cualquier boton al cambiar un campo por culpa del ajax. 
            //Manteniendo el modo grupo y modo aula. Este problema surgio que aunque tenia algo podian desactivarse los botones
            if (booleanBotonGrupo && !booleanBotonAula) {//Si esta activado el boton
                activarodesactivarBotones(true);
                actualizarTabla();
            } else if (booleanBotonAula && !booleanBotonGrupo) {
                activarodesactivarBotones(true);
                actualizarTabla();

            } else {
                booleanBotonAula = true;
                booleanBotonGrupo = true;
                activarodesactivarBotones(false);
            }

        } else {
            booleanBotonAula = false;
            booleanBotonGrupo = false;
            activarodesactivarBotones(false);
        }

        if ("".equals(aulas)) {//Si despues de poner en blanco la celda simplemente reinicializar horario
            inicializarHorario();//Por si las dudas vuelvo a poner otra vez el horario.              
        }

        if (booleanBuscarAula) {//Si estamos en modo aula al presionar el modoaula
            actualizarTabla();//Actualiza mi tabla con los horarios que deben ser por aula. 
            booleanBotonGrupo = false;//Como hay una actualizacion de los botones de nuevo poner el que nos interesa en blanco porque queremos que salga de ahi. 
            System.out.println("Valor booleanBoton grupo" + booleanBotonGrupo);

        }

        if (booleanBotonGrupo) {//Evitar que el cambio en el campo de texto aula si esta activo el modoBuscarpor GRUPO
            booleanBotonAula = false;
        }

    }

    public void cambioGrupo() {//Cuando cambie el campo grupo
        System.out.println("Cambio de grupo. ");
        //listaHorarios = null; //Limpiamos la lista 
        if (verificacionDeCamposActualizado()) { //Esto sirve al inicio cuando apenas estoy llenando los campos verifica que todos esten llenos si no no ocurre nada.
            //Se evita que se desactive cualquier boton al cambiar un campo por culpa del ajax. Manteniendo el modo grupo y modo aula.

            if (booleanBotonGrupo && !booleanBotonAula) {//Si esta activado el boton
                activarodesactivarBotones(true);
                actualizarTabla();
            } else if (booleanBotonAula && !booleanBotonGrupo) {
                activarodesactivarBotones(true);
                actualizarTabla();
            } else {
                booleanBotonAula = true;
                booleanBotonGrupo = true;
                activarodesactivarBotones(false);
            }
        } else {
            booleanBotonAula = false;
            booleanBotonGrupo = false;
            activarodesactivarBotones(false);
        }

        //  
        if ("".equals(valorgrupo)) {
            inicializarHorario();
        }

        if (booleanBuscarGrupo) {//Cuando presionamos en modogrupo
            actualizarTabla();//Actualiza mi tabla con los horarios que deben ser por Grupo. 
            booleanBotonAula = false;//Como actualiza el estado de los botones internamente vuelvo a ponerlo a su estado original
            System.out.println("Valor booleanBoton aula" + booleanBotonAula);

        }

        if (booleanBuscarAula) {//Evitar que el cambio en el campo de texto grupo si esta activo el modoBuscarpor AULA
            booleanBotonGrupo = false;
        }

    }
}
