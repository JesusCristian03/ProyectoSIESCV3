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
import modelo.Permisos;
import modelo.Usuario;
import servicio.CarreraServicioLocal;
import servicio.OrganigramaServicioLocal;
import servicio.PermisoServicioLocal;

/**
 *
 * @author cris_
 */
@Named(value = "asignacionMGBean")
@SessionScoped
public class asignacionMGBean implements Serializable {

    @EJB
    private PermisoServicioLocal permisoServicio;

    @EJB
    private CarreraServicioLocal carreraServicio;

    @EJB
    private OrganigramaServicioLocal organigramaServicio;

    private Organigrama departamentoSeleccionado;

    List<Organigrama> listaDepartamentos;

    List<Carrera> listaCarreras;

    List<Permisos> listaPermisos;

    private Usuario usuario;

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
        departamentoSeleccionado = new Organigrama();
        listaCarreras = carreraServicio.traerListaCarrera();
        listaPermisos = permisoServicio.buscarCarreras(usuario.getUsuario());

        System.out.println("Lista departamento" + listaDepartamentos.size());

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

    public Organigrama getDepartamentoSeleccionado() {
        return departamentoSeleccionado;
    }

    public void setDepartamentoSeleccionado(Organigrama departamentoSeleccionado) {
        this.departamentoSeleccionado = departamentoSeleccionado;
    }

    public List<Organigrama> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Organigrama> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

}
