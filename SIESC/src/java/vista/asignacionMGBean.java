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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import modelo.Carrera;
import modelo.Organigrama;
import modelo.PeriodoEscolar;
import modelo.Permisos;
import modelo.Personal;
import modelo.Usuario;
import servicio.CarreraServicioLocal;
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

    private Usuario usuario;
    private String periodoSeleccionado;
    private Integer semestreSeleccionado = 0;
    private Integer carreraSeleccionada;
    private String departamentoSeleccionado;
    private String docenteRFC;

    /**
     * Creates a new instance of asignacionMGBean
     */
    public asignacionMGBean() {
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

        System.out.println("DepartamentoSeleccionado ClaveArea(String):" + departamentoSeleccionado);
        System.out.println("CarreraSeleccionado Reticula(Integer):" + carreraSeleccionada);
        System.out.println("SemestreSeleccionado S(Integer)" + semestreSeleccionado);
        System.out.println("DocenteSeleciconado docenteRFC(String)" + docenteRFC);
        System.out.println("PeriodoSeleccionado p.periodo(String)" + periodoSeleccionado);

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
