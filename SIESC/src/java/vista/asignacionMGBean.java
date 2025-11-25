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
import javax.faces.event.ActionEvent;
import modelo.Carrera;
import modelo.Grupos;
import modelo.HorarioAsignatura;
import modelo.HorarioCrear;
import modelo.Horarios;
import modelo.Organigrama;
import modelo.PeriodoEscolar;
import modelo.Permisos;
import modelo.Personal;
import modelo.Usuario;
import servicio.CarreraServicioLocal;
import servicio.GruposServicioLocal;
import servicio.HorarioServicioLocal;
import servicio.OrganigramaServicioLocal;
import servicio.PeriodoEscolarServicioLocal;
import servicio.PermisoServicioLocal;
import servicio.PersonalServicioLocal;

/**
 *
 * @author cris_
 */
@Named(value = "asignacionMGBean")
@SessionScoped
public class asignacionMGBean implements Serializable {
    @EJB
    private HorarioServicioLocal horarioServicio;
    @EJB
    private GruposServicioLocal gruposServicio;
    @EJB
    private PeriodoEscolarServicioLocal periodoEscolarServicio;
    @EJB
    private PermisoServicioLocal permisoServicio;
    @EJB
    private CarreraServicioLocal carreraServicio;
    @EJB
    private OrganigramaServicioLocal organigramaServicio;
    @EJB
    private PersonalServicioLocal personalServicio;

    private List<Organigrama> listaDepartamentos;
    private List<Carrera> listaCarreras;
    private List<Permisos> listaPermisos;
    private List<PeriodoEscolar> listaPeriodos;
    private List<Integer> semestres;
    private List<Personal> listaDocentes;
    private List<HorarioAsignatura> listaHorarioCrear;

    private Usuario usuario;
    private String periodoSeleccionado;
    private Integer semestreSeleccionado;
    private Integer carreraSeleccionada;
    private String departamentoSeleccionado;
    private String docenteRFC;
    private HorarioAsignatura horarioCrearSeleccionado;

    /**
     * Creates a new instance of asignacionMGBean
     */
    public asignacionMGBean() {
    }

