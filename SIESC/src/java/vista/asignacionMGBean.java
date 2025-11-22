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
import javax.faces.event.ActionEvent;
import modelo.Organigrama;
import servicio.OrganigramaServicioLocal;

/**
 *
 * @author cris_
 */
@Named(value = "asignacionMGBean")
@SessionScoped
public class asignacionMGBean implements Serializable {

    @EJB
    private OrganigramaServicioLocal organigramaServicio;

    private Organigrama departamentoSeleccionado;

    List<Organigrama> listaDepartamentos;

    /**
     * Creates a new instance of asignacionMGBean
     */
    public asignacionMGBean() {
    }

    public void inicializar(ActionEvent event) {

    }

    public String abrirAsignacion() {
        // Lógica que antes estaba en inicializar()

        listaDepartamentos = organigramaServicio.traerListaOrganigrama();
        departamentoSeleccionado = new Organigrama();
        listaDepartamentos = new ArrayList();
        System.out.println("Lista departamento" + listaDepartamentos.size());

        // Navega a la página
        return "/academico/AsignacionActualizacion.xhtml?faces-redirect=true";
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