    private void addMessage(FacesMessage.Severity severity, String titulo, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, titulo, detalle));
    }

    public void validacionCampos() {
        if ((departamentoSeleccionado != null && !"".equals(departamentoSeleccionado))
                && (carreraSeleccionada != null && carreraSeleccionada != 0)
                && (periodoSeleccionado != null && !"".equals(periodoSeleccionado))
                && (semestreSeleccionado != null && !"".equals(semestreSeleccionado))) {

            addMessage(FacesMessage.SEVERITY_INFO, "CAMPOS COMPLETOS",
                    "Area: " + departamentoSeleccionado
                    + " Carrera: " + carreraServicio.buscarPorId(carreraSeleccionada).getNombreCarrera()
                    + " Periodo: " + periodoSeleccionado
                    + " Semestre: " + semestreSeleccionado);

        }

    }

    public void inicializar(ActionEvent event) {
        // 4. Obtiene el contexto actual de JSF (para trabajar con sesión, request, etc.)
        FacesContext contexto = FacesContext.getCurrentInstance();
        // 5. Recupera el objeto "usuario" guardado en la sesión
        usuario = (Usuario) contexto.getExternalContext().getSessionMap().get("usuario");
        listaDepartamentos = organigramaServicio.traerListaOrganigrama();
        //departamentoSeleccionado = new Organigrama();
        listaCarreras = carreraServicio.traerListaCarrera();
        listaPermisos = permisoServicio.buscarCarreras(usuario.getUsuario());
        listaPeriodos = periodoEscolarServicio.periodosEscolaresActivos();
        periodoSeleccionado = listaPeriodos.get(0).getPeriodo();
        semestres = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            semestres.add(i);
        }
        listaDocentes = personalServicio.personalActivos();

    }

    public void prueba() {
        System.out.println("-------------------------------------MENSAJE----------------------------------");
        System.out.println("DepartamentoSeleccionado ClaveArea(String):" + departamentoSeleccionado);
        System.out.println("CarreraSeleccionado Reticula(Integer):" + carreraSeleccionada);
        System.out.println("SemestreSeleccionado S(Integer)" + semestreSeleccionado);
        System.out.println("DocenteSeleciconado docenteRFC(String)" + docenteRFC);
        System.out.println("PeriodoSeleccionado p.periodo(String)" + periodoSeleccionado);
        System.out.println("-------------------------------------MENSAJE----------------------------------");

    }

    public void cambioPeriodo() {

        prueba();
        validacionCampos();
    }

    public void cambioDepartamento() {

        listaDocentes = personalServicio.personalPorArea(departamentoSeleccionado);
        System.out.println("Tamaño listadocentes" + listaDocentes.size());
        prueba();
        listaHorarioCrear = gruposServicio.buscarGruposPorDepartamento(carreraSeleccionada, semestreSeleccionado, periodoSeleccionado, departamentoSeleccionado);
        System.out.println("listaHorarioCrear->" + listaHorarioCrear.size());
        validacionCampos();
    }

    public void cambioCarrera() {
        prueba();
        listaHorarioCrear = gruposServicio.buscarGruposPorDepartamento(carreraSeleccionada, semestreSeleccionado, periodoSeleccionado, departamentoSeleccionado);
        System.out.println("listaHorarioCrear->" + listaHorarioCrear.size());
        validacionCampos();

    }

    public void cambioSemestre() {
        prueba();
        listaHorarioCrear = gruposServicio.buscarGruposPorDepartamento(carreraSeleccionada, semestreSeleccionado, periodoSeleccionado, departamentoSeleccionado);
        System.out.println("listaHorarioCrear->" + listaHorarioCrear.size());
        validacionCampos();

    }

    public void filaSeleccionada() {
        listaDocentes = personalServicio.personalPorArea(departamentoSeleccionado);
        System.out.println("HorarioSeleccionado" + horarioCrearSeleccionado);
                addMessage(FacesMessage.SEVERITY_INFO, "MATERIA SELECCIONADA",
                    "Materia"+horarioCrearSeleccionado.getAsignatura()
                +" Clave: "+horarioCrearSeleccionado.getMateria()
                +" Grupo: "+horarioCrearSeleccionado.getGrupo());

    }

    public void accionEliminar(HorarioAsignatura ha) {
        if (ha != null) {
            Grupos g = gruposServicio.buscarPorIdInt(ha.getId());
            g.setRfc(null);
            gruposServicio.actualizar(g);
            List<Horarios> lh = horarioServicio.buscarHorarioPorMateriayGrupo(
                    carreraSeleccionada,
                    semestreSeleccionado,
                    periodoSeleccionado,
                    ha.getMateria(),
                    ha.getGrupo());
            System.out.println("HorarioCrearSeleccionado"+ha);
            for (Horarios h : lh) {
                h.setRfc(null);
                horarioServicio.actualizar(h);
                System.out.println("Actualize");
            }
            addMessage(FacesMessage.SEVERITY_INFO, "ACTUALIZANDO",
                    "SE HA ELIMINADO DOCENTE");
            listaHorarioCrear = gruposServicio.buscarGruposPorDepartamento(carreraSeleccionada, semestreSeleccionado, periodoSeleccionado, departamentoSeleccionado);
        }
    }

    public void guardarMaestro() {
        HorarioAsignatura ha = null;

        Personal maestro = personalServicio.buscarPorId(docenteRFC);

        for (HorarioAsignatura h : listaHorarioCrear) {
            if (h.getId() == horarioCrearSeleccionado.getId()) {
                // Aquí actualizas el objeto real
                h.setDocente(maestro.getNombreEmpleado()
                        + " " + maestro.getApellidoPaterno()
                        + " " + maestro.getApellidoMaterno());
                ha = h;
                break;
            }
        }

        if (ha != null) {
            System.out.println("=================================");
            System.out.println("Grupo: " + ha.getGrupo());
            System.out.println("IdGrupo: " + ha.getId());
            System.out.println("Materia: " + ha.getMateria());
            System.out.println("Asignatura: " + ha.getAsignatura());
            System.out.println("Docente: " + ha.getDocente());
            System.out.println("Lunes: " + ha.getLunes());
            System.out.println("Martes: " + ha.getMartes());
            System.out.println("Miércoles: " + ha.getMiercoles());
            System.out.println("Jueves: " + ha.getJueves());
            System.out.println("Viernes: " + ha.getViernes());
            System.out.println("Sábado: " + ha.getSabado());
            System.out.println("=================================");

            Grupos g = gruposServicio.buscarPorIdInt(ha.getId());
            Personal objetorfc = personalServicio.buscarPorId(docenteRFC);
            g.setRfc(objetorfc);
            gruposServicio.actualizar(g);

            List<Horarios> lh = horarioServicio.buscarHorarioPorMateriayGrupo(
                    carreraSeleccionada,
                    semestreSeleccionado,
                    periodoSeleccionado,
                    horarioCrearSeleccionado.getMateria(),
                    ha.getGrupo());
            System.out.println("===== PARÁMETROS PARA buscarHorariosPorGrupos =====");
            System.out.println("Carrera: " + carreraServicio.buscarPorId(carreraSeleccionada));
            System.out.println("Carrera (ID): " + carreraSeleccionada);
            System.out.println("Semestre: " + semestreSeleccionado);
            System.out.println("Periodo: " + periodoEscolarServicio.buscarPorId(periodoSeleccionado));
            System.out.println("Periodo (ID): " + periodoSeleccionado);
            System.out.println("Materia(NNN): " + horarioCrearSeleccionado.getMateria());
            System.out.println("====================================================");
            for (Horarios h : lh) {
                h.setRfc(objetorfc);
                horarioServicio.actualizar(h);
            }
            addMessage(FacesMessage.SEVERITY_INFO, "ACTUALIZANDO",
                    "DOCENTE:" + objetorfc.getNombreEmpleado() + " " + objetorfc.getApellidoPaterno() + " " + objetorfc.getApellidoMaterno() + " SE HA INSERTADO.");

        }

        docenteRFC = "";
    }

    public HorarioAsignatura getHorarioCrearSeleccionado() {
        return horarioCrearSeleccionado;
    }

    public void setHorarioCrearSeleccionado(HorarioAsignatura horarioCrearSeleccionado) {
        this.horarioCrearSeleccionado = horarioCrearSeleccionado;
    }

    public List<HorarioAsignatura> getListaHorarioCrear() {
        return listaHorarioCrear;
    }

    public void setListaHorarioCrear(List<HorarioAsignatura> listaHorarioCrear) {
        this.listaHorarioCrear = listaHorarioCrear;
    }

    public Integer getCarreraSeleccionada() {
        return carreraSeleccionada;
    }

    public void setCarreraSeleccionada(Integer carreraSeleccionada) {
        this.carreraSeleccionada = carreraSeleccionada;
    }

    public Integer getSemestreSeleccionado() {
        return semestreSeleccionado;
    }

    public void setSemestreSeleccionado(Integer semestreSeleccionado) {
        this.semestreSeleccionado = semestreSeleccionado;
    }

    public List<Integer> getSemestres() {
        return semestres;
    }

    public void setSemestres(List<Integer> semestres) {
        this.semestres = semestres;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Permisos> getListaPermisos() {
        return listaPermisos;
    }

    public void setListaPermisos(List<Permisos> listaPermisos) {
        this.listaPermisos = listaPermisos;
    }

    public List<Carrera> getListaCarreras() {
        return listaCarreras;
    }

    public void setListaCarreras(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }

    public String getDepartamentoSeleccionado() {
        return departamentoSeleccionado;
    }

    public void setDepartamentoSeleccionado(String departamentoSeleccionado) {
        this.departamentoSeleccionado = departamentoSeleccionado;
    }

    public List<Organigrama> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Organigrama> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public List<Personal> getListaDocentes() {
        return listaDocentes;
    }

    public void setListaDocentes(List<Personal> listaDocentes) {
        this.listaDocentes = listaDocentes;
    }

    public String getDocenteRFC() {
        return docenteRFC;
    }

    public void setDocenteRFC(String docenteRFC) {
        this.docenteRFC = docenteRFC;
    }

}
